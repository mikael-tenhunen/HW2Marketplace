package marketplace.server;

import bankrmi.shared.Account;
import bankrmi.shared.Bank;
import bankrmi.shared.RejectedException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import marketplace.shared.Item;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.MarketplaceClient;
import marketplace.shared.RegisterCustomerException;

public class MarketplaceAccountImpl extends UnicastRemoteObject implements MarketplaceAccount{
    static final String DEFAULT_BANK = "Nordea";
    private String customerName;
    private Account bankAccount;
    private MarketplaceClient client;   //to make callbacks
    private MarketplaceImpl marketplace;
    private List<Item> availableSales;
    
    public MarketplaceAccountImpl(String customerName, String bankAccountName, 
            MarketplaceImpl marketplace) throws RemoteException, RegisterCustomerException {
        this.customerName = customerName;
        //We do this so we can do callbacks to client
        try {
                client = (MarketplaceClient) Naming.lookup(customerName);
        } catch (Exception e) {
                System.out.println("Failed creating a serverside representation"
                        + " of MarketplaceClient in constructor of account: " 
                        + e.getMessage());
                throw new RegisterCustomerException("Failed creating a serverside representation"
                        + " of MarketplaceClient in constructor of account: " + e.getMessage()); 
        }    
        //Make RMI to bank account possible
        try {
            Bank bank = (Bank) Naming.lookup(DEFAULT_BANK);
            bankAccount = bank.getAccount(bankAccountName);
            System.out.println("Bank: " + bank);
            System.out.println("Bank Account: " + bankAccount);
        } catch (Exception e) {
            System.out.println("Failed creating a serverside representation"
                    + "of bank account in constructor of marketplace account: " 
                    + e.getMessage());
            throw new RegisterCustomerException("Failed creating a serverside representation"
                    + " of bank account in constructor of marketplace account: " + e.getMessage()); 
        }   

        this.marketplace = marketplace;
        System.out.println("MarketplaceAccount succesfully created");
        try {
            //TEST
            deposit(1);
        } catch (RejectedException ex) {
            System.out.println("Deposit rejected");
        }
        System.out.println("He needs a dollar, dollar");
        //TEST
        
        availableSales = new ArrayList();
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException, RejectedException {
        bankAccount.deposit(value);
    }

    @Override
    public synchronized void withdraw(float value) throws RemoteException, RejectedException {
        bankAccount.withdraw(value);
    }

    @Override
    public synchronized void addProduct(String productName, float price) throws RemoteException {
        Item product = (Item) new ItemImpl(productName, price, customerName);
        if (marketplace.listItems().contains(product)) {
            throw new RemoteException("Item already exists! Change price or name");
        }
        marketplace.addProduct(product);
    }

    @Override
    public synchronized void buyProduct(Item product) throws RemoteException {
        try {
            withdraw(product.getPrice());
            marketplace.buyProduct(product);
        } catch (RejectedException ex) {
            System.out.println("Bank rejected withdrawal for purchase");
        }
        
    }

    @Override
    public void addWish(String itemName, float maxPrice) throws RemoteException {
        marketplace.addWish(itemName, maxPrice, customerName);
    }

    @Override
    public void notifyWishAvailable() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void notifySale(String productName, float price) throws RemoteException {
        client.notifySale(productName, price);
    }
    
    public List<Item> getAvailableSales() {
        return availableSales;
    }

    public void setAvailableSales(List<Item> availableSales) {
        this.availableSales = availableSales;
    }
}
