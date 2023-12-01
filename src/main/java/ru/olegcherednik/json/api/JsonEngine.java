package ru.olegcherednik.json.api;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an implementation of concrete json engine (e.g. Jackson or Gson).
 *
 * @author Oleg Cherednik
 * @since 24.11.2023
 */
public interface JsonEngine {

    // ---------- read String ----------

    <V> V readValue(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> List<V> readList(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> Set<V> readSet(String json, Class<V> valueClass) throws IOException;

    // @NotNull
    List<Map<String, Object>> readListOfMap(String json) throws IOException;

    // @NotNull
    <K, V> Map<K, V> readMap(String json, Class<K> keyClass, Class<V> valueClass) throws IOException;

    // ---------- read Reader ----------

    <V> V readValue(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> List<V> readList(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    <V> Set<V> readSet(Reader reader, Class<V> valueClass) throws IOException;

    // @NotNull
    List<Map<String, Object>> readListOfMap(Reader reader) throws IOException;

    <V> Iterator<V> readListLazy(Reader reader, Class<V> valueClass) throws IOException;

    Iterator<Map<String, Object>> readListOfMapLazy(Reader reader) throws IOException;

    // @NotNull
    <K, V> Map<K, V> readMap(Reader reader, Class<K> keyClass, Class<V> valueClass) throws IOException;

    // ---------- write ----------

    <V> String writeValue(V obj) throws IOException;

    <V> void writeValue(V obj, Writer writer) throws IOException;

}
