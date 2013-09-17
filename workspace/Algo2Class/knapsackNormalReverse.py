import sys,os  # @UnusedImport
if __name__ == '__main__':
    sys.stdin=open("knapsack_big.txt", encoding='utf-8')
    (size,n)=(int(tmp) for tmp in input().split())
    w=[0 for i in range(n)]
    v=[0 for i in range(n)]
    for i in range(n):
        (v[i],w[i])=(int(tmp) for tmp in input().split())
    #print(w)
    #print(v)
    maxV=[0 for i in range(0,size+1)]
    for i in range(0,n):
        for j in range(size,w[i]-1,-1):
            maxV[j]=max(maxV[j-w[i]]+v[i],maxV[j])
        print("item {0} finished:{1}".format(i,maxV[size]))
        #print(maxV)
    print(maxV[size])
'''
Small Case
11 4
8 4
10 5
15 8
4 3
'''