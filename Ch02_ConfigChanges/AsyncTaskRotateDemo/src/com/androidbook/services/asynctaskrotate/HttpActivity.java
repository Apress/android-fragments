package com.androidbook.services.asynctaskrotate;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HttpActivity extends Activity {
    private DownloadImageTask diTask;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // The following checks to see if we're restarting with a backgrounded
        // AsyncTask. If so, re-establish the connection. Also, since the image
        // did not get saved across the destroy/create cycle, if the AsyncTask
        // has finished, grab the downloaded image again from the AsyncTask.
        if( (diTask = DownloadImageTask.getInstance()) != null) {
        	diTask.setContext(this);  // Give my AsyncTask the new Activity reference
        	if(diTask.getStatus() == AsyncTask.Status.FINISHED)
        		diTask.setImageInView();
        }
    }

    public void doClick(View view) {
    	if(diTask != null) {
    		AsyncTask.Status diStatus = diTask.getStatus();
    		Log.v("doClick", "diTask status is " + diStatus);
    		if(diStatus != AsyncTask.Status.FINISHED) {
    			Log.v("doClick", "... no need to start a new task");
                return;
    		}
    		// Since diStatus must be FINISHED, we can try again.
    	}
  	    diTask = DownloadImageTask.newInstance(this);
   	    diTask.execute("https://chart.googleapis.com/chart?chl=Froyo%7CGingerbread%7CIce%20Cream%20Sandwich%7CJelly%20Bean%7CKitKat&chd=t%3A0.6%2C9.8%2C8.5%2C50.9%2C30.2&chf=bg%2Cs%2C00000000&chco=c4df9b%2C6fad0c&cht=p&chs=500x250");
    }
}
