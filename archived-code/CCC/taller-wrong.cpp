//
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
#ifndef MAX_N
#define MAX_N 1000001
#endif
using namespace std;
bool map_t[MAX_N][MAX_N];
int n,m,d;
bool search_t(int x,int y){
	if (map_t[x][y]=='t') {return true;}
	else{
		for (int i = 1; i <=n; ++i){
			if (map[x][i]=='t') {
				if (search_t(i,y)) {
					return true;
				}
			}
		}
	}
}
bool search_s(int x,int y){
	if (map_t[x][y]=='s') {return true;}
	else{
		for (int i = 1; i <=n; ++i){
			if (map[x][i]=='s') {
				if (search_t(i,y)) {
					return true;
				}
			}
		}
	}
}
int main(int argc, char const *argv[]) {
	cin>>n>>m;
	for (int i = 0; i <=n; ++i){
		for (int j = 0; j <=n; ++j){
			map_t[i][j]='u';
		}
	}
	int x,y;
	for (int i = 0; i < m; ++i){
		cin>>x>>y;
		map_t[x][y]='t';
		map_t[y][x]='s';
	}
	cin>>x>>y;
	int re=search(x,y);
	return 0;
}