package marketplace.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import marketplace.shared.Item;
import marketplace.shared.Marketplace;
import marketplace.shared.MarketplaceAccount;
import marketplace.shared.MarketplaceClient;
import marketplace.shared.RegisterCustomerException;

public class MarketplaceClientImpl extends UnicastRemoteObject implements MarketplaceClient{
    
    private Marketplace marketplace;
    private String bankAccountName;
    private String name;
    public MarketplaceAccount marketplaceAccount;
    private static String MARKETPLACENAME = "Marketplace";
    
    static enum CommandName {
		buy, offer, list, register, unregister, wish, quit, help;
	};
    
    public MarketplaceClientImpl() throws RemoteException {
        try {
                marketplace = (Marketplace)Naming.lookup("marketplacemaininstance");
//                System.out.println("Client connected to Marketplace");  
//                java.rmi.Naming.rebind(customerName, client);
        } catch (Exception e) {
                System.out.println("Failed connecting to Marketplace: " 
                        + e.getMessage());
                System.exit(0);
        }        
    }    
    
//    public MarketplaceClientImpl(String name, String bankAccountName) throws RemoteException {
//        this.name = name;
//        
//        try {
//                marketplace = (Marketplace)Naming.lookup("marketplacemaininstance");
//        } catch (Exception e) {
//                System.out.println("The runtime failed: " + e.getMessage());
//                System.exit(0);
//        }
//        System.out.println("Client connected to Marketplace");        
//    }
    
    private class Command {
            private String arg1;
            private String arg2;
            private CommandName commandName;

            private String getArg1() {
                    return arg1;
            }

            private String getArg2() {
                    return arg2;
            }

            private CommandName getCommandName() {
                    return commandName;
            }

            private Command(MarketplaceClientImpl.CommandName commandName, String arg1, String arg2) {
                    this.commandName = commandName;
                    this.arg1 = arg1;
                    this.arg2 = arg2;
            }
    }
    
