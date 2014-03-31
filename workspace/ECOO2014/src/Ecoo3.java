import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Solver3{
	static final int moveX[]={0,1,1,1,0,-1,-1,-1};
	static final int moveY[]={1,1,0,-1,-1,-1,0,1};
	char map[][];
	boolean taken[][];
	int nr,nc,nw;
	String dict[];
	boolean valid(char ch){
		if((int)ch<=90 && (int)ch>=65){
			return true;
		}
		return false;
	}
	void init(Scanner sc){
		nr=sc.nextInt();
		nc=sc.nextInt();
		sc.nextLine();
		map=new char[nr][nc];
		taken=new boolean[nr][nc];
		for (int i=0; i<nr; i++) {
			String tmp=sc.nextLine();
			for (int j=0; j<nc; j++) {
				map[i][j]=tmp.charAt(j);
				
				taken[i][j]=false;
			}
		}
		nw=sc.nextInt();
		sc.nextLine();
		dict=new String[nw];
		for (int i=0; i<nw; i++) {
			String tmp=sc.nextLine();
			String tmpb="";
			for(int j=0;j<tmp.length();j++){
				if(valid(tmp.charAt(j))){
					tmpb+=tmp.charAt(j);
				}
			}
			dict[i]=tmpb;

		}
	}
	boolean check(int x,int y,int index, int direct){
		for (int i=0;i<dict[index].length() ;i++) {
			if(x+moveX[direct]*i >=nr || x+moveX[direct]*i <0){return false;}
			if(y+moveY[direct]*i >=nc || y+moveY[direct]*i <0){return false;}
			if(map[x+moveX[direct]*i][y+moveY[direct]*i] == dict[index].charAt(i)){
				continue;
			}
			else{return false;}
		}
		return true;
	}
	void crossOut(int x,int y,int index, int direct){
		for (int i=0;i<dict[index].length() ;i++) {
			taken[x+moveX[direct]*i][y+moveY[direct]*i]=true;
		}
	}
	void searchByWord(int index){
		for (int i=0; i<nr; i++) {
			for (int j=0; j<nc; j++) {
				for(int k=0;k<8;k++){
					if(check(i,j,index,k)){
						crossOut(i,j,index,k);
					}
				}
			}
		}
	}
	void search(){
		for (int i=0; i<nw; i++) {
			searchByWord(i);
		}
	}
	String ans(){
		String tmp="";
		for (int i=0; i<nr; i++) {
			for (int j=0; j<nc; j++) {
				if(!taken[i][j]){
					tmp+=map[i][j];
				}
			}
		}
		return tmp;
	}
}
public class Ecoo3 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(new File("src/DATA31.txt"));
		Solver3 solver=new Solver3();
		while(sc.hasNext()){
			solver.init(sc);
			solver.search();
			System.out.println(solver.ans());
		}
	}

}
