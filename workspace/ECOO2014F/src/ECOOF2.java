import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

import java.util.Iterator;

class Person{
	HashSet<String> sons;
	String mom;
	Person(){
		sons=new HashSet<String>();
		mom="";
	}
}
class Solver2{
	Hashtable<String,Person> people;
	int n=0;
	void init(Scanner scan){
		people=new Hashtable<String,Person>();
		n=scan.nextInt();
		for(int i=0;i<n;i++){
			String m=scan.next();
			String d=scan.next();
			if(!people.containsKey(m)){
				people.put(m, new Person());
			}
			people.get(m).sons.add(d);
			if(!people.containsKey(d)){
				people.put(d, new Person());
			}
			people.get(d).mom=m;
		}
	}
	int countSis(String n){
		String m;
		m=people.get(n).mom;
		if(m.equals("")){return 0;}
		return people.get(m).sons.size()-1;
	}
	int countCou(String n){
		int cou=0;
		String m;
		m=people.get(n).mom;
		if(m.equals("")){return 0;}
		String gm=people.get(m).mom;
		if(gm.equals("")){return 0;}
		Iterator<String> it=people.get(gm).sons.iterator();
		while(it.hasNext()){
			String tmp=it.next();
			if(!tmp.equals(m)){
				cou+=people.get(tmp).sons.size();
			}
		}
		return cou;
	}
	void output(Scanner scan){
		for(int i=0;i<10;i++){
			String tmp=scan.next();
			int cou=countCou(tmp);
			int sis=countSis(tmp);
			System.out.println("Cousins: "+cou+", Sisters: "+sis);
		}
	}
}

public class ECOOF2 {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(new File("src/DATA21.txt"));
		while(scan.hasNext()){
			Solver2 s=new Solver2();
			s.init(scan);
			s.output(scan);
		}
	}

}
