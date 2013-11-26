package marketplace.server;

import marketplace.shared.MarketplaceClient;
import marketplace.shared.Item;
import marketplace.shared.MarketplaceAccount;


public class ItemImpl implements Item {
    private String name;
    private float price;
    private MarketplaceAccount seller;
    
    public ItemImpl (String name, float price, MarketplaceAccount seller) {
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

    public MarketplaceAccount getSeller() {
        return seller;
    }

    public void setSeller(MarketplaceAccount seller) {
        this.seller = seller;
    }
}
