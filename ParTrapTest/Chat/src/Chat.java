import java.rmi.*;
import java.io.*;
import java.util.ArrayList;

public interface Chat extends Remote {	
	//enregistre le nouvel utilisateur dans le Chat
	public String sayHello(String client, Broadcast_itf clientAcc) throws RemoteException;

	//envoie le message d'un client a tout le monde
	public void talk(String client, String s, Integer idMess) throws RemoteException,IOException;

	//supprime le nom du client de la Hashtable
	public String sayGoodBye(String client) throws RemoteException;	

	//verifie si un nom n'est pas deja utilisé dans la table de Hashage
	public boolean alreadyIn(String nom) throws RemoteException;
	
	//envoie un message privé me de la part de exp a rec
	public void sendPrivateMessage(String exp, String rec, String me )throws RemoteException;
	
	//renvoie la liste des utilisateurs connectés au chat
	public ArrayList<String> listUsers() throws RemoteException;

	public Boolean getPriv() throws RemoteException;

	public String getMdp() throws RemoteException;

	public String getName() throws RemoteException;

	public Integer getIdMess() throws RemoteException;

}
