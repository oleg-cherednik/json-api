package ru.olegcherednik.json.api;

import lombok.RequiredArgsConstructor;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@RequiredArgsConstructor
public class JsonWriteDecorator {

    protected final Supplier<JsonEngine> supplier;
    private final boolean autoCloseSource = false;

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
            writer.flush();

            if (autoCloseSource)
                writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
