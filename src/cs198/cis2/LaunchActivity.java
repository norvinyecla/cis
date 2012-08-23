package cs198.cis2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
        createLocalRecord();
    }
    
    public void createLocalRecord(){
    	FileStats fileStats = new FileStats();
    	
    	datasource = new FileStatsDataSource(this);
        datasource.open();

        List<FileStats> values = datasource.getAllFileStats();

        
    	
    }
    
    class OnlyExt implements FilenameFilter{
	  String ext;   	 
	  public OnlyExt(String ext){
			 this.ext="." + ext;
	  }
	  
	  public boolean accept(File dir,String name){
		  return name.endsWith(ext);
	  }
	}
		
	public class FilterFiles{
		  
		public String[] main(String args[]) throws IOException{
		
		String dir = "\\mnt\\sdcard";
		String extn = "jpg";
		File f = new File(dir);
		FilenameFilter ff = new OnlyExt(extn);
		String s[] = f.list(ff);
		return s;
		}

  }

    
}
