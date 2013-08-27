#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
//#include <sstream>
using namespace std;
#ifndef MAX_N
#define MAX_N 20
#endif
#ifndef MAX_F
#define MAX_F 268435456
#endif
int f[MAX_N][MAX_N];
int solution[MAX_N][MAX_N];
int out[MAX_N];
int n;
/*string showing(int l,int r){
	if (l==r) {
		string s=""+f[l][r];
		return s;
	}
	else{
		string s="(";
		s+=showing(l,solution[l][r]);
		s+='+';
		s+=showing(solution[l][r]+1,r);
		s+=')';
		return s;
	}
}*/
void test(){
	printf("F:\n");
	for (int i = 0; i < n; ++i){
		for (int j = 0; j < n; ++j){
			printf("%d ",f[i][j] );
		}printf("\n");
	}printf("\n solution: \n");
	for (int i = 0; i < n; ++i){
		for (int j = 0; j < n; ++j){
			printf("%d ",solution[i][j] );
		}printf("\n");
	}printf("\n");
	system("pause");
}
int main(int argc, char const *argv[]) {
	memset(f,0,sizeof(f));
	memset(solution,0,sizeof(solution));
	cin>>n;
	for (int i = 0; i < n; ++i){
		cin>>f[i][i];
	}
	for (int i = 1; i < n; ++i){
		f[i-1][i]=f[i-1][i-1]+f[i][i];
		solution[i-1][i]=i-1;
	}
	test();
	for (int k = 2; k < n; ++k){
		for (int i = k; i < n; ++i){
			f[i-k][i]=MAX_F;
			for (int j = i-k; j <i; ++j){
				if ((2*f[i-k][j]+f[j+1][i])<f[i-k][i]) {
					f[i-k][i]=2*f[i-k][j]+f[j+1][i];
					solution[i-k][i]=j;
				}
			}
		}
		test();
	}
	cout<<f[0][n-1]<<endl;
	//cout<<showing(0,n-1)<<endl;
	system("pause");
	return 0;
}