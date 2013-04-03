package cs198.cis2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class ImageDLerActivitywithBar extends Activity {
	List<FileStats> uList;
	FileStats df;
	FileStatsDataSource datasource;
	ByteArrayBuffer baf;
	InputStream is;
	BufferedInputStream bis = new BufferedInputStream(is);
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
    FileStats f;
    String[] filenames = {};
    ProgressDialog progressDialog;
    static String[] d;
    static String ipadd = "http://cis.p.ht/CS198/androidbackend"; 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dler);

        datasource = new FileStatsDataSource(this);
        datasource.open();
        uList = datasource.getAllEmptyFileStats();
        datasource.open();
        Log.i("norvin2", uList.toString());

        if (uList.size() > 0){ // may empty rows pa
			Log.i("norvin2", "we are here!");
	        //Toast.makeText(this, "we are here!", Toast.LENGTH_LONG);
			Intent myIntent = new Intent(ImageDLerActivitywithBar.this, IdentifyActivity.class);
			ImageDLerActivitywithBar.this.startActivity(myIntent);
			ImageDLerActivitywithBar.this.finish();
        }
		else //wala nang empty rows, hence dapat magDL na ng pix (kung tapos na) or iupload yung CSV (if not)  
		{
			settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);      
	        String status = settings.getString("done", "start");
	        if (status.equalsIgnoreCase("start") || status.equalsIgnoreCase("true")){
	        	//StartDL();
	        	new LoadViewTask().execute();  
			}
	        else {
	        	Intent myIntent = new Intent(ImageDLerActivitywithBar.this, CsvActivity.class);
	    		ImageDLerActivitywithBar.this.startActivity(myIntent);
	    		ImageDLerActivitywithBar.this.finish();
	        }
		}//end
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_dler, menu);
        return true;
    }

    class OnlyExt implements FilenameFilter{
    	  String extn;   	 
    	  File dir;
    	  public OnlyExt(String extn){
    			 this.extn="." + extn;
    	  }
    	  
    	  public void setDir(File dir){
    		  this.dir = dir;		  
    	  }
    	  
    	  public boolean accept(File dir,String name){
    		  return name.endsWith(extn);
    	  }
    }
    		
    public class FilterFiles{
    		  
    		public String[] filter() throws IOException{
    		
    		String dir = Environment.getExternalStorageDirectory().toString();
    		String extn = "jpg";
    		File ff = new File(dir);
    		FilenameFilter fff = new OnlyExt(extn);
    		String s[] = ff.list(fff);
    		return s;
    		}

      }    
    	
	private void StartDL() {
		
	}

    	
    	//To use the AsyncTask, it must be subclassed  
        private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
        {  
            //Before running code in separate thread  
            @Override  
            protected void onPreExecute()  
            {  
                //Create a new progress dialog  
                progressDialog = new ProgressDialog(ImageDLerActivitywithBar.this);  
                //Set the progress dialog to display a horizontal progress bar  
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
                //Set the dialog title to 'Loading...'  
                progressDialog.setTitle("Loading...");  
                //Set the dialog message to 'Loading application View, please wait...'  
                progressDialog.setMessage("Loading application View, please wait...");  
                //This dialog can't be canceled by pressing the back key  
                progressDialog.setCancelable(false);  
                //This dialog isn't indeterminate  
                progressDialog.setIndeterminate(false);  
                //The maximum number of items is 100  
                progressDialog.setMax(100);  
                //Set the current progress to zero  
                progressDialog.setProgress(0);  
                //Display the progress dialog  
                progressDialog.show();  
            }  
      
            //The code to be executed in a background thread.  
            @Override  
            protected Void doInBackground(Void... params)  
            {  
                /* This is just a code that delays the thread execution 4 times, 
                 * during 850 milliseconds and updates the current progress. This 
                 * is where the code that is going to be executed on a background 
                 * thread must be placed. 
                 */  
                //try  
                //{  
                    //Get the current thread's token  
                    synchronized (this)  
                    {  
                    	d = DownloadImagesActivity.download();
                	    int i;
                	    for (i = 0; i < d.length; i++){
                	        URL url = null;
                			try {
                				url = new URL (ipadd+"/images/"+d[i]);
                	
                			} catch (MalformedURLException e) {
                				// TODO Auto-generated catch block
                				e.printStackTrace();
                				Log.i("norvin", "nandito sa url ang error");
                			}

                			try {
                		           URLConnection ucon = url.openConnection();
                		           is = ucon.getInputStream();
                		           bis = new BufferedInputStream(is);
                			} catch (IOException e) {
                				// TODO Auto-generated catch block
                				e.printStackTrace();
                			}
                			try {
                				/*
                	             * Read bytes to the Buffer until there is nothing more to read(-1).
                	             */
                	            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
                	            int current = 0;
                	            while ((current = bis.read()) != -1) {
                	               baf.append((byte) current);
                	            }
                	            /* Convert the Bytes read to a String. */
                	            //The sdcard directory e.g. '/sdcard' can be used directly, or 
                	            //more safely abstracted with getExternalStorageDirectory()
                	            File storagePath = Environment.getExternalStorageDirectory();
                	            FileOutputStream fos = new FileOutputStream(new File(storagePath,d[i]));
                	            fos.write(baf.toByteArray());
                	            fos.flush();
                	            fos.close();
                	            //Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
                                publishProgress(i);  

                	        } catch (IOException e) {
                				// TODO Auto-generated catch block
                				e.printStackTrace();
                			} 
                	     
                	    }			
                	    //DBMaker(); creates all filestats
                	    FilterFiles myFilter = new FilterFiles();
                	    try {
                			filenames = myFilter.filter();
                			//Toast.makeText(getApplicationContext(), "number of files="+filenames.length, Toast.LENGTH_LONG).show();
                		} catch (IOException e) {
                			// TODO Auto-generated catch block
                			e.printStackTrace();	
                	
                		}
                    }  
                //}  
                //catch (InterruptedException e)  
                //{  
                //    e.printStackTrace();  
                //}  
        	    int n = filenames.length;
        	    int ctr = 0;
        	    for (ctr = 0; ctr < n; ctr++){
        	    	f = datasource.createFileStat(filenames[ctr]);
        	    }
        	    datasource.close();
        	    Editor editor = settings.edit();
        	    editor.putString("done", "false");
        	    editor.commit();
                Intent myIntent = new Intent(ImageDLerActivitywithBar.this, IdentifyActivity.class);
        		ImageDLerActivitywithBar.this.startActivity(myIntent);
        		ImageDLerActivitywithBar.this.finish();
                return null;  
            }  
      
            //Update the progress  
            @Override  
            protected void onProgressUpdate(Integer... values)  
            {  
                //set the current progress of the progress dialog  
                progressDialog.setProgress(values[0]);  
            }  
      
            //after executing the code in the thread  
            @Override  
            protected void onPostExecute(Void result)  
            {  
                //close the progress dialog  
                progressDialog.dismiss();  
                //initialize the View  
                setContentView(R.layout.activity_image_dler);  
            }  
        }  
    
}
