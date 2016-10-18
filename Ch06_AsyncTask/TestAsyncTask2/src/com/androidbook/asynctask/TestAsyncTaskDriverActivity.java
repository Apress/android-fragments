package com.androidbook.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class TestAsyncTaskDriverActivity extends Activity
implements IReportBack
{
	public static final String tag="TestAsyncTaskDriverActivity";
	private static final String ASYNCTASKFRAG = "ASYNCTASKFRAG";
	
	private AsyncTaskFragment atf = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if ((atf = (AsyncTaskFragment) getFragmentManager()
                .findFragmentByTag(ASYNCTASKFRAG)) != null) {
			// we found an incomplete AsyncTask in the background
			Log.d(tag, "Found an incomplete AsyncTask");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){ 
    	super.onCreateOptionsMenu(menu);
 	   	MenuInflater inflater = getMenuInflater();
 	   	inflater.inflate(R.menu.main_menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){ 
    	appendMenuItemText(item);
    	if (item.getItemId() == R.id.menu_clear){
    		this.emptyText();
    		return true;
    	}
    	if (item.getItemId() == R.id.menu_test_async1){
    		if(atf == null) {
                atf = AsyncTaskFragment
                	.newInstance("String1","String2","String3");
                getFragmentManager().beginTransaction()
	                .add(atf, ASYNCTASKFRAG).commit();
    		}
    		return true;
    	}
       	return true;
    }
    private TextView getTextView(){
        return (TextView)this.findViewById(R.id.text1);
    }
    private void appendMenuItemText(MenuItem menuItem){
       String title = menuItem.getTitle().toString();
       TextView tv = getTextView(); 
       tv.setText(tv.getText() + "\n" + title);
    }
    private void emptyText(){
       TextView tv = getTextView();
       tv.setText("");
    }
    private void appendText(String s){
       TextView tv = getTextView(); 
       tv.setText(tv.getText() + "\n" + s);
       Log.d(tag,s);
    }
	public void reportBack(String tag, String message)
	{
		this.appendText(tag + ":" + message);
		Log.d(tag,message);
	}
	public void reportTransient(String tag, String message)
	{
		String s = tag + ":" + message;
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
		reportBack(tag,message);
		Log.d(tag,message);
	}

	public void allDone(int status) {
		// Could do various things based on the returned status
		// but need to throw away the fragment so we can do this
		// again if needed.
		Log.d(tag, "AsyncTask returned: " + status);
        getFragmentManager().beginTransaction()
            .remove(atf).commitAllowingStateLoss();
		atf = null;
	}
}