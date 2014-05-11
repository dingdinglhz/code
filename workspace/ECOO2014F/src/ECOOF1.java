import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Solver1{
	int kd=0;
	private String original;
	private String decrypted;
	boolean check(int k){
		for(int i=0;i<original.length();i++){
			char oc=original.charAt(i);
			char dc=decrypted.charAt(i);
			if( charToInt(dc)*k%67 != charToInt(oc)){
				return false;
			}
		}
		return true;
	}
	int charToInt(char c){
		int tmp=(int)c;
		if(tmp<='Z' && tmp>='A'){
			return tmp-'A'+1;
		}
		if(tmp<='z' && tmp>='a'){
			return tmp-'a'+27;
		}
		if(tmp<='9' && tmp>='0'){
			return tmp-'0'+53;
		}
		if(tmp==' '){return 63;}
		if(tmp=='.'){return 64;}
		if(tmp==','){return 65;}
		if(tmp=='?'){return 66;}
		return 0;
	}
	char intToChar(int i){
		if(i<=26 && i>=1){
			return (char) ('A'-1+i);
		}
		if(i<=52 && i>=27){
			return (char) ('a'-27+i);
		}
		if(i<=62 && i>=53){
			return (char) ('0'-53+i);
		}
		if(i==63){return ' ';}
		if(i==64){return '.';}
		if(i==65){return ',';}
		if(i==66){return '?';}
		return 0;
	}
	void findKd(Scanner scan){
		original = scan.nextLine();
		original=original.substring(1,original.length()-1);
		decrypted = scan.nextLine();
		decrypted=decrypted.substring(1,decrypted.length()-1);
		//System.out.println(original);
		//System.out.println(decrypted);
		for(int i=2;i<=66;i++){
			if(check(i)){
				kd=i;
				return;
			}
		}
	}
	String decrypt(Scanner scan){
		String tmp = scan.nextLine();
		tmp=tmp.substring(1,tmp.length()-1);
		String ans="";
		for(int i=0;i<tmp.length();i++){
			int m=charToInt(tmp.charAt(i))*kd%67;
			ans+=intToChar(m);
		}
		return ans;
	}
}

public class ECOOF1 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(new File("src/DATA11.txt"));
		while (sc.hasNext()){
			Solver1 s=new Solver1();
			s.findKd(sc);
			String tmp="*"+s.decrypt(sc)+"*";
			System.out.println(tmp);
		}
	}

}
