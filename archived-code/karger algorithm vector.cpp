#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <vector>
#include <algorithm>
#include <iterator>

#define RETRY_TIME 200
#define MAX_NODE_SIZE 875720

using namespace std;

struct VertexN {
    int num;
    vector<int> edge_vector;
    VertexN(int v){
        num=v;
    }
};
class Graph {
    vector<VertexN> vertex_vector;
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
        for (vector<VertexN>::iterator i = ga.vertex_vector.begin(); i !=ga.vertex_vector.end(); ++i){
            InsertVnode( (*i).num);
            vector<VertexN>::iterator iv=SearchVnode((*i).num);
            for (vector<int>::iterator j = (*i).edge_vector.begin();j != (*i).edge_vector.end();++j){
                InsertEnode(iv,*j);
            }
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    void InsertEnode(int v, int e){
        vector<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_vector.push_back(e);
        ++edge_num;
    }
    void InsertEnode(vector<VertexN>::iterator iv, int e){
        (*iv).edge_vector.push_back(e);
        ++edge_num;
    }
    void DeleteEnode(int v, int ei){
        vector<VertexN>::iterator iv=SearchVnode(v);
        (*iv).edge_vector.erase( (*iv).edge_vector.begin()+ei );
        --edge_num;
    }
    void AppendEnodes(int src, int dest){
        vector<VertexN>::iterator isrc =SearchVnode(src );
        vector<VertexN>::iterator idest=SearchVnode(dest);
        for (vector<int>::iterator j = (*isrc).edge_vector.begin();j != (*isrc).edge_vector.end();++j){
            (*idest).edge_vector.push_back(*j);
        }
    }
    void InsertVnode(int v){
        VertexN vn=VertexN(v);
        vertex_vector.push_back(vn);
        ++vertex_num;
    }
    void DeleteVnode(int v){
        vertex_vector.erase(SearchVnode(v));
        --vertex_num;
    }
    vector<VertexN>::iterator SearchVnode(int v){
        for (vector<VertexN>::iterator i = vertex_vector.begin(); i != vertex_vector.end(); ++i){
            if ((*i).num==v){return i;}
        }
    }
    int GetNumOfEdges(int v){
        return (*SearchVnode(v)).edge_vector.size();
    }
    void SubstituteEnodes(int from, int to){
        for (vector<VertexN>::iterator i = vertex_vector.begin(); i != vertex_vector.end(); ++i){
            for (vector<int>::iterator j = (*i).edge_vector.begin();j != (*i).edge_vector.end();++j){
                if (*j==from) {*j=to;}
            }
        }
    }
    void DeleteSelfLoop(int v){
        vector<VertexN>::iterator iv=SearchVnode(v);
        vector<int>::iterator j = (*iv).edge_vector.begin();
        while(j != (*iv).edge_vector.end()){
            if ((*j)== v) {
                j=(*iv).edge_vector.erase(j);
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
            vector<VertexN>::iterator iv=SearchVnode(v);
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
        for (vector<VertexN>::iterator i = vertex_vector.begin(); i != vertex_vector.end(); ++i){
            cout <<"v: "<<(*i).num<<" edges: ";
            for (vector<int>::iterator j = (*i).edge_vector.begin();j != (*i).edge_vector.end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
    void GetRandomEdge(int &v, int &e){
        vector<VertexN>::iterator iv = vertex_vector.begin()+(rand() % (vertex_num));
        vector<int>::iterator je = (*iv).edge_vector.begin() +(rand() % ((*iv).edge_vector.size()) );
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
