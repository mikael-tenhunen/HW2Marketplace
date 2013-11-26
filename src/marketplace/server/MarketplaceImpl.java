package marketplace.server;

import bankrmi.shared.Account;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    
    public MarketplaceImpl() throws RemoteException {
        super();
        
    }

    @Override
    public MarketplaceAccount registerCustomer(String customerName, Account bankAccount) throws RemoteException {
    }

    @Override
    public boolean unregisterCustomer(String customerName) throws RemoteException {
    }

    @Override
    public listItems() throws RemoteException {
    }
}
