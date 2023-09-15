package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Loader;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.myadapter.TMSearchAdapter;
import com.templatemela.instagramzy.handeler.TMPersonDetails;
import com.templatemela.instagramzy.handeler.TMSearchHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

public class TMSearchActivity extends AppCompatActivity {
    EditText editText;
    TextView em;
    LinearLayout emptyView;
    Loader loader;
    TMPersonDetails personDetails;
    RecyclerView recyclerView;
    TMSearchAdapter searchAdapter;
    TMSearchHandeler searchHandeler;
    TextView textView;

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_search);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.title);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        em = (TextView) findViewById(R.id.empty_text);
        if (!new Functions(this).internetIsConnected()) {
            startActivity(new Intent(this, TMErrorActivity.class));
            finish();
        }
        textView.setText(getIntent().getStringExtra("title"));
        personDetails = new TMPersonDetails(this);
        loader = new Loader(this);
        if (!new TMUserDetails(this).isLoggedIn()) {
            new TMLoginRequiredDialog(this).showDialog();
        }
        findViewById(R.id.tb_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        em.setText("Search a name or paste a profile url to" + getIntent().getStringExtra("title").toLowerCase());
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        searchHandeler = new TMSearchHandeler(this);
        searchAdapter = new TMSearchAdapter(this, new JSONArray(), getIntent().getIntExtra("type", 0));
        editText.addTextChangedListener(new TextWatcher() {
          @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().contains("https://www.instagram.com/")) {
                    loader.showOnlyAnimation();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final TMUserModel personIdFromUrl = personDetails.getPersonIdFromUrl(charSequence.toString());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    openAc(personIdFromUrl);
                                    loader.dismiss();
                                }
                            });
                        }
                    }).start();
                    return;
                }
                searchHandeler.getSearchResult(charSequence.toString());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
    }

    
    public void openAc(TMUserModel user) {
        Intent intent = new Intent(this, TMDpViewActivity.class);
        intent.putExtra("id", user.getId());
        intent.putExtra("name", user.getName());
        intent.putExtra("dp", user.getDp());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(JSONArray jSONArray) {
        searchAdapter.setJsonArray(jSONArray);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
