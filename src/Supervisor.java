package project408;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Supervisor extends UnicastRemoteObject implements ISupervisor{

	Map<Integer, SensorDescriptors> sensorsList;
	Map<Integer, SensorDescriptors> HigherSensorsList;
	Map<Integer, SensorDescriptors> BasicSensorsList;
	
	public static IDataServer IDS;
	
	int idSink;
	ArrayList<Zone> zones;
	ISink sink;
	
	public Supervisor() throws RemoteException {
		super();
		sensorsList=new HashMap<>();
		Zone z=new Zone();
		z.createMainZones(); //allocates the zones array of z.zones
		zones=z.zones;
		
		//lookups data server: this.IDS=...
	}
	
	
	@Override
	public String registerSink(ISink sink) throws RemoteException {
		// TODO Auto-generated method stub
		String reply = "success";
		idSink=-1;
		this.sink=sink;
		return reply;
	}

	@Override
	public SensorDescriptors registerBasicSensor(ISensor iSensor) throws RemoteException {
		// TODO Auto-generated method stub
		SensorDescriptors sd=registerSensor();
		sd.selfRef=iSensor;
		sd.tier=1;
		sd.ChildrenCapacity=0;
		BasicSensorsList.put(BasicSensorsList.size(),sd);
		//allocate parent 
		allocateParent(sd);
		if(sd.parentId==-1)
			sd.parent=null;
		else
			sd.parent=HigherSensorsList.get(sd.parentId).selfRef;
		
		return sd;
	}
	
	@Override
	public SensorDescriptors registerHigherSensor(ISensor iSensor) throws RemoteException {
		// TODO Auto-generated method stub
		SensorDescriptors sd=registerSensor();
		sd.selfRef=iSensor;
		sd.tier=2;
		sd.ChildrenCapacity=2;
		HigherSensorsList.put(HigherSensorsList.size(), sd);
		//allocate parent 
		allocateParent(sd);
		if(sd.parentId==-1)
			sd.parent=null;
		
		for (SensorDescriptors key : HigherSensorsList.values()) {
			SensorDescriptors descriptors=key;
			descriptors.sameTierRef.add(sd.selfRef);
			updateSensor(descriptors);
		}
		
		
		return sd;
	}
	
	@Override
	public SensorDescriptors registerSensor() throws RemoteException {
		// TODO Auto-generated method stub
		int id;
		do {
			Random r=new Random();
			id =Math.abs(r.nextInt())%10000;
		}while(sensorsList.containsKey(id) || id==idSink);
		int[] resolution= {480,480};
		SensorDescriptors sd=new SensorDescriptors(id,true,30, resolution);
		sd.rmi="rmi://127.0.0.1:1234/Sensor"+Integer.toString(id);
		sensorsList.put(sensorsList.size(), sd);
		sd.sink=this.sink;
		sd.IDS=this.IDS;
		return sd;
	}
	
	public void allocateParent(SensorDescriptors sd) {
		/*first check if all primary zones (12 zones) are allocated, if not keep allocating them and 
		 * set sink as parent, else, create new sub-zones and allocate sensors to parents having parent zone
		 */
		if (sensorsList.size()<12) {
			sd.zone=zones.get(sensorsList.size()-1);
			sd.coveredZone.add(sd.zone);
			if(sd.tier==1) {//basic sensor
				if(HigherSensorsList.size()==0) {
					sd.parentId=-1; //set sink as parent
				}
				else { //higher tier exist
					boolean flag=false;
					for (SensorDescriptors key : HigherSensorsList.values()) {
						SensorDescriptors descriptors=key;
						if(descriptors.ChildrenCapacity>0)
						{
							sd.parentId=descriptors.id;
							descriptors.childrenList.add(sd);
							descriptors.coveredZone.addAll(sd.coveredZone);
							descriptors.childrenRef.add(sd.selfRef);
							sd.parent=descriptors.selfRef;
							updateSensor(descriptors);
							flag=true;
							break;
						}
					}
					if(flag==false) { //no parent can handle this basic sensor
						sd.parentId=-1;
					}
				}
			}
			if(sd.tier==2) {//higher sensor
				if(BasicSensorsList.size()==0) {
					sd.parentId=-1; //set sink as parent
				}
				else { //basic tier exist
					boolean flag=false;
					for (SensorDescriptors key : BasicSensorsList.values()) {
						SensorDescriptors descriptors=key;
						if(descriptors.parentId==-1) {
							sd.childrenList.add(descriptors);
							descriptors.parentId=sd.id;
							descriptors.coveredZone.addAll(sd.coveredZone);
							sd.childrenRef.add(descriptors.selfRef);
							descriptors.parent=sd.selfRef;
							updateSensor(descriptors);
							flag=true;
							break;
						}
					}
					if(flag==false) {
						sd.parentId=-1;
					}
				}
			}
		}
		else {
			//primary 12 zones allocated with 12 sensors
			//generate random zone
			Zone z=new Zone();
			z.setRandomZone(zones.size());
			sd.zone=z;
			sd.coveredZone.add(sd.zone);
			if(sd.tier==1) {//basic sensor
				if(HigherSensorsList.size()==0) {
					sd.parentId=-1; //set sink as parent
				}
				else { //higher tier exist
					boolean flag=false;
					for (SensorDescriptors key : HigherSensorsList.values()) {
						SensorDescriptors descriptors=key;
						if(descriptors.ChildrenCapacity>0)
						{
							sd.parentId=descriptors.id;
							descriptors.childrenList.add(sd);
							descriptors.coveredZone.addAll(sd.coveredZone);
							descriptors.childrenRef.add(sd.selfRef);
							sd.parent=descriptors.selfRef;
							updateSensor(descriptors);
							flag=true;
							break;
						}
					}
					if(flag==false) { //no parent can handle this basic sensor
						sd.parentId=-1;
					}
				}
			}
			if(sd.tier==2) {//higher sensor
				if(BasicSensorsList.size()==0) {
					sd.parentId=-1; //set sink as parent
				}
				else { //basic tier exist
					boolean flag=false;
					for (SensorDescriptors key : BasicSensorsList.values()) {
						SensorDescriptors descriptors=key;
						if(descriptors.parentId==-1) {
							sd.childrenList.add(descriptors);
							descriptors.parentId=sd.id;
							sd.coveredZone.addAll(descriptors.coveredZone);
							sd.childrenRef.add(descriptors.selfRef);
							descriptors.parent=sd.selfRef;
							updateSensor(descriptors);
							flag=true;
							break;
						}
					}
					if(flag==false) {
						sd.parentId=-1;
					}
				}
			}
		}

	}
		

	public boolean updateSensor(SensorDescriptors descriptors) {
		//tell the sensor
		sink.addSensor(descriptors);
		//tell the sink (needs to know all about sensors)
		
		return false;
	}

	
	public int modifySensorZone(Zone needed) {
		for (SensorDescriptors key : sensorsList.values()) {
			SensorDescriptors descriptors=key;
			if(descriptors.zone.contains(needed))
			{
				return 1;
			}
		}
		return 0;
	}
	
	public static void main(String args[]) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			Supervisor supervisor= new Supervisor();;
			String name_supervisor="rmi://127.0.0.1:1234/project/supervisor";
			Naming.rebind(name_supervisor, supervisor);
			IDS = (IDataServer) Naming.lookup("rmi://127.0.0.1:1234/project/dataServer");
		}catch(Exception e) {
			System.err.println("exception: "+e.getMessage());
			e.printStackTrace();
		}
	}

}
