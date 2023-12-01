package ru.olegcherednik.json.api;

import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

/**
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@Getter
@Builder
public class JsonSettings {

    @Builder.Default
    private DateTimeFormatter dateTimeFormatter = ISO_OFFSET_DATE_TIME;

}
