#include <iostream>  
#include <cstdio>  
#include <cstdlib>  
#include <cstring>  
#include "BinaryHeap.h"
#ifndef INF
#define INF 1000000000
#endif
#ifndef EDGE_L
#define EDGE_L 1000
#endif
using namespace std;
//a-star

struct Point{
	int x,y;
	int f;
	Point(int i_x,int i_y){
		x=i_x; y=i_y; f=INF;
	}
	Point(){
		x=0; y=0; f=INF;
	}
};
class BinaryHeap_min_Astar
{
	Point data[2048];
	int tail;
public:
	BinaryHeap_min_Astar(){
		tail=1;
		memset(data,0,sizeof(int)*2048);
	}
	void push(Point dt_ip){
		data[tail]=dt_ip;
		int i=tail,j;
		++tail;
		while(i>1){
			j=i>>1;
			if(data[i]<data[j]){
				swap(pointer[data[i].x][data[i].y],pointer[data[j].x][data[j].y]);
				swap(data[i],data[j]);
				i=j;
			}else{
				break;
			}
		}
	}
	Point pop(){
		return pop(1);
	}
	Point pop(int st_p){
		if(st_p>=tail){return NULL;}
		--tail;
		Point rt_da=data[st_p];
		data[st_p]=data[tail];
		if(data[st_p]<data[st_p>>1]){
			int j;
			while(st_p>1){
				j=st_p>>1;
				if(data[st_p]<data[j]){
					swap(pointer[data[st_p].x][data[st_p].y],pointer[data[j].x][data[j].y]);
					swap(data[st_p],data[j]);
					st_p=j;
				}else{
					break;
				}
			}
		}
		else{
			while((st_p<<1)<tail){
				if( (data[st_p<<1]<data[(st_p<<1)+1]) || ((st_p<<1)==tail) ){
					if(data[st_p<<1]<data[st_p]){
						swap(pointer[data[st_p<<1].x][data[st_p<<1].y],pointer[data[st_p].x][data[st_p].y]);
						swap(data[st_p<<1],data[st_p]);
						st_p<<=1;
					}else{
						break;
					}
				}else{
				    if(data[(st_p<<1)+1]<data[st_p]){
				    	swap(pointer[data[(st_p<<1)+1].x][data[(st_p<<1)+1].y],pointer[data[st_p].x][data[st_p].y]);
						swap(data[(st_p<<1)+1],data[st_p]);
						st_p=(st_p<<1)+1;
					}else{
						break;
					}
				}
			}
		}
		return rt_da;
	}
	void change(int st_p,Point in_da){
		data[tail]=in_da;
		++tail;
		pop(st_p);
	}
	void print_test(){
		for (int i = 0; i < tail+1; ++i){
			printf("%d ",data[i]);
		}
		printf("\n");
	}

	~BinaryHeap_min_Astar(){};
};

bool operator<(const Point p_a,const Point p_b){
	return p_a.f<p_b.f;
}
bool operator>(const Point p_a,const Point p_b){
	return p_a.f>p_b.f;
}
int g[EDGE_L][EDGE_L];
int pointer[EDGE_L][EDGE_L];
BinaryHeap_min a_star_heap;
void initial(){
	memset(g      ,0,sizeof(int)*EDGE_L*EDGE_L);
	memset(pointer,0,sizeof(int)*EDGE_L*EDGE_L);
}
void a_star(Point start,Point end){
	
}