package com.thisobeystudio.shareimage.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.thisobeystudio.shareimage.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by thisobeystudio on 18/4/18.
 * Copyright: (c) 2018 ThisObey Studio
 * Contact: thisobeystudio@gmail.com
 */
public class ShareUtils {

    private static final String TAG = ShareUtils.class.getSimpleName();

    public static void shareImage(Context context, Bitmap bitmap, String message) {

        if (context == null || bitmap == null) {
            if (context != null)
                Toast.makeText(context, "Bitmap is NULL", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            // Make sure that this path String is equals than filepaths.xml => path="shared_image"
            // <cache-path name="shared_image" path="shared_image/"/>
            String path = "shared_image/";

            // save bitmap to cache directory.
            File cachePath = new File(context.getCacheDir(), path);

            // don't forget to make the directory
            if (cachePath.mkdirs()) {
                Log.i(TAG, "New path created");
            } else {
                Log.i(TAG, "New path created");
            }

            // overwrites this image every time
            String fileName = "image_to_share.jpg";                   // jpg extension
            String filePath = cachePath + "/" + fileName;
            FileOutputStream stream = new FileOutputStream(filePath); // JPEG CompressFormat
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

            // get saved image
            File newFile = new File(cachePath, fileName);

            // get the file Uri
            Uri contentUri =
                    FileProvider.getUriForFile(
                            context,
                            context.getString(R.string.file_provider),
                            newFile);

            if (contentUri != null) {

                // create an Intent
                final Intent share = new Intent();

                // set the intent action
                share.setAction(Intent.ACTION_SEND);

                // temp permission for receiving app to read this file
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // set as new task
                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // pass the newFile as Stream
                share.putExtra(Intent.EXTRA_STREAM, contentUri);

                // set the message if it's not empty nor null
                if (!TextUtils.isEmpty(message)) share.putExtra(Intent.EXTRA_TEXT, message);

                // content type
                share.setType("image/*");

                // get the chooser title
                String title = context.getString(R.string.chooser_title);

                // finally start the activity as Chooser
                context.startActivity(Intent.createChooser(share, title));
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "shareImage IOException", Toast.LENGTH_SHORT).show();
        }
    }
}
