#include <iostream>  
#include <cstdio>  
#include <cstdlib>  
#include <cstring>  
#include <algorithm>  
  
using namespace std;
struct test{
       void tes(){
             if(this==NULL){cout<<"null\n";}
             else{cout<<"success";}
             }
};
int main(){
    cout<<"hello world";
    test *p=NULL;
    p->tes();
    system("pause");
    return 0;
}
