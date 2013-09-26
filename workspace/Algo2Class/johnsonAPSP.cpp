#include <iostream>
#include <cstdlib>
#include <cstdio>
#include <map>
#include <queue>
using namespace std;
#define MAX_VAL 0x0F000000
#define MAX_V 20010
struct Edge{
	int to,cost;//points to 'to' with cost 'cost'
	Edge(){to=0;cost=MAX_VAL;}
	Edge(int to_i,int cost_i){
		to=to_i;cost=cost_i;
	}
};
struct Vertice{
	int v,dist;//point v with shortest distance 'dist'
	Vertice(){v=0;dist=MAX_VAL;}
	Vertice(int v_i,int dist_i){
		v=v_i;dist=dist_i;
	}
};
bool operator<(const Vertice &va,const Vertice &vb){
	return va.dist>vb.dist;
}
bool operator>(const Vertice &va,const Vertice &vb){
	return va.dist<vb.dist;
}//the operator is reversed since default cpp priority_queue is maxHeap
std::vector<Edge> graph[MAX_V];
int n,m;
int dist[MAX_V];//distance for spfa(bellman ford)
int adis[MAX_V][MAX_V];//all pair distance
queue<int> q;//queue for SPFA
bool inQueue[MAX_V];
int entered[MAX_V];//the number of a vertice enters the SPFA queue
void initial(){
	cin>>n>>m;
	for (int i=0,u,v,cost; i<m; ++i){
		cin>>u>>v>>cost;
		graph[u].push_back(Edge(v,cost));
	}//graph input
	for (int i=1; i<=n; ++i){
		graph[0].push_back(Edge(i,0));
	}//add a dummy-source point for johnson's algorithm
	for (int i = 0; i <=n; ++i){
		for (int j = 0; j <=n; ++j){
			adis[i][j]=MAX_VAL;
		}
	}//initializing the distance array
	for (int i=0; i<=n; ++i){
		dist[i]=MAX_VAL;
		inQueue[i]=false;
		entered[i]=0;
	}//initializing for SPFA
}
bool spfa(int source){//spfa: a variation of Bellma-Ford
	dist[source]=0;
	inQueue[source]=true;
	++entered[source];
	q.push(source);//source pushed into queue
	while(!q.empty()){
		int tmp=q.front();//take first vertice in queue
		for (std::vector<Edge>::iterator i = graph[tmp].begin();
			i != graph[tmp].end(); ++i){
			// relax adjacent nodes
			int to=(*i).to,cost=(*i).cost;
			if (cost+dist[tmp]<dist[to] ){
				dist[to]=cost+dist[tmp];
				if (!inQueue[to]){
					//if 'to' is not in quene q, push it into q
					q.push(to);
					inQueue[to]=true;
					++entered[to];
					if (entered[to]>n){
						//negative cycle detected. Return with a false value
						while(!q.empty()){q.pop();}
						return false;
					}
				}
			}
		}
		q.pop();
		inQueue[tmp]=false;
		//process of the current node finished. pop this node out of q
	}
	return true;//success in computing distance (no negative cycle)
}
void reWeighting(){
	for (int i=1; i<=n; ++i){
		for (std::vector<Edge>::iterator j=graph[i].begin();
			j!= graph[i].end(); ++j){
			(*j).cost+=dist[i]-dist[(*j).to];
		}
	}//re-weight all edges according to johnson's algorithms
}
void dijkstra(int source){
	bool explored[n+1];
	for (int i = 0; i<=n; ++i){explored[i]=false;}
	std::priority_queue<Vertice> heap;
	//initializing for dijkstra
	explored[source]=true;
	int exploredNum=1;
	adis[source][source]=0;
	heap.push(Vertice(source,0));
	//push the source vertice to heap
	while(exploredNum<n && !heap.empty()){
		Vertice tmp=heap.top();
		//printf("top\n");
		while (adis[source][tmp.v]<tmp.dist){
			//explored[tmp.v] || 
			//printf("in\n");
			heap.pop();
			if (heap.empty()){return;}
			tmp=heap.top();
		}//obtain first valuable vertice in the heap. All out-dated vertices are thrown away.
		//printf("v %d dist %d\n", tmp.v,tmp.dist);
		//system("pause");
		explored[tmp.v]=true;//put the current node into explored set. The distance will stay constant
		++exploredNum;
		for (std::vector<Edge>::iterator i = graph[tmp.v].begin();
			i != graph[tmp.v].end(); ++i){//explore adjacent nodes
			if(!explored[(*i).to]){
				int disTmp=(*i).cost+tmp.dist,v=(*i).to;
				if (disTmp<adis[source][v]){
					heap.push(Vertice(v,disTmp));
					adis[source][v]=disTmp;//if the distance can be updated, push it to heap
				}
			}
		}
		heap.pop();//pop this used vertice
	}
}
void show(){
	cout<<endl;
	for (int i = 1; i <=n; ++i){
		for (int j = 1; j <=n; ++j){
			cout<<adis[i][j]-dist[i]+dist[j]<<" ";
		}
		cout<<endl;
	}//show all-pair distance
}
int main(int argc, char const *argv[]){
	//freopen("g3.txt","r",stdin);
	initial();//initializing
	printf("initial\n");
	if (spfa(0)){//step one: SPFA. If no negative cycle exists, go to next step
		printf("spfa\n");
		reWeighting();//re-weight the graph
		printf("reWeighting\n");
		for (int i =1; i <=n; ++i){
			dijkstra(i);//run dijkstra algorithm for each of the nodes in the new reweighted graph
			printf("dij %i\n",i);
			//show();
		}
		//cout<<"399 to 904 "<<adis[399][904]-dist[399]+dist[904]<<endl;
		int minDis=MAX_VAL,mini=-1,minj=-1;
		for (int i = 1; i <=n; ++i){
			for (int j = 1; j <=n; ++j){
				if( (adis[i][j]-dist[i]+dist[j]) <minDis){
					minDis=(adis[i][j]-dist[i]+dist[j]);
					mini=i,minj=j;
				}
			}
		}//find the smallest shortest distance and output the result.
		cout<<"minDis"<<minDis<<endl;
		cout<<"from "<<mini<<"to "<<minj<<endl;
	}
	else{
		cout<<"negative cycle detected";
		system("pause");
		return 0;
	}
	//if(spfa(399)){cout<<"399 to 904 "<<dist[904]<<endl;}
	//else{cout<<"negative cycle detected";}
	system("pause");
	return 0;
}
/*Test Case
6 7
1 2 -2
2 3 -1
3 1 4
3 4 2
3 5 -3
6 4 1
6 5 -4
*/
