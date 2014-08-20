package hz.lin.sparkiMapper;

import java.util.Scanner;
import java.util.Vector;

public class MapData {
    public double currentX, currentY, currentAng;
    boolean positionChanged;
    public Vector<SingleReading> readings;
    MapData() {
        readings = new Vector<SingleReading>();
        positionChanged=true;
        currentX = 0;
        currentY = 0;
        currentAng = Math.PI / 2.0;
    }
    MapData(double x, double y, double ang) {
    	readings = new Vector<SingleReading>();
    	positionChanged=true;
        currentX = x;
        currentY = y;
        currentAng = ang;
    }
    public void ChangePos(double x, double y, double ang) {
    	positionChanged=true;
        currentX = x;
        currentY = y;
        currentAng = ang;
    }
    public synchronized void move(double dist) {
    	positionChanged=true;
        currentX+=dist*Math.cos(currentAng);
        currentY+=dist*Math.sin(currentAng);
    }
    public synchronized void rotate(double rad) {//rotate clockwise, in radians
    	positionChanged=true;
    	currentAng -= rad;
        while (currentAng <-Math.PI) {
            currentAng += 2*Math.PI;
        }
        while (currentAng > Math.PI) {
            currentAng -=2*Math.PI;
        }
    }
    public void rotateDeg(double deg) {//rotate clockwise, in degrees
    	rotate(deg/180.0*Math.PI);
    }
    public synchronized void newDistance(double ang, double dis, double err){
    	if(positionChanged){
    		readings.add(new SingleReading(currentX,currentY,currentAng));
    		positionChanged=false;
    	}
    	readings.lastElement().distances.add(new SingleDistance(ang,dis,err));
    }
    public void loadFromScanner(Scanner scan){
    	String command;
    	double tmpA,tmpB,tmpC;
    	while(scan.hasNextLine()){
    		command=scan.next();
    		//System.out.print(command);
    		if(!scan.hasNextLine()){
    			break;
    		}
    		if(command.equals("A")){
    			tmpA=scan.nextDouble();
    			tmpB=scan.nextDouble()/2; //Remember, the distance is doubled, so it has to be divided by 2!!!!
    			tmpC=scan.nextDouble();
    			newDistance(tmpA,tmpB,tmpC);
    		}else if(command.equals("M")){
    			tmpA=scan.nextDouble();
    			move(tmpA);
    		}else if(command.equals("T")){
    			tmpA=scan.nextDouble();
    			rotateDeg(tmpA);
    		}
    	}
    }
    public void debug(){
    	System.out.println("Current position- X: "+currentX+" Y:"+currentY+" Ang: "+currentAng);
    	for (int i = 0; i < readings.size(); i++) {
			SingleReading reading = readings.get(i);
			System.out.println("Position# "+i+" - X: "+reading.x+" Y:"+reading.y+" Ang: "+reading.ang);
    		for(SingleDistance dist : reading.distances){
    			System.out.println("Ang: "+dist.ang+" Dis: "+dist.dis+" Err: "+dist.err);
    		}
		}
    }
}
