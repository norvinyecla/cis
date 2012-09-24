package cs198.cis2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LaunchActivity extends Activity {
		
	private Button continueButton;
	private  EditText emailfield;
	private EditText passwordfield;
    static String ipadd =  "192.168.60.49"; // wifi ComSci
    // static String ipadd = "192.168.32.1"; // usb
    // static String ipadd = "10.0.2.2"; // emulator 
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	String[] a;
	String userid;
	URL url;
	ByteArrayBuffer baf;
	InputStream is;
	BufferedInputStream bis = new BufferedInputStream(is);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        continueButton =  (Button) findViewById(R.id.FirstButton);
        emailfield = (EditText) findViewById(R.id.emailBox);
        passwordfield = ((EditText) findViewById(R.id.passwordBox));
        emailfield.setText("Username");
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	this.emailfield.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	emailfield.setText("");
            }});
        this.continueButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String email = emailfield.getText().toString();
            	String password = passwordfield.getText().toString(); 
            	a = FetchData.fetch(email, password);
            	Log.i("log_tag", a[0]);
            	if (new String(a[0]).equals("false")){
            		Toast.makeText(getApplicationContext(), "You entered a wrong combination. Please try again", Toast.LENGTH_SHORT).show();
            		Toast.makeText(getApplicationContext(), "name: "+email+"    password: "+password, Toast.LENGTH_SHORT).show();
            		emailfield.setText("");
            		passwordfield.setText("");
            		emailfield.requestFocus();
            	}
            	else {
            		Editor editor = settings.edit();
            		editor.putString("userid", a[0]);
            		editor.putString("password", a[1]);
            		editor.putString("pin", a[2]);
            		editor.commit();
            		
            		String[] d = DownloadImages.download();
                    int i;
                    for (i = 0; i < d.length; i++){
            	        URL url = null;
            			try {
            				url = new URL ("http://"+ipadd+"/CS198/androidbackend/images/"+d[i]);

            			} catch (MalformedURLException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            				Log.i("norvin", "nandito sa url ang error");
            			}
            			
            			try {
            				//input = url.openStream();
            				/* Open a connection to that URL. */
            		           URLConnection ucon = url.openConnection();

            		           /*
            		            * Define InputStreams to read from the URLConnection.
            		            */
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

            	        } catch (IOException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			} 
            	     
            	    }
            	    
            		Intent myIntent = new Intent(LaunchActivity.this, Passcode.class);
	            	LaunchActivity.this.startActivity(myIntent);
	            	LaunchActivity.this.finish();
            	}
            }
          });
         
       }
    }
        
    
    
    
    
   

