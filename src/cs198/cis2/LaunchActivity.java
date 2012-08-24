package cs198.cis2;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LaunchActivity extends Activity {
	private Button continueButton;
	private  EditText emailfield;
	private EditText passwordfield;
	private FileStatsDataSource datasource;
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
        String[] filenames = {};

    	datasource = new FileStatsDataSource(this);
        datasource.open();

        FilterFiles myFilter = new FilterFiles();
        try {
			filenames = myFilter.filter();
			Toast.makeText(getApplicationContext(), "number of files ="+filenames.length, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        
        int n = filenames.length;
        int ctr = 0;
        for (ctr = 0; ctr < n; ctr++){
        	datasource.createFileStat(filenames[ctr]);
        }
        datasource.close();
        
        
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
		File f = new File(dir);
		FilenameFilter ff = new OnlyExt(extn);
		String s[] = f.list(ff);
		return s;
		}

  }

    
}
