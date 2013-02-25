package cs198.cis2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import cs198.cis2.R;

public class Welcome extends Activity {
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	String userid;
	Intent myIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	//userid = settings.getString("userid", null);
    	
    	if ((settings.getString("userid", null) != null)){
    		myIntent = new Intent(Welcome.this, PasscodeActivity.class);
    		Welcome.this.startActivity(myIntent);
        	Welcome.this.finish();   
    		
    	}
    	else {
    		myIntent = new Intent(Welcome.this, LoginActivity.class);
    		Welcome.this.startActivity(myIntent);
        	Welcome.this.finish();   
    	}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
        return true;
    }

    
}
