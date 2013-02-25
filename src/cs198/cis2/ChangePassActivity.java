package cs198.cis2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassActivity extends Activity {
    Button goButton;
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
                EditText oBox = (EditText) findViewById(R.id.origPassBox);
                EditText nBox1 = (EditText) findViewById(R.id.newPassBox1);
                EditText nBox2 = (EditText) findViewById(R.id.newPassBox2);
                boolean isOrigCorrect = oBox.getText().toString().equals(PINCODE);
                boolean isNewMatches =  nBox1.getText().toString().equals(nBox2.getText().toString());
            	if (isOrigCorrect){ // if old password is correct
            		
            		if (isNewMatches){
            			// enter call PHP code
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
