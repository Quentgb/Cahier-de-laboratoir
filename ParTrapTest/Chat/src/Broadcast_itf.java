
import java.rmi.*;

public interface Broadcast_itf extends Remote {

	//ecrit le message m sur le terminal
	public void receiveMessage(String m) throws RemoteException;
}
