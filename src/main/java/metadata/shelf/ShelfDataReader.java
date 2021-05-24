package metadata.shelf;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metadata.MetadataReader;
import util.AbstractSingleton;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShelfDataReader extends MetadataReader<ShelfData> {

    private static final AbstractSingleton<ShelfDataReader> objHolder = new AbstractSingleton<ShelfDataReader>() {
        @Override
        protected ShelfDataReader newObj() {
            return new ShelfDataReader();
        }
    };

    public static ShelfDataReader getInstance(){
        return objHolder.getInstance();
    }


    @Override
    public ShelfData read(String data) throws ParseException {
        Pattern shelfDataPattern = Pattern.compile("(\\d+)-(\\w*)-(\\d*\\.?\\d*)");
        Matcher shelfDataMatcher = shelfDataPattern.matcher(data);
        if (!shelfDataMatcher.find()){
            throw new ParseException("Object detected cannot be parsed", -1);
        }

        Integer shelfStand = Integer.parseInt(shelfDataMatcher.group(1));
        String action = shelfDataMatcher.group(2);
        Double weight = Double.parseDouble(shelfDataMatcher.group(3));
        return new ShelfData(shelfStand, action, weight);
    }

}
