package sort;

import java.util.Date;
import java.util.Scanner;

public class Evaluation {
	/**
         *
         */
        public static int total,length,range;
	/*public static boolean compare(Quicksort qs,Mergesort ms){
		for (int i=0;i<length;++i) {
			if(qs.data[i]!=ms.data[0][i]){return false;}
		}
		return true;
	}*/
        public static boolean compare(QsortB qb,Mergesort ms){
		for (int i=0;i<length;++i) {
			if(qb.data[i]!=ms.data[0][i]){return false;}
		}
		return true;
	}
	public static void main(String[] args) {
		Date date = new Date();
		double averageo = 0;
		double averaget1=0,averaget2=0,averaget3=0;
		Scanner sc=new Scanner(System.in);
		total=sc.nextInt();
		length=sc.nextInt();
                range=sc.nextInt();
		for(int i=0;i<=total;++i){
			Quicksort qs=new Quicksort(length);
			Mergesort ms=new Mergesort(length);
                        QsortB qb=new QsortB(length);
			for(int j=0;j<length;++j){
				int a=(int) (Math.random()*range);
				qs.data[j]=a;
				ms.data[0][j]=a;
				ms.data[1][j]=a;
                                qb.data[j]=a;
			}
			long t1=System.currentTimeMillis(); 
			qs.qsort(0,length-1);
			long t2=System.currentTimeMillis(); 
			ms.msort(0,length-1,0);
			long t3=System.currentTimeMillis();
                        qb.qsort(0,length-1);
			long t4=System.currentTimeMillis();
            //System.out.println(" t1 "+t1+" t2 "+t2+" t3 "+t3);
            if(!compare(qb,ms)){System.out.println("not the same!!");}
			t1=t2-t1; t2=t3-t2; t3=t4-t3;
			System.out.println(" qs "+t1+" ms "+t2+"qb "+t3);
			if(i>0){averaget1+=t1; averaget2+=t2; averaget3+=t3;}
			//System.out.println(qs.theta);
			//averageo+=qs.theta;

		}
		averaget1/=total;
		averaget2/=total;
                averaget3/=total;
                //averageo/=total;
		//System.out.println("averageo="+averageo);
                System.out.println("averaget1="+averaget1);
                System.out.println("averaget2="+averaget2);
                System.out.println("averaget3="+averaget3);
	}
}