import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MineSolver{
	int[][] given;
	int[][] mine;//-1:empty 0:unknown 1:mine
	int n,unknownT;
	void input(Scanner scan){
		String tmp=scan.next();
		n=tmp.length();
		unknownT=n*n;
		given=new int[n][n];
		mine=new int[n][n];
		for(int j=0;j<n;j++){
			mine[0][j]=0;
			if(tmp.charAt(j)=='-'){
				given[0][j]=-1;
			}
			else{
				given[0][j]=tmp.charAt(j)-'0';
			}
		}
		for(int i=1;i<n;i++){
			tmp=scan.next();
			for(int j=0;j<n;j++){
				mine[i][j]=0;
				if(tmp.charAt(j)=='-'){
					given[i][j]=-1;
				}
				else{
					given[i][j]=(int)tmp.charAt(j)-'0';
				}
			}
		}
		//debug();
		
	}
	void debug(){
		System.out.println("given:");
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(given[i][j]==-1){
					System.out.print('-');
				}else{
					System.out.print(given[i][j]);
				}
			}
			System.out.println();
		}
		
	}
	int identify(int x,int y){
		if(x<0 || y<0 || x>=n || y>=n){
			return -1;//empty
		}
		else{
			return mine[x][y];
		}
	}
	void setMine(int x,int y){
		if(x<0 || y<0 || x>=n || y>=n){ return;}
		mine[x][y]=1; unknownT--;
	}
	void setEmpty(int x,int y){
		if(x<0 || y<0 || x>=n || y>=n){ return;}
		mine[x][y]=-1; unknownT--;
	}
	void checkGrid(int x,int y){
		int unknown=0,mineN=0;
		for(int i=x-1;i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++){
				if(identify(i,j)==0){
					unknown++;
				}
				else if(identify(i,j)==1){
					mineN++;
				}
			}
		}
		if(unknown == 0){return;}
		if(unknown+mineN == given[x][y]){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(identify(i,j)==0){
						setMine(i,j);
					}
				}
			}
			return;
		}
		if(mineN==given[x][y]){
			for(int i=x-1;i<=x+1;i++){
				for(int j=y-1;j<=y+1;j++){
					if(identify(i,j)==0){
						setEmpty(i,j);
					}
				}
			}
			return;
		}
	}
	void solve(){
		while(unknownT>0){
			//System.out.print(" "+unknownT);
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(given[i][j]!=-1){
						checkGrid(i,j);
					}
				}
			}
		}
	}
	void output(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(mine[i][j]==1){
					System.out.print('M');
				}else if(mine[i][j]==-1){
					System.out.print('.');
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
public class ECOO3 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(new File("src/DATA31.txt"));
		while(scan.hasNext()){
			MineSolver ms=new MineSolver();
			ms.input(scan);
			ms.solve();
			ms.output();
		}

	}

}
