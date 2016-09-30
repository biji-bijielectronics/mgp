import zmq
context = zmq.Context()
subscriber = context.socket(zmq.SUB)
subscriber.connect("tcp://localhost:8889")

subscriber.setsockopt(zmq.SUBSCRIBE, "vidloopa")
print "Listening on port 8889"
count = 0
while True:
	msg = subscriber.recv()
	print msg
