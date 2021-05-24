package metadata.pose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metadata.MetadataReader;
import util.AbstractSingleton;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HumanPoseReader extends MetadataReader<HumanPose> {


    private static final AbstractSingleton<HumanPoseReader> objHolder = new AbstractSingleton<HumanPoseReader>() {
        @Override
        protected HumanPoseReader newObj() {
            return new HumanPoseReader();
        }
    };

    public static HumanPoseReader getInstance() {
        return objHolder.getInstance();
    }

    @Override
    public HumanPose read(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(jsonStr, Root.class);
        HashMap<String, HumanPose.BodyPart> body = new HashMap<>();
        for (Result result : root.result) {
            body.put(result.part, new HumanPose.BodyPart(result.position.x, result.position.y, result.score));
        }
        return new HumanPose(body);
    }

    static class Position {
        public double x;
        public double y;
    }

    static class Result {
        public double score;
        public String part;
        public Position position;
    }

    static class Root {
        public boolean success;
        public List<Result> result;
    }

}
