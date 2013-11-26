package marketplace.server;

import marketplace.shared.MarketplaceClient;
import marketplace.shared.Item;


public class ItemImpl implements Item {
    private String name;
    private float price;
    private MarketplaceClient seller;
    
    public ItemImpl (String name, float price, MarketplaceClient seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MarketplaceClient getSeller() {
        return seller;
    }

    public void setSeller(MarketplaceClient seller) {
        this.seller = seller;
    }
}
