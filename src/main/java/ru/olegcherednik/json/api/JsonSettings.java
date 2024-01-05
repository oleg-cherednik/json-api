/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
import java.util.Locale;

/**
 * @author Oleg Cherednik
 * @since 27.11.2023
 */
@Getter
@Builder
public class JsonSettings {

    public static final JsonSettings DEFAULT;
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
        DF_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        DF_INSTANT = DateTimeFormatter.ISO_INSTANT;
        DF_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
        DF_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
        DF_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DF_OFFSET_TIME = DateTimeFormatter.ISO_OFFSET_TIME;
        DF_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        DF_ZONED_DATE_TIME = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        DEFAULT = builder().build();
    }

    /**
     * Optional specification of {@link ZoneId} for {@code offsetTimeFormatter}, {@code offsetDateTimeFormatter} and
     * {@code zonedDateTimeFormatter}. In case formatter already contains zone, then it will be overwritten.
     */
    private ZoneId zoneId;
    /**
     * Formatter is used for {@link Date}.
     */
    @Builder.Default
    private final DateFormat dateFormatter = DF_DATE;
    /**
     * Formatter is used for {@link Instant}.
     */
    @Builder.Default
    private final DateTimeFormatter instantFormatter = DF_INSTANT;
    /**
     * Formatter is used for {@link LocalDate}.
     */
    @Builder.Default
    private final DateTimeFormatter localDateFormatter = DF_LOCAL_DATE;
    /**
     * Formatter is used for {@link LocalTime}.
     */
    @Builder.Default
    private final DateTimeFormatter localTimeFormatter = DF_LOCAL_TIME;
    /**
     * Formatter is used for {@link LocalDateTime}.
     */
    @Builder.Default
    private final DateTimeFormatter localDateTimeFormatter = DF_LOCAL_DATE_TIME;
    /**
     * Formatter is used for {@link OffsetTime}.
     */
    @Builder.Default
    private final DateTimeFormatter offsetTimeFormatter = DF_OFFSET_TIME;
    /**
     * Formatter is used for {@link OffsetDateTime}.
     */
    @Builder.Default
    private final DateTimeFormatter offsetDateTimeFormatter = DF_OFFSET_DATE_TIME;
    /**
     * Formatter is used for {@link ZonedDateTime}.
     */
    @Builder.Default
    private final DateTimeFormatter zonedDateTimeFormatter = DF_ZONED_DATE_TIME;
    /**
     * If {@literal true}, then <tt>null</tt> values will be serialized as well.
     */
    private final boolean serializeNull;

}
