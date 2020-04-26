package md.vnastasi.cloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    public static String readString(String fileName) {
        var inputStream = JsonUtils.class.getClassLoader().getResourceAsStream("json/" + fileName);
        Objects.requireNonNull(inputStream, String.format("Could not read from file </json/%s>", fileName));
        try (var scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            return scanner.next();
        }
    }

    public static <T> T deserialize(String fileName, Class<T> targetClass) throws IOException {
        var jsonString = readString(fileName);
        return new ObjectMapper().readValue(jsonString, targetClass);
    }
}
