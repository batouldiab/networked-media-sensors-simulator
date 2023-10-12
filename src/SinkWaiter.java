package project408;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Map;

public class SinkWaiter extends Thread{

	
	Map<Integer, Zone> z;
	Map<Integer, SensorDescriptors> s;
	
	public SinkWaiter(Map<Integer,Zone> z, Map<Integer, SensorDescriptors> s) {
		this.s=s;
		this.z=z;
	}
	
	public void run() {
		SinkCallBack me;
		try {	
			me = new SinkCallBack(s.size());
			for(int k=0;k<s.size();k++) {
				Packet p=new Packet(me);
				p.z=z.get(k);
				s.get(k).selfRef.receive(p);
			}
			me.waitForResponse();

			for(int k=0;k<me.image.size();k++) {
				System.out.println(me.image.get(k));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
