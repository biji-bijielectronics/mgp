import zmq
import time
import redis
context = zmq.Context(1)
publisher = context.socket(zmq.PUB)
publisher.bind("tcp://*:8889")
#subscriber = context.socket(zmq.SUB)
#subscriber.bind("tcp://*:8888")

#subscriber.setsockopt(zmq.SUBSCRIBE, "web-feed")
#print "Listening on port 8888"

r = redis.Redis()

pubsub = r.pubsub()
pubsub.subscribe(['vidloopa'])

count = 0


for item in pubsub.listen():
	count += 1
	msg = "vidloopa " + str(item['data'])
	publisher.send(msg)
	print "sending: " + msg
	
