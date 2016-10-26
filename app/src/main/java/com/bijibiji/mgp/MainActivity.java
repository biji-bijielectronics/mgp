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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.rohbot.webtest.R;

import java.io.Serializable;

public class MainActivity extends Activity implements Serializable {

    private static final String TOPIC = "vidloopa";

    private TextView consoleTxt;
    private Uri video1;
    private Uri video2;
    private Uri currentVideo;
    private VideoView myVideoView;
    private int position = 0;

    private String ip = "tcp://192.168.100.109:8889";
    private EditText ip_address;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consoleTxt = (TextView) findViewById(R.id.txtConsole);
        connect();

        video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.eye);
        video2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.kitkat);

        currentVideo = video1;

        // Find your VideoView in your video_main.xml layout
        myVideoView = (VideoView) findViewById(R.id.videoView);

        try {
            new Thread(new ZeroMQListener(handler, ip, TOPIC)).start();

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

        myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (currentVideo == video1) {
                    currentVideo = video2;
                } else {
                    currentVideo = video1;
                }

                myVideoView.setVideoURI(currentVideo);
                myVideoView.seekTo(0);
                myVideoView.start();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items"localhost" to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            setContentView(R.layout.ip_setting_main2);

            Button connectButton = (Button) findViewById(R.id.connect_button);
            ip_address = (EditText) findViewById(R.id.ip_address);

            connectButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // System.out.println(ip_address.getText().toString());
                    ip = ip_address.getText().toString();
                    onCreate(new Bundle());
                    System.out.println(ip);
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    private void serverMessageReceived(String messageBody) {
        //Fetch topic from message body
        String topic = messageBody.substring(0, TOPIC.length());
        String msg;
        if (topic.equals(TOPIC))
            //Remove TOPIC from messageBody if same as TOPIC
            msg = messageBody.substring(TOPIC.length() + 1, messageBody.length());
        else
            msg = messageBody;
        //Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        consoleTxt.setText(msg);
        if (msg.equals("play1")) {
            currentVideo = video1;
            myVideoView.setVideoURI(currentVideo);
            myVideoView.seekTo(0);
            myVideoView.start();
        }
        if (msg.equals("play2")) {
            currentVideo = video2;
            myVideoView.setVideoURI(currentVideo);
            myVideoView.seekTo(0);
            myVideoView.start();
        }
        if (msg.equals("stop")) {
            myVideoView.seekTo(0);
            myVideoView.stopPlayback();
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            String msg = message.getData().getString("message");
            serverMessageReceived(msg);
        }
    };


    public void connect() {
        //consoleTxt.setText("Connecting to MQ");
        new Thread(new ZeroMQListener(handler, "tcp://192.168.0.38:8889", TOPIC)).start();
        //new Thread(new ZeroMQListener(handler,"tcp://rohbot.net:8889",TOPIC)).start();

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
