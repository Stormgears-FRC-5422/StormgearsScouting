package org.stormgearsfrc.stormgearsscouting;

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

    static String alliance = "Red", offDef = "Defensive";
    static boolean isQualifier;
    static int autoHighGoals = 0, autoLowGoals = 0, teleopHighGoals = 0, teleopLowGoals = 0;

    Spinner[] autoStatusSelect = new Spinner[9];
    Spinner[] teleopStatusSelect = new Spinner[9];
    Spinner castleStatus;

    CheckBox isQualifierCheckBox = null, breachCheckbox = null, captureCheckbox = null;

    TextView lblAutoLowGoals, lblAutoHighGoals, lblTeleLowGoals, lblTeleHighGoals;

    RadioGroup grpAlliance, grpOffDef;
    RadioButton selectedAlliance, selectedOffDef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        isQualifierCheckBox = (CheckBox) findViewById(R.id.chkIsQualifier);
        breachCheckbox = (CheckBox) findViewById(R.id.chkBreach);
        captureCheckbox = (CheckBox) findViewById(R.id.chkCapture);



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

        //Create GUI elements after the dialog box

        Button submitScoutForm = (Button) findViewById(R.id.btnSubmitScoutForm);
        submitScoutForm.setOnClickListener(this);

        //Declare number picker labels
        lblAutoLowGoals = (TextView) findViewById(R.id.lblALNum);
        lblAutoHighGoals = (TextView) findViewById(R.id.lblAHNum);
        lblTeleLowGoals = (TextView) findViewById(R.id.lblTLNum);
        lblTeleHighGoals = (TextView) findViewById(R.id.lblTHNum);

        //Add action listeners to buttons
        Button btnALMinus = (Button) findViewById(R.id.btnALMinus);
        Button btnALPlus = (Button) findViewById(R.id.btnALPlus);
        Button btnAHMinus = (Button) findViewById(R.id.btnAHMinus);
        Button btnAHPlus = (Button) findViewById(R.id.btnAHPlus);

        btnALMinus.setOnClickListener(this);
        btnALPlus.setOnClickListener(this);
        btnAHMinus.setOnClickListener(this);
        btnAHPlus.setOnClickListener(this);

        Button btnTLMinus = (Button) findViewById(R.id.btnTLMinus);
        Button btnTLPlus = (Button) findViewById(R.id.btnTLPlus);
        Button btnTHMinus = (Button) findViewById(R.id.btnTHMinus);
        Button btnTHPlus = (Button) findViewById(R.id.btnTHPlus);

        btnTLMinus.setOnClickListener(this);
        btnTLPlus.setOnClickListener(this);
        btnTHMinus.setOnClickListener(this);
        btnTHPlus.setOnClickListener(this);

        final RadioButton redOpt = (RadioButton) findViewById(R.id.optRed);
        redOpt.setOnClickListener(this);

        final RadioButton blueOpt = (RadioButton) findViewById(R.id.optBlue);
        blueOpt.setOnClickListener(this);

        final RadioButton offensive = (RadioButton) findViewById(R.id.optOffensive);
        offensive.setOnClickListener(this);

        final RadioButton defensive = (RadioButton) findViewById(R.id.optDefensive);
        defensive.setOnClickListener(this);

        castleStatus = (Spinner) findViewById(R.id.spnCastleStatus);
        castleStatus.setOnItemSelectedListener(new MyOnItemSelectedListener());

        //Set up autonomous defense status spinners
        autoStatusSelect[0] = (Spinner) findViewById(R.id.spnID0DefenseAutoStatus);
        autoStatusSelect[0].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[1] = (Spinner) findViewById(R.id.spnID1DefenseAutoStatus);
        autoStatusSelect[1].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[2] = (Spinner) findViewById(R.id.spnID2DefenseAutoStatus);
        autoStatusSelect[2].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[3] = (Spinner) findViewById(R.id.spnID3DefenseAutoStatus);
        autoStatusSelect[3].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[4] = (Spinner) findViewById(R.id.spnID4DefenseAutoStatus);
        autoStatusSelect[4].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[5] = (Spinner) findViewById(R.id.spnID5DefenseAutoStatus);
        autoStatusSelect[5].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[6] = (Spinner) findViewById(R.id.spnID6DefenseAutoStatus);
        autoStatusSelect[6].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[7] = (Spinner) findViewById(R.id.spnID7DefenseAutoStatus);
        autoStatusSelect[7].setOnItemSelectedListener(new MyOnItemSelectedListener());

        autoStatusSelect[8] = (Spinner) findViewById(R.id.spnID8DefenseAutoStatus);
        autoStatusSelect[8].setOnItemSelectedListener(new MyOnItemSelectedListener());


        //Set up teleop defense status spinners
        teleopStatusSelect[0] = (Spinner) findViewById(R.id.spnID0DefenseTeleopStatus);
        teleopStatusSelect[0].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[1] = (Spinner) findViewById(R.id.spnID1DefenseTeleopStatus);
        teleopStatusSelect[1].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[2] = (Spinner) findViewById(R.id.spnID2DefenseTeleopStatus);
        teleopStatusSelect[2].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[3] = (Spinner) findViewById(R.id.spnID3DefenseTeleopStatus);
        teleopStatusSelect[3].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[4] = (Spinner) findViewById(R.id.spnID4DefenseTeleopStatus);
        teleopStatusSelect[4].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[5] = (Spinner) findViewById(R.id.spnID5DefenseTeleopStatus);
        teleopStatusSelect[5].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[6] = (Spinner) findViewById(R.id.spnID6DefenseTeleopStatus);
        teleopStatusSelect[6].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[7] = (Spinner) findViewById(R.id.spnID7DefenseTeleopStatus);
        teleopStatusSelect[7].setOnItemSelectedListener(new MyOnItemSelectedListener());

        teleopStatusSelect[8] = (Spinner) findViewById(R.id.spnID8DefenseTeleopStatus);
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

        grpAlliance = (RadioGroup) findViewById(R.id.allianceRadioGroup);
        grpOffDef = (RadioGroup) findViewById(R.id.grpDefOff);

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

        String scoutID = "0", eventCode = "0", matchNumber = "0", teamNumber = "0", totalAlliancePointsRed = "0", totalAlliancePointsBlue = "0", comments = "No Comments", fouls = "0", castleStatusString;
        //0 is match scout; 1 is pit scout

        int[] defenseAutoCounter = new int[9];
        int[] defenseTeleopCounter = new int[9];
        int[] defenseCounter = new int[9];

        int autoPoints = 0;
        int teleopPoints = 0;
        int totalPoints = 0;
        int rankingPoints = 0;

        boolean breach = breachCheckbox.isChecked(), captured = captureCheckbox.isChecked();

        EditText matchNumberTxt = (EditText) findViewById(R.id.txtMatchNum);
        EditText teamNumberTxt = (EditText) findViewById(R.id.txtTeamNum);
        EditText totalPointsTxtRed = (EditText) findViewById(R.id.txtTotalPointsRed);
        EditText totalPointsTxtBlue = (EditText) findViewById(R.id.txtTotalPointsBlue);
        EditText txtFouls = (EditText) findViewById(R.id.txtFouls);
        EditText txtComments = (EditText) findViewById(R.id.txtComments);

        if (isQualifierCheckBox.isChecked()) isQualifier = true;
        else isQualifier = false;

        //Get values of spinners
        for (int i = 0; i < 9; i++) {
            defenseAutoStatus[i] = String.valueOf(autoStatusSelect[i].getSelectedItem());
            defenseTeleopStatus[i] = String.valueOf(teleopStatusSelect[i].getSelectedItem());

            //Tally up autonomous points from defense crossings
            if (defenseAutoStatus[i].equals("Reached")) {
                autoPoints += 2; //Add 2 points to auto score for reaching a defense
            } else if (defenseAutoStatus[i].equals("Crossed Once")) {
                autoPoints += 10;
                defenseAutoCounter[i]++;
            } else if (defenseAutoStatus[i].equals("Crossed Twice")) {
                defenseAutoCounter[i] += 2;
                autoPoints += 20;
            }

            //Tally up teleop points from defense crossings
            if (defenseTeleopStatus[i].equals("Crossed Once")) {
                teleopPoints += 5;
                defenseTeleopCounter[i] ++;
            } else if (defenseTeleopStatus[i].equals("Crossed Twice")) {
                teleopPoints += 10;
                defenseTeleopCounter[i] += 2;
            }
        }

        //Add up crossings from auto and teleop
        for (int i = 0; i < 9; i++) {
            defenseCounter[i] = defenseAutoCounter[i] + defenseTeleopCounter[i];
        }

        //Calculate ranking points
        if (breach && isQualifier) rankingPoints += 1;
        else if (breach && !isQualifier) totalPoints += 20;

        if (captured && isQualifier) rankingPoints += 1;

        eventCode = MainMenu.eventCodeFromProperties;
        matchNumber = matchNumberTxt.getText().toString();
        teamNumber = teamNumberTxt.getText().toString();
        totalAlliancePointsRed = totalPointsTxtRed.getText().toString();
        totalAlliancePointsBlue = totalPointsTxtBlue.getText().toString();

        if (!txtFouls.getText().toString().equals("")) fouls = txtFouls.getText().toString();

        if (!txtComments.getText().toString().equals("")) comments = txtComments.getText().toString();

        castleStatusString = String.valueOf(castleStatus.getSelectedItem());

        if (matchNumber.equals("")) {
            //Show a dialog that tells the user that they need to enter a match number before continuing
            AlertDialog.Builder dlgNoMatchNum = new AlertDialog.Builder(this);
            dlgNoMatchNum.setMessage("There is no match number entered. Please scroll to the top and enter one.");
            dlgNoMatchNum.setTitle("Please Enter Match Number");

            dlgNoMatchNum.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });

            dlgNoMatchNum.setCancelable(false);
            dlgNoMatchNum.create().show();
            return;
        }

        if (teamNumber.equals("")) {
            //Show a dialog that tells the user thatmatch they need to enter a team number before continuing
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

        if (totalAlliancePointsRed.equals("")) totalAlliancePointsRed = "0";
        if (totalAlliancePointsBlue.equals("")) totalAlliancePointsBlue = "0";

        //Do remaining points calculations
        if (castleStatusString.equals("Did Nothing")) {
            teleopPoints += 0;
        }
        else if (castleStatusString.equals("Drove Near Castle")) {
            teleopPoints += 0;
        }
        else if (castleStatusString.equals("Grappled Onto Castle") || castleStatusString.equals("Grappled/Lifted Part Way") && (autoLowGoals + autoHighGoals + teleopLowGoals + teleopHighGoals) >= 8) {
            teleopPoints += 5;
        }
        else if ((autoLowGoals + autoHighGoals + teleopLowGoals + teleopHighGoals) >= 8) teleopPoints += 15;

        autoPoints += (autoLowGoals * 5) + (autoHighGoals * 10);
        teleopPoints += (teleopLowGoals * 2) + (teleopHighGoals * 5);

        totalPoints = totalPoints + autoPoints + teleopPoints;

        // get selected radio button from alliance buttons
        int allianceID = grpAlliance.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        selectedAlliance = (RadioButton) findViewById(allianceID);
        alliance = selectedAlliance.getText().toString();

        //Calculate ranking points if they won the match
        if (alliance.equals("Red") && Integer.parseInt(totalAlliancePointsRed) > Integer.parseInt(totalAlliancePointsBlue)) rankingPoints += 2;
        else if (alliance.equals("Blue") && Integer.parseInt(totalAlliancePointsBlue) > Integer.parseInt(totalAlliancePointsRed)) rankingPoints += 2;
        else if (Integer.parseInt(totalAlliancePointsRed) == Integer.parseInt(totalAlliancePointsBlue)) rankingPoints++;

        if (!isQualifier) rankingPoints = 0;

        // get selected radio button from def/off buttons
        int defOffID = grpOffDef.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        selectedOffDef = (RadioButton) findViewById(defOffID);
        offDef = selectedOffDef.getText().toString();

        //Prepares the message that will be sent to the server in CSV format
        BluetoothSender.message = scoutID + "," + eventCode + "," + matchNumber + "," + teamNumber + "," + alliance + "," + totalAlliancePointsRed + "," + totalAlliancePointsBlue + ","
                + totalPoints + "," + defenseCounter[0] + "," + defenseCounter[1] + "," + defenseCounter[2] + "," + defenseCounter[3] + "," + defenseCounter[4] + "," + defenseCounter[5] + ","
                + defenseCounter[6] + "," + defenseCounter[7] + "," + defenseCounter[8] + "," + fouls + "," + rankingPoints + "," + comments + "," + "!" + "," + "!" + "," + "!" + "," + "!" +
                "," + "!" + "," + "!" + "," + "!" + "," + castleStatusString + "," + offDef + "," + isQualifier;

        System.out.println(BluetoothSender.message);

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

        switch (v.getId()) {

            case R.id.btnSubmitScoutForm:
                //Submit the form
                submitMatchScoutFormClick();
                break;

            //Number picker buttons
            case R.id.btnALMinus:
                //Subtract one from the auto low goals
                if (autoLowGoals > 0) {
                    autoLowGoals--;
                    //Set text of label
                    lblAutoLowGoals.setText(autoLowGoals + "");
                }
                break;

            case R.id.btnALPlus:
                //Add one to the auto low goals
                if (autoLowGoals < 100) {
                    autoLowGoals++;
                    //Set text of label
                    lblAutoLowGoals.setText(autoLowGoals + "");
                }
                break;

            case R.id.btnAHMinus:
                //Subtract one from the auto high goals
                if (autoHighGoals > 0) {
                    autoHighGoals--;
                    //Set text of label
                    lblAutoHighGoals.setText(autoHighGoals + "");
                }
                break;

            case R.id.btnAHPlus:
                //Add one to the auto high goals
                if (autoHighGoals < 100) {
                    autoHighGoals++;
                    //Set text of label
                    lblAutoHighGoals.setText(autoHighGoals + "");
                }
                break;

            case R.id.btnTLMinus:
                //Subtract one from the tele low goals
                if (teleopLowGoals > 0) {
                    teleopLowGoals--;
                    //Set text of label
                    lblTeleLowGoals.setText(teleopLowGoals + "");
                }
                break;

            case R.id.btnTLPlus:
                //Add one to the tele low goals
                if (teleopLowGoals < 100) {
                    teleopLowGoals++;
                    //Set text of label
                    lblTeleLowGoals.setText(teleopLowGoals + "");
                }
                break;

            case R.id.btnTHMinus:
                //Subtract one from the tele high goals
                if (teleopHighGoals > 0) {
                    teleopHighGoals--;
                    //Set text of label
                    lblTeleHighGoals.setText(teleopHighGoals + "");
                }
                break;

            case R.id.btnTHPlus:
                //Add one to the tele high goals
                if (teleopHighGoals < 100) {
                    teleopHighGoals++;
                    //Set text of label
                    lblTeleHighGoals.setText(teleopHighGoals + "");
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
