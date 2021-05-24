package metadata.shelf;

import lombok.AllArgsConstructor;
import metadata.MetadataObject;

@AllArgsConstructor
public class ShelfData extends MetadataObject {

    public final Integer shelfStand;
    public final String action;
    public final Double weight;

    public String toString() {
        return String.format("stand: %d, action: %s, weight: %,.2f", shelfStand, action, weight);
    }

}
