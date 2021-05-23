package tracker;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ObjectDetectedReader {

    private ObjectDetectedReader() {
    }

    public static ObjectDetected read(String metadata) throws ParseException {

        Pattern boxCoordPattern = Pattern.compile("\\((\\d+), (\\d+), (\\d+), (\\d+)\\)");
        Pattern trackerIdPattern = Pattern.compile("Tracker ID: (\\d+)");
        Pattern classPattern = Pattern.compile("Class: ([a-zA-Z]+)");
        Matcher boxMatcher = boxCoordPattern.matcher(metadata);
        Matcher trackerIdMatcher = trackerIdPattern.matcher(metadata);
        Matcher classMatcher = classPattern.matcher(metadata);

        if (!boxMatcher.find() || !trackerIdMatcher.find() || !classMatcher.find()) {
            throw new ParseException("Object detected cannot be parsed", -1);
        }

        String className = classMatcher.group(1);
        Integer objectId = Integer.parseInt(trackerIdMatcher.group(1));
        List<Integer> boxCoord = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            boxCoord.add(Integer.parseInt(boxMatcher.group(i)));
        }
        return new ObjectDetected(className, objectId, boxCoord);
    }

}