/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package queueandstack;

//import java.util.Scanner;

/**
 *
 * @author linkb
 */
public class Queue {
	
	private int[] nu;
	private int head,tail,length,q;
	private boolean full,empty;

	
	public Queue(){
		nu=new int[2001];
		head=0;tail=0;length=0;q=2000;
		full=false;empty=true;
	}
	/**
     *
     * @param q_i
     */
        public Queue(int q_i){
		nu=new int[q_i];
		head=0;tail=0;length=0;q=q_i;
		full=false;empty=true;
	}
	public void push(int x){
		if(full){return;}
		if(empty){empty=false;}
		nu[tail]=x;tail++;
		tail%=q;length++;
		if(length==q){full=true;}
	}
	public int pop(){
		if(empty){return 0;}
		if(full){full=false;}
		int i=nu[head];nu[head]=0;
		head++;head%=q;length--;
		if(length==0){empty=true;}
		return i;
	}
        public void test (){
           System.out.println(" head:"+head+" tail:"+tail+" length:"+length);
           int i;
           for (i=0;i<q;i++){
               String s="";
               s+=i;
               s+="--";
               s+=nu[i];
               s+="  -  ";
               System.out.print(s); 
           }
           System.out.println(""+empty+full);
      }
        /*public static void main (String args[]) {
        Queue q = new Queue(15);
        int k;int n;
        Scanner is=new Scanner(System.in);
        n=is.nextInt();
        for(int i=1;i<=n;i++){
            k=is.nextInt();
            q.push(k);
            q.test();
            }
        for(int i=1;i<=n;i++){
            q.pop();
            q.test();
            }
      }*/
	
}