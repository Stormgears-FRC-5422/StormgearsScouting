package org.stormgearsfrc.stormgearsscouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Settings extends ActionBarActivity implements View.OnClickListener {

    static String serverMacAddress = "0";
    static String serverMacAddressFromProperties;
    String FILENAME = "stormgears_servaddress_file";
    FileOutputStream fos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button apply = (Button) findViewById(R.id.btnApply);
        apply.setOnClickListener(this);

        // Check to see if stormgears_eventcode_file exists, then read it if it does
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        try {
            fis = openFileInput(FILENAME);
            DataInputStream dataIO = new DataInputStream(fis);
            String strLine = null;

            if ((strLine = dataIO.readLine()) != null) {
                storedString.append(strLine);
                serverMacAddressFromProperties = strLine;
            }
        } catch (Exception e) {
        }

        System.out.println("Prop serverMacAddress: " + serverMacAddressFromProperties);
    }

    private void updateServAddress() {
        // Get the server address from serverMacAddressTxt
        EditText serverMacAddressTxt = (EditText) findViewById(R.id.txtServerMacAddress);
        serverMacAddress = serverMacAddressTxt.getText().toString();

        // Store it in a file
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(serverMacAddress.getBytes());
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
                serverMacAddressFromProperties = strLine;
                if (strLine == null) {
                    strLine = "00:00:00:00:00:00";
                }
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

        System.out.println("Prop serverMacAddress: " + serverMacAddressFromProperties);
    }

    // Listen for clicks of buttons and do appropriate actions based on which button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnApply:
                updateServAddress();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
