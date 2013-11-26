package marketplace.server;

import bankrmi.shared.Account;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.Item;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    Map<String, MarketplaceAccount> accounts;
    
    
    public MarketplaceImpl() throws RemoteException { 
        this.accounts = new HashMap();
    }

    @Override
    public MarketplaceAccount registerCustomer(String customerName, 
            String bankAccountName) throws RemoteException {
        MarketplaceAccount account = (MarketplaceAccount) 
                new MarketplaceAccountImpl(customerName, bankAccountName, 
            this);
        accounts.put(customerName, account);
        return account;
    }

    @Override
    public boolean unregisterCustomer(String customerName) throws RemoteException {
        return false;
    }

    @Override
    public List<Item> listItems() throws RemoteException {
        return null;
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
