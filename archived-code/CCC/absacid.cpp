//absacid.cpp
#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <cmath>
using namespace std;
#ifndef VAL_RANGE
#define VAL_RANGE 1000
#endif
struct Read_Dat{
	int val;
	int freq;
	Read_Dat(){
		val=0;
		freq=0;
	}
};
Read_Dat result[VAL_RANGE];
int n;
void initiate(){
	cin>>n;
	for (int i = 0; i < VAL_RANGE; ++i){
		result[i].val=i+1;
	}
	int vv;
	for (int i = 0; i < n; ++i){
		cin>>vv;
		++result[vv-1].freq;
	}
}
int cmp(const void *a, const void *b)  {
	Read_Dat *ai=(Read_Dat*)(a);
	Read_Dat *bi=(Read_Dat*)(b);
	return (bi->freq)-(ai->freq);
}
void show(){
	for (int i = 0; i < VAL_RANGE; ++i){
		printf("val: %d freq: %d\n",result[i].val,result[i].freq);
	}
}
int max_abs(){
	int tmp=0,freq_j=result[1].freq,val_j=result[0].val;
	for (int i = 1; i < VAL_RANGE; ++i){
		if(result[i].freq!=freq_j){break;}
		tmp=max( tmp,abs(result[i].val-val_j) );
	}
	return tmp;
}
int main(int argc, char const *argv[]) {
	//freopen("acid.txt","w",stdout);
	initiate();
	//show();
	qsort(result,VAL_RANGE,sizeof(Read_Dat),cmp);
	//show();
	cout<<max_abs();
	//system("pause");
	return 0;
}

