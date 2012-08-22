package cs198.cis2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LaunchActivity extends Activity {
	private Button continueButton;
	private  EditText emailfield;
	private EditText passwordfield;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        continueButton =  (Button) findViewById(R.id.button1);
        emailfield = (EditText) findViewById(R.id.emailBox);
        passwordfield = ((EditText) findViewById(R.id.PasscodeBox));
        this.continueButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//String email = emailfield.getText().toString();
            	//String password = passwordfield.getText().toString(); //getPasswordfield().getText().toString();
            	Intent myIntent = new Intent(LaunchActivity.this, Passcode.class);
            	LaunchActivity.this.startActivity(myIntent);
            	LaunchActivity.this.finish();
            }
          });
        createLocalRecord();
    }
    
    private void createLocalRecord(){
    	
    }
    
}
