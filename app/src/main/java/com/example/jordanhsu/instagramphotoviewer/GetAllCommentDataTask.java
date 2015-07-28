package com.example.jordanhsu.instagramphotoviewer;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jordanhsu on 7/29/15.
 */
public class GetAllCommentDataTask extends AsyncTask<Void, Void, JSONObject> {
    private static final String GET_ALL_COMMENT_DATA_TASK_DEV_TAG = "getAllCommentDataTaskDevTag";
    private String urlStr;
    private WeakReference<MainActivity> activityWeakReference;

    public GetAllCommentDataTask(String url, MainActivity mainActivity) {
        this.urlStr = url;
        this.activityWeakReference = new WeakReference<MainActivity>(mainActivity);
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        StringBuilder content = new StringBuilder();
        Log.d(GET_ALL_COMMENT_DATA_TASK_DEV_TAG, "data retriver task!!!!");
        try{

            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line);
            }
            bufferedReader.close();
        }catch (Exception e){
            Log.d(GET_ALL_COMMENT_DATA_TASK_DEV_TAG, e.toString());
        }

        // convert data into JSONObject and return
        try {
            Log.d(GET_ALL_COMMENT_DATA_TASK_DEV_TAG,content.toString());
            return new JSONObject(content.toString());
        } catch (JSONException e) {
            Log.d(GET_ALL_COMMENT_DATA_TASK_DEV_TAG, e.toString());

            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if ( activityWeakReference.get() != null){
            activityWeakReference.get().renderAllComment(result);
        }
    }
}
