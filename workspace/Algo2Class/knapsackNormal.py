import sys,os  # @UnusedImport
if __name__ == '__main__':
	sys.stdin=open("knapsack1.txt", encoding='utf-8')
	w=list()
	v=list()
	(size,n)=(int(tmp) for tmp in input().split())
	for i in range(0,n):
		(vt,wt)=(int(tmp) for tmp in input().split())
		w.append(wt)
		v.append(vt)
	#print(w)
	#print(v)
	maxV=[[0 for i in range(0,size+1)],[0 for i in range(0,size+1)]]
	for i in range(0,n):
		for j in range(w[i],size+1):
			maxV[i&1][j]=max(maxV[(i-1)&1][j-w[i]]+v[i],maxV[(i-1)&1][j])
		print("item {0} finished:{1}".format(i,maxV[i&1][size]))
		#print(maxV)
	print(maxV[(n-1)&1][size])
'''
Small Case
11 4
8 4
10 5
15 8
4 3
'''