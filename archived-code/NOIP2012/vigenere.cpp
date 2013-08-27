#include <iostream>
#include <cstring>
#include <cctype>
#include <cstdio>
#include <cstdlib>

using namespace std;
string k,m,c;

 int main(int argc, char const *argv[])
{
	cin>>k>>c;
	m="";
	int ks=k.size(),cs=c.size();
	for (int i = 0; i < cs; ++i)
	{
		int ka=0,ma=0,ca=0;
		/*=(m[i]-'A'+k[i%ks]-'A')%26;*/
		if(isupper(k[i%ks])){ka=k[i%ks]-'A';}
		else{ka+=k[i%ks]-'a';}
		if(isupper(c[i])){
			ca=c[i]-'A'+26;
			ma=(ca-ka)%26+'A';
			
		}
		else{
			ca=c[i]-'a'+26;
			ma=(ca-ka)%26+'a';
			
		}
		//printf("ka %d ca %d ma %d \n",ka ,ca,ma);
		m+=char(ma);
	}
	cout<<m;
	return 0;
}
