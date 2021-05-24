import util.arg.ArgumentsParser;

public class JobDispatcher {

    private static final ArgumentsParser argumentsParser = new ArgumentsParser();

    public static void main (String [] args) {
        argumentsParser.parse(args);
    }

}