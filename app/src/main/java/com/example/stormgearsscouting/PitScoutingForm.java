package com.example.stormgearsscouting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

public class PitScoutingForm extends ActionBarActivity implements View.OnClickListener {

    String driveTrainType = null;

    Spinner driveTrainSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouting_form);

        driveTrainSelect = (Spinner)findViewById(R.id.spnDriveTrainSelect);
        driveTrainSelect.setOnItemSelectedListener(new MyOnItemSelectedListener());


        //Enable up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (id == R.id.action_match_help) {
            openHelp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openHelp() {

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        driveTrainType = String.valueOf(driveTrainSelect.getSelectedItem());
        if (driveTrainType == null) driveTrainType = "Unknown";
        System.out.println(driveTrainType);

        finish();
        return true;
    }
}
