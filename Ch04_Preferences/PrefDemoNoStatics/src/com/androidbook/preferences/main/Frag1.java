package com.androidbook.preferences.main;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;


public class Frag1 extends PreferenceFragment  
    implements OnPreferenceChangeListener, 
               OnSharedPreferenceChangeListener {
    private EditTextPreference pkgPref;
	private EditTextPreference emailPref;
	private ListPreference listPref;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main);

        listPref = (ListPreference)findPreference("flight_sort_option");
        pkgPref = (EditTextPreference)
                      findPreference("package_name_preference");
        pkgPref.setSummary(pkgPref.getText());
        emailPref = (EditTextPreference)
                      findPreference("alert_email_address");
        emailPref.setSummary(emailPref.getText());
    }
	
	@Override
	public boolean onPreferenceChange(Preference preference,
			                          Object newValue) {
        final String key = preference.getKey();
        if ("package_name_preference".equals(key)) {
        	pkgPref.setSummary(newValue.toString());
        }
        else if ("alert_email_address".equals(key)) {
        	emailPref.setSummary(newValue.toString());
        }
        else if ("flight_sort_option".equals(key)) {
        	setFlightOptionsSummary(newValue.toString());
        }
        // true indicates it's okay to update the preference
        return true;  
	}
	
	public void onResume() {
		super.onResume();
		// getPreferenceManager().getSharedPreferences()
		//        .registerOnSharedPreferenceChangeListener(this);
		listPref.setOnPreferenceChangeListener(this);
		setFlightOptionsSummary(null);
		pkgPref.setOnPreferenceChangeListener(this);
		pkgPref.setSummary(pkgPref.getText());
		emailPref.setOnPreferenceChangeListener(this);
		emailPref.setSummary(emailPref.getText());
	}
	
	public void onPause() {
		super.onPause();
		// getPreferenceManager().getSharedPreferences()
		//        .unregisterOnSharedPreferenceChangeListener(this);
		listPref.setOnPreferenceChangeListener(null);
		pkgPref.setOnPreferenceChangeListener(null);
		emailPref.setOnPreferenceChangeListener(null);
	}

	@Override
	public void onSharedPreferenceChanged(
			SharedPreferences sharedPreferences, String key) {
		if("package_name_preference".equals(key)) {
			pkgPref.setSummary(pkgPref.getText());
		}
		else if("alert_email_address".equals(key)) {
			emailPref.setSummary(emailPref.getText());
		}
		else if("flight_sort_option".equals(key)) {
			setFlightOptionsSummary(listPref.getValue());
		}
	}

	private void setFlightOptionsSummary(String newValue) {
		String setTo = newValue;
		if(setTo == null)
			setTo = listPref.getValue();
    	String[] optionText = this.getResources().getStringArray(R.array.flight_sort_options);
    	try {
		    listPref.setSummary("Currently set to " + optionText[Integer.valueOf(setTo)]);
    	}
    	catch(IllegalArgumentException e) {
    		listPref.setSummary("Preference error: unknown value of listPref: " + setTo);
    	}
	}
}
