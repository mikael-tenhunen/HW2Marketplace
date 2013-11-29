package marketplace.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Marketplace extends Remote {
    public MarketplaceAccount registerCustomer(MarketplaceClient client, String bankAccountName) throws RemoteException,
                                                                                            RegisterCustomerException;
    public boolean unregisterCustomer(String customerName) throws RemoteException; 
    public List<Item> listItems() throws RemoteException;
}
