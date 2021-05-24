package metadata.pose;

import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public final class HumanPose {

    public HashMap<String, BodyPart> body;

    static class BodyPart {
        public double x;
        public double y;
        public double score;

        public BodyPart(double x, double y, double score) {
            this.x = x;
            this.y = y;
            this.score = score;
        }

        public String toString() {
            return String.format("(x:%,.2f,y:%,.2f) score:%,.2f", x, y, score);
        }
    }

}
