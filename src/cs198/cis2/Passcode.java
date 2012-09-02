package cs198.cis2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Passcode extends Activity {
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	private FileStatsDataSource datasource;
    FileStats f;
    String[] filenames = {};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode);
        final String PINCODE;
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        PINCODE = settings.getString("pin", null);
    	datasource = new FileStatsDataSource(this);
        datasource.open();
        
        FilterFiles myFilter = new FilterFiles();
        try {
			filenames = myFilter.filter();
			Toast.makeText(getApplicationContext(), "number of files="+filenames.length, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	

		}
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);        
        if (datasource.getAllFileStats().size() == 0){
	        int n = filenames.length;
	        int ctr = 0;
	        for (ctr = 0; ctr < n; ctr++){
	        	f = datasource.createFileStat(filenames[ctr]);
	        }
	        datasource.close();
        }
        
        
        Button q = (Button) findViewById(R.id.PasscodeButton);
        q.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	EditText mEdit = (EditText) findViewById(R.id.PasscodeBox);
            	
            	if (mEdit.getText().toString().equals(PINCODE) ){
            		Toast.makeText(Passcode.this, "You entered the correct code!", Toast.LENGTH_SHORT).show();
            		           		
	            	Intent myIntent = new Intent(Passcode.this, IdentifyActivity.class);
	            	//myIntent.putExtra("statnumber",fid);
	            	Passcode.this.startActivity(myIntent);
	            	Passcode.this.finish();
            	}
            	else {(Toast.makeText(Passcode.this, "You entered the wrong code!", Toast.LENGTH_SHORT)).show();
            		mEdit.setText("");
            	}
            }
          });
    }

    class OnlyExt implements FilenameFilter{
  	  String extn;   	 
  	  File dir;
  	  public OnlyExt(String extn){
  			 this.extn="." + extn;
  	  }
  	  
  	  public void setDir(File dir){
  		  this.dir = dir;		  
  	  }
  	  
  	  public boolean accept(File dir,String name){
  		  return name.endsWith(extn);
  	  }
  	}
  		
  	public class FilterFiles{
  		  
  		public String[] filter() throws IOException{
  		
  		String dir = Environment.getExternalStorageDirectory().toString();
  		String extn = "jpg";
  		File ff = new File(dir);
  		FilenameFilter fff = new OnlyExt(extn);
  		String s[] = ff.list(fff);
  		return s;
  		}

    }    
}
