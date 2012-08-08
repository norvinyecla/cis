package cs198.cis2;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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

