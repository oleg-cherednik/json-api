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

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import ru.olegcherednik.json.api.data.Data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 03.01.2021
 */
@Test
public class InputStreamTest {

    public void shouldRetrieveNullWhenObjectNull() {
        assertThat(Json.readValue((InputStream) null, Object.class)).isNull();
        assertThat(Json.readListLazy((InputStream) null)).isNull();
        assertThat(Json.readListLazy((InputStream) null, Object.class)).isNull();
        assertThat(Json.readListOfMapLazy((InputStream) null)).isNull();
    }

    public void shouldRetrieveEmptyCollectionWhenObjectNull() {
        assertThat(Json.readList((InputStream) null)).isEmpty();
        assertThat(Json.readList((InputStream) null, Object.class)).isEmpty();
        assertThat(Json.readSet((InputStream) null)).isEmpty();
        assertThat(Json.readSet((InputStream) null, Object.class)).isEmpty();
        assertThat(Json.readListOfMap((InputStream) null)).isEmpty();
        assertThat(Json.readMap((InputStream) null)).isEmpty();
        assertThat(Json.readMap((InputStream) null, String.class)).isEmpty();
        assertThat(Json.readMap((InputStream) null, String.class, String.class)).isEmpty();
    }

    public void shouldRetrieveDeserializedObjectWhenReadValue() throws IOException {
        Data actual = Json.readValue(ResourceData.getResourceAsInputStream("/data.json"), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Data.TOM_CRUISE);
    }

    public void shouldRetrieveCorrectNumericWhenObjectContainsDifferentNumericList() throws IOException {
        List<Object> actual = Json.readList(convertToInputStream("[\"one\", \"two\", \"three\"]"));
        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveDeserializedListWhenReadAsList() throws IOException {
        List<Data> actual = Json.readList(ResourceData.inputStreamDataList(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveUniqueValuesWhenReadListNoUniqueValueAsSet() throws IOException {
        Set<Object> actual = Json.readSet(convertToInputStream("[\"one\",\"two\",\"three\",\"two\",\"one\"]"));

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveUniqueDataWhenReadListNoUniqueDataAsSet() throws IOException {
        Set<Data> actual = Json.readSet(ResourceData.inputStreamDataSet(), Data.class);

        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveListOfMapWhenRead() throws IOException {
        List<Map<String, Object>> actual = Json.readListOfMap(ResourceData.inputStreamDataList());

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).hasSize(2);
        assertThat(actual.get(0)).containsEntry("firstName", "Tom");
        assertThat(actual.get(0)).containsEntry("lastName", "Cruise");
        assertThat(actual.get(1)).hasSize(2);
        assertThat(actual.get(1)).containsEntry("firstName", "Nicole");
        assertThat(actual.get(1)).containsEntry("lastName", "Kidman");
    }

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadAsLazyList() throws Exception {
        Map<String, Object> expected1 = MapUtils.of("firstName", "Tom",
                                                    "lastName", "Cruise");
        Map<String, Object> expected2 = MapUtils.of("firstName", "Nicole",
                                                    "lastName", "Kidman");

        try (AutoCloseableIterator<Object> it = Json.readListLazy(ResourceData.inputStreamDataList())) {
            assertThat(it.hasNext()).isTrue();

            Object actual1 = it.next();
            assertThat(actual1).isNotNull();
            assertThat(actual1).isEqualTo(expected1);
            assertThat(it.hasNext()).isTrue();

            Object actual2 = it.next();
            assertThat(actual2).isNotNull();
            assertThat(actual2).isEqualTo(expected2);
            assertThat(it.hasNext()).isFalse();
        }
    }

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadValueLazyList() throws Exception {
        try (AutoCloseableIterator<Data> it = Json.readListLazy(ResourceData.inputStreamDataList(), Data.class)) {
            assertThat(it.hasNext()).isTrue();

            Data actual1 = it.next();
            assertThat(actual1).isNotNull();
            assertThat(actual1).isEqualTo(Data.TOM_CRUISE);
            assertThat(it.hasNext()).isTrue();

            Data actual2 = it.next();
            assertThat(actual2).isNotNull();
            assertThat(actual2).isEqualTo(Data.NICOLE_KIDMAN);
            assertThat(it.hasNext()).isFalse();
        }
    }

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadByteBufferAsListOfMapLazy() throws Exception {
        Map<String, Object> tomCruise = MapUtils.of("firstName", "Tom",
                                                    "lastName", "Cruise");
        Map<String, Object> nicoleKidman = MapUtils.of("firstName", "Nicole",
                                                       "lastName", "Kidman");

        try (AutoCloseableIterator<Map<String, Object>> it =
                     Json.readListOfMapLazy(ResourceData.inputStreamDataList())) {
            assertThat(it.hasNext()).isTrue();

            Object actual1 = it.next();
            assertThat(actual1).isNotNull();
            assertThat(actual1).isEqualTo(tomCruise);
            assertThat(it.hasNext()).isTrue();

            Object actual2 = it.next();
            assertThat(actual2).isNotNull();
            assertThat(actual2).isEqualTo(nicoleKidman);
            assertThat(it.hasNext()).isFalse();
        }
    }

    public void shouldRetrieveDataMapWhenReadAsMapWithStringKey() throws IOException {
        Map<String, Object> tomCruise = MapUtils.of("firstName", "Tom",
                                                    "lastName", "Cruise");
        Map<String, Object> nicoleKidman = MapUtils.of("firstName", "Nicole",
                                                       "lastName", "Kidman");

        Map<String, Object> actual = Json.readMap(ResourceData.inputStreamDataMap());
        assertThat(actual).isNotNull();
        assertThat(actual.keySet()).containsExactly("man", "woman");
        assertThat(actual).containsEntry("man", tomCruise);
        assertThat(actual).containsEntry("woman", nicoleKidman);
    }

    public void shouldRetrieveDeserializedMapWhenReadAsMapListWithStringKeyAndDataType() throws IOException {
        Map<String, Data> expected = MapUtils.of("man", Data.TOM_CRUISE,
                                                 "woman", Data.NICOLE_KIDMAN);

        Map<String, Data> actual = Json.readMap(ResourceData.inputStreamDataMap(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldRetrieveIntegerValueMapWhenReadAsMapWithIntKeyAndBookType() throws IOException {
        Map<Character, Data> expected = MapUtils.of('M', Data.TOM_CRUISE,
                                                    'W', Data.NICOLE_KIDMAN);

        Map<Character, Data> actual = Json.readMap(ResourceData.getResourceAsInputStream("/data_map_char.json"),
                                                   Character.class, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    private static InputStream convertToInputStream(String str) {
        return IOUtils.toInputStream(str, StandardCharsets.UTF_8);
    }

}


