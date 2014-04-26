import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*class you{
	int x;
	int y;
	int dir = 0;//0 up 1 right 2 down 3 left
}*/

class solver{
	char given[][] = new char[6][6];
	boolean[][][] visited= new boolean[6][6][4];
	int x,y,o=0;//0 up 1 right 2 down 3 left
	boolean started=false;
	void input(Scanner scan){
		for(int i = 0; i < 6; i++){
			String temp = scan.next();
			for(int j =0; j< 6;j++){
				given[i][j] = temp.charAt(j);
				if(given[i][j]=='S'){
					y=i;x=j;
				}
			}
		}
		//visited = new boolean[6][6][4];
		for(int i = 0; i < 6; i++){
			for(int j =0; j< 6;j++){
				for(int k=0;k<4;k++){
					visited[i][j][k]=false;
				}
			}
		}
		/*for(int i = 0; i < 6; i++){
			for(int j =0; j< 6;j++){
				System.out.print(given[i][j]);
			}
			System.out.println();
		}*/
	}
	void move(char ch){
		switch(ch){
		case 'C':
			o=(o+1)%4;
			search();
			o=(o+4-1)%4;
			break;
		case 'B':
			o=(o+4-1)%4;
			search();
			o=(o+1)%4;
			break;
		case 'U':
			y--;
			search();
			y++;
			break;
		case 'D':
			y++;
			search();
			y--;
			break;
		case 'L':
			x--;
			search();
			x++;
			break;
		case 'R':
			x++;
			search();
			x--;
			break;
		}
	}
	void debug(){
		System.out.println("x:"+x+" y:"+y+" o:"+o+" visited?"+visited[y][x][o]);
	}
	void search(){//o:oreientation 0 up 1 right 2 down 3 left
		//debug();
		if(visited[y][x][o]){
			return;
		}
		visited[y][x][o]=true;
		if(given[y][x]=='.' || given[y][x]=='T'){
			return;
		}
		if(given[y][x]=='S'){
			if(started){return;}
			else{
				started=true;
				boolean hasU=false;
				for(int i=y-1;i>=1;i--){
					if(given[i][x]=='U'){
						hasU=true;
					}
				}
				if(hasU){move('U');}
				return;
			}
		}
		if(o==0){
			for(int i=y-1;i>=1;i--){
				move(given[i][x]);
			}
		}
		else if(o==1){
			for(int i=x+1;i<=4;i++){
				move(given[y][i]);
			}
		}
		else if(o==2){
			for(int i=y+1;i<=4;i++){
				move(given[i][x]);
			}
		}
		else if(o==3){
			for(int i=x-1;i>=1;i--){
				move(given[y][i]);
			}
		}
	}
	String solve(){
		String ans="";
		search();
		for(int i = 0; i < 6; i++){
			for(int j =0; j< 6;j++){
				if(given[i][j]=='T'){
					boolean flag=false;
					for(int k=0;k<4;k++){
						if(visited[i][j][k]){
							flag=true;
						}
					}
					if(flag){
						ans+="T";
					}
					else{
						ans+="F";
					}
					
				}
			}
		}
		return ans;
	}
}


public class ECOO4 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("src/DATA41.txt"));
		while(scan.hasNext()){
			solver s = new solver();
			s.input(scan);
			System.out.println(s.solve());
		}
		
	}

}
