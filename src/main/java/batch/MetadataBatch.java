package batch;

import lombok.AllArgsConstructor;
import metadata.MetadataObject;
import metadata.pose.HumanPose;
import metadata.shelf.ShelfData;
import metadata.tracker.Entity;

import java.util.Map;
import java.util.Set;

import static util.Utilities.mapToString;

@AllArgsConstructor
public final class MetadataBatch {

    public final Integer frameId;
    public final Set<Entity> entities;
    public final Map<Integer, Map<Integer, HumanPose>> poseMetadata; // Map frameTime to Map of Entity.id to Pose
    public final ShelfData shelfMetadata;
    public final Map<Integer, Map<Integer, Entity>> entitiesMetadata; // Map frameTime to Map of Entity.id to Entity

    public String toString() {
        return String.format("Frame id: %d\n Pose Metadata: %s\nShelf Metadata: %s\nTracker Metadata: %s",
                frameId, mapToString(poseMetadata), shelfMetadata, mapToString(entitiesMetadata));
    }


}
