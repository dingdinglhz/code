// aromnum.cpp
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cmath>
#include <cstring>
using namespace std;

string s;
int result;
int tellbase(char sym){
	switch(sym){
		case 'I': return 1;
		case 'V': return 5;
		case 'X': return 10;
		case 'L': return 50;
		case 'C': return 100;
		case 'D': return 500;
		case 'M': return 1000;
		default : return 0;
	}
}
int main(int argc, char const *argv[]) {
	cin>>s;
	result=0;
	for (int i = 0; i < s.size(); i+=2){
		int di=s[i]-'0';
		int bi=tellbase(s[i+1]);
		if ( (i+2<s.size()) && (bi<tellbase(s[i+3])) ) {
			di=-di;
		}
		result+=di*bi;
	}
	cout<<result<<endl;
	//system("pause");
	return 0;
}