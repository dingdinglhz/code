#include<iostream>
#include<string>
using namespace std;
#ifndef lomath_h
#define lomath_h
struct lomath{
       unsigned char s[255];
       unsigned char size;
       lomath();
       void ilomath();
};
lomath::lomath(){
   size=0;
   memset(s,0,sizeof(s));
}
void lomath::ilomath(){
   string lomath_str;
   cin>>lomath_str;
   for(int i=0;i<lomath_str.size();i++){
            s[i]=lomath_str[lomath_str.size()-i-1]-'0';
            }
   size=lomath_str.size()-1;
}
ostream& operator<<(ostream& out,lomath lomath_s){
   for(int i=lomath_s.size;i>=0;i--){out<<int(lomath_s.s[i]);}
   return out;
}        
lomath operator+(lomath a,lomath b){
     lomath c;
     int k=max(a.size,b.size);
     for(int i=0;i<=k;i++){
             c.s[i]+=a.s[i]+b.s[i];
             c.s[i+1]+=c.s[i]/10;
             c.s[i]=c.s[i]%10;
             }
     if(c.s[k+1]){c.size=k+1;}
     else{c.size=k;}
     return c;
}
lomath operator*(lomath a,lomath b){
       lomath c;
       for(int i=0;i<=a.size;i++){
             for(int j=0;j<=b.size;j++){
                     c.s[i+j]+=a.s[i]*b.s[i];
                     c.s[i+j+1]+=c.s[i+j]/10;
                     c.s[i+j]=c.s[i+j]%10;
                     }
       }
       int k=a.size+b.size+2;
       while(c.s[k]==0){k--;}
       c.size=k;
       return c;
}
lomath operator-(lomath a,lomath b){
       lomath c;
       int k=min(a.size,b.size);
       if(k==a.size){c=b;}
       else {c=a;}
       for(int i=0;i<=k;i++){
         if(k==b.size){
             if(c.s[i]<b.s[i]){
                c.s[i+1]--;
                c.s[i]+=10-b.s[i];
                }
             else{
                c.s[i]-=b.s[i];
                }
             }
         else{
             if(c.s[i]<a.s[i]){
                c.s[i+1]--;
                c.s[i]+=10-a.s[i];
                }
             else{
                c.s[i]-=a.s[i];
                }
             }
         }
     k=max(a.size,b.size);
     while(c.s[k]==0){k--;}
     c.size=k;
     return c;
}

bool operator>=(lomath a,lomath b){
     if(a.size<b.size){return false;}
     else{
          for(int lobig=a.size;lobig>=0;lobig--){
                  if(a.s[lobig]<b.s[lobig]){return false;}
                  }
          return true;
          }
}

bool operator<=(lomath a,lomath b){
     if(a.size>b.size){return false;}
     else{
          for(int lobig=a.size;lobig>=0;lobig--){
                  if(a.s[lobig]>=b.s[lobig]){return false;}
                  }
          return true;
          }
}

bool operator==(lomath a,lomath b){
     if(a.size!=b.size){return false;}
     else{
          for(int lobig=a.size;lobig>=0;lobig--){
                  if(a.s[lobig]!=b.s[lobig]){return false;}
                  }
          return true;
          }
}

bool operator!=(lomath a,lomath b){
     if(a.size!=b.size){return true;}
     else{
          for(int lobig=a.size;lobig>=0;lobig--){
                  if(a.s[lobig]!=b.s[lobig]){return true;}
                  }
          return false;
          }
}

#endif
