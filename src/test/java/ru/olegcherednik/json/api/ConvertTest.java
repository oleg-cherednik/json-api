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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 10.04.2022
 */
@Test
public class ConvertTest {

    public void shouldRetrieveEmptyCollectionWhenObjectNull() {
        assertThat(Json.convertToMap(null)).isEmpty();
    }

    public void shouldRetrieveMapWhenConvertToMap() {
        Map<String, Object> actual = Json.convertToMap(Data.TOM_CRUISE);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(MapUtils.of("firstName", "Tom", "lastName", "Cruise"));
    }

}
