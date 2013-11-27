package marketplace.server;

import bankrmi.shared.Account;
import bankrmi.shared.Bank;
import bankrmi.shared.RejectedException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    private Marketplace marketplace;
    
    public MarketplaceAccountImpl(String customerName, String bankAccountName, 
            Marketplace marketplace) throws RemoteException, RegisterCustomerException {
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
        System.out.println("I heard you need a dollar");
        //TEST
    }

    @Override
    public void deposit(float value) throws RemoteException, RejectedException {
        bankAccount.deposit(value);
    }

    @Override
    public void withdraw(float value) throws RemoteException, RejectedException {
        bankAccount.withdraw(value);
    }

    @Override
    public void addProduct(Item product) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buyProduct(Item product) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addWish(String itemName, float maxPrice) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyWishAvailable() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
