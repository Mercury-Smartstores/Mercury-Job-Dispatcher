package batch;

import metadata.pose.HumanPose;
import metadata.shelf.ShelfData;
import metadata.tracker.ObjectDetected;

import java.util.Map;

import static util.Utilities.mapToString;

public final class MetadataBatch {

    public final Map<Integer, HumanPose> poseMetadata;
    public final Map<Integer, ShelfData> shelfMetadata;
    public final Map<Integer, ObjectDetected> trackerMetadata;

    public MetadataBatch(Map<Integer, HumanPose> poseMetadata, Map<Integer, ShelfData> shelfMetadata,
                         Map<Integer, ObjectDetected> trackerMetadata) {
        this.poseMetadata = poseMetadata;
        this.trackerMetadata = trackerMetadata;
        this.shelfMetadata = shelfMetadata;
    }

    public String toString() {
        return String.format("Pose Metadata: %s\nShelf Metadata: %s\nTracker Metadata: %s", mapToString(poseMetadata),
                mapToString(shelfMetadata), mapToString(trackerMetadata));
    }


}
