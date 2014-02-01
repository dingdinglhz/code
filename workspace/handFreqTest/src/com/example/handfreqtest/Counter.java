package com.example.handfreqtest;

import java.util.ArrayList;

//import android.util.Log;
public class Counter {
	static final double INIT_PREV_TIME=-1;
	static final double MS_TO_NS=1000000;
	static final double S_TO_MS=1000;
	ArrayList<Double> timeList;
	double previousTime;
	double averageTime;
	double standardDeviation;
	double frequency;
	Counter(){
		timeList=new ArrayList<Double>();
		previousTime=INIT_PREV_TIME;
		averageTime=0;
		standardDeviation=0;
		frequency=0;
	}
	void newClick(){
		if(previousTime==INIT_PREV_TIME){
			previousTime=System.nanoTime()/MS_TO_NS;
		}
		else{
			double currentTime=System.nanoTime()/MS_TO_NS;
			timeList.add(currentTime-previousTime);
			previousTime=currentTime;
			
		}
		//Log.d("click", "prevTime:"+previousTime+" tls"+timeList.size());
	}
	void statAnalysis(){
		//for(int i=0; i<timeList.size(); i++){
		//	Log.d("time", "t: "+timeList.get(i));
		//}
		for(int i=0; i<timeList.size(); i++){
			averageTime+=timeList.get(i);
			//Log.d("avgadd", "avg:"+averageTime);
		}
		averageTime/=(double)timeList.size();
		//Log.d("avgafin", "avg:"+averageTime);
		frequency=S_TO_MS/averageTime;
		//Log.d("frq", "frq:"+frequency);
		for(int i=0; i<timeList.size(); i++){
			double tmp=timeList.get(i)-averageTime;
			standardDeviation+=tmp*tmp;
			//Log.d("sdadd", "sd:"+standardDeviation);
		}
		standardDeviation/=(double)timeList.size();
		//Log.d("sddiv", "sd:"+standardDeviation);
		standardDeviation=Math.sqrt(standardDeviation);
		//Log.d("sdroot", "sd:"+standardDeviation);
	}
	String statResult(){
		statAnalysis();
		String str="";
		str+="Average time:"+averageTime+" ms \n";
		str+="Frequency:"+frequency+" Hz \n";
		str+="Standard deviation:"+standardDeviation+" ms \n";
		str+="Precentage ratio:"+(standardDeviation/averageTime*100)+"% \n";
		return str;
	}
	
}
