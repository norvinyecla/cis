package cs198.cis2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Passcode extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode);
        final String PINCODE = "1000";
        Button q = (Button) findViewById(R.id.PasscodeButton);
        q.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	EditText mEdit = (EditText) findViewById(R.id.PasscodeBox);
            	Toast.makeText(Passcode.this, mEdit.getText().toString(), Toast.LENGTH_SHORT).show();
            	if (mEdit.getText().toString().equals(PINCODE) ){
            		Toast.makeText(Passcode.this, "You entered the correct code!", Toast.LENGTH_SHORT).show();
	            	Intent myIntent = new Intent(Passcode.this, IdentifyActivity.class);
	            	Passcode.this.startActivity(myIntent);
	            	Passcode.this.finish();
            	}
            	else {(Toast.makeText(Passcode.this, "You entered the wrong code!", Toast.LENGTH_SHORT)).show();
            		mEdit.setText("");
            	}
            }
          });
    }

    
}
