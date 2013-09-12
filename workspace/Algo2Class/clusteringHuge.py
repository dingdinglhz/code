'''
Created on 2013-9-10

@author: linhz
'''
import sys,os  # @UnusedImport
byteToNode={}
nodeToByte={}
clusterNum=0
spanGoal=3
class disjointSet:
    def __init__(self,size=0):
        self._father=[i for i in range(0,size+1)]
        self._rank=[0 for i in range(0,size+1)]
    def find(self,v): # find the ancestor of a element v
        if self._father[v]!=v:
            self._father[v]=self.find(self._father[v])
        return self._father[v]
    def join(self,a,b): # join 2 sets by setting the father of the ancestor of setA into the ancestor of setB
        fatherA=self.find(a)
        fatherB=self.find(b)
        #maintaining the rank(deepest node) during union operation
        if self._rank[fatherA]==self._rank[fatherB]: 
            self._father[fatherA]=fatherB
            self._rank[fatherB]+=1
        elif self._rank[fatherA]<self._rank[fatherB]:
            self._father[fatherA]=fatherB
        elif self._rank[fatherA]>self._rank[fatherB]:
            self._father[fatherB]=fatherA
'''def countBit(byteInput):
    ansOutput=0
    while byteInput>0:
        byteInput&=byteInput-1
        ansOutput+=1
        if ansOutput>2:
            return 3
    return ansOutput'''
if __name__ == '__main__':
    sys.stdin=open("clustering_big.txt", encoding='utf-8')
    (n,length)=(int(tmp) for tmp in input().split())
    vSet=disjointSet(n)
    clusterNum=n
    bitTmp=[1<<i for i in range(0,length)]
    for i in range(0,n):
        num=0
        bits=[int(tmp) for tmp in input().split()]
        for bit in bits:
            num=num*2+bit
        #print(bin(num))
        #os.system("pause")
        if num not in byteToNode:
            byteToNode[num]=i
            nodeToByte[i]=num
        else:
            clusterNum-=1
    for node in nodeToByte:
        #print("1st fin n{0}".format(node))
        for i in bitTmp:
            byteB=nodeToByte[node]^i
            if byteB in byteToNode:
                nodeB=byteToNode[byteB]
                if vSet.find(node)!=vSet.find(nodeB):
                    vSet.join(node, nodeB)
                    clusterNum-=1
        #print("1st fin n{0}".format(node))
    for node in nodeToByte:
        for i in range(0,length):
            for j in range(0,i):
                byteB=nodeToByte[node]^(bitTmp[i]+bitTmp[j])
                if byteB in byteToNode:
                    nodeB=byteToNode[byteB]
                    if vSet.find(node)!=vSet.find(nodeB):
                        vSet.join(node, nodeB)
                        clusterNum-=1
        print("2st fin n{0}".format(node))
    '''for nodeA in nodeToByte:
        for nodeB in nodeToByte:
            if countBit(nodeToByte[nodeA]^nodeToByte[nodeB])<=2:
                if vSet.find(nodeA)!=vSet.find(nodeB):
                    vSet.join(nodeA, nodeB)
                    clusterNum-=1
        print("finished node{0}".format(nodeA))'''
    print(clusterNum)
'''small test case
11 8
0 1 1 0 0 1 1 1
0 1 1 0 0 1 1 1
1 1 0 0 1 0 1 1
1 0 1 1 1 0 0 1
1 1 1 1 0 0 1 0
0 0 1 1 0 0 1 1
0 0 0 1 1 0 1 1
1 1 0 0 1 1 1 1
1 0 1 1 1 1 1 0
1 0 0 0 0 1 1 0
0 1 0 0 0 0 1 1
'''