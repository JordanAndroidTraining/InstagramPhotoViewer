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

        usernameTv.setText(IGPostRowList.get(position).getUserName());
        captionTv.setText(IGPostRowList.get(position).getCaption());
        likeCountTv.setText(IGPostRowList.get(position).getLikeCount());
        commentUserNameTv1.setText(IGPostRowList.get(position).getCommentUserName1());
        commentContentTv1.setText(IGPostRowList.get(position).getCommentContent1());
        commentUserNameTv2.setText(IGPostRowList.get(position).getCommentUserName2());
        commentContentTv2.setText(IGPostRowList.get(position).getCommentContent2());

        mainPhotoIv.setImageResource(0);
        userProfilePhotoIv.setImageResource(0);
        commentProfilePhotoIv1.setImageResource(0);
        commentProfilePhotoIv2.setImageResource(0);

        // retrieve previous task and stop it
        ImageLoadingTask prevUserProfileTask = (ImageLoadingTask) userProfilePhotoIv.getTag();
        if(prevUserProfileTask != null) {
            prevUserProfileTask.cancel(true);
        }

        ImageLoadingTask prevMainPhotoTask = (ImageLoadingTask) mainPhotoIv.getTag();
        if(prevMainPhotoTask != null){
            prevMainPhotoTask.cancel(true);
        }

        ImageLoadingTask prevCommentProfilePhotoTask1 = (ImageLoadingTask) commentProfilePhotoIv1.getTag();
        if(prevCommentProfilePhotoTask1 != null){
            prevCommentProfilePhotoTask1.cancel(true);
        }

        ImageLoadingTask prevCommentProfilePhotoTask2 = (ImageLoadingTask) commentProfilePhotoIv2.getTag();
        if(prevCommentProfilePhotoTask2 != null){
            prevCommentProfilePhotoTask2.cancel(true);
        }

        // create new task
        ImageLoadingTask userProfilePhotoTask =  new ImageLoadingTask(IGPostRowList.get(position).getUserProfilePhotoUrl(),userProfilePhotoIv);
        ImageLoadingTask mainPhotoTask = new ImageLoadingTask(IGPostRowList.get(position).getMainPhotoUrl(),mainPhotoIv);
        ImageLoadingTask commentProfielPhotoTask1 = new ImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl1(),commentProfilePhotoIv1);
        ImageLoadingTask commentProfielPhotoTask2 = new ImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl2(),commentProfilePhotoIv2);

        userProfilePhotoIv.setTag(userProfilePhotoTask);
        mainPhotoIv.setTag(mainPhotoTask);
        commentProfilePhotoIv1.setTag(commentProfielPhotoTask1);
        commentProfilePhotoIv2.setTag(commentProfielPhotoTask2);

        // execute asyncTask
        userProfilePhotoTask.execute();
        mainPhotoTask.execute();
        commentProfielPhotoTask1.execute();
        commentProfielPhotoTask2.execute();
        return  view;
    }
}
