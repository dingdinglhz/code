import java.util.Scanner;


public class EightRooksPuzzle {
	
	int n;//side length
	int[] rooks;
	boolean[] columns;
	private static Scanner scan;
	void printSolution(){
		//printing / debugging
		for(int i=1;i<=n;i++){
			for(int j=1;j<rooks[i];j++){
				System.out.print('-');
			}
			if(rooks[i]>0){System.out.print('R');}
			for(int j=rooks[i]+1;j<=n;j++){
				System.out.print('-');
			}
			System.out.println();
		}
		System.out.println("***********************");
	}
	void dfs(int steps){
		//deepth first search
		printSolution();
		scan.next();
		if(steps>n){
			printSolution();
			return;
			}
		for(int i=1;i<=n;i++){
			if(columns[i]){
				columns[i]=false;
				rooks[steps]=i;
				
				dfs(steps+1);
				
				rooks[steps]=0;
				columns[i]=true;
			}
		}
		
	}
	void start(){
		//start the first step
		dfs(1);
	}
	EightRooksPuzzle(int size){
		//initializing
		n=size;
		rooks=new int[n+1];
		columns=new boolean[n+1];
		for(int i=1;i<=n;i++){
			rooks[i]=0;
			columns[i]=true;
		}
	}
	
	public static void main(String[] args) {
		//main method
		scan = new Scanner(System.in);
		int size=scan.nextInt();
		EightRooksPuzzle solver= new EightRooksPuzzle(size);
		solver.start();

	}

}
