#include <fstream>
#include <iostream>
#include <sstream>
#include <cstdlib>
#include <ctime>
#include <cstring>
#ifndef INIT_DIS
#define INIT_DIS 1000000
#endif
#ifndef MAX_N
#define MAX_N 201
#endif
using namespace std;
int map[MAX_N][MAX_N];
int dis[MAX_N];
bool used[MAX_N];
int n;
void init(){
    n=MAX_N-1;
    for (int i = 0; i <=n; ++i){
        used[i]=false;
        for (int j =0; j <=n; ++j){
            if (i!=j) {
                map[i][j]=INIT_DIS;
            }
        }
    }
}
void input(){
    ifstream ifs;
    ifs.open("dijkstraData.txt");
    string line;
    stringstream ss;
    int v, e, c;
    while (getline(ifs, line)) {
        ss << line;
        ss >> v;
        while (!ss.eof()) {
            ss>>e>>c;
            map[v][e]=c;
        }
        ss.clear();
    }
    ifs.close();
}
void Dijkstra(){
    for(int i=1;i<=n;i++){dis[i] = map[1][i];}
    int k;
    for(int i=1;i<n;i++){
        int tmin = INIT_DIS;
        for(int j=1;j<=n;j++){
            if( !used[j] && tmin > dis[j] ){
                tmin = dis[j];
                k = j;
            }
        }
        used[k] = 1;
        for(int j=1;j<=n;j++){
            if( dis[k] + map[k][j] < dis[j] ){ 
                dis[j] = dis[k] + map[k][j];
            }
        }
    }
    printf("%d\n",dis[n]);
}
int ans_need[10]={7,37,59,82,99,115,133,165,188,197};
int main(int argc, char const *argv[]) {
    init();
    input();
    Dijkstra();
    for (int i = 0; i < 10; ++i){
        cout<<dis[ans_need[i]]<<',';
    }cout<<endl;
    for (int i = 0; i <=n; ++i){
        cout<<dis[i]<<" ";
    }cout<<endl;
    system("pause");
    return 0;
}