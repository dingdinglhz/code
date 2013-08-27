//median.cpp
#include "AVLtree.h"
#include <fstream>
#include <cstdlib>
using namespace std;
#ifndef MAX_N
#define MAX_N 10000
#endif
#ifndef MOD_N
#define MOD_N 10000
#endif
int n,ans=0;
AVL_Tree<int> t;
int main(int argc, char const *argv[]) {
	freopen("Median.txt","r",stdin);
	freopen("Median_out.txt","w",stdout);
	int in;n=MAX_N;
	for (int i = 1; i <=n; ++i){
		cin>>in;
		t.insert(in);
		//t.print_tree();
		ans+=(*t.order((i+1)/2)).dat;
		ans%=MOD_N;
		printf("ans=%d\n", ans);;
	}
	cout<<ans<<endl;
	system("pause");
	return 0;
}
