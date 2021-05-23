package tracker;

import java.util.List;

public class ObjectDetected {

    public final String className;
    public final Integer objectId;
    public final List<Integer> boxCoord;

    public ObjectDetected(String className, Integer objectId, List<Integer> boxCoord) {
        this.className = className;
        this.objectId = objectId;
        this.boxCoord = boxCoord;
    }

    public String toString() {
        return String.format("class: %s, id: %d, box: %s", className, objectId, boxCoord);
    }

}
