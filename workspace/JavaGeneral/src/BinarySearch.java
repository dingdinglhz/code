/*--------------------------------------------------------------------------------------*/
/*  BinarySearch.java                                                                   */
/*  This program uses both binary search to find a target phone number from a pre-sorted*/
/*  number list provided in phone.txt. It also compares the time cost by 2 algorithms.  */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen Lin                                                                 */
/*  Date:   2013,Nov,11                                                                 */
/*--------------------------------------------------------------------------------------*/
/*  Input:  A file containing a pre-sorted list of phone numbers and standard input from*/
/*  user that provides the desired phone number to search.                              */
/*  Output: The index of the target number in the number list and the number of comparis*/
/*  on using both algorithms.                                                           */
/*--------------------------------------------------------------------------------------*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
//These are classes that are used in my code. They are imported here.
public class BinarySearch {
	/**
	 * @param args
	 * @throws IOException 
	 */
	static final int FILE_NOT_FOUND=-1;//define the index for numbers not found in the list to be -1
	static private int nBin,nLin;
	//nBin is the number of comparison using Binary Search. nLin is the number of comparison using Linear Search.
	static private ArrayList<String> numberList;
	//numberList stores the phone numbers.
	static private int binary(int lb,int rb,String target){
		//This method uses binary search algorithm to find the target.
		//lb is the left bound of the region that the target can appear in the sorted array.
		//similarly, rb is the right bound. target is the target number we want to find.
		if(lb>rb){
			return FILE_NOT_FOUND;
		}//if lb>rb, then it means that desiring file is not found in the list.
		int i=(lb+rb)/2;//i is the index of the element in the middle of current region.
		++nBin;//add number of comparison by 1.
		if(numberList.get(i).equals(target)){
			return i;//if the current middle element equals the target, return the index.
		}else if(numberList.get(i).compareTo(target)>0){
			return binary(lb,i-1,target);
			//if the target is smaller than middle element, it must be in the left half. Use recursion to continue searching.
		}
		else{
			return binary(i+1,rb,target);
			//if the target is larger than middle element, it must be in the right half. Use recursion to continue searching.
		}
	}
	static private int linear(String target){
		//This method uses linear search algorithm to find the target.
		for(int i=0;i<numberList.size();++i){//iterate all elements to find the target.
			++nLin;//add number of comparison by 1.
			if(numberList.get(i).equals(target)){
				return i;//As long as the target is found, return the index. 
			}
		}
		return FILE_NOT_FOUND;//The target cannot be found.
	}
	
	public static void main(String[] args) throws IOException {
		//main method
		BufferedReader fbr=new BufferedReader(new FileReader("phone.txt"));//use BufferedReader+FileReader to input number list.
		numberList=new ArrayList<String>();
		while(fbr.ready()){
			numberList.add(fbr.readLine());//input all numbers in the file and store them in numberList.
		}
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//after loading all numbers, start interacting with user.
		do{
			System.out.println("Pleas enter a phone number for searching");
			String target=br.readLine();
			//ask user which number they want to search for.
			nBin=0;nLin=0;
			//initializing number of comparison
			int index=binary(0,numberList.size()-1,target);
			//use binary search algorithm to search the number first.
			linear(target);
			//use linear search algorithm to search the number.
			if(index==FILE_NOT_FOUND){
				System.out.println("The number is not found.");
				//if the target is not found, tell the user.
			}
			else{
				System.out.println("The index of this number is "+index);
				//if the target is sucessfully found, tell the user the index.
			}
			System.out.println("The binary search used "+nBin+" comparisons, while the linear search used "+nLin+" comparisons.");
			//displaying the number of comparison of both algorithm.
		}while(true);
	}
}
