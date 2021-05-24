package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utilities {

    public static String mapToString(Map<?, ?> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    @SneakyThrows
    public static Map<Integer, List<String>> parseShelfMetadata(final String filepath) {
        Map<Integer, List<String>> result = new HashMap<>();
        getContentStream(filepath).forEach(line -> {
            String[] lineSplit = line.split("-",2);
            Integer frame = Integer.parseInt(lineSplit[0]);
            if (result.containsKey(frame)) {
                result.get(frame).add(lineSplit[1]);
            } else {
                result.put(frame, new ArrayList<>(Arrays.asList(lineSplit[1])));
            }
        });
        return result;
    }

    @SneakyThrows
    private static Stream<String> getContentStream(final String filepath) {
        return Files.readAllLines(Path.of(filepath)).stream();
    }
}
