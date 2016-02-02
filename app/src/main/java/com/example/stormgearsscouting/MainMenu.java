package com.example.stormgearsscouting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainMenu extends ActionBarActivity implements View.OnClickListener {

    static String eventCode = "0";
    static String eventCodeFromProperties;
    String FILENAME = "stormgears_eventcode_file";
    FileOutputStream fos = null;
    EditText eventCodeTxt;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openPitScoutForm = (Button) findViewById(R.id.btnOpenPitScoutForm);
        openPitScoutForm.setOnClickListener(this);

        Button openScoutForm = (Button) findViewById(R.id.btnOpenScoutForm);
        openScoutForm.setOnClickListener(this);

        Button updateEventCodeButton = (Button) findViewById(R.id.btnUpdateEventCode);
        updateEventCodeButton.setOnClickListener(this);

        // Check to see if stormgears_eventcode_file exists, then read it if it does
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        try {
            fis = openFileInput(FILENAME);
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;

            if ((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
                eventCodeFromProperties = strLine;
            }
        } catch (Exception e) {
        }

        System.out.println("Prop eventCode: " + eventCodeFromProperties);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openScoutFormClick() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainMenu.this, ScoutingForm.class);
                startActivity(intent);
            }
        });
    }

    private void openPitScoutFormClick() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainMenu.this, PitScoutingForm.class);
                startActivity(intent);
            }
        });
    }

    private void openSettings() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainMenu.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    private void updateEventCode() {
        // Get the event code from eventCodeTxt
        eventCodeTxt = (EditText) findViewById(R.id.txtEventCode);
        eventCode = eventCodeTxt.getText().toString();

        // Store it in a file
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(eventCode.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        final StringBuffer storedString = new StringBuffer();
        DataInputStream dataIO = null;
        try {
            fis = openFileInput(FILENAME);
            dataIO = new DataInputStream(fis);
            String strLine = null;

            if ((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
                eventCodeFromProperties = strLine;
            }
        } catch (Exception e) {
        } finally {
            // If there is something in dataIO, close it
            if (dataIO != null) {
                try {
                    dataIO.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // If there is something in fis, close it
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Prop eventCode: " + eventCodeFromProperties);
    }

    // Listen for clicks of buttons and do appropriate actions based on which button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenScoutForm:
                openScoutFormClick();
                break;
            case R.id.btnOpenPitScoutForm:
                openPitScoutFormClick();
                break;
            case R.id.btnUpdateEventCode:
                updateEventCode();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.stormgearsscouting/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.stormgearsscouting/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
