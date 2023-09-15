package com.templatemela.instagramzy.utils;

import android.content.Context;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.models.TMPersonModel;
import java.util.ArrayList;
import java.util.List;

public class TMFollowerAnalysisListsCounts {
    Context context;
    Functions functions;
    List<TMPersonModel> mainList;
    Pref pref;

    public TMFollowerAnalysisListsCounts(Context context2, List<TMPersonModel> list) {
        context = context2;
        mainList = list;
        pref = new Pref(context2);
        functions = new Functions(context2);
    }

    public String getListCount(int i) {
        List arrayList = new ArrayList();
        if (i < 3) {
            for (int i2 = 0; i2 < mainList.size(); i2++) {
                TMPersonModel person = mainList.get(i2);
                if (i == 0) {
                    if (person.getFollowedByMe().booleanValue() && !person.getFollowing().booleanValue()) {
                        arrayList.add(person);
                    }
                } else if (i == 1) {
                    if (!person.getFollowedByMe().booleanValue() && person.getFollowing().booleanValue()) {
                        arrayList.add(person);
                    }
                } else if (i == 2 && person.getFollowedByMe().booleanValue() && person.getFollowing().booleanValue()) {
                    arrayList.add(person);
                }
            }
        }
        if (i > 3) {
            List<TMPersonModel> recentFollowUnfollowList = pref.getRecentFollowUnfollowList(i - 4);
            for (TMPersonModel person2 : recentFollowUnfollowList) {
                if (pref.getFollowerListUpdateTime() - person2.getTime() < 3600000) {
                    arrayList.add(person2);
                }
            }
            if (arrayList.size() > 0) {
                return "+" + functions.withSuffix((long) arrayList.size()) + " ";
            }
            arrayList = recentFollowUnfollowList;
        }
        return "" + functions.withSuffix((long) arrayList.size()) + " ";
    }
}
