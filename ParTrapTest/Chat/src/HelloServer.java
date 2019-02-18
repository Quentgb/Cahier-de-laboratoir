
import java.rmi.*; 
import java.util.Hashtable;
import java.rmi.server.*; 
import java.rmi.registry.*;

public class HelloServer {

  public static void  main(String [] args) {
	  try {

	  	// Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry(); 
	    
		// Create a Hello remote object
	    ChatFactory f = new ChatFactory(registry);
	    Factory_Itf f_stub = (Factory_Itf) UnicastRemoteObject.exportObject(f, 0);

	    Accounts_ItfImpl a = new Accounts_ItfImpl();
	    Accounts_Itf a_stub = (Accounts_Itf) UnicastRemoteObject.exportObject(a, 0);
	    
	    registry.rebind("ChatService", f_stub);
	    registry.rebind("AccountsService", a_stub);
	    

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
