package project408;

import java.util.ArrayList;

public class SensorDescriptors{
	public int id;
	public String rmi;
	public boolean idle;
	public Zone zone;
	public ArrayList<Zone> coveredZone;
	public int capacity;
	public int ChildrenCapacity;
	public ArrayList<SensorDescriptors> childrenList;
	public int[] resolution;
	public int tier;
	public int parentId;
	public ISensor selfRef;
	
	public ISink sink;
	public IDataServer IDS;
	public ArrayList<ISensor> childrenRef;
	public ArrayList<ISensor> sameTierRef;
	public ISensor parent;
	
	public SensorDescriptors(int id,boolean idle, int capacity, int[] resolution)
	{
		childrenList=new ArrayList<>();
		coveredZone=new  ArrayList<>();
		this.id=id;
		this.idle=idle;
		this.capacity=capacity;
		this.resolution=resolution;
	}
	
	public boolean coversPoint(int x, int y) {
		for(int i=0; i<coveredZone.size();i++) {
			if(coveredZone.get(i).containsPoint(x,y))
				return true;
		}
		return false;
	}
	
}
