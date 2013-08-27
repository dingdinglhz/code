#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <list>
#include <algorithm>
#include <iterator>

#define RETRY_TIME 200
#define MAX_NODE_SIZE 875720

using namespace std;
typedef std::map<int, list<int> >::value_type VertexN;
class Graph {
    std::map<int, list<int> > vertex_list;
    int vertex_num;
    int edge_num;
public:
    Graph(){
        vertex_num=0;
        edge_num=0;
        //srand( (unsigned)time( NULL ) );
    }
    Graph(const Graph& gs) {
        edge_num = 0;
        vertex_num = 0;
        Graph &ga=const_cast<Graph&>(gs);
        for (list<VertexN>::iterator i = ga.vertex_list.begin(); i !=ga.vertex_list.end(); ++i){
            InsertVnode( (*i).num);
            list<VertexN>::iterator iv=SearchVnode((*i).num);
            for (list<int>::iterator j = (*i).edge_list.begin();j != (*i).edge_list.end();++j){
                InsertEnode(iv,*j);
            }
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    list<int>::iterator IteratorAdd(list<int>::iterator ei_o,int add_n){
        for (int i = 0; i < add_n; ++i){++ei_o;}
        return ei_o;
    }
    list<VertexN>::iterator IteratorAdd(list<VertexN>::iterator vi_o,int add_n){
        for (int i = 0; i < add_n; ++i){++vi_o;}
        return vi_o;
    }
    void InsertEnode(int v, int e){
        list<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_list.push_back(e);
        ++edge_num;
    }
    void InsertEnode(list<VertexN>::iterator iv, int e){
        (*iv).edge_list.push_back(e);
        ++edge_num;
    }
    void DeleteEnode(int v, int ei){
        list<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_list.erase( IteratorAdd((*iv).edge_list.begin(),ei) );
        --edge_num;
    }
    void AppendEnodes(int src, int dest){
        list<VertexN>::iterator isrc =SearchVnode(src );
        list<VertexN>::iterator idest=SearchVnode(dest);
        for (list<int>::iterator j = (*isrc).edge_list.begin();j != (*isrc).edge_list.end();++j){
            (*idest).edge_list.push_back(*j);
        }
    }
    void InsertVnode(int v){
        vertex_list.push_back(VertexN(v,temp));
        ++vertex_num;
    }
    void DeleteVnode(int v){
        vertex_list.erase(SearchVnode(v));
        --vertex_num;
    }
    list<VertexN>::iterator SearchVnode(int v){
        for (list<VertexN>::iterator i = vertex_list.begin(); i != vertex_list.end(); ++i){
            if ((*i).num==v){return i;}
        }
    }
    int GetNumOfEdges(int v){
        return (*SearchVnode(v)).edge_list.size();
    }
    void SubstituteEnodes(int from, int to){
        for (list<VertexN>::iterator i = vertex_list.begin(); i != vertex_list.end(); ++i){
            for (list<int>::iterator j = (*i).edge_list.begin();j != (*i).edge_list.end();++j){
                if (*j==from) {*j=to;}
            }
        }
    }
    void DeleteSelfLoop(int v){
        list<VertexN>::iterator iv=SearchVnode(v);
        list<int>::iterator j = (*iv).edge_list.begin();
        while(j != (*iv).edge_list.end()){
            if ((*j)== v) {
                j=(*iv).edge_list.erase(j);
                --edge_num;
            }
            else{ ++j; }
        }
    }
    void ReadGraphFromText(){
        ifstream ifs;
        ifs.open("kargerAdj.txt");
        string line;
        stringstream strstream;
        int v, e;

        while (getline(ifs, line)) {
            strstream << line;
            strstream >> v;
            InsertVnode(v);
            list<VertexN>::iterator iv=SearchVnode(v);
            while (strstream >> e) {
                InsertEnode(iv, e);
            }
            strstream.clear();
        }
        ifs.close();
    }
    int GraphContraction(){
        while (vertex_num > 2) { // contract edges until vertexNum is 2
            int e1, e2;
            srand( (unsigned)time(NULL) );
            GetRandomEdge(e1, e2);
            AppendEnodes(e1, e2); // append all the nodes from v1 to v2
            DeleteVnode(e1); // delete v1
            SubstituteEnodes(e1, e2); // substitute all (x, e1) with (x, e2)
            DeleteSelfLoop(e2); // delete selp-loop in v2
        }
        return edge_num;
    }
    void DisplayGraph() {
        for (list<VertexN>::iterator i = vertex_list.begin(); i != vertex_list.end(); ++i){
            cout <<"v: "<<(*i).num<<" edges: ";
            for (list<int>::iterator j = (*i).edge_list.begin();j != (*i).edge_list.end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
    void GetRandomEdge(int &v, int &e){
        list<VertexN>::iterator iv = IteratorAdd( vertex_list.begin() , (rand() % (vertex_num)) );
        list<int>::iterator je = IteratorAdd( (*iv).edge_list.begin() , (rand() % ((*iv).edge_list.size())) );
        v = (*iv).num;
        e = *je;
    }

};
int main() {
    Graph g;
    g.ReadGraphFromText();
    g.DisplayGraph();
    system("pause");
    int mincut = 10000;
    for (int i = 0; i < RETRY_TIME; i++) {
        Graph gTemp = Graph(g);
        //Graph gTemp=g;
        int temp = gTemp.GraphContraction();
        if (mincut > temp) mincut = temp;
    }
    //mincut=g.GraphContraction();
    mincut = mincut / 2; // because the number of edges is doubled
    cout << "minimum cut is " << mincut << endl; // mincut is double times the real number of edges
    system("pause");
    return (0);
}
