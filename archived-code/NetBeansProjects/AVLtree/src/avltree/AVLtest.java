/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

import java.util.Scanner;

/**
 *
 * @author linhz
 */
public class AVLtest {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
    	AVLtree<Integer> t=new AVLtree<Integer>();
        int tmpa,tmpb;String act;
        do{
            act=scan.next();
            switch(act){
            	case "i": {
            	    tmpa=scan.nextInt();
            	    t.insert(tmpa);
            	    t.preOrder();
            	    t.inOrder();
            	    t.printTree();
            	    break;
            	    }
            	case "f": {
            	    tmpa=scan.nextInt();
            	    System.out.println(t.find(tmpa).dat);
            	    break;
            	    }
            	case "d":{
            		tmpa=scan.nextInt();
            		t.del(tmpa);
            	    t.preOrder();
            	    t.inOrder();
            	    t.printTree();
            	    break;
            }
            case "o": return;
            default: break; 
        }
            
        }while(scan.hasNext());
    }
}
