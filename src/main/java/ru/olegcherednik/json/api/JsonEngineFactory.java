package ru.olegcherednik.json.api;

/**
 * @author Oleg Cherednik
 * @since 19.11.2023
 */
public interface JsonEngineFactory {

    // this method should be defined in implementations
    // public static JsonEngineFactory getInstance()

    JsonEngine createJsonEngine(JsonSettings settings);

    JsonEngine createPrettyPrintJsonEngine(JsonSettings settings);

}
