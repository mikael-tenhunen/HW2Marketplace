package marketplace.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import marketplace.shared.Marketplace;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    
    public MarketplaceImpl() throws RemoteException {
        super();
        
    }
}
