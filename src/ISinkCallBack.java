package project408;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface ISinkCallBack extends Remote{

	public void waitForResponse() throws RemoteException;
	public void returnResponse(Packet p) throws RemoteException;
	
}
