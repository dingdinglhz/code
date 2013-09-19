import sys,os  # @UnusedImport
def fun(i,j):
    if i>j:
        return 0
    else:
        return f[i][j]
if __name__ == '__main__':
    n=int(input())
    p=[0.0]+[float(tmp) for tmp in input().split()]
    f=[[0 for i in range(n+1)] for j in range(n+1)]
    prefixS=[0 for i in range(n+1)]
    for i in range(1,n+1):
        prefixS[i]=prefixS[i-1]+p[i]
    #print(p),print(f)
    print(prefixS)
    for s in range(n):
        for i in range(1,n+1-s):
            r=i
            f[i][i+s]=fun(i,r-1)+fun(r+1,i+s)
            for r in range(i+1,i+s+1):
                f[i][i+s]=min(f[i][i+s],fun(i,r-1)+fun(r+1,i+s))
            f[i][i+s]+=prefixS[i+s]-prefixS[i-1]
            #print("i{0}j{1}f:{2}".format(i,i+s,f))
    print(f[1][n])
    #print(f)
'''
DATA
7
0.05 0.4 0.08 0.04 0.1 0.1 0.23
'''