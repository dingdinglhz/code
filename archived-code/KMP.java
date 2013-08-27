/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;

/**
 *
 * @author linhz
 */
//kmp.java
public class KMP{
	public static void main(String[] args) {
		String a,b;//a:for matching; b:matching key;
		Scanner scan=new Scanner(System.in);
		a=scan.next();
		b=scan.next();
		int p[]=new int[b.length()+1];
		int i,j,n=a.length(),m=b.length();
		p[1]=0;j=0;
		for (i=2;i<=m;i++){
			while(j>0 && b.charAt(j)!=b.charAt(i-1)){j=p[j];}
			if (b.charAt(j)==b.charAt(i-1)) {j++;}
			p[i]=j;
		}
		j=0;
		for (i=0;i<n;i++){
			while(j>0 && b.charAt(j)!=a.charAt(i)){j=p[j];}
			if (b.charAt(j)==a.charAt(i)){j++;}
			if (j==m) {
				System.out.println("success matching in"+i);
				j=p[j];
			}
		}
	}
}