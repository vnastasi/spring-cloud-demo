package md.vnastasi.cloud.util;

import org.springframework.lang.NonNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class Strings {

    @NonNull
    public static OffsetDateTime asOffsetDatetime(@NonNull String input) {
        return OffsetDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"));
    }

    private Strings() {
    }
}
