import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.olegcherednik.json.api.Json;
import ru.olegcherednik.json.api.JsonHelper;
import ru.olegcherednik.json.api.JsonSettings;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Oleg Cherednik
 * @since 19.11.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtilsTest {

    public static void main(String... args) throws IOException {
        Logger log = LoggerFactory.getLogger(JsonUtilsTest.class);
        log.info("Hello World !!");

        // string
        Data s1 = Json.readValue(ResourceData.getResourceAsString("/data.json"), Data.class);
        List<Object> s21 = Json.readList(ResourceData.getResourceAsString("/data_list.json"));
        List<Data> s22 = Json.readList(ResourceData.getResourceAsString("/data_list.json"), Data.class);
        Set<Object> s31 = Json.readSet(ResourceData.getResourceAsString("/data_list.json"));
        Set<Data> s32 = Json.readSet(ResourceData.getResourceAsString("/data_list.json"), Data.class);
        List<Map<String, Object>> s4 = Json.readListOfMap(ResourceData.getResourceAsString("/data_list.json"));
        Map<String, Object> s51 = Json.readMap(ResourceData.getResourceAsString("/variable_value_map.json"));
        Map<String, Book> s52 = Json.readMap(ResourceData.getResourceAsString("/books_dict_string_key.json"),
                                             Book.class);
        Map<Integer, Book> s53 = Json.readMap(ResourceData.getResourceAsString("/books_dict_int_key.json"),
                                              Integer.class, Book.class);

        // byte buffer
        Data bb1 = Json.readValue(ResourceData.getResourceAsByteBuffer("/data.json"), Data.class);
        List<Object> bb21 = Json.readList(ResourceData.getResourceAsByteBuffer("/data_list.json"));
        List<Data> bb22 = Json.readList(ResourceData.getResourceAsByteBuffer("/data_list.json"), Data.class);
        Set<Object> bb31 = Json.readSet(ResourceData.getResourceAsByteBuffer("/data_list.json"));
        Set<Data> bb32 = Json.readSet(ResourceData.getResourceAsByteBuffer("/data_list.json"), Data.class);
        List<Map<String, Object>> bb4 = Json.readListOfMap(ResourceData.getResourceAsByteBuffer("/data_list.json"));
        Map<String, Object> bb51 = Json.readMap(ResourceData.getResourceAsByteBuffer("/variable_value_map.json"));
        Map<String, Book> bb52 = Json.readMap(ResourceData.getResourceAsByteBuffer("/books_dict_string_key.json"),
                                              Book.class);
        Map<Integer, Book> bb53 = Json.readMap(ResourceData.getResourceAsByteBuffer("/books_dict_int_key.json"),
                                               Integer.class, Book.class);
        List<Object> bb61 = convertToList(Json.readListLazy(ResourceData.getResourceAsByteBuffer("/books.json")));
        List<Book> bb62 = convertToList(Json.readListLazy(ResourceData.getResourceAsByteBuffer("/books.json"), Book.class));
        List<Map<String, Object>> bb7 = convertToList(Json.readListOfMapLazy(ResourceData.getResourceAsByteBuffer("/books.json")));

        // reader
        Data r1 = Json.readValue(ResourceData.getResourceAsReader("/data.json"), Data.class);
        List<Object> r21 = Json.readList(ResourceData.getResourceAsReader("/data_list.json"));
        List<Data> r22 = Json.readList(ResourceData.getResourceAsReader("/data_list.json"), Data.class);
        Set<Object> r31 = Json.readSet(ResourceData.getResourceAsReader("/data_list.json"));
        Set<Data> r32 = Json.readSet(ResourceData.getResourceAsReader("/data_list.json"), Data.class);
        List<Map<String, Object>> r4 = Json.readListOfMap(ResourceData.getResourceAsReader("/data_list.json"));
        Map<String, Object> r51 = Json.readMap(ResourceData.getResourceAsReader("/variable_value_map.json"));
        Map<String, Book> r52 = Json.readMap(ResourceData.getResourceAsReader("/books_dict_string_key.json"),
                                             Book.class);
        Map<Integer, Book> r53 = Json.readMap(ResourceData.getResourceAsReader("/books_dict_int_key.json"),
                                              Integer.class, Book.class);
        List<Object> r61 = convertToList(Json.readListLazy(ResourceData.getResourceAsReader("/books.json")));
        List<Book> r62 = convertToList(Json.readListLazy(ResourceData.getResourceAsReader("/books.json"), Book.class));
        List<Map<String, Object>> r7 = convertToList(Json.readListOfMapLazy(ResourceData.getResourceAsReader("/books.json")));

        // input stream
        Data is1 = Json.readValue(ResourceData.getResourceAsInputStream("/data.json"), Data.class);
        List<Object> is21 = Json.readList(ResourceData.getResourceAsInputStream("/data_list.json"));
        List<Data> is22 = Json.readList(ResourceData.getResourceAsInputStream("/data_list.json"), Data.class);
        Set<Object> is31 = Json.readSet(ResourceData.getResourceAsInputStream("/data_list.json"));
        Set<Data> is32 = Json.readSet(ResourceData.getResourceAsInputStream("/data_list.json"), Data.class);
        List<Map<String, Object>> is4 = Json.readListOfMap(ResourceData.getResourceAsInputStream("/data_list.json"));
        Map<String, Object> is51 = Json.readMap(ResourceData.getResourceAsInputStream("/variable_value_map.json"));
        Map<String, Book> is52 = Json.readMap(ResourceData.getResourceAsInputStream("/books_dict_string_key.json"),
                                              Book.class);
        Map<Integer, Book> is53 = Json.readMap(ResourceData.getResourceAsInputStream("/books_dict_int_key.json"),
                                               Integer.class, Book.class);
        List<Object> is61 = convertToList(Json.readListLazy(ResourceData.getResourceAsInputStream("/books.json")));
        List<Book> is62 = convertToList(Json.readListLazy(ResourceData.getResourceAsInputStream("/books.json"), Book.class));
        List<Map<String, Object>> is7 = convertToList(Json.readListOfMapLazy(ResourceData.getResourceAsInputStream("/books.json")));

        // write
        String w1 = Json.writeValue(Arrays.asList(new Data(555, "victory"), new Data(666, "omen")));
        String w2 = convertOutputStreamToString(out -> Json.writeValue(Arrays.asList(new Data(555, "victory"), new Data(666, "omen")), out));
        String w3 = convertWriterToString(writer -> Json.writeValue(Arrays.asList(new Data(555, "victory"), new Data(666, "omen")), writer));

        // pretty print
        System.out.println(Json.prettyPrint().writeValue(s1));

        Map<String, Object> map = new HashMap<>();
        map.put("now", ZonedDateTime.now().toInstant());
        System.out.println(Json.writeValue(map));

        JsonHelper.setDefaultSettings(JsonSettings.builder()
//                                           .zoneModifier(JsonSettings.ZONE_MODIFIER_USE_ORIGINAL)
                                                  .build());
        System.out.println(Json.writeValue(map));
        int a = 0;
        a++;

    }

    private static <V> List<V> convertToList(Iterator<V> it) {
        return IteratorUtils.toList(it);
    }

    private static String convertWriterToString(Consumer<Writer> consumer) throws IOException {
        try (StringWriter out = new StringWriter()) {
            consumer.accept(out);
            return out.toString();
        }
    }

    private static String convertOutputStreamToString(Consumer<OutputStream> consumer) throws IOException {
        try (OutputStream out = new ByteArrayOutputStream()) {
            consumer.accept(out);
            return out.toString();
        }
    }

}
