package cs198.cis2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class FetchData {
    static InputStream is;
	public static final String PREFS_NAME = "MyApp_Settings";
	static SharedPreferences settings;
	Context mContext;

    public FetchData(Context context) {
        mContext = context;
        settings = PreferenceManager.getDefaultSharedPreferences(mContext);
    }
    public static String[] fetch(String username, String pin) {
        String ipadd = settings.getString("mainurl", "null");
        if (ipadd.length() == 0 || ipadd.equals("null") ){
        	ipadd =  "http://192.168.60.32/CIIS/bin/android";
        } 
        
        
        
        String phpfetch = ipadd+"/fetch_data.php";
    	String[] val = {"false", "false"};
        String result = "";
        //the user data to send
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("pin", pin));
        
        //http post
        try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(phpfetch);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost); 
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("log_tag", "connection success ");
                
        }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
                return val;

        }
        //convert response to string
        try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                     // ADD THIS
                        Log.i("log_tag","Line reads: " + line);
                }
                is.close();
                result=sb.toString();
        }catch(Exception e){
               Log.e("log_tag", "Error converting result "+e.toString());
               return val;

        }

        //parse json data
        try{
	        	
	            JSONArray jArray = new JSONArray(result);
                JSONObject json_data = jArray.getJSONObject(0);
                Log.i("log_tag","username: "+json_data.getString("username")+
                        ", pin: "+json_data.getInt("pin")
                );
                val[0] = json_data.getString("username");
                val[1] = Integer.toString(json_data.getInt("pin"));

        }catch(JSONException e){
        		Log.e("log_tag", "Error parsing data "+e.toString());
        		return val;
        }
        return val;
    }
}	