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

package ru.olegcherednik.json.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.olegcherednik.json.api.JsonEngine;
import ru.olegcherednik.json.api.JsonEngineFactory;
import ru.olegcherednik.json.api.JsonSettings;
import ru.olegcherednik.json.impl.adapters.ZonedDateTimeTypeAdapter;
import ru.olegcherednik.json.impl.factories.EnumIdTypeAdapterFactory;
import ru.olegcherednik.json.impl.factories.IteratorTypeAdapterFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Oleg Cherednik
 * @since 05.01.2024
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StaticJsonEngineFactory implements JsonEngineFactory {

    private static final StaticJsonEngineFactory INSTANCE = new StaticJsonEngineFactory();


    public static StaticJsonEngineFactory getInstance() {
        return INSTANCE;
    }

    public static String getMainClass() {
        return "com.google.gson.Gson";
    }

    // ---------- JsonEngineFactory ----------

    @Override
    public JsonEngine createJsonEngine(JsonSettings settings) {
        DateTimeFormatter df = settings.getZonedDateTimeFormatter();

        if (settings.getZoneId() != null)
            df = df.withZone(settings.getZoneId());

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(EnumIdTypeAdapterFactory.INSTANCE)
                .registerTypeAdapterFactory(IteratorTypeAdapterFactory.INSTANCE)
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter(df).nullSafe())
                .create();
        return new GsonEngine(gson);
    }

    @Override
    public JsonEngine createPrettyPrintJsonEngine(JsonSettings settings) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(EnumIdTypeAdapterFactory.INSTANCE)
                .registerTypeAdapterFactory(IteratorTypeAdapterFactory.INSTANCE)
                .create();
        return new GsonEngine(gson);
    }

}
