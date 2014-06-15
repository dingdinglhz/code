import serial,sys,time,base64,zipfile
import winsound
wavFile= 'BadApple.wav'

widthByte=11
segmentHeight=16
segPerFrame=4
nofFrame=6566 #number of total frames
imgSize=704
zipPath="ba_bin.zip"
duration=219.15 #length of video in seconds

binList=[None for i in range(nofFrame)]
timeList=list()


zipF=zipfile.ZipFile(zipPath)
for i in range(nofFrame):
	fileName="ba_bin/ba{0:04}.bin".format(i)
	binTmp=zipF.read(fileName)
	binList[i]=[None for j in range(segPerFrame)]
	for j in range(segPerFrame):
		binList[i][j]=binTmp[j*widthByte*segmentHeight : (j+1)*widthByte*segmentHeight]
zipF.close()
#print(binList[100])

#b=b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\x7f\xfb\x00\x38\x70\xb8\x7f\xf5\xff\xff\xff\xff\xff\x7f\xfd\xaf\xbf\xf7\x0b\x84\xed\xff\xff\xff\xff'
#f=open("aaa.bin","rb");
#b=f.read(704)
print("file loaded")
log=open("log.txt",mode="a",encoding="utf-8")

ser=serial.Serial("COM3",125000)
iniTime=None #declaring variable but real initial time depends on the first '-' signal
iFrame=0
iSeg=0
started=False
while True:
	ready=False
	while not ready:
		while ser.inWaiting()==0:
			pass
		tmp=ser.read()
		if tmp==b'N':
			ready=True
		elif tmp==b'-':
			tmpTime=time.time()
			iSeg=0
			if started:
				iFrame=round( (tmpTime-iniTime)*nofFrame/duration )
			else:
				winsound.PlaySound(wavFile, winsound.SND_FILENAME|winsound.SND_ASYNC)
				iniTime=time.time()
				started=True
				# if it is the first time that '-' is detected, start the play
			timeList.append(tmpTime)
		log.write(tmp.decode(encoding="ascii"))
	if iFrame>=nofFrame:
		break
	ser.write(binList[iFrame][iSeg])
	#print("iF:{0}".format(iFrame))
	iSeg=(iSeg+1)%segPerFrame


print(timeList,file=log)
print(len(timeList),file=log)

avgTime=(timeList[len(timeList)-1]-timeList[0])/(len(timeList)-1)
print()
print(avgTime,file=log)
print(avgTime)

log.close()