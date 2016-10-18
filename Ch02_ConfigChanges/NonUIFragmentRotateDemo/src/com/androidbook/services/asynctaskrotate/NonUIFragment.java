package com.androidbook.services.asynctaskrotate;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NonUIFragment extends Fragment {
    private HttpActivity myActivity = null;
    int mCurCheckPosition = 0;
    private DownloadImageTask diTask = null;

    static public NonUIFragment newInstance() {
    	NonUIFragment frag = new NonUIFragment();
        // Here is the magic phrase to prevent this fragment from being
        // destroyed and recreated during configuration changes. Must set
    	// this before the fragment ends up on a back stack.
        frag.setRetainInstance(true);
    	return frag;
    }
    
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle icicle) {
    	Log.v(HttpActivity.TAG,
    			"in fragment onInflate. AttributeSet contains:");
    	for(int i=0; i<attrs.getAttributeCount(); i++) {
            Log.v(HttpActivity.TAG, "    " + attrs.getAttributeName(i) +
            		" = " + attrs.getAttributeValue(i));
    	}
    	super.onInflate(activity, attrs, icicle);
    }

    @Override
    public void onAttach(Activity myActivity) {
    	Log.v(HttpActivity.TAG, "in fragment onAttach; activity is: " + myActivity);
    	super.onAttach(myActivity);
    	this.myActivity = (HttpActivity)myActivity;
    }

    @Override
    public void onCreate(Bundle icicle) {
    	Log.v(HttpActivity.TAG, "in fragment onCreate. Bundle contains:");
    	if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(HttpActivity.TAG, "    " + key);
            }
    	}
    	else {
            Log.v(HttpActivity.TAG, "    myBundle is null");
    	}
    	super.onCreate(icicle);
        if (icicle != null) {
            // Restore last state for checked position.
            mCurCheckPosition = icicle.getInt("curChoice", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater myInflater, ViewGroup container, Bundle icicle) {
    	Log.v(HttpActivity.TAG, "in fragment onCreateView. container is " + container);
    	return super.onCreateView(myInflater, container, icicle);
    }
    
    @Override
    public void onActivityCreated(Bundle icicle) {
    	Log.v(HttpActivity.TAG, "in fragment onActivityCreated. icicle contains:");
    	if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(HttpActivity.TAG, "    " + key);
            }
    	}
    	else {
            Log.v(HttpActivity.TAG, "    icicle is null");
    	}
        super.onActivityCreated(icicle);
        // It is here that our activity is created and we can re-establish the
        // image fetched by the background thread, or let the background
        // thread know what the new activity reference is.
        
        // The following checks to see if we're restarting with a backgrounded
        // AsyncTask. If so, re-establish the connection. Also, since the image
        // did not get saved across the destroy/create cycle, if the AsyncTask
        // has finished, grab the downloaded image again from the AsyncTask.
        if(diTask != null) {
        	Log.v(HttpActivity.TAG, "fragment has an existing download task");
        	diTask.setContext(myActivity);  // Give my AsyncTask the new Activity reference
        	if(diTask.getStatus() == AsyncTask.Status.FINISHED) {
            	Log.v(HttpActivity.TAG, "   task was done, setting image from memory");
        		diTask.setImageInView();
        	}
        }
        else {
        	// If we didn't already have a download image task, go make
        	// a new one now to download the image.
        	Log.v(HttpActivity.TAG, "   new fragment created, go get image");
        	getImage();
        }
    }

    @Override
    public void onStart() {
    	Log.v(HttpActivity.TAG, "in fragment onStart");
    	super.onStart();
    }

    @Override
    public void onResume() {
    	Log.v(HttpActivity.TAG, "in fragment onResume");
    	super.onResume();
    }

    @Override
    public void onPause() {
    	Log.v(HttpActivity.TAG, "in fragment onPause");
    	super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle icicle) {
    	Log.v(HttpActivity.TAG, "in fragment onSaveInstanceState");
        super.onSaveInstanceState(icicle);
        icicle.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onStop() {
    	Log.v(HttpActivity.TAG, "in fragment onStop");
    	super.onStop();
    }

    @Override
    public void onDestroyView() {
    	Log.v(HttpActivity.TAG, "in fragment onDestroyView");
    	super.onDestroyView();
    }

    @Override
    public void onDestroy() {
    	Log.v(HttpActivity.TAG, "in fragment onDestroy");
    	super.onDestroy();
    }

    @Override
    public void onDetach() {
    	Log.v(HttpActivity.TAG, "in fragment onDetach");
    	super.onDetach();
    	myActivity = null;
    }

    @Override
    public void onViewCreated(View view, Bundle icicle) {
    	Log.v(HttpActivity.TAG, "in fragment onViewCreated");
    	super.onViewCreated(view, icicle);
    }
    
    public void getImage() {
    	Log.v(HttpActivity.TAG, "in fragment getImage");
    	if(diTask != null) {
    		AsyncTask.Status diStatus = diTask.getStatus();
    		Log.v(HttpActivity.TAG, "diTask status is " + diStatus);
    		if(diStatus != AsyncTask.Status.FINISHED) {
    			Log.v(HttpActivity.TAG, "... no need to start a new task");
                return;
    		}
    		// Since diStatus must be FINISHED, we can go again.
    	}
    	Log.v(HttpActivity.TAG, "starting new download image task...");
  	    diTask = new DownloadImageTask(myActivity);
   	    diTask.execute("https://chart.googleapis.com/chart?chl=Froyo%7CGingerbread%7CIce%20Cream%20Sandwich%7CJelly%20Bean%7CKitKat&chd=t%3A0.6%2C9.8%2C8.5%2C50.9%2C30.2&chf=bg%2Cs%2C00000000&chco=c4df9b%2C6fad0c&cht=p&chs=500x250");
    }
}
