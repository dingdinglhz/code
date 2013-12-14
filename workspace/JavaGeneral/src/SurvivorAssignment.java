/*--------------------------------------------------------------------------------------*/
/*  SurvivorAssignment.java                                                             */
/*  This program simulate the TV show Survivor. It stores contesters in a liked list. It*/
/*  has the following processed:Input, Backup, elimination and summary.                 */
/*--------------------------------------------------------------------------------------*/
/*  Author: Hanzhen Lin                                                                 */
/*  Date:   2013,Dec,10                                                                 */
/*--------------------------------------------------------------------------------------*/
/*  Input:User input from standard input containing a list of original contesters and   */
/*  contesters to eliminate.                                                            */
/*  Output: Response of the processes (whether it is successful or not, and the summary */
/*  of original contesters and the winner.                                              */
/*--------------------------------------------------------------------------------------*/
import java.util.Scanner;//Import Scanner for input

class SurvivorNode{//The class for nodes in the liked list.
	String name;  //The name of the contester.
	SurvivorNode next;
	//The next node that this node points to
	SurvivorNode (String nameIn){
	//Initializer for the node. It generates nodes according 
	//to the name provided in the parameter.
		name=nameIn;
		next=null;
	}
}

class SurvivorList{
//The class that stores a cyclic linked list. The head & tail of the list
//in maintained and the next node of tail always points to the head.
	SurvivorNode head;
	SurvivorNode tail;
	SurvivorList(){
		head=null;
		tail=null;
	}
	void add(String dataIn){
	//this method adds one node to the liked list.
		if(tail!=null){
		//if it is not the first node in the list, append
		//new node after the tail and update the new tail
			tail.next=new SurvivorNode(dataIn);
			tail=tail.next;
			tail.next=head;
		}
		else{
		//else, create a new node which is both head and tail.
			head=new SurvivorNode(dataIn);
			head.next=head;
			tail=head;
		}
	}
	boolean delete(String dataIn){
		//this method deletes one node from the liked list.
		SurvivorNode i=head,j=tail;//tail is the previous node of head.
		while(true){
		//scan each node in the list. If there is such node,make the
		//previous node points to the node after current node to delete.
			if(i.name.equals(dataIn)){
				if(i==head){head=i.next;}
				if(i==tail){tail=j;}
				j.next=i.next;
				return true;//return true if the deletion is successful.
			}
			i=i.next; j=j.next;//j is maintained as the previous node of i.
			if(i==head){break;}
		//if i equals head, all nodes have been visited, quit the loop.
		}
		return false;//return false if the node is not in the list.
	}
}

public class SurvivorAssignment {
//The major class of this file, which deal with the processes in the game. 
	SurvivorList list,bklist;//"list" is The list of current survivors 
	//and the "bklist" is the backup list of original contesters.
	public void input(Scanner scan){
	//Deals with user input. It adds nodes to "list" using names received.
		list=new SurvivorList();
		String name;
		while(scan.hasNext()){//if there is no string left, terminate loop.
			name=scan.next();//input a string
			if(name.equals("fin")){
				return;//"fin" indicates the end of the List, so it ends.
			}
			list.add(name);//adds nodes to "list" using names received
		}
	}
	public void backUp(){
	//This method deals with the backup process. It scans each nodes in 
	//"list" and add those nodes into "bklist"
		bklist=new SurvivorList();
		SurvivorNode i=list.head;//i visits each node in the list.
		while(true){
			bklist.add(i.name);
			i=i.next;
			if(i==list.head){break;}//if i equals the head of 
		//list, all nodes have been visited, terminates the loop.
		}
	}
	public void elimination(Scanner scan){
	//this method deals with the elimination process.
		String name;
		while(scan.hasNext() && list.head!=list.tail){
		//if there is no string left or there is only one node left 
		//in the list indicated by head==tail, stop the loop.
			name=scan.next();//input a string
			if( !list.delete(name) ){//if deletion fails, notifies the user.
				System.out.println
				("Deletion is not successful, please " +
						"check if the name is correct!");
			}
			else{//if deletion is successful, tells the user as well.
				System.out.println("The Contestor " +
						"is eliminated successfully");
			}
			//display(); for debugging.
			if(list.head==list.tail){return;}
			//if there is only one node left, terminates the method.
		}
	}
	public void summary(){//summary process, which prints the final result.
		System.out.println("List of Original Contesters:");
		SurvivorNode i=bklist.head;
		while(true){
			System.out.println(i.name);
			i=i.next;
			if(i==bklist.head){break;}
		}//scan each nodes in the "bklist" and output there names as the list
		//of original contesters. Similar process as in method"backup()".
		System.out.println("The Winner:");
		System.out.println(list.head.name);
		//print the name of the winner, the only node in "list"
	}
	String summaryString(){
		String dataOut="";
		dataOut+="List of Original Contesters:";
		SurvivorNode i=bklist.head;
		while(true){
			dataOut+="\n"+i.name;
			i=i.next;
			if(i==bklist.head){break;}
		}//scan each nodes in the "bklist" and output there names as the list
		//of original contesters. Similar process as in method"backup()".
		dataOut+="\n"+"The Winner:";
		dataOut+="\n"+list.head.name+"\n";
		return dataOut;
	}
	public void display(){//for debugging use, similar to "summary()"
		System.out.println("list :");
		SurvivorNode i=list.head;
		while(true){
			System.out.print(i.name+" ");
			i=i.next;
			if(i==list.head){break;}
		}
		System.out.println();
		System.out.println("bklist :");
		i=bklist.head;
		while(true){
			System.out.print(i.name+" ");
			i=i.next;
			if(i==bklist.head){break;}
		}
		System.out.println();
	}
	public boolean delete(String dataIn){
		return list.delete(dataIn);
	}
	public boolean hasWinner(){
		return list.head==list.tail;
	}
	public static void main(String[] args) {//main method;
		SurvivorAssignment survivor=new SurvivorAssignment();
		Scanner scan=new Scanner(System.in);//create a scanner using stdin.
		System.out.println("Enter the name of contesters:");
		survivor.input(scan);//input process
		System.out.println("Input is processed Successfully");
		survivor.backUp();//backup process
		System.out.println("Backup is done");
		survivor.elimination(scan);//elimination process
		survivor.summary();//summary process.
	}
}
/*
Galilei
Newton
Bohr
Einstein
Maxwell
Boltzmann
Schrodinger
Feynman
fin
*/