package metadata.shelf;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShelfDataReader {

    public static ShelfData read(String data) throws ParseException {
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
