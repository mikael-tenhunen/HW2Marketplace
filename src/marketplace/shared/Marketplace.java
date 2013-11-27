package marketplace.shared;

import bankrmi.shared.Account;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Marketplace extends Remote {
    public MarketplaceAccount registerCustomer(String customerName, String bankAccountName) throws RemoteException,
                                                                                            RegisterCustomerException;
    public boolean unregisterCustomer(String customerName) throws RemoteException; 
    public List<Item> listItems() throws RemoteException;
}
