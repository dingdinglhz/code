import serial,sys,time,base64
import winsound
wavFile= 'BadApple.wav'

widthByte=11
segmentHeight=16
segPerFrame=4
nofFrame=2189
imgSize=704
binList=[None for i in range(nofFrame)]
timeList=list()
strroot="ba_bin/ba"


for i in range(nofFrame):
	path="ba_bin/ba{0:04}.bin".format(i)
	f=open(path,"rb")
	binTmp=f.read(imgSize)
	binList[i]=[None for j in range(segPerFrame)]
	for j in range(segPerFrame):
		binList[i][j]=binTmp[j*widthByte*segmentHeight : (j+1)*widthByte*segmentHeight]

#print(binList[100])

#b=b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\x7f\xfb\x00\x38\x70\xb8\x7f\xf5\xff\xff\xff\xff\xff\x7f\xfd\xaf\xbf\xf7\x0b\x84\xed\xff\xff\xff\xff'
#f=open("aaa.bin","rb");
#b=f.read(704)
print("file loaded")

ser=serial.Serial("COM3",125000)
iSeg=0
while iSeg<(nofFrame*segPerFrame):
	ready=False
	while not ready:
		while ser.inWaiting()==0:
			pass
		tmp=ser.read()
		if tmp==b'N':
			ready=True
		elif tmp==b'-':
			timeList.append(time.time())
		sys.stdout.write(tmp.decode(encoding="ascii"))
	if iSeg==0:
		winsound.PlaySound(wavFile, winsound.SND_FILENAME|winsound.SND_ASYNC)
	#print(ser.read(100).decode(encoding="ascii"))
	ser.write(binList[iSeg//segPerFrame][iSeg%segPerFrame])
	iSeg+=1
	#print(base64.b16encode(ser.read(44)))
	#print("tick")
	#if(ser.inWaiting()>0):
	#	while  ser.inWaiting()>0:
	#		sys.stdout.write(ser.read().decode(encoding="ascii"))

log=open("log.txt",mode="w",encoding="utf-8")
print(timeList,file=log)
print(len(timeList),file=log)

avgTime=(timeList[len(timeList)-1]-timeList[0])/(len(timeList)-1)
print()
print(avgTime,file=log)
print(avgTime)