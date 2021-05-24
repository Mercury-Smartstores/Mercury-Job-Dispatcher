package metadata.pose;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import metadata.MetadataObject;

import java.util.HashMap;

@AllArgsConstructor
public final class HumanPose extends MetadataObject {

    public HashMap<String, BodyPart> body;

    @AllArgsConstructor
    @NoArgsConstructor
    static class BodyPart {
        public double x;
        public double y;
        public double score;

        public String toString() {
            return String.format("(x:%,.2f,y:%,.2f) score:%,.2f", x, y, score);
        }
    }

}
