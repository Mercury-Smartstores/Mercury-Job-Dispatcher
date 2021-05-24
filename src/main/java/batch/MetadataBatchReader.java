package batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metadata.MetadataObject;
import metadata.MetadataReader;
import metadata.pose.HumanPoseReader;
import metadata.shelf.ShelfDataReader;
import metadata.tracker.ObjectDetectedReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MetadataBatchReader {

    public static MetadataBatch read(Map<Integer, List<String>> poseMetadata, Map<Integer, List<String>> shelfMetadata,
                                     Map<Integer, List<String>> trackerMetadata) {

        return new MetadataBatch(processMetadata(poseMetadata, HumanPoseReader.getInstance()),
                processMetadata(shelfMetadata, ShelfDataReader.getInstance()),
                processMetadata(trackerMetadata, ObjectDetectedReader.getInstance()));
    }


    private static Map<Integer, List<MetadataObject>> processMetadata(Map<Integer, List<String>> rawMetadata, MetadataReader reader){
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
