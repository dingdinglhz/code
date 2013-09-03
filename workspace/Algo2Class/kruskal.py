import sys, os  # @UnusedImport
edges=[]
treeSize=1
treeCost=0
#treeEdges=set()
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
class disjointSet:
    father=[]
    def __init__(self,size=0):
        self.father=[i for i in range(0,size+1)]
    def find(self,v): # find the ancestor of a element v
        if self.father[v]!=v:
            self.father[v]=self.find(self.father[v])
        return self.father[v]
    def join(self,a,b): # join 2 sets by setting the father of the ancestor of setA into the ancestor of setB
        fatherA=self.find(a)
        fatherB=self.find(b)
        self.father[fatherA]=fatherB
if __name__ == '__main__':
    sys.stdin=open("prim.txt", encoding='utf-8')
    (n, m) =(int(tmp) for tmp in input().split())
    vSet=disjointSet(n)
    for i in range(0,m):
        (x,y,cost) =(int(tmp) for tmp in input().split())
        edges.append(edge(x,y,cost))
    edges.sort()
    for e in edges:
        if treeSize>=n:
            break
        #print(e)
        if vSet.find(e.x)!=vSet.find(e.y):
            vSet.join(e.x,e.y)
            #treeEdges.add(e)
            ++treeSize
            treeCost+=e.cost
    #print( treeEdges)
    print(treeCost)
            