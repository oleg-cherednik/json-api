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

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * This class represents all available writing methods for a {@link JsonEngine}.
 *
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@RequiredArgsConstructor
public class JsonWriter {

    protected final Supplier<JsonEngine> supplier;

    public <V> String writeValue(V obj) {
        if (obj == null)
            return null;
        return apply(jsonEngine -> jsonEngine.writeValue(obj));
    }

    public <V> void writeValue(V obj, OutputStream out) {
        if (obj == null)
            return;

        requireNotNull(out);
        writeValue(obj, new OutputStreamWriter(out));
    }

    public <V> void writeValue(V obj, Writer writer) {
        if (obj == null)
            return;

        requireNotNull(writer);
        apply(writer, jsonEngine -> jsonEngine.writeValue(obj, writer));
    }

    protected String apply(WriteStringTask task) {
        try {
            return task.write(supplier.get());
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    protected void apply(Writer writer, WriteTask task) {
        try {
            task.write(supplier.get());
        } catch (Exception e) {
            throw new JsonException(e);
        }

        try {
            writer.flush();
        } catch (IOException ignored) {
            // ignored
        }
    }

    protected static void requireNotNull(OutputStream out) {
        Objects.requireNonNull(out, "'out' should not be null");
    }

    protected static void requireNotNull(Writer writer) {
        Objects.requireNonNull(writer, "'writer' should not be null");
    }

    public interface WriteStringTask {

        String write(JsonEngine jsonEngine) throws Exception;

    }

    public interface WriteTask {

        void write(JsonEngine jsonEngine) throws Exception;

    }
}
