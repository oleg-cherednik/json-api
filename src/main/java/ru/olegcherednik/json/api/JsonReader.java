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

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * This class represents all available reading methods for a {@link JsonEngine}.
 *
 * @author Oleg Cherednik
 * @since 02.01.2021
 */
@SuppressWarnings("PMD.ExcessivePublicCount")
@RequiredArgsConstructor
public class JsonReader {

    protected static final char FIND_OPEN_BRACKET = '<';
    protected static final char FIND_CLOSE_BRACKET = '>';
    protected static final char OPEN_CURLY_BRACKET = '{';
    protected static final char OPEN_SQUARE_BRACKET = '[';

    protected final Supplier<JsonEngine> supplier;

    // ---------- read String----------

    public <V> V readValue(String json, Class<V> valueClass) {
        if (isBlank(json))
            return null;

        requireNotCollection(valueClass);
        requireNotNullValueClass(valueClass);
        return apply(jsonEngine -> jsonEngine.readValue(json, valueClass));
    }

    // @NotNull
    public List<Object> readList(String json) {
        return readList(json, Object.class);
    }

    // @NotNull
    public <V> List<V> readList(String json, Class<V> valueClass) {
        if (isNullOrEmpty(json))
            return Collections.emptyList();

        requireNotNullValueClass(valueClass);
        return apply(jsonEngine -> jsonEngine.readList(json, valueClass));
    }

    // @NotNull
    public Set<Object> readSet(String json) {
        return readSet(json, Object.class);
    }

    // @NotNull
    public <V> Set<V> readSet(String json, Class<V> valueClass) {
        if (isNullOrEmpty(json))
            return Collections.emptySet();

        requireNotNullValueClass(valueClass);
        return apply(jsonEngine -> jsonEngine.readSet(json, valueClass));
    }

    // @NotNull
    public List<Map<String, Object>> readListOfMap(String json) {
        if (isNullOrEmpty(json))
            return Collections.emptyList();
        return apply(jsonEngine -> jsonEngine.readListOfMap(json));
    }

    // @NotNull
    public Map<String, Object> readMap(String json) {
        return readMap(json, String.class, Object.class);
    }

    // @NotNull
    public <V> Map<String, V> readMap(String json, Class<V> valueClass) {
        return readMap(json, String.class, valueClass);
    }

