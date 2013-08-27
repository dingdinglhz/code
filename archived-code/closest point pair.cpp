#include <iostream>
#include <cstdlib>
#include <cstdio>

#ifndef POI_ARR_LEN
#define POI_ARR_LEN 10000 //point array length
#endif
using namespace std;
struct Point{
	int x,y;
	friend istream& operator>>(istream& in,Point& in_Point){
		in>>in_Point.x>>in_Point.y;
		return in;
	}
	friend ostream& operator<<(ostream& out,Point& out_Point){
		out<<'('<<out_Point.x<<','<<out_Point.y<<')';
		return out;
	}
};
Point p_array[POI_ARR_LEN];//point array
int p_xs[POI_ARR_LEN];
int p_ys[POI_ARR_LEN];
int n;
void test(){
	for (int i = 0; i < n; ++i){
		cout<<p_array[i]<<" ";
	}
	for (int i = 0; i < n; ++i){
		cout<<p_xs[i]<<" ";
	}
	for (int i = 0; i < n; ++i){
		cout<<p_ys[i]<<" ";
	}
}
int cmpx(const void *a, const void *b)  {
	int *ai=(int*)(a);
	int *bi=(int*)(b);
	return p_array[*ai].x-p_array[*bi].x;
}
int cmpy(const void *a, const void *b)  {
	int *ai=(int*)(a);
	int *bi=(int*)(b);
	return p_array[*ai].y-p_array[*bi].y;
}
int main(int argc, char const *argv[])
{
	cin>>n;
	for (int i = 0; i < n; ++i){
		cin>>p_array[i];
		p_xs[i]=i;p_ys[i]=i;
	}
	qsort(p_xs,n,sizeof(int),cmpx);
	qsort(p_ys,n,sizeof(int),cmpy);
	test();
	system("pause");
	return 0;
}
/*9
0 1
-4 -3
4 4
5 0
1 -2
-3 -1
-2 3
-1 -4
3 2

*/
