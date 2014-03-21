//taller.cpp
#include <iostream>
#include <cstdlib>
#include <vector>
#include <set>
#include <iterator>
#include <algorithm>
using namespace std;
class Graph {
public:
    vector< set<int> > vertex_vector;
    //set<int>  vertex_vector[MAX_NODE_SIZE];
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
        set<int> temp;
        for (int i = 0; i < size_i; ++i){
            vertex_vector.push_back(temp);
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    void InsertEnode(int v, int e){
        vertex_vector[v].insert(e);
        ++edge_num;
    }
    bool Connected(int v,int e){
        return (vertex_vector[v].find(e)!=vertex_vector[v].end());
    }
    void DisplayGraph() {
        for (size_t i = 0; i < vertex_vector.size(); ++i){
            cout <<"v: "<<i<<" edges: ";
            for (set<int>::iterator j = vertex_vector[i].begin();j != vertex_vector[i].end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
};
int n,m;
Graph g_t;
bool search_t(int x,int y){
    if (g_t.Connected(x,y)){return true;}
    else{
         for (set<int>::iterator i = g_t.vertex_vector[x].begin();i != g_t.vertex_vector[x].end();++i){
            if (search_t(*i,y)) {
                return true;
            }
        }
        return false;
    }
}
int main(int argc, char const *argv[]) {
    cin>>n>>m;
    g_t=Graph(n+1);
    //g_s=Graph(n+1);
    int x,y;
    for (int i = 0; i < m; ++i){
        cin>>x>>y;
        g_t.InsertEnode(x,y);
        //g_s.InsertEnode(y,x);
    }
    cin>>x>>y;
    if(search_t(x,y)){cout<<"yes";return 0;}
    if(search_t(y,x)){cout<<"no";return 0;}
    cout<<"unknown";
    return 0;
}
/*
10 9
1 2
1 3
2 4
4 8
5 4
7 10
7 9
5 7
3 5

*/