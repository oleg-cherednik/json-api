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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * @author Oleg Cherednik
 * @since 07.01.2021
 */
@Test
public class WriterTest {

    public void shouldRetrieveNullWhenObjectNull() {
        assertThat(Json.writeValue(null)).isNull();
    }

    public void shouldNotWriteToOutputStreamWhenObjectNull() throws IOException {
        try (OutputStream out = mock(OutputStream.class)) {
            Json.writeValue(null, out);
            verifyNoInteractions(out);
        }
    }

    public void shouldNotWriteToWriterWhenObjectNull() throws IOException {
        try (Writer out = mock(Writer.class)) {
            Json.writeValue(null, out);
            verifyNoInteractions(out);
        }
    }

    public void shouldRetrieveJsonWhenWriteObject() {
        String actual = Json.writeValue(Data.TOM_CRUISE);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo("{\"firstName\":\"Tom\",\"lastName\":\"Cruise\"}");
    }

    public void shouldWriteJsonToStreamWhenWriteObjectToWriter() throws IOException {
        try (Writer out = new StringWriter()) {
            Json.writeValue(Data.TOM_CRUISE, out);
            assertThat(out).hasToString("{\"firstName\":\"Tom\",\"lastName\":\"Cruise\"}");
        }
    }

    public void shouldWriteJsonToStreamWhenWriteObjectToOutputStream() throws IOException {
        try (OutputStream out = new ByteArrayOutputStream()) {
            Json.writeValue(Data.NICOLE_KIDMAN, out);
            assertThat(out).hasToString("{\"firstName\":\"Nicole\",\"lastName\":\"Kidman\"}");
        }
    }

}
