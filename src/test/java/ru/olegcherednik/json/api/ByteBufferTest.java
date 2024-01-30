/*
 * Copyright of Oleg Cherednik
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
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 19.02.2022
 */
@Test
public class ByteBufferTest {

    public void shouldRetrieveNullWhenObjectNull() {
        assertThat(Json.readValue((ByteBuffer) null, Object.class)).isNull();
    }

    public void shouldRetrieveEmptyIteratorWhenObjectNull() {
        assertThat(Json.readListLazy((ByteBuffer) null)).isSameAs(Collections.emptyIterator());
        assertThat(Json.readListLazy((ByteBuffer) null, Object.class)).isSameAs(Collections.emptyIterator());
        assertThat(Json.readListOfMapLazy((ByteBuffer) null)).isSameAs(Collections.emptyIterator());
    }

    public void shouldRetrieveEmptyCollectionWhenObjectNull() {
        assertThat(Json.readList((ByteBuffer) null)).isEmpty();
        assertThat(Json.readList((ByteBuffer) null, Object.class)).isEmpty();
        assertThat(Json.readSet((ByteBuffer) null)).isEmpty();
        assertThat(Json.readSet((ByteBuffer) null, Object.class)).isEmpty();
        assertThat(Json.readListOfMap((ByteBuffer) null)).isEmpty();
        assertThat(Json.readMap((ByteBuffer) null)).isEmpty();
        assertThat(Json.readMap((ByteBuffer) null, String.class)).isEmpty();
        assertThat(Json.readMap((ByteBuffer) null, String.class, String.class)).isEmpty();
    }

    public void shouldRetrieveDeserializedObjectWhenReadValue() throws IOException {
        Data actual = Json.readValue(ResourceData.getResourceAsByteBuffer("/data.json"), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Data.TOM_CRUISE);
    }

    public void shouldRetrieveCorrectNumericWhenObjectContainsDifferentNumericList() throws IOException {
        List<Object> actual = Json.readList(convertToByteBuffer("[\"one\", \"two\", \"three\"]"));
        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveDeserializedListWhenReadAsList() throws IOException {
        List<Data> actual = Json.readList(ResourceData.byteBufferDataList(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveUniqueValuesWhenReadListNoUniqueValueAsSet() throws IOException {
        Set<Object> actual = Json.readSet(convertToByteBuffer("[\"one\",\"two\",\"three\",\"two\",\"one\"]"));

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveUniqueDataWhenReadListNoUniqueDataAsSet() throws IOException {
        Set<Data> actual = Json.readSet(ResourceData.byteBufferDataSet(), Data.class);

        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactly(Data.TOM_CRUISE, Data.NICOLE_KIDMAN);
    }

    public void shouldRetrieveListOfMapWhenRead() throws IOException {
        List<Map<String, Object>> actual = Json.readListOfMap(ResourceData.byteBufferDataList());

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

        Iterator<Object> it = Json.readListLazy(ResourceData.byteBufferDataList());
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

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadValueLazyList() throws Exception {
        Iterator<Data> it = Json.readListLazy(ResourceData.byteBufferDataList(), Data.class);
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

    public void shouldRetrieveIteratorOfDeserializedObjectsWhenReadByteBufferAsListOfMapLazy() throws Exception {
        Map<String, Object> tomCruise = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> nicoleKidman = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                       Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        Iterator<Map<String, Object>> it = Json.readListOfMapLazy(ResourceData.byteBufferDataList());
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

    public void shouldRetrieveDataMapWhenReadAsMapWithStringKey() throws IOException {
        Map<String, Object> tomCruise = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> nicoleKidman = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                       Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        Map<String, Object> actual = Json.readMap(ResourceData.byteBufferDataMap());
        assertThat(actual).isNotNull();
        assertThat(actual.keySet()).containsExactly("man", "woman");
        assertThat(actual).containsEntry("man", tomCruise);
        assertThat(actual).containsEntry("woman", nicoleKidman);
    }

    public void shouldRetrieveDeserializedMapWhenReadAsMapListWithStringKeyAndDataType() throws IOException {
        Map<String, Data> expected = MapUtils.of("man", Data.TOM_CRUISE,
                                                 "woman", Data.NICOLE_KIDMAN);

        Map<String, Data> actual = Json.readMap(ResourceData.byteBufferDataMap(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldRetrieveIntegerValueMapWhenReadAsMapWithIntKeyAndBookType() throws IOException {
        Map<Character, Data> expected = MapUtils.of('M', Data.TOM_CRUISE,
                                                    'W', Data.NICOLE_KIDMAN);

        Map<Character, Data> actual = Json.readMap(ResourceData.getResourceAsByteBuffer("/data_map_char.json"),
                                                   Character.class, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    private static ByteBuffer convertToByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
    }

}


