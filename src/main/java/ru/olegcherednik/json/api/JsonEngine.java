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

package ru.olegcherednik.json.api;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an implementation of concrete json framework (e.g. Jackson or Gson).
 *
 * @author Oleg Cherednik
 * @since 24.11.2023
 */
public interface JsonEngine {

    // ---------- read String ----------

    <V> V readValue(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> List<V> readList(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> Set<V> readSet(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    List<Map<String, Object>> readListOfMap(String json) throws IOException;

    // @NotNull
    <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) throws IOException;

    // ---------- read Reader ----------

    <V> V readValue(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> List<V> readList(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> Set<V> readSet(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) throws IOException;

    // @NotNull
    List<Map<String, Object>> readListOfMap(Reader reader) throws IOException;

    // @NotNull
    <V> AutoCloseableIterator<V> readListLazy(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(Reader reader) throws IOException;

    // ---------- write ----------

    <V> String writeValue(V obj) throws IOException;

    <V> void writeValue(V obj, Writer writer) throws IOException;

    // ---------- convert ----------

    <V> Map<String, Object> convertToMap(V obj);

}
