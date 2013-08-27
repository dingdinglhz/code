package sort;

import java.util.Random;
import java.util.Scanner;
import javax.swing.JTextArea;

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
	public void qsort(int l,int r){
		if(l<r){
			int rd=random0.nextInt(r)%(r-l+1)+l;
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
			qsort(l,i-2);
			qsort(i,r);
		}
	}
	public void input(){
        Scanner sc=new Scanner(System.in);
        random0=new Random();
		size=sc.nextInt();
		data=new int[size];
        for(int i=0;i<size;i++){
            data[i]=sc.nextInt();
        }
    }
    public void input(Scanner sc){
    	random0=new Random();
		size=sc.nextInt();
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
    public void showInTextArea(JTextArea jta){
    	for(int i=0;i<size;i++){
            jta.append(" "+data[i]);
        }jta.append("\n");
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
	public static void main(String[] args) {
		QsortB qsortB;
        qsortB = new QsortB();
        qsortB.input();
        qsortB.qsort(0, qsortB.size-1);
        qsortB.show();     
	}
}