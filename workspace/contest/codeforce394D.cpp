#include <iostream>
#include <cstdlib>
using namespace std;
#ifndef MAX_N
#define MAX_N 1010
#endif
int n;
int height[MAX_N];
int minCost=999999;
int maxStep,stepAns,start;
int cmp (const void * a, const void * b)
{
  if ( *(int*)a <  *(int*)b ) return -1;
  if ( *(int*)a == *(int*)b ) return 0;
  if ( *(int*)a >  *(int*)b ) return 1;
}
int main(int argc, char const *argv[])
{
	cin>>n;
	for (int i = 0; i < n; ++i){
		cin>>height[i];
	}
	qsort(height,n,sizeof(int),cmp);
	maxStep=height[n-1]-height[0];
	for (int step = 0; step <=maxStep; ++step){
		int maxShift=-999999,minShift= 999999;
		for (int i = 0; i < n; ++i){
			maxShift=max(maxShift,height[i]-step*i);
			minShift=min(minShift,height[i]-step*i);
		}
		int mid=(maxShift+minShift)/2;
		int cost=max(maxShift-mid, mid-minShift);
		if ( cost < minCost ){
			minCost=cost;
			start=mid;
			stepAns=step;
		}
	}
	cout<<minCost<<endl;
	cout<<start<<" "<<stepAns;
	return 0;
}
/*
6
94 65 -33 -43 60 -24
*/