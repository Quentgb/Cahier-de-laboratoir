import java.rmi.*;
import java.io.*;

public interface Accounts_Itf extends Remote {

	public void registerNewAccount(String name, String mdp) throws RemoteException;

	public boolean passwordIsGood(String name, String pwd) throws RemoteException;

	public boolean isAlreadyIn(String name) throws RemoteException;
	
}