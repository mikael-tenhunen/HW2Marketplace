package marketplace.server;

import marketplace.shared.Client;
import marketplace.shared.Item;


public class ItemImpl implements Item {
    private String name;
    private float price;
    private Client seller;
    
    public ItemImpl (String name, float price, Client seller) {
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

    public Client getSeller() {
        return seller;
    }

    public void setSeller(Client seller) {
        this.seller = seller;
    }
}
