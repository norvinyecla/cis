package cs198.cis2;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class IdentifyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify);
        
        Button a = (Button) findViewById(R.id.button1);
        a.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(IdentifyActivity.this, IdentifyActivity.class);
            	IdentifyActivity.this.startActivity(myIntent);
            	IdentifyActivity.this.finish();
            }
          });
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
             R.array.confidence_lvl, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        
        // String path = Environment.getExternalStorageDirectory()+ "/1.jpg";
        String path = "/mnt/sdcard/1.jpg";
        File imgFile = new File(path);
        if(imgFile.exists())
        {
           Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
           ImageView imageView=(ImageView)findViewById(R.id.imageView1);
           imageView.setImageBitmap(myBitmap);
        }

        
    }

    
}

