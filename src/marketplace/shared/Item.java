package marketplace.shared;

public interface Item {
        
    public String getName();
    public void setName(String name);
    public float getPrice();
    public void setPrice(float price);
    public MarketplaceAccount getSeller();
    public void setSeller(MarketplaceAccount seller);
}
