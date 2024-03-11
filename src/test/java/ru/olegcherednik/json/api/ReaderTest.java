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

import org.testng.annotations.Test;
import ru.olegcherednik.json.api.data.Data;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 19.11.2023
 */
@Test
public class ReaderTest {

    public void shouldRetrieveNullWhenObjectNull() {
        assertThat(Json.readValue((Reader) null, Object.class)).isNull();
    }

    public void shouldRetrieveEmptyCollectionWhenObjectNull() {
        assertThat(Json.readList((Reader) null)).isEmpty();
        assertThat(Json.readList((Reader) null, Object.class)).isEmpty();
        assertThat(Json.readSet((Reader) null)).isEmpty();
        assertThat(Json.readSet((Reader) null, Object.class)).isEmpty();
        assertThat(Json.readListOfMap((Reader) null)).isEmpty();
        assertThat(Json.readMap((Reader) null)).isEmpty();
        assertThat(Json.readMap((Reader) null, String.class)).isEmpty();
        assertThat(Json.readMap((Reader) null, String.class, String.class)).isEmpty();
    }

    public void shouldRetrieveEmptyIteratorWhenObjectNull() {
        assertThat(Json.readListLazy((Reader) null)).isSameAs(EmptyAutoCloseableIterator.getInstance());
        assertThat(Json.readListLazy((Reader) null, Object.class)).isSameAs(EmptyAutoCloseableIterator.getInstance());
        assertThat(Json.readListOfMapLazy((Reader) null)).isSameAs(EmptyAutoCloseableIterator.getInstance());
    }

    public void shouldRetrieveDeserializedObjectWhenReadValue() throws IOException {
        Data actual = Json.readValue(ResourceData.getResourceAsReader("/data.json"), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Data.TOM_CRUISE);
    }

    public void shouldRetrieveDeserializedObjectWhenReadValueWithNewJsonReader() throws IOException {
        Data actual = Json.createReader(JsonSettings.DEFAULT)
                          .readValue(ResourceData.getResourceAsReader("/data.json"), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Data.TOM_CRUISE);
    }

    public void shouldRetrieveCorrectNumericWhenObjectContainsDifferentNumericList() throws IOException {
        List<Object> actual = Json.readList(convertToReader("[\"one\", \"two\", \"three\"]"));
        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveDeserializedListWhenReadAsList() throws IOException {
        List<Data> actual = Json.readList(ResourceData.readerDataList(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveUniqueValuesWhenReadListNoUniqueValueAsSet() throws IOException {
        Set<Object> actual = Json.readSet(convertToReader("[\"one\",\"two\",\"three\",\"two\",\"one\"]"));

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveUniqueDataWhenReadListNoUniqueDataAsSet() throws IOException {
        Set<Data> actual = Json.readSet(ResourceData.readerDataSet(), Data.class);

        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveDataMapWhenReadAsMapWithStringKey() throws IOException {
        Map<String, Object> tomCruise = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> nicoleKidman = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                       Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        Map<String, Object> actual = Json.readMap(ResourceData.readerDataMap());
        assertThat(actual).isNotNull();
        assertThat(actual.keySet()).containsExactly("man", "woman");
        assertThat(actual).containsEntry("man", tomCruise);
        assertThat(actual).containsEntry("woman", nicoleKidman);
    }

    public void shouldRetrieveDeserializedMapWhenReadAsMapListWithStringKeyAndDataType() throws IOException {
        Map<String, Data> expected = MapUtils.of("man", Data.TOM_CRUISE,
                                                 "woman", Data.NICOLE_KIDMAN);

        Map<String, Data> actual = Json.readMap(ResourceData.readerDataMap(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldRetrieveIntegerValueMapWhenReadAsMapWithIntKeyAndBookType() throws IOException {
        Map<Character, Data> expected = MapUtils.of('M', Data.TOM_CRUISE,
                                                    'W', Data.NICOLE_KIDMAN);

        Map<Character, Data> actual = Json.readMap(ResourceData.getResourceAsReader("/data_map_char.json"),
                                                   Character.class, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldRetrieveListOfMapWhenRead() throws IOException {
        List<Map<String, Object>> actual = Json.readListOfMap(ResourceData.readerDataList());

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).hasSize(2);
        assertThat(actual.get(0)).containsEntry(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName());
        assertThat(actual.get(0)).containsEntry(Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        assertThat(actual.get(1)).hasSize(2);
        assertThat(actual.get(1)).containsEntry(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName());
        assertThat(actual.get(1)).containsEntry(Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());
    }

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadAsLazyList() throws Exception {
        Map<String, Object> expected1 = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> expected2 = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                    Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        try (AutoCloseableIterator<Object> it = Json.readListLazy(ResourceData.readerDataList())) {
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
        try (AutoCloseableIterator<Data> it = Json.readListLazy(ResourceData.readerDataList(), Data.class)) {
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
        Map<String, Object> tomCruise = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> nicoleKidman = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                       Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        try (AutoCloseableIterator<Map<String, Object>> it = Json.readListOfMapLazy(ResourceData.readerDataList())) {
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

    public void shouldThrowJsonExceptionWhenReadIncorrectByteBuffer() {
        assertThatThrownBy(() -> Json.readValue(convertToReader("incorrect"), Data.class))
                .isExactlyInstanceOf(JsonException.class);
    }

    private static Reader convertToReader(String str) {
        return new StringReader(str);
    }

}


