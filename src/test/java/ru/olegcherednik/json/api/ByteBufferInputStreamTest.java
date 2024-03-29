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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 05.01.2024
 */
@Test
public class ByteBufferInputStreamTest {

    public void shouldRetrieveEofWhenNotRemainingBytes() throws IOException {
        try (InputStream in = new ByteBufferInputStream(ByteBuffer.wrap(new byte[] { 1, 2 }))) {
            assertThat(in.read()).isEqualTo(1);
            assertThat(in.read()).isEqualTo(2);
            assertThat(in.read()).isEqualTo(-1);
        }
    }
}
