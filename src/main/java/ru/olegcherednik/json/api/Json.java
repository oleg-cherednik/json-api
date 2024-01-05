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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
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

    // ---------- read String ----------

    public static <V> V readValue(String json, Class<V> valueClass) {
        return reader().readValue(json, valueClass);
    }

    public static List<Object> readList(String json) {
        return reader().readList(json);
    }

    public static <V> List<V> readList(String json, Class<V> valueClass) {
        return reader().readList(json, valueClass);
    }

    public static Set<Object> readSet(String json) {
        return reader().readSet(json);
    }

    public static <V> Set<V> readSet(String json, Class<V> valueClass) {
        return reader().readSet(json, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(String json) {
        return reader().readListOfMap(json);
    }

    public static Map<String, Object> readMap(String json) {
        return reader().readMap(json);
    }

    public static <V> Map<String, V> readMap(String json, Class<V> valueClass) {
        return reader().readMap(json, valueClass);
    }

    public static <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(json, keyClass, valueClass);
    }

    // ---------- read ByteBuffer ----------

    public static <V> V readValue(ByteBuffer buf, Class<V> valueClass) {
        return reader().readValue(buf, valueClass);
    }

    public static List<Object> readList(ByteBuffer buf) {
        return reader().readList(buf);
    }

    public static <V> List<V> readList(ByteBuffer buf, Class<V> valueClass) {
        return reader().readList(buf, valueClass);
    }

    public static Set<Object> readSet(ByteBuffer buf) {
        return reader().readSet(buf);
    }

    public static <V> Set<V> readSet(ByteBuffer buf, Class<V> valueClass) {
        return reader().readSet(buf, valueClass);
    }

    public static Iterator<Object> readListLazy(ByteBuffer buf) {
        return reader().readListLazy(buf);
    }

    public static <V> Iterator<V> readListLazy(ByteBuffer buf, Class<V> valueClass) {
        return reader().readListLazy(buf, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(ByteBuffer buf) {
        return reader().readListOfMap(buf);
    }

    public static Iterator<Map<String, Object>> readListOfMapLazy(ByteBuffer buf) {
        return reader().readListOfMapLazy(buf);
    }

    public static Map<String, Object> readMap(ByteBuffer buf) {
        return reader().readMap(buf);
    }

    public static <V> Map<String, V> readMap(ByteBuffer buf, Class<V> valueClass) {
        return reader().readMap(buf, valueClass);
    }

    public static <K, V> Map<K, V> readMap(ByteBuffer buf, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(buf, keyClass, valueClass);
    }

    // ---------- read InputStream ----------

    public static <V> V readValue(InputStream in, Class<V> valueClass) {
        return reader().readValue(in, valueClass);
    }

    public static List<Object> readList(InputStream in) {
        return reader().readList(in);
    }

    public static <V> List<V> readList(InputStream in, Class<V> valueClass) {
        return reader().readList(in, valueClass);
    }

    public static Set<Object> readSet(InputStream in) {
        return reader().readSet(in);
    }

    public static <V> Set<V> readSet(InputStream in, Class<V> valueClass) {
        return reader().readSet(in, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(InputStream in) {
        return reader().readListOfMap(in);
    }

    public static AutoCloseableIterator<Object> readListLazy(InputStream in) {
        return reader().readListLazy(in);
    }

    public static <V> AutoCloseableIterator<V> readListLazy(InputStream in, Class<V> valueClass) {
        return reader().readListLazy(in, valueClass);
    }

    public static AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(InputStream in) {
        return reader().readListOfMapLazy(in);
    }

    public static Map<String, Object> readMap(InputStream in) {
        return reader().readMap(in);
    }

    public static <V> Map<String, V> readMap(InputStream in, Class<V> valueClass) {
        return reader().readMap(in, valueClass);
    }

    public static <K, V> Map<K, V> readMap(InputStream in, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(in, keyClass, valueClass);
    }

    // ---------- read Reader ----------

    public static <V> V readValue(Reader reader, Class<V> valueClass) {
        return reader().readValue(reader, valueClass);
    }

    public static List<Object> readList(Reader reader) {
        return reader().readList(reader);
    }

    public static <V> List<V> readList(Reader reader, Class<V> valueClass) {
        return reader().readList(reader, valueClass);
    }

    public static Set<Object> readSet(Reader reader) {
        return reader().readSet(reader);
    }

    public static <V> Set<V> readSet(Reader reader, Class<V> valueClass) {
        return reader().readSet(reader, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(Reader reader) {
        return reader().readListOfMap(reader);
    }

    public static AutoCloseableIterator<Object> readListLazy(Reader reader) {
        return reader().readListLazy(reader);
    }

    public static <V> AutoCloseableIterator<V> readListLazy(Reader reader, Class<V> valueClass) {
        return reader().readListLazy(reader, valueClass);
    }

    public static AutoCloseableIterator<Map<String, Object>> readListOfMapLazy(Reader reader) {
        return reader().readListOfMapLazy(reader);
    }

    public static Map<String, Object> readMap(Reader reader) {
        return reader().readMap(reader);
    }

    public static <V> Map<String, V> readMap(Reader reader, Class<V> valueClass) {
        return reader().readMap(reader, valueClass);
    }

    public static <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) {
        return reader().readMap(reader, keyClass, valueClass);
    }

    // ---------- write ----------

    public static <V> String writeValue(V obj) {
        return writer().writeValue(obj);
    }

    public static <V> void writeValue(V obj, OutputStream out) {
        writer().writeValue(obj, out);
    }

    public static <V> void writeValue(V obj, Writer writer) {
        writer().writeValue(obj, writer);
    }

    // ---------- print ----------

    public static JsonReader reader() {
        return JSON_READER;
    }

    public static JsonWriter writer() {
        return JSON_WRITER;
    }

    public static JsonWriter prettyPrint() {
        return PRETTY_PRINT_JSON_WRITER;
    }

    // ---------- decorators ----------

    public static JsonReader createReader() {
        return createReader(null);
    }

    public static JsonWriter createWriter() {
        return createWriter(null);
    }

    public static JsonWriter createPrettyPrint() {
        return new JsonWriter(JsonHelper::getPrettyPrintJsonEngine);
    }

    // ---------- settings ----------

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

    // ---------- convert ----------

    public static <V> Map<String, Object> convertToMap(V obj) {
        return reader().convertToMap(obj);
    }

}
