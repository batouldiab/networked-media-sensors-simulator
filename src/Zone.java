package project408;

import java.util.ArrayList;
import java.util.Random;

public class Zone {

	//these 3 bellow variables are declared here and not in the supervisor for facilitating any need to change
	public int fullWidth=200;
	public int fullHeight=150;
	public int leastSensorsNb=12;

	public int x;
	public int y;
	public int height;
	public int width;
	public int id;
	
	public ArrayList<Zone> zones; //to handle array of zones to the main zones
	
	public Zone() {
		super();
	}
	
	public void createMainZones() { //these function is declared here and not in the supervisor for facilitating any need to change
		int x=(int) Math.sqrt(leastSensorsNb);
		while(this.leastSensorsNb%x!=0)//x is not a divisor of the least sensors number
		{
			x--;
		}
		int y=leastSensorsNb/x;
		zones=new ArrayList<>();
		int id = 0;
		for(int j=0; j<y; j++) {
			for(int i=0; i<x; i++) {
				zones.add(new Zone(id,i*(fullWidth/x),j*(fullHeight/y),(fullWidth/x),(fullHeight/y)));
				id++;
			}
		}
	}
	
	public Zone(int id, int x, int y, int height, int width) {
		this.id=id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public Zone(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String getZone() {
		return Integer.toString(x)+" "+Integer.toString(y)+" "+Integer.toString(width)+" "+Integer.toString(height);
	}
	
	public boolean contains(Zone z) {
		if (z.x>=this.x && z.x<=(this.x+width))
			if(z.y>=this.y && z.y<=(this.y+height))
				return true;
		return false;
	}
	
	public boolean containsPoint (int x, int y) {
		if (x>=this.x && x<=(this.x+width))
			if(y>=this.y && y<=(this.y+height))
				return true;
		return false;
	}
	
	public void setRandomZone(int id) { //only id isn't random
		Random r=new Random();
		x=Math.abs(r.nextInt())%fullWidth-1;
		y=Math.abs(r.nextInt())%fullHeight-1;
		width=Math.abs(r.nextInt())%fullWidth-x;
		height=Math.abs(r.nextInt())%fullHeight-y;
		this.id=id;
	}
	
	public ArrayList<Zone> mainZones(){
		ArrayList<Zone> zones=new ArrayList<>();
		int id = 0; 
		for(int y=0; y<3; y++) {
			for(int x=0; x<4; x++) {
				zones.add(new Zone(id,x*50,y*50,50,50));
				id++;
			}
		}
		return null;
		
	}
}
