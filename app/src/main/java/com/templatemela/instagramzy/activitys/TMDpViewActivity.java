package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMDownloadHelper;
import com.templatemela.instagramzy.handeler.TMPersonDetails;

import java.util.Random;

public class TMDpViewActivity extends AppCompatActivity {
    CardView downloadCard;
    TMDownloadHelper downloadHelper;
    String dpUrl = "";
    String filename;
    String id;
    String name;
    TMPersonDetails personDetails;
    PhotoView photoView;
    String tag = "DpView";
    TextView title;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_dp_view);
        personDetails = new TMPersonDetails(this);
        photoView = (PhotoView) findViewById(R.id.photo_view);
        title = (TextView) findViewById(R.id.title);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        downloadCard = (CardView) findViewById(R.id.card);
        downloadHelper = new TMDownloadHelper(this);
        title.setText("Dp of " + name);
        name.replaceAll(" ", "_");
        filename = "dp_of_" + name + new Random().nextInt() + ".jpg";
        Log.i(tag, id);
        if (getIntent().getStringExtra("dp") == null) {
            new Thread(new Runnable() {
               @Override
                public void run() {
                    dpUrl = personDetails.getProfilePicture(id);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Glide.with((FragmentActivity) TMDpViewActivity.this).load(dpUrl).into((ImageView) photoView);
                        }
                    });
                }
            }).start();
        } else {
            Glide.with((FragmentActivity) this).load(getIntent().getStringExtra("dp")).into((ImageView) photoView);
        }
        downloadCard.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (dpUrl.equals("")) {
                    Toast.makeText(TMDpViewActivity.this, "Dp is not loaded yet", Toast.LENGTH_LONG).show();
                } else {
                    downloadHelper.startDownload(dpUrl, filename);
                }
            }
        });
    }
}
