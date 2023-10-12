package project408;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SinkCallBack extends UnicastRemoteObject implements ISinkCallBack {

	int i;
	ArrayList<String> image;
	
	protected  SinkCallBack(int i) throws RemoteException {
		super();
		this.i=i;
		image = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void waitForResponse() throws RemoteException {
		try {
			if(i!=0)
				wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void returnResponse(Packet p) throws RemoteException {
		// TODO Auto-generated method stub
		image.addAll(p.image);	
		i--;
		if(i==0)
			notify();
	}


}
