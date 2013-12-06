import java.util.Scanner;
class SurvivorNode{
	String name;
	SurvivorNode next;
	SurvivorNode (String nameIn){
		name=nameIn;
		next=null;
	}
}
class SurvivorList{
	SurvivorNode head;
	SurvivorNode tail;
	SurvivorList(){
		head=null;
		tail=null;
	}
	void add(String dataIn){
		if(tail!=null){
			tail.next=new SurvivorNode(dataIn);
			tail=tail.next;
			tail.next=head;
		}
		else{
			head=new SurvivorNode(dataIn);
			head.next=head;
			tail=head;
		}
	}
	boolean delete(String dataIn){
		SurvivorNode i=head,j=tail;
		while(true){
			if(i.name.equals(dataIn)){
				if(i==head){head=i.next;}
				if(i==tail){tail=j;}
				j.next=i.next;
				return true;
			}
			i=i.next; j=j.next;
			if(i==head){break;}
		}
		return false;
	}
}
public class SurvivorAssignment {
	SurvivorList list,bklist;
	public void input(Scanner scan){
		list=new SurvivorList();
		String name;
		while(scan.hasNext()){
			name=scan.next();
			if(name.equals("fin")){
				return;
			}
			list.add(name);
		}
	}
	public void backUp(){
		bklist=new SurvivorList();
		SurvivorNode i=list.head;
		while(true){
			bklist.add(i.name);
			i=i.next;
			if(i==list.head){break;}
		}
	}
	public void elimination(Scanner scan){
		String name;
		while(scan.hasNext() && list.head!=list.tail){
			name=scan.next();
			if( !list.delete(name) ){
				System.out.println
				("Deletion is not successful, please " +
						"check if the name is correct!");
			}
			else{
				System.out.println("The Contestor " +
						"is eliminated successfully");
			}
			//display();
			if(list.head==list.tail){return;}
		}
	}
	public void summary(){
		System.out.println("List of Original Contestors:");
		SurvivorNode i=bklist.head;
		while(true){
			System.out.println(i.name);
			i=i.next;
			if(i==bklist.head){break;}
		}
		System.out.println("The Winner:");
		System.out.println(list.head.name);
	}
	public void display(){
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
	public static void main(String[] args) {
		SurvivorAssignment survivor=new SurvivorAssignment();
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter the name of contestors:");
		survivor.input(scan);
		System.out.println("Input is processed Successfully");
		survivor.backUp();
		System.out.println("Backup is done");
		survivor.elimination(scan);
		survivor.summary();
		return;
	}
}