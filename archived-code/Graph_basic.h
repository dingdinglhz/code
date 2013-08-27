struct Edge {
	int to,val;
	Edge(){
		to=0;val=0;
	}
};
class Graph {
public:
    vector< list<Edge> > vertex_vector;
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
        list<Edge> temp;
        for (int i = 0; i < size_i; ++i){
            vertex_vector.push_back(temp);
        }
    }
    ~Graph(){
        edge_num = 0; 
        vertex_num = 0; 
    }
    void InsertEnode(int v, Edge e){
        vertex_vector[v].push_back(e);
        ++edge_num;
    }
    void DisplayGraph() {
        for (size_t i = 0; i < vertex_vector.size(); ++i){
            cout <<"v: "<<i<<" edges: ";
            for (list<Edge>::iterator j = vertex_vector[i].begin();j != vertex_vector[i].end();++j){
                cout<<*j<<" ";
            }
            cout<<endl;
        }
    }
};
