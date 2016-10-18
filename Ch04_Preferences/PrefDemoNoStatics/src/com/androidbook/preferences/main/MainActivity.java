package com.androidbook.preferences.main;
// This file is MainActivity.java

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Resources resources;
	private TextView tv = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        PreferenceManager.setDefaultValues(this, R.xml.main, false);
        
        resources = this.getResources();

        tv = (TextView)findViewById(R.id.text1);
        
        setOptionText();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.mainmenu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
       if (item.getItemId() == R.id.menu_prefs)
       {
           Intent intent = new Intent()
           		.setClass(this,
                com.androidbook.preferences.main.MainPreferenceActivity.class);
           this.startActivityForResult(intent, 0);
       }
       return true;
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
    	super.onActivityResult(reqCode, resCode, data);
    	setOptionText();
    }
    
    private void setOptionText()
    {
    	String valuesText;
        SharedPreferences prefs =
        	    PreferenceManager.getDefaultSharedPreferences(this);
//      This is the other way to get to the shared preferences:
//    	SharedPreferences prefs = getSharedPreferences(
//    			"com.androidbook.preferences.main_preferences", 0);

    	String flight_option = prefs.getString(
    	        resources.getString(R.string.flight_sort_option),
    	        resources.getString(R.string.flight_sort_option_default_value));

    	String[] optionText = resources.getStringArray(R.array.flight_sort_options);

    	// String together a selection of preferences to display
        valuesText = "option value is " + flight_option + " (" + 
        		optionText[Integer.parseInt(flight_option)] + ")";

        valuesText += "\nShow Airline: " +
        		prefs.getBoolean("show_airline_column_pref", false);

        valuesText += "\nAlert email address: " +
        		prefs.getString("alert_email_address", "");
        
        valuesText += "\nFavorite pizza toppings: " +
        		prefs.getStringSet("pizza_toppings", null);
        
        tv.setText(valuesText);
        
        
    }
}