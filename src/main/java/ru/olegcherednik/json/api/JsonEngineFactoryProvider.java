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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Oleg Cherednik
 * @since 19.11.2023
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonEngineFactoryProvider {

    public static JsonEngineFactory findJsonEngineFactory() {
        Set<String> files = findJsonEngineFactoryFiles();

        if (files.size() > 1) {
            log.error("Class path contains multiple {}", JsonEngineFactory.class.getSimpleName());

            for (String file : files)
                log.error("JsonUtils: Found {} in [{}]", JsonEngineFactory.class.getSimpleName(), file);

            throw new RuntimeException(String.format("Class path contains multiple %s",
                                                     JsonEngineFactory.class.getSimpleName()));
        }

        try {
            Class<? extends JsonEngineFactory> cls =
                    Class.forName("ru.olegcherednik.json.impl.StaticJsonEngineFactory")
                         .asSubclass(JsonEngineFactory.class);
            return (JsonEngineFactory) cls.getMethod("getInstance").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<String> findJsonEngineFactoryFiles() {
        try {
            Enumeration<URL> urls =
                    getJsonEngineFactoryPaths("ru/olegcherednik/json/impl/StaticJsonEngineFactory.class");
            return Collections.list(urls).stream()
                              .map(URL::getFile)
                              .collect(Collectors.toSet());
        } catch (IOException e) {
            log.error("Error getting resources from path", e);
            return Collections.emptySet();
        }
    }

    private static Enumeration<URL> getJsonEngineFactoryPaths(String name) throws IOException {
        ClassLoader classLoader = JsonEngineFactory.class.getClassLoader();
        return classLoader == null ? ClassLoader.getSystemResources(name) : classLoader.getResources(name);
    }

}
