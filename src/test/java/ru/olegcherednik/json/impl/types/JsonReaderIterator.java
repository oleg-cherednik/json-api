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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import lombok.RequiredArgsConstructor;
import ru.olegcherednik.json.api.JsonException;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @param <V> Type of the value object
 * @author Oleg Cherednik
 * @since 05.01.2024
 */
@RequiredArgsConstructor
public class JsonReaderIterator<V> implements Iterator<V> {

    protected final JsonReader in;
    protected final TypeAdapter<V> typeAdapter;

    @Override
    public boolean hasNext() {
        try {
            return in.hasNext();
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public V next() {
        try {
            if (!hasNext())
                throw new NoSuchElementException();
            return typeAdapter.read(in);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

}
