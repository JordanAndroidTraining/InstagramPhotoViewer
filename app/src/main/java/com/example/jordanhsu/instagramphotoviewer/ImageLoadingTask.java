package com.example.jordanhsu.instagramphotoviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jordanhsu on 7/26/15.
 */
public class ImageLoadingTask extends AsyncTask<Void,Void,Bitmap> {
    public static final String IMAGE_LOADING_DEV_TAG = "imageLoadingDevTag";
    private String imgUrl;
    private ImageView iv;
//    private InstagramPostRowAdapter adapter;


    public ImageLoadingTask(String imgUrl, ImageView iv) {
        this.imgUrl = imgUrl;
        this.iv = iv;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Log.d(IMAGE_LOADING_DEV_TAG,"image loading task do in background!");
        try{
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;

        } catch (MalformedURLException e) {
            Log.d(IMAGE_LOADING_DEV_TAG, "MalformedURLException| " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(IMAGE_LOADING_DEV_TAG, "IOException| " +e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.d(IMAGE_LOADING_DEV_TAG,"onPostExecute!!");
        iv.setImageBitmap(bitmap);
    }
}
