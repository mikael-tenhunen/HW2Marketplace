
package marketplace.server;

import java.util.Scanner;
import marketplace.client.MarketplaceClientImpl;
import marketplace.shared.MarketplaceClient;


public class Server {
    /**
    * Here we do some naming
    */
    public Server(String bankName) {
           try {
                   Marketplace marketplace = new MarketplaceImpl(bankName);
                   // Register the newly created object at rmiregistry.
                   java.rmi.Naming.rebind(bankName, bankobj);
                   System.out.println(bankobj + " is ready.");
           } catch (Exception e) {
                   e.printStackTrace();
           }
    } 
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Hello Marketplace customer! Enter your name to "
                + "log in: ");
        String customerName = in.nextLine();
        
        MarketplaceClient client = new MarketplaceClientImpl(customerName);
        
        
    }    
}
