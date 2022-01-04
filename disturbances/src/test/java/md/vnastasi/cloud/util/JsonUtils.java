package md.vnastasi.cloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public final class JsonUtils {

    @NonNull
    public static String readString(@NonNull String fileName) {
        var inputStream = JsonUtils.class.getClassLoader().getResourceAsStream("json/" + fileName);
        Objects.requireNonNull(inputStream, String.format("Could not read from file </json/%s>", fileName));
        try (var scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            return scanner.next();
        }
    }

    @NonNull
    public static <T> T deserialize(@NonNull String fileName, @NonNull Class<T> targetClass) throws IOException {
        var jsonString = readString(fileName);
        return new ObjectMapper().readValue(jsonString, targetClass);
    }
}
