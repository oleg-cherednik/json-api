package ru.olegcherednik.json.api;

/**
 * @author Oleg Cherednik
 * @since 04.12.2023
 */
public class JsonDecorator {

    private JsonReadDecorator readDelegate;
    private JsonWriteDecorator writeDelegate;
    private JsonWriteDecorator prettyPrintWriteDelegate;

    // ---------- print ----------

    public JsonReadDecorator read() {
        return readDelegate;
    }

    public JsonWriteDecorator print() {
        return writeDelegate;
    }

    public JsonWriteDecorator prettyPrint() {
        return prettyPrintWriteDelegate;
    }

}
