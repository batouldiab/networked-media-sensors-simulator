package project408;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HigherSensor implements ISensor{


	int id;
	static SensorDescriptors registryReply=null;
	ISupervisor supervisor;
	Map<Integer, SensorDescriptors> sensorsList= new HashMap<Integer, SensorDescriptors>();
	static ArrayList<SensorDescriptors> connectedSensorsList=null;
	static int zoneId=0;
	static ArrayList<String> image;
	
	
	protected HigherSensor() throws RemoteException, MalformedURLException, NotBoundException {
		super();
		supervisor = (ISupervisor) Naming.lookup("supervisor");
		image = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}


	public void migrate(Packet p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	public SensorDescriptors register(HigherSensor s) throws RemoteException {
		registryReply=supervisor.registerHigherSensor(s);
		return registryReply;
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String args[]) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			//registration
			HigherSensor s=new HigherSensor();
			registryReply= s.register(s);
			while(registryReply==null)
			{
				Thread.sleep(1000);
				s=new HigherSensor();
				registryReply= s.register(s);
				connectedSensorsList=registryReply.childrenList;
			}
			System.out.println("HigherSensor Succesfully registered!\n");
			ThreadStreaming ts =new ThreadStreaming(registryReply.IDS,image,registryReply.zone);
			ts.start();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}


	@Override
	public void receive(Packet p) {

		Zone z=p.z;
		// TODO Auto-generated method stub
				int i,j,k;
				boolean covered=false;
				Map<Integer, SensorDescriptors> coveringSensors = new HashMap<Integer,SensorDescriptors>();
				Map<Integer, Zone> coveringZones = new HashMap<Integer, Zone>(); //corresponding to coveringSensors
				//end of width:
				int xEnd=z.x+z.width;
				//end of height 
				int yEnd=z.y+z.height;
				
				for (j=z.y; j<yEnd; j++) {
					for (i=z.x; i<xEnd; i++) {
						covered=false;
						if(registryReply.zone.containsPoint(i, j))
						{
							coveringSensors.put(coveringSensors.size(), registryReply);
							
							coveringZones.put(coveringZones.size(), new Zone(0,0,0,0));
							covered=true; 
							coveringZones.get(coveringZones.size()-1).x=i;
							coveringZones.get(coveringZones.size()-1).y=j;
							
							if(registryReply.zone.width<xEnd-i)
							{
								coveringZones.get(coveringZones.size()-1).width=registryReply.zone.width;
								i=i+registryReply.zone.width;
							}else {
								coveringZones.get(coveringZones.size()-1).width=xEnd-i;
								i=xEnd;
							}
							
							if(registryReply.zone.height<yEnd-j)
							{
								coveringZones.get(coveringZones.size()-1).height=registryReply.zone.height;
								j=j+registryReply.zone.height;
							}else {
								coveringZones.get(coveringZones.size()-1).height=yEnd-j;
								j=yEnd;
							}
							covered=true;
						}
						if(!covered) { //not found in the sensors already set to be covering this zones so i must find the new sensor to cover
							for (SensorDescriptors key : connectedSensorsList) {
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
				SensorWaiter s =new SensorWaiter(coveringZones,coveringSensors,p.me,image.get(0));
				s.start();
	}

	
}

