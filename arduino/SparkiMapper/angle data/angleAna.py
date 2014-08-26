error=dict()
times=dict()
for i in range(-70,80,10):
	error[i]=0
	times[i]=0
f=open("0820140835.txt","r")
for line in f:
	words=line.split()
	if words[0] is "A":
		error[int(words[1])]+=float(words[3])
		times[int(words[1])]+=1
for i in range(-70,80,10):
	error[i]/=times[i]
	print("Deg:{0} Times:{1} Err:{2}".format(i,times[i],error[i]))