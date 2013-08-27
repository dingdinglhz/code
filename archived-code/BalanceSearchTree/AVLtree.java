
class AVL_Node<Tree_Data>{
 	public Tree_Data dat;
 	short hei;
	int siz;//data height size
	AVL_Node *lcd,*rcd,*pre;//left-child,right-child,parent
	AVL_Node(){
		dat=Tree_Data();hei=0;siz=1;
		lcd=NULL;rcd=NULL;pre=NULL;
	}
	AVL_Node(Tree_Data dat_in){
		dat=dat_in;hei=0;siz=1;
		lcd=NULL;rcd=NULL;pre=NULL;
	}
	AVL_Node(Tree_Data dat_in,int hei_in){
		dat=dat_in;hei=hei_in;siz=1;
		lcd=NULL;rcd=NULL;pre=NULL;
	}
	inline int height(){
		if (this==NULL) {return -1;}
		else{return hei;}
	}
	inline int size(){
		if (this==NULL) {return 0;}
		else{return siz;}
	}
	~AVL_Node(){
		//delete lcd;
		//delete rcd;
		//delete pre;
	}
};
template<typename Tree_Data>
class AVL_Tree {
	void final_del(const AVL_Node<Tree_Data> *node){
		if (node) {
			final_del(node->lcd);
			final_del(node->rcd);
			delete node;
		}
	}
public:
	AVL_Node<Tree_Data> *root;
	AVL_Tree(){
		root=NULL;
	}
	~AVL_Tree(){
		final_del(root);
	}
protected:
	inline void RotateLeft(AVL_Node<Tree_Data> * &up_root){
		if (up_root==NULL || up_root->rcd==NULL) {return;}
		AVL_Node<Tree_Data> *p=up_root;
		up_root=p->rcd;
		p->rcd=up_root->lcd;
		p->hei=max(p->lcd->height(),p->rcd->height())+1;
		p->siz=p->lcd->size()+p->rcd->size()+1;
		up_root->lcd=p;
		up_root->hei=max(up_root->lcd->height(),up_root->rcd->height())+1;
		up_root->siz=up_root->lcd->size()+up_root->rcd->size()+1;
	}
	inline void RotateRight(AVL_Node<Tree_Data> * &up_root){
		if (up_root==NULL || up_root->lcd==NULL) {return;}
		AVL_Node<Tree_Data> *p=up_root;
		up_root=p->lcd;
		p->lcd=up_root->rcd;
		p->hei=max(p->lcd->height(),p->rcd->height())+1;
		p->siz=p->lcd->size()+p->rcd->size()+1;
		up_root->rcd=p;
		up_root->hei=max(up_root->lcd->height(),up_root->rcd->height())+1;
		up_root->siz=up_root->lcd->size()+up_root->rcd->size()+1;
	}
	inline void RotateRL(AVL_Node<Tree_Data> * &up_root){
		RotateRight(up_root->rcd);
		RotateLeft(up_root);
	}
	inline void RotateLR(AVL_Node<Tree_Data> * &up_root){
		RotateLeft(up_root->lcd);
		RotateRight(up_root);
	}
	void insert_node(AVL_Node<Tree_Data> * & node,const Tree_Data &x){
		if (node==NULL) {
			node=new AVL_Node<Tree_Data>(x);
			return;
		}
		if (x<node->dat) {
			insert_node(node->lcd,x);
			if ((node->lcd->height())-(node->rcd->height())==2) {
				if (x<node->lcd->dat) {
					RotateRight(node);
				}
				else{RotateLR(node);}
			}
		}
		else if (x>node->dat) {
			insert_node(node->rcd,x);
			if ((node->rcd->height())-(node->lcd->height())==2) {
				if (x>node->rcd->dat) {
					RotateLeft(node);
				}
				else{RotateRL(node);}
			}
		}
		node->hei=max(node->lcd->height(),node->rcd->height())+1;
		node->siz=node->lcd->size()+node->rcd->size()+1;
	}
	AVL_Node<Tree_Data>* find_node(AVL_Node<Tree_Data> * & node,const Tree_Data &x){
		if (node==NULL) {return NULL;}
		if (x<node->dat){return find_node(node->lcd,x);}
		if (x>node->dat){return find_node(node->rcd,x);}
		return node;
	}
	AVL_Node<Tree_Data>* order_node(AVL_Node<Tree_Data> * & node,const int &order){
		if (node==NULL) {return NULL;}
		int siz_k=node->lcd->size();
		if (order<=siz_k){return order_node(node->lcd,order);}
		else if (order==siz_k+1){return node;}
		else{return order_node(node->rcd,order-siz_k-1);}
	}
	void delete_node(AVL_Node<Tree_Data> * & node,const Tree_Data &x){
		if (node==NULL) {return;}
		if (x<node->dat) {
			delete_node(node->lcd,x);
			if ((node->rcd->height())-(node->lcd->height())==2) {
				if ((node->rcd->lcd->height())<(node->rcd->rcd->height())) {
					RotateLeft(node);
				}
				else{RotateRL(node);}
			}
		}
		else if (x>node->dat) {
			delete_node(node->rcd,x);
			if ((node->lcd->height())-(node->rcd->height())==2) {
				if ((node->lcd->lcd->height())>(node->lcd->rcd->height())) {
					RotateRight(node);
				}
				else{RotateLR(node);}
			}
		}
		else{
			if (node->lcd && node->rcd) {
				AVL_Node<Tree_Data> *tmp=node->rcd;
				while(tmp->lcd){tmp=tmp->lcd;}
				node->dat=tmp->dat;
				delete_node(node->rcd,tmp->dat);
				if ((node->lcd->height())-(node->rcd->height())==2) {
					if ((node->lcd->lcd->height())>(node->lcd->rcd->height())) {
						RotateRight(node);
					}
					else{RotateLR(node);}
				}
			}
			else{
				AVL_Node<Tree_Data> *pfd=node;//pointer for deletion
				if (node->lcd) {node=node->lcd;}
				else{node=node->rcd;}
				delete pfd;
			}
		}
		if (node) {
			node->hei=max(node->lcd->height(),node->rcd->height())+1;
			node->siz=node->lcd->size()+node->rcd->size()+1;
		}
	}
	
	void print_tree_n(const AVL_Node<Tree_Data> *t,const int layer){
    	if(t == NULL ) {return;}
    	if(t->rcd){print_tree_n(t->rcd,layer+1);}
    	for(int i =0;i<layer;i++){cout<<"    ";}
    	cout<<t->dat<<endl;
    	if(t->lcd){print_tree_n(t->lcd,layer+1);}
	}
	void in_order_n(const AVL_Node<Tree_Data> *node){
		if (node==NULL) {return;}
		in_order_n(node->lcd);
		cout<<node->dat<<" ";
		in_order_n(node->rcd);
	}
	void pre_order_n(const AVL_Node<Tree_Data> *node){
		if (node==NULL) {return;}
		cout<<"("<<node->dat<<": ";
		pre_order_n(node->lcd);
		pre_order_n(node->rcd);
		cout<<")";
	}
public:
	inline void in_order(){ in_order_n(root);cout<<endl;}
	inline void pre_order(){pre_order_n(root);cout<<endl;}
	inline void insert(const Tree_Data &x){insert_node(root,x);}
	inline AVL_Node<Tree_Data>* find(const Tree_Data &x){return find_node(root,x);}
	inline AVL_Node<Tree_Data>* order(const int &order){return order_node(root,order);}
	inline void del(const Tree_Data &x){delete_node(root,x);}
	inline void print_tree(){print_tree_n(root,0);}

};