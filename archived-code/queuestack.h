#include<iostream>
#include<stdio.h>
using namespace std;
#ifndef QUEUE_AND_STACK 
#define QUEUE_AND_STACK 
class queue{
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
           if(full){return;}
           empty=false; 
           nu[ta]=x;ta++;ta%=q;len++;
           if(len==q){full=true;}
      }
      
      int pop(){
          if(empty){return 0;}
          full=false;
          int i=nu[he];nu[he]=0;he++;he%=q;len--;
          if(len==0){empty=true;}
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
};
#endif
