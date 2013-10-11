import sys,os
class SCCSolver:
    '''
    Strong Connected Component (SCC) Solver.
    Given a graph to initiate and then use
    Kosaraju's Two-Pass Algorithm to solve.
    Returns a list describing SCC.
    Input Graph is diction of lists.
    '''
    def __init__(self,g,verticeSet):
        self.g=g
        self.vSet=verticeSet
        self.grev={}
        for i in verticeSet:
            self.grev.setdefault(i,list())
            self.g.setdefault(i,list())
        for v in self.g:
            for u in self.g[v]: 
                self.grev[u].append(v)
        print("reconstructing graph finished")
        #constructing the reverse Graph self.grev
        self._dfsLoopR()
        print("dfs on reversed graph finished")
        self._dfsLoop()
        print("dfs on original graph finished")
    def _dfsLoopR(self):
        #this is the reversed dfs loop, whose purpose is to calculate the topology order of the reversed graph
        #the source (the last vertice in topoOrder) is the sink of the altered original graph(with all SCC replaced by one vertice).
        #thus, if we run normal dfs on sink SCC first, dfs will not explore other SCCs
        self._explored=set()
        self._topoOrder=list()
        for v in self.grev:
            if v not in self._explored:
                self._dfsR(v)
    def _dfsR(self,v):
        self._explored.add(v)
        for u in self.grev[v]:
            if u not in self._explored:
                self._dfsR(u)
        self._topoOrder.append(v)
    def _dfsLoop(self):
        #this is the dfs loop on original graph, whose purpose is to calculate SCCs
        self._explored=set()
        self.SCCDict={}
        self._currentLead=0
        for v in reversed(self._topoOrder):
            if v not in self._explored:
                self._currentLead=v
                self.SCCDict[v]=set()
                self._dfs(v)
    def _dfs(self,v):
        self._explored.add(v)
        self.SCCDict[self._currentLead].add(v)
        for u in self.g[v]:
            if u not in self._explored:
                self._dfs(u)
    def getSCCDict(self):
        return self.SCCDict
    
if __name__ == '__main__':
    sys.stdin=open("2sat6.txt", encoding='utf-8')
    n=int(input())
    graph={}
    vSet=set()
    for i in range(n):
        tmp=input().split()
        (a,b)=(int(tmp[0]),int(tmp[1]))
        if -a not in graph:
            graph[-a]=list()
        graph[-a].append(b)
        if -b not in graph:
            graph[-b]=list()
        graph[-b].append(a)
        vSet.add(a),vSet.add(-a)
        vSet.add(b),vSet.add(-b)
    #print(graph)
    print("graph input finished")
    scc=SCCSolver(graph,vSet)
    sccDict=scc.getSCCDict()
    satisfiable=True
    for i in sccDict:
        for j in sccDict[i]:
            if -j in sccDict[i]:
                satisfiable=False
                break
        if not satisfiable:
            break
    if satisfiable:
        print("satisfiable")
    else:
        print("not satisfiable")
'''
Test Case(satisfiable)
4
1 2
3 4
-1 -3
-2 -4
Test Case(unsatisfiable)
5
-1 -2
2 -1
1 -3
3 1
4 5
'''