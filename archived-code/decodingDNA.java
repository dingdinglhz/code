//ECOO2012 S1P2
import java.util.Scanner;
public class decodingDNA{
	static String BEGINKEY="TATAAT";
	String dnaS,ans;
	static char complement(char dnaIn) throws IllegalArgumentException{
		switch(dnaIn){
			case 'A': return 'T';
			case 'T': return 'A';
			case 'G': return 'C';
			case 'C': return 'G';
			default: throw IllegalArgumentException("Not a DNA character\n") ; 
		}
	}
	int void findBegin() {
		int p[]=new int[BEGINKEY.length()+1];
		int i,j,n=dnaS.length(),m=BEGINKEY.length();
		p[1]=0;j=0;
		for (i=2;i<=m;i++){
			while(j>0 && BEGINKEY.charAt(j)!=BEGINKEY.charAt(i-1)){j=p[j];}
			if (BEGINKEY.charAt(j)==BEGINKEY.charAt(i-1)) {j++;}
			p[i]=j;
		}
		j=0;
		for (i=0;i<n;i++){
			while(j>0 && BEGINKEY.charAt(j)!=dnaS.charAt(i-1)){j=p[j];}
			if (BEGINKEY.charAt(j)==dnaS.charAt(i-1)) {j++;}
			if (j==m) {return i+5;}
		}
	}
	int void judgeEnd(int end){
		int p[]=new int[7];
		String key=dnaS.substring(end, end+6);
		for (; ; ) {
			
		}

	}
}