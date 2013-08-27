/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;
import java.util.*;
/**
 *
 * @author linkb
 */
public class Helloworld {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         System.out.println("Hello World!");
        // TODO code application logic here
         System.out.println(new Date());
         Properties p = System.getProperties();
         p.list(System.out);
         System.out.println("--- Memory Usage:");
         Runtime rt = Runtime.getRuntime();
         System.out.println("Total Memory = "
                 + rt.totalMemory()
                 + " Free Memory = "
                 + rt.freeMemory());
    }
}
