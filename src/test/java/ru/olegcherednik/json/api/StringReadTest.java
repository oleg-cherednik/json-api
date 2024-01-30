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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 07.01.2021
 */
@Test
public class StringReadTest {

    public void shouldRetrieveNullWhenObjectNull() {
        assertThat(Json.readValue((String) null, Object.class)).isNull();
    }

    public void shouldRetrieveEmptyCollectionWhenObjectNull() {
        assertThat(Json.readList((String) null)).isEmpty();
        assertThat(Json.readList((String) null, Object.class)).isEmpty();
        assertThat(Json.readSet((String) null)).isEmpty();
        assertThat(Json.readSet((String) null, Object.class)).isEmpty();
        assertThat(Json.readListOfMap((String) null)).isEmpty();
        assertThat(Json.readMap((String) null)).isEmpty();
        assertThat(Json.readMap((String) null, Object.class)).isEmpty();
        assertThat(Json.readMap((String) null, String.class, String.class)).isEmpty();
    }

    public void shouldRetrieveDeserializedObjectWhenReadValue() throws IOException {
        String json = ResourceData.getResourceAsString("/data.json");
        Data actual = Json.readValue(json, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Data.TOM_CRUISE);
    }

    public void shouldRetrieveCorrectNumericWhenObjectContainsDifferentNumericList() {
        String json = "[\"one\", \"two\", \"three\"]";
        List<Object> actual = Json.readList(json);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveDeserializedListWhenReadAsList() throws IOException {
        String json = ResourceData.stringDataList();
        List<Data> actual = Json.readList(json, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(Arrays.asList(Data.TOM_CRUISE, Data.NICOLE_KIDMAN));
    }

    public void shouldRetrieveCorrectNumericWhenObjectContainsDifferentNumericSet() {
        String json = "[\"one\", \"two\", \"three\", \"two\", \"one\"]";
        Set<Object> actual = Json.readSet(json);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveUniqueValuesWhenReadListNoUniqueValueAsSet() {
        String json = "[\"one\",\"two\",\"three\",\"two\",\"one\"]";
        Set<String> actual = Json.readSet(json, String.class);

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactly("one", "two", "three");
    }

    public void shouldRetrieveListOfMapWhenRead() throws IOException {
        String json = ResourceData.stringDataList();
        List<Map<String, Object>> actual = Json.readListOfMap(json);

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).hasSize(2);
        assertThat(actual.get(0)).containsEntry(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName());
        assertThat(actual.get(0)).containsEntry(Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        assertThat(actual.get(1)).hasSize(2);
        assertThat(actual.get(1)).containsEntry(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName());
        assertThat(actual.get(1)).containsEntry(Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());
    }

    public void shouldRetrieveDataMapWhenReadAsMapWithStringKey() throws IOException {
        Map<String, Object> tomCruise = MapUtils.of(Data.FIRST_NAME, Data.TOM_CRUISE.getFirstName(),
                                                    Data.LAST_NAME, Data.TOM_CRUISE.getLastName());
        Map<String, Object> nicoleKidman = MapUtils.of(Data.FIRST_NAME, Data.NICOLE_KIDMAN.getFirstName(),
                                                       Data.LAST_NAME, Data.NICOLE_KIDMAN.getLastName());

        Map<String, Object> actual = Json.readMap(ResourceData.stringDataMap());
        assertThat(actual).isNotNull();
        assertThat(actual.keySet()).containsExactly("man", "woman");
        assertThat(actual).containsEntry("man", tomCruise);
        assertThat(actual).containsEntry("woman", nicoleKidman);
    }

    public void shouldRetrieveDeserializedMapWhenReadAsMapListWithStringKeyAndDataType() throws IOException {
        Map<String, Data> expected = MapUtils.of("man", Data.TOM_CRUISE,
                                                 "woman", Data.NICOLE_KIDMAN);

        Map<String, Data> actual = Json.readMap(ResourceData.stringDataMap(), Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldRetrieveIntegerValueMapWhenReadAsMapWithIntKeyAndBookType() throws IOException {
        Map<Character, Data> expected = MapUtils.of('M', Data.TOM_CRUISE,
                                                    'W', Data.NICOLE_KIDMAN);

        Map<Character, Data> actual = Json.readMap(ResourceData.getResourceAsString("/data_map_char.json"),
                                                   Character.class, Data.class);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

}
