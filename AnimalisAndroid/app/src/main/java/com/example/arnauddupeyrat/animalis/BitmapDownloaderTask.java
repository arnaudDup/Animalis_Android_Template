package com.example.arnauddupeyrat.animalis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Size;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



class BitmapDownloaderTask extends AsyncTask<URL, Void , Bitmap> {
    private final int imageViewReference;
    private final Activity activity;
    private  int width;
    private  int heigth;
    public BitmapDownloaderTask(int imageView, Activity anActivity, int aWidth, int aHeight ) {
        super();
        imageViewReference = imageView;
        this.activity = anActivity;
        width = aWidth;
        heigth = aHeight;
    }

    @Override
    protected void onPreExecute() {
        Log.d("on pre Execute", "Rfabrication de la BIPMAP");
        // TODO affichge du fragment d'attente spinner.
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(URL... params) {
        return downloadBitmap(params[0]);

    }

    @Override
    protected void onCancelled (Bitmap bitmap){
        Log.d("on cancel", "Rfabrication de la BIPMAP");
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        Log.d("on post Execute", "Rfabrication de la BIPMAP");
        super.onPostExecute(bitmap);
        if (isCancelled()) {
            bitmap = null;
        }
        if (imageViewReference != 0) {

            ImageView imageView = (ImageView) activity.findViewById(imageViewReference);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private Bitmap downloadBitmap(URL url) {
        Bitmap bitmap = null;
        try {
            Log.d("ServerRequest", "url = " + url + "   requestType = " + "GET");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                if (stream != null) {
                    try {
                        bitmap = BitmapFactory.decodeStream(stream);
                        bitmap = Bitmap.createScaledBitmap(bitmap,this.width,this.heigth,true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}

