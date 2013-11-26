package marketplace.shared;

import bankrmi.shared.RejectedException;
import bankrmi.shared.Account;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Marketplace extends Remote {
    public void addProduct(Item product) throws RemoteException;
    public void buyProduct(Item product) throws RemoteException;
    public void registerCustomer(String customerName) throws RemoteException;
    public void unregisterCustomer(String customerName) throws RemoteException;
    public void deposit(float value, Account account) throws RemoteException, RejectedException;
    public void withdraw(float value, Account account) throws RemoteException, RejectedException;  
}
