package batch;

import lombok.AllArgsConstructor;
import metadata.pose.HumanPose;
import metadata.shelf.ShelfData;
import metadata.tracker.ObjectDetected;

import java.util.Map;

import static util.Utilities.mapToString;

@AllArgsConstructor
public final class MetadataBatch {

    public final Map<Integer, HumanPose> poseMetadata;
    public final Map<Integer, ShelfData> shelfMetadata;
    public final Map<Integer, ObjectDetected> trackerMetadata;

    public String toString() {
        return String.format("Pose Metadata: %s\nShelf Metadata: %s\nTracker Metadata: %s", mapToString(poseMetadata),
                mapToString(shelfMetadata), mapToString(trackerMetadata));
    }


}
