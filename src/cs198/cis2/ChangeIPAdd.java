package cs198.cis2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangeIPAdd extends Activity {
	Button helpbut;
    EditText mainurlbox;
    EditText imageurlbox;
	SharedPreferences settings;
	public static final String PREFS_NAME = "MyApp_Settings";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ipadd);
        mainurlbox = (EditText) findViewById(R.id.ipbox);
        imageurlbox = (EditText) findViewById(R.id.imageipbox);
        Button helpbut = (Button) findViewById(R.id.help);
        helpbut.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
            	// 1. Instantiate an AlertDialog.Builder with its constructor
            	AlertDialog.Builder builder = new AlertDialog.Builder(ChangeIPAdd.this);

            	// 2. Chain together various setter methods to set the dialog characteristics
            	builder.setMessage(R.string.helptext)
            	       .setTitle(R.string.help);

            	// 3. Get the AlertDialog from create()
            	AlertDialog dialog = builder.create();
            	dialog.show();
            }});
        
        Button savebut = (Button) findViewById(R.id.savemainurl);
        savebut.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
            	String mainurl = mainurlbox.getText().toString();
            	String imageurl = imageurlbox.getText().toString(); 
                settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            	Editor editor = settings.edit();
        	    editor.clear();
        		editor.putString("mainurl", mainurl);
        		editor.putString("imageurl", imageurl);
        		editor.commit(); 
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_change_ipadd, menu);
        return true;
    }
}
