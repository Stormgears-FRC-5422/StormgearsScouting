package org.stormgearsfrc.stormgearsscouting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class PitScoutingForm extends ActionBarActivity implements View.OnClickListener {

    String driveTrainType = null;

    Spinner driveTrainSelect, defense1, defense2, defense3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouting_form);

        //If there is no event code stored, ask the user to enter an event code
        if (MainMenu.eventCodeFromProperties == null || MainMenu.eventCodeFromProperties == "") {
            AlertDialog.Builder dlgEvnError = new AlertDialog.Builder(this);
            dlgEvnError.setMessage("There is no event code set. Please go to the main menu and set it.");
            dlgEvnError.setTitle("Event Code Not Set");

            dlgEvnError.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });

            dlgEvnError.setCancelable(false);
            dlgEvnError.create().show();

            return;
        }

        driveTrainSelect = (Spinner) findViewById(R.id.spnDriveTrainSelect);
        driveTrainSelect.setOnItemSelectedListener(new MyOnItemSelectedListener());

        defense1 = (Spinner) findViewById(R.id.spnPDef1);
        defense2 = (Spinner) findViewById(R.id.spnPDef2);
        defense3 = (Spinner) findViewById(R.id.spnPDef3);

        defense1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        defense2.setOnItemSelectedListener(new MyOnItemSelectedListener());
        defense3.setOnItemSelectedListener(new MyOnItemSelectedListener());

        Button btnSubmit = (Button) findViewById(R.id.btnPSubmit);
        btnSubmit.setOnClickListener(this);

        //Enable up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void submitToServer() {
        //Submit pit scouting data to server
        driveTrainType = String.valueOf(driveTrainSelect.getSelectedItem());
        String  scoutID = "0", teamNumber  = "0", teamName = "No Name", driverExp = "0", driveMotorsQty = "0", strengths = "No Strengths", weaknesses = "No Weaknesses", comments = "No Comments", eventCode = MainMenu.eventCodeFromProperties, topDefenses;

        EditText txtPTeamNum = (EditText) findViewById(R.id.txtPTeamNum);
        EditText txtPTeamName = (EditText) findViewById(R.id.txtPTeamName);
        EditText txtPDriverExp = (EditText) findViewById(R.id.txtPDriverExp);
        EditText txtPDriveMotorsQty = (EditText) findViewById(R.id.txtPDriveMotorsNum);
        EditText txtPStrengths = (EditText) findViewById(R.id.txtPStrengths);
        EditText txtPWeaknesses = (EditText) findViewById(R.id.txtPWeakness);
        EditText txtPComments = (EditText) findViewById(R.id.txtPComments);

        teamNumber = txtPTeamNum.getText().toString();

        if (teamNumber.equals("")) {
            //Show a dialog that tells the user that they need to enter a team number before continuing
            AlertDialog.Builder dlgNoTeamNum = new AlertDialog.Builder(this);
            dlgNoTeamNum.setMessage("There is no team number entered. Please scroll to the top and enter one.");
            dlgNoTeamNum.setTitle("Please Enter Team Number");

            dlgNoTeamNum.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });

            dlgNoTeamNum.setCancelable(false);
            dlgNoTeamNum.create().show();
            return;
        }

        //Get values from text boxes
        if (!txtPTeamName.getText().toString().equals("")) teamName = txtPTeamName.getText().toString();
        if (!txtPDriverExp.getText().toString().equals("")) driverExp = txtPDriverExp.getText().toString();
        if (!txtPDriveMotorsQty.getText().toString().equals("")) driveMotorsQty = txtPDriveMotorsQty.getText().toString();
        if (!txtPStrengths.getText().toString().equals("")) strengths = txtPStrengths.getText().toString();
        if (!txtPWeaknesses.getText().toString().equals("")) weaknesses = txtPWeaknesses.getText().toString();
        if (!txtPComments.getText().toString().equals("")) comments = txtPComments.getText().toString();
        topDefenses = "1. " + defense1.getSelectedItem() + " 2. " + defense2.getSelectedItem() + " 3. " + defense3.getSelectedItem();

        //Prepare message to send over bluethoth
        BluetoothSender.message = scoutID + "," + eventCode + ",!" + "," + teamNumber + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!" + ",!"
                + ",!" + ",!" + ",!" + "," + comments + "," + teamName + "," + driverExp + "," + driveTrainType + "," + driveMotorsQty + "," + strengths + "," + weaknesses + "," + topDefenses +
        ",!" + ",!" + ",!";

        System.out.println(BluetoothSender.message);

        //Send the message over bluetooth
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PitScoutingForm.this, BluetoothSender.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPSubmit:
                submitToServer();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
