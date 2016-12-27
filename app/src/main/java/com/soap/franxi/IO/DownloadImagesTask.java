package com.soap.franxi.IO;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 27/12/2016.
 */

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public DownloadImagesTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imageUrl = params[0];

        InputStream in = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            } else {
                return null;
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return null;
    }


    // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
    // Download thành công, update kết quả lên giao diện.
    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            this.imageView.setImageBitmap(result);
        } else {
            Log.e("MyMessage", "Failed to fetch data!");
        }
    }
}
