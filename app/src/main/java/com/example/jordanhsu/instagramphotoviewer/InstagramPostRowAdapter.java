package com.example.jordanhsu.instagramphotoviewer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jordanhsu on 7/26/15.
 */
public class InstagramPostRowAdapter extends ArrayAdapter<InstagramPostRow> {

    public static final String IG_POST_ROW_ADAPTER_DEV_TAG = "IGPostRowAdapterDevTag";
    Context context;
    ArrayList<InstagramPostRow> IGPostRowList;
    public InstagramPostRowAdapter(Context context, int resource,  ArrayList<InstagramPostRow> IGPostRowList) {
        super(context, resource, IGPostRowList);
        this.context = context;
        this.IGPostRowList = IGPostRowList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            Log.d(IG_POST_ROW_ADAPTER_DEV_TAG, "Convert View is null");
            view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        }

        TextView usernameTv = (TextView) view.findViewById(R.id.userNameTextView);
        TextView captionTv = (TextView) view.findViewById(R.id.captionTextView);
        TextView likeCountTv = (TextView) view.findViewById(R.id.likeCountTextView);
        ImageView userProfilePhotoIv = (ImageView) view.findViewById(R.id.profilePhotoImageView);
        ImageView mainPhotoIv = (ImageView) view.findViewById(R.id.photoContainerImageView);
        TextView commentUserNameTv1 = (TextView) view.findViewById(R.id.commentUserNameTextView1);
        TextView commentContentTv1 = (TextView) view.findViewById(R.id.commentContentTextView1);
        ImageView commentProfilePhotoIv1 = (ImageView) view.findViewById(R.id.commentProfileImageView1);
        TextView commentUserNameTv2 = (TextView) view.findViewById(R.id.commentUserNameTextView2);
        TextView commentContentTv2 = (TextView) view.findViewById(R.id.commentContentTextView2);
        ImageView commentProfilePhotoIv2 = (ImageView) view.findViewById(R.id.commentProfileImageView2);
        TextView relativeTimestampTv = (TextView) view.findViewById(R.id.relativeTimestampTextView);


        usernameTv.setText(IGPostRowList.get(position).getUserName());
        captionTv.setText(IGPostRowList.get(position).getCaption());
        likeCountTv.setText(IGPostRowList.get(position).getLikeCount());
        commentUserNameTv1.setText(IGPostRowList.get(position).getCommentUserName1());
        commentContentTv1.setText(IGPostRowList.get(position).getCommentContent1());
        commentUserNameTv2.setText(IGPostRowList.get(position).getCommentUserName2());
        commentContentTv2.setText(IGPostRowList.get(position).getCommentContent2());
        relativeTimestampTv.setText(IGPostRowList.get(position).getRelativeTimestamp());

        doAsyncImageLoadingTask(IGPostRowList.get(position).getUserProfilePhotoUrl(),userProfilePhotoIv);
        doAsyncImageLoadingTask(IGPostRowList.get(position).getMainPhotoUrl(),mainPhotoIv);
        doAsyncImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl1(),commentProfilePhotoIv1);
        doAsyncImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl2(),commentProfilePhotoIv2);
        return  view;
    }

    public void doAsyncImageLoadingTask(String url, ImageView iv){
        // clear image resource
        iv.setImageResource(0);

        // cancel previous task
        ImageLoadingTask prevTask = (ImageLoadingTask) iv.getTag();
        if(prevTask != null) {
            prevTask.cancel(true);
        }

        // create new task && set to ImageView tag
        ImageLoadingTask newTask = new ImageLoadingTask(url,iv);
        iv.setTag(newTask);

        // execute asyncTask
        newTask.execute();
    }
}
