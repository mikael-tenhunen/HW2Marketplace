package marketplace.server;

import bankrmi.shared.RejectedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.Item;
import marketplace.shared.RegisterCustomerException;

public class MarketplaceImpl extends UnicastRemoteObject implements Marketplace {
    Map<String, MarketplaceAccount> accounts;
    List<Wish> wishes;
    List<Item> items;
    
    
    public MarketplaceImpl() throws RemoteException { 
        accounts = new HashMap();
        wishes = new ArrayList();
        items = new ArrayList();
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
       MarketplaceAccountImpl account = (MarketplaceAccountImpl) accounts.get(customerName);
       List<Item> customerSales = account.getAvailableSales();
       for (Item item : customerSales) {
           items.remove(item);
       }
       accounts.remove(customerName);
       return true;
    }

    @Override
    public synchronized List<Item> listItems() throws RemoteException {
        return items;
    }
    
    public synchronized void addProduct(Item product) {
        items.add(product);
        MarketplaceAccountImpl account = (MarketplaceAccountImpl) accounts.get(product.getSellerName());
        account.getAvailableSales().add(product);
        Collections.sort(items);
    }
    
    public synchronized void buyProduct(Item product) {
        try {
            MarketplaceAccountImpl seller = (MarketplaceAccountImpl) accounts.get(product.getSellerName());
            seller.deposit(product.getPrice());
            seller.notifySale(product.getName(), product.getPrice());
            items.remove(product);
            seller.getAvailableSales().remove(product);
        } catch (RemoteException ex) {
            System.out.println("Problem contacting bank when depositing");
        } catch (RejectedException ex) {
            System.out.println("Problem depositing to bank account of seller");
        }
    }
}
