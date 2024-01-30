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
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Oleg Cherednik
 * @since 04.11.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceData {

    private static final String DATA_LIST_JSON = "/data_list.json";
    private static final String DATA_MAP_JSON = "/data_map.json";
    private static final String DATA_SET_JSON = "/data_set.json";

    // ---------- String ----------

    public static String stringDataList() throws IOException {
        return getResourceAsString(DATA_LIST_JSON);
    }

    public static String stringDataMap() throws IOException {
        return getResourceAsString(DATA_MAP_JSON);
    }

    public static String getResourceAsString(String name) throws IOException {
        try (InputStream in = getResourceAsInputStream(name)) {
            return IOUtils.toString(Objects.requireNonNull(in), StandardCharsets.UTF_8);
        }
    }

    // ---------- InputStream ----------

    public static InputStream inputStreamDataList() throws IOException {
        return getResourceAsInputStream(DATA_LIST_JSON);
    }

    public static InputStream inputStreamDataSet() throws IOException {
        return getResourceAsInputStream(DATA_SET_JSON);
    }

    public static InputStream inputStreamDataMap() throws IOException {
        return getResourceAsInputStream(DATA_MAP_JSON);
    }

    public static InputStream getResourceAsInputStream(String name) throws IOException {
        return ResourceData.class.getResourceAsStream(name);
    }

    // ---------- Reader ----------

    public static Reader readerDataList() throws IOException {
        return getResourceAsReader(DATA_LIST_JSON);
    }

    public static Reader readerDataSet() throws IOException {
        return getResourceAsReader(DATA_SET_JSON);
    }

    public static Reader readerDataMap() throws IOException {
        return getResourceAsReader(DATA_MAP_JSON);
    }

    public static Reader getResourceAsReader(String name) throws IOException {
        InputStream in = Objects.requireNonNull(ReaderTest.class.getResourceAsStream(name));
        return new InputStreamReader(in);
    }

    // ---------- ByteBuffer ----------

    public static ByteBuffer byteBufferDataList() throws IOException {
        return getResourceAsByteBuffer(DATA_LIST_JSON);
    }

    public static ByteBuffer byteBufferDataSet() throws IOException {
        return getResourceAsByteBuffer(DATA_SET_JSON);
    }

    public static ByteBuffer byteBufferDataMap() throws IOException {
        return getResourceAsByteBuffer(DATA_MAP_JSON);
    }

    public static ByteBuffer getResourceAsByteBuffer(String name) throws IOException {
        try (InputStream in = Objects.requireNonNull(ByteBufferTest.class.getResourceAsStream(name))) {
            return ByteBuffer.wrap(IOUtils.toByteArray(in));
        }
    }


}
