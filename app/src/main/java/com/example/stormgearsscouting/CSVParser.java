package com.example.stormgearsscouting;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CSVParser implements Runnable {

    public static String teamData = "";
    public static boolean dataLoaded = false;

    @Override
    public void run() {
        URL stockURL;

        try {
            stockURL = new URL("https://docs.google.com/spreadsheets/d/1d0Fn2G3koMXXLYz9q0lLr5wFiabFAAT3oXNwx7aSnm4/pub?output=csv");
            ScoutingDataViewer.in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
            System.out.println("I got to here.");
            CSVReader reader = new CSVReader(ScoutingDataViewer.in);
            String[] nextLine;

            teamData = "";

            try {
                while ((nextLine = reader.readNext()) != null) {
                    // nextLine[] is an array of values from the line of the CSV
                    if (nextLine[3].trim().equals(ScoutingDataViewer.teamToFind)) {
                        String teamDataEntry = "Event Code: " + nextLine[1] + "\nMatch Number: " + nextLine[2] + "\nAlliance: " + nextLine[4] + "\nTotal Points: " + nextLine[5];
                        teamData = teamData + "\n\n" + teamDataEntry;
                        System.out.println("It got to retrieving the data. \n\n" + teamData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataLoaded = true;
        //System.out.println(teamData);
    }
}