import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ECOO2 {

	private static Scanner scan;
	public static ArrayList<Integer> facgen(int j){
		ArrayList<Integer> solution = new ArrayList<Integer>();
		for(int i = 1; i<= j; i++){
			if (j%i == 0){
				solution.add(i);
			}
		}
		return solution;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		scan = new Scanner(new File("src/DATA22.txt"));
		while(scan.hasNext()){
			System.out.println(solve());
		}
	}
	public static String solve(){
		String ans="";
		int n=scan.nextInt();
		ArrayList<Integer> factors=facgen(n);
		
		for(int i=0;i<5;i++){
			int tmp=0;
			int r,c;
			r=scan.nextInt();
			c=scan.nextInt();
			for(int j=0;j<factors.size();j++){
				//System.out.print(factors.get(j));
				tmp+=(r-1)/factors.get(j);
				tmp+=(c-1)/factors.get(j);
				tmp+=1;
			}
			if(tmp%2==0){
				ans+="G";
			}else{
				ans+="B";
			}
		}
		return ans;
	}

}
