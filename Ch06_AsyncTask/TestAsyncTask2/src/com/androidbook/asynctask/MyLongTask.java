package com.androidbook.asynctask;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
public class MyLongTask 
extends AsyncTask<String,Integer,Integer>
implements OnCancelListener
{
	private IReportBack r;
	private Context ctx;
	private String tag = null;
	private ProgressDialog pd = null;
	final private int PDMAX = 5;
	private int pd_progress = 0;
	MyLongTask(IReportBack inr, Context inCtx, String inTag)
	{
		r = inr;
		ctx = inCtx;
		tag = inTag;
	}
	protected void onPreExecute()
	{
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
		pd = newPDinstance(0);
		pd.show();
	}
    protected void onProgressUpdate(Integer... progress) 
    {
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
		this.reportThreadSignature();
		
		//will be called multiple times
		//triggered by onPostExecute
    	Integer i = progress[0];
    	r.reportBack(tag, "Progress:" + i.toString());
    	pd.setProgress(i);
    }     
    protected void onPostExecute(Integer result) 
    {         
		//Runs on the main ui thread
		Utils.logThreadSignature(this.tag);
    	r.reportBack(tag, "onPostExecute result:" + result);
    	pd.cancel();
    	r.allDone(0);
    }
    protected Integer doInBackground(String...strings)
    {
		//Runs on a worker thread
    	//May even be a pool if there are 
    	//more tasks.
		Utils.logThreadSignature(this.tag);
		
    	for(String s :strings)
    	{
    		Log.d(tag, "Processing:" + s);
    	}
    	for (int i=0;i<PDMAX;i++)
    	{
    		Utils.sleepForInSecs(2);
    		publishProgress(i+1);
    		if(isCancelled()) {
    			Log.e(tag, "*** This task has been cancelled");
    			break;
    		}
    	}
    	return 1;
    }
    
    public void onCancelled(Integer result) {
        Log.d(tag, "AsyncTask was cancelled");
        r.allDone(1);
    }
    
    protected void reportThreadSignature()
    {
    	String s = Utils.getThreadSignature();
    	r.reportBack(tag,s);
    }

    public void onCancel(DialogInterface d)
    {
    	r.reportBack(tag,"Cancel called on the dialog");
    	// Therefore, cancel the AsyncTask
    	this.cancel(false);
    }
    
    // Must detach the progress dialog from the
    // activity before it's gone away, otherwise
    // we'll get a window leaked exception.
    public void dismissDialog() {
    	if(pd != null) {
    	    pd_progress = pd.getProgress();
    	    pd.dismiss();
    	}
    }
    
    // When there's a new activity, need to kill the
    // old dialog and create a new one, but with the
    // latest progress.
    public void setActivity(Context newCtx) {
    	r = (IReportBack)newCtx;
    	ctx = newCtx;
    	pd = newPDinstance(pd_progress);
    	pd.show();
    }
    
    private ProgressDialog newPDinstance(int progress) {
    	ProgressDialog newPD = new ProgressDialog(ctx);
    	newPD.setTitle("title");
    	newPD.setMessage("In progress...");
    	newPD.setCancelable(true);
    	newPD.setOnCancelListener(this);
    	newPD.setIndeterminate(false);
    	newPD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    	newPD.setMax(PDMAX);
    	newPD.setProgress(progress);
    	return newPD;
    }
}
