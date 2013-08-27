#include <iostream>
#include <cstdio>
#include <stdlib.h>
#ifndef QUEUE_AND_STACK 
#define QUEUE_AND_STACK 
using namespace std;
template <typename queue_d>
class queue{
      private:
        queue_d nu[2000];
        int he,ta,len,q/*ÈÝÁ¿*/;
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
      void push(queue_d x){
           if(full){return;}
           empty=false; 
           nu[ta]=x;ta++;ta%=q;len++;
           if(len==q){full=true;}
      }
      
      queue_d pop(){
          if(empty){return 0;}
          full=false;
          queue_d i=nu[he];
          nu[he]=0;
          he++;he%=q;len--;
          if(len==0){empty=true;}
          return i;
      }
      void test (){
           printf("head:%d  tail:%d  length:%d\n",he,ta,len);
           for(int i=0;i<q;i++){ cout<<i<<"--"<<nu[i]<<"  "; }
           cout<<empty<<full<<endl;
           }
};
template <typename stack_d>
class stack{
      private:
              stack_d nu[2000];
              int ta,q;
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
      void push(stack_d x){
           if (full){return;}
           if(empty){empty=false;}
           nu[ta]=x;ta++;
           if(ta==q-1){
              full=true;
           }
      }
      
      stack_d pop(){
          if (empty){return 0;}
          if (full){full=false;}
          ta--;
          stack_d i=nu[ta];
          nu[ta]=0;
          if(ta==0){
              empty=true;
              }
          return i;
      }
      void test (){
           printf("tail:%d",ta);
           for(int i=0;i<q;i++){ cout<<i<<"--"<<nu[i]<<"  "; }
           cout<<empty<<full<<endl;
           }
};
#endif
