package md.vnastasi.cloud.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonResponses {

    public static String fromFile(String fileName) {
        InputStream inputStream = JsonResponses.class.getClassLoader().getResourceAsStream("json/" + fileName);
        Objects.requireNonNull(inputStream, String.format("Could not read from file </json/%s>", fileName));
        try (Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            return scanner.next();
        }
    }
}
