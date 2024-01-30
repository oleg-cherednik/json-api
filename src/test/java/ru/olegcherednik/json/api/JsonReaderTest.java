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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Oleg Cherednik
 * @since 05.01.2024
 */
@Test
public class JsonReaderTest {

    public void shouldReturnTrueWhenJsonIsEmpty() {
        assertThat(LocalJsonReader.isNullOrEmptyLocal(null)).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal("")).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal("{}")).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal("[]")).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" { } ")).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" [ ] ")).isEqualTo(true);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" l ] ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" { ] ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" [ } ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" } { ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" ] [ ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" { ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" [ ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" } ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" ] ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" {}{} ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal(" [][] ")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal("{1,2,3}")).isEqualTo(false);
        assertThat(LocalJsonReader.isNullOrEmptyLocal("[1,2,3]")).isEqualTo(false);
    }

    public void shouldReturnTrueWhenStringIsBlank() {
        assertThat(LocalJsonReader.isBlankLocal(null)).isEqualTo(true);
        assertThat(LocalJsonReader.isBlankLocal("")).isEqualTo(true);
        assertThat(LocalJsonReader.isBlankLocal("  ")).isEqualTo(true);
        assertThat(LocalJsonReader.isBlankLocal("one")).isEqualTo(false);
        assertThat(LocalJsonReader.isBlankLocal(" two ")).isEqualTo(false);
    }

    @SuppressWarnings("PMD.CloseResource")
    public void shouldDelegateToInputStreamWhenUseUtf8Reader() throws IOException {
        InputStream in = mock(InputStream.class);
        when(in.markSupported()).thenReturn(true);

        Reader reader = LocalJsonReader.utf8ReaderLocal(in);
        assertThat(reader.markSupported()).isTrue();
        verify(in, times(1)).markSupported();

        reader.mark(666);
        verify(in, times(1)).mark(eq(666));

        reader.reset();
        verify(in, times(1)).reset();
    }

    private static final class LocalJsonReader extends JsonReader {

        private LocalJsonReader() {
            super(null);
        }

        public static boolean isNullOrEmptyLocal(String json) {
            return isNullOrEmpty(json);
        }

        public static boolean isBlankLocal(String str) {
            return isBlank(str);
        }

        @SuppressWarnings("NewMethodNamingConvention")
        public static Reader utf8ReaderLocal(InputStream in) {
            return utf8Reader(in);
        }

    }

}
