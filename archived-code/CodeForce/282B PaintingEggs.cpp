//painting eggs 
//code force 282B
#include <iostream>
#include <cstdlib>
#include <cstdio>
using namespace std;
#ifndef MAXN
#define MAXN 1000001
#endif
int n;
int main(int argc, char const *argv[]) {
	cin>>n;
	int a,g,sa=0,sg=0;
	for (int i = 0; i < n; ++i){
		cin>>a>>g;
		if (a+g!=1000) {cerr<<"invalid input\n";}
		if (abs(sa+a-sg)<=500){
			sa+=a;printf("A");
		}else{
			sg+=g;printf("G");
		}
	}
	//system("pause");
	return 0;
}