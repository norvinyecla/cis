package cs198.cis2;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassActivity extends Activity {
    Button goButton;
    static String ipadd = "http://cis.p.ht/CS198/androidbackend"; // usb
    static String phpfetch = ipadd+"/change_password.php";
    
    InputStream is;
    public static final String PREFS_NAME = "MyApp_Settings";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        
        goButton = (Button) findViewById(R.id.goButton);
        
        
        goButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            	String PINCODE = settings.getString("pin", null);
            	String USERNAME = settings.getString("userid", null);
                EditText oBox = (EditText) findViewById(R.id.origPassBox);
                EditText nBox1 = (EditText) findViewById(R.id.newPassBox1);
                EditText nBox2 = (EditText) findViewById(R.id.newPassBox2);
                boolean isOrigCorrect = oBox.getText().toString().equals(PINCODE);
                boolean isNewMatches =  nBox1.getText().toString().equals(nBox2.getText().toString());
            	if (isOrigCorrect){ // if old password is correct
            		
            		if (isNewMatches){
            			// enter call PHP code
            			
            	        //the user data to send
            	        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            	        nameValuePairs.add(new BasicNameValuePair("username", USERNAME));
            	        nameValuePairs.add(new BasicNameValuePair("oldpassword", PINCODE));
            	        nameValuePairs.add(new BasicNameValuePair("newpassword",nBox1.getText().toString()));
      
            	        //http post
            	        try{
            	                HttpClient httpclient = new DefaultHttpClient();
            	                HttpPost httppost = new HttpPost(phpfetch);
            	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            	                HttpResponse response = httpclient.execute(httppost); 
            	                HttpEntity entity = response.getEntity();
            	                is = entity.getContent();
            	                Log.e("log_tag", "connection success ");
            	                
            	        }catch(Exception e){
            	                Log.e("log_tag", "Error in http connection "+e.toString());

            	        }		
            			// new activity
            		    Intent myIntent = new Intent(ChangePassActivity.this, PasscodeActivity.class);
                	    ChangePassActivity.this.startActivity(myIntent);
                	    ChangePassActivity.this.finish();
            		}
            		else {
            			oBox.setText("");
                        nBox1.setText("");
                        nBox2.setText("");
                        Toast.makeText(ChangePassActivity.this, "The new passwords don't match.", Toast.LENGTH_LONG).show();
            		}
            	}
            	else {
                    oBox.setText("");
                    nBox1.setText("");
                    nBox2.setText("");
                    Toast.makeText(ChangePassActivity.this, "You entered a wrong password.", Toast.LENGTH_LONG).show();
            	}
            }
          });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_change_pass, menu);
        return true;
    }

    
}
