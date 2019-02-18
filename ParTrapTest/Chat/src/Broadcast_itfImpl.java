
import java.rmi.*;

public  class Broadcast_itfImpl implements Broadcast_itf {

	public Broadcast_itfImpl(){};
	public void receiveMessage(String m) throws RemoteException{
		System.out.println(m);	
	}
}

