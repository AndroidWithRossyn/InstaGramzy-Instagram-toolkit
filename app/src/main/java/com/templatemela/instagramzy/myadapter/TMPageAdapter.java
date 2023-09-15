package com.templatemela.instagramzy.myadapter;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.templatemela.instagramzy.fragments.TMDownloadFilesFragment;
import com.templatemela.instagramzy.fragments.TMHomeFragment;
import com.templatemela.instagramzy.fragments.TMChatListFragment;
import com.templatemela.instagramzy.fragments.TMPostFunctionFragment;

public class TMPageAdapter extends FragmentPagerAdapter {
    Activity activity;
    private Context myContext;
    int totalTabs;

    public TMPageAdapter(Context context, Activity activity2, FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.myContext = context;
        this.activity = activity2;
        this.totalTabs = i;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new TMHomeFragment();
        }
        if (i == 1) {
            return new TMPostFunctionFragment(this.activity);
        }
        if (i == 2) {
            return new TMChatListFragment();
        }
        if (i != 3) {
            return null;
        }
        return new TMDownloadFilesFragment(this.activity);
    }


    @Override
    public int getCount() {
        return this.totalTabs;
    }
}
