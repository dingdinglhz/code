//coin game
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cmath>
#include <cstring>
#include <vector>
#include <algorithm>
using namespace std;
using namespace std;
#ifndef NUM
#define NUM 8
#endif
bool visited [16777216];
int n;
struct Coin_Game {
	vector<int> co_v[NUM]; // coin vector
	int size;
	Coin_Game(){
		size=0;
	}
	Coin_Game(int in_si){size=in_si;}
	~Coin_Game(){}
	int to_int(){
		int ans=0;
		for (int i=0,k=1; i<size;++i,k*=size){
			for (std::vector<int>::iterator j = co_v[i].begin(); j != co_v[i].end(); ++j){
				ans+=(*i)*k;
			}
		}
		return ans;
	}
};

queue<Coin_Game> que;
void bfs(){
	while(!que.empty){
		Coin_Game &n_n=que.front();//next node
		for (int i = 0; i < n_n.size; ++i){
			if(i>0 && (n_n.co_v[i].back() > n_n.co_v[i-1].back()) ){
				Coin_Game tmp(n);
				
			}
		}
	}
}
int main(){
	return 0;
}