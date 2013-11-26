package marketplace.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface MarketplaceClient extends Remote {
    public void offerProduct(Item product);
    public void buyProduct(Item product);
    public void notifySale(Item product) throws RemoteException;
    public void registerAtMarketplace();
    public void unregisterAtMarketplace();
    public void notifyWishAvailable() throws RemoteException;
}
