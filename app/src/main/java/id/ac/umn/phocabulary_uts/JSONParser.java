package id.ac.umn.phocabulary_uts;

import android.widget.Toast;

/**
 * Created by Nathania on 11/05/2017.
 */

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) throws ConnectException {

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST")) {
            System.out.println("NIAO");
            // request method is POST
            try {
                urlObj = new URL(url);
                System.out.println(url);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();

                paramsString = sbParams.toString();
                System.out.println(paramsString);
                //wr = new DataOutputStream(conn.getOutputStream());
                conn.getOutputStream().write(paramsString.getBytes());
                BufferedReader reader=new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.d("JSON Parser", "JSON result: " + result.toString());


            }
            catch (ConnectException e){
                throw new ConnectException("Please connect to Wifi");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                conn.disconnect();
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        // try parse the string to a JSON object
        try {
            System.out.println(result.toString());
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}