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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import ru.olegcherednik.json.api.enumid.EnumId;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 09.03.2024
 */
@Test
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class EnumIdMapValuesTest {

    private static final String UNKNOWN = "<unknown>";

    public void shouldRetrieveEnumValueWhenParseIdCaseInsensitive() {
        assertThat(Weekday.parseId("monday")).isSameAs(Weekday.MONDAY);
        assertThat(Weekday.parseId("WEDNESDAY")).isSameAs(Weekday.WEDNESDAY);
        assertThat(Weekday.parseId("sunDay-sUNday")).isSameAs(Weekday.SUNDAY);
        assertThat(Weekday.parseId(null)).isSameAs(Weekday.NONE);
    }

    public void shouldRetrieveEnumValueWhenParseNameCaseInsensitive() {
        assertThat(Weekday.parseName("tuesday")).isSameAs(Weekday.TUESDAY);
        assertThat(Weekday.parseName("ThuRsDay")).isSameAs(Weekday.THURSDAY);
        assertThat(Weekday.parseName("sunday")).isSameAs(Weekday.SUNDAY);
    }

    public void shouldRetrieveEnumValueWhenParseIdOrNameCaseInsensitive() {
        assertThat(Weekday.parseIdOrName("monday")).isSameAs(Weekday.MONDAY);
        assertThat(Weekday.parseIdOrName("WedNesDay")).isSameAs(Weekday.WEDNESDAY);
        assertThat(Weekday.parseIdOrName("sunday")).isSameAs(Weekday.SUNDAY);
        assertThat(Weekday.parseIdOrName("sunday-sunday")).isSameAs(Weekday.SUNDAY);
        assertThat(Weekday.parseIdOrName(null)).isSameAs(Weekday.NONE);
    }

    public void shouldRetrieveDefaultValueWhenNotFound() {
        assertThat(Weekday.parseIdWithDef(UNKNOWN)).isSameAs(Weekday.NONE);
        assertThat(Weekday.parseNameWithDef(UNKNOWN)).isSameAs(Weekday.NONE);
        assertThat(Weekday.parseIdOrNameWithDef(UNKNOWN)).isSameAs(Weekday.NONE);
    }

    public void shouldThrowExceptionWhenParseCaseInsensitiveNotFound() {
        assertThatThrownBy(() -> Weekday.parseId(UNKNOWN))
                .isExactlyInstanceOf(EnumConstantNotPresentException.class);
        assertThatThrownBy(() -> Color.parseId(UNKNOWN))
                .isExactlyInstanceOf(EnumConstantNotPresentException.class);
        assertThatThrownBy(() -> Weekday.parseName(UNKNOWN))
                .isExactlyInstanceOf(EnumConstantNotPresentException.class);
        assertThatThrownBy(() -> Weekday.parseName(null))
                .isExactlyInstanceOf(EnumConstantNotPresentException.class);
        assertThatThrownBy(() -> Weekday.parseIdOrName(UNKNOWN))
                .isExactlyInstanceOf(EnumConstantNotPresentException.class);
    }

    public void shouldUseEmptyMapWheNoConstants() {
        assertThat(Color.MAP_ID).isSameAs(Collections.emptyMap());
    }

    public void shouldThrowExceptionWhenDuplicateIds() {
        assertThatThrownBy(() -> Auto.parseId("audi")).isExactlyInstanceOf(ExceptionInInitializerError.class);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public enum Weekday implements EnumId {

        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday"),
        SUNDAY("sunday-sunday"),
        NONE(null);

        @SuppressWarnings("StaticCollection")
        private static final Map<String, Weekday> MAP_ID = EnumId.createMapId(Weekday.class);
        @SuppressWarnings("StaticCollection")
        private static final Map<String, Weekday> MAP_NAME = EnumId.createMapName(Weekday.class);

        private final String id;

        public static Weekday parseId(String id) {
            return EnumId.parseId(Weekday.class, MAP_ID, id);
        }

        public static Weekday parseIdWithDef(String id) {
            return EnumId.parseId(Weekday.class, MAP_ID, id, NONE);
        }

        public static Weekday parseName(String name) {
            return EnumId.parseName(Weekday.class, MAP_NAME, name);
        }

        public static Weekday parseNameWithDef(String name) {
            return EnumId.parseName(Weekday.class, MAP_NAME, name, NONE);
        }

        public static Weekday parseIdOrName(String idOrName) {
            return EnumId.parseIdOrName(Weekday.class, MAP_ID, MAP_NAME, idOrName);
        }

        public static Weekday parseIdOrNameWithDef(String idOrName) {
            return EnumId.parseIdOrName(Weekday.class, MAP_ID, MAP_NAME, idOrName, NONE);
        }

    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public enum Color implements EnumId {

        ;

        @SuppressWarnings("StaticCollection")
        private static final Map<String, Color> MAP_ID = EnumId.createMapId(Color.class);

        private final String id;

        public static Color parseId(String id) {
            return EnumId.parseId(Color.class, MAP_ID, id);
        }

    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public enum Auto implements EnumId {

        AUDI("audi"),
        MERCEDES_1("mercedes"),
        MERCEDES_2("mercedes");

        @SuppressWarnings("StaticCollection")
        private static final Map<String, Auto> MAP_ID = EnumId.createMapId(Auto.class);

        private final String id;

        public static Auto parseId(String id) {
            return EnumId.parseId(Auto.class, MAP_ID, id);
        }

    }

}
