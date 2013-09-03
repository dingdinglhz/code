import sys, os  # @UnusedImport
from heapq import heappush,heappop
graph = {}
tree=set()
treeEdges=set()
heap=[]
firstVertice=1
treeCost=0;
class edge:
    def __init__(self,x=0,y=0,cost=0): 
        self.x=x
        self.y=y
        self.cost=cost
    def __lt__(self,y):  #operator< definition
        return self.cost<y.cost
    def __rt__(self,y):  #operator> definition
        return self.cost>y.cost
    def __str__(self):   #output definition
        return "{0} - {1} cost:{2} ".format(self.x,self.y,self.cost)
if __name__ == '__main__':
    sys.stdin=open("prim.txt", encoding='utf-8')
    (n, m) =(int(tmp) for tmp in input().split())
    for i in range(1,n+1):
        graph.setdefault(i,{})
    #print(graph)
    for i in range(0,m):
        (x,y,cost) =(int(tmp) for tmp in input().split())
        graph[x][y]=cost
        graph[y][x]=cost
    #print(graph)
    tree.add(firstVertice)
    for v in graph[firstVertice]:
        heappush(heap, edge(firstVertice,v,graph[firstVertice][v]) )
    #initializing
    '''htmp=heap.copy()
    print(len(heap))
    for edgei in range(len(htmp)):
        print(heappop(htmp))'''
    while len(tree)<n:
        tmp=heappop(heap)
        while ((tmp.x in tree)^(tmp.y in tree))==False:
            tmp=heappop(heap)
        tree.add(tmp.y)
        treeEdges.add(tmp)
        treeCost+=tmp.cost
        for v in graph[tmp.y]:
            heappush(heap, edge(tmp.y,v,graph[tmp.y][v]) )
    print(treeCost)
    
'''
small scale data
4 5
2 1 1
4 3 5
4 1 4
2 3 2
1 3 3
out:7
5 6
1 2 6
1 3 3
3 4 12
1 4 -14
2 4 8
4 5 -5
out:-10
'''