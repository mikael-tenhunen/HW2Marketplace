
package marketplace.server;

import java.util.Scanner;
import marketplace.client.MarketplaceClientImpl;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceClient;


public class Server {
    
    public static final String MARKETPLACENAME = "marketplacemaininstance";
    private Marketplace marketplace;
            
    /**
    * Here we do some naming
    */
    public Server() {
           try {
                   marketplace = new MarketplaceImpl();
                   // Register the newly created object at rmiregistry.
                   java.rmi.Naming.rebind(MARKETPLACENAME, marketplace);
                   System.out.println(marketplace + " is ready.");
           } catch (Exception e) {
                   e.printStackTrace();
           }
    } 
    
    public static void main(String[] args) {
        Server server = new Server();
    }    
}
