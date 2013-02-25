package cs198.cis2;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
	public static final String PREFS_NAME = "MyApp_Settings";
	SharedPreferences settings;
    //static String ipadd =  "10.40.93.103"; // wifi CVMIG
    //static String ipadd =  "192.168.60.49"; // wifi ComSci
    //static String ipadd = "http://192.168.32.1/CS198/androidbackend"; // usb
    //static String ipadd = "10.0.2.2/CS198/androidbackend"; // emulator
    static String ipadd = "http://cis.p.ht/CS198/androidbackend"; // hotstinger online  
    //..static String ipadd = "http://192.168.0.114/CS198/androidbackend"; // usb
    
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
        datasource.close();
        this.upload_Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//String email = emailfield.getText().toString();
            	// login once 

            	getApplicationContext().deleteDatabase(DATABASE_NAME);
            	//b.delete();
            	//Intent myIntent = new Intent(CsvActivity.this, .class);
            	//CsvActivity.this.startActivity(myIntent);
            	Uploader u = new Uploader();
            	u.upload();
                datasource.close();
        		settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE); 
                Editor editor = settings.edit();
                editor.putString("done", "true");
                editor.commit();
            	CsvActivity.this.finish();
            }
          });
        
        f=new File(Environment.getExternalStorageDirectory(), "labels.csv");
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
        	
        	String path = Environment.getExternalStorageDirectory()+ "/" + f.getFileName();
        	File a = new File(path);
        	a.delete();
        	
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.csv, menu);
        return true;
    }
    
    public class Uploader{
    	HttpURLConnection connection ;
    	DataOutputStream outputStream ;
    	DataInputStream inputStream ;

    	String pathToOurFile;
    	String urlServer;
    	String lineEnd;
    	String twoHyphens;
    	String boundary;

    	int bytesRead, bytesAvailable, bufferSize;
    	byte[] buffer;
    	int maxBufferSize = 1*1024*1024;
    	
    	public Uploader(){
    		connection = null;
        	outputStream = null;
        	inputStream = null;
        	
        	pathToOurFile = (new File(Environment.getExternalStorageDirectory(), "labels.csv")).getAbsolutePath();
        	urlServer = ipadd+"/upload_csv.php";
        	lineEnd = "\r\n";
        	twoHyphens = "--";
        	boundary =  "*****";

        	maxBufferSize = 1*1024*1024;
    	}
    	
    	
    	public void upload(){
    	try
    	{
    	FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

    	URL url = new URL(urlServer);
    	connection = (HttpURLConnection) url.openConnection();

    	// Allow Inputs & Outputs
    	connection.setDoInput(true);
    	connection.setDoOutput(true);
    	connection.setUseCaches(false);

    	// Enable POST method
    	connection.setRequestMethod("POST");

    	connection.setRequestProperty("Connection", "Keep-Alive");
    	connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

    	outputStream = new DataOutputStream( connection.getOutputStream() );
    	outputStream.writeBytes(twoHyphens + boundary + lineEnd);
    	outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
    	outputStream.writeBytes(lineEnd);

    	bytesAvailable = fileInputStream.available();
    	bufferSize = Math.min(bytesAvailable, maxBufferSize);
    	buffer = new byte[bufferSize];

    	// Read file
    	bytesRead = fileInputStream.read(buffer, 0, bufferSize);

    	while (bytesRead > 0)
    	{
	    	outputStream.write(buffer, 0, bufferSize);
	    	bytesAvailable = fileInputStream.available();
	    	bufferSize = Math.min(bytesAvailable, maxBufferSize);
	    	bytesRead = fileInputStream.read(buffer, 0, bufferSize);
    	}

    	outputStream.writeBytes(lineEnd);
    	outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

    	// Responses from the server (code and message)
    	int serverResponseCode = connection.getResponseCode();
    	String serverResponseMessage = connection.getResponseMessage();

    	fileInputStream.close();
    	outputStream.flush();
    	outputStream.close();
    	
    	String parse = ipadd+"/parse_csv.php";
    	URL url2 = new URL(parse);
    	
//    	connection.setDoInput(true);
//    	connection.setDoOutput(true);
//    	connection = (HttpURLConnection) url2.openConnection();
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(parse);
        HttpResponse response = httpclient.execute(httppost); 
    	Log.i("norvin4", "done");
    	}
    	catch (Exception ex)
    	{
    	//Exception handling
    	}
    	
      }
    }
}

    	
    