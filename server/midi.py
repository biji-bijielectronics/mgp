import mido
import serial
ser = serial.Serial('/dev/ttyACM0', 115200)

def map_pitch(pitch):
	val = (float(pitch) + 8192) / 16383 * 144 
	return int(val)

def sendSerial(channel, byte):
	data = bytearray([channel, byte])
	print 'sending:' , data
	ser.write(data)

sendSerial(0,0)

with mido.open_input('nanoKONTROL2 MIDI 1') as inport:
	for msg in inport:
		if msg.type == 'pitchwheel':
			channel = msg.channel
			pitch = msg.pitch
			print channel, map_pitch(pitch)
			if channel < 7:
				sendSerial(channel, map_pitch(pitch)) 
		else:
			pass
			#print msg

		