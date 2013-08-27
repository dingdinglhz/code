#include <iostream>  
#include <cstdio>  
#include <cstdlib>   
#ifndef MAX_NUM
#define MAX_NUM 1000
#endif
using namespace std;
int n;
int num[MAX_NUM];
void show(){
	for (int i = 0; i < n; ++i){
		printf("Group %d: %d\n",i,num[i]);
	}
}
bool judge(){
	for (int i = 0; i < n; ++i){
		if(num[i]){return true;}
	}
	return false;
}
int main(){
	scanf("%d",&n);
	for (int i = 0; i < n; ++i)	{
		scanf("%d",&num[i]);
	}
	bool win=false;
	int in_a,in_take,target;
	while(judge()){
		target=num[0];
		for (int i = 1; i < n; ++i){
			target=target^num[i];
		}
		if(target){
		    for (int i = 0; i < n; ++i){
		    	if ((num[i]^target)<num[i]){
			    	printf("get %d out of Group %d \n",num[i]-(num[i]^target) , i);
			    	num[i]=(num[i]^target);
			    	break;
		    	}
		    }
		}else{
			for (int i = 0; i < n; ++i){
				if(num[i]){
					printf("get %d out of Group %d \n",num[i],i);
					num[i]=0;
					break;
				}
			}
		}
		show();
		////////////////
		if(judge()){
			scanf("%d%d",&in_a,&in_take);
			num[in_a]=num[in_a]-in_take;
			if(num[in_a]<0){printf("error!!!");}
			show();
        }
		else{win=true;}
	}
	printf("Game over\n");
	if (win) {printf("PC  win!!\n");}
	else     {printf("YOU win!!\n");}
	system("pause");
}
