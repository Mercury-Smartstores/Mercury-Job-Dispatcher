package batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metadata.MetadataObject;
import metadata.MetadataReader;
import metadata.pose.HumanPose;
import metadata.pose.HumanPoseReader;
import metadata.shelf.ShelfDataReader;
import metadata.tracker.Entity;
import metadata.tracker.EntityReader;

import java.awt.*;
import java.text.ParseException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MetadataBatchReader {

    public static MetadataBatch read(Integer frameId, Map<Integer, List<String>> poseMetadata, String shelfMetadata,
                                     Map<Integer, List<String>> trackerMetadata) throws ParseException {
        Map<Integer, List<MetadataObject>> processedPoses = processMetadata(poseMetadata, HumanPoseReader.getInstance());
        Map<Integer, List<MetadataObject>> processedEntities = processMetadata(trackerMetadata, EntityReader.getInstance());

        Set<Entity> entityHashSet = new HashSet<>();
        processedEntities.forEach((timeFrame, entityList) -> entityList.forEach(entity -> entityHashSet.add((Entity) entity)));

        Map<Integer, Map<Integer, Entity>> entities = new HashMap<>();
        processedEntities.forEach((timeFrame, entityList) -> entities.put(timeFrame, getMapEntityFromList(entityList)));

        Map<Integer, Map<Integer, HumanPose>> poses = new HashMap<>();
        processedPoses.forEach((timeFrame, humanPoseList) -> poses.put(timeFrame, getMapPosesFromMetadata(humanPoseList,
                entities.get(timeFrame))));

        return new MetadataBatch(frameId, entityHashSet, poses, ShelfDataReader.getInstance().read(shelfMetadata), entities);
    }

    private static Map<Integer, HumanPose> getMapPosesFromMetadata(List<MetadataObject> list,
                                                                   Map<Integer, Entity> entities) {
        Map<Integer, HumanPose> map = new HashMap<>();
        Set<Integer> setEntitiesId = entities.keySet();
        for (MetadataObject obj : list) {
            HumanPose pose = (HumanPose) obj;
            int bestId = -1, bestMatch = -1;
            for (Integer id : setEntitiesId) {
                int currentMatches = 0;
                for (Map.Entry<String, HumanPose.BodyPart> entry : pose.body.entrySet()) {
                    Point bodyPartPoint = new Point((int) entry.getValue().x, (int) entry.getValue().y);
                    if (entities.get(id).box.pointInside(bodyPartPoint)) {
                        currentMatches++;
                    }
                }
                if (currentMatches > bestMatch) {
                    bestMatch = currentMatches;
                    bestId = id;
                }
            }
            map.put(bestId, pose);
        }
        return map;
    }

    private static Map<Integer, Entity> getMapEntityFromList(List<MetadataObject> list) {
        Map<Integer, Entity> map = new HashMap<>();
        list.forEach(entity -> map.put(((Entity) entity).id, (Entity) entity));
        return map;
    }

    private static Map<Integer, List<MetadataObject>> processMetadata(Map<Integer, List<String>> rawMetadata, MetadataReader reader) {
        return rawMetadata.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    List<MetadataObject> metadataList = new ArrayList<>();
                    e.getValue().forEach(
                            metadataStr ->
                            {
                                MetadataObject obj = null;
                                try {
                                    obj = reader.read(metadataStr);
                                } catch (Exception parseException) {
                                    parseException.printStackTrace();
                                }
                                metadataList.add(obj);
                            }
                    );
                    return metadataList;
                }));
    }

}
