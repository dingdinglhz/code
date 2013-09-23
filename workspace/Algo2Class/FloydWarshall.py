import sys,os
#graph={}
maxVal=1<<24
if __name__ == '__main__':
    sys.stdin=open("g1.txt", encoding='utf-8')
    (n, m) =(int(tmp) for tmp in input().split())
    #for i in range(1,n+1):
    #    graph.setdefault(i,{})
    dis=[[maxVal for i in range(n+1)] for i in range (n+1)]
    for i in range(1,n+1):
        dis[i][i]=0
    for i in range(m):
        (x,y,cost) =(int(tmp) for tmp in input().split())
        dis[x][y]=cost
        #graph[x][y]=cost
    #print(graph)
    #print(dis)
    for k in range(1,n+1):
        for i in range(1,n+1):
            for j in range(1,n+1):
                if(dis[i][k]+dis[k][j]<dis[i][j]):
                    dis[i][j]=dis[i][k]+dis[k][j]
        print("k={0} finished".format(k))
    for i in range(1,n+1):
        if dis[i][i]<0:
            print("negative edge cycle exists!")