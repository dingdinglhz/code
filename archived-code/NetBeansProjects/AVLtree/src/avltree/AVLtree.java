/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/
package avltree;

/*
 *
 *@author linhz
 */
import java.lang.Comparable;

class AVLtree<Tree_Data extends Comparable<Tree_Data>> {
    /**
     * @param args the command line arguments
     */
	class AVLnode<Tree_Data>{
 		public Tree_Data dat;
 		private byte hei;
		private int siz;//data Height size
		private AVLnode<Tree_Data> lcd,rcd,pre;//left-child,right-child,parent
		AVLnode(){
			hei=0;siz=1;
			lcd=null;rcd=null;pre=null;
		}
		AVLnode(Tree_Data dat_in){
			dat=dat_in;hei=0;siz=1;
			lcd=null;rcd=null;pre=null;
		}
		AVLnode(Tree_Data dat_in,byte hei_in){
			dat=dat_in;hei=hei_in;siz=1;
			lcd=null;rcd=null;pre=null;
		}
		/*byte Height(){
		*	if (this==null) {return -1;}
		*	else{return hei;}
		*}
		*intSize(){
		*	if (this==null) {return 0;}
		*	else{return siz;}
		*}
		*/
	};
	private byte Height(AVLnode<Tree_Data> node){
		if (node==null) {return -1;}
		else{return node.hei;}
	}
	private int Size(AVLnode<Tree_Data> node){
		if (node==null) {return 0;}
		else{return node.siz;}
	}
	byte MaxHeight(byte Maxa, byte Maxb){return Maxa>Maxb?Maxa:Maxb;}
	public AVLnode<Tree_Data> root;
	AVLtree(){root=null;}
	AVLnode<Tree_Data> RotateLeft(AVLnode<Tree_Data> up_root){
		if (up_root==null || up_root.rcd==null) {return up_root;}
		AVLnode<Tree_Data> p=up_root;
		up_root=p.rcd;
		p.rcd=up_root.lcd;
		p.hei=(byte) (MaxHeight(Height(p.lcd),Height(p.rcd))+1);
		p.siz=Size(p.lcd)+Size(p.rcd)+1;
		up_root.lcd=p;
		up_root.hei=(byte) (MaxHeight(Height(up_root.lcd),Height(up_root.rcd))+1);
		up_root.siz=Size(up_root.lcd)+Size(up_root.rcd)+1;
		return up_root;
	}
	AVLnode<Tree_Data> RotateRight(AVLnode<Tree_Data>  up_root){
		if (up_root==null || up_root.lcd==null) {return up_root;}
		AVLnode<Tree_Data> p=up_root;
		up_root=p.lcd;
		p.lcd=up_root.rcd;
		p.hei=(byte) (MaxHeight(Height(p.lcd),Height(p.rcd))+1);
		p.siz=Size(p.lcd)+Size(p.rcd)+1;
		up_root.rcd=p;
		up_root.hei=(byte) (MaxHeight(Height(up_root.lcd),Height(up_root.rcd))+1);
		up_root.siz=Size(up_root.lcd)+Size(up_root.rcd)+1;
		return up_root;
	}
	AVLnode<Tree_Data> RotateRL(AVLnode<Tree_Data>  up_root){
		up_root.rcd=RotateRight(up_root.rcd);
		up_root=RotateLeft(up_root);
		return up_root;
	}
	AVLnode<Tree_Data> RotateLR(AVLnode<Tree_Data>  up_root){
		up_root.lcd=RotateLeft(up_root.lcd);
		up_root=RotateRight(up_root);
		return up_root;
	}
	AVLnode<Tree_Data> InsertNode(AVLnode<Tree_Data> node,Tree_Data x){
		if (node==null) {
			node=this.new AVLnode<Tree_Data>(x);
			return node;
		}
		if (x.compareTo(node.dat)<0) {
			node.lcd=InsertNode(node.lcd,x);
			if ((Height(node.lcd))-(Height(node.rcd))==2) {
				if (x.compareTo(node.lcd.dat)<0) {
					node=RotateRight(node);
				}
				else{node=RotateLR(node);}
			}
		}
		else if (x.compareTo(node.dat)>0) {
			node.rcd=InsertNode(node.rcd,x);
			if ((Height(node.rcd))-(Height(node.lcd))==2) {
				if (x.compareTo(node.rcd.dat)>0) {
					node=RotateLeft(node);
				}
				else{node=RotateRL(node);}
			}
		}
		node.hei=(byte) (MaxHeight(Height(node.lcd),Height(node.rcd))+1);
		node.siz=Size(node.lcd)+Size(node.rcd)+1;
		return node;
	}
	AVLnode<Tree_Data> FindNode(AVLnode<Tree_Data> node, Tree_Data x){
		if (node==null) {return null;}
		if (x.compareTo(node.dat)<0) {return FindNode(node.lcd,x);}
		if (x.compareTo(node.dat)>0) {return FindNode(node.rcd,x);}
		return node;
	}
	AVLnode<Tree_Data> OrderNode(AVLnode<Tree_Data> node, int order){
		if (node==null) {return null;}
		int siz_k=Size(node.lcd);
		if (order<=siz_k){return OrderNode(node.lcd,order);}
		else if (order==siz_k+1){return node;}
		else{return OrderNode(node.rcd,order-siz_k-1);}
	}
	AVLnode<Tree_Data> DeleteNode(AVLnode<Tree_Data> node, Tree_Data x){
		if (node==null) {return node;}
		if (x.compareTo(node.dat)<0) {
			node.lcd=DeleteNode(node.lcd,x);
			if ((Height(node.rcd))-(Height(node.lcd))==2) {
				if ((Height(node.rcd.lcd))<(Height(node.rcd.rcd))) {
					node=RotateLeft(node);
				}
				else{node=RotateRL(node);}
			}
		}
		else if (x.compareTo(node.dat)>0) {
			node.rcd=DeleteNode(node.rcd,x);
			if ((Height(node.lcd))-(Height(node.rcd))==2) {
				if ((Height(node.lcd.lcd))>(Height(node.lcd.rcd))) {
					node=RotateRight(node);
				}
				else{node=RotateLR(node);}
			}
		}
		else{
			if (node.lcd!=null && node.rcd!=null) {
				AVLnode<Tree_Data> tmp=node.rcd;
				while(tmp.lcd!=null){tmp=tmp.lcd;}
				node.dat=tmp.dat;
				node.rcd=DeleteNode(node.rcd,tmp.dat);
				if ((Height(node.lcd))-(Height(node.rcd))==2) {
					if ((Height(node.lcd.lcd))>(Height(node.lcd.rcd))) {
						node=RotateRight(node);
					}
					else{node=RotateLR(node);}
				}
			}
			else{
				//AVLnode<Tree_Data> pfd=node;//pointer for deletion
				if (node.lcd!=null) {node=node.lcd;}
				else{node=node.rcd;}
			}
		}
		if (node!=null) {
			node.hei=(byte) (MaxHeight(Height(node.lcd),Height(node.rcd))+1);
			node.siz=Size(node.lcd)+Size(node.rcd)+1;
		}
		return node;
	}
	
