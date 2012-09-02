package cs198.cis2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
	private FileStatsDataSource datasource;
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	String[] a;
	String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        continueButton =  (Button) findViewById(R.id.FirstButton);
        emailfield = (EditText) findViewById(R.id.emailBox);
        passwordfield = ((EditText) findViewById(R.id.passwordBox));
        
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	
        this.continueButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	String email = emailfield.getText().toString();
            	String password = passwordfield.getText().toString(); 
            	a = FetchData.fetch(email, password);
            	if (new String(a[0]).equals("false")){
            		Toast.makeText(getApplicationContext(), "You entered a wrong combination. Please try again", Toast.LENGTH_SHORT).show();
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
            		Intent myIntent = new Intent(LaunchActivity.this, Passcode.class);
	            	LaunchActivity.this.startActivity(myIntent);
	            	LaunchActivity.this.finish();
            	}
            }
          });

        
        
    }
    
    
    
   
}
