package com.cs411.packman;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

class RequestTask extends AsyncTask<String, String, String>{
	
	private final static String URL = "http://teambazinga.web.engr.illinois.edu/php/";
	private final static String REQUEST_SERVLET = "request.php";
	private final static String INDEX_SERVLET = "index.php";

    @Override
    protected String doInBackground(String... uri) {
    	// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
        String responseString = null;
        String sessionID = getSessionID();
	    
        try {
        	HttpResponse response = httpclient.execute(new HttpGet(uri[0] + "&pm_session_id=" + sessionID.replace("\n","").replace("\"", "")));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
    
    /**
     * TODO: This could be a bit smarter in that it could cache it's session id and only make a request when necessary.
     */
    private String getSessionID() {
        try {
        	// Create a new HttpClient and Post Header
        	HttpClient httpclient = new DefaultHttpClient();
        	HttpResponse response = httpclient.execute(new HttpGet(URL + REQUEST_SERVLET + "?requestName=getSessionID&username=" + MainActivity.getUserName() + "&password=" + MainActivity.getPassword()));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                return out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e) {
            //TODO Handle problems..
        }
        
        return null;
    }
    
    public JSONArray getPackages() throws Exception{
    	AsyncTask<String, String, String> response = execute(URL + REQUEST_SERVLET + "?requestName=getPackages");
		String responseString = response.get().replace("\n", "");
		return new JSONArray(responseString);
    }
}