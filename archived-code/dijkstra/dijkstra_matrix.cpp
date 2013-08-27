//dijkstra.cpp
#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <vector>
#ifndef INIT_DIS
#define INIT_DIS 1000000
#endif
#ifndef MAX_N
#define MAX_N 201
#endif
#ifndef MAX_LONG
#define MAX_LONG 2147483647 
#endif
using namespace std;
int matrix[MAX_N][MAX_N];//graph matrix
int n;
int disp[MAX_N];// final distance
int v_h_map[MAX_N];//a map of the place of vertex in the heap -2:unvisited -1:visited others:location in heap
struct V_in_Heap {
	int num,dis;
	V_in_Heap(){
		num=0;dis=0;
	}
    V_in_Heap(int n_i,int d_i){
        num=n_i;
        dis=d_i;
    }
    V_in_Heap(const V_in_Heap &v_i){
        num=v_i.num;dis=v_i.dis;
    }
    void out(){
        cout<<"v_"<<num<<"dis="<<dis<<" ";
     }
};
inline bool operator<(const V_in_Heap &i_a,const V_in_Heap &i_b){return i_a.dis<i_b.dis;}
inline bool operator>(const V_in_Heap &i_a,const V_in_Heap &i_b){return i_a.dis>i_b.dis;}

class BiMiHeap_Dijk{
    //protected:
public:
    long cnt;
    V_in_Heap h[MAX_N];
    inline long pre(long n) {return n>>1;}
    inline long lch(long n) {return n<<1;}
    inline long rch(long n) {return (n<<1)+1;}
    public:
    BiMiHeap_Dijk() {cnt=0;h[0]=V_in_Heap();}//其实h[0]没用 
    inline long size(){return cnt;}
    void ins(V_in_Heap n){
        int p=++cnt;//元素个数+1，p指向堆底
        while(p>1 && n<h[pre(p)]){//若没到堆顶 且 待插入元素小于p的父亲 
            h[p]=h[pre(p)];//则把p的父亲移到他儿子的地方(就是p的地方)
            v_h_map[h[p].num]=p;
            p=pre(p);//p指向它的父亲 
        }
        h[p]=n;//最后把待插入元素放入p的地方
        v_h_map[n.num]=p;
    }
    inline V_in_Heap pop() {
        return pop(1);
    }
    inline V_in_Heap pop(int p) {
        if(cnt<p){return V_in_Heap();}
        V_in_Heap ans=h[p];
        ref(p,h[cnt--]);
        return ans;
    }
    void ref(long p,V_in_Heap tmp)  {//refresh
        if (p>1 && tmp<h[pre(p)]){
            while(p>1 && tmp<h[pre(p)]){//若没到堆顶 且 待插入元素大于p的父亲 
                h[p]=h[pre(p)];//则把p的父亲移到他儿子的地方(就是p的地方)
                v_h_map[h[p].num]=p;
                p=pre(p);//p指向它的父亲 
            }
            h[p]=tmp;
            v_h_map[tmp.num]=p;
        }
        else{
            if(cnt<0) return;//返回-MAX_LONG视为错误
            //long p=1;
            long to;
            while(p<=cnt){//若未到栈底
                to=-MAX_LONG;//init
                if(lch(p)<=cnt && tmp>h[lch(p)]){to=lch(p);}//若有左孩子且比取出的栈底元素小
                if(rch(p)<=cnt && tmp>h[rch(p)] && h[to]>h[rch(p)]){to=rch(p);}//若有右孩子 且 比取出的栈底元素小 且 小于左孩子
                if(to!=-MAX_LONG) {h[p]=h[to];v_h_map[h[p].num]=p;p=to;}//如果比左右孩子都小，就说明找对位置了，把取出的栈底放上去；否则交换
                else{h[p]=tmp;v_h_map[tmp.num]=p;break;}
            }
        }
        return;
    }
    void out(){
        for (int i = 0; i <=cnt; ++i){
            h[i].out();
        }cout<<endl;
    }
};
BiMiHeap_Dijk heap;
inline void init(){
    n=MAX_N-1;
    //n=8;
    for (int i = 0; i <=n; ++i){
        disp[i]=INIT_DIS;
        v_h_map[i]=-2;
        for (int j = 0; j <=n; ++j){
            matrix[i][j]=INIT_DIS;
        }
    }
}
void input(){
    ifstream ifs;
    ifs.open("dijkstraData.txt");
    string line;
    stringstream strstream;
    int v, e, c;
    while (getline(ifs, line)) {
        strstream << line;
        strstream >> v;
        while (strstream >> e >> c) {
            matrix[v][e]=c;
        }
        strstream.clear();
    }
    ifs.close();
}
inline void insert(int n_i,int d_i){
    if (v_h_map[n_i]==-2) {
        heap.ins( V_in_Heap(n_i,d_i) );
        return;
    }
    if ( (v_h_map[n_i]!=-1)&&(d_i<(heap.h[v_h_map[n_i]].dis)) ){
        heap.ref(v_h_map[n_i], V_in_Heap(n_i,d_i));
        return;
    }
    
    
}
void test(){
    printf("heap(szie %d):",heap.size());
    heap.out();
    for (int i =0; i <=n; ++i){
        printf("v_%d h_n=%d dis=%d \n",i,v_h_map[i],disp[i]);
    }
}
void dijkstra(int source){
	insert(source,0);
    while(heap.size()){
        V_in_Heap extend=heap.pop();
        if (extend.num==0) {
            cerr<<"a default node poped\n";
        }
        disp[extend.num]=extend.dis;
        v_h_map[extend.num]=-1;
        for (int i =1; i<=n; ++i){
            if (matrix[extend.num][i]<INIT_DIS) {
                insert(i,extend.dis+matrix[extend.num][i]);
            }
        }//test();
    }
}
int ans_need[10]={7,37,59,82,99,115,133,165,188,197};
void heap_test() {
    long tmpa,tmpb;
    char act;
    while(cin>>act){
        switch(act){
            case 'i': {
                cin>>tmpa>>tmpb;
                insert(tmpa,tmpb);
                break;
                }
            case 'p': {
                cin>>tmpb;
                heap.pop(tmpb).out();
                cout<<"poped\n";
                break;
                }
            case 'o': return;
            default: cerr<<"did not recognize";break; 
        }
        test();
    }
    system("pause");
}
int main(int argc, char const *argv[]) {
    //freopen("dijkstra_out.txt","w",stdout);
	init();
    input();
    //heap_test();
    dijkstra(1);
    for (int i = 0; i <=n; ++i){
        cout<<disp[i]<<" ";
    }cout<<endl;
    for (int i = 0; i < 10; ++i){
        cout<<disp[ans_need[i]]<<',';
    }cout<<endl;
    system("pause");
	return 0;
}
