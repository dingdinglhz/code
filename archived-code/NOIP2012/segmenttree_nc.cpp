#include <iostream>
#include <cstdio>
#include <cstdlib>
//#include <cstring>
#define min(a,b) a<b?a:b
#define max(a,b) a>b?a:b
using namespace std;

long long m_const;
long long data[2097152];
void add (int s,int t,long long x_data){
		for (s=s+m_const-1,t=t+m_const+1; s^t^1;s>>=1,t>>=1){
			if((s&1)==0){data[s^1]+=x_data;}
			if((t&1)==1){data[t^1]+=x_data;}
			long long a=min(data[s],data[s^1]);
			data[s]-=a;data[s^1]-=a;data[s>>1]+=a;
			a=min(data[t],data[t^1]);
			data[t]-=a;data[t^1]-=a;data[t>>1]+=a;
		}
}


long long min_data(int s,int t){
		long long l_ans=0,r_ans=0;
		for (s=s+m_const,t=t+m_const; s^t^1;s>>=1,t>>=1){
			l_ans+=data[s];r_ans+=data[t];
			if((s&1)==0){l_ans=min(data[s^1],l_ans);}
			if((t&1)==1){r_ans=min(data[t^1],r_ans);}
		}
		l_ans+=data[s];r_ans+=data[t];
		long long ans=min(l_ans,r_ans);
		while(s>1){s>>=1;ans+=data[s];}
		return ans;
}
void test(){
	for (int i = 0; i <(m_const<<1); ++i){cout<<data[i];}
	cout<<endl;
}
int n,m;
int main(){
	scanf("%d%d",&n,&m);
	long long r,d,s,t;

	m_const=1;while(m_const<(n+2)){m_const<<=1;}
	for (int i = 0; i < m_const<<1; ++i){data[i]=0;}
	//memset(data,0,2097152*sizeof(long long));
	//test();
	for (int i =1; i <=n; ++i)	{
		cin>>r;
		add(i,i,r);
		//test();
	}
	bool a=true;
	int b;
	for (int i = 0; i < m; ++i)	{
		//scanf("%d%d%d",&d,&s,&t);
		cin>>d>>s>>t;
		//test();
		//cout<<min_data(s,t)<<"--"<<max_data(s,t)<<endl;
		if(min_data(s,t)<d){
			
			a=false;
			b=i+1;
			break;
		}else{add(s,t,-d);}
	}
	//test();
	if(a){printf("0\n");}
	else{printf("-1\n%d",b);}
	//system("pause");
	return 0;
}
/*
4 3
2 5 4 3
2 1 3
3 2 4
4 2 4
*/
