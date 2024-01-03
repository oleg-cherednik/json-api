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

package ru.olegcherednik.json.api.enumid;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.olegcherednik.json.api.JsonException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Oleg Cherednik
 * @since 01.12.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumIdSupport {

    public static final Class<? extends Annotation> JSON_CREATOR_CLASS = EnumIdJsonCreator.class;

    // @NotNull
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public static <T> Function<String, T> createFactory(Class<T> rawType) {
        List<Method> methods = getJsonCreatorMethods(rawType);

        if (methods.size() > 1) {
            return id -> {
                throw new JsonException("Multiple methods with '%s' annotation was found in '%s' class",
                                        JSON_CREATOR_CLASS.getSimpleName(), rawType.getSimpleName());
            };
        }

        if (methods.size() == 1)
            return createFunc(methods.get(0));

        Method method = getParseIdMethod(rawType);

        if (method == null) {
            return id -> {
                throw new JsonException("Factory method for EnumId '%s' was not found",
                                        rawType.getSimpleName());
            };
        }

        return createFunc(method);
    }


    @SuppressWarnings({ "deprecation", "PMD.AvoidAccessibilityAlteration" })
    private static <T> Function<String, T> createFunc(Method method) {
        return id -> {
            boolean accessible = method.isAccessible();

            try {
                method.setAccessible(true);
                return (T) method.invoke(null, id);
            } catch (Exception e) {
                throw new JsonException(e);
            } finally {
                method.setAccessible(accessible);
            }
        };
    }

    private static List<Method> getJsonCreatorMethods(final Class<?> rawType) {
        List<Method> res = new ArrayList<>();
        Class<?> type = rawType;

        while (type != Object.class) {
            for (Method method : type.getDeclaredMethods())
                if (isValidFactoryMethod(method))
                    res.add(method);

            type = type.getSuperclass();
        }

        return res;
    }

    @SuppressWarnings({ "PMD.AvoidReassigningParameters", "PMD.EmptyCatchBlock" })
    private static Method getParseIdMethod(Class<?> rawType) {
        while (rawType != Object.class) {
            try {
                return rawType.getDeclaredMethod("parseId", String.class);
            } catch (NoSuchMethodException ignore) {
                // ignore
            }

            rawType = rawType.getSuperclass();
        }

        return null;
    }

    private static boolean isValidFactoryMethod(Method method) {
        return Modifier.isStatic(method.getModifiers())
                && method.isAnnotationPresent(JSON_CREATOR_CLASS)
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0] == String.class;
    }
}