    /**
     * After example in bankrmi.Client
     */
    public void run() {        
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(name + "@" + MARKETPLACENAME + ">");
            try {
                String userInput = consoleIn.readLine();
                execute(parse(userInput));
    //			}catch (RejectedException re) {
    //				System.out.println(re);				
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * After example in bankrmi.Client
     * 
     * @param userInput
     * @return 
     */
    private Command parse(String userInput) {
        if (userInput == null) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer(userInput);
        if (tokenizer.countTokens() == 0) {
            return null;
        }
        CommandName commandName = null;
        String arg1 = null;
        String arg2 = null;
        int userInputTokenNo = 1;

        while (tokenizer.hasMoreTokens()) {
            switch (userInputTokenNo) {
                case 1:
                    try {
                        String commandNameString = tokenizer.nextToken();
                        commandName = CommandName.valueOf(CommandName.class, commandNameString);
                    } catch (IllegalArgumentException commandDoesNotExist) {
                        System.out.println("Illegal command");
                        return null;
                    }
                    break;
                case 2:
                    arg1 = tokenizer.nextToken();
                    break;
                case 3:
                    arg2 = tokenizer.nextToken(); //Float.parseFloat(tokenizer.nextToken());
                    break;
                default:
                    System.out.println("Illegal command");
                    return null;
            }
            userInputTokenNo++;
        }
        return new Command(commandName, arg1, arg2);
    }  
    
    /**
     * After example in bankrmi.Client
     * 
     * @param command
     * @throws RemoteException
     */
    void execute(Command command) throws RemoteException {
        if (command == null) {
            return;
        }

        switch (command.getCommandName()) {
            case list:
                try {
                    for (Item item : marketplace.listItems()) {
                        System.out.println(item.getName() + ", " 
                                + item.getPrice());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                return;
            case unregister:
                unregisterAtMarketplace();
                return;
            case quit:
                System.exit(1);
            case help:
                for (CommandName commandName : CommandName.values()) {
                    System.out.println(commandName);
                }
                return;
        }

        // all further commands require a first command argument
        String arg1 = command.getArg1();
//        if (arg1 == null) {
//            arg1 = name;
//        }
//
//        if (userName == null) {
//            System.out.println("name is not specified");
//            return;
//        }

//        switch (command.getCommandName()) {
//            case newAccount:
//                clientname = userName;
//                bankobj.newAccount(userName);
//                return;
//            case deleteAccount:
//                clientname = userName;
//                bankobj.deleteAccount(userName);
//                return;
//        }

        // all further commands require a second command argument
        String arg2 = command.getArg2();
//        Account acc = bankobj.getAccount(userName);
//        if (acc == null) {
//            System.out.println("No account for" + userName);
//            return;
//        } else {
//            account = acc;
//            clientname = userName;
//        }
        
        switch (command.getCommandName()) {
            case register:
                registerAtMarketplace(arg1, arg2);
                break;
            case offer:
                try {
                    String productName = arg1;
                    float price = Float.valueOf(arg2);
                    offerProduct(productName, price);
                } catch (Exception ne) {
                    System.out.println("Problem with second argument: should be"
                            + "floating point number");
                }
                break;
            case buy:
                try {
                    String productName = arg1;
                    float price = Float.valueOf(arg2);
                    buyProduct(productName, price);
                    System.out.println("Purchase successful. $" + price 
                                        + " has been withdrawn from your account.");
                } catch (Exception ne) {
                    System.out.println("Problem with second argument: should be"
                            + "floating point number");
                }                
                break;
            case wish:
                try {
                    String productName = arg1;
                    float price = Float.valueOf(arg2);
                    makeWish(productName, price);
                } catch (Exception ne) {
                    System.out.println("Problem with second argument: should be"
                            + "floating point number");
                }   
                break;
            default:
                System.out.println("Illegal command");
        }
    }   
    
    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
    
    protected void offerProduct(String itemName, float price) {
        try {
            marketplaceAccount.addProduct(itemName, price);
        } catch (RemoteException ex) {
            System.out.println("Problem creating sale");
        }
    }

    protected void buyProduct(String itemName, float price) {
        try {
            Item product = null;
            List<Item> items = marketplace.listItems();
            for (Item item : items) {
                if (item.getName().equals(itemName)
                    && item.getPrice() == price) {
                    product = item;
                }
            }
            if (product != null) {
                marketplaceAccount.buyProduct(product);
            }
            else {
                System.out.println("No such item available");
            }
            
        } catch (RemoteException ex) {
            System.out.println("Problem creating sale");
        }
    }
    
    protected void makeWish(String itemName, float maxPrixe) {
        try {
            marketplaceAccount.addWish(itemName, maxPrixe);
        } catch (RemoteException ex) {
            System.out.println("Problem sending wish to Marketplace");
        }
    }

    protected void registerAtMarketplace(String name, String bankAccountName) {
        try {
            this.name = name;
            this.bankAccountName = bankAccountName;
            System.out.println("Client connected to Marketplace");  
            java.rmi.Naming.rebind(name, this);
            this.marketplaceAccount = (MarketplaceAccount)
                    marketplace.registerCustomer(name, bankAccountName);
        } catch (RemoteException ex) {
            System.out.println("Remote call to method registerCustomer at"
                    + "MarketPlace failed.");
            ex.printStackTrace();
        } catch (RegisterCustomerException rcException) {
            System.out.println(rcException);
        } catch (MalformedURLException urlException) {
            System.out.println(urlException);
        }
    }

    protected void unregisterAtMarketplace() {
        try {
            marketplace.unregisterCustomer(name);
        } catch (RemoteException ex) {
            System.out.println("Something went wrong with unregister.");
        }
    }

    @Override
    public void notifySale(String productName, float price) throws RemoteException {
        System.out.println("A buyer has been found for your " + productName + ". $" 
                            + price +" has been deposited to your account.");
    }

    @Override
    public void notifyWishAvailable(String itemName, float price) throws RemoteException {
    }
    
//    public void command() {
//        System.out.println();
//        
//        
//        
//        
//        
//        
//        System.out.println("Hello Marketplace customer! Enter your name to "
//                + "register: ");
//        String customerName = in.nextLine();    
//        
//        System.out.println("Enter your Nordea account name ");
//        String bankAccountName = in.nextLine();          
//        
//        try {
//            
//            // Register the newly created object at rmiregistry.
//            java.rmi.Naming.rebind(customerName, client);
//            System.out.println(client + " is ready.");
//            
//            //TEST
//            client.registerAtMarketplace(customerName, bankAccountName);
//            //TEST                
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//    }
    
    
    public static void main(String[] args) {
        try {
            MarketplaceClientImpl client = new MarketplaceClientImpl();
            client.run();
        } catch (RemoteException ex) {
            System.out.println("Problem in main method");
            ex.printStackTrace();
        }
    }     
}
