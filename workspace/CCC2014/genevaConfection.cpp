#include <iostream>
#include <cstdlib>
#include <cmath>
using namespace std;
#define MAX_N 100010
int n,t;
int sequence[MAX_N];
int branch[MAX_N];
bool check(){
	int i=1,seqH=1,branchT=0;
	while(seqH<=n){
		if(seqH<=n && sequence[seqH]==i){
			seqH++; i++;
		}else if(branchT>0 && branch[branchT]==i){
			branch[branchT]=0;
			branchT--; i++;
		}else if(branchT==0 || sequence[seqH]<branch[branchT]){
			branchT++;
			branch[branchT]=sequence[seqH];
			seqH++;
		}else{
			return false;
		}
	}
	return true;
}
int main(int argc, char const *argv[]){
	cin>>t;
	for (int i = 0; i < t; ++i){
		cin>>n;
		for (int i =n; i >=1; --i){
			cin>>sequence[i];
		}
		if (check()){
			cout<<"Y"<<endl;
		}
		else{
			cout<<"N"<<endl;
		}
		for (int i = 0; i <=n; ++i){
			sequence[i]=0;
			branch[i]=0;
		}
	}
	//system("pause");
	return 0;
}