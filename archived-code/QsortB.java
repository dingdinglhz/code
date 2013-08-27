import java.io.*;
import java.util.Random;
import java.util.Scanner;

class QsortB {
	public int data[];
	public int size;
	Random random0;
	public QsortB(){
		random0=new Random();
		size=0;
	}
	public QsortB(int in_n){
		random0=new Random();
		size=in_n;
		data=new int[size];
	}
	private int fmlpivot(int l,int r){
		int midpivot=(l+r)/2;
		if(data[l]>data[r] && data[r]>data[midpivot]){return r;}
		if(data[midpivot]>data[r] && data[r]>data[l]){return r;}
		if(data[r]>data[l] && data[l]>data[midpivot]){return l;}
		if(data[midpivot]>data[l] && data[l]>data[r]){return l;}
		return midpivot;
	}
	public int qsort(int l,int r){
		int rt=0;
		if(l<r){
			//int rd=random0.nextInt(r)%(r-l+1)+l;
			int rd=fmlpivot(l,r);
			int tmp=data[l];
			int pivot=data[rd];
			data[l]=pivot;
			data[rd]=tmp;
			int i=l+1;
			for (int j=l+1;j<=r;j++){
				if (data[j] < pivot) {
					tmp=data[j];
					data[j]=data[i];
					data[i]=tmp;
					++i;				
				}
			}
			tmp=data[l];
			data[l]=data[i-1];
			data[i-1]=tmp;
			rt+=qsort(l,i-2);
			rt+=qsort(i,r);
			rt+=r-l;
			
		}
		return rt;
	}
	public void input() throws FileNotFoundException, IOException
	{
        //Scanner sc=new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader("E:\\QuickSort.txt"));
        random0=new Random();
		//size=sc.nextInt();
		size=10000;
		data=new int[size];
        for(int i=0;i<size;i++){
            data[i]=sc.nextInt();
        }
    }
    public void show(){
        for(int i=0;i<size;i++){
            System.out.print(" "+data[i]);
        }
         System.out.println(" ");
    }
    public void show(int l,int r,int m_v,int st){
        switch(st){
            case -1: System.out.println("head:");    break;
            case 0:  System.out.println("mid:");     break;
            case 1:  System.out.println("tail:");    break;
            default: System.out.println("deafault"); break;
        }
        show();
        System.out.println(" l "+l+" r "+r+" m_v "+m_v);
    }
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		QsortB qsortB;
        qsortB = new QsortB();
        qsortB.input();
        System.out.println(qsortB.qsort(0, qsortB.size-1));
        //qsortB.show();
	}
}
/*
 17
13 2 9 4 6 8 8 8 12 5 10 11 11 11 1 7 3 
17
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
17
17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
 * 
 */