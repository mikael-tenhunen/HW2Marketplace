package marketplace.shared;

import bankrmi.shared.Account;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Marketplace extends Remote {
    public MarketplaceAccount registerCustomer(String customerName, Account bankAccount) throws RemoteException;
    public boolean unregisterCustomer(String customerName) throws RemoteException; 
    public boolean listItems() throws RemoteException;
}
