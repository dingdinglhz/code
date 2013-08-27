//binary heap new_version.h
#include <iostream>
#include <cstdlib>
using namespace std;
#ifndef BINARY_HEAP
#define BINARY_HEAP value
#define MAX_LONG 2147483647
#define MAX_HEAP 100000

template<typename BH_T>
class binary_heap{
		protected:
		long cnt;
		BH_T h[MAX_HEAP];

		binary_heap() {cnt=0;h[0]=BH_T();}//其实h[0]没用 
		inline long pre(long n) {return n>>1;}
		inline long lch(long n) {return n<<1;}
		inline long rch(long n) {return (n<<1)+1;}
	
		public:
		inline long size(){return cnt;}
};
template<typename BH_T>
class BinaryHeap_min : public binary_heap<BH_T>{
	protected:
	using binary_heap<BH_T>::cnt;
	using binary_heap<BH_T>::h;
	using binary_heap<BH_T>::pre;
	using binary_heap<BH_T>::lch;
	using binary_heap<BH_T>::rch;
	public:
	using binary_heap<BH_T>::size;
	void ins(BH_T n){
		int p=++cnt;//元素个数+1，p指向堆底 
		while(p>1 && n<h[pre(p)]){//若没到堆顶 且 待插入元素小于p的父亲 
			h[p]=h[pre(p)];//则把p的父亲移到他儿子的地方(就是p的地方) 
			p=pre(p);//p指向它的父亲 
		}
		h[p]=n;//最后把待插入元素放入p的地方 
	}
	/*BH_T pop(){
		if(cnt==0) return -MAX_LONG;//返回-MAX_LONG视为错误
		long p=1;
		BH_T ans=h[1];
		BH_T tmp=h[cnt--];//取出堆底元素然
		long to;
		while(p<=cnt){//若未到栈底
			to=-MAX_LONG;//init
			if(lch(p)<=cnt && tmp>h[lch(p)]){to=lch(p);}//若有左孩子且比取出的栈底元素小
			if(rch(p)<=cnt && tmp>h[rch(p)] && h[to]>h[rch(p)]){to=rch(p);}//若有右孩子 且 比取出的栈底元素小 且 小于左孩子
			if(to!=-MAX_LONG) {h[p]=h[to];p=to;}//如果比左右孩子都小，就说明找对位置了，把取出的栈底放上去；否则交换
		}
		return ans;
	}*/
	BH_T pop(int p)	{
		BH_T ans=h[p];
		BH_T tmp=h[cnt--];//取出堆底元素然
		if (p>1 && tmp<h[pre(p)]){
			while(p>1 && tmp<h[pre(p)]){//若没到堆顶 且 待插入元素大于p的父亲 
				h[p]=h[pre(p)];//则把p的父亲移到他儿子的地方(就是p的地方) 
				p=pre(p);//p指向它的父亲 
			}
			h[p]=tmp;
		}
		else{
			if(cnt<0) return -MAX_LONG;//返回-MAX_LONG视为错误
			long to;
			while(p<=cnt){//若未到栈底
				to=-MAX_LONG;//init
				if(lch(p)<=cnt && tmp>h[lch(p)]){to=lch(p);}//若有左孩子且比取出的栈底元素小
				if(rch(p)<=cnt && tmp>h[rch(p)] && h[to]>h[rch(p)]){to=rch(p);}//若有右孩子 且 比取出的栈底元素小 且 小于左孩子
				if(to!=-MAX_LONG) {h[p]=h[to];p=to;}//如果比左右孩子都小，就说明找对位置了，把取出的栈底放上去；否则交换
				else{h[p]=tmp;break;}
			}
		}
		return ans;
	}
	void refresh(long p,BH_T n_v)	{
		long tmp=n_v;//取出堆底元素然
		if (p>1 && tmp<h[pre(p)]){
			while(p>1 && tmp<h[pre(p)]){//若没到堆顶 且 待插入元素大于p的父亲 
				h[p]=h[pre(p)];//则把p的父亲移到他儿子的地方(就是p的地方) 
				p=pre(p);//p指向它的父亲 
			}
			h[p]=tmp;
		}
		else{
			if(cnt<0) return;//返回-MAX_LONG视为错误
			//long p=1;
			long to;
			while(p<=cnt){//若未到栈底
				to=-MAX_LONG;//init
				if(lch(p)<=cnt && tmp>h[lch(p)]){to=lch(p);}//若有左孩子且比取出的栈底元素小
				if(rch(p)<=cnt && tmp>h[rch(p)] && h[to]>h[rch(p)]){to=rch(p);}//若有右孩子 且 比取出的栈底元素小 且 小于左孩子
				if(to!=-MAX_LONG) {h[p]=h[to];p=to;}//如果比左右孩子都小，就说明找对位置了，把取出的栈底放上去；否则交换
				else{h[p]=tmp;break;}
			}
		}
		return;
	}
	void output(){
		for (int i = 0; i <=cnt; ++i){
			cout<<h[i]<<" ";
		}cout<<endl;
	}
};
#endif
