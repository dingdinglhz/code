#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <set>
#include <algorithm>
using namespace std;
#ifndef MAX_SIZE
#define MAX_SIZE 100001
#endif
int data[MAX_SIZE];
int n,k,n_t;
int fmlpivot(int l,int r){
    int midpivot=(l+r)/2;
    if(data[l]>data[r] && data[r]>data[midpivot]){return r;}
    if(data[midpivot]>data[r] && data[r]>data[l]){return r;}
    if(data[r]>data[l] && data[l]>data[midpivot]){return l;}
    if(data[midpivot]>data[l] && data[l]>data[r]){return l;}
    return midpivot;
}
void qsort(int l,int r){
    //int rt=0;
    if(l<r){
        int rd=rand()%(r-l+1)+l;
        //int rd=fmlpivot(l,r);
        int tmp=data[l];
        int pivot=data[rd];
        data[l]=pivot;
        data[rd]=tmp;
        int i=l+1;
        for (int j=l+1;j<=r;j++){
            if (data[j] < pivot) {
                tmp=data[j];
                data[j]=data[i];
                data[i]=tmp;
                ++i;
            }
        }
        tmp=data[l];
        data[l]=data[i-1];
        data[i-1]=tmp;
        qsort(l,i-2);
        qsort(i,r);
        //rt+=qsort(l,i-2);
        //rt+=qsort(i,r);
        //rt+=r-l;
        
    }
    //return rt;
}
int kth_num(int l,int r,int k){
    if(l<r){
        int rd=rand()%(r-l+1)+l;
        //int rd=fmlpivot(l,r);
        int tmp=data[l];
        int pivot=data[rd];
        data[l]=pivot;
        data[rd]=tmp;
        int i=l+1;
        for (int j=l+1;j<=r;j++){
            if (data[j] < pivot) {
                tmp=data[j];
                data[j]=data[i];
                data[i]=tmp;
                ++i;
            }
        }
        tmp=data[l];
        data[l]=data[i-1];
        data[i-1]=tmp;
        if (k==i-1) {
            return data[k];
        }
        if (k>(i-1)) {
            return kth_num(i,r,k);
        }
        if (k<(i-1)) {
            return kth_num(l,i-2,k);
        }
        return -1;
        //rt+=qsort(l,i-2);
        //rt+=qsort(i,r);
        //rt+=r-l;
        
    }
    //return rt;
}
//set<int> ss;
/*void input(){
    cin>>n>>k;
    int ii;n_t=0;
    for (int i = 0; i < n; ++i){
        cin>>ii;
        if (ss.find(ii)==ss.end()) {
            ss.insert(ii);
            data[n_t]=ii;
            ++n_t;
        }
    }
}*/
void input(){
    cin>>n_t>>k;
    for (int i = 0; i < n_t; ++i){
        cin>>data[i];
    }
}
void show(){
    for (int i = 0; i < n_t; ++i){
        printf("%d ", data[i]);
    }printf("\n");
}
int main(int argc, char const *argv[]) {
    srand( (unsigned)time( NULL ) );
    input();
    //show();
    //system("pause");
    cout<<kth_num(0,n_t-1,n_t-k)<<endl;
    //show();
    //system("pause");
    return 0;
}

/*
 17
13 2 9 4 6 8 8 8 12 5 10 11 11 11 1 7 3 
17
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
17
17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
 * 
 */
