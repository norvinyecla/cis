package cs198.cis2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LaunchActivity extends Activity {
	private Button continueButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        continueButton =  (Button) findViewById(R.id.button1);
        this.continueButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(LaunchActivity.this, Passcode.class);
            	LaunchActivity.this.startActivity(myIntent);
            }
          });
    }

    
}
