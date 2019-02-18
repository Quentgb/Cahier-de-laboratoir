import java.rmi.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Accounts_ItfImpl implements Accounts_Itf {

	private  Hashtable<String, Info_itf> accounts;

	public Accounts_ItfImpl(){
		accounts = new Hashtable<>();
	}

	public synchronized void registerNewAccount(String name, String mdp) throws RemoteException{
		Info_itfImpl i = new Info_itfImpl(name,mdp);
		accounts.put(name,i);
	}

	public synchronized boolean passwordIsGood(String name, String pwd) throws RemoteException{

		Set<String> keys = accounts.keySet();
		Iterator<String> itr = keys.iterator();
      	String element ="";

		while(itr.hasNext() && !element.equals(name)) {
        	element = itr.next();
      	}
      	if(element.equals(name) && accounts.get(element).passwordIsGood(pwd)){
      		return true;
      	}
      	return false;
	}

	public  synchronized boolean isAlreadyIn(String name) throws RemoteException{
		if(!(accounts.containsKey(name)))
			return false;
		else
			return true;
	}
}


