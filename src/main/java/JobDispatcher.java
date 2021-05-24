import util.Utilities;
import util.arg.ArgumentsParser;

import java.util.List;
import java.util.Map;

public class JobDispatcher {

    private static final ArgumentsParser argumentsParser = new ArgumentsParser();

    public static void main (String [] args) {
        argumentsParser.parse(args);
        Map<Integer, List<String>> shelfMap = Utilities.parseShelfMetadata(argumentsParser.getShelfMetadataFilename());
    }

}