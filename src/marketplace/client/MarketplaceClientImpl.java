package marketplace.client;

import bankrmi.client.Client;
import java.rmi.RemoteException;
import marketplace.shared.Item;
import marketplace.shared.MarketplaceClient;

public class MarketplaceClientImpl extends Client implements MarketplaceClient{
    public MarketplaceClientImpl () {
        super();
    }

    @Override
    public void offerProduct(Item product) {
    }

    @Override
    public void buyProduct(Item product) {
    }

    @Override
    public void notifySale(Item product) {
    }

    @Override
    public void registerAtMarketplace() {
    }

    @Override
    public void unregisterAtMarketplace() {
    }

    public void notifyWishAvailable() throws RemoteException {
    }

    @Override
    public void notifyWishAvailable(String itemName, float price) 
            throws RemoteException {
    }
    
    
}
