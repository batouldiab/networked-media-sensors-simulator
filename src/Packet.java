package project408;

import java.util.ArrayList;

public class Packet implements IPacket{

	public ISinkCallBack me;
	public Zone z;
	public ArrayList<String> image;
	public Packet(ISinkCallBack me) {
		this.me=me;
		image=new ArrayList<String>();
	}
	public Packet(ISinkCallBack me, ArrayList<String> im) {
		this.me=me;
		image=new ArrayList<String>();
		image.addAll(im);
	}
	
}
