package com.example.jordanhsu.instagramphotoviewer;

import android.content.Context;
import android.util.Log;

import com.ocpsoft.pretty.time.PrettyTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jordanhsu on 7/28/15.
 */
public class InstagramAPIUtil {


    public static final String IG_AUTH_TOKEN = "2107486180.369ca46.a58425c8557e44caa6e4d3d01c77c1ff";
    public static final String IG_API_PREFIX = "https://api.instagram.com/v1";
    public static final String INSTAGRAM_API_UTIL_DEV_TAG = "instagramAPIUtilDevTag";
    private Context context;

    public InstagramAPIUtil(Context context) {
        this.context = context;
    }

    public ArrayList<InstagramPostRow> processPopularInstagramData(JSONObject result) throws IOException, JSONException {
        ArrayList<InstagramPostRow> returnList = new ArrayList<InstagramPostRow>();
        // process API data
        Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "processPopularInstagramData");
//        JSONObject result = InstagramAPIClient("/media/popular");
        Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "result: " + result.toString());

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
                String relativeTimestamp = "";

                if (row.has("user")){
                    JSONObject userObj = row.getJSONObject("user");
                    username = userObj.has("username") ? userObj.getString("username") : "";
                    userProfilePhotoUrl = userObj.has("profile_picture") ? userObj.getString("profile_picture") : "";
                }
                Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "\n username: " + username +
                        "\n userProfilePhotoUrl: " + userProfilePhotoUrl);

                Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "\n has caption:" + row.has("caption"));

                if (row.has("caption")){
                    Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "\n has caption(in if):" + row.has("caption"));
                    Log.d(INSTAGRAM_API_UTIL_DEV_TAG, row.getJSONObject("caption").toString());
                    caption = row.getJSONObject("caption").has("text") ? row.getJSONObject("caption").getString("text") : "";
                }
                Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "\n caption:" + caption);

                if(row.has("likes")){
                    if(row.getJSONObject("likes").has("count")){
                        DecimalFormat myFormatter = new DecimalFormat("###,###");
                        likeCount = myFormatter.format(row.getJSONObject("likes").getInt("count")) + " " + context.getString(R.string.like_wording);

                    }
                }

                Log.d(INSTAGRAM_API_UTIL_DEV_TAG,"\n likeCount: " + likeCount);


                if(row.has("images")){
                    if(row.getJSONObject("images").has("standard_resolution")){
                        if(row.getJSONObject("images").getJSONObject("standard_resolution").has("url")){
                            mainPhotoUrl = row.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        }
                    }
                }

                Log.d(INSTAGRAM_API_UTIL_DEV_TAG, "\n mainPhotoUrl: " + mainPhotoUrl);

                if(row.has("comments")){
                    commentCount = row.getJSONObject("comments").has("count") ? row.getJSONObject("comments").getInt("count") : 0;
                }

                if (commentCount >= 2){
                    JSONArray commentArr = row.getJSONObject("comments").getJSONArray("data");
                    JSONObject comment1 = commentArr.getJSONObject(commentArr.length() - 2);
                    if (comment1.has("from")){
                        commentUserName1 = comment1.getJSONObject("from").has("username") ? comment1.getJSONObject("from").getString("username"): "";
                        commentProfilePhotoUrl1 = comment1.getJSONObject("from").has("profile_picture") ? comment1.getJSONObject("from").getString("profile_picture"): "";
                        commentContent1 = comment1.has("text") ? comment1.getString("text") : "";
                    }

                    JSONObject comment2 = commentArr.getJSONObject(commentArr.length() - 1);
                    if(comment2.has("from")){
                        commentUserName2 = comment2.getJSONObject("from").has("username") ? comment2.getJSONObject("from").getString("username") : "";
                        commentProfilePhotoUrl2 = comment2.getJSONObject("from").has("profile_picture") ? comment2.getJSONObject("from").getString("profile_picture") : "";
                        commentContent2 = comment2.has("text") ? comment2.getString("text") : "";
                    }
                }


                Log.d(INSTAGRAM_API_UTIL_DEV_TAG,
                        "\n commentContent1: " + commentContent1 +
                                "\n commentUserName2: " + commentUserName2 +
                                "\n commentProfilePhotoUrl2: " + commentProfilePhotoUrl2 +
                                "\n commentContent2: " + commentContent2 +
                                "\n---------------------------------------------------------");

                if(row.has("created_time")){
                    relativeTimestamp = new PrettyTime().format(new Date(row.getLong("created_time")*1000));
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
                ipRow.setRelativeTimestamp(relativeTimestamp);
                returnList.add(ipRow);
            }
            return returnList;
        }catch (JSONException e){
            Log.d(INSTAGRAM_API_UTIL_DEV_TAG, e.toString());
            Log.d(INSTAGRAM_API_UTIL_DEV_TAG, e.getMessage());
            return null;
        }
    }
}
