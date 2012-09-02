package cs198.cis2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CsvActivity extends Activity {
	FileStatsDataSource datasource;
	List<FileStats> cList;
	FileStats df;
	Button upload_Button;
	int ctr = 0;
	File f;
	FileWriter fstream;
    BufferedWriter out ;
    private static final String DATABASE_NAME = "filestats.db";
    public static final String COLUMN_USERID = "userid";
	public static final String COLUMN_FILENAME = "filename";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_CONF = "conf";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.csv);
        upload_Button =  (Button) findViewById(R.id.uploadButton);
        datasource = new FileStatsDataSource(this);
        datasource.open();
        cList = datasource.getAllFileStats();
        this.upload_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//String email = emailfield.getText().toString();
            	// login once 

            	getApplicationContext().deleteDatabase(DATABASE_NAME);
            	//b.delete();
            	//Intent myIntent = new Intent(CsvActivity.this, .class);
            	//CsvActivity.this.startActivity(myIntent);
            	CsvActivity.this.finish();
            }
          });
        
        f=new File(Environment.getExternalStorageDirectory()+"/labels.csv");
        if(!f.exists()){
	        try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
        	f.delete();
        }
        
        try{
            // Create file 
            fstream = new FileWriter(Environment.getExternalStorageDirectory()+"/labels.csv", false);
            }catch (Exception e){//Catch exception if any
              System.err.println("Error: " + e.getMessage());
            }
//        
        for (ctr = 0; ctr < cList.size(); ctr++){
        	FileStats f = cList.get(ctr);
        	String line = f.getUserId()+","+f.getFileName()+","+f.getType()+","+f.getConf();
        	try {
        	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory()+"/labels.csv", true)));
        	    out.println(line);
        	    out.close();
        	} catch (IOException e) {
        	    //oh noes!
        	}
//        	String path = Environment.getExternalStorageDirectory()+ "/" + f.getFileName();
//        	File a = new File(path);
//        	a.delete();
        }
        
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.csv, menu);
        return true;
    }
}
