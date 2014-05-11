import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
class BabyW{
	int s,e;
	BabyW(int s,int e){
		this.s=s;this.e=e;
	}
}

class Solver4{
	String text;
	int len;
	ArrayList<BabyW> li;
	int []f;
	void init(Scanner scan){
		text=scan.next();
		len=text.length();
		li=new ArrayList<BabyW>();
		f=new int[len+1];
		//System.out.println(verify(0,5));
	}
	boolean verify(int l,int r){
		int m=(l+r)/2;
		
		if(text.substring(l, m+1).equals( text.substring(m+1, r+1) )){
			return true;
		}else{ return false;}
	}
	void findBabyW(){
		for(int i=0;i<len;i++){
			for(int j=i+1;j<len;j+=2){
				if(verify(i,j)){
					//System.out.println("Tfrom "+i+" to "+j);
					li.add(new BabyW(i,j+1));
				}
			}
		}
		/*for(int j=0;j<li.size();j++){
			BabyW tmp=li.get(j);
			System.out.println("from "+tmp.s+" to "+tmp.e);
		}*/
	}
	int findMax(){
		int max=0;
		for(int i=0;i<=len;i++){
			f[i]=0;
			for(int j=0;j<li.size();j++){
				BabyW tmp=li.get(j);
				if(tmp.e==i){
					if(f[tmp.s]+tmp.e-tmp.s > f[i]){
						f[i]=f[tmp.s]+tmp.e-tmp.s;
					}
				}
			}
			if(f[i]>max){
				max=f[i];
			}
		}
		return max;
	}
	
}

public class ECOOF4 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(new File("src/DATA41.txt"));
		while(scan.hasNext()){
			Solver4 s=new Solver4();
			s.init(scan);
			s.findBabyW();
			System.out.println(s.findMax());
		}
	}

}
