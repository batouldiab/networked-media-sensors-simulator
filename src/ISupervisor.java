package project408;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISupervisor extends Remote{

	public String registerSink(ISink sink) throws RemoteException;
	public SensorDescriptors registerSensor() throws RemoteException;
	public int modifySensorZone(Zone needed);
	public SensorDescriptors registerHigherSensor(ISensor iSensor) throws RemoteException;
	SensorDescriptors registerBasicSensor(ISensor iSensor) throws RemoteException;
} 
