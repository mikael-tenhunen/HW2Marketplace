package marketplace.shared;

import java.rmi.Remote;


public interface Client extends Remote {
    void offerProduct(Item product);
    void buyProduct(Item product);
    void notifySale(Item product);
    
}
