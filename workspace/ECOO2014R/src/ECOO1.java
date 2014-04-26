import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ECOO1 {

	private static Scanner scan;
	static final String[] match={"?","$1","$2","$5","$10","$50","$100","$1000","$10000","$500000","$1000000"};
	static int[] num;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		scan = new Scanner(new File("src/DATA12.txt"));
		while(scan.hasNext()){
			input();
			System.out.println(solve());
		}
	}
	static void input(){
		num=new int[11];
		for(int i=0;i<9;i++){
			String tmp=scan.next();
			for(int j=0;j<11;j++){
				if(tmp.equals(match[j])){
					num[j]+=1;
				}
			}
		}
	}
	static String solve(){
		String ans="";
		for(int i=1;i<11;i++){
			if (num[i]>=3){
				return match[i];
			}
		}
		if(num[0]==1){
			for(int i=1;i<11;i++){
				if (num[i]>=2){
					ans+=match[i]+" ";
				}
			}
			//
		}
		else if(num[0]==2){
			for(int i=1;i<11;i++){
				if (num[i]>=1){
					ans+=match[i]+" ";
				}
			}
			//return ans;
		}else if(num[0]>=3){
			for(int i=1;i<11;i++){
				ans+=match[i]+" ";
			}
			//return ans;
		}
		if(ans.equals("")){
			return "No Prizes Possible";
		}else{
		return ans;
		}
	}

}
