package ru.olegcherednik.json.api;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.IOUtils;

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
            throw new RuntimeException(e);
        }
    }

    protected void apply(Writer writer, WriteTask task) {
        try {
            task.write(supplier.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            writer.flush();
        } catch (IOException ignored) {
            // ingored
        }
    }

    private static void requireNotNull(OutputStream out) {
        Objects.requireNonNull(out, "'out' should not be null");
    }

    private static void requireNotNull(Writer writer) {
        Objects.requireNonNull(writer, "'writer' should not be null");
    }

    public interface WriteStringTask {

        String write(JsonEngine jsonEngine) throws Exception;

    }

    public interface WriteTask {

        void write(JsonEngine jsonEngine) throws Exception;

    }
}
