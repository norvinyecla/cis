package cs198.cis2;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cs198.cis2.R;

public class LoginActivity extends Activity {
		
	private Button continueButton;
	private  EditText emailfield;
	private EditText passwordfield;
    
	
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	String[] a;
	String userid;
	URL url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
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
            	FetchData f = new FetchData(LoginActivity.this);
            	a = f.fetch(email, password);
            	Editor editor = settings.edit();
            	Log.i("norvin3", a[0]);
            	if (new String(a[0]).equals("false")){
            		editor.clear();
            		Toast.makeText(getApplicationContext(), "You entered a wrong combination. Please try again", Toast.LENGTH_SHORT).show();
            		Toast.makeText(getApplicationContext(), "name: "+email+"    password: "+password, Toast.LENGTH_SHORT).show();
            		emailfield.setText("");
            		passwordfield.setText("");
            		emailfield.requestFocus();
            	}
            	else {
            		
            	    editor.clear();
            		editor.putString("userid", a[0]);
            		editor.putString("pin", a[1]);
            		editor.commit();           		
            		Intent myIntent = new Intent(LoginActivity.this, OptionsActivity.class);
	            	LoginActivity.this.startActivity(myIntent);
	            	LoginActivity.this.finish();
            	}
            }
          });
         
       }
    }
        
    
    
    
    
   

