#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include "BinaryHeap.h"
using namespace std;
BinaryHeap_min<int> heap;
int main(int argc, char const *argv[])
{
	heap.output();
	int n;scanf("%d",&n);
	for (int i=0,s;i<n;++i){
		scanf("%d",&s);
		heap.ins(s);
		heap.output();
	}
	for (int i=0,a,b;i<n;++i){
		scanf("%d%d",&a,&b);
		heap.refresh(a,b);
		heap.output();
	}
	for (int i=0;i<n;++i){
		heap.pop();
		heap.output();
	}
	system("pause");
	return 0;
}
/*
9
10 4 6 1 7 5 2 8 3
insert测试通过，pop通过测试。 
1 4 5 4 8 9 5 5 10
*/
