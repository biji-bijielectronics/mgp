package com.bijibiji.mgp;



import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import net.rohbot.webtest.R;

public class MainActivity extends Activity  {
	
	private static final String TOPIC = "vidloopa";
	
	private EditText nameTxt;
	private CheckBox checkBox;
	private TextView consoleTxt;
    private Uri video1;
    private Uri video2;
    private Uri currentVideo;
    private VideoView myVideoView;
    private int position = 0;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//nameTxt = (EditText) findViewById(R.id.nameTxt);

		//checkBox = (CheckBox) findViewById(R.id.checkBox);
		
		consoleTxt = (TextView) findViewById(R.id.txtConsole);
		connect();

        video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.eye);
        video2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat);


        currentVideo = video1;

        // Find your VideoView in your video_main.xml layout
        myVideoView = (VideoView) findViewById(R.id.videoView);

        try {
            new Thread(new ZeroMQListener(handler,"tcp://192.168.0.38:8889",TOPIC)).start();

            //myVideoView.setMediaController(mediaControls);
            myVideoView.setVideoURI(currentVideo);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);

                myVideoView.seekTo(position);

            }
        });

        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp) {
                if(currentVideo == video1){
                    currentVideo = video2;
                }else{
                    currentVideo = video1;
                }

                myVideoView.setVideoURI(currentVideo);
                myVideoView.seekTo(0);
                myVideoView.start();
            }
        });



    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items"localhost" to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void serverMessageReceived(String messageBody) {
		//Fetch topic from message body
		String topic = messageBody.substring(0, TOPIC.length());
		String msg;
		if(topic.equals(TOPIC))
			//Remove TOPIC from messageBody if same as TOPIC
			msg = messageBody.substring(TOPIC.length() + 1, messageBody.length());
		else
			msg = messageBody;
        //Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
		consoleTxt.setText(msg);
        if(msg.equals("play1")){
            currentVideo = video1;
            myVideoView.setVideoURI(currentVideo);
            myVideoView.seekTo(0);
            myVideoView.start();
        }
        if(msg.equals("play2")){
            currentVideo = video2;
            myVideoView.setVideoURI(currentVideo);
            myVideoView.seekTo(0);
            myVideoView.start();
        }
        if(msg.equals("stop")){
            myVideoView.seekTo(0);
            myVideoView.stopPlayback();
        }
	}

	@SuppressLint("HandlerLeak")
	private  final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message message){
			String msg = message.getData().getString("message");
			serverMessageReceived(msg);
		}
	};


	public void connect(){
		//consoleTxt.setText("Connecting to MQ");
		new Thread(new ZeroMQListener(handler,"tcp://192.168.0.38:8889",TOPIC)).start();
		//new Thread(new ZeroMQListener(handler,"tcp://rohbot.net:8889",TOPIC)).start();

	}
	


}
