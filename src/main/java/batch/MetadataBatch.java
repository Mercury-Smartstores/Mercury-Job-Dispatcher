package batch;

import lombok.AllArgsConstructor;
import metadata.MetadataObject;

import java.util.List;
import java.util.Map;

import static util.Utilities.mapToString;

@AllArgsConstructor
public final class MetadataBatch {

    public final Integer frameId;
    public final Map<Integer, List<MetadataObject>> poseMetadata;
    public final MetadataObject shelfMetadata;
    public final Map<Integer, List<MetadataObject>> trackerMetadata;

    public String toString() {
        return String.format("Frame id: %d\n Pose Metadata: %s\nShelf Metadata: %s\nTracker Metadata: %s",
                frameId, mapToString(poseMetadata), shelfMetadata, mapToString(trackerMetadata));
    }


}
