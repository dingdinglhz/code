#TSP problem simple heuristic solution
import random,math,os
class TSPSolver:
	def __init__(self,g,n):
		self.graph=g
		self.n=n
	def solve(self):
		self.vSeq=list(range(1,n+1))
		random.shuffle(self.vSeq)
		self.tour=[self.vSeq[0],self.vSeq[0]]
		self.pathLength=0
		#print("vSeq {0}".format(self.vSeq))
		for i in self.vSeq[1:]:
			self._addVertice(i)
			#print("add {0} seq {1} pl {2}".format(i,self.tour,self.pathLength))
		return self.pathLength
	def _addVertice(self,i):
		minj=0
		minIncre=self.graph[self.tour[0]][i]+self.graph[i][self.tour[1]]-graph[self.tour[0]][self.tour[1]]
		for j in range(1,len(self.tour)-1):
			tmp=self.graph[self.tour[j]][i]+self.graph[i][self.tour[j+1]]-graph[self.tour[j]][self.tour[j+1]]
			if tmp<minIncre:
				minIncre=tmp
				minj=j
		self.tour.insert(minj+1,i)
		self.pathLength+=minIncre
		
if __name__ == '__main__':
	n=int(input())
	points=[None]
	graph={}
	for i in range(1,n+1):
		graph.setdefault(i,{})
	for i in range(n):
		tmp=input().split()
		points.append((float(tmp[0]),float(tmp[1])))
	#print(points)
	for i in range(1,n+1):
		for j in range(1,n+1):
			if i!=j:
				graph[i][j]=math.sqrt((points[i][0]-points[j][0])**2+(points[i][1]-points[j][1])**2)
				graph[j][i]=graph[i][j];
			else:
				graph[i][j]=0.0
	#print(graph)
	solver=TSPSolver(graph,n)
	minPathLength=solver.solve()
	print(minPathLength)
	freq={}
	for i in range(n**2):
	#while True:
		tmp=solver.solve()
		if tmp<minPathLength:
			minPathLength=tmp
		print("min {0} current {1}".format(minPathLength,tmp))
		if int(tmp) in freq:
			freq[int(tmp)]+=1
		else:
			freq[int(tmp)]=1
	print("Final Ans {0}".format(minPathLength))
	print(freq)
'''
Test Case
5
0 0
0 0.5
0 1
1 1
1 0
Test Case(result 37)
8
38.24 20.42
39.57 26.15
40.56 25.32
36.26 23.12
33.48 10.54
37.56 12.19
38.42 13.11
37.52 20.44
Test Case(result 73.9876)
16
38.24 20.42
39.57 26.15
40.56 25.32
36.26 23.12
33.48 10.54
37.56 12.19
38.42 13.11
37.52 20.44
41.23 9.10
41.17 13.05
36.08 -5.21
38.47 15.13
38.15 15.35
37.51 15.17
35.49 14.32
39.36 19.56

Wrong Implementation
	12952
	12907
	12765
wrong Implementation
	23008
	22722
	22451
	22484
	22385.6953
	22365.21956
	'''