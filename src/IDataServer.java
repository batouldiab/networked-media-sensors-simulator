package project408;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDataServer extends Remote {

	public  int  startSending(InetAddress a, Zone z) throws RemoteException;
}