    // @NotNull
    public <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) {
        if (json == null)
            return Collections.emptyMap();

        requireNotNullKeyClass(keyClass);
        requireNotNullValueClass(valueClass);

        return apply(jsonEngine -> jsonEngine.readMap(json, keyClass, valueClass));
    }

    // ---------- read ByteBuffer----------

    public <V> V readValue(ByteBuffer buf, Class<V> valueClass) {
        if (buf == null)
            return null;

        requireNotCollection(valueClass);
        requireNotNullValueClass(valueClass);
        return readValue(utf8Reader(buf), valueClass);
    }

    // @NotNull
    public List<Object> readList(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptyList();
        return readList(utf8Reader(buf));
    }

    // @NotNull
    public <V> List<V> readList(ByteBuffer buf, Class<V> valueClass) {
        if (buf == null)
            return Collections.emptyList();

        requireNotNullValueClass(valueClass);
        return readList(utf8Reader(buf), valueClass);
    }

    // @NotNull
    public Set<Object> readSet(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptySet();
        return readSet(utf8Reader(buf));
    }

    // @NotNull
    public <V> Set<V> readSet(ByteBuffer buf, Class<V> valueClass) {
        if (buf == null)
            return Collections.emptySet();

        requireNotNullValueClass(valueClass);
        return readSet(utf8Reader(buf), valueClass);
    }

    public Iterator<Object> readListLazy(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptyIterator();
        return readListLazy(utf8Reader(buf));
    }

    public <V> Iterator<V> readListLazy(ByteBuffer buf, Class<V> valueClass) {
        if (buf == null)
            return Collections.emptyIterator();

        requireNotNullValueClass(valueClass);
        return readListLazy(utf8Reader(buf), valueClass);
    }

    // @NotNull
    public List<Map<String, Object>> readListOfMap(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptyList();
        return readListOfMap(utf8Reader(buf));
    }

    public Iterator<Map<String, Object>> readListOfMapLazy(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptyIterator();
        return readListOfMapLazy(utf8Reader(buf));
    }

    // @NotNull
    public Map<String, Object> readMap(ByteBuffer buf) {
        if (buf == null)
            return Collections.emptyMap();
        return readMap(utf8Reader(buf));
    }

    // @NotNull
    public <V> Map<String, V> readMap(ByteBuffer buf, Class<V> valueClass) {
        if (buf == null)
            return Collections.emptyMap();

        requireNotNullValueClass(valueClass);
        return readMap(utf8Reader(buf), valueClass);
    }

    // @NotNull
    public <K, V> Map<K, V> readMap(ByteBuffer buf, Class<K> keyClass, Class<V> valueClass) {
        if (buf == null)
            return Collections.emptyMap();

        requireNotNullKeyClass(keyClass);
        requireNotNullValueClass(valueClass);
        return readMap(utf8Reader(buf), keyClass, valueClass);
    }

    // ---------- read InputStream ----------

    public <V> V readValue(InputStream in, Class<V> valueClass) {
        if (in == null)
            return null;

        requireNotCollection(valueClass);
        requireNotNullValueClass(valueClass);
        return readValue(utf8Reader(in), valueClass);
    }

    // @NotNull
    public List<Object> readList(InputStream in) {
        return readList(in, Object.class);
    }

    // @NotNull
    public <V> List<V> readList(InputStream in, Class<V> valueClass) {
        if (in == null)
            return Collections.emptyList();

        requireNotNullValueClass(valueClass);
        return readList(utf8Reader(in), valueClass);
    }

    // @NotNull
    public Set<Object> readSet(InputStream in) {
        return readSet(in, Object.class);
    }

    // @NotNull
    public <V> Set<V> readSet(InputStream in, Class<V> valueClass) {
        if (in == null)
            return Collections.emptySet();

        requireNotNullValueClass(valueClass);
        return readSet(utf8Reader(in), valueClass);
    }

    // @NotNull
    public List<Map<String, Object>> readListOfMap(InputStream in) {
        if (in == null)
            return Collections.emptyList();
        return readListOfMap(utf8Reader(in));
    }

    public AutoCloseableIterator<Object> readListLazy(InputStream in) {
        return readListLazy(in, Object.class);
    }

    public <V> AutoCloseableIterator<V> readListLazy(InputStream in, Class<V> valueClass) {
        if (in == null)
            return null;

        requireNotNullValueClass(valueClass);
        return readListLazy(utf8Reader(in), valueClass);
    }

    public AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(InputStream in) {
        if (in == null)
            return null;
        return readListOfMapLazy(utf8Reader(in));
    }

    // @NotNull
    public Map<String, Object> readMap(InputStream in) {
        if (in == null)
            return Collections.emptyMap();
        return readMap(utf8Reader(in));
    }

    // @NotNull
    public <V> Map<String, V> readMap(InputStream in, Class<V> valueClass) {
        return readMap(in, String.class, valueClass);
    }

    // @NotNull
    public <K, V> Map<K, V> readMap(InputStream in, Class<K> keyClass, Class<V> valueClass) {
        if (in == null)
            return Collections.emptyMap();

        requireNotNullKeyClass(keyClass);
        requireNotNullValueClass(valueClass);

        return readMap(utf8Reader(in), keyClass, valueClass);
    }

    // ---------- read Reader ----------

    public <V> V readValue(Reader reader, Class<V> valueClass) {
        if (reader == null)
            return null;

        requireNotCollection(valueClass);
        requireNotNullValueClass(valueClass);
        return apply(reader, jsonEngine -> jsonEngine.readValue(reader, valueClass));
    }

    // @NotNull
    public List<Object> readList(Reader reader) {
        return readList(reader, Object.class);
    }

    // @NotNull
    public <V> List<V> readList(Reader reader, Class<V> valueClass) {
        if (reader == null)
            return Collections.emptyList();

        requireNotNullValueClass(valueClass);
        return apply(reader, jsonEngine -> jsonEngine.readList(reader, valueClass));
    }

    // @NotNull
    public Set<Object> readSet(Reader reader) {
        return readSet(reader, Object.class);
    }

    // @NotNull
    public <V> Set<V> readSet(Reader reader, Class<V> valueClass) {
        if (reader == null)
            return Collections.emptySet();

        requireNotNullValueClass(valueClass);
        return apply(reader, jsonEngine -> jsonEngine.readSet(reader, valueClass));
    }

    // @NotNull
    public List<Map<String, Object>> readListOfMap(Reader reader) {
        if (reader == null)
            return Collections.emptyList();
        return apply(reader, jsonEngine -> jsonEngine.readListOfMap(reader));
    }

    public AutoCloseableIterator<Object> readListLazy(Reader reader) {
        return readListLazy(reader, Object.class);
    }

    public <V> AutoCloseableIterator<V> readListLazy(Reader reader, Class<V> valueClass) {
        if (reader == null)
            return null;

        requireNotNullValueClass(valueClass);
        return apply(reader, jsonEngine -> jsonEngine.readListLazy(reader, valueClass));
    }

    public AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(Reader reader) {
        if (reader == null)
            return null;
        return apply(reader, jsonEngine -> jsonEngine.readListOfMapLazy(reader));
    }

    // @NotNull
    public Map<String, Object> readMap(Reader reader) {
        if (reader == null)
            return Collections.emptyMap();
        return readMap(reader, String.class, Object.class);
    }

    // @NotNull
    public <V> Map<String, V> readMap(Reader reader, Class<V> valueClass) {
        if (reader == null)
            return Collections.emptyMap();
        return readMap(reader, String.class, valueClass);
    }

    // @NotNull
    public <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) {
        if (reader == null)
            return Collections.emptyMap();

        requireNotNullKeyClass(keyClass);
        requireNotNullValueClass(valueClass);

        return apply(reader, jsonEngine -> jsonEngine.readMap(reader, keyClass, valueClass));
    }

    // ---------- convert ----------

    // @NotNull
    public <V> Map<String, Object> convertToMap(V obj) {
        if (obj == null)
            return Collections.emptyMap();
        return supplier.get().convertToMap(obj);
    }

    // ---------- misc ----------

    @SuppressWarnings("NewMethodNamingConvention")
    protected static Reader utf8Reader(InputStream in) {
        return new InputStreamReader(in, StandardCharsets.UTF_8) {
            @Override
            public boolean markSupported() {
                return in.markSupported();
            }

            @Override
            public void mark(int readAheadLimit) throws IOException {
                in.mark(readAheadLimit);
            }

            @Override
            public void reset() throws IOException {
                in.reset();
            }
        };
    }

    protected <V> V apply(ReadTask<V> task) {
        try {
            return task.read(supplier.get());
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    protected <V> V apply(Reader reader, ReadTask<V> task) {
        try {
            return task.read(supplier.get());
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    protected static <K> void requireNotNullKeyClass(Class<K> keyClass) {
        Objects.requireNonNull(keyClass, "'keyClass' should not be null");
    }

    protected static <V> void requireNotNullValueClass(Class<V> valueClass) {
        Objects.requireNonNull(valueClass, "'valueClass' should not be null");
    }

    protected static <V> void requireNotCollection(Class<V> valueClass) {
        if (Collection.class.isAssignableFrom(valueClass) || Map.class.isAssignableFrom(valueClass))
            throw new JsonException("'valueClass' should not be a collection");
    }

    @SuppressWarnings("NewMethodNamingConvention")
    protected static Reader utf8Reader(ByteBuffer buf) {
        return utf8Reader(new ByteBufferInputStream(buf));
    }

    @SuppressWarnings("PMD.AvoidReassigningParameters")
    protected static boolean isNullOrEmpty(String json) {
        if (json == null || json.isEmpty())
            return true;

        char expected = FIND_OPEN_BRACKET;

        for (int i = 0; i < json.length(); i++) {
            char ch = json.charAt(i);

            if (Character.isWhitespace(ch))
                continue;
            if (expected == FIND_CLOSE_BRACKET)
                return false;
            if (expected == FIND_OPEN_BRACKET) {
                if (ch == OPEN_CURLY_BRACKET)
                    expected = '}';
                else if (ch == OPEN_SQUARE_BRACKET)
                    expected = ']';
                else
                    return false;
            } else if (expected == ch)
                expected = FIND_CLOSE_BRACKET;
            else
                return false;
        }

        return expected == '>';
    }

    protected static boolean isBlank(String str) {
        if (str != null)
            for (int i = 0; i < str.length(); i++)
                if (!Character.isWhitespace(str.charAt(i)))
                    return false;

        return true;
    }

    public interface ReadTask<V> {

        V read(JsonEngine jsonEngine) throws Exception;

    }

}
