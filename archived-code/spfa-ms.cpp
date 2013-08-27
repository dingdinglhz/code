#include<iostream>
#include<cstdio>
#include<vector>
#include<queue>
#define MAXV 10000
#define INF 1000000000
using namespace std;
struct Edge{
	int v;
	int to;
};
vector<Edge> e[MAXV];
int dist[MAXV];
int cnt[MAXV];
queue<int> buff;
bool done[MAXV]; 
int pre_p[MAXV]; 
int v;
int e_n;
bool spfa(const int st){
	for(int i=0;i<v;i++){
		dist[i]=INF;pre_p[i]=0;cnt[i]=0;
	}
	dist[st]=0;
	buff.push(st);
	done[st]=true;
	cnt[st]=1;
	while(!buff.empty()){
		int tmp=buff.front();
		for (int i=0;i<(int)e[tmp].size();i++){
			Edge *t=&e[tmp][i];
			if(dist[tmp]+(*t).v<dist[(*t).to]){
				dist[(*t).to]=dist[tmp]+(*t).v;
				pre_p[(*t).to]=tmp;
				if(!done[(*t).to]){
					buff.push((*t).to);
					done[(*t).to]=true;
					++cnt[(*t).to];
					if(cnt[(*t).to]>v){
						while(!buff.empty()){buff.pop();}
						return false;
					}
				}
			}
		}
		buff.pop();
		done[tmp]=0;
	}
	return true;
}
void pri_ans(){
	for (int i = 0; i < v; ++i){
		printf("节点0到节点%d的最短距离为%d,前驱为%d\n",i,dist[i],pre_p[i]);
	}
}
int main(int argc, char const *argv[])
{
	scanf("%d%d",&v,&e_n);
	for (int i = 0,x,y,l; i < e_n; ++i){
		scanf("%d%d%d",&x,&y,&l);
		Edge tmp;
		tmp.v=l;
		tmp.to=y;
		e[x].push_back(tmp);
		tmp.to=x;
		e[y].push_back(tmp);
	}
	if(spfa(0)){pri_ans();}
	else{
		printf("出现负环,最短路不存在\n");
	}
	system("pause");
	return 0;
}
/*
6 10
0 4 6
0 1 5
0 2 8
4 5 7
4 1 1
1 5 10
1 2 4
1 3 9
2 3 8
3 5 3

*/
