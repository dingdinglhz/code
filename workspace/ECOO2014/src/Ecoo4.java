import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Solver4{
	final static char dict[]={'K','Q','R','B','N'};
	char seq[];
	int ans=0;
	Solver4(String br){
		seq= new char[8];
		for (int i=0;i<8; i++) {
			seq[i]=br.charAt(i);
		}
	}
	int getAns(){
		return ans;
	}
	void check(){
		String tmp=new String(seq);
		int count[]=new int[5];
		for (int i=0; i<8; i++) {
			for(int j=0;j<5;j++){
				if(seq[i]==dict[j]){
					count[j]+=1;
				}
			}
		}
		if (count[0]!=1 || count[1]!= 1 || count[2]!=2 || count[3]!=2 || count[4]!=2) {
			return;
		}
		if((tmp.lastIndexOf('B')-tmp.indexOf('B'))%2!=1){
			return;
		}

		if ((tmp.lastIndexOf('R')>tmp.indexOf('K')) && (tmp.indexOf('R')<tmp.indexOf('K') )) {
			ans+=1;
		}else{
			return;
		}
	}
	void search(int i){
		if(i>=8){
			check();
			return;
		}
		if(seq[i]=='_'){
			for (int j=0; j<5; j++) {
				seq[i]=dict[j];
				search(i+1);
				seq[i]='_';
			}
		}else{
			search(i+1);
		}
	}
}
public class Ecoo4 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(new File("src/DATA41.txt"));
		Solver4 solver;
		while(sc.hasNext()){
			String tmp=sc.next();
			solver=new Solver4(tmp);
			solver.search(0);
			System.out.println(solver.getAns());
		}
	}

}
