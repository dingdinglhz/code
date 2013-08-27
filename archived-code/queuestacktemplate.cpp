#include <iostream>
#include <cstdio>
#include <stdlib.h>
#include "queuestacktemplate.h"
using namespace std;

int main(){
    queue<int> q(15);
    int k;int n;cin>>n;
    for(int i=1;i<=n;i++){
            scanf("%d",&k);
            q.push(k);
            q.test();
            }
    for(int i=1;i<=n;i++){
            q.pop();
            q.test();
            }
    system("pause");
    return 0;
}
