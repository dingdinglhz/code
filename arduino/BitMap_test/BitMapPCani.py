import serial,sys,time,base64

nofFrame=3275
imgSize=704
binList=[None for i in range(nofFrame)]
timeList=list()
strroot="ba_bin/ba"
for i in range(nofFrame):
	path="ba_bin/ba{0:04}.bin".format(i)
	f=open(path,"rb")
	binList[i]=f.read(imgSize)

#print(binList[100])

#b=b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\x7f\xfb\x00\x38\x70\xb8\x7f\xf5\xff\xff\xff\xff\xff\x7f\xfd\xaf\xbf\xf7\x0b\x84\xed\xff\xff\xff\xff'
#f=open("aaa.bin","rb");
#b=f.read(704)
print("file loaded")

ser=serial.Serial("COM3",125000)
for binImg in binList:
	#time.sleep(0.01)
	#print(ser.read(100).decode(encoding="ascii"))
	ser.write(binImg)
	#print(base64.b16encode(ser.read(44)))
	#print("tick")
	if(ser.inWaiting()>0):
		timeList.append(time.time())
		while  ser.inWaiting()>0:
			sys.stdout.write(ser.read().decode(encoding="ascii"));
#print(timeList)

avgTime=(timeList[-1]-timeList[0])/len(timeList)
print(avgTime)