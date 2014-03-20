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
	double dis;
	Edge(){
		to=-1;
		dis=1000000.0;
	}
	Edge(int toIn, double disIn){
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
	double x,y;
	Point(){
		x=0,y=0;
	}
	Point(double xin,double yin){
		x=xin,y=yin;
	}
};
double dist(const Point &a, const Point &b){
	double dx=a.x-b.x, dy=a.y-b.y;
	return sqrt(dx*dx+dy*dy);
}
Point poi[MAX_N];
int n;
void input(){
	cin>>n;
	for (int i = 0; i <n; ++i){
		cin>>poi[i].x>>poi[i].y;
	}
	graph=vector< vector<Edge> >(n, vector<Edge>() );
	for (int i = 0; i < n; ++i){
		for (int j = 0; j < n; ++j){
			if (j==i){continue;}
			graph[i].push_back( Edge(j, dist(poi[i],poi[j] ) ) );
		}
		sort(graph[i].begin(), graph[i].end());
	}
	/*for (int i = 0; i < n; ++i){
		cout<<"From:"<<i<<" -- ";
		for (int j = 0; j < n-1; ++j){
			cout<<"To:"<<graph[i][j].to<<"dis:"<<graph[i][j].dis<<" - ";
		}
		cout<<endl;
	}*/
}
int score=0,maxScore=0;
void dfs(int v,double disLim){
	bool flag=true;
	++score;
	for (std::vector<Edge>::iterator i = graph[v].begin(); i != graph[v].end(); ++i)
	{
		if( (*i).dis<disLim ){
			flag=false;
			dfs((*i).to,(*i).dis);
		}
	}
	if (flag && score>maxScore){
		maxScore=score;
	}
	--score;
}
int main(int argc, char const *argv[])
{
	input();
	Point origin(0,0);
	for (int i = 0; i < n; ++i){
		dfs(i, dist(origin,poi[i]) );
	}
	cout<<maxScore;
	//system("pause");
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