
import java.rmi.*;
import java.io.*;

public  class Broadcast_itfImpl implements Broadcast_itf {

	private String c;

	public Broadcast_itfImpl(String client){c=client;};
	public void receiveMessage(String m) throws RemoteException{
		System.out.println(m);	
	}
}

