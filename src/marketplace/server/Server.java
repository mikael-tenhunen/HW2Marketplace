
package marketplace.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import marketplace.shared.Marketplace;


public class Server {
    
    public static final String MARKETPLACENAME = "Marketplace";
    private Marketplace marketplace;
            
    /**
    * Here we do some naming
    */
    public Server() throws RemoteException {
            try {
                LocateRegistry.getRegistry(1099).list();
            }
            catch (RemoteException e) {
                System.out.println("will create registry");
                LocateRegistry.createRegistry(1099); //IS THIS RIGHT?
            }
        
           try {
                   marketplace = new MarketplaceImpl();
                   // Register the newly created object at rmiregistry.
                   java.rmi.Naming.rebind("rmi://localhost/" + MARKETPLACENAME, marketplace);
                   System.out.println(marketplace + " is ready.");
           } catch (Exception e) {
                   e.printStackTrace();
           }
    } 
    
    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (RemoteException ex) {
            System.out.println("Problem initializing server");
        }
    }    
}