	void printTreeN( AVLnode<Tree_Data> t, int layer){
    	if(t == null ) {return;}
    	if(t.rcd!=null){printTreeN(t.rcd,layer+1);}
    	for(int i =0;i<layer;i++){System.out.print("    ");}
    	System.out.println(t.dat);
    	if(t.lcd!=null){printTreeN(t.lcd,layer+1);}
	}
	void showTreeN( AVLnode<Tree_Data> t, int layer,javax.swing.JTextArea jta){
    	if(t == null ) {return;}
    	if(t.rcd!=null){showTreeN(t.rcd,layer+1,jta);}
    	for(int i =0;i<layer;i++){jta.append("       ");}
    	jta.append(""+t.dat+"\n");
    	if(t.lcd!=null){showTreeN(t.lcd,layer+1,jta);}
	}
	void inOrderN( AVLnode<Tree_Data> node){
		if (node==null) {return;}
		inOrderN(node.lcd);
		System.out.print(""+node.dat+" ");
		inOrderN(node.rcd);
	}
	void preOrderN( AVLnode<Tree_Data> node){
		if (node==null) {return;}
        System.out.print("("+node.dat+":");
		preOrderN(node.lcd);
		preOrderN(node.rcd);
		System.out.println(")");
	}
	public void inOrder(){ inOrderN(root);System.out.println();}
	public void preOrder(){preOrderN(root);System.out.println();}
	public void insert( Tree_Data x){root=InsertNode(root,x);}
	public AVLnode<Tree_Data> find( Tree_Data x){return FindNode(root,x);}
	public AVLnode<Tree_Data> order(int order){return OrderNode(root,order);}
	public void del( Tree_Data x){root=DeleteNode(root,x);}
	public void printTree(){printTreeN(root,0);}
	public void showTree(javax.swing.JTextArea jta){
		jta.setText("");
		showTreeN(root,0,jta);
	}

};