package util.arg;

import lombok.Getter;
import org.apache.commons.cli.*;

public class ArgumentsParser {

    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();
    private final HelpFormatter formatter = new HelpFormatter();
    @Getter private String trackingMetadataFilename = "";
    @Getter private String poseMetadataFilename = "";
    @Getter private String shelfMetadataFilename = "";

    public ArgumentsParser() {
        addOption("t", "tracking", "Tracking metadata file ([REQUIRED] or use --tracking)");
        addOption("p", "pose", "Pose estimation metadata file ([REQUIRED] or use --pose)");
        addOption("s", "shelf", "Shelf metadata file ([REQUIRED] or use --shelf)");
    }

    private void addOption(final String shortOpt, final String longOpt, final String description) {
        options.addOption(Option.builder(shortOpt)
                .longOpt(longOpt)
                .hasArg(true)
                .desc(description)
                .required()
                .build());
    }

    public void parse(String [] args) {
        try {
            CommandLine cmd = parser.parse(options, args);
            trackingMetadataFilename = cmd.getOptionValue("t");
            poseMetadataFilename = cmd.getOptionValue("p");
            shelfMetadataFilename = cmd.getOptionValue("s");
        } catch (ParseException e) {
            System.out.println("Error while parsing command-line arguments");
            System.out.println("Please, follow the instructions below:");
            formatter.printHelp("JobDispatcher", options);
            System.exit(1);
        }
    }
}
