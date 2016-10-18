package com.androidbook.preferences.main;

import java.util.List;
import android.preference.PreferenceActivity;

public class MainPreferenceActivity extends PreferenceActivity
{
    @Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.headers, target);
	}
}
