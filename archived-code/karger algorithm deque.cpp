#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <deque>
#include <algorithm>
#include <iterator>

#define RETRY_TIME 200
#define MAX_NODE_SIZE 875720

using namespace std;

struct VertexN {
    int num;
    deque<int> edge_deque;
    VertexN(int v){
        num=v;
    }
};
class Graph {
    deque<VertexN> vertex_deque;
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
        for (deque<VertexN>::iterator i = ga.vertex_deque.begin(); i !=ga.vertex_deque.end(); ++i){
            InsertVnode( (*i).num);
            deque<VertexN>::iterator iv=SearchVnode((*i).num);
            for (deque<int>::iterator j = (*i).edge_deque.begin();j != (*i).edge_deque.end();++j){
                InsertEnode(iv,*j);
            }
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    void InsertEnode(int v, int e){
        deque<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_deque.push_back(e);
        ++edge_num;
    }
    void InsertEnode(deque<VertexN>::iterator iv, int e){
        (*iv).edge_deque.push_back(e);
        ++edge_num;
    }
    void DeleteEnode(int v, int ei){
        deque<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_deque.erase( (*iv).edge_deque.begin()+ei );
        --edge_num;
    }
    void AppendEnodes(int src, int dest){
        deque<VertexN>::iterator isrc =SearchVnode(src );
        deque<VertexN>::iterator idest=SearchVnode(dest);
        for (deque<int>::iterator j = (*isrc).edge_deque.begin();j != (*isrc).edge_deque.end();++j){
            (*idest).edge_deque.push_back(*j);
        }
    }
    void InsertVnode(int v){
        VertexN vn=VertexN(v);
        vertex_deque.push_back(vn);
        ++vertex_num;
    }
    void DeleteVnode(int v){
        vertex_deque.erase(SearchVnode(v));
        --vertex_num;
    }
    deque<VertexN>::iterator SearchVnode(int v){
        for (deque<VertexN>::iterator i = vertex_deque.begin(); i != vertex_deque.end(); ++i){
            if ((*i).num==v){return i;}
        }
    }
    int GetNumOfEdges(int v){
        return (*SearchVnode(v)).edge_deque.size();
    }
    void SubstituteEnodes(int from, int to){
        for (deque<VertexN>::iterator i = vertex_deque.begin(); i != vertex_deque.end(); ++i){
            for (deque<int>::iterator j = (*i).edge_deque.begin();j != (*i).edge_deque.end();++j){
                if (*j==from) {*j=to;}
            }
        }
    }
    void DeleteSelfLoop(int v){
        deque<VertexN>::iterator iv=SearchVnode(v);
        deque<int>::iterator j = (*iv).edge_deque.begin();
        while(j != (*iv).edge_deque.end()){
            if ((*j)== v) {
                j=(*iv).edge_deque.erase(j);
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
            deque<VertexN>::iterator iv=SearchVnode(v);
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
        for (deque<VertexN>::iterator i = vertex_deque.begin(); i != vertex_deque.end(); ++i){
            cout <<"v: "<<(*i).num<<" edges: ";
            for (deque<int>::iterator j = (*i).edge_deque.begin();j != (*i).edge_deque.end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
    void GetRandomEdge(int &v, int &e){
        deque<VertexN>::iterator iv = vertex_deque.begin()+(rand() % (vertex_num));
        deque<int>::iterator je = (*iv).edge_deque.begin() +(rand() % ((*iv).edge_deque.size()) );
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
