//AVLtree.cpp
#include <iostream>
#include <cstdlib>
#include "AVLtree.h"
using namespace std;
AVL_Tree<int> t;
void heap_test() {
    long tmpa,tmpb;
    char act;
    while(cin>>act){
        switch(act){
            case 'i': {
                cin>>tmpa;
                t.insert(tmpa);
                t.pre_order();
                t.in_order();
                t.print_tree();
                break;
                }
            case 'f': {
                cin>>tmpa;
                cout<<(*t.find(tmpa)).dat;
                break;
                }
            case 'd':{
            	cin>>tmpa;
            	t.del(tmpa);
                t.pre_order();
                t.in_order();
                t.print_tree();
                break;
            }
            case 'o': return;
            default: cerr<<"did not recognize";break; 
        }
    }
}
int main(int argc, char const *argv[]) {
	heap_test();
	return 0;
}