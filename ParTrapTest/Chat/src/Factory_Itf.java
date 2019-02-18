import java.rmi.*;
import java.io.*;
import java.util.ArrayList;

public interface Factory_Itf extends Remote {

	public Chat createChat(String name, boolean priv, String mdp, String moderateur) throws RemoteException,IOException;

	public ArrayList<String> getRooms() throws RemoteException;

	//verifie si un nom n'est pas deja utilisé dans la table de Hashage
	public boolean alreadyIn(String nom) throws RemoteException;

	public Chat join(String n) throws RemoteException;

}