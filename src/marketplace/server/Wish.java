package marketplace.server;

public class Wish {
    private String itemName;
    private float maxPrice;
    private String wisherName;
    
    public Wish(String itemName, float maxPrice, String wisherName) {
        this.itemName = itemName;
        this.maxPrice = maxPrice;
        this.wisherName = wisherName;
    }

    public String getItemName() {
        return itemName;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public String getWisherName() {
        return wisherName;
    }
}
