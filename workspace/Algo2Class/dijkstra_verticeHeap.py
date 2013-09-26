import sys, os  # @UnusedImport
from heapq import heappush,heappop
graph = {}
explored=set()
shortestDistance={}#key:vertice value:shortest distance
formerVertice={}#key:vertice value:former vertices
heap=[]
sourceV=1
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
    sys.stdin=open("dijkstraData.txt", encoding='utf-8')
    n =int(input())
    for i in range(1,n+1):
        inputTmp=input().split()
        graph[i]={}
        if int(inputTmp[0])!=i:
            sys.stderr("input format error!")
        for j in range(1,len(inputTmp)):
            edgeTmp=inputTmp[j].split(",")
            graph[i][int(edgeTmp[0])]=int(edgeTmp[1])
    #print(graph)
    explored.add(sourceV)
    shortestDistance[sourceV]=0
    formerVertice[sourceV]=0
    for v in graph[sourceV]:
        heappush(heap, vertice(v,graph[sourceV][v]) )
        shortestDistance[v]=graph[sourceV][v]
        formerVertice[v]=sourceV
    while len(explored)<n and len(heap)>0:
        tmp=heappop(heap)
        while (tmp.v in explored)or(shortestDistance[tmp.v]<tmp.distance):
            tmp=heappop(heap)
        explored.add(tmp.v)
        for v in graph[tmp.v]:
            if v in explored:
                continue
            newDistance=graph[tmp.v][v]+tmp.distance
            if(v not in shortestDistance or newDistance<shortestDistance[v]):
                heappush(heap, vertice(v,newDistance) )
                shortestDistance[v]=newDistance
                formerVertice[v]=tmp.v
    #print(shortestDistance)
    #print(formerVertice)
    outputV=(7,37,59,82,99,115,133,165,188,197)
    output=[shortestDistance[v] for v in outputV]
    print(output)
'''
small scale data
4
1 2,1 3,3 4,4
2 1,1 3,2 4,1
3 1,3 2,2 4,5
4 1,4 3,5

'''