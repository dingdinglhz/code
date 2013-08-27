//mousejourney.cpp
#include <iostream>
#include <cstdlib>
using namespace std;
int f[27][27];
bool cat[27][27];
int r,c,k;
int main(int argc, char const *argv[]) {
	//initiate
	cin>>r>>c>>k;
	for (int i=0; i <=r; ++i){
		for (int j =0; j <=c; ++j){
			f[i][j]=0;
			cat[i][j]=false;
		}
	}
	int catx,caty;
	for (int i = 0; i < k; ++i){
		cin>>catx>>caty;
		cat[catx][caty]=true;
	}
	//computing
	f[0][1]=1;
	for (int i=1; i <=r; ++i){
		for (int j =1; j <=c; ++j){
			if (!cat[i][j]) {
				f[i][j]=f[i-1][j]+f[i][j-1];
			}
			else{f[i][j]=0;}
		}
	}
	/////
	cout<<f[r][c];
	//system("pause");
	return 0;
}