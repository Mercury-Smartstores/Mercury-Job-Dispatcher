package metadata.shelf;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShelfData {

    public final Integer shelfStand;
    public final String action;
    public final Double weight;

    public String toString() {
        return String.format("stand: %d, action: %s, weight: %,.2f", shelfStand, action, weight);
    }

}
