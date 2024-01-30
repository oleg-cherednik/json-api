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

package ru.olegcherednik.json.impl.types;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * @param <K> Type of the key object
 * @param <V> Type of the value object
 * @author Oleg Cherednik
 * @since 09.01.2021
 */
@RequiredArgsConstructor
public class LinkedHashMapParameterizedType<K, V> implements ParameterizedType {

    protected final Class<K> keyClass;
    protected final Class<V> valueClass;

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { keyClass, valueClass };
    }

    @Override
    public Type getRawType() {
        return LinkedHashMap.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

}
