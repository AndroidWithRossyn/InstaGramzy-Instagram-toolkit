package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.myadapter.TMCaptionTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TMCaptionTypeActivity extends AppCompatActivity {
    TMCaptionTypeAdapter captionTypeAdapter;
    Functions functions;
    RecyclerView recyclerView;
    ImageView backBtn;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_caption_type);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        functions = new Functions(this);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            captionTypeAdapter = new TMCaptionTypeAdapter(this, new JSONArray(functions.readFromAssetsJson("cts.json")));
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(captionTypeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((ImageView) findViewById(R.id.fav)).setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View view) {
                Intent intent = new Intent(TMCaptionTypeActivity.this, TMCaptionViewActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("title", "Favourite");
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
