import java.util.Iterator;


public class LhzLinkedListIterator<E> implements Iterator<E> {
	LhzLinkedListNode<E> node;
	public LhzLinkedListIterator(LhzLinkedList<E> list) {
		node=list.startingNode;
	}
	@Override
	public boolean hasNext() {
		if(node.nextNode!=null){return true;}
		return false;
	}

	@Override
	public E next() {
		if(hasNext()){
			E data=node.data;
			node=node.nextNode;
			return data;
		}
		else{return null;}
	}
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
}
