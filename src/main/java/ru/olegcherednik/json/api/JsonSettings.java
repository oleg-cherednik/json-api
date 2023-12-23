package ru.olegcherednik.json.api;

import lombok.Builder;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.UnaryOperator;

/**
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@Getter
@Builder
public class JsonSettings {

    public static final JsonSettings DEFAULT;
    public static final UnaryOperator<ZoneId> DEFAULT_ZONE_MODIFIER;
    public static final ZoneId SYSTEM_DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static final DateFormat DF_DATE;
    public static final DateTimeFormatter DF_INSTANT;
    public static final DateTimeFormatter DF_LOCAL_DATE;
    public static final DateTimeFormatter DF_LOCAL_TIME;
    public static final DateTimeFormatter DF_LOCAL_DATE_TIME;
    public static final DateTimeFormatter DF_OFFSET_TIME;
    public static final DateTimeFormatter DF_OFFSET_DATE_TIME;
    public static final DateTimeFormatter DF_ZONED_DATE_TIME;

    static {
        DEFAULT_ZONE_MODIFIER = ZoneModifier.USE_SYSTEM_DEFAULT;

        DF_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        DF_INSTANT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
        DF_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
        DF_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
        DF_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DF_OFFSET_TIME = DateTimeFormatter.ISO_OFFSET_TIME;
        DF_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        DF_ZONED_DATE_TIME = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        DEFAULT = builder().build();
    }

    @Builder.Default
    private UnaryOperator<ZoneId> zoneModifier = DEFAULT_ZONE_MODIFIER;

    /** This formatter is used for {@link Date}. */
    @Builder.Default
    private DateFormat dateFormatter = DF_DATE;
    /** This formatter is used for {@link Instant}. */
    @Builder.Default
    private DateTimeFormatter instantFormatter = DF_INSTANT;
    /** This formatter is used for {@link LocalDate}. */
    @Builder.Default
    private DateTimeFormatter localDateFormatter = DF_LOCAL_DATE;
    /** This formatter is used for {@link LocalTime}. */
    @Builder.Default
    private DateTimeFormatter localTimeFormatter = DF_LOCAL_TIME;
    /** This formatter is used for {@link LocalDateTime}. */
    @Builder.Default
    private DateTimeFormatter localDateTimeFormatter = DF_LOCAL_DATE_TIME;
    /** This formatter is used for {@link OffsetTime}. */
    @Builder.Default
    private DateTimeFormatter offsetTimeFormatter = DF_OFFSET_TIME;
    /** This formatter is used for {@link OffsetDateTime}. */
    @Builder.Default
    private DateTimeFormatter offsetDateTimeFormatter = DF_OFFSET_DATE_TIME;
    /** This formatter is used for {@link ZonedDateTime}. */
    @Builder.Default
    private DateTimeFormatter zonedDateTimeFormatter = DF_ZONED_DATE_TIME;

}
