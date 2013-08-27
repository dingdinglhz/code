import random
HASH_SIZE=789671
rand_n=int(random.random()*HASH_SIZE)
h_lis=[[] for x in range(HASH_SIZE)]
ans=0

def hash_f(in_in):
	return rand_n*in_in%HASH_SIZE

def ins(in_in):
	k=hash_f(in_in)
	if in_in in h_lis[k]:
		pass
	else:
		h_lis[k].append(in_in)

def find(t):
	global ans
	flag=False
	for x in range(t):
		if x in h_lis[hash_f(x)] and (t-x) in h_lis[hash_f(t-x)]:
			if flag:
				ans+=1
				return True
			else:
				flag=True
	return False
def main():
	with open("HashInt.txt", encoding='utf-8') as HashFile:
		for num_s in HashFile:
			ins(int(num_s))
		for t in range(2500,4001):
			find(t)
		print(ans)
if __name__ == '__main__':
	main()
