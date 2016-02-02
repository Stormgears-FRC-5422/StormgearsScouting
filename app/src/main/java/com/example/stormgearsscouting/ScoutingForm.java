package com.example.stormgearsscouting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ScoutingForm extends ActionBarActivity implements View.OnClickListener {

    static String alliance = "Red";
    static boolean isQualifier;
    static int autoHighGoals = 0, autoLowGoals = 0, teleopHighGoals = 0, teleopLowGoals = 0;

    Spinner[] autoStatusSelect = new Spinner[9];
    Spinner[] teleopStatusSelect = new Spinner[9];

    CheckBox isQualifierCheckBox = null;

    protected void onCreate(Bundle savedInstanceState) {
        //Show a dialog that asks the user if the match is a qualifier or playoff round
        AlertDialog.Builder dlgMatchType = new AlertDialog.Builder(this);
        dlgMatchType.setMessage("Is this match a qualifer or playoff match?");
        dlgMatchType.setTitle("Select Match Type");

        dlgMatchType.setNegativeButton("Playoff", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
                isQualifier = false;
                System.out.println(isQualifier);
            }
        });
        dlgMatchType.setNeutralButton("Qualifier", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //dismiss the dialog
                isQualifier = true;
                isQualifierCheckBox.setChecked(true);
                System.out.println(isQualifier);
            }
        });

        dlgMatchType.setCancelable(false);
        dlgMatchType.create().show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        //Create GUI elements after the dialog box

        isQualifierCheckBox = (CheckBox)findViewById(R.id.chkIsQualifier);

        Button submitScoutForm = (Button)findViewById(R.id.btnSubmitScoutForm);
        submitScoutForm.setOnClickListener(this);

        //Declare number picker buttons for high and low goals
        Button addLowAutoGoal = (Button)findViewById(R.id.btnALPlus);
        addLowAutoGoal.setOnClickListener(this);
        Button subtractLowAutoGoal = (Button)findViewById(R.id.btnALMinus);
        subtractLowAutoGoal.setOnClickListener(this);

        final RadioButton redOpt = (RadioButton)findViewById(R.id.optRed);
        redOpt.setOnClickListener(this);

        final RadioButton blueOpt = (RadioButton)findViewById(R.id.optBlue);
        blueOpt.setOnClickListener(this);

        //Set up autonomous defense status spinners
        autoStatusSelect[0] = (Spinner)findViewById(R.id.spnID0DefenseAutoStatus);
        autoStatusSelect[0].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[1] = (Spinner)findViewById(R.id.spnID1DefenseAutoStatus);
        autoStatusSelect[1].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[2] = (Spinner)findViewById(R.id.spnID2DefenseAutoStatus);
        autoStatusSelect[2].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[3] = (Spinner)findViewById(R.id.spnID3DefenseAutoStatus);
        autoStatusSelect[3].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[4] = (Spinner)findViewById(R.id.spnID4DefenseAutoStatus);
        autoStatusSelect[4].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[5] = (Spinner)findViewById(R.id.spnID5DefenseAutoStatus);
        autoStatusSelect[5].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[6] = (Spinner)findViewById(R.id.spnID6DefenseAutoStatus);
        autoStatusSelect[6].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[7] = (Spinner)findViewById(R.id.spnID7DefenseAutoStatus);
        autoStatusSelect[7].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[8] = (Spinner)findViewById(R.id.spnID8DefenseAutoStatus);
        autoStatusSelect[8].setOnItemSelectedListener(new MyOnItemSelectedListener());


        //Set up teleop defense status spinners
        teleopStatusSelect[0] = (Spinner)findViewById(R.id.spnID0DefenseTeleopStatus);
        teleopStatusSelect[0].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[1] = (Spinner)findViewById(R.id.spnID1DefenseTeleopStatus);
        teleopStatusSelect[1].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[2] = (Spinner)findViewById(R.id.spnID2DefenseTeleopStatus);
        teleopStatusSelect[2].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[3] = (Spinner)findViewById(R.id.spnID3DefenseTeleopStatus);
        teleopStatusSelect[3].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[4] = (Spinner)findViewById(R.id.spnID4DefenseTeleopStatus);
        teleopStatusSelect[4].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[5] = (Spinner)findViewById(R.id.spnID5DefenseTeleopStatus);
        teleopStatusSelect[5].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[6] = (Spinner)findViewById(R.id.spnID6DefenseTeleopStatus);
        teleopStatusSelect[6].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[7] = (Spinner)findViewById(R.id.spnID7DefenseTeleopStatus);
        teleopStatusSelect[7].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[8] = (Spinner)findViewById(R.id.spnID8DefenseTeleopStatus);
        teleopStatusSelect[8].setOnItemSelectedListener(new MyOnItemSelectedListener());

        //Set up alliance selector (radio group) and listen for button presses
        RadioGroup allianceSelect = (RadioGroup) findViewById(R.id.allianceRadioGroup);
        allianceSelect.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Check which radio button was clicked
                if (checkedId == R.id.optRed) {
                    alliance = "Red";
                    blueOpt.setChecked(false);

                } else if (checkedId == R.id.optBlue) {
                    alliance = "Blue";
                    redOpt.setChecked(false);
                }
            }
        });

        //Enable up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scouting_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_match_help) {
            openMatchHelp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitMatchScoutFormClick() {
        String[] defenseAutoStatus = new String[9];
        String[] defenseTeleopStatus = new String[9];

        String scoutID = "0", eventCode = "0", matchNumber = "0", teamNumber = "0", totalAlliancePoints = "0";
        //0 is match scout; 1 is pit scout

        int[] defenseAutoCounter = new int[9];
        int[] defenseTeleopCounter = new int[9];

        int autoPoints = 0;
        int teleopPoints = 0;
        int totalPoints = 0;

        EditText matchNumberTxt = (EditText)findViewById(R.id.txtMatchNum);
        EditText teamNumberTxt = (EditText)findViewById(R.id.txtTeamNum);
        EditText totalPointsTxt = (EditText)findViewById(R.id.txtTotalPoints);

        if (isQualifierCheckBox.isChecked()) isQualifier = true;
        else isQualifier = false;

        //Get values of spinners
        for (int i = 0; i < 9; i++) {
            defenseAutoStatus[i] = String.valueOf(autoStatusSelect[i].getSelectedItem());
            defenseTeleopStatus[i] = String.valueOf(teleopStatusSelect[i].getSelectedItem());

            //Tally up autonomous points from defense crossings
            if (defenseAutoStatus[i].equals("Reached")) {
                autoPoints += 2; //Add 2 points to auto score for reaching a defense
            }
            else if (defenseAutoStatus[i].equals("Crossed Once")) {
                autoPoints += 10;
            }
            else if (defenseAutoStatus[i].equals("Crossed Twice")) {
                autoPoints += 20;
            }

            //Tally up teleop points from defense crossings
            if (defenseTeleopStatus[i].equals("Crossed Once")) {
                teleopPoints += 5;
            }
            else if (defenseTeleopStatus[i].equals("Crossed Twice")) {
                teleopPoints += 10;
            }
        }



        for (int i = 0; i < 9; i++) {
            defenseTeleopStatus[i] = String.valueOf(teleopStatusSelect[i].getSelectedItem());
        }

        eventCode = MainMenu.eventCodeFromProperties;
        matchNumber = matchNumberTxt.getText().toString();
        teamNumber = teamNumberTxt.getText().toString();
        totalAlliancePoints = totalPointsTxt.getText().toString();

        //Prepares the message that will be sent to the server in CSV format
        BluetoothSender.message = scoutID + "," + eventCode + "," + matchNumber + "," + teamNumber + "," + alliance + "," + totalAlliancePoints;

        //Send the message over bluetooth
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScoutingForm.this, BluetoothSender.class);
                startActivity(intent);
            }
        });
    }

    private void openMatchHelp() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScoutingForm.this, MatchScoutingHelp.class);
                startActivity(intent);
            }
        });
    }

    //Listen for clicks of buttons and do appropriate actions based on which button is clicked
    @Override
    public void onClick(View v) {
        TextView lblAutoLowGoals = (TextView)findViewById(R.id.lblALNum);
        switch (v.getId()) {

            case R.id.btnSubmitScoutForm:
                //Submit the form
                submitMatchScoutFormClick();
                break;

            case R.id.btnALMinus:
                //Subtract one from the auto low goals
                if (autoLowGoals >= 0 && autoLowGoals <= 99) {
                    autoLowGoals --;
                    lblAutoLowGoals.setText(autoLowGoals);

                }
                break;

            case R.id.btnALPlus:
                //Add one to the auto low goals
                if (autoLowGoals >= 0 && autoLowGoals <= 99) {
                    autoLowGoals ++;
                    lblAutoLowGoals.setText(autoLowGoals);
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
