package org.stormgearsfrc.stormgearsscouting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

public class ScoutingDataViewer extends ActionBarActivity implements View.OnClickListener {

    static BufferedReader in;
    static String teamToFind;
    static TextView teamDataLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_data_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button findTeamDataButton = (Button) findViewById(R.id.btnFindData);
        findTeamDataButton.setOnClickListener(this);

        teamDataLbl = (TextView) findViewById(R.id.lblTeamData);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Listen for clicks of buttons and do appropriate actions based on which button is clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnFindData:
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                EditText teamToFindTxt = (EditText) findViewById(R.id.txtTeamToFind);
                teamToFind = teamToFindTxt.getText().toString();

                // Create an instance of CSVParser class and parse CSV according to what team number was entered
                CSVParser dataFinder = new CSVParser();
                Thread t = new Thread(dataFinder);
                t.start();
                while (CSVParser.dataLoaded == false) {
                    teamDataLbl.setText("Data loading...");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                teamDataLbl.setText(CSVParser.teamData);
                break;
        }
    }
}
