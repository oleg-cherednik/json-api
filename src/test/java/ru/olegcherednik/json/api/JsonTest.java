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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 05.01.2024
 */
@Test
@SuppressWarnings("NewClassNamingConvention")
public class JsonTest {

    public void shouldCreateSeparateReaderWhenCreateReader() {
        JsonReader one = Json.createReader();
        JsonReader two = Json.createReader();
        assertThat(one).isNotSameAs(two);
    }

    public void shouldCreateSeparateWriterWhenCreateWriter() {
        JsonWriter one = Json.createWriter();
        JsonWriter two = Json.createWriter();
        assertThat(one).isNotSameAs(two);

        List<Data> data = Collections.singletonList(Data.TOM_CRUISE);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }

    public void shouldCreateSeparatePrettyPrintWriterWhenCreatePrettyPrint() {
        JsonWriter one = Json.createPrettyPrint();
        JsonWriter two = Json.createPrettyPrint();
        assertThat(one).isNotSameAs(two);

        List<Data> data = Collections.singletonList(Data.NICOLE_KIDMAN);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }

    public void shouldCreateSeparatePrettyPrintWriterWithGivenSettingWhenCreatePrettyPrint() {
        JsonSettings settings = JsonSettings.builder()
                                            .serializeNullMapValue(false)
                                            .build();
        JsonWriter one = Json.createPrettyPrint(settings);
        JsonWriter two = Json.createPrettyPrint(settings);
        assertThat(one).isNotSameAs(two);

        List<Data> data = Collections.singletonList(Data.NICOLE_KIDMAN);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }

}
