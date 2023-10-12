package project408;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sink extends UnicastRemoteObject implements ISink{

	int id;
	static String registryReply="";
	ISupervisor supervisor;
	Map<Integer, SensorDescriptors> sensorsList=null;
	static Map<Integer, SensorDescriptors> connectedSensorsList= new HashMap<Integer, SensorDescriptors>();
	static int zoneId=0;
	
	
	protected Sink() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void migrate(Packet p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String register(ISink sink) throws RemoteException {
		registryReply=supervisor.registerSink(sink);
		return registryReply;
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String args[]) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			//registration
			Sink s=new Sink();
			registryReply= s.register(s);
			while (!registryReply.equals("success")){
				Thread.sleep(1000);
				registryReply= s.register(s);
			}
			System.out.println("Sink Succesfully registered!\n");
			int x,y,width,height;
			Scanner input=new Scanner(System.in);
			//get new request
			while(true)
			{
				System.out.println("Enter the zone to request:\n");
				System.out.println("x:\n");
				x=input.nextInt();
				System.out.println("y:\n");
				y=input.nextInt();
				System.out.println("width:\n");
				width=input.nextInt();
				System.out.println("height:\n");
				height=input.nextInt();
				Zone z=new Zone(zoneId,x,y,width,height);
				zoneId++;
				sendRequest(z);
				Thread.sleep(100);

			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	@Override
	public void addSensor(SensorDescriptors sd) {
		sensorsList.put(sensorsList.size(), sd);
		if(sd.parentId==-1)
			connectedSensorsList.put(sensorsList.size(), sd);
	}

	
	private static void sendRequest(Zone z) throws IOException, NotBoundException{
		// TODO Auto-generated method stub
		int i,j,k;
		boolean covered=false;
		Map<Integer, SensorDescriptors> coveringSensors = new HashMap<Integer, SensorDescriptors>();
		Map<Integer, Zone> coveringZones = new HashMap<Integer, Zone>(); //corresponding to coveringSensors
		//end of width:
		int xEnd=z.x+z.width;
		//end of height 7
		
		int yEnd=z.y+z.height;
		
		for (j=z.y; j<yEnd; j++) {
			for (i=z.x; i<xEnd; i++) {
				covered=false;
				if(!covered) { //not found in the sensors already set to be covering this zones so i must find the new sensor to cover
					for (SensorDescriptors key : connectedSensorsList.values()) {
						SensorDescriptors descriptors=key;
						if (descriptors.coversPoint(i, j)) {
							coveringSensors.put(coveringSensors.size(), descriptors);
							
							coveringZones.put(coveringZones.size(), new Zone(0,0,0,0));
							covered=true; 
							coveringZones.get(coveringZones.size()-1).x=i;
							coveringZones.get(coveringZones.size()-1).y=j;
							
							if(descriptors.zone.width<xEnd-i)
							{
								coveringZones.get(coveringZones.size()-1).width=descriptors.zone.width;
								i=i+descriptors.zone.width;
							}else {
								coveringZones.get(coveringZones.size()-1).width=xEnd-i;
								i=xEnd;
							}
							
							if(descriptors.zone.height<yEnd-j)
							{
								coveringZones.get(coveringZones.size()-1).height=descriptors.zone.height;
								j=j+descriptors.zone.height;
							}else {
								coveringZones.get(coveringZones.size()-1).height=yEnd-j;
								j=yEnd;
							}
							break;
						}
					}
				}
				
			}
		}
		//corresponding sensors with targeted zone are allocated above in the arrays
		//now send to each sensor in coveringSensors the corresponding zone from coveringZones (same hash key integer)
		SinkWaiter s =new SinkWaiter(coveringZones,coveringSensors);
		s.start();
	}
	
	

}
