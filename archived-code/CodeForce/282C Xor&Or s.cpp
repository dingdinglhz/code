//Xor and Or
//code force 282C
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cstring>
using namespace std;
int main(int argc, char const *argv[]) {
    string s,t;
    cin>>s>>t;
    if(s==t){cout<<"YES";return 0;}
    if(s.length()!=t.length()){cout<<"NO";return 0;}
    if (s.find('1')==string::npos){cout<<"NO";return 0;}
    if (t.find('1')==string::npos){cout<<"NO";return 0;}
    cout<<"YES";return 0;
}