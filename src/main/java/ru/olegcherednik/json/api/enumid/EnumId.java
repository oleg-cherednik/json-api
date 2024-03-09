/*
 * Copyright Oleg Cherednik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.olegcherednik.json.api.enumid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This interface hides enum implementation and enum constant id. In general case, it is not good to use
 * {@link Enum#name()} as constant in database or in any formatted string, because after any refactoring,
 * all existed database records should be modified as well.<p>
 * For serialization, the enum should override {@link #getId()} to define a string value.<p>
 * For deserialization, the enum should contain exactly one static method with single {@link String} argument
 * and either be annotated with special annotation or has name <b>parseId</b>.
 *
 * @author Oleg Cherednik
 * @since 17.10.2021
 */
public interface EnumId {

    String name();

    default String getId() {
        return name();
    }

    static <T extends Enum<?> & EnumId> String getId(T obj, T def) {
        return Optional.ofNullable(obj).orElse(def).getId();
    }

    static <T extends Enum<?> & EnumId> T parseName(Class<T> cls, String name) {
        return Optional.ofNullable(parseName(cls.getEnumConstants(), name, null))
                       .orElseThrow(() -> new EnumConstantNotPresentException(cls, name));
    }

    static <T extends Enum<?> & EnumId> T parseName(Class<T> cls, String name, T def) {
        return parseName(cls.getEnumConstants(), name, def);
    }

    static <T extends Enum<?> & EnumId> T parseIdOrName(Class<T> cls, String idOrName) {
        return Optional.ofNullable(parseIdOrName(cls, idOrName, null))
                       .orElseThrow(() -> new EnumConstantNotPresentException(cls, idOrName));
    }

    static <T extends Enum<?> & EnumId> T parseIdOrName(Class<T> cls, String idOrName, T def) {
        T[] values = cls.getEnumConstants();
        return Optional.ofNullable(parseId(values, idOrName, null))
                       .orElseGet(() -> parseName(values, idOrName, def));
    }

    static <T extends Enum<?> & EnumId> T parseId(Class<T> cls, String id) {
        return Optional.ofNullable(parseId(cls.getEnumConstants(), id, null))
                       .orElseThrow(() -> new EnumConstantNotPresentException(cls, id));
    }

    static <T extends Enum<?> & EnumId> T parseId(Class<T> cls, String id, T def) {
        return parseId(cls.getEnumConstants(), id, def);
    }

    static <T extends EnumId> T parseName(T[] values, String name, T def) {
        for (T value : values)
            if (value.name().equalsIgnoreCase(name))
                return value;

        return def;
    }

    static <T extends EnumId> T parseId(T[] values, String id, T def) {
        for (T value : values)
            if (id == null ? value.getId() == null : id.equalsIgnoreCase(value.getId()))
                return value;

        return def;
    }

    // ---------- MAP_ID ----------

    static <T extends Enum<?> & EnumId> Map<String, T> createMapId(Class<T> cls) {
        return createMap(cls, EnumId::getId);
    }

    static <T extends Enum<?> & EnumId> T parseId(Class<T> cls, Map<String, T> mapId, String id) {
        String lowerCaseId = id == null ? null : id.toLowerCase(Locale.ROOT);
        return Optional.ofNullable(mapId.get(lowerCaseId))
                       .orElseThrow(() -> new EnumConstantNotPresentException(cls, id));
    }

    static <T extends Enum<?> & EnumId> T parseId(Class<T> cls, Map<String, T> mapId, String id, T def) {
        String lowerCaseId = id == null ? null : id.toLowerCase(Locale.ROOT);
        return mapId.getOrDefault(lowerCaseId, def);
    }

    // ---------- MAP_NAME ----------

    static <T extends Enum<?> & EnumId> Map<String, T> createMapName(Class<T> cls) {
        return createMap(cls, EnumId::name);
    }

    static <T extends Enum<?> & EnumId> T parseName(Class<T> cls, Map<String, T> mapName, String name) {
        return Optional.ofNullable(name)
                       .map(n -> name.toLowerCase(Locale.ROOT))
                       .map(mapName::get)
                       .orElseThrow(() -> new EnumConstantNotPresentException(cls, name));
    }

    static <T extends Enum<?> & EnumId> T parseName(Class<T> cls, Map<String, T> mapName, String name, T def) {
        return mapName.getOrDefault(name.toLowerCase(Locale.ROOT), def);
    }

    // ---------- MAP_ID + MAP_NAME ----------

    static <T extends Enum<?> & EnumId> T parseIdOrName(Class<T> cls,
                                                        Map<String, T> mapId,
                                                        Map<String, T> mapName,
                                                        String idOrName) {
        return Optional.ofNullable(parseId(cls, mapId, idOrName, null))
                       .orElseGet(() -> parseName(cls, mapName, idOrName));
    }

    static <T extends Enum<?> & EnumId> T parseIdOrName(Class<T> cls,
                                                        Map<String, T> mapId,
                                                        Map<String, T> mapName,
                                                        String idOrName,
                                                        T def) {
        return Optional.ofNullable(parseId(cls, mapId, idOrName, null))
                       .orElseGet(() -> parseName(cls, mapName, idOrName, def));
    }

    // ----------

    static <T extends Enum<?> & EnumId> Map<String, T> createMap(Class<T> cls, Function<T, String> getKey) {
        Map<String, T> map = new HashMap<>();

        for (T value : cls.getEnumConstants()) {
            String key = Optional.ofNullable(getKey.apply(value))
                                 .map(k -> k.toLowerCase(Locale.ROOT))
                                 .orElse(null);

            if (map.put(key, value) != null)
                throw new ExceptionInInitializerError();
        }

        return map.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(map);
    }

}
