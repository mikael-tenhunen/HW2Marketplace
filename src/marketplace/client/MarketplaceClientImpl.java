package marketplace.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import marketplace.shared.Item;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.MarketplaceClient;
import marketplace.shared.RegisterCustomerException;

public class MarketplaceClientImpl extends UnicastRemoteObject implements MarketplaceClient{
    
    private Marketplace marketplace;
    private String bankAccountName;
    public MarketplaceAccount marketplaceAccount;
    private String name;
    
    public MarketplaceClientImpl(String name, String bankAccountName) throws RemoteException {
        this.name = name;
        
        try {
                marketplace = (Marketplace)Naming.lookup("marketplacemaininstance");
        } catch (Exception e) {
                System.out.println("The runtime failed: " + e.getMessage());
                System.exit(0);
        }
        System.out.println("Client connected to Marketplace");        
    }

    protected void offerProduct(String  itemName, float price) {
    }

    protected void buyProduct(String itemName) {
    }

    protected void registerAtMarketplace(String name, String bankAccountName){
        try {
            this.marketplaceAccount = (MarketplaceAccount)
                    marketplace.registerCustomer(name, bankAccountName);
        } catch (RemoteException ex) {
            System.out.println("Remote call to method registerCustomer at"
                    + "MarketPlace failed.");
            ex.printStackTrace();
        } catch(RegisterCustomerException rcException)
        {
            System.out.println(rcException);
        }
       
    }

    protected void unregisterAtMarketplace() {
    }

    @Override
    public void notifySale(Item product) throws RemoteException {
    }

    @Override
    public void notifyWishAvailable(String itemName, float price) throws RemoteException {
    }
    
    public void command() {
        
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Hello Marketplace customer! Enter your name to "
                + "register: ");
        String customerName = in.nextLine();    
        
        System.out.println("Enter your Nordea account name ");
        String bankAccountName = in.nextLine();          
        
        try {
            MarketplaceClientImpl client = new MarketplaceClientImpl(customerName, bankAccountName);
            // Register the newly created object at rmiregistry.
            java.rmi.Naming.rebind(customerName, client);
            System.out.println(client + " is ready.");
            
            //TEST
            client.registerAtMarketplace(customerName, bankAccountName);
            //TEST                
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }     
}
