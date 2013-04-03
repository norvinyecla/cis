package cs198.cis2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
    //static String ipadd = "http://cis.p.ht/CS198/androidbackend"; // hotstinger online
    static String ipadd =  "http://192.168.60.22/CIIS/bin/android";
    public static String[] download() {
    	String filename[] = {""};
      	ArrayList<String> fileList = new ArrayList<String>();
        String result = "";
        
        //the user data to send
        //ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //nameValuePairs.add(new BasicNameValuePair("url", url));
        //nameValuePairs.add(new BasicNameValuePair("password", password));

        //http post
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