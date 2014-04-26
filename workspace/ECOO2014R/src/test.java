import java.io.*;
import java.util.Scanner;

public class test {
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan=new Scanner(new File("src/test.txt"));
		while(scan.hasNext()){
			String tmp=scan.next();
			System.out.println(tmp);
			
			System.out.println(tmp.length());
			
			
		}

	}

}
