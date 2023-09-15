package com.templatemela.instagramzy.magic_text;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.templatemela.instagramzy.R;


import java.util.ArrayList;
import java.util.List;


public class TMMagicTextActivity extends AppCompatActivity {

    public TabLayout tabLayout;


    public class PagerAdepeters extends FragmentPagerAdapter {


        public final List arrayList = new ArrayList();


        public final List arrayList1 = new ArrayList();

        public PagerAdepeters(TMMagicTextActivity magicTextActivity, FragmentManager t0Var) {
            super(t0Var);
        }



        public int getItem() {
            return this.arrayList.size();
        }


        @Override
        public CharSequence getPageTitle(int i) {
            return (CharSequence) this.arrayList1.get(i);
        }


        @Override
        public Fragment getItem(int i) {
            return (Fragment) this.arrayList.get(i);
        }



        @Override
        public int getCount() {
            return arrayList.size();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main_magic_text);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_ad_magic_text);


        findViewById(R.id.tb_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdepeters pagerAdepeters = new PagerAdepeters(this, getSupportFragmentManager());
        pagerAdepeters.arrayList.add(new FontsFragment());
        pagerAdepeters.arrayList1.add("Fonts");
        pagerAdepeters.arrayList.add(new TextDecorationFragment());
        pagerAdepeters.arrayList1.add("Decoration");
        viewPager.setAdapter(pagerAdepeters);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
