package com.bijibiji.mgp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.rohbot.webtest.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    static public final String fileIp = "ip";
    static public final String fileVideo1 = "video1";
    static public final String fileVideo2 = "video2";
    static public final String fileVideo3 = "video3";
    static public final String fileVideo4 = "video4";
    static String ip;

    private TextView consoleTxt;
    private Button settings, play;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vidloopa_main);

        readIpFromFile();
        readVideo1FromFile();
        readVideo2FromFile();
        readVideo3FromFile();
        readVideo4FromFile();

        consoleTxt = (TextView) findViewById(R.id.txtConsole);
        settings = (Button) findViewById(R.id.settings);
        play = (Button) findViewById(R.id.play_button);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(MainActivity.this, Settings.class);
                startActivity(settings);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoPlayer = new Intent(MainActivity.this, VideoPlayer.class);
                startActivity(videoPlayer);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void readIpFromFile() {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), fileIp);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Log.d(TAG, buffer.toString());
            ip = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readVideo1FromFile() {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), fileVideo1);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Log.d(TAG, buffer.toString());
            Settings.video1 = Uri.parse(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readVideo2FromFile() {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), fileVideo2);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Log.d(TAG, buffer.toString());
            Settings.video2 = Uri.parse(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readVideo3FromFile() {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), fileVideo3);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Log.d(TAG, buffer.toString());
            Settings.video3 = Uri.parse(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readVideo4FromFile() {
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(getFilesDir(), fileVideo4);

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            Log.d(TAG, buffer.toString());
            Settings.video4 = Uri.parse(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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


