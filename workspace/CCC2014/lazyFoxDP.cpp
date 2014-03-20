#include <iostream>
#include <cstdlib>
#include <cmath>
#include <vector>
#include <algorithm>
#define MAX_N 2010
using namespace std;
struct Edge
{
	int to;
	long long dis;
	Edge(){
		to=-1;
		dis=1000000000;
	}
	Edge(int toIn, long long disIn){
		to=toIn;
		dis=disIn;
	}
};
bool operator<(const Edge &a, const Edge &b){
	return a.dis<b.dis;
}
vector< vector<Edge> > graph;

struct Point
{
	long long x,y;
	Point(){
		x=0,y=0;
	}
	Point(long long xin,long long yin){
		x=xin,y=yin;
	}
};
long long dist(const Point &a, const Point &b){
	long long dx=a.x-b.x, dy=a.y-b.y;
	return dx*dx+dy*dy;
}
Point poi[MAX_N];
int n;
void input(){
	cin>>n;
	poi[0]=Point(0,0);
	for (int i = 1; i <=n; ++i){
		cin>>poi[i].x>>poi[i].y;
	}
	graph=vector< vector<Edge> >(n+1, vector<Edge>() );
	for (int i = 0; i <=n; ++i){
		graph[i].reserve(n+10);
		for (int j = 0; j <=n; ++j){
			if (j==i){continue;}
			graph[i].push_back( Edge(j, dist(poi[i],poi[j] ) ) );
		}
		sort(graph[i].begin(), graph[i].end());
	}
	/*for (int i = 0; i <=n; ++i){
		cout<<"From:"<<i<<" -- ";
		for (int j = 0; j <n; ++j){
			cout<<"To:"<<graph[i][j].to<<"dis:"<<graph[i][j].dis<<" - ";
		}
		cout<<endl;
	}*/
}
int score[MAX_N][MAX_N];
int dpScore(int u, int v){
	if (score[u][v]!=-1){
		return score[u][v];
	}
	else{
		long long disLim=dist(poi[u],poi[v]);
		int scoreTmp=0;
		for (int i = 0; i < n; ++i){
			if(graph[u][i].dis<disLim ){
				if (graph[u][i].to!=0){
					scoreTmp=max(scoreTmp,dpScore(graph[u][i].to, u));
				}	
			}
			else{break;}
		}
		score[u][v]=scoreTmp+1;
		//cout<<"score["<<u<<"]["<<v<<"]="<<score[u][v]<<endl;
		return score[u][v];
	}
}
int main(int argc, char const *argv[])
{
	input();
	for (int i = 0; i <=n; ++i){
		for (int j = 0; j <=n; ++j){
			score[i][j]=-1;
			//if(i==j){score[i][j]=0;}
		}
	}
	int maxScore=0;
	for (int i = 1; i <=n; ++i){
		maxScore=max(maxScore,dpScore(i,0));
	}
	cout<<maxScore<<endl;

	/*for (int i = 0; i <=n; ++i){
		for (int j = 0; j <=n; ++j){
			cout<<score[i][j]<<" ";
		}cout<<endl;
	}
	system("pause");*/
	return 0;
}
/*
5
5 8
4 10
3 1
3 2
3 3

*/