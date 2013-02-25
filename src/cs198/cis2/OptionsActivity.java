package cs198.cis2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class OptionsActivity extends Activity {
	Button startButton;
    Button changePassButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        
        startButton = (Button) findViewById(R.id.startButton);
        changePassButton = (Button) findViewById(R.id.changePassButton);
        
        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(OptionsActivity.this, ImageDLerActivity.class);
            	OptionsActivity.this.startActivity(myIntent);
            	OptionsActivity.this.finish();
            }
          });
        
        changePassButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(OptionsActivity.this, ChangePassActivity.class);
            	OptionsActivity.this.startActivity(myIntent);
            	OptionsActivity.this.finish();
            }
          });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_options, menu);
        return true;
    }
    
    

    
}
