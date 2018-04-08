package com.example.admin.lab1_smarttree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class content_share extends AppCompatActivity{

    private LikeView btnLike;
    private ShareDialog shareDialog;
    private int PICK_IMAGE_REQUEST=1;
    private int PICK_VIDEO_REQUEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_share);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        shareDialog = new ShareDialog(this);  // intialize facebook shareDialog.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


    }

    public void shareLinks(View view) {
//
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Androidlift")
//                    .setContentDescription("Androidlift blog")
//                    .setContentUrl(Uri.parse("http://androidlift.info"))
//                    .build();
//
//            shareDialog.show(linkContent);
//        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:///"+ Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.mp3"));
        startActivity(Intent.createChooser(share, "Share Sound File"));
    }

    public void sharePhotos(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void shareVideo(View view) {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    public void shareEmail(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "sumakaq@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            if (requestCode==PICK_IMAGE_REQUEST && data!=null && data.getData()!=null) {

                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();

                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    shareDialog.show(sharePhotoContent);
                }
            }else if(requestCode==PICK_VIDEO_REQUEST && data!=null && data.getData()!=null){

                ShareVideo shareVideo = new ShareVideo.Builder()
                        .setLocalUrl(data.getData())
                        .build();
                if (ShareDialog.canShow(ShareVideoContent.class)) {
                    ShareVideoContent shareVideoContent = new ShareVideoContent.Builder()
                            .setVideo(shareVideo)
                            .build();
                    shareDialog.show(shareVideoContent);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
