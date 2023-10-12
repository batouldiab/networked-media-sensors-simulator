package project408;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ThreadStreaming extends Thread{

	public IDataServer ids;
	ArrayList<String> image;
	InetAddress host;
	int PORT;
	Zone z;
	DatagramSocket ds;
	DatagramPacket inPacket,outPacket;
	public ThreadStreaming(IDataServer ids,ArrayList<String> image, Zone z) throws RemoteException, UnknownHostException {
		this.ids=ids;
		this.z=z;
		this.image=image;
		host=InetAddress.getLocalHost();
		PORT = ids.startSending(host,z);
	}
	public void run() {
		while(true) {
			try {
				
				DatagramSocket ds=new DatagramSocket(PORT);
				byte[] buffer=new byte[256];
				DatagramPacket inPacket=new DatagramPacket(buffer, buffer.length);
				ds.receive(inPacket);
				String response=new String(inPacket.getData(),0,inPacket.getLength());
				image.add(response);		
				ds.close();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
