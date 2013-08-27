/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import java.util.Scanner;

/**
 *
 * @author linkb
 */
public class Quicksort {

    /**
     * @param args the command line arguments
     */
    public int[] data;
    public int size;
    //public int theta;

    Quicksort(int length) {
       data=new int[length];
       size=length;//theta=0;
    }

    private Quicksort() {
        size=0;//theta=0;
    }
    public void Quicksort(int qs_l){
        data=new int[qs_l];
        size=qs_l;//theta=0;
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
    
    public void  qsort(int l,int r){
        if(l<r){
        int i=l,j=r;
        int m_v=data[l];
        //show(l,r,m_v,-1);
        while(i<j){
            while(i<j && data[j]>=m_v){--j;}
            data[i]=data[j];//++theta;
            //show(i,j,m_v,0);
            while(i<j && data[i]<=m_v){++i;}
            data[j]=data[i];//++theta;
            //show(i,j,m_v,0);
            
        }
        data[i]=m_v;
        
        //show(i,j,m_v,1);
        
        qsort(l,i-1);
        qsort(i+1,r);
        }
        
    }
    public void input(){
        Scanner sc=new Scanner(System.in);
        Quicksort(sc.nextInt());
        for(int i=0;i<size;i++){
            data[i]=sc.nextInt();
        }
    }
    

    public static void main(String[] args) {
        // TODO code application logic here
        Quicksort quicksort;
        quicksort = new Quicksort();
        quicksort.input();
        quicksort.qsort(0, quicksort.size-1);
        quicksort.show();        
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