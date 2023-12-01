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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Json {

    private static final JsonReadDecorator READ_DELEGATE;
    private static final JsonWriteDecorator WRITE_DELEGATE;
    private static final JsonWriteDecorator PRETTY_PRINT_WRITE_DELEGATE;

    static {
        READ_DELEGATE = new JsonReadDecorator(JsonHelper::getJsonEngine);
        WRITE_DELEGATE = new JsonWriteDecorator(JsonHelper::getJsonEngine);
        PRETTY_PRINT_WRITE_DELEGATE = new JsonWriteDecorator(JsonHelper::getPrettyPrintJsonEngine);
    }

    // ---------- read String ----------

    public static <V> V readValue(String json, Class<V> valueClass) {
        return read().readValue(json, valueClass);
    }

    public static List<Object> readList(String json) {
        return read().readList(json);
    }

    public static <V> List<V> readList(String json, Class<V> valueClass) {
        return read().readList(json, valueClass);
    }

    public static Set<Object> readSet(String json) {
        return read().readSet(json);
    }

    public static <V> Set<V> readSet(String json, Class<V> valueClass) {
        return read().readSet(json, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(String json) {
        return read().readListOfMap(json);
    }

    public static Map<String, Object> readMap(String json) {
        return read().readMap(json);
    }

    public static <V> Map<String, V> readMap(String json, Class<V> valueClass) {
        return read().readMap(json, valueClass);
    }

    public static <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return read().readMap(json, keyClass, valueClass);
    }

    // ---------- read ByteBuffer ----------

    public static <V> V readValue(ByteBuffer buf, Class<V> valueClass) {
        return read().readValue(buf, valueClass);
    }

    public static List<Object> readList(ByteBuffer buf) {
        return read().readList(buf);
    }

    public static <V> List<V> readList(ByteBuffer buf, Class<V> valueClass) {
        return read().readList(buf, valueClass);
    }

    public static Set<Object> readSet(ByteBuffer buf) {
        return read().readSet(buf);
    }

    public static <V> Set<V> readSet(ByteBuffer buf, Class<V> valueClass) {
        return read().readSet(buf, valueClass);
    }

    public static Iterator<Object> readListLazy(ByteBuffer buf) {
        return read().readListLazy(buf);
    }

    public static <V> Iterator<V> readListLazy(ByteBuffer buf, Class<V> valueClass) {
        return read().readListLazy(buf, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(ByteBuffer buf) {
        return read().readListOfMap(buf);
    }

    public static Iterator<Map<String, Object>> readListOfMapLazy(ByteBuffer buf) {
        return read().readListOfMapLazy(buf);
    }

    public static Map<String, Object> readMap(ByteBuffer buf) {
        return read().readMap(buf);
    }

    public static <V> Map<String, V> readMap(ByteBuffer buf, Class<V> valueClass) {
        return read().readMap(buf, valueClass);
    }

    public static <K, V> Map<K, V> readMap(ByteBuffer buf, Class<K> keyClass, Class<V> valueClass) {
        return read().readMap(buf, keyClass, valueClass);
    }

    // ---------- read InputStream ----------

    public static <V> V readValue(InputStream in, Class<V> valueClass) {
        return read().readValue(in, valueClass);
    }

    public static List<Object> readList(InputStream in) {
        return read().readList(in);
    }

    public static <V> List<V> readList(InputStream in, Class<V> valueClass) {
        return read().readList(in, valueClass);
    }

    public static Set<Object> readSet(InputStream in) {
        return read().readSet(in);
    }

    public static <V> Set<V> readSet(InputStream in, Class<V> valueClass) {
        return read().readSet(in, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(InputStream in) {
        return read().readListOfMap(in);
    }

    public static Iterator<Object> readListLazy(InputStream in) {
        return read().readListLazy(in);
    }

    public static <V> Iterator<V> readListLazy(InputStream in, Class<V> valueClass) {
        return read().readListLazy(in, valueClass);
    }

    public static Iterator<Map<String, Object>> readListOfMapLazy(InputStream in) {
        return read().readListOfMapLazy(in);
    }

    public static Map<String, Object> readMap(InputStream in) {
        return read().readMap(in);
    }

    public static <V> Map<String, V> readMap(InputStream in, Class<V> valueClass) {
        return read().readMap(in, valueClass);
    }

    public static <K, V> Map<K, V> readMap(InputStream in, Class<K> keyClass, Class<V> valueClass) {
        return read().readMap(in, keyClass, valueClass);
    }

    // ---------- read Reader ----------

    public static <V> V readValue(Reader reader, Class<V> valueClass) {
        return read().readValue(reader, valueClass);
    }

    public static List<Object> readList(Reader reader) {
        return read().readList(reader);
    }

    public static <V> List<V> readList(Reader reader, Class<V> valueClass) {
        return read().readList(reader, valueClass);
    }

    public static Set<Object> readSet(Reader reader) {
        return read().readSet(reader);
    }

    public static <V> Set<V> readSet(Reader reader, Class<V> valueClass) {
        return read().readSet(reader, valueClass);
    }

    public static List<Map<String, Object>> readListOfMap(Reader reader) {
        return read().readListOfMap(reader);
    }

    public static Iterator<Object> readListLazy(Reader reader) {
        return read().readListLazy(reader);
    }

    public static <V> Iterator<V> readListLazy(Reader reader, Class<V> valueClass) {
        return read().readListLazy(reader, valueClass);
    }

    public static Iterator<Map<String, Object>> readListOfMapLazy(Reader reader) {
        return read().readListOfMapLazy(reader);
    }

    public static Map<String, Object> readMap(Reader reader) {
        return read().readMap(reader);
    }

    public static <V> Map<String, V> readMap(Reader reader, Class<V> valueClass) {
        return read().readMap(reader, valueClass);
    }

    public static <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) {
        return read().readMap(reader, keyClass, valueClass);
    }

    // ---------- write ----------

    public static <V> String writeValue(V obj) {
        return print().writeValue(obj);
    }

    public static <V> void writeValue(V obj, OutputStream out) {
        print().writeValue(obj, out);
    }

    public static <V> void writeValue(V obj, Writer writer) {
        print().writeValue(obj, writer);
    }

    // ---------- print ----------

    public static JsonReadDecorator read() {
        return READ_DELEGATE;
    }

    public static JsonWriteDecorator print() {
        return WRITE_DELEGATE;
    }

    public static JsonWriteDecorator prettyPrint() {
        return PRETTY_PRINT_WRITE_DELEGATE;
    }

    // ---------- convert ----------

    public static <V> Map<String, Object> convertToMap(V obj) {
        return read().convertToMap(obj);
    }

}
