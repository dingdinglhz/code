//
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
#ifndef TEAM_N
#define TEAM_N 4
#endif
using namespace std;
int game_m[6][2]={{1,2},{1,3},{1,4},{2,3},{2,4},{3,4}};
int score[5]={0,0,0,0,0};
bool past[5][5];
int t,g,ans=0;
void search(int s){
	if (s==6) {
		for (int i = 1; i <=4; ++i){
			if (i==t) {continue;}
			if (score[i]>=score[t]) {
				return;
			}
		}
		++ans;
	}
	else{
		if (past[ game_m[s][0] ][ game_m[s][1] ]) {
			search(s+1);
		}
		else{
			score[game_m[s][0]]+=3;
			search(s+1);
			score[game_m[s][0]]-=3;
			score[game_m[s][1]]+=3;
			search(s+1);
			score[game_m[s][1]]-=3;
			score[game_m[s][0]]+=1;
			score[game_m[s][1]]+=1;
			search(s+1);
			score[game_m[s][0]]-=1;
			score[game_m[s][1]]-=1;
		}
	}
}
int main(int argc, char const *argv[]) {
	cin>>t>>g;
	int a,b,sa,sb;
	memset(past,0,sizeof(past));
	for (int i = 0; i < g; ++i){
		cin>>a>>b>>sa>>sb;
		past[a][b]=true;
		past[b][a]=true;
		if (sa>sb) {
			score[a]+=3;
			continue;
		}
		if (sa<sb){
			score[b]+=3;
			continue;
		}
		if (sa==sb) {
			score[a]+=1;
			score[b]+=1;
			continue;
		}
	}
	search(0);
	cout<<ans;
	//system("pause");
	return 0;
}
/*
3
3
1 3 7 5
3 4 0 8
2 4 2 2
*/
/*
3
4
1 3 5 7
3 4 8 0
2 4 2 2
1 2 5 5
*/