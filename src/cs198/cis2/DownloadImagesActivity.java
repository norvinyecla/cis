package cs198.cis2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DownloadImagesActivity {
    static InputStream is;
    static JSONArray jArray;
    static String ipadd =  "http://192.168.60.76/CIIS/bin/android";
    public static String[] download() {
    	String parse = ipadd+"/update_imagedb.php";
    	try {
			URL url2 = new URL(parse);
	    	
	    	HttpClient httpclient1 = new DefaultHttpClient();
	        HttpPost httppost1 = new HttpPost(parse);
	        HttpResponse response1 = httpclient1.execute(httppost1); 
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    	
    	String filename[] = {""};
      	ArrayList<String> fileList = new ArrayList<String>();
        String result = "";

        try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(ipadd+"/get_image.php");
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost); 
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("log_tag", "connection success ");
                
        }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
                return filename;

        }
        //convert response to string
        try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        // ADD THIS
                        Log.i("log_tag","Line reads in DL: " + line);
                }
                is.close();
                result=sb.toString();
        }catch(Exception e){
               Log.e("log_tag", "Error converting result "+e.toString());
        }

        //parse json data
        
    		//paring data
            
            String flnm;
            ArrayList<String> l = new ArrayList<String>();
            try{
            jArray = new JSONArray(result);
            JSONObject json_data=null;

            for(int i=0;i<jArray.length();i++){
                    json_data = jArray.getJSONObject(i);
                    flnm=json_data.getString("filename");
                    l.add(flnm);
            }
            String[] stockArr = new String[l.size()];
            stockArr = l.toArray(stockArr);
            for(String s : stockArr)
                System.out.println(s);
			return stockArr;
            }catch(JSONException e1){
            	e1.printStackTrace();
            }catch (ParseException e1){
                e1.printStackTrace();
            }
			return filename;

        
     
		
     }
}	