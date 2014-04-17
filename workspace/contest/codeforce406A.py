import os
def flip(i):
	if i==1:
		return 0
	else:
		return 1
if __name__ == '__main__':
	n=int(input())
	matrix=[list() for i in range(n)]
	for i in range(n):
		tmp=input().split()
		matrix[i]=[int(j) for j in tmp]
	#print(matrix)
	ans=0
	for i in range(n):
		for k in range (n):
			ans+=matrix[i][k]*matrix[k][i]
			ans%=2
	#print(ans)
	q=int(input())
	for i in range(q):
		tmp=int(input().split()[0])
		if tmp == 3:
			print(ans,end='')
		else:
			ans=flip(ans)
		
	#os.system("pause")