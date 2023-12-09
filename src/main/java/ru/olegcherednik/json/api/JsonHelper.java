/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package ru.olegcherednik.json.api;

import java.util.Optional;

/**
 * @author Oleg Cherednik
 * @since 19.11.2014
 */
public final class JsonHelper {

    private static final JsonEngineFactory JSON_ENGINE_FACTORY = JsonEngineFactoryProvider.findJsonEngineFactory();
    private static JsonEngine jsonEngine = createJsonEngine(JsonSettings.DEFAULT);
    private static JsonEngine prettyPrintJsonEngine = createPrettyPrintJsonEngine(JsonSettings.DEFAULT);

    public static synchronized JsonEngine getJsonEngine() {
        return jsonEngine;
    }

    public static synchronized JsonEngine getPrettyPrintJsonEngine() {
        return prettyPrintJsonEngine;
    }

    public static synchronized void setDefaultSettings(JsonSettings jsonSettings) {
        jsonEngine = JSON_ENGINE_FACTORY.createJsonEngine(jsonSettings);
        prettyPrintJsonEngine = JSON_ENGINE_FACTORY.createPrettyPrintJsonEngine(jsonSettings);
    }

    public static JsonEngine createJsonEngine(JsonSettings jsonSettings) {
        return JSON_ENGINE_FACTORY.createJsonEngine(Optional.ofNullable(jsonSettings)
                                                            .orElse(JsonSettings.DEFAULT));
    }

    public static JsonEngine createPrettyPrintJsonEngine(JsonSettings jsonSettings) {
        return JSON_ENGINE_FACTORY.createPrettyPrintJsonEngine(Optional.ofNullable(jsonSettings)
                                                                       .orElse(JsonSettings.DEFAULT));
    }

}
