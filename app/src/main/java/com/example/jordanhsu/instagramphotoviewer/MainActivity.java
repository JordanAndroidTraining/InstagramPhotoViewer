package com.example.jordanhsu.instagramphotoviewer;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    public static final String MAIN_ACTIVITY_LOG_DEV_TAG = "mainActivityLogDevTag";
    public static final String IG_AUTH_TOKEN = "2107486180.369ca46.a58425c8557e44caa6e4d3d01c77c1ff";
    public static final String IG_API_PREFIX = "https://api.instagram.com/v1/media";
    public static final String ALL_COMMENT_FRAGMENT_TAG = "all_comment_fragment_tag";
    private ArrayList<InstagramPostRow> IGPostRowList;
    private ArrayList<CommentRow> commentRowList;
    private InstagramPostRowAdapter IGPostRowAdapter;
    private ListView mainContainerListView;
    private SwipeRefreshLayout swipeContainer;
    private Activity self;
    private InstagramAPIUtil apiUtil;
    private AllCommentDialogFragment commentDialogFg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "onCreate");
        self = this;
        apiUtil = new InstagramAPIUtil(self);

        // init render
//        renderPopularPage();
        doGetInstagramPopularDataAsync();

        // swipe to refresh
        swipeContainer =  (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                renderPopularPage();
                doGetInstagramPopularDataAsync();
                swipeContainer.setRefreshing(false);
            }
        });
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

    public void doGetInstagramPopularDataAsync(){
        new GetInstagramPopularDataTask(IG_API_PREFIX + "/popular?access_token=" + IG_AUTH_TOKEN, (MainActivity) self).execute();
    }

    public void doGetAllCommentDataAsync(String postID){
        new GetAllCommentDataTask(IG_API_PREFIX + "/" + postID + "/comments?access_token=" + IG_AUTH_TOKEN, (MainActivity) self).execute();
    }


    public void renderPopularPage(JSONObject result){
        try {
            IGPostRowList = apiUtil.processPopularInstagramData(result);
            if(IGPostRowList != null){
                mainContainerListView = (ListView) findViewById(R.id.mainContainerListView);
                IGPostRowAdapter = new InstagramPostRowAdapter(self,0,IGPostRowList);
                mainContainerListView.setAdapter(IGPostRowAdapter);
            }
            else {
                Toast.makeText(self, getString(R.string.get_popular_data_failed_msg) , Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void renderAllComment(JSONObject result){
        try {
            commentRowList = apiUtil.processAllCommentInstagramData(result);
            if(commentRowList != null){
//                FragmentManager fm = getFragmentManager();
//                AllCommentDialogFragment editNameDialog = new AllCommentDialogFragment();;
//                editNameDialog.show(fm, "fragment_all_comment");
                commentDialogFg.refreshView(commentRowList);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loadMoreTextView:
                String postID = (String) v.getTag();
                doGetAllCommentDataAsync(postID);
                FragmentManager fm = getFragmentManager();
                commentDialogFg = new AllCommentDialogFragment();;
                commentDialogFg.show(fm, ALL_COMMENT_FRAGMENT_TAG);
                break;
        }
    }
}
