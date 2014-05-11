/**
 * 
 */

/**
 * @author linhz
 *
 */
interface Function{
	double valueAt(double x);
}
class QuadFunc implements Function{
	public double valueAt(double x) {
		return -x*x+x;
	}
}

public class SinglePeakFinder {
	static final int SEGMENTS=6;
	static final double TOLERANCE=0.000000000000001;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean[] b=new boolean[100];
		System.out.println(b[0]);
		QuadFunc f = new QuadFunc();
		double ansX=findPeak(0,10,f);
		System.out.println("SEGMENTS="+SEGMENTS);
		System.out.println("x="+ansX+" f(x)="+f.valueAt(ansX));
		
		
	}
	static double findPeak(double lb,double rb, Function f){
		if(rb-lb < TOLERANCE){return lb;}
		double maxF=f.valueAt(lb);
		int    maxI=0;
		for(int i=1;i<=SEGMENTS;i++){
			double tmpX=rb*i/SEGMENTS+lb*(SEGMENTS-i)/SEGMENTS;
			double tmpF=f.valueAt(tmpX);
			if(tmpF>=maxF){
				maxF=tmpF;
				maxI=i;
			}
		}
		double lbNew=rb*(maxI-1)/SEGMENTS+lb*(SEGMENTS-maxI+1)/SEGMENTS;
		double rbNew=rb*(maxI+1)/SEGMENTS+lb*(SEGMENTS-maxI-1)/SEGMENTS;
		if(maxI==0){
			return findPeak(lb,rbNew,f);
		}else if(maxI==SEGMENTS){
			return findPeak(lbNew,rb,f);
		}else{
			return findPeak(lbNew,rbNew,f);
		}
	}
	
}
