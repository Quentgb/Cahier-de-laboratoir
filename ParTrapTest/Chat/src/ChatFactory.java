import java.rmi.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.io.*;
import java.util.ArrayList;
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatFactory implements Factory_Itf{

	private Hashtable<String,Chat> rooms;
	private Registry registry;
	private Hashtable<String,Chat> rooms_stub;

	public ChatFactory(Registry registry) throws RemoteException,IOException{
		rooms = new Hashtable<>();
		rooms_stub = new Hashtable<>();
		String name= "default";
		ChatImpl c = new ChatImpl(name, name);
		rooms.put(name,c);
		Chat c_stub = (Chat) UnicastRemoteObject.exportObject(c, 0);
		rooms_stub.put(name,c_stub);
		this.registry=registry;
	}

	public synchronized Chat createChat(String name, boolean priv, String mdp, String moderateur)throws RemoteException,IOException{
		if(priv){
			PrivateChatImpl c = new PrivateChatImpl(name, mdp, moderateur);
			rooms.put(name,c);
			Chat c_stub = (Chat) UnicastRemoteObject.exportObject(c, 0);
			rooms_stub.put(name,c_stub);
			return c_stub;
		}
		ChatImpl c = new ChatImpl(name, name);
		rooms.put(name,c);
		Chat c_stub = (Chat) UnicastRemoteObject.exportObject(c, 0);
		rooms_stub.put(name,c_stub);
		return c;
	}

	public synchronized ArrayList<String> getRooms() throws RemoteException{
		ArrayList<String> l = new ArrayList<>();

		Set<String> keys = rooms.keySet();
		Iterator<String> itr = keys.iterator();

		while(itr.hasNext()) {
        	String element = itr.next();
        	String p="public";
        	if(rooms.get(element).getPriv())
        		p="privée";
        	l.add(element + " : " + p);

      	}
      	return l;
	}

	public synchronized Chat join(String n) throws RemoteException{
		Set<String> keys = rooms_stub.keySet();
		Iterator<String> itr = keys.iterator();

		Chat c=null;

		while(itr.hasNext()) {
        	String element = itr.next();
        	if(element.equals(n))
        		c=rooms_stub.get(n);

      	}
      	return c;
	}

	//verifie si un nom n'est pas deja utilisé dans la table de Hashage
	public synchronized boolean alreadyIn(String nom) throws RemoteException{
		if(!(rooms.containsKey(nom)))
			return false;
		else
			return true;
	}
}