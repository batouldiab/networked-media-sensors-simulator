package project408;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ISink extends Remote{
	void migrate(Packet p) throws RemoteException;
	String register(ISink sink) throws RemoteException;
	void addSensor(SensorDescriptors descriptors);
}