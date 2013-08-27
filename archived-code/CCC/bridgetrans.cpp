//
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
#ifndef MAX_N
#define MAX_N 100009
#endif
using namespace std;
int w,n;
int cars[MAX_N];
int main(int argc, char const *argv[]) {
	memset(cars,0,sizeof(cars));
	cin>>w>>n;
	for (int i = 4; i < n+4; ++i){
		cin>>cars[i];
	}
	int k=0,i;
	bool success=true;
	for (i = 0; i <=n; ++i){
		//printf("i=%d,k=%d\n",i,k);
		if (k>w) {
			cout<<i-1;
			success=false;
			break;
		}
		else{
			k-=cars[i];
			k+=cars[i+4];
		}
	}
	if (success) {
		cout<<n;
	}
	//system("pause");
	return 0;
}
/*
100
6
50
30
10
10
40
50
*/
/*
100
6
50
30
10
10
40
30
*/