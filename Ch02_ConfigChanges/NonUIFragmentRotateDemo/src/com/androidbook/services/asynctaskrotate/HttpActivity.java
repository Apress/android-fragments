package com.androidbook.services.asynctaskrotate;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HttpActivity extends Activity {
    protected static String TAG="HttpActivity";
    private NonUIFragment workerFrag;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void doClick(View view) {
    	FragmentManager fragMgr = getFragmentManager();
    	workerFrag = (NonUIFragment) fragMgr.findFragmentByTag("worker");
    	if( workerFrag != null) {
    		Log.v(TAG, "found a worker fragment, getting image...");
    		workerFrag.getImage();
    	}
    	else {
    		Log.v(TAG, "need to make a new worker fragment");
    		workerFrag = NonUIFragment.newInstance();
    		fragMgr.beginTransaction()
    				.add(workerFrag, "worker")
    				.commit();
    		// At this point, the workerFrag exists, but is not
    		// connected to anything. When it starts up, it will create
    		// a background task to download the image for us.
    	}
    }
}
