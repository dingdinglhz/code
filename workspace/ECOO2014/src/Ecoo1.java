import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

public class Ecoo1 {
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File("src/DATA11.txt"));
		DecimalFormat df = new DecimalFormat ("00");
		
		//
		double secondse;
		double secondsm;
		
		final double RATIO = 86400/88642.663;
		
		for(int i =0;i<10;i++){
			int secondseI = sc.nextInt() * 86400+ sc.nextInt() * 3600 + sc.nextInt()*60;
			//System.out.println(secondseI);
			//secondsm = secondse * RATIO;
			//System.out.println(secondsm);
			//int secondsmI=(int)secondsm;
			int secondsmI=(int) ((secondseI+37*60+22.663)*RATIO);
			
			
			//int secondsmI=secondseI*86400000/88642663;
			int day = secondsmI/86400;
			
			//int hour = (int) ((secondsm - day*86400)/3600);
			int hour=secondsmI%86400/3600;
			//System.out.println((secondsm - day*86400 - hour*3600));
			int mins=secondsmI%3600/60;
			//int mins = (int) ((secondsm - day*86400 - hour*3600)/60);
			
		
			
			
			System.out.println("Day " + day + ", " + df.format(hour) + ":" + df.format(mins));
					
					
			
		}
		
		

	}

}
