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

package ru.olegcherednik.json.impl.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Oleg Cherednik
 * @since 08.01.2021
 */
@RequiredArgsConstructor
public class ZonedDateTimeTypeAdapter extends TypeAdapter<ZonedDateTime> {

    protected final DateTimeFormatter df;

    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        out.value(df.format(value));
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        return ZonedDateTime.parse(in.nextString(), df);
    }

}
