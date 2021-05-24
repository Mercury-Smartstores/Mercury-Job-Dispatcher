package metadata.tracker;

import lombok.AllArgsConstructor;
import metadata.MetadataObject;
import util.math.BoundingQuadrilateral;

@AllArgsConstructor
public class Entity extends MetadataObject {

    public final String className;
    public final Integer id;
    public final BoundingQuadrilateral box;

    public String toString() {
        return String.format("class: %s, id: %d, box: %s", className, id, box);
    }

}
