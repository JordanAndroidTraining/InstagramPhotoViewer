package com.example.jordanhsu.instagramphotoviewer;

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
 * Created by jordanhsu on 7/29/15.
 */
public class AllCommentFragmentAdapter  extends ArrayAdapter<CommentRow> {
    public static final String IG_ALL_COMMENT_FRAGMENT_ADAPTER_DEV_TAG = "igAllCommentFragmentAdapterDevTag";
    private Context context;
    private ArrayList<CommentRow> commentRowList;

    public AllCommentFragmentAdapter(Context context, int resource,  ArrayList<CommentRow> commentRowList) {
        super(context, resource, commentRowList);
        this.context = context;
        this.commentRowList = commentRowList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            Log.d(IG_ALL_COMMENT_FRAGMENT_ADAPTER_DEV_TAG, "Convert View is null");
            view = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        }

        // find view reference
        ImageView commentUserProfileIv = (ImageView) view.findViewById(R.id.commentUserProfilePhotoIv);
        TextView commentUserNameTv = (TextView) view.findViewById(R.id.commentUserNameTv);
        TextView commentContentTv = (TextView) view.findViewById(R.id.commentContentTv);

        Log.d(IG_ALL_COMMENT_FRAGMENT_ADAPTER_DEV_TAG,"commentUserProfileIv: " + commentUserProfileIv);
        Log.d(IG_ALL_COMMENT_FRAGMENT_ADAPTER_DEV_TAG,"commentUserNameTv: " + commentUserNameTv);
        Log.d(IG_ALL_COMMENT_FRAGMENT_ADAPTER_DEV_TAG,"commentContentTv: " + commentContentTv);

        // set view content
        doAsyncImageLoadingTask(commentRowList.get(position).getCommentUserProfileUrl(), commentUserProfileIv);
        commentUserNameTv.setText(commentRowList.get(position).getCommentUserName());
        commentContentTv.setText(commentRowList.get(position).getCommentContent());
        return view;
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
