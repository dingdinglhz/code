import sys,os  # @UnusedImport
import threading
maxV={}
w=list()
v=list()
def calMaxV(i,j): # i is the number of items, j is the size of the bag
    #print("enter{0},{1}:{2}".format(i,j,maxV))
    if i<=0 or j<=0:
        return 0
    elif j not in maxV[i]:
        if j>=w[i]:
            maxV[i][j]=max(calMaxV(i-1,j),calMaxV(i-1,j-w[i])+v[i])
        else:
            maxV[i][j]=calMaxV(i-1,j)
    #print("exist{0},{1}:{2}".format(i,j,maxV))
    return maxV[i][j]
if __name__ == '__main__':
    threading.stack_size(67108864)
    sys.setrecursionlimit(20000)
    sys.stdin=open("knapsack_big.txt", encoding='utf-8')
    (size,n)=(int(tmp) for tmp in input().split())
    w=[0 for i in range(n+1)]
    v=[0 for i in range(n+1)]
    for i in range(1,n+1):
        (v[i],w[i])=(int(tmp) for tmp in input().split())
        maxV.setdefault(i,{})
    #print(w),print(v),print(maxV)
    print(calMaxV(n,size))
    
    '''maxV=[[0 for i in range(0,size+1)],[0 for i in range(0,size+1)]]
    for i in range(0,n):
        for j in range(w[i],size+1):
            maxV[i&1][j]=max(maxV[(i-1)&1][j-w[i]]+v[i],maxV[(i-1)&1][j])
        #print("item {0} finished:{1}".format(i,maxV[i&1][size]))
        #print(maxV)
    print(maxV[(n-1)&1][size])'''
'''
Small Case
11 4
8 4
10 5
15 8
4 3
'''