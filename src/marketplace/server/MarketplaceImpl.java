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
import marketplace.shared.MarketplaceClient;
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
    public MarketplaceAccount registerCustomer(MarketplaceClient client, 
            String bankAccountName) throws RemoteException, RegisterCustomerException {
        try
        {
        String customerName = client.getName();
        MarketplaceAccount account = (MarketplaceAccount) 
                new MarketplaceAccountImpl(client, customerName, bankAccountName, 
            this);
        accounts.put(customerName, account);
        System.out.println("New account registered: " + customerName);
        return account;
        }
        catch(Exception e)
        {
            System.out.println(e);
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
       System.out.println("Account removed: " + customerName);
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
        for (Wish wish : wishes) {
            if (wish.getItemName().equals(product.getName())
                    && wish.getPrice() == product.getPrice()) {
                notifyWish(wish);
            }
        }
        System.out.println("Product added: " + product.getName());
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
    
    public synchronized void addWish(String itemName, float price, String wisherName) {
        Wish wish = new Wish(itemName, price, wisherName);
        wishes.add(wish);
        //Check if wished item already exists, in that case notify
        if (items.contains(new ItemImpl(itemName,price,null))) {
            notifyWish(wish);
        }
    }
   
    public synchronized void notifyWish(Wish wish) {
        String wisherName = wish.getWisherName();
        MarketplaceAccountImpl wisherAccount = (MarketplaceAccountImpl) accounts.get(wisherName);
        wisherAccount.notifyWishAvailable(wish.getItemName(), wish.getPrice());
    }

    public List<Wish> getWishes() {
        return wishes;
    }
}
