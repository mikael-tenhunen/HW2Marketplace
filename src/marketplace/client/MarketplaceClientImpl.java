package marketplace.client;

import bankrmi.client.Client;
import bankrmi.shared.Account;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import marketplace.shared.Item;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.MarketplaceClient;

public class MarketplaceClientImpl implements MarketplaceClient{
    
    private Marketplace marketplace;
    private Account bankAccount;
    private MarketplaceAccount marketplaceAccount;
    private String name;
    
    public MarketplaceClientImpl (String name) {
        this.name = name;
        
        try {
                marketplace = (Marketplace)Naming.lookup("marketplacemaininstance");
        } catch (Exception e) {
                System.out.println("The runtime failed: " + e.getMessage());
                System.exit(0);
        }
        System.out.println("Connected to bank Marketplace");        
    }

    private void offerProduct(String itemName, float price) {
    }

    private void buyProduct(String itemName) {
    }

    private void registerAtMarketplace() {
    }

    private void unregisterAtMarketplace() {
    }

    @Override
    public void notifySale(Item product) throws RemoteException {
    }

    @Override
    public void notifyWishAvailable(String itemName, float price) throws RemoteException {
    }
}
