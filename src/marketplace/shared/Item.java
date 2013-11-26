package marketplace.shared;

public interface Item {
        
    public String getName();
    public void setName(String name);
    public float getPrice();
    public void setPrice(float price);
    public MarketplaceClient getSeller();
    public void setSeller(MarketplaceClient seller);
}
