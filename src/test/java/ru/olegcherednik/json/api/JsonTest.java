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

import java.time.ZonedDateTime;
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

    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.parse("2017-07-23T13:57:14.225Z");

    public void shouldCreateSeparateReaderWhenCreateReader() {
        JsonReader one = Json.createReader();
        JsonReader two = Json.createReader();
        assertThat(one).isNotSameAs(two);
    }

    public void shouldCreateSeparateWriterWhenCreateWriter() {
        JsonWriter one = Json.createWriter();
        JsonWriter two = Json.createWriter();
        assertThat(one).isNotSameAs(two);

        List<ZonedDateTime> data = Collections.singletonList(ZONED_DATE_TIME);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }

    public void shouldCreateSeparatePrettyPrintWriterWhenCreatePrettyPrint() {
        JsonWriter one = Json.createPrettyPrint();
        JsonWriter two = Json.createPrettyPrint();
        assertThat(one).isNotSameAs(two);

        List<ZonedDateTime> data = Collections.singletonList(ZONED_DATE_TIME);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }

    public void shouldCreateSeparatePrettyPrintWriterWithGivenSettingWhenCreatePrettyPrint() {
        JsonSettings settings = JsonSettings.builder().zoneId(LocalZoneId.ASIA_SINGAPORE).build();
        JsonWriter one = Json.createPrettyPrint(settings);
        JsonWriter two = Json.createPrettyPrint(settings);
        assertThat(one).isNotSameAs(two);

        List<ZonedDateTime> data = Collections.singletonList(ZONED_DATE_TIME);
        assertThat(one.writeValue(data)).isEqualTo(two.writeValue(data));
    }
}
