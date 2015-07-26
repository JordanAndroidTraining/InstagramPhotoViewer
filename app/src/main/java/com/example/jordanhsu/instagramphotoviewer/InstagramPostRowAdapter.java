package com.example.jordanhsu.instagramphotoviewer;

import android.app.Activity;
import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_item,null);
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

        new ImageLoadingTask(IGPostRowList.get(position).getUserProfilePhotoUrl(),userProfilePhotoIv).execute();
        new ImageLoadingTask(IGPostRowList.get(position).getMainPhotoUrl(),mainPhotoIv).execute();
        new ImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl1(),commentProfilePhotoIv1).execute();
        new ImageLoadingTask(IGPostRowList.get(position).getCommentUserProfilePhotoUrl2(),commentProfilePhotoIv2).execute();
        return  view;
    }

//    public static Bitmap loadBitmap(String url) {
//        Bitmap bitmap = null;
//        InputStream in = null;
//        BufferedOutputStream out = null;
//
//        try {
//            in = new BufferedInputStream(new URL(url).openStream(), 1024);
//
//            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//            out = new BufferedOutputStream(dataStream, 1024);
//            copy(in, out);
//            out.flush();
//
//            final byte[] data = dataStream.toByteArray();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            //options.inSampleSize = 1;
//
//            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
//        } catch (IOException e) {
//            Log.e(IG_POST_ROW_ADAPTER_DEV_TAG, "Could not load Bitmap from: " + url);
//        }
//        return bitmap;
//    }

}
