package metadata.shelf;

public class ShelfData {

    public final Integer shelfStand;
    public final String action;
    public final Double weight;

    public ShelfData(Integer shelfStand, String action, Double weight) {
        this.shelfStand = shelfStand;
        this.action = action;
        this.weight = weight;
    }

    public String toString() {
        return String.format("stand: %d, action: %s, weight: %,.2f", shelfStand, action, weight);
    }

}
