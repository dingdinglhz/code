//permutation generator 
#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
using namespace std;
#ifndef PERMU_LIMIT
#define PERMU_LIMIT 100
#endif
int num[PERMU_LIMIT];
bool direction[PERMU_LIMIT];//flase:left  true:right
int n;
int max_n,max_m;
void fi_ne_mo(){//find next move
	max_n=0,max_m=0;
	for (int i=1; i <=n; ++i){
		if(direction[i]){
			if(i==n){continue;}
			if(num[i]>num[i+1] && num[i]>max_n){
				max_n=num[i];max_m=i;
			}
		}else{
			if(i==1){continue;}
			if(num[i]>num[i-1] && num[i]>max_n){
				max_n=num[i];max_m=i;
			}
		}
	}
}
void output(){
	for (int i = 1; i <=n; ++i){
		printf("%d ", num[i]);
	}printf("\n");
	/*for (int i = 1; i <=n; ++i){
		printf("%d ", direction[i]);
	}printf("\n");*/
}
int main(int argc, char const *argv[])
{
	cin>>n;
	for (int i =0; i <=n; ++i){
		num[i]=i;direction[i]=0;
	}
	fi_ne_mo();
	while(max_m){
		output();
		if (direction[max_m]){
			swap(num[max_m],num[max_m+1]);
			swap(direction[max_m],direction[max_m+1]);
		}else{
			swap(num[max_m],num[max_m-1]);
			swap(direction[max_m],direction[max_m-1]);
		}
		
		for (int i = 1; i <=n; ++i){
			if (num[i]>max_n){
				direction[i]=!direction[i];
			}
		}
		//output();
		//system("pause");
		fi_ne_mo();
	}
	system("pause");
	return 0;
}
