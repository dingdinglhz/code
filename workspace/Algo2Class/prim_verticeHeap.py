import sys, os  # @UnusedImport
from heapq import heappush,heappop
graph = {}
tree=set()
shortestDistance={}#key:vertice value:shortest distance
heap=[]
firstVertice=1
treeCost=0;
class vertice:
    v=0
    distance=0
    def __init__(self,v=0,distance=0):
        self.v=v
        self.distance=distance
    def __lt__(self,y):
        return self.distance<y.distance
    def __rt__(self,y):
        return self.distance>y.distance
    def __str__(self):
        return "v: %d dis: %d "%(self.v,self.distance)
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
        heappush(heap, vertice(v,graph[firstVertice][v]) )
        shortestDistance[v]=graph[firstVertice][v]
    '''htmp=heap.copy()
    print(len(heap))
    for edgei in range(len(htmp)):
        print(heappop(htmp))'''
    while len(tree)<n:
        tmp=heappop(heap)
        while (tmp.v in tree)or(shortestDistance[tmp.v]<tmp.distance):
            tmp=heappop(heap)
        tree.add(tmp.v)
        treeCost+=tmp.distance
        for v in graph[tmp.v]:
            if(v not in tree)and((v not in shortestDistance) or (graph[tmp.v][v]<shortestDistance[v])):
                heappush(heap, vertice(v,graph[tmp.v][v]) )
                shortestDistance[v]=graph[tmp.v][v]
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