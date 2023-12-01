package ru.olegcherednik.json.api;

/**
 * @author Oleg Cherednik
 * @since 22.12.2020
 */
public class JsonException extends RuntimeException {

    public JsonException(String format, Object... args) {
        super(String.format(format, args));
    }

    public JsonException(Throwable cause) {
        super(cause);
    }

}
