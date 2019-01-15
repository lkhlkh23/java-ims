package support.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameConverter {

    private static final String SPLIT_STANDARD = ".";

    public static String convert(String fileName) {
        return String.format("%s", String.valueOf((getLocalDateTime() + fileName).hashCode()));
    }

    public static String getLocalDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
