package com.bijibiji.mgp;

import org.jeromq.ZMQ;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ZeroMQListener implements Runnable {
	private final Handler uiThreadHandler;
	private String BROKER_URL = "tcp://192.168.59.22:8888";
	private String TOPIC = "UPDATE";
	
	public ZeroMQListener(Handler handler){
		this.uiThreadHandler = handler;
	}

	public ZeroMQListener(Handler handler,String url, String topic){
		this.uiThreadHandler = handler;
		this.BROKER_URL = url;
		this.TOPIC = topic;
	}
	
	private void sendMessage(String message){
		 Message msg = uiThreadHandler.obtainMessage();
		 Bundle b = new Bundle();
		 b.putString("message", message);
		 msg.setData(b);
		 uiThreadHandler.sendMessage(msg);
	
	}
	
	@Override
	public void run() {
		sendMessage("Listening on " + TOPIC);
		ZMQ.Context context = ZMQ.context(); 
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		subscriber.connect(BROKER_URL);
		subscriber.subscribe(TOPIC);
 
		while(!Thread.currentThread().isInterrupted()) {
			String message = subscriber.recvStr();
			sendMessage(message);
			 //socket.send(Util.reverseInPlace(msg), 0);
		 }
		 subscriber.close();
		 context.term();
	}
	

}
