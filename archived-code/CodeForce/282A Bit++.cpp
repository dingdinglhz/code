//bit++ 
//code force 282A
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
using namespace std;
int main(int argc, char const *argv[]) {
	string s;
	int n,x=0;
	cin>>n;
	for (int i = 0; i < n; ++i){
		cin>>s;
		if (s.find('+')!=string::npos) {
			++x;}
		else{--x;}
	}
	cout<<x;
	return 0;
}