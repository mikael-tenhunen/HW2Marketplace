package bankrmi.server;

import bankrmi.shared.Bank;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {    
	private static final String USAGE = "java bankrmi.Server <bank_rmi_url>";
	private static final String BANK = "Nordea";

        /**
         * Here we do some naming
         */
	public Server(String bankName) throws RemoteException, MalformedURLException {
		try {
                    LocateRegistry.getRegistry(1099).list();
                }
                catch (RemoteException e) {
                    System.out.println("will create registry");
                    LocateRegistry.createRegistry(1099); //IS THIS RIGHT?
                }
            
                try {
			Bank bankobj = new BankImpl(bankName);
			// Register the newly created object at rmiregistry.
//			java.rmi.Naming.rebind(bankName, bankobj);
                        Naming.rebind("rmi://localhost/" + bankName, bankobj);
			System.out.println(bankobj + " is ready.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
            if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
                    System.out.println(USAGE);
                    System.exit(1);
            }

            String bankName = null;
            if (args.length > 0) {
                    bankName = args[0];
            } else {
                    bankName = BANK;
            }
                
            try {
                new Server(bankName);
            } catch (RemoteException ex) {
                System.out.println("Couldn't start server");
            } catch (MalformedURLException urlEx) {
                System.out.println(urlEx);
            }
	}
}
