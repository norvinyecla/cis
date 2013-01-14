package cs198.cis2;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cs198.cis2.CsvActivity.Uploader;

public class PasscodeActivity extends Activity {
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	FileStatsDataSource datasource;
	List<FileStats> cList;
    FileStats f;
    String[] filenames = {};

    String PINCODE;
    String USERNAME;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        datasource = new FileStatsDataSource(this);
        datasource.open();
        USERNAME = settings.getString("userid", null);
        PINCODE = settings.getString("pin", null);
    	        TextView r = (TextView) findViewById(R.id.SecondLabel);
        r.setText("Hello, "+USERNAME+"!");
        Toast.makeText(this, settings.toString(), Toast.LENGTH_LONG).show();
        Button q = (Button) findViewById(R.id.PasscodeButton);
        q.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	EditText mEdit = (EditText) findViewById(R.id.PasscodeBox);
            	
            	if (mEdit.getText().toString().equals(PINCODE) ){
            		Toast.makeText(PasscodeActivity.this, "You entered the correct code!", Toast.LENGTH_SHORT).show();
            		datasource.close();           		
	            	Intent myIntent = new Intent(PasscodeActivity.this, ImageDLerActivity.class);
	            	//myIntent.putExtra("statnumber",fid);
	            	PasscodeActivity.this.startActivity(myIntent);
	            	PasscodeActivity.this.finish();
            	}
            	else {(Toast.makeText(PasscodeActivity.this, "You entered the wrong code!", Toast.LENGTH_SHORT)).show();
            		mEdit.setText("");
            	}
            }
          });
        
        Button t = (Button) findViewById(R.id.NotMeButton);
        t.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                    Editor editor = settings.edit();
                    editor.clear();
                    editor.commit();
                    int ctr;
                    cList = datasource.getAllFileStats();
                    for (ctr = 0; ctr < cList.size(); ctr++){
                    	FileStats f = cList.get(ctr);
                    	String path = Environment.getExternalStorageDirectory()+ "/" + f.getFileName();
                    	datasource.deleteFileStat(f);
                    	File a = new File(path);
                    	a.delete();
                    }
                    datasource.close();
	            	Intent myIntent = new Intent(PasscodeActivity.this, LoginActivity.class);
	            	//myIntent.putExtra("statnumber",fid);
	            	PasscodeActivity.this.startActivity(myIntent);
	            	PasscodeActivity.this.finish();
            	}
            	
          });
    }

        
}
