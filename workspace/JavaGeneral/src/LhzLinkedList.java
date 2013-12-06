import java.util.Scanner;


class LhzLinkedListNode <E> {
	/**
	 * @param args
	 */
	public E data;
	public LhzLinkedListNode <E> nextNode;
	LhzLinkedListNode (E dataIn){
		data=dataIn;
	}
}

public class LhzLinkedList <E> {
	LhzLinkedListNode <E> startingNode;
	LhzLinkedListNode <E> endingNode;
	void add(E dataIn){
		if(endingNode!=null){
			endingNode.nextNode=new LhzLinkedListNode<E>(dataIn);
			endingNode=endingNode.nextNode;
		}
		else{
			startingNode=new LhzLinkedListNode<E>(dataIn);
			endingNode=startingNode;
		}
	}
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		LhzLinkedList<String> list=new LhzLinkedList<String>();
		while(scan.hasNext()){
			list.add(scan.next());
		}
	}
}
