import serial,sys,time,base64
ser=serial.Serial("COM3",125000)
timelist=list()
b=b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\x7f\xfb\x00\x38\x70\xb8\x7f\xf5\xff\xff\xff\xff\xff\x7f\xfd\xaf\xbf\xf7\x0b\x84\xed\xff\xff\xff\xff'
for i in range(1000):
	#time.sleep(0.01)
	#print(ser.read(100).decode(encoding="ascii"))
	ser.write(b)
	#print(base64.b16encode(ser.read(52)))
	#print("tick")
	if(ser.inWaiting()>0):
		timelist.append(time.time())
		while  ser.inWaiting()>0:
			sys.stdout.write(ser.read().decode(encoding="ascii"));
print(timelist)