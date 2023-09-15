package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.utils.ServiceTools;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.services.TMFollowerFetchService;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class TMFollowerListHadeler {
    Context context;
    public  boolean isFirstTime=false;
    Functions functions;
    Handler handler;
    List<TMPersonModel> personList = new ArrayList();
    Pref pref;
    TMUserModel user;
    TMUserDetails userDetails;

    public TMFollowerListHadeler(Context context2) {
        context = context2;
        functions = new Functions(context2);
        pref = new Pref(context2);
        handler = new Handler(context2.getMainLooper());
        TMUserDetails userDetails2 = new TMUserDetails(context2);
        userDetails = userDetails2;
        user = userDetails2.getUser(false);
         isFirstTime=false;
    }


    public void saveWhoUnfollowedMe() {
        ArrayList arrayList = new ArrayList();
        List<TMPersonModel> mainList = pref.getMainList();
        Log.e("size", String.valueOf(mainList.size()));
        if (mainList.size() != 0) {
            for (int i = 0; i < mainList.size(); i++) {
                int i2 = 0;
                while (true) {
                    if (i2 >= personList.size()) {
                        break;
                    } else if (!mainList.get(i).getId().equals(personList.get(i2).getId())) {
                        i2++;
                    } else if (mainList.get(i).getFollowing().booleanValue() && !personList.get(i2).getFollowing().booleanValue()) {
                        TMPersonModel person = mainList.get(i);
                        person.setTime(System.currentTimeMillis());
                        arrayList.add(person);
                    }
                }
                if (i2 == personList.size() && mainList.get(i).getFollowing().booleanValue()) {
                    TMPersonModel person2 = mainList.get(i);
                    person2.setTime(System.currentTimeMillis());
                    arrayList.add(person2);
                }
            }
            pref.saveRecentlyFollowUnFollowedList(arrayList, 0);
        }
    }

    public void saveWhoFollowedMe() {
        ArrayList arrayList = new ArrayList();
        List<TMPersonModel> mainList = pref.getMainList();
        if (mainList.size() != 0) {
            for (int i = 0; i < personList.size(); i++) {
                int i2 = 0;
                while (true) {
                    if (i2 >= mainList.size()) {
                        break;
                    } else if (!mainList.get(i2).getId().equals(personList.get(i).getId())) {
                        i2++;
                    } else if (personList.get(i).getFollowing().booleanValue() && !mainList.get(i2).getFollowing().booleanValue()) {
                        TMPersonModel person = personList.get(i);
                        person.setTime(System.currentTimeMillis());
                        arrayList.add(person);
                    }
                }
                if (i2 == mainList.size() && personList.get(i).getFollowing().booleanValue()) {
                    TMPersonModel person2 = personList.get(i);
                    person2.setTime(System.currentTimeMillis());
                    arrayList.add(person2);
                }
            }
            pref.saveRecentlyFollowUnFollowedList(arrayList, 1);
        }
    }
    public void fetchList(Boolean bool) {
        personList.clear();
        if (bool.booleanValue()) {
            new Thread(new Runnable() {
                public void run() {
                    List<TMPersonModel> mainList = pref.getMainList();
                    if (mainList.size() == 0) {
                        callService();
                        return;
                    }
                    int i = 0;
                    int i2 = 0;
                    for (TMPersonModel next : mainList) {
                        if (next.getFollowing().booleanValue()) {
                            i++;
                        }
                        if (next.getFollowedByMe().booleanValue()) {
                            i2++;
                        }
                    }
                    if (userDetails.isFollowerOrFollowingChanged(i, i2).booleanValue()) {
                        int followers = user.getFollowers() + user.getFollowing();
                        if (followers > 50000) {
                            EventBus.getDefault().post(new TMStrModel("Follower list is too big to load", 0));
                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, "Follower list is too big to load", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                        if (followers > 20000) {
                            handler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, "Follower list is big. It will take a long time", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        callService();
                        return;
                    }
                    EventBus.getDefault().post(mainList);
                    EventBus.getDefault().post(new TMStrModel("Fetching followers and following : 100%", 0));
                    EventBus.getDefault().post(new TMStrModel("finish", 0));
                    pref.write("mainListUpdateTime" + functions.getUserId(), System.currentTimeMillis() + "");
                }
            }).start();
        }
        else if (pref.getMainList().size() == 0) {
            fetchList(true);

        } else {
            final List<TMPersonModel> mainList = pref.getMainList();
            Log.i("list", mainList.size() + " ");
            if(!isFirstTime){
                saveWhoUnfollowedMe();
                saveWhoFollowedMe();
                isFirstTime=true;
            }
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(mainList);
                    EventBus.getDefault().post(new TMStrModel("finish", 0));
                }
            }).start();
        }
    }

   
    public void callService() {
        context.startService(new Intent(context, TMFollowerFetchService.class));
        if (new ServiceTools().isAnyListFetching(context)) {

            Log.i("data__", "start");
            return;
        }
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, "Already fetching your data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
