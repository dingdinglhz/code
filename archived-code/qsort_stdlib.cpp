//cpp merge soft -- low 
#include <iostream>
#include <cstdlib>
#include <cstdio>
using namespace std;
#ifndef MERGESORT_SIZE
#define MERGESORT_SIZE 100000
#endif
int data[MERGESORT_SIZE];
int n;

void test(){
	for (int i = 0; i < n; ++i){printf("%d ",data[i]);}
	printf("\n");

}
int cmp(const void *a, const void *b)  {
	int *ai=(int*)(a);
	int *bi=(int*)(b);
	return (*ai)-(*bi);
}

int main(int argc, char const *argv[])
{
	scanf("%d",&n);
	for (int i = 0; i < n; ++i)
	{
		scanf("%d",&data[i]);
	}
	qsort(data,n,sizeof(int),cmp);
	test();
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
