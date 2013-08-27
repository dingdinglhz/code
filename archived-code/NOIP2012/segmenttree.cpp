#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#define min(a,b) a<b?a:b
#define max(a,b) a>b?a:b
using namespace std;
class SegmentTree
{
public:
	long long m_const;
	long long data[2097152];

	SegmentTree(int data_l){
		m_const=1;while(m_const<data_l){m_const=m_const<<1;}
	};
	~SegmentTree(){};
	long long query(int s,int t){
		long long ans=0;
		for (s=m_const+s-1,t=m_const+t+1;s^t^1;s>>=1,t>>=1){
			if((s&1)==0){ans+=data[s^1];}
			if((t&1)==1){ans+=data[t^1];}
		}
	};
	void change(int n,long long in_data){
		n+=m_const;data[n]=in_data;
		for (n>>=1;n>1;n>>=1){
			data[n]=data[n<<1]+data[n<<1+1];
		}
	};


};
class SegmentTree_RMQ : public SegmentTree
{
public:
	SegmentTree_RMQ(int data_l) : SegmentTree(data_l){};
	~SegmentTree_RMQ(){};

	void add (int s,int t,long long x_data){
		for (s=s+m_const-1,t=t+m_const+1; s^t^1;s>>=1,t>>=1){
			if((s&1)==0){data[s^1]+=x_data;}
			if((t&1)==1){data[t^1]+=x_data;}
			long long a=min(data[s],data[s^1]);
			data[s]-=a;data[s^1]-=a;data[s>>1]+=a;
			a=min(data[t],data[t^1]);
			data[t]-=a;data[t^1]-=a;data[t>>1]+=a;
		}
	};

	long long min_data(int s,int t){
		long long l_ans=0,r_ans=0;
		for (s=s+m_const-1,t=t+m_const+1; s^t^1;s>>=1,t>>=1){
			l_ans+=data[s];r_ans+=data[t];
			if((s&1)==0){l_ans=min(data[s^1],l_ans);}
			if((t&1)==1){r_ans=min(data[t^1],r_ans);}
		}
		long long ans=min(l_ans,r_ans);
		while(s>1){ans+=data[s>>=1];}
	};
};
int n,m;
int main(int argc, char const *argv[]){
	scanf("%d%d",&n,&m);
	long long r,d,s,t;
	SegmentTree_RMQ st_rmq(n);
	for (int i = 0; i < n; ++i)	{
		scanf("%d",&r);
		st_rmq.add(i,i,r);
	}
	bool a=true;
	int b;
	for (int i = 0; i < m; ++i)	{
		scanf("%d%d%d",&d,&s,&t);
		if(st_rmq.min_data(s,t)<d){
			a=false;
			b=i+1;
			break;
		}else{st_rmq.add(s,t,-d);}
	}
	if(a){printf("0\n");}
	else{printf("-1\n%d",b);}
	system("pause");
	return 0;
}
