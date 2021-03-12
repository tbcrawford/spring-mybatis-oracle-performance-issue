package io.perf.test.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AppUtils {

    private static final int MS_S = 1_000;

    public static double toSeconds(long milliseconds) {
        return (double) milliseconds / MS_S;
    }

    public static Date asDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
