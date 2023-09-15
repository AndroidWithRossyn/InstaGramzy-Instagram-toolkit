package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.myadapter.TMCaptionAdapter;
import org.json.JSONArray;
import org.json.JSONException;

public class TMCaptionViewActivity extends AppCompatActivity {
    LinearLayout emptyView;
    ImageView fav;
    Functions functions;
    int id;
    Pref pref;
    RecyclerView recyclerView;
    String title;
    TextView titleTv;
    int type;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_caption_type);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        titleTv = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        titleTv.setText(" " + title);
        fav = (ImageView) findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TMCaptionViewActivity.this, TMCaptionViewActivity.class);
                intent.putExtra("id", 0);
                intent.putExtra("title", "Favourite");
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        String str;
        super.onResume();
        functions = new Functions(this);
        pref = new Pref(this);
        if (type == 0) {
            str = functions.readFromAssetsJson(functions.getStringForNumber(id)+".json");
            Log.e("json",str);
        } else {
            str = pref.getBookMarks().toString();
            fav.setVisibility(View.GONE);
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            TMCaptionAdapter captionAdapter = new TMCaptionAdapter(this, jSONArray, 0);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(captionAdapter);
            if (jSONArray.length() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
