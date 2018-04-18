package com.thisobeystudio.shareimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.thisobeystudio.shareimage.share.ShareUtils;

public class MainActivity extends AppCompatActivity {

    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDemoView();
    }

    private void setupDemoView() {
        // find the image to share holder (ImageView)
        ImageView demoImageView = findViewById(R.id.demo_image_view);

        // get bitmap from imageView
        BitmapDrawable bitmapDrawable = (BitmapDrawable) demoImageView.getDrawable();
        if (bitmapDrawable != null)
            bitmap = bitmapDrawable.getBitmap();

        // set onClick listener to demo share button
        findViewById(R.id.share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform share image
                ShareUtils.shareImage(MainActivity.this, bitmap, "Share Image Demo Message");
            }
        });
    }
}
