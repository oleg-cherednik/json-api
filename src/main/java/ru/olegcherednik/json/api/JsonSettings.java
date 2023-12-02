package ru.olegcherednik.json.api;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

/**
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@Getter
@Builder
public class JsonSettings {

    public static final UnaryOperator<ZoneId> DEFAULT_ZONE_MODIFIER = ZoneModifier.USE_ORIGINAL;

    @Builder.Default
    private UnaryOperator<ZoneId> zoneModifier = DEFAULT_ZONE_MODIFIER;

    /**
     * This formatter is used for:
     * <ul>
     * <li>{@link LocalDate}</li>
     * </ul>
     */
    private DateTimeFormatter localDateFormatter;
    /**
     * This formatter is used for:
     * <ul>
     * <li>{@link LocalTime}</li>
     * </ul>
     */
    private DateTimeFormatter localTimeFormatter;
    /**
     * This formatter is used for:
     * <ul>
     * <li>{@link OffsetTime}</li>
     * </ul>
     */
    private DateTimeFormatter offsetTimeFormatter;
    /**
     * This formatter is used for:
     * <ul>
     * <li>{@link LocalDateTime}</li>
     * </ul>
     */
    private DateTimeFormatter dateTimeFormatter;
    /**
     * This formatter is used for:
     * <ul>
     * <li>{@link OffsetDateTime}</li>
     * <li>{@link ZonedDateTime}</li>
     * </ul>
     */
    private DateTimeFormatter offsetDateTimeFormatter;
}
