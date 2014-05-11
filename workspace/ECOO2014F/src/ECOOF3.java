import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Scanner;

/*class TelePort{
	int x,y;
	TelePort(){
		x=0;y=0;
	}
	TelePort(int x,int y){
		this.x=x;
		this.y=y;
	}
}*/
class Node{
	int x,y,step;
	Node(){
		x=0;y=0;step=0;
	}
	Node(int x,int y){
		this.x=x;
		this.y=y;
	}
	boolean equals(Node b){
		return this.x==b.x && this.y==b.y;
	}
}
class Solver3{
	int h=0,v=0; //h:x v:y
	int m;
	boolean visited[][];
	Node tele[][];
	static final int direcX[]={-1, 0, 1, 0};
	static final int direcY[]={ 0,-1, 0, 1};
	Node start,end;
	void init(Scanner scan){
		h=scan.nextInt();
		v=scan.nextInt();
		
		visited=new boolean[h][v];
		tele= new Node[h][v];
		
		start=new Node();
		end = new Node();
		start.x=scan.nextInt();
		start.y=scan.nextInt();
		end.x  =scan.nextInt();
		end.y  =scan.nextInt();
		m=scan.nextInt();
		for(int i=0;i<m;i++){
			int x1,y1,x2,y2;
			x1=scan.nextInt();
			y1=scan.nextInt();
			x2=scan.nextInt();
			y2=scan.nextInt();
			tele[x1][y1]=new Node(x2,y2);
			tele[x2][y2]=new Node(x1,y1);
		}
		/*for(int i=0;i<h;i++){
			for(int j=0;j<v;j++){
				System.out.print(visited[i][j]);
			}
			System.out.println();
		}*/
	}
	
	int BFS(){
		ArrayDeque<Node> q=new ArrayDeque<Node>();
		q.addLast(start);
		visited[start.x][start.y]=true;
		while(!q.isEmpty()){
			/*System.out.println("si="+q.size()+"  ");
			Object[] na=q.toArray();
			for(int i=0;i<q.size();i++){
				Node ttt=(Node)na[i];
				System.out.println("x="+ttt.x+" y="+ttt.y+" step="+ttt.step);
			}*/
			Node tmp=q.getFirst();
			if(tele[tmp.x][tmp.y]!=null){
				int step=tmp.step;
				tmp=tele[tmp.x][tmp.y];
				if(visited[tmp.x][tmp.y]==false){
					tmp.step=step;
					q.addFirst(tmp);
					visited[tmp.x][tmp.y]=true;
				}
			}
			tmp=q.removeFirst();
			if(tmp.equals(end)){
				return tmp.step;
			}
			for(int i=0;i<4;i++){
				int tmpX=tmp.x+direcX[i];
				int tmpY=tmp.y+direcY[i];
				if(tmpX<h && tmpX >=0 && tmpY<v && tmpY>=0 && visited[tmpX][tmpY]==false){
					Node newN=new Node(tmpX,tmpY);
					newN.step=tmp.step+1;
					q.addLast(newN);
					visited[tmpX][tmpY]=true;
				}
			}
			
			
		}
		return -1;
	}
}
public class ECOOF3 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(new File("src/DATA32.txt"));
		while(scan.hasNext()){
			Solver3 s=new Solver3();
			s.init(scan);
			System.out.println(s.BFS());
		}
	}

}
