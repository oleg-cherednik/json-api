package ru.olegcherednik.json.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.UnaryOperator;

/**
 * @author Oleg Cherednik
 * @since 02.12.2023
 */
public enum ZoneModifier implements UnaryOperator<ZoneId> {

    USE_ORIGINAL {
        @Override
        public ZoneId apply(ZoneId zoneId) {
            return zoneId;
        }
    },
    CONVERT_TO_UTC {
        @Override
        public ZoneId apply(ZoneId zoneId) {
            return ZoneOffset.UTC;
        }
    }

}
