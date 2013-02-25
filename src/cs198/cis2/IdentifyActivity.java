package cs198.cis2;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import cs198.cis2.R;

public class IdentifyActivity extends Activity {
	int statnumber, selected;
	FileStatsDataSource datasource;
	List<FileStats> uList;
	FileStats df;
	String userid, coraltype, conflvl, path;
	Button a;
	RadioGroup g;
	RadioButton b;
	Spinner c;
	File imgFile;
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
	Intent myIntent;
	String[] filenames;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify);
        
        
        datasource = new FileStatsDataSource(this);
        datasource.open();
        uList = datasource.getAllEmptyFileStats();
		
		if (uList.size() > 0){
	        df = uList.get(0);
        }
		else {
			datasource.close();
			Intent myIntent = new Intent(IdentifyActivity.this, CsvActivity.class);
        	IdentifyActivity.this.startActivity(myIntent);
        	IdentifyActivity.this.finish();
			
		}
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.confidence_lvl, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
        path = Environment.getExternalStorageDirectory()+ "/" + df.getFileName();
        imgFile = new File(path);
        if(imgFile.exists())
        {
           Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
           ImageView imageView=(ImageView)findViewById(R.id.imageView1);
           imageView.setImageBitmap(myBitmap);
        }
        
        
        a = (Button) findViewById(R.id.button1);
        a.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	// Gets a reference to our radio group
                // rBtnDigits is the name of our radio group (code not shown)
                g = (RadioGroup) findViewById(R.id.radioGroup1); 
                //Returns an integer which represents the selected radio button's ID
                selected = g.getCheckedRadioButtonId();
                // Gets a reference to our "selected" radio button
                // Now you can get the text or whatever you want from the "selected" radio button
                c = (Spinner) findViewById(R.id.spinner1);
            	b = (RadioButton) findViewById(selected);
            	
                settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            	userid = settings.getString("userid", null);
            	coraltype = b.getText().toString();
            	conflvl = c.getSelectedItem().toString();
            	
            	df.setUserId(userid);
            	df.setType(coraltype);
            	df.setConf(conflvl);
            	datasource.updateFileStat(df);	
            	
            	uList = datasource.getAllEmptyFileStats();
        		if (uList.size() == 0){
        	        myIntent = new Intent(IdentifyActivity.this, CsvActivity.class);
                	 			
        		}
        		else {
        			myIntent = new Intent(IdentifyActivity.this, IdentifyActivity.class);
	            	
        		}
        		datasource.close();
        		IdentifyActivity.this.startActivity(myIntent);
            	IdentifyActivity.this.finish();   
            }
            
          
          });
        
        ImageView imageView=(ImageView)findViewById(R.id.imageView1);
        imageView.setClickable(true);
        imageView.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        		i.setDataAndType(Uri.parse(imgFile.toURI().toString()),"image/jpg");
        		startActivity(i); 
        		
        	}
      	  
        });
        

        
    }

    
}

