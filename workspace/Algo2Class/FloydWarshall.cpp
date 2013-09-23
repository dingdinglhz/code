//FloydWarshall.cpp
#include <iostream>
#include <cstdlib>
#include <cstdio>
using namespace std;
#define MAX_VAL 0x0F000000
#define MAX_V 1010
int n,m;
int dis[MAX_V][MAX_V];
void show(){
	cout<<endl;
	for (int i =1; i <=n; ++i){
		for (int j = 1; j <=n; ++j){
			cout<<dis[i][j]<<" ";
		}
		cout<<endl;
	}
}
int main(int argc, char const *argv[]){
	freopen("g3.txt","r",stdin);
	//freopen("log.txt","w",stdout);
	cin>>n>>m;
	for (int i =0; i <=n; ++i){
		for (int j = 0; j <=n; ++j){
			dis[i][j]=MAX_VAL;
		}
		dis[i][i]=0;
	}
	for (int i = 0,x,y,cost; i < m; ++i){
		cin>>x>>y>>cost;
		dis[x][y]=cost;
	}
	//show();
	for (int k = 1; k <=n; ++k){
		for (int i = 1; i <=n; ++i){
			for (int j = 1; j <=n; ++j){
				if((dis[i][k]+dis[k][j])<dis[i][j]){
					dis[i][j]=dis[i][k]+dis[k][j];
				}
			}
		}
		//cout<<"k="<<k<<"fin; ";
		//show();
	}
	system("pause");
	//show();
	printf("g3\n");
	for (int i = 1; i <=n; ++i){
		if (dis[i][i]<0){
			cout<<"negative edge cycle exists!\n";
			system("pause");
			return 0;
		}
	}
	int minDis=MAX_VAL;
	for (int i = 1; i <=n; ++i){
		for (int j = 1; j <=n; ++j){
			minDis=min(minDis,dis[i][j]);
		}
	}
	cout<<minDis<<endl;
	system("pause");
	return 0;
}
/*
6 7
1 2 -2
2 3 -1
3 1 4
3 5 2
3 6 -3
4 5 1
4 6 -4
 */