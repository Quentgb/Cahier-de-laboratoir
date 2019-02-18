
import java.rmi.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.io.*;
import java.util.ArrayList;

public class PrivateChatImpl implements Chat {

 	private Hashtable<String,Broadcast_itf> clients;
 	private String historique;
 	private String lastTalker;
 	private String nameRoom;
 	private Boolean priv; 
 	private String mdp;
 	private String moderateur;

	public PrivateChatImpl(String nameRoom, String mdp, String moder) throws IOException{
		clients=new Hashtable<>();
		historique=lireHistorique(nameRoom);
		lastTalker="";
		this.nameRoom=nameRoom;
		priv=true;
		this.mdp=mdp;
		moderateur=moder;
	}

	public Boolean getPriv() throws RemoteException{
		return this.priv;
	}

	public String getMdp() throws RemoteException{
		return this.mdp;
	}

	public String getName() throws RemoteException{
		return this.nameRoom;
	}

	public String getModo() throws RemoteException{
		return moderateur;
	}

	//recupere l'istorique dans un fichier texte et renvoie la String de celle-ci
	static public synchronized String lireHistorique(String nameRoom) throws IOException{
		File fichier = new File("historique/"+nameRoom+"historique.txt");
 
		if (! fichier.exists())
		{
		  fichier.createNewFile();
		}


		BufferedReader b = new BufferedReader(new FileReader("historique/"+nameRoom+"historique.txt"));
		String s = "";
		String res = "";
		Integer suiv;
		//limitation de l'affichage de l'historique on affiche que les 1000 dernier characteres 
		//(environ car une ligne ne sera pas coupé)
		while((s=b.readLine())!=null){
			res=res+s+"\n";
			if(res.length() > 1000){
				suiv = res.indexOf("\n");
				res = res.substring(suiv+1);
			}
		}
		b.close();
		return res;
	}

	//verifie si un nom n'est pas deja utilisé dans la table de Hashage
	public synchronized boolean alreadyIn(String nom) throws RemoteException{
		if(!(clients.containsKey(nom)))
			return false;
		else
			return true;
	}

	//enregistre le nouvel utilisateur dans le Chat
	public synchronized String sayHello(String client,Broadcast_itf clientAcc) throws RemoteException {
		
		if(!(clients.containsKey(client)))
			clients.put(client,clientAcc);
		//normalement impossible car deja vérifié
		else
			return "Ce nom est deja pris!";

		Set<String> keys = clients.keySet();
		Iterator<String> itr = keys.iterator();
      
		while(itr.hasNext()) {
        	String element = itr.next();
        	if(!element.equals(client))
        		(clients.get(element)).receiveMessage(client + " rejoint le Chat");

      	}
		return  historique + "\nVous etes maintenant connecte au chat ! ";
	}

	//envoie le message d'un client a tout le monde 
	public synchronized void talk(String client, String s) throws RemoteException,IOException {
		PrintWriter p = new PrintWriter(new FileWriter("historique/"+this.nameRoom+"historique.txt",true));
		String tailleNom="";
		Boolean newC ;
		//cas dans lequel le dernier a avoir envoyé un message est celui qui a renvoyé 
		//un message et donc on evite de reafficher son nom
		if(lastTalker.equals(client)){
        	for(int j = 0;j<client.length()+3;j++)
        		tailleNom = tailleNom+" ";
        	historique = historique + tailleNom + s +"\n";
        	p.println(tailleNom + s);
        	newC=false;
        }else{
        	newC=true;
			historique = historique + client + " : " + s +"\n";
			//on sauve le commentaire du client dans l'historique
			p.println(client + " : " + s);
		}
		p.close();
		Integer suiv; 
		//limitation de l'affichage de l'historique on affiche que les 1000 dernier characteres 
		//(environ car une ligne ne sera pas coupé)
		if(historique.length() > 1000){
			suiv = historique.indexOf("\n");
			historique = historique.substring(suiv+1);
		}
		Set<String> keys = clients.keySet();
		Iterator<String> itr = keys.iterator();
      
		while(itr.hasNext()) {
        	String element = itr.next();
        	if(lastTalker.equals(client) && !newC){
        		(clients.get(element)).receiveMessage(tailleNom + s);
        	}
        	else{
        		(clients.get(element)).receiveMessage(client + " : " + s);
        		lastTalker = client;
        	}

      	}
      	
	}

	//envoie un message privé me de la part de exp a rec
	public synchronized void sendPrivateMessage(String exp, String rec, String me )throws RemoteException{

		Set<String> keys = clients.keySet();
		Iterator<String> itr = keys.iterator();
      
		while(itr.hasNext()) {
        	String element = itr.next();
        	if(element.equals(rec))
        		(clients.get(element)).receiveMessage("mp de [" + exp +"] : "+me);

      	}
	}

	//supprime le nom du client de la Hashtable
	public synchronized String sayGoodBye(String client) throws RemoteException {
		clients.remove(client);

		Set<String> keys = clients.keySet();
		Iterator<String> itr = keys.iterator();

		while(itr.hasNext()) {
        	String element = itr.next();
        	(clients.get(element)).receiveMessage(client + " a quitté le Chat");

      	}
		return  "Vous etes maintenant deconnecte du chat ! ";
	}

	//renvoie la liste des utilisateurs connectés au chat
	public synchronized ArrayList<String> listUsers() throws RemoteException{
		ArrayList<String> l = new ArrayList<>();

		Set<String> keys = clients.keySet();
		Iterator<String> itr = keys.iterator();

		while(itr.hasNext()) {
        	String element = itr.next();
        	l.add(element);

      	}
      	return l;
	}

}
