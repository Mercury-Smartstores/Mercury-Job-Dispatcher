package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import util.io.HumanPoseFileFormat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public static Map<Integer, List<String>> parsePoseMetadata(final String filepath) {
        Map<Integer, List<String>> result = new HashMap<>();
        Optional<String> jsonInput = getContentStream(filepath).reduce((f, s) -> f + "\n" + s);
        if (jsonInput.isEmpty()) {
            return result;
        }
        String jsonString = jsonInput.get();
        ObjectMapper mapper = new ObjectMapper();
        List<HumanPoseFileFormat> json = mapper.readValue(jsonString, new TypeReference<>(){});
        json.forEach(data -> {
            List<String> poses = new ArrayList<>();
            data.poses.forEach(pose -> {
                String jsonPoseString = "";
                try {
                    jsonPoseString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pose);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                poses.add(jsonPoseString);
            });
            result.put(data.frame, poses);
        });
        return result;
    }

    @SneakyThrows
    public static Map<Integer, List<String>> parseTrackingMetadata(final String filepath) {
        Map<Integer, List<String>> result = new HashMap<>();
        Pattern framePattern = Pattern.compile("Frame #: {2}(\\d+)");
        AtomicBoolean isValid = new AtomicBoolean(false);
        List<String> trackingInfoList = new ArrayList<>();
        AtomicInteger frame = new AtomicInteger(0);
        getContentStream(filepath).forEachOrdered(line -> {
            Matcher framePatternMatcher = framePattern.matcher(line);
            if (framePatternMatcher.matches()) {
                frame.set(Integer.parseInt(framePatternMatcher.group(1)));
            } else if (line.contains("Tracker")) {
                isValid.set(true);
                trackingInfoList.add(line);
            } else if (line.contains("FPS") && isValid.get()) {
                result.put(frame.get(), new ArrayList<>(trackingInfoList));
                isValid.set(false);
                trackingInfoList.clear();
            }
        });
        return result;
    }

    @SneakyThrows
    private static Stream<String> getContentStream(final String filepath) {
        return Files.readAllLines(Path.of(filepath)).stream();
    }
}
