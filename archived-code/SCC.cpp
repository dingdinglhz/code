//SSC.cpp
#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <vector>
#include <list>
#include <algorithm>
#include <iterator>

#define RETRY_TIME 200
#define MAX_NODE_SIZE 875715
//#define MAX_NODE_SIZE 9
//#pragma comment(linker,"/STACK:0x8000")
using namespace std;
class Graph {
public:
    vector< list<int> > vertex_vector;
    //list<int>  vertex_vector[MAX_NODE_SIZE];
    int vertex_num;
    int edge_num;
    Graph(){
        vertex_num=0;
        edge_num=0;
    }
    Graph(int size_i){
    	vertex_num=0;
        edge_num=0;
        vertex_vector.reserve(size_i);
        list<int> temp;
        for (int i = 0; i < size_i; ++i){
            vertex_vector.push_back(temp);
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    void InsertEnode(int v, int e){
        vertex_vector[v].push_back(e);
        ++edge_num;
    }
    void DisplayGraph() {
        for (size_t i = 0; i < vertex_vector.size(); ++i){
            cout <<"v: "<<i<<" edges: ";
            for (list<int>::iterator j = vertex_vector[i].begin();j != vertex_vector[i].end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
};
int n;
int topomap[MAX_NODE_SIZE];
int leader[MAX_NODE_SIZE];
Graph g_f,g_i;
vector<int> result;

void ReadGraphFromText(){
    ifstream ifs("SCC.txt",ifstream::in);
    int i,j;
    while(!ifs.eof()) {
        ifs>>i>>j;
        g_f.InsertEnode(i,j);
        g_i.InsertEnode(j,i);
    }
    ifs.close();
}
int t,s;
void test(){
    printf("topomap: ");
    for (int i = 0; i < n; ++i){
        printf("%d ",topomap[i]);
    }printf("\nleader: ");
    for (int i = 0; i < n; ++i){
        printf("%d ",leader[i]);
    }printf("\n");
}
void DFS_r1(int vi){
    leader[vi]=s;
    for (std::list<int>::iterator i=g_i.vertex_vector[vi].begin();
        i!=(g_i.vertex_vector[vi]).end();++i){
        if (!leader[*i]) {
            DFS_r1(*i);
        }
    }
    ++t;
    topomap[t]=vi;
}
void SCC_r1(){
    //memset(topomap,0,MAX_NODE_SIZE*sizeof(int));
    //memset(  leader ,0,MAX_NODE_SIZE*sizeof(int));
    t=0;s=0;
    for(int i=n-1;i>=1;--i){
        if(!leader[i]){
            s=i;
            DFS_r1(i);
        }
    }
}
int DFS_r2(int vi){
    leader[vi]=s;
    int rt_va=0;
    for (std::list<int>::iterator i=(g_f.vertex_vector[vi]).begin();
        i!=(g_f.vertex_vector[vi]).end();++i){
        if (!leader[*i]) {
            rt_va+=DFS_r2(*i);
        }
    }
    return (rt_va+1);
}
void SCC_r2(){
    memset(  leader ,0,MAX_NODE_SIZE*sizeof(int));
    for(int i=n-1;i>=1;--i){
        if(!leader[topomap[i]]){
            s=topomap[i];
            //cout<<"SSC of "<<s<<" :"<<DFS_r2(topomap[i])<<endl;
            //printf("%d\n",DFS_r2(topomap[i]));
            result.push_back(DFS_r2(topomap[i]));
        }
    }
}

int main() {
    n=MAX_NODE_SIZE;
    freopen("SCC out.txt","w",stdout);
    g_f=Graph(n);
    g_i=Graph(n);//graph-front and graph-inverse
    ReadGraphFromText();
    printf("input_finished\n");
    //g_f.DisplayGraph();
    //printf("------\n");
    //g_i.DisplayGraph();
    //system("pause");
    SCC_r1();
    //test();
    //system("pause");
    SCC_r2();
    //test();
    //system("pause");
    sort(result.begin(),result.end());
    while(!result.empty()){
        printf("%d\n", result.back());
        result.pop_back();
    }
    return (0);
}
