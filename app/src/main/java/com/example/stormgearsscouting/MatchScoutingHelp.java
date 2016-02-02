package com.example.stormgearsscouting;

import android.os.Bundle;
import android.app.Activity;

public class MatchScoutingHelp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scouting_help);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
