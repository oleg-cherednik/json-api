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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 19.11.2014
 */
@SuppressWarnings("PMD.ExcessivePublicCount")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Json {

    private static final JsonReader JSON_READER = new JsonReader(JsonHelper::getJsonEngine);
    private static final JsonWriter JSON_WRITER = new JsonWriter(JsonHelper::getJsonEngine);
    private static final JsonWriter PRETTY_PRINT_JSON_WRITER = new JsonWriter(JsonHelper::getPrettyPrintJsonEngine);

    // ***********************
    // ***   read String   ***
    // ***********************

    /**
     * Transform given {@code json} string into an elements represented by {@code valueClass}.
     *
     * @param json       source json string
     * @param valueClass class object of the required result value
     * @param <V>        type of the result value
     * @return Either instance of {@code valueClass} with data from the given {@code json} string or {@literal null}
     * in case of given {@code json} is blank
     * @throws JsonException        in case of given {@code valueClass} is {@link Collection} or {@link Map}
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    public static <V> V readValue(String json, Class<V> valueClass) {
        return reader().readValue(json, valueClass);
    }

    /**
     * Transform given {@code json} string into a {@link List} of {@link Object} elements.
     *
     * @param json source json string
     * @return not {@literal null} {@link List} of {@link Object} elements
     */
    // @NotNull
    public static List<Object> readList(String json) {
        return reader().readList(json);
    }

    /**
     * Transform give {@code json} string into a {@link List} of an elements represented by {@code valueClass}.
     *
     * @param json       source json string
     * @param valueClass class object of the required result value
     * @param <V>        type of the list's element of result value
     * @return not {@literal  null} {@link List} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> List<V> readList(String json, Class<V> valueClass) {
        return reader().readList(json, valueClass);
    }

    /**
     * Transform given {@code json} string into a {@link Set} of {@link Object} elements.
     *
     * @param json source json string
     * @return not {@literal null} {@link Set} of {@link Object} elements
     */
    // @NotNull
    public static Set<Object> readSet(String json) {
        return reader().readSet(json);
    }

    /**
     * Transform give {@code json} string into a {@link Set} of an elements represented by {@code valueClass}.
     *
     * @param json       source json string
     * @param valueClass class object of the required result value
     * @param <V>        type of the set's element of result value
     * @return not {@literal  null} {@link Set} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Set<V> readSet(String json, Class<V> valueClass) {
        return reader().readSet(json, valueClass);
    }

    /**
     * Transform given {@code json} string into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value.
     *
     * @param json source json string
     * @return not {@literal null} {@link List} of {@link Map}
     */
    // @NotNull
    public static List<Map<String, Object>> readListOfMap(String json) {
        return reader().readListOfMap(json);
    }

    /**
     * Transform given {@code json} string into a {@link Map} with {@link String} key and {@link Object} value.
     *
     * @param json source json string
     * @return not {@literal null} {@link Map} of {@link String} key and {@link Object} values
     */
    // @NotNull
    public static Map<String, Object> readMap(String json) {
        return reader().readMap(json);
    }

    /**
     * Transform given {@code json} string into a {@link Map} with {@link String} key and a value represented by
     * {@code valueClass}.
     *
     * @param json source json string
     * @return not {@literal null} {@link Map} of {@link String} key and {@code valueClass} value
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Map<String, V> readMap(String json, Class<V> valueClass) {
        return reader().readMap(json, valueClass);
    }

    /**
     * Transform given {@code json} string into a {@link Map} with a key represented by {@code keyClass} and
     * a value represented by {@code valueClass}.
     *
     * @param json source json string
     * @return not {@literal null} {@link Map} of {@code keyClass} key and {@code valueClass} values
     * @throws NullPointerException in case of given {@code keyClass} or {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(json, keyClass, valueClass);
    }

    // ***************************
    // ***   read ByteBuffer   ***
    // ***************************

    /**
     * Transform data from the given {@link ByteBuffer} into an elements represented by {@code valueClass}.
     *
     * @param buf        source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the result value
     * @return Either instance of {@code valueClass} with data from the given {@link ByteBuffer} or {@literal null}
     * in case of given {@code buf} is {@literal null}
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    public static <V> V readValue(ByteBuffer buf, Class<V> valueClass) {
        return reader().readValue(buf, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link List} of {@link Object} elements.
     *
     * @param buf source data
     * @return not {@literal null} {@link List} of {@link Object} elements
     */
    // @NotNull
    public static List<Object> readList(ByteBuffer buf) {
        return reader().readList(buf);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link List} of an elements represented by
     * {@code valueClass}.
     *
     * @param buf        source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the list's element of result value
     * @return not {@literal  null} {@link List} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> List<V> readList(ByteBuffer buf, Class<V> valueClass) {
        return reader().readList(buf, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Set} of {@link Object} elements.
     *
     * @param buf source data
     * @return not {@literal null} {@link Set} of {@link Object} elements
     */
    // @NotNull
    public static Set<Object> readSet(ByteBuffer buf) {
        return reader().readSet(buf);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Set} of an elements represented by
     * {@code valueClass}.
     *
     * @param buf        source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the set's element of result value
     * @return not {@literal  null} {@link Set} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Set<V> readSet(ByteBuffer buf, Class<V> valueClass) {
        return reader().readSet(buf, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value.
     *
     * @param buf source data
     * @return not {@literal null} {@link List} of {@link Map}
     */
    // @NotNull
    public static List<Map<String, Object>> readListOfMap(ByteBuffer buf) {
        return reader().readListOfMap(buf);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Map} with {@link String} key and {@link Object}
     * value.
     *
     * @param buf source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@link Object} values
     */
    // @NotNull
    public static Map<String, Object> readMap(ByteBuffer buf) {
        return reader().readMap(buf);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Map} with {@link String} key and a value
     * represented by {@code valueClass}.
     *
     * @param buf source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@code valueClass} value
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Map<String, V> readMap(ByteBuffer buf, Class<V> valueClass) {
        return reader().readMap(buf, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Map} with a key represented by {@code keyClass}
     * and a value represented by {@code valueClass}.
     *
     * @param buf source data
     * @return not {@literal null} {@link Map} of {@code keyClass} key and {@code valueClass} values
     * @throws NullPointerException in case of given {@code keyClass} or {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <K, V> Map<K, V> readMap(ByteBuffer buf, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(buf, keyClass, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Iterator} of {@link Object} elements with lazy
     * reading. I.e. in case of large {@code buf}, next portion of data will be read only when {@link Iterator#next()}
     * is invoked.
     *
     * @param buf source data
     * @return not {@literal null} {@link Iterator} of {@link Object} elements
     */
    // @NotNull
    public static Iterator<Object> readListLazy(ByteBuffer buf) {
        return reader().readListLazy(buf);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link Iterator} of an elements represented by
     * {@code valueClass} elements with lazy reading. I.e. in case of large {@code buf}, next portion of data will be
     * read only when {@link Iterator#next()} is invoked.
     *
     * @param buf source data
     * @return not {@literal null} {@link Iterator} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Iterator<V> readListLazy(ByteBuffer buf, Class<V> valueClass) {
        return reader().readListLazy(buf, valueClass);
    }

    /**
     * Transform data from the given {@link ByteBuffer} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value with lazy reading. I.e. in case of large {@code buf}, next portion of data will be read only
     * when {@link Iterator#next()} is invoked.
     *
     * @param buf source data
     * @return not {@literal null} {@link Iterator} of {@link List} of {@link Map}
     */
    // @NotNull
    public static Iterator<Map<String, Object>> readListOfMapLazy(ByteBuffer buf) {
        return reader().readListOfMapLazy(buf);
    }

    // ****************************
    // ***   read InputStream   ***
    // ****************************

    /**
     * Transform data from the given {@link InputStream} into an elements represented by {@code valueClass}.
     *
     * @param in         source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the result value
     * @return Either instance of {@code valueClass} with data from the given {@link InputStream} or {@literal null}
     * in case of given {@code buf} is {@literal null}
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    public static <V> V readValue(InputStream in, Class<V> valueClass) {
        return reader().readValue(in, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link List} of {@link Object} elements.
     *
     * @param in source data
     * @return not {@literal null} {@link List} of {@link Object} elements
     */
    // @NotNull
    public static List<Object> readList(InputStream in) {
        return reader().readList(in);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link List} of an elements represented by
     * {@code valueClass}.
     *
     * @param in         source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the list's element of result value
     * @return not {@literal  null} {@link List} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> List<V> readList(InputStream in, Class<V> valueClass) {
        return reader().readList(in, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Set} of {@link Object} elements.
     *
     * @param in source data
     * @return not {@literal null} {@link Set} of {@link Object} elements
     */
    // @NotNull
    public static Set<Object> readSet(InputStream in) {
        return reader().readSet(in);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Set} of an elements represented by
     * {@code valueClass}.
     *
     * @param in         source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the set's element of result value
     * @return not {@literal  null} {@link Set} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Set<V> readSet(InputStream in, Class<V> valueClass) {
        return reader().readSet(in, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value.
     *
     * @param in source data
     * @return not {@literal null} {@link List} of {@link Map}
     */
    // @NotNull
    public static List<Map<String, Object>> readListOfMap(InputStream in) {
        return reader().readListOfMap(in);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Map} with {@link String} key and {@link Object}
     * value.
     *
     * @param in source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@link Object} values
     */
    // @NotNull
    public static Map<String, Object> readMap(InputStream in) {
        return reader().readMap(in);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Map} with {@link String} key and a value
     * represented by {@code valueClass}.
     *
     * @param in source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@code valueClass} value
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Map<String, V> readMap(InputStream in, Class<V> valueClass) {
        return reader().readMap(in, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Map} with a key represented by {@code keyClass}
     * and a value represented by {@code valueClass}.
     *
     * @param in source data
     * @return not {@literal null} {@link Map} of {@code keyClass} key and {@code valueClass} values
     * @throws NullPointerException in case of given {@code keyClass} or {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <K, V> Map<K, V> readMap(InputStream in, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(in, keyClass, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Iterator} of {@link Object} elements with lazy
     * reading. I.e. in case of large {@code buf}, next portion of data will be read only when {@link Iterator#next()}
     * is invoked.
     *
     * @param in source data
     * @return not {@literal null} {@link Iterator} of {@link Object} elements
     */
    // @NotNull
    public static AutoCloseableIterator<Object> readListLazy(InputStream in) {
        return reader().readListLazy(in);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link Iterator} of an elements represented by
     * {@code valueClass} elements with lazy reading. I.e. in case of large {@code buf}, next portion of data will be
     * read only when {@link Iterator#next()} is invoked.
     *
     * @param in source data
     * @return not {@literal null} {@link Iterator} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> AutoCloseableIterator<V> readListLazy(InputStream in, Class<V> valueClass) {
        return reader().readListLazy(in, valueClass);
    }

    /**
     * Transform data from the given {@link InputStream} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value with lazy reading. I.e. in case of large {@code buf}, next portion of data will be read only
     * when {@link Iterator#next()} is invoked.
     *
     * @param in source data
     * @return not {@literal null} {@link Iterator} of {@link List} of {@link Map}
     */
    // @NotNull
    public static AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(InputStream in) {
        return reader().readListOfMapLazy(in);
    }

    // ***********************
    // ***   read Reader   ***
    // ***********************

    /**
     * Transform data from the given {@link Reader} into an elements represented by {@code valueClass}.
     *
     * @param reader     source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the result value
     * @return Either instance of {@code valueClass} with data from the given {@link Reader} or {@literal null}
     * in case of given {@code buf} is {@literal null}
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    public static <V> V readValue(Reader reader, Class<V> valueClass) {
        return reader().readValue(reader, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link List} of {@link Object} elements.
     *
     * @param reader source data
     * @return not {@literal null} {@link List} of {@link Object} elements
     */
    // @NotNull
    public static List<Object> readList(Reader reader) {
        return reader().readList(reader);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link List} of an elements represented by
     * {@code valueClass}.
     *
     * @param reader     source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the list's element of result value
     * @return not {@literal  null} {@link List} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> List<V> readList(Reader reader, Class<V> valueClass) {
        return reader().readList(reader, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Set} of {@link Object} elements.
     *
     * @param reader source data
     * @return not {@literal null} {@link Set} of {@link Object} elements
     */
    // @NotNull
    public static Set<Object> readSet(Reader reader) {
        return reader().readSet(reader);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Set} of an elements represented by
     * {@code valueClass}.
     *
     * @param reader     source data
     * @param valueClass class object of the required result value
     * @param <V>        type of the set's element of result value
     * @return not {@literal  null} {@link Set} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Set<V> readSet(Reader reader, Class<V> valueClass) {
        return reader().readSet(reader, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value.
     *
     * @param reader source data
     * @return not {@literal null} {@link List} of {@link Map}
     */
    // @NotNull
    public static List<Map<String, Object>> readListOfMap(Reader reader) {
        return reader().readListOfMap(reader);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Map} with {@link String} key and {@link Object}
     * value.
     *
     * @param reader source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@link Object} values
     */
    // @NotNull
    public static Map<String, Object> readMap(Reader reader) {
        return reader().readMap(reader);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Map} with {@link String} key and a value
     * represented by {@code valueClass}.
     *
     * @param reader source data
     * @return not {@literal null} {@link Map} of {@link String} key and {@code valueClass} value
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> Map<String, V> readMap(Reader reader, Class<V> valueClass) {
        return reader().readMap(reader, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Map} with a key represented by {@code keyClass}
     * and a value represented by {@code valueClass}.
     *
     * @param reader source data
     * @return not {@literal null} {@link Map} of {@code keyClass} key and {@code valueClass} values
     * @throws NullPointerException in case of given {@code keyClass} or {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(reader, keyClass, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Iterator} of {@link Object} elements with lazy
     * reading. I.e. in case of large {@code buf}, next portion of data will be read only when {@link Iterator#next()}
     * is invoked.
     *
     * @param reader source data
     * @return not {@literal null} {@link Iterator} of {@link Object} elements
     */
    // @NotNull
    public static AutoCloseableIterator<Object> readListLazy(Reader reader) {
        return reader().readListLazy(reader);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link Iterator} of an elements represented by
     * {@code valueClass} elements with lazy reading. I.e. in case of large {@code buf}, next portion of data will be
     * read only when {@link Iterator#next()} is invoked.
     *
     * @param reader source data
     * @return not {@literal null} {@link Iterator} of {@code valueClass} elements
     * @throws NullPointerException in case of given {@code valueClass} is {@literal null}
     */
    // @NotNull
    public static <V> AutoCloseableIterator<V> readListLazy(Reader reader, Class<V> valueClass) {
        return reader().readListLazy(reader, valueClass);
    }

    /**
     * Transform data from the given {@link Reader} into a {@link List} of {@link Map} with {@link String} key and
     * {@link Object} value with lazy reading. I.e. in case of large {@code buf}, next portion of data will be read only
     * when {@link Iterator#next()} is invoked.
     *
     * @param reader source data
     * @return not {@literal null} {@link Iterator} of {@link List} of {@link Map}
     */
    // @NotNull
    public static AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(Reader reader) {
        return reader().readListOfMapLazy(reader);
    }

    // *****************
    // ***   write   ***
    // *****************

    /**
     * Convert given {@code obj} to the json string.
     *
     * @param obj any single {@link Object}, including {@link Collection} and {@link Map} that should be converted to
     *            json string
     * @param <V> type of the obj
     * @return {@link String} json sting or {@literal null} in case of given {@code obj} is {@literal null}
     */
    public static <V> String writeValue(V obj) {
        return writer().writeValue(obj);
    }

    /**
     * Convert given {@code obj} to the json string and write it to the given {@link OutputStream}. Nothing will be
     * sent to the {@code out} in case of {@code obj} is {@literal null}. After all, given {@link OutputStream} will
     * not be closed (the client should do it explicitly).
     *
     * @param obj any single {@link Object}, including {@link Collection} and {@link Map}
     * @param out destination {@literal OutputStream}; will not be closed after all
     * @param <V> type of the obj
     */
    public static <V> void writeValue(V obj, OutputStream out) {
        writer().writeValue(obj, out);
    }

    /**
     * Convert given {@code obj} to the json string and write it to the given {@link Writer}. Nothing will be
     * sent to the {@code writer} in case of {@code obj} is {@literal null}. After all, given {@link Writer} will
     * not be closed (the client should do it explicitly).
     *
     * @param obj    any single {@link Object}, including {@link Collection} and {@link Map}
     * @param writer destination {@literal Writer}; will not be closed after all
     * @param <V>    type of the obj
     */
    public static <V> void writeValue(V obj, Writer writer) {
        writer().writeValue(obj, writer);
    }

    // *****************
    // ***   print   ***
    // *****************

    /**
     * Retrieves default {@link JsonReader} instance (instance with current default settings), that is used by all
     * {@link Json} class.
     *
     * @return not {@literal null} default instance of {@link JsonReader}
     */
    // @NotNull
    public static JsonReader reader() {
        return JSON_READER;
    }

    /**
     * Retrieves default {@link JsonWriter} instance (instance with current default settings), that is used by all
     * {@link Json} class.
     *
     * @return not {@literal null} default instance of {@link JsonWriter}
     */
    // @NotNull
    public static JsonWriter writer() {
        return JSON_WRITER;
    }

    /**
     * Retrieves default {@link JsonWriter} instance (instance with current default settings) with enabled
     * <tt>pretty-print</tt> option. This is the only one difference between this {@link JsonWriter} and instance, that
     * is retrieved by {@link Json#writer()}.
     *
     * @return not {@literal null} default instance of {@link JsonWriter} with enabled <tt>pretty-print</tt> option
     */
    // @NotNull
    public static JsonWriter prettyPrint() {
        return PRETTY_PRINT_JSON_WRITER;
    }

    // **********************
    // ***   decorators   ***
    // **********************

    public static JsonReader createReader() {
        return createReader(null);
    }

    public static JsonWriter createWriter() {
        return createWriter(null);
    }

    public static JsonWriter createPrettyPrint() {
        return new JsonWriter(JsonHelper::getPrettyPrintJsonEngine);
    }

    // ********************
    // ***   settings   ***
    // ********************

    public static JsonReader createReader(JsonSettings settings) {
        JsonEngine engine = JsonHelper.createJsonEngine(settings);
        return new JsonReader(() -> engine);
    }

    public static JsonWriter createWriter(JsonSettings settings) {
        JsonEngine engine = JsonHelper.createJsonEngine(settings);
        return new JsonWriter(() -> engine);
    }

    public static JsonWriter createPrettyPrint(JsonSettings settings) {
        JsonEngine engine = JsonHelper.createPrettyPrintJsonEngine(settings);
        return new JsonWriter(() -> engine);
    }

    // *******************
    // ***   convert   ***
    // *******************

    public static <V> Map<String, Object> convertToMap(V obj) {
        return reader().convertToMap(obj);
    }

}
