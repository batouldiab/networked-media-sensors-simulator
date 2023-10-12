package project408;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BasicSensor implements ISensor{
	int id;
	static SensorDescriptors registryReply=null;
	ISupervisor supervisor;
	Map<Integer, SensorDescriptors> sensorsList=null;
	static int zoneId=0;
	static ArrayList<String> image;
	Map<Integer, SensorDescriptors> coveringSensors = new HashMap<Integer,SensorDescriptors>();
	Map<Integer, Zone> coveringZones = new HashMap<Integer, Zone>(); //corresponding to coveringSensors
	
	protected BasicSensor() throws RemoteException, MalformedURLException, NotBoundException {
		super();
		image =new ArrayList<String>();
		supervisor = (ISupervisor) Naming.lookup("supervisor");
		// TODO Auto-generated constructor stub
	}


	public void migrate(Packet p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	public SensorDescriptors register(BasicSensor s) throws RemoteException {
		registryReply=supervisor.registerBasicSensor(s);
		return registryReply;
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String args[]) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			//registration
			BasicSensor s=new BasicSensor();
			registryReply= s.register(s);
			while(registryReply==null)
			{
				Thread.sleep(1000);
				s=new BasicSensor();
				registryReply= s.register(s);
			}
			System.out.println("BasicSensor Succesfully registered!\n");
			
			ThreadStreaming ts=new ThreadStreaming(registryReply.IDS,image,registryReply.zone);
			ts.start();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}


	@Override
	public void receive(Packet p) {
		// TODO Auto-generated method stub
		SensorWaiter s =new SensorWaiter(coveringZones,coveringSensors,p.me,image.get(0));
		s.start();
	}

}
