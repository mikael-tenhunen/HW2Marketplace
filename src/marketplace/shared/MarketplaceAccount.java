package marketplace.shared;

import bankrmi.shared.Account;
import bankrmi.shared.RejectedException;
import java.rmi.RemoteException;

public interface MarketplaceAccount {
    public void deposit(float value, Account account) throws RemoteException, RejectedException;
    public void withdraw(float value, Account account) throws RemoteException, RejectedException; 
    public void addProduct(Item product) throws RemoteException;
    public void buyProduct(Item product) throws RemoteException;
    public void addWish(String itemName, float maxPrice) throws RemoteException;
    public void notifyWishAvailable();
}
