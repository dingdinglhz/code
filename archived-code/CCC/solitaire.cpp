//
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <cmath>
#ifndef MAX_N
#define MAX_N 5000001
#endif
using namespace std;
int f[MAX_N];
int n;
int search(int s){
	if(f[s]!=-1){return f[s];}
	if (s>n) {return 0x3FFFFFFF;}
	int ans=0x3FFFFFFF;
	for (int i = sqrt(s)+1; i >=1; --i){
		if (s%i==0) {
			if(i>1){
				int k=search(s/i*(i-1))+i-1;
				if (k<ans) {
					ans=k;
				}
			}
			int ii=s/i;
			if(ii>1){
				int k=search(s/ii*(ii-1))+ii-1;
				if (k<ans) {
					ans=k;
				}
			}

		}

	}
	//printf("s=%d  ans=%d\n",s,ans);
	f[s]=ans;
	return ans;
}
int main(int argc, char const *argv[]) {
	for (int i = 2; i < MAX_N; ++i){
		f[i]=-1;
	}
	cin>>n;
	cout<<search(n);
	//system("pause");
	return 0;
}
