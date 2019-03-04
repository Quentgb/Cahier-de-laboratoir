
import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.io.*;
import java.util.ArrayList;


public class HelloClient {
	public static void main(String [] args) {
	
	  	try {
		  	if (args.length < 1) {
			  	System.out.println("Usage: java HelloClient <rmiregistry host>");
			  	return;
			}

		  	String host = args[0];
		  	// Get remote object reference
		  	Registry registry = LocateRegistry.getRegistry(host);

		    Factory_Itf f = (Factory_Itf) registry.lookup("ChatService");
		    Accounts_Itf a = (Accounts_Itf) registry.lookup("AccountsService");

		    String nomClient = login_register(a);

		  	if(nomClient.equals("quit"))
		  		System.exit(0);
		  	System.out.println("Login fait!");

		    menu(f, nomClient);

		    System.exit(0);

		  	} catch (Exception e)  {
		  		System.err.println("Error on client: " + e);
		  	}
  	}

  	static private String readConsole(){
  		BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in));
        String s=null;
        try{
        	while((s = stdIn.readLine()) == null){}
        
        } catch (Exception e)  {
		  		System.err.println("Error reading: " + e);
		}
        return s;

  	}

  	static private String login_register( Accounts_Itf a){

        String name="return";
        try{
			System.out.println("Que voulez vous faire :\nR -> s'enregistrer \nL -> se loguer\n quit-> pour quitter");
			while(name.equals("return")){
				while(((name=readConsole())==null) || (!name.equals("R") && !name.equals("L") && !name.equals("quit"))){}
				//Cas do Login
				if(name.equals("L")){
					System.out.println("Entrez votre nom :");
					while((((name=readConsole())==null)) || (!a.isAlreadyIn(name) && !name.equals("return"))){
		                if(!a.isAlreadyIn(name))
		                    System.out.println("Ce nom n'existe pas!\nTapez 'return' si vous voulez retournee au menu\n\nEntrez votre nom : ");
		            }
		            if(name.equals("return")){
		            	System.out.println("Que voulez vous faire :\nR -> s'enregistrer \nL -> se loguer\n quit-> pour quitter");
		            }else{
		            	String nom = name.replace(" ","_");
		            	System.out.println("Entrez votrez mot de passe ou 'return' pour retourner au menu : ");
		            	while((((name=readConsole())==null)) || (!a.passwordIsGood(nom,name) && !name.equals("return"))){
		            		System.out.println("ici");
			                if(!a.passwordIsGood(nom,name))
			                    System.out.println("Mot de passe incorrecte!\nTapez 'return' si vous voulez retournee au menu\n\nEntrez votre nom : ");
		            	}
		            	if(name.equals("return")){
		            		System.out.println("Que voulez vous faire :\nR -> s'enregistrer \nL -> se loguer\n quit-> pour quitter");
		            	}else{
		            		return nom;
		            	}
		            }
				}
				//Cas du register
				else if (name.equals("R")){
					System.out.println("Entrez votre nom :");
					while((((name=readConsole())==null)) || (a.isAlreadyIn(name) && !name.equals("return"))){
		                if(a.isAlreadyIn(name))
		                    System.out.println("Ce nom n'est pas disponible!\nTapez 'return' si vous voulez retournee au menu\n\nEntrez votre nom : ");
		            }
		            if(name.equals("return")){
		            	System.out.println("Que voulez vous faire :\nR -> s'enregistrer \nL -> se loguer\n quit-> pour quitter");
		            }
		            else{
		            	String nom = name.replace(" ","_");
		            	System.out.println("Entrez votrez mot de passe ou 'return' pour retourner au menu");
		            	while((((name=readConsole())==null)) || name.equals("return")){}
		            	if(name.equals("return")){
		            		System.out.println("Que voulez vous faire :\nR -> s'enregistrer \nL -> se loguer\n quit-> pour quitter");
		            	}else{
		            		a.registerNewAccount(nom,name);
		            		return nom;
		            	}
		            }
				}
				//cas ou l'on veut quitter
				else{
					return "quit";
				}
			}
		}catch(Exception e){
      		System.err.println("Error on reading: " + e);
    	}
		return "quit";
  	}

  	static private void menu(Factory_Itf f, String nom) throws RemoteException {
		Chat h2;

    	BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in));
        String name=null;
        try{
	        System.out.println("Choisissez quoi faire : \n create -> creer un nouveau serveur\n List -> montre la liste des serveurs \n Entrer <nom serveur> -> rejoins un serveur \n quit -> quitter");
	        
	        while((name = stdIn.readLine()) != null && !name.equals("quit")){

	            if((name.contains("Entrer") || name.contains("entrer")) && f.alreadyIn(name.substring(7))){
	                
	                System.out.println(name.substring(7));
	                h2 = f.join(name.substring(7));
	                Boolean ok = true;

	                if(h2.getPriv()){
		                System.out.println("Tapez 'retour' si vous voulez revenir au menu \nEntrez le mot de passe du Chat :");
		                while(((name=readConsole())==null) || (!h2.getMdp().equals(name) && !name.equals("retour"))){
		                    System.out.println("mot de passe erroné");
		                }
		                if(name.equals("retour"))
		                    ok=false;
		                if(h2.getMdp().equals(name))
		                    System.out.println("mot de passe validé");
	                }
	                if(ok){

	                    Broadcast_itfImpl acc = new Broadcast_itfImpl(nom);
	                    Broadcast_itf acc_stub = (Broadcast_itf) UnicastRemoteObject.exportObject(acc, 0);
	            
	                    System.out.println(h2.sayHello(nom,acc_stub));
	            
	                    chating(h2,nom);
	            
	                    System.out.println(h2.sayGoodBye(nom));  
	                }

	                System.out.println("Choisissez quoi faire : \n create -> creer un nouveau Chat\n List -> montre la liste des Chats \n Entrer <nom serveur> -> rejoins un serveur \n quit -> quitter");
	                
	            }
	            else if(name.equals("List")){

		            System.out.println("\nListe des Chats :");
		            ArrayList<String> l=f.getRooms();

		            for(int j =0; j<l.size();j++)
		                System.out.println(l.get(j));
		            System.out.println("\n");

	            }else if(name.equals("create")){

		            System.out.println("Entrez le nom de votre Chat");

		            while(((name=readConsole())==null) || f.alreadyIn(name)){System.out.println(name);}
		              
		            String nomServeur = name;
		            String modo = nom;
		            System.out.println("Voulez vous votre Chat privée? O/N ");

		            while((((name=readConsole())==null)) || (!name.equals("O") && !name.equals("N"))){}
		            
		            if(name.equals("N")){
		                h2=f.createChat(nomServeur,false,"",modo);
		                System.out.println("Chat créé!");
	            	}
					else{

	                	System.out.println("Entrez le mdp du Chat");
	                	while((name = stdIn.readLine())== null){}

	                	h2=f.createChat(nomServeur,true,name,modo);

	              	}
	          
	                Broadcast_itfImpl acc = new Broadcast_itfImpl(modo);
	                Broadcast_itf acc_stub = (Broadcast_itf) UnicastRemoteObject.exportObject(acc, 0);
	          
	                System.out.println(h2.sayHello(nom,acc_stub));
	          
	                chating(h2,nom);
	          
	                System.out.println(h2.sayGoodBye(nom));
	                System.out.println("Choisissez quoi faire : \n create -> creer un nouveau Chat\n List -> montre la liste des Chats \n Entrer <nom serveur> -> rejoins un Chat \n quit -> quitter");

	            }
	            else{}
	        }

	    }catch(Exception e){
      		System.err.println("Error on reading: " + e);
    	}

  }



  //Les options du Chat
  static private void chating(Chat h2, String i) throws RemoteException{

    BufferedReader stdIn =
          new BufferedReader(
              new InputStreamReader(System.in));

    Integer idMess=null;

    //Chat de client
    
    System.out.println("Pour quitter le programme, entrez 'exit' \nPour plus de Commandes : 'help'");
    String command = null;

    try{
      	while ((command = stdIn.readLine()) != null && !command.equals("exit")) {

	        //cas de message privé
	        if(command.indexOf("mp")==0 || command.indexOf("mp")==1){
		        
		        command = command.substring(command.indexOf("mp")+2);
		        
		        while(command.indexOf(" ")==0)
		            command = command.substring(command.indexOf(" ")+1);
		        
		        String nom = command.substring(0,command.indexOf(" "));
		        
		        while(command.indexOf(" ")==0)
		            command = command.substring(command.indexOf(" ")+1);
		        
		        command = command.substring(command.indexOf(" ")+1);

		        System.out.println("mp a ["+nom+"] : "+ command);
		        h2.sendPrivateMessage(i,nom,command);

	        }
	        //Cas affichage liste Users
	        else if(command.equals("show")){
	          
	          	System.out.println("liste des personnes connecté :");
		        ArrayList<String> l = h2.listUsers();

		        for(int j =0; j< l.size(); j++)
		        	if(!(l.get(j).equals(i)))
		            	System.out.println(l.get(j));
	          
	        }
	        //Cas help
	        else if(command.equals("help")){
		        System.out.println("\n\nexit -> Pour quitter le programme");
		        System.out.println("mp <nomde la persone> <votre message> -> Pour envoyer un message privé ");
		        System.out.println("show -> montre la liste des utilisateurs");
		        System.out.println("help -> montre la liste des commandes");
	        }
	        //Cas message vide
	        else if((command.replace(" ","")).equals("")){}
	        //envoie d'un message classique 
	        else{
	        	ArrayList<String> list = h2.listUsers();
				String listu = "[";
				for(int j =0; j<list.size(); j++){
					listu = listu+"\""+list.get(j)+"\"";
					if(j<list.size()-1)
						listu= listu+", ";
				}
				listu = listu+"]";
				idMess=h2.getIdMess();
	        	EcrireFile.ecrireDans("{\"id\" :\"BroadcastS\",\"temps\" : "+System.nanoTime()+",\"idMess\" : "+idMess+", \"client\" : \""+i+"\", \"message\" : \""+command+"\", \"listUsers\" : "+listu+"},","traces/messagetrace.json",true);
				h2.talk(i,command, idMess);
	        }
      	}
    }catch(Exception e){
      	System.err.println("Error on reading: " + e);
    }
  }
}
