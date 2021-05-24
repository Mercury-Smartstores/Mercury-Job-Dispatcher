package batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import metadata.pose.HumanPose;
import metadata.pose.HumanPoseReader;
import metadata.shelf.ShelfData;
import metadata.shelf.ShelfDataReader;
import metadata.tracker.ObjectDetected;
import metadata.tracker.ObjectDetectedReader;

import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

public final class MetadataBatchReader {

    private MetadataBatchReader() {
    }

    public static MetadataBatch read(Map<Integer, String> poseMetadata, Map<Integer, String> shelfMetadata,
                                     Map<Integer, String> trackerMetadata) {
        Map<Integer, HumanPose> poseMetadataProcessed = poseMetadata.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    HumanPose od = null;
                    try {
                        od = HumanPoseReader.read(e.getValue());
                    } catch (JsonProcessingException parseException) {
                        parseException.printStackTrace();
                    }
                    return od;
                }));

        Map<Integer, ShelfData> shelfMetadataProcessed = shelfMetadata.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    ShelfData od = null;
                    try {
                        od = ShelfDataReader.read(e.getValue());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    return od;
                }));

        Map<Integer, ObjectDetected> trackerMetadataProcessed = trackerMetadata.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    ObjectDetected od = null;
                    try {
                        od = ObjectDetectedReader.read(e.getValue());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    return od;
                }));

        return new MetadataBatch(poseMetadataProcessed, shelfMetadataProcessed, trackerMetadataProcessed);
    }

}
