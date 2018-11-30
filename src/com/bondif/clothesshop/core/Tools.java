package com.bondif.clothesshop.core;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class Tools {
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
