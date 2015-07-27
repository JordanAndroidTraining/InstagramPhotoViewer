package com.example.jordanhsu.instagramphotoviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ocpsoft.pretty.time.PrettyTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    public static final String MAIN_ACTIVITY_LOG_DEV_TAG = "mainActivityLogDevTag";
    public static final String IG_AUTH_TOKEN = "2107486180.369ca46.a58425c8557e44caa6e4d3d01c77c1ff";
    public static final String IG_API_PREFIX = "https://api.instagram.com/v1";
    private ArrayList<InstagramPostRow> IGPostRowList;
    private InstagramPostRowAdapter IGPostRowAdapter;
    private ListView mainContainerListView;
    private SwipeRefreshLayout swipeContainer;
    private Activity self;
    private InstagramAPIUtil apiUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MAIN_ACTIVITY_LOG_DEV_TAG, "onCreate");
        self = this;
        apiUtil = new InstagramAPIUtil(self);

        // init render
        renderPopularPage();

        // swipe to refresh
        swipeContainer =  (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                renderPopularPage();
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

    public void renderPopularPage(){
        try {
            IGPostRowList = apiUtil.getPopularInstagramData();
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
}
