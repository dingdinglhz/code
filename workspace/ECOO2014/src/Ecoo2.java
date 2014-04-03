import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Character;
class Solver{
	String code;
	int len;
	boolean bina[];
	boolean binb[];
	String ans;
	boolean valid(char ch){
		if(ch==' '){return true;}
		if((int)ch<=90 && (int)ch>=65){
			return true;
		}
		return false;
	}
	Solver(String code){
		this.code=code;
		this.len=code.length();
		bina= new boolean[code.length()];
		binb= new boolean[code.length()];
		for(int i=0;i<code.length();++i){
			if(code.charAt(i)=='A' || code.charAt(i)=='T'){
				bina[i]=true;
				binb[i]=false;
				continue;
			}
			if(code.charAt(i)=='C' || code.charAt(i)=='G'){
				binb[i]=true;
				bina[i]=false;
				continue;
			}
		}
	}
	/*public void printBin(){
		for(int i=0;i<len;i++){
			System.out.print(bina[i]+" ");
		}
		System.out.println();
		for(int i=0;i<len;i++){
			System.out.print(binb[i]+" ");
		}
		System.out.println();
	}*/
	public boolean checka(int offset){
		String str="";
		for(int i=offset;i<len;i+=8){
			int tmp=0;
			if(i+7>len){break;}
			for(int j=0;j<8;j++){
				if(bina[i+j]){
					tmp+=1<<(7-j);
					//System.out.println(tmp);
				}
			}
			char c=(char)tmp;
			if(valid(c)){
				str+=c;
				
			}
			else{
				return false;
			}
		}
		ans=str;
		return true;
	}
	public boolean checkb(int offset){
		String str="";
		for(int i=offset;i<len;i+=8){
			int tmp=0;
			if(i+7>len){break;}
			for(int j=0;j<8;j++){
				if(binb[i+j]){
					tmp+=1<<(7-j);
					//System.out.println(tmp);
				}
			}
			char c=(char)tmp;
			if(valid(c)){
				str+=c;
			}
			else{
				return false;
			}
		}
		ans=str;
		return true;
	}
	public String decode(){
		for(int i=0;i<8;i++){
			if(checka(i)){
				return ans;
			}
			if(checkb(i)){
				return ans;
			}
		}
		return "";
	}
}
public class Ecoo2 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("src/DATA21.txt"));
		Solver solver;
		while(sc.hasNext()){
			String tmp=sc.nextLine();
			sc.nextLine();
			solver=new Solver(tmp);
			//solver.printBin();
			System.out.println(solver.decode());
			
		}
	}

}
