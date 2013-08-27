/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package queueandstack;
import java.util.Scanner;
/**
 *
 * @author linkb
 */


 
public class Stack{
      private int nu[];
      private int ta,q;
      private boolean full,empty;
      public Stack(){
         nu=new int[2000];
         ta=0;q=2000;
         full=false;empty=true;
      }
      /**
     *
     * @param q_i
     */
    public Stack(int q_i){
         nu=new int[q_i];
         ta=0;q=q_i;
         full=false;empty=true;
      }
      public void push(int x){
           if (full){return;}
           if (empty){empty=false;}
           nu[ta]=x;ta++;
           if(ta==q-1){
              full=true;
           }
      }
      public int pop(){
          if (empty){return 0;}
          if (full){full=false;}
          ta--;
          int i=nu[ta];
          nu[ta]=0;
          if(ta==0){full=true;}
          return i;
      }
      public void test (){
           System.out.println("tail"+ta);
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
      public static void main (String args[]) {
        Stack q;
        q = new Stack(15);
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
      }
};
 
 

