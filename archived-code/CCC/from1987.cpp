//
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
using namespace std;
bool distinct_judge(int num){
	bool list[10];
	for (int i = 0; i < 10; ++i){
		list[i]=false;
	}
	while(num>0){
		if(list[num%10]){return false;}
		else{
			list[num%10]=true;
			num/=10;
		}
	}
	return true;
}
int main(int argc, char const *argv[]) {
	int y;
	cin>>y;
	++y;
	while(!distinct_judge(y)){++y;}
	cout<<y;
	system("pause");
	return 0;
}