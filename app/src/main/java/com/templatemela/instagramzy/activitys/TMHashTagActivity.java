package com.templatemela.instagramzy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import com.templatemela.instagramzy.interfaces.HashTagInterface;
import com.templatemela.instagramzy.interfaces.HastagAdapterInterface;
import com.templatemela.instagramzy.models.TMHashTagModel;
import com.templatemela.instagramzy.myadapter.TMHashTagAdapter;
import com.templatemela.instagramzy.handeler.TMHashTagSearchHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TMHashTagActivity extends AppCompatActivity implements HastagAdapterInterface, HashTagInterface {
    TextView add30;
    TextView addto;
    BottomSheetDialog bottomSheetDialog;
    ImageView clear;
    TextView clearall;
    LinearLayout content;
    TextView copy;
    EditText editText;
    TextView empty;
    LinearLayout emptyList;
    ImageView flSrc;
    Functions functions;
    CardView gen;
    LinearLayout gotoCollection;
    ImageView gotoCollection1;
    TMHashTagAdapter hashTagAdapter;
    TMHashTagSearchHandeler hashTagSearchHandeler;
    long leftPosition = 0;
    List<TMHashTagModel> list;
    LottieAnimationView lottieAnimationView;


    Pref pref;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    List<TMHashTagModel> rList;

    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    TMHashTagAdapter relatedHashtagAdapter;
    long rightPosition = 100;
    TextView sCount;
    List<TMHashTagModel> sList;
    BottomSheetBehavior sheetBehavior;
    LinearLayout slideUp;
    ConstraintLayout slide_up_layout;
    final String tag = "HashTag";
    TagView tagGroup;




    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_hash_tag);


        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyler_view1);
        slideUp = (LinearLayout) findViewById(R.id.slide_up);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottie_layer);
        flSrc = (ImageView) findViewById(R.id.fl_src);
        editText = (EditText) findViewById(R.id.input);
        gen = (CardView) findViewById(R.id.gen);
        clear = (ImageView) findViewById(R.id.clear);
        sCount = (TextView) findViewById(R.id.sCount);

        emptyList = (LinearLayout) findViewById(R.id.empty_list);
        content = (LinearLayout) findViewById(R.id.content);
        add30 = (TextView) findViewById(R.id.add30);
        clearall = (TextView) findViewById(R.id.clearall);
        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        tagGroup = (TagView) findViewById(R.id.tag_group);
        addto = (TextView) findViewById(R.id.addto);
        copy = (TextView) findViewById(R.id.copyH);
        empty = (TextView) findViewById(R.id.em_text);
        gotoCollection = (LinearLayout) findViewById(R.id.gotocollection);
        gotoCollection1 = (ImageView) findViewById(R.id.gotocollection1);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rightPosition = (long) Math.pow(10.0d, 10.0d);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.slup);
        slide_up_layout = constraintLayout;
        sheetBehavior = BottomSheetBehavior.from(constraintLayout);
        slide_up_layout.setVisibility(View.GONE);
        list = new ArrayList();
        sList = new ArrayList();
        rList = new ArrayList();
        hashTagSearchHandeler = new TMHashTagSearchHandeler(this);
        functions = new Functions(this);
        hashTagAdapter = new TMHashTagAdapter(this, list, 0);
        relatedHashtagAdapter = new TMHashTagAdapter(this, rList, 1);
        pref = new Pref(this);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onSlide(View view, float f) {
            }

            @Override
            public void onStateChanged(View view, int i) {
                if (i == 3) {
                    Glide.with((FragmentActivity) TMHashTagActivity.this).load(Integer.valueOf(R.drawable.ic_baseline_expand_more_24)).into(flSrc);
                    Log.i("HashTag", "STATE_EXPANDED");
                } else if (i == 4) {
                    Glide.with((FragmentActivity) TMHashTagActivity.this).load(Integer.valueOf(R.drawable.ic_baseline_expand_less_24)).into(flSrc);
                    Log.i("HashTag", "STATE_COLLAPSED");
                }
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String str = "";
                for (int i = 0; i < sList.size(); i++) {
                    str = str + " #" + sList.get(i).getName();
                }
                functions.copy(str);
            }
        });
        addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "";
                for (int i = 0; i < sList.size(); i++) {
                    str = str + " #" + sList.get(i).getName();
                }
                pref.addToHashTagCollection(str);
                Toast.makeText(TMHashTagActivity.this, "Added", Toast.LENGTH_SHORT).show();
            }
        });
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(editText.getText().toString());
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(TMHashTagActivity.this, "Enter a keyword", Toast.LENGTH_SHORT).show();
                } else if (!functions.internetIsConnected()) {
                    startActivity(new Intent(TMHashTagActivity.this, TMErrorActivity.class));
                } else if (!new TMUserDetails(TMHashTagActivity.this).isLoggedIn()) {
                    new TMLoginRequiredDialog(TMHashTagActivity.this).showDialog();
                } else {
                    hashTagSearchHandeler.getSearchResult(arrayList);
                    slide_up_layout.setVisibility(View.VISIBLE);
                    emptyList.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    TMHashTagActivity.hideKeyboard(TMHashTagActivity.this);
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(hashTagAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(relatedHashtagAdapter);
        tagGroup.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int i) {
                for (int i2 = 0; i2 < sList.size(); i2++) {
                    if (sList.get(i2).getName().trim().equals(tag.text.replaceAll("#", "").trim())) {
                        sList.remove(i2);
                        setSelectedList(sList);
                        setTagList(sList);
                        Log.i("HashTag", "selected list size : " + sList.size());
                        sCount.setText(sList.size() + "");
                        return;
                    }
                }
            }
        });
        slideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        add30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                if (list.size() < rList.size()) {
                    size = rList.size();
                }
                sList.clear();
                while (sList.size() < 25) {
                    int nextInt = new Random().nextInt(size - 1);
                    if (!arrayList.contains(Integer.valueOf(nextInt))) {
                        arrayList.add(Integer.valueOf(nextInt));
                        if (nextInt % 2 == 0 || nextInt % 3 == 0) {
                            try {
                                sList.add(rList.get(nextInt));
                            } catch (Exception unused) {
                                sList.add(list.get(nextInt));
                            }
                        } else {
                            try {
                                sList.add(list.get(nextInt));
                            } catch (Exception unused2) {
                                sList.add(rList.get(nextInt));
                            }
                        }
                    }
                }

                setTagList(sList);
                setSelectedList(sList);
                if (sList.size() == 0) {
                    tagGroup.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    return;
                }
                tagGroup.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
            }
        });
        clearall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sList.clear();
                setTagList(sList);
                setSelectedList(sList);
                hashTagAdapter.notifyDataSetChanged();
            }
        });
        gotoCollection.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                startActivity(new Intent(TMHashTagActivity.this, TMHashtagCollectionsActivity.class));
            }
        });
        gotoCollection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TMHashTagActivity.this, TMHashtagCollectionsActivity.class));
            }
        });
    }


    public long getExp(int i) {
        long j;
        double pow;
        long j2 = 0;
        double pow2 = 0;
        if (i < 10) {
            j = (long) i;
            pow = Math.pow(10.0d, 3.0d);
        } else if (i < 30) {
            j = (long) i;
            pow = Math.pow(10.0d, 4.0d);
        } else {
            if (i < 50) {
                j2 = (long) i;
                pow2 = Math.pow(10.0d, 5.0d);
            } else if (i < 60) {
                j2 = (long) i;
                pow2 = Math.pow(10.0d, 5.0d);
            } else if (i <= 80) {
                j = (long) i;
                pow = Math.pow(10.0d, 6.0d);
            } else if (i <= 90) {
                j = (long) i;
                pow = Math.pow(10.0d, 7.0d);
            } else {
                j = (long) i;
                pow = Math.pow(10.0d, 8.0d);
            }
            return j2 * ((long) pow2);
        }
        return j * ((long) pow);
    }

    public void onSelected(TMHashTagModel hashTagModel) {
        sList.add(hashTagModel);
        setTagList(sList);
        setSelectedList(sList);
        lottieAnimationView.playAnimation();
        sCount.setText(sList.size() + "");
    }

    public void onDeSelected(TMHashTagModel hashTagModel) {
        removeFromSelectedList(hashTagModel);
        setTagList(sList);
        setSelectedList(sList);
        lottieAnimationView.playAnimation();
        sCount.setText(sList.size() + "");
    }

    private void removeFromSelectedList(TMHashTagModel hashTagModel) {
        for (int i = 0; i < sList.size(); i++) {
            if (sList.get(i).getName().equals(hashTagModel.getName())) {
                Log.i("HashTag", i + "  " + hashTagModel.getName());
                sList.remove(i);
                return;
            }
        }
    }


    public void setSelectedList(List<TMHashTagModel> list2) {
        ArrayList arrayList = new ArrayList();
        for (TMHashTagModel add : list2) {
            arrayList.add(add);
        }
        hashTagAdapter.setSelectedHashTags(arrayList);
        hashTagAdapter.notifyDataSetChanged();
        relatedHashtagAdapter.setSelectedHashTags(arrayList);
        relatedHashtagAdapter.notifyDataSetChanged();
        sCount.setText(sList.size() + "");
        if (sList.size() == 0) {
            tagGroup.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            return;
        }
        tagGroup.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }


    public void setTagList(List<TMHashTagModel> list2) {
        ArrayList arrayList = new ArrayList();
        for (TMHashTagModel next : list2) {
            Log.i("HashTag", "name " + next.getName() + " count : " + next.getPostCount());
            StringBuilder sb = new StringBuilder();
            sb.append(" #");
            sb.append(next.getName());
            sb.append(" ");
            Tag tag2 = new Tag(sb.toString());
            tag2.layoutColor =getColor(R.color.tagView) ;
            tag2.tagTextColor = Color.parseColor("#000000");
            tag2.deleteIndicatorColor = Color.parseColor("#40247f");
            tag2.tagTextSize = 13.0f;
            arrayList.add(tag2);
        }
        tagGroup.addTags((List<Tag>) arrayList);
        if (sList.size() == 0) {
            tagGroup.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            return;
        }
        tagGroup.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
    }


    public void setList(List<TMHashTagModel> list2, TMHashTagAdapter hashTagAdapter2) {
        List<TMHashTagModel> sortedList = new Sort(list2).getSortedList();
        Log.i("HashTag", leftPosition + "");
        Log.i("HashTag", rightPosition + "");

        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < sortedList.size(); i++) {
            if (((long) sortedList.get(i).getPostCount()) >= leftPosition && ((long) sortedList.get(i).getPostCount()) <= rightPosition) {
                arrayList.add(sortedList.get(i));
            }
        }
        hashTagAdapter2.setList(arrayList);
        try {
            hashTagAdapter2.setMax(((TMHashTagModel) arrayList.get(0)).getPostCount());
            hashTagAdapter2.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onSearchComplete(List<TMHashTagModel> list2) {
        list = list2;
        setList(list2, hashTagAdapter);
        setSelectedList(sList);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar1.setVisibility(View.GONE);
    }

    public void onRelatedListComplete(List<TMHashTagModel> list2) {
        rList = list2;
        setList(list2, relatedHashtagAdapter);
        setSelectedList(sList);
        recyclerView1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.GONE);
    }

    public class Sort {
        List<TMHashTagModel> list;

        public Sort(List<TMHashTagModel> list2) {
            list = list2;
        }

        class SortByCountUp implements Comparator<TMHashTagModel> {
            SortByCountUp() {
            }

            public int compare(TMHashTagModel hashTagModel, TMHashTagModel hashTagModel2) {
                return hashTagModel2.getPostCount() - hashTagModel.getPostCount();
            }
        }

        public List<TMHashTagModel> getSortedList() {
            Collections.sort(list, new SortByCountUp());
            return list;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
}
