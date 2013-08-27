#include<iostream>
#include<cstdio>
#include<stdlib.h>
#include"queuestack.h"
using namespace std;

/*class queue{
      private:
              int nu[2000],he,ta,len,q;
              bool full,empty;
      public:
      queue(){
         he=1;ta=1;len=0;q=1999;
         full=false;empty=true;
      }
      queue(int q_i){
         he=1;ta=1;len=0;q=q_i;
         full=false;empty=true;
      }
      /////////////
      void push(int x){
           if (full){return;}
           nu[ta]=x;ta++;ta%=q;len++;
           if(ta==he){
              if(len==q){full=true;}
              else{empty=true;}
           }
           else{empty=false;full=false;}
      }
      
      int pop(){
          if(empty){return 0;}
          int i=nu[he];nu[he]=0;he++;he%=q;len--;
          if(ta==he){
              if(len==q){full=true;}
              else{empty=true;}
              }
          else{empty=false;full=false;}
          return i;
      }
      void test (){
           printf("head:%d  tail:%d  length:%d\n",he,ta,len);
           for(int i=0;i<q;i++){ printf("%d--%d  -  ",i,nu[i]); }
           cout<<empty<<full<<endl;
           }
};

class stack{
      private:
              int nu[2000],ta,q;
              bool full,empty;
      public:
      stack(){
         ta=0;q=2000;
         full=false;empty=true;
      }
      stack(int q_i){
         ta=0;q=q_i;
         full=false;empty=true;
      }
      /////////////
      void push(int x){
           if (full){return;}
           if(empty){empty=false;}
           nu[ta]=x;ta++;
           if(ta==q-1){
              full=true;
           }
      }
      
      int pop(){
          if (empty){return 0;}
          if (full){full=false;}
          ta--;
          int i=nu[ta];
          nu[ta]=0;
          if(ta==0){
              empty=true;
              }
          return i;
      }
      void test (){
           printf("tail:%d",ta);
           for(int i=0;i<q;i++){ printf("%d--%d  -  ",i,nu[i]); }
           cout<<empty<<full<<endl;
           }
};*/
int main(){
    queue q(15);int k;int n;cin>>n;
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
