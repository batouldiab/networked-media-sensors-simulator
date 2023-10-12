package project408;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DataServer extends UnicastRemoteObject implements IDataServer {
	int port;
	public DataServer() throws RemoteException {
		super();
		port = 1000;
	}
	
	
	
	public int startSending(InetAddress a, Zone z) {
		DataServerToSensorSender dt = new DataServerToSensorSender(a, port, z);
		dt.start();
		return port++;
	}
	
	
	public void main(String[] args) throws RemoteException, MalformedURLException {
		//implement the following in thread
		DataServer ds= new DataServer();
		Naming.rebind("rmi://127.0.0.1:1234/project/dataServer", ds);
		while(true);
			
	}

}
