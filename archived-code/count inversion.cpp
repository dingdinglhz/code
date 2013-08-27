//cpp merge soft -- low 
#include<iostream>
#include<cstdlib>
#include<cstdio>
using namespace std;
#ifndef MERGESORT_SIZE
#define MERGESORT_SIZE 100007
#endif
int data[2][MERGESORT_SIZE];
int n;

void test(int l,int r,bool s){
	printf("l: %d r: %d s: %d\n",l,r,s);
	for (int i = 0; i < n; ++i){printf("%d ",data[0][i]);}
	printf("\n");
	for (int i = 0; i < n; ++i){printf("%d ",data[1][i]);}
	printf("\n");

}

void mergesort(int l,int r,bool s){
	if(l<r){
		//test(l,r,s);
		int mid=(l+r)/2;
		mergesort(l,mid,!s);
		mergesort(mid+1,r,!s);
		//printf("after loading\n");
		//test(l,r,s);
		int i=l,j=mid+1,po=l;
		while(i<=mid && j<=r){
			if(data[!s][i]<data[!s][j]){
				data[s][po]=data[!s][i];
				++i;
			}
			else{
				data[s][po]=data[!s][j];
				++j;
			}
			++po;
		}
		//printf("after picking i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
		if(i<=mid){
			for (;i<=mid;++i,++po){
				data[s][po]=data[!s][i];}
			}
		if(j<=r){
			for (;j<=r;++j,++po){
				data[s][po]=data[!s][j];} 
			}
		//printf("after changing i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
	}
	return;
}
long long find_inversion(int l,int r,bool s){
	if(l<r){
		//test(l,r,s);
		int mid=(l+r)/2;
		long long rt_v=find_inversion(l,mid,!s);
		       rt_v+=find_inversion(mid+1,r,!s);
		//printf("after loading\n");
		//test(l,r,s);
		int i=l,j=mid+1,po=l;
		while(i<=mid && j<=r){
			if(data[!s][i]<data[!s][j]){
				data[s][po]=data[!s][i];
				++i;
			}
			else{
				data[s][po]=data[!s][j];
				++j;
				rt_v+=mid-i+1;
			}
			++po;
		}
		//printf("after picking i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
		if(i<=mid){
			for (;i<=mid;++i,++po){
				data[s][po]=data[!s][i];}
			}
		if(j<=r){
			for (;j<=r;++j,++po){
				data[s][po]=data[!s][j];} 
			}
		//printf("after changing i: %d j: %d po %d\n",i,j,po);
		//test(l,r,s);
		return rt_v;
	}
	else{
		return 0;
	}
}
int main(int argc, char const *argv[])
{
	freopen("IntegerArray.txt","r",stdin);
	//scanf("%d",&n);
	n=100000;
	int nn;
	for (int i = 0; i < n; ++i)
	{
		scanf("%d",&nn);
		data[0][i]=nn;
		data[1][i]=nn;
	}
	cout<<find_inversion(0,n-1,0)<<endl;
	//test(-1,-1,0);
	system("pause");
	return 0;
}
/*
8
1 3 8 4 5 7 6 2
 17
13 2 9 4 6 8 8 8 12 5 10 11 11 11 1 7 3 
17
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
17
17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
*/
