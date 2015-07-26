package com.example.jordanhsu.instagramphotoviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    public static final String MAIN_ACTIVITY_LOG_DEV_TAG = "mainActivityLogDevTag";
    public static final String IG_AUTH_TOKEN = "2107486180.369ca46.a58425c8557e44caa6e4d3d01c77c1ff";
    public static final String IG_API_PREFIX = "https://api.instagram.com/v1";
    private ArrayList<InstagramPostRow> IGPostRowList;
    private InstagramPostRowAdapter IGPostRowAdapter;
    private ListView mainContainerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "onCreate");

        IGPostRowList = new ArrayList<InstagramPostRow>();
        mainContainerListView = (ListView) findViewById(R.id.mainContainerListView);

        try {
            renderPopularInstagramData();
            IGPostRowAdapter = new InstagramPostRowAdapter(this,0,IGPostRowList);
            mainContainerListView.setAdapter(IGPostRowAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void renderPopularInstagramData() throws IOException, JSONException {
        // get view


        // process API data
        Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "renderPopularInstagramData");
        JSONObject result = InstagramAPIClient("/media/popular");
        Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "result: " + result.toString());

        try {
            JSONArray allRowData = result.getJSONArray("data");
            for (int i = 0; i < allRowData.length(); i++){
                InstagramPostRow ipRow = new InstagramPostRow();
                JSONObject row = allRowData.getJSONObject(i);

                String username = "";
                String userProfilePhotoUrl = "";
                String caption = "";
                String likeCount = "";
                String mainPhotoUrl = "";
                String commentUserName1 = "";
                String commentProfilePhotoUrl1 = "";
                String commentContent1 = "";
                String commentUserName2 = "";
                String commentProfilePhotoUrl2 = "";
                String commentContent2 = "";
                int commentCount = 0;

                if (row.has("user")){
                    JSONObject userObj = row.getJSONObject("user");
                    username = userObj.has("username") ? userObj.getString("username") : "";
                    userProfilePhotoUrl = userObj.has("profile_picture") ? userObj.getString("profile_picture") : "";
                }

                if (row.has("caption")){
                    caption = row.getJSONObject("caption").has("text") ? row.getJSONObject("caption").getString("text") : "";
                }
                if(row.has("likes")){
                    if(row.getJSONObject("likes").has("count")){
                        DecimalFormat myFormatter = new DecimalFormat("###,###");
                        likeCount = myFormatter.format(row.getJSONObject("likes").getInt("count"));

                    }
                }

                if(row.has("images")){
                    if(row.getJSONObject("images").has("standard_resolution")){
                        if(row.getJSONObject("images").getJSONObject("standard_resolution").has("url")){
                            mainPhotoUrl = row.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        }
                    }
                }

                if(row.has("comments")){
                    commentCount = row.getJSONObject("comments").has("count") ? row.getJSONObject("comments").getInt("count") : 0;
                }

                if (commentCount >= 2){
                    JSONArray commentArr = row.getJSONObject("comments").getJSONArray("data");
                    JSONObject comment1 = commentArr.getJSONObject(0);
                    if (comment1.has("from")){
                        commentUserName1 = comment1.getJSONObject("from").has("username") ? comment1.getJSONObject("from").getString("username"): "";
                        commentProfilePhotoUrl1 = comment1.getJSONObject("from").has("profile_picture") ? comment1.getJSONObject("from").getString("profile_picture"): "";
                        commentContent1 = comment1.has("text") ? comment1.getString("text") : "";
                    }

                    JSONObject comment2 = commentArr.getJSONObject(1);
                    if(comment2.has("from")){
                        commentUserName2 = comment2.getJSONObject("from").has("username") ? comment2.getJSONObject("from").getString("username") : "";
                        commentProfilePhotoUrl2 = comment2.getJSONObject("from").has("profile_picture") ? comment2.getJSONObject("from").getString("profile_picture") : "";
                        commentContent2 = comment2.has("text") ? comment2.getString("text") : "";
                    }
                }
                ipRow.setUserName(username);
                ipRow.setUserProfilePhotoUrl(userProfilePhotoUrl);
                ipRow.setCaption(caption);
                ipRow.setLikeCount(likeCount);
                ipRow.setMainPhotoUrl(mainPhotoUrl);
                ipRow.setCommentUserName1(commentUserName1);
                ipRow.setCommentUserProfilePhotoUrl1(commentProfilePhotoUrl1);
                ipRow.setCommentContent1(commentContent1);
                ipRow.setCommentUserName2(commentUserName2);
                ipRow.setCommentUserProfilePhotoUrl2(commentProfilePhotoUrl2);
                ipRow.setCommentContent2(commentContent2);
                IGPostRowList.add(ipRow);

                Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "\n username: " + username +
                        "\n userProfilePhotoUrl: " + userProfilePhotoUrl +
                        "\n caption:" + caption +
                        "\n likeCount: " + likeCount +
                        "\n mainPhotoUrl: " + mainPhotoUrl +
                        "\n commentUserName1: " + commentUserName1 +
                        "\n commentProfilePhotoUrl1: " + commentProfilePhotoUrl1 +
                        "\n commentContent1: " + commentContent1 +
                        "\n commentUserName2: " + commentUserName2 +
                        "\n commentProfilePhotoUrl2: " + commentProfilePhotoUrl2 +
                        "\n commentContent2: " + commentContent2 +
                        "\n---------------------------------------------------------");
                if (i > 5)
                    break;
            }
        }catch (JSONException e){
            Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, e.toString());
            Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, e.getMessage());
        }
    }


    public JSONObject InstagramAPIClient(String apiRoute) {
        try {
            Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "process api: " + apiRoute);
            Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "auth token: " + String.valueOf(IG_AUTH_TOKEN));
            JSONObject result = new GetHttpJSONTask(IG_API_PREFIX + apiRoute + "?access_token=" + IG_AUTH_TOKEN).execute().get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
