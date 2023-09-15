package com.templatemela.instagramzy.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.myadapter.TMCollectionAdapter;
import org.json.JSONArray;

public class TMHashtagCollectionsActivity extends AppCompatActivity {
    TMCollectionAdapter collectionAdapter;
    LinearLayout emptyView;
    Pref pref;
    RecyclerView recyclerView;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_hashtag_collections);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        pref = new Pref(this);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        JSONArray hashTagCollection = pref.getHashTagCollection();
        if (hashTagCollection.length() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collectionAdapter = new TMCollectionAdapter(this, hashTagCollection, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(collectionAdapter);
    }
}
