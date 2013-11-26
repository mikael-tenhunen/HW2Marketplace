package marketplace.shared;

import java.rmi.Remote;


public interface MarketplaceClient extends Remote {
    void offerProduct(Item product);
    void buyProduct(Item product);
    void notifySale(Item product);
    
}
