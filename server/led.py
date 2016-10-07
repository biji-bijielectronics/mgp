import serial
import time

strPort = '/dev/ttyACM0'

ser = serial.Serial(strPort, 115200)

NUM_LED = 144


for i in range(NUM_LED):
	data = bytearray([i])
	print data

	ser.write(data);
	time.sleep(0.01)
