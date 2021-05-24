package util;

import java.util.Map;
import java.util.stream.Collectors;

public final class Utilities {

    private Utilities(){}

    public static String mapToString(Map<?, ?> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
