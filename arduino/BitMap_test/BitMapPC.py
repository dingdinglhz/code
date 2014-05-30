import serial,sys,time,base64
ser=serial.Serial("COM3",125000)
timelist=list()
#b=b'\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\xff\x7f\xfb\x00\x38\x70\xb8\x7f\xf5\xff\xff\xff\xff\xff\x7f\xfd\xaf\xbf\xf7\x0b\x84\xed\xff\xff\xff\xff'
#b=b'\x00\x00\xe0\xff\xff\xff\xff\x00\x00\x00\xc0\x00\x00\xc0\xff\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\xc0\xff\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\xc0\xff\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x1f\x00\x00\x00\xc0\x00\x00\x00\xfe\xff\xff\x1f\x00\x00\x00\xc0\x00\x01\x00\xff\xff\xff\x1f\x01\x00\x01\xc0\x00\x00\x00\xfe\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x07\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf8\xff\xff\x00\x00\x00\x00\xc0\x01\x00\x01\xf8\xff\xff\x01\x00\x01\x00\xc1\x00\x00\x00\xf8\xff\xff\x00\x00\x00\x00\xc0\x00\x00\x00\xf8\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf8\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf8\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf0\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf0\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xf8\xff\xff\x01\x00\x00\x00\xc0\x00\x01\x00\xf9\xff\xff\x03\x01\x00\x01\xc0\x00\x00\x00\xf0\xff\xff\x00\x00\x00\x00\xc0\x00\x00\x00\xfc\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x00\xfe\xff\xff\x00\x00\x00\x00\xc0\x00\x00\x00\xff\xff\xff\x01\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x03\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x03\x00\x00\x00\xc0\x01\x00\x81\xff\xff\xff\x07\x00\x01\x00\xc1\x00\x00\x80\xff\xff\xff\x07\x00\x00\x00\xc0\x00\x00\xc0\xff\xff\xff\x07\x00\x00\x00\xc0\x00\x00\x80\xff\xff\xff\x1f\x00\x00\x00\xc0\x00\x00\xc0\xff\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\xf8\xff\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\xf8\xff\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\xf8\xff\xff\xff\x3f\x00\x00\x00\xc0\x00\x01\xf0\xff\xff\xff\x3f\x01\x00\x01\xc0\x00\x00\xf0\xff\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\xf0\xf7\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\xf0\xf7\xff\xff\x3f\x00\x00\x00\xc0\x00\x00\xf8\xf7\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\xf8\xe7\xff\xff\x7f\x00\x00\x00\xc0\x00\x00\xf8\xe7\xff\xff\xff\x00\x00\x00\xc0\x00\x00\xf8\xe7\xff\xff\xff\x00\x00\x00\xc0\x01\x00\xfd\xf7\xff\xff\xff\x01\x01\x00\xc1\x00\x00\xfc\xe7\xff\xff\xff\x00\x00\x00\xc0\x00\x00\xfe\xf7\xff\xff\xff\x01\x00\x00\xc0\x00\x00\xfe\xf7\xff\xff\xff\x01\x00\x00\xc0\x00\x00\xff\xf7\xff\xff\xff\x01\x00\x00\xc0\x00\x00\xff\xf7\xff\xff\xff\x03\x00\x00\xc0\x00\x80\xff\xf7\xff\xff\xff\x03\x00\x00\xc0\x00\x80\xff\xf3\xff\xff\xff\x03\x00\x00\xc0\x00\xc1\xff\xfb\xff\xff\xff\x07\x00\x01\xc0\x00\xc0\xff\xf3\xff\xff\xff\x07\x00\x00\xc0\x00\xc0\xff\xfb\xff\xff\xff\x07\x00\x00\xc0\x00\xe0\xff\xff\xff\xff\xff\x03\x00\x00\xc0\x00\xf0\xff\xff\xff\xff\xff\x03\x00\x00\xc0\x00\xf0\xff\xff\xff\xff\xff\x07\x00\x00\xc0\x00\xf0\xff\xff\xff\xff\xff\x07\x00\x00\xc0\x00\xf8\xff\xff\xff\xff\xff\x0f\x00\x00\xc0\x01\xf0\xff\xff\xff\xff\xff\x0f\x01\x00\xc1'
for i in range(100):
	#time.sleep(0.01)
	#print(ser.read(100).decode(encoding="ascii"))
	ser.write(b)
	#print(base64.b16encode(ser.read(44)))
	#print("tick")
	if(ser.inWaiting()>0):
		timelist.append(time.time())
		while  ser.inWaiting()>0:
			sys.stdout.write(ser.read().decode(encoding="ascii"));
print(timelist)