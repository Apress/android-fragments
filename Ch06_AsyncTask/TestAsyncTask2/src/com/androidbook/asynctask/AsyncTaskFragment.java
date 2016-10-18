package com.androidbook.asynctask;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class AsyncTaskFragment extends Fragment {
	private static final String tag = "AsyncTaskFragment";
	private TestAsyncTaskDriverActivity ctx = null;
	private MyLongTask mlt = null;
	
	public static AsyncTaskFragment newInstance(String... params) {
		AsyncTaskFragment myMF = new AsyncTaskFragment();
		Bundle bundle = new Bundle();
		bundle.putStringArray("params", params);
		myMF.setArguments(bundle);
		return myMF;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		ctx = (TestAsyncTaskDriverActivity) activity;
		if(mlt != null && 
			mlt.getStatus() != AsyncTask.Status.FINISHED) {
			// we must have an incomplete task, make
			// sure it has the correct activity
			mlt.setActivity(activity);
		}
		else {
			mlt = new MyLongTask(ctx, ctx, "Task1");
			String[] params = this.getArguments().getStringArray("params");
			mlt.execute(params);			
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRetainInstance(true); 
	}
	
	// When the activity is going away, make sure
	// to dismiss the dialog if it's there.
	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(tag, "calling for dismissal of the dialog");
		if(mlt != null) {
			mlt.dismissDialog();
		}
	}
}
