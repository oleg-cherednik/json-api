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

import org.testng.annotations.AfterMethod;
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
public class JsonHelperTest {

    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.parse("2017-07-23T13:57:14.225Z");

    @AfterMethod
    public void cleanup() {
        JsonHelper.setDefaultSettings(JsonSettings.builder().zoneId(LocalZoneId.ASIA_SINGAPORE).build());
    }

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void shouldUseCurrentDefaultSettingWhenCreateJsonEngine() {
        List<ZonedDateTime> data = Collections.singletonList(ZONED_DATE_TIME);

        // default settings - use zone as is
        assertThat(Json.writeValue(data)).isEqualTo("[\"2017-07-23T13:57:14.225Z\"]");
        assertThat(Json.createWriter().writeValue(data)).isEqualTo("[\"2017-07-23T13:57:14.225Z\"]");

        // update default settings to use Asia/Singapore zone
        JsonHelper.setDefaultSettings(JsonSettings.builder().zoneId(LocalZoneId.ASIA_SINGAPORE).build());
        assertThat(Json.writeValue(data)).isEqualTo("[\"2017-07-23T21:57:14.225+08:00[Asia/Singapore]\"]");
        assertThat(Json.createWriter().writeValue(data))
                .isEqualTo("[\"2017-07-23T21:57:14.225+08:00[Asia/Singapore]\"]");

        // update default settings to use Australia/Sydney zone
        JsonHelper.setDefaultSettings(JsonSettings.builder().zoneId(LocalZoneId.AUSTRALIA_SYDNEY).build());
        assertThat(Json.writeValue(data)).isEqualTo("[\"2017-07-23T23:57:14.225+10:00[Australia/Sydney]\"]");
        assertThat(Json.createWriter().writeValue(data))
                .isEqualTo("[\"2017-07-23T23:57:14.225+10:00[Australia/Sydney]\"]");

        // restore to initial default settings
        JsonHelper.reset();
        assertThat(Json.writeValue(data)).isEqualTo("[\"2017-07-23T13:57:14.225Z\"]");
        assertThat(Json.createWriter().writeValue(data)).isEqualTo("[\"2017-07-23T13:57:14.225Z\"]");
    }

}
