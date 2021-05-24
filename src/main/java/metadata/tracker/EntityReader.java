package metadata.tracker;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metadata.MetadataReader;
import util.AbstractSingleton;
import util.math.BoundingQuadrilateral;

import java.awt.*;
import java.rmi.UnexpectedException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityReader extends MetadataReader<Entity> {


    private static final AbstractSingleton<EntityReader> objHolder = new AbstractSingleton<>() {
        @Override
        protected EntityReader newObj() {
            return new EntityReader();
        }
    };

    public static EntityReader getInstance(){
        return objHolder.getInstance();
    }

    @Override
    public Entity read(String metadata) throws ParseException, UnexpectedException {
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

        int xmin = Integer.parseInt(boxMatcher.group(1));
        int ymin = Integer.parseInt(boxMatcher.group(2));
        int xmax = Integer.parseInt(boxMatcher.group(3));
        int ymax = Integer.parseInt(boxMatcher.group(4));

        Point p1 = new Point(xmin, ymin);
        Point p3 = new Point(xmax, ymax);
        Point p2 = new Point(xmin, ymax);
        Point p4 = new Point(xmax, ymin);

        return new Entity(className, objectId, new BoundingQuadrilateral(p1, p2, p3, p4));
    }

}
