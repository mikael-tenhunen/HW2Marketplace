package marketplace.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.Item;
import marketplace.shared.RegisterCustomerException;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    Map<String, MarketplaceAccount> accounts;
    Map<String, List<Float>> wishes;
    List<Item> items;
    
    
    public MarketplaceImpl() throws RemoteException { 
        this.accounts = new HashMap();
    }

    @Override
    public MarketplaceAccount registerCustomer(String customerName, 
            String bankAccountName) throws RemoteException, RegisterCustomerException {
        try
        {
        MarketplaceAccount account = (MarketplaceAccount) 
                new MarketplaceAccountImpl(customerName, bankAccountName, 
            this);
        accounts.put(customerName, account);
        return account;
        }
        catch(Exception e)
        {
            throw new RegisterCustomerException("Something went wrong with your"
                    +" registration. please try again");
        }
    }

    @Override
    public boolean unregisterCustomer(String customerName) throws RemoteException {
       return false;
    }

    @Override
    public List<Item> listItems() throws RemoteException {
        return null;
    }
}
