package metadata.tracker;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ObjectDetected {

    public final String className;
    public final Integer objectId;
    public final List<Integer> boxCoord;

    public String toString() {
        return String.format("class: %s, id: %d, box: %s", className, objectId, boxCoord);
    }

}
