/*
 * Copyright of Oleg Cherednik
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Synchronized;

import java.util.Optional;

/**
 * @author Oleg Cherednik
 * @since 19.11.2014
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonHelper {

    private static final JsonEngineFactory JSON_ENGINE_FACTORY = JsonEngineFactoryProvider.findJsonEngineFactory();

    @Getter(onMethod_ = { @Synchronized })
    private static JsonEngine jsonEngine = createJsonEngine(JsonSettings.DEFAULT);
    @Getter(onMethod_ = { @Synchronized })
    private static JsonEngine prettyPrintJsonEngine = createPrettyPrintJsonEngine(JsonSettings.DEFAULT);
    private static JsonSettings defaultSettings = JsonSettings.DEFAULT;

    public static synchronized void reset() {
        setDefaultSettings(JsonSettings.DEFAULT);
    }

    public static synchronized void setDefaultSettings(JsonSettings settings) {
        defaultSettings = settings;
        jsonEngine = JSON_ENGINE_FACTORY.createJsonEngine(defaultSettings);
        prettyPrintJsonEngine = JSON_ENGINE_FACTORY.createPrettyPrintJsonEngine(defaultSettings);
    }

    public static JsonEngine createJsonEngine(JsonSettings settings) {
        return JSON_ENGINE_FACTORY.createJsonEngine(Optional.ofNullable(settings).orElse(defaultSettings));
    }

    public static JsonEngine createPrettyPrintJsonEngine(JsonSettings settings) {
        return JSON_ENGINE_FACTORY.createPrettyPrintJsonEngine(Optional.ofNullable(settings).orElse(defaultSettings));
    }

}
