import sys,os  # @UnusedImport
if __name__ == '__main__':
	#sys.stdin=open("knapsack_big.txt", encoding='utf-8')
	w=list()
	v=list()
	(size,n)=(int(tmp) for tmp in input().split())
	for i in range(0,n):
		(vt,wt)=(int(tmp) for tmp in input().split())
		w.append(wt)
		v.append(vt)
	#print(w)
	#print(v)
	maxV=[0 for i in range(0,size+1)]
	for i in range(0,n):
		for j in range(w[i],size+1):
			if (maxV[j-w[i]]+v[i])>maxV[j]:
				maxV[j]=maxV[j-w[i]]+v[i]
		print("item {0} finished:{1}".format(i,maxV[size]))
		print(maxV)
	print(maxV[size])
'''
Small Case
11 4
8 4
10 5
15 8
4 3
'''