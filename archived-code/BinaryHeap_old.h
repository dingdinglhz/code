#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <vector>
using namespace std;
/*
This heap template requires defination of operator> and operator<
*/
#ifndef BINARYHEAP
#define BINARYHEAP

template <typename BH_d>
struct BinaryHeap_min{
	vector<BH_d> data;
	int tail;
	BinaryHeap_min(){
		data.push_back(BH_d());
		tail=1;
	}
	bool empty(){
		return tail<=1;
	}
	void push(const BH_d &dt_ip){
		data.push_back(dt_ip);
		int i=tail,j;
		++tail;
		while(i>1){
			j=i>>1;
			if(data[i]<data[j]){
				swap(data[i],data[j]);
				i=j;
			}else{
				break;
			}
		}
	}
	BH_d pop(){
		return pop(1);
	}
	BH_d pop(int st_p){
		if(st_p>=tail){
			return BH_d();
		}
		--tail;
		BH_d rt_da=data[st_p];
		refresh(st_p,data[tail]);
		data.pop_back();
		return rt_da;
	}
	void refresh(int st_p,const BH_d &new_value){
		data[st_p]=new_value;
		if(data[st_p]<data[st_p>>1] && (st_p>1)){
			int j;
			while(st_p>1){
				j=st_p>>1;
				if(data[st_p]<data[j]){
					swap(data[st_p],data[j]);
					st_p>>=1;
				}else{
					break;
				}
			}
		}
		else{
			while((st_p<<1)<tail){
				if( (data[st_p<<1]<data[(st_p<<1)+1]) || ((st_p<<1)==tail-1) ){
					if(data[st_p<<1]<data[st_p]){
						swap(data[st_p<<1],data[st_p]);
						st_p<<=1;
					}else{
						break;
					}
				}else{
				if(data[(st_p<<1)+1]<data[st_p]){
						swap(data[(st_p<<1)+1],data[st_p]);
						st_p=(st_p<<1)+1;
					}else{
						break;
					}
				}
			}
		}
	}
	void print_test(){
		for (int i = 0; i < tail; ++i){
			cout<<data[i]<<" ";
		}
		cout<<endl;
	}
	~BinaryHeap_min(){};
};
/*template <typename BH_d>
class BinaryHeap_max
{
	BH_d data[2048];
	int tail;
public:
	BinaryHeap_max(){
		tail=1;
		memset(data,0,sizeof(int)*2048);
	}
	void push(BH_d dt_ip){
		data[tail]=dt_ip;
		int i=tail,j;
		++tail;
		while(i>1){
			j=i>>1;
			if(data[i]>data[j]){
				swap(data[i],data[j]);
				i=j;
			}else{
				break;
			}
		}
	}
	BH_d pop(){
		--tail;int st_p=1;
		BH_d rt_da=data[1];
		data[1]=data[tail];
		while((st_p<<1)<tail){
			if( (data[st_p<<1]>data[(st_p<<1)+1]) || ((st_p<<1)==tail) ){
				if(data[st_p<<1]>data[st_p]){
					swap(data[st_p<<1],data[st_p]);
					st_p<<=1;
				}else{
					break;
				}
			}else{
				if(data[(st_p<<1)+1]>data[st_p]){
					swap(data[(st_p<<1)+1],data[st_p]);
					st_p=(st_p<<1)+1;
				}else{
					break;
				}
			}
		}
		return rt_da;
	}
	void print_test(){
		for (int i = 0; i < tail+1; ++i){
			printf("%d ",data[i]);
		}
		printf("\n");
	}

	~BinaryHeap_max(){};
};*/
#endif
