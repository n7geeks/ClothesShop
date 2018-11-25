package com.bondif.clothesshop.core;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class Tools {
    public static String getExtension(String path) {
        if(path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0)
            return path.substring(path.lastIndexOf(".")+1);

        return null;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static java.sql.Date toDate(LocalDateTime dateTime) {
        return new java.sql.Date(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
