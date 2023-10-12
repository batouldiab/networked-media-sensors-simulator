package project408;

import java.rmi.RemoteException;
import java.util.Map;

public class SensorWaiter extends Thread{
	Map<Integer, Zone> z;
	Map<Integer, SensorDescriptors> s;
	ISinkCallBack me;
	String img;
	public SensorWaiter(Map<Integer,Zone> z, Map<Integer, SensorDescriptors> s, ISinkCallBack me, String img) {
		this.s=s;
		this.z=z;
		this.me= me;
		this.img= img;
	}
	
	public void run() {
		SinkCallBack mycallBack;
		try {	
			mycallBack = new SinkCallBack(s.size());
			Packet p=new Packet(mycallBack);
			for(int k=0;k<s.size();k++) {
				p.z=z.get(k);
				s.get(k).selfRef.receive(p);
			} 
			mycallBack.waitForResponse();
			mycallBack.image.add(img);
			Packet rp= new Packet(mycallBack,mycallBack.image);
			me.returnResponse(rp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
