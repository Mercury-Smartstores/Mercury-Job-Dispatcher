package util.io;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import metadata.pose.HumanPoseReader;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class HumanPoseFileFormat {
    public int frame;
    public List<HumanPoseReader.Root> poses;
}
