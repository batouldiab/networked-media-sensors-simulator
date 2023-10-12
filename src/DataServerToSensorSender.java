package project408;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DataServerToSensorSender extends Thread{

	InetAddress a;
	int port;
	Zone z;
	
	public DataServerToSensorSender(InetAddress a,int port,Zone z) {
		this.a=a;
		this.z= z;
		this.port=port;
	}
	
	
	public void run() {
		while(true) {
			
			try {
				DatagramSocket ds=new DatagramSocket(port);
				byte[] buffer=new byte[256];
				DatagramPacket dp=new DatagramPacket(buffer,buffer.length);
				ds.receive(dp);
				InetAddress clientAddress = dp.getAddress();
				int clientPort=dp.getPort();
				//String msg=new String(dp.getData(),0,dp.getLength());
				String response="l badak yeh" + z.toString() ;
				DatagramPacket outputPacket=new DatagramPacket(response.getBytes(),response.length(),clientAddress,clientPort);
				ds.send(outputPacket);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			try {
				sleep(34);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
