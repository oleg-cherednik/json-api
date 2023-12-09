package ru.olegcherednik.json.api;

/**
 * @author Oleg Cherednik
 * @since 04.12.2023
 */
public class JsonDecorator {

    private JsonReader readDelegate;
    private JsonWriter writeDelegate;
    private JsonWriter prettyPrintWriteDelegate;

    // ---------- print ----------

    public JsonReader read() {
        return readDelegate;
    }

    public JsonWriter print() {
        return writeDelegate;
    }

    public JsonWriter prettyPrint() {
        return prettyPrintWriteDelegate;
    }

}
