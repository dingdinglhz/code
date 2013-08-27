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
public class QueueAndStack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
    }
}
