if __name__ == '__main__':
	n=int(input())
	height=[int(i) for i in input().split()]
	n=len(height)
	height.sort()
	maxStep=(height[-1]-height[0])
	minCost=999999
	stepAns=0
	start=0
	#print(height)
	#print(maxStep)
	for step in range(0,maxStep+1):
		maxDiff=-999999
		minDiff= 999999
		for i in range(0,n):
			maxDiff=max(maxDiff,height[i]-step*i)
			minDiff=min(minDiff,height[i]-step*i)
		mid=(maxDiff+minDiff)//2
		#print("max: {0} min: {1} with step:{2}".format(maxDiff,minDiff,step))
		cost=max(maxShift-mid, mid-minShift)
		if cost<minCost:
			minCost=cost
			start=mid
			stepAns=step
			#print("update ans for cost:{0} start:{1} step:{2}".format(minCost,start,stepAns))
	print(minCost)
	print("{0} {1}".format(start,stepAns))
	
'''
6
94 65 -33 -43 60 -24

'''
