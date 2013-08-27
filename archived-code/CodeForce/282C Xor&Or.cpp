//Xor and Or
//code force 282C
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
using namespace std;
int compute(bool i1,bool i2,bool target){
	if (i1) {
		if (i2) {return !target;}
		else{
			if (target) {return 1;}
			else{return -1;}
		}
	}
	else{
		if (i2) {
			if (target) {return 1;}
			else{return -1;}
		}
		else{
			if (target) {return -1;}
			else{return 0;}
		}
	}
}
string s,t;//source target
int main(int argc, char const *argv[]) {
	cin>>s>>t;
	int l=s.length()-1;
	bool flag=true;
	for (int i = 0; i <l; ++i){
		int k=compute(s[i]-'0',s[i+1]-'0',t[i]-'0');
		if(k==-1) {flag==false;break;}
		s[i+1]='0'+k;
	}
	if (flag && s[l]==t[l]) {
		cout<<"YES";
	}else{cout<<"NO";}
	//system("pause");
	return 0;
}