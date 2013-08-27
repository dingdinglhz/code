//kmp.cpp
#include <iostream>
#include <cstdlib>
#include <cstring>
using namespace std;
int main(int argc, char const *argv[]) {
	string a,b;//a:for matching; b:matching key;
	cin>>a>>b; a=" "+a;b=" "+b;
	int p[b.length()+1];
	int i,j,n=a.length()-1,m=b.length()-1;
	p[1]=0;j=0;
	for (i=2;i<=m;++i){
		p[i]=0;
		while(j>0 && (b[j+1]!=b[i])){j=p[j];}
		if (b[j+1]==b[i]) {++j;}
		p[i]=j;
	}
	j=0;
	for (i=1;i<=n;++i){
		while(j>0 && b[j+1]!=a[i]){j=p[j];}
		if (b[j+1]==a[i]) {++j;}
		if (j==m) {
			cout<<"success matching in"<<i-m;
			j=p[j];
		}
	}
	system("pause");
	return 0;
}
/*
abababaababacb ababacb
*/
