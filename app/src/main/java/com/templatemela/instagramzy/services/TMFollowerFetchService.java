package com.templatemela.instagramzy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.models.TMUserModel;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMFollowerFetchService extends Service {
    Functions functions;
    List<TMPersonModel> personList;
    Pref pref;
    int prelistSize;
    TMUserModel user;
    TMUserDetails userDetails;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        functions = new Functions(getApplicationContext());
        pref = new Pref(getApplicationContext());
        personList = new ArrayList();
        TMUserDetails userDetails2 = new TMUserDetails(getApplicationContext());
        userDetails = userDetails2;
        user = userDetails2.getUser(false);
        int size = pref.getMainList().size();
        prelistSize = size;
        if (size == 0) {
            prelistSize = user.getFollowers() + user.getFollowing();
        }
        personList.clear();
        new Thread(new Runnable() {
            public void run() {
                getList(0);
                getList(1);
                Log.i("list", personList.size() + " ");
                saveWhoFollowedMe();
                saveWhoUnfollowedMe();
                pref.saveMainList(personList);
                EventBus.getDefault().post(personList);
                stopSelf();
            }
        }).start();
        return Service.START_NOT_STICKY;
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

    public void getList(int paramInt) {
        String str1 = this.functions.getUserId();
        if (paramInt == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://i.instagram.com/api/v1/friendships/");
            stringBuilder.append(str1);
            stringBuilder.append("/followers/");
            str1 = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://i.instagram.com/api/v1/friendships/");
            stringBuilder.append(str1);
            stringBuilder.append("/following/");
            str1 = stringBuilder.toString();
        }
        String str2 = "";
        String str3 = str1;
        byte b = 0;
        while (true) {
            if (!str2.equals("null"))
                try {
                    str2 = this.functions.page(str3).text();
                    JSONObject jSONObject = new JSONObject(str2);
                    JSONArray jSONArray = jSONObject.getJSONArray("users");
                    int i;
                    for (i = 0; i < jSONArray.length(); i++) {
                        Boolean bool1;
                        Boolean bool2;
                        JSONObject jSONObject1 = jSONArray.getJSONObject(i);
                        if (paramInt == 0) {
                            bool2 = Boolean.valueOf(false);
                            bool1 = Boolean.valueOf(true);
                        } else {
                            bool2 = Boolean.valueOf(true);
                            bool1 = Boolean.valueOf(false);
                        }
                        String str4 = jSONObject1.getString("pk");
                        if (paramInt == 0) {
                            TMPersonModel person = new TMPersonModel(jSONObject1.getString("full_name"), str4, jSONObject1.getString("username"), jSONObject1.getString("profile_pic_url"), bool1, bool2, Boolean.valueOf(jSONObject1.getBoolean("is_verified")), 0L);
                            this.personList.add(person);
                        } else if (!isInList(str4).booleanValue()) {
                            Log.i("json_obj", jSONObject1.toString());
                            TMPersonModel person = new TMPersonModel(jSONObject1.getString("full_name"), str4, jSONObject1.getString("username"), jSONObject1.getString("profile_pic_url"), bool1, bool2, Boolean.valueOf(jSONObject1.getBoolean("is_verified")), 0L);
                            this.personList.add(person);
                        }
                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append(i);
                        stringBuilder1.append(" ");
                        stringBuilder1.append(jSONObject1.toString());
                        Log.i("data", stringBuilder1.toString());
                    }
                    Pref pref = this.pref;
                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append("d");
                    stringBuilder.append(paramInt);
                    String str = stringBuilder.toString();
                    stringBuilder = new StringBuilder();

                    stringBuilder.append(jSONArray.length());
                    stringBuilder.append("\n");
                    stringBuilder.append(jSONObject.toString());
                    pref.write(str, stringBuilder.toString());
                    try {
                        String str4 = jSONObject.getString("next_max_id");
                        stringBuilder = new StringBuilder();

                        stringBuilder.append(str1);
                        stringBuilder.append("?max_id=");
                        stringBuilder.append(str4);
                        String str5 = stringBuilder.toString();
                        if (++b > 0 && b % 6 == 0) {
                            try {
                                Thread.sleep(4000L);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        } else {
                            Thread.sleep(700L);
                        }
                        i = (int)(this.personList.size() / this.prelistSize * 100.0D);
                        if (i < 100) {
                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append("Fetching followers and following : ");
                            stringBuilder1.append(i);
                            stringBuilder1.append("%");
                            String str6 = stringBuilder1.toString();
                            EventBus.getDefault().post(new TMStrModel(str6, 0));
                        } else {
                            EventBus.getDefault().post(new TMStrModel("Fetching followers and following : 100%", 0));
                            EventBus.getDefault().post(new TMStrModel("finish", 0));
                        }
                        stopSelf();
                        continue;
                    } catch (JSONException jSONException) {}
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            return;
        }
    }

 /*   public void getList(int paramInt) {
        String str1 = functions.getUserId();
        if (paramInt == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://i.instagram.com/api/v1/friendships/");
            stringBuilder.append(str1);
            stringBuilder.append("/followers/");
            str1 = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://i.instagram.com/api/v1/friendships/");
            stringBuilder.append(str1);
            stringBuilder.append("/following/");
            str1 = stringBuilder.toString();
        }
        String str2 = "";
        String str3 = str1;
        byte b = 0;
        while (true) {
            if (!str2.equals("null"))
                try {
                    str2 = functions.page(str3).text();
                    JSONObject jSONObject = new JSONObject(str2);
                    FO(str2);
                    JSONArray jSONArray = jSONObject.getJSONArray("users");
                    int i;
                    for (i = 0; i < jSONArray.length(); i++) {
                        Boolean bool1;
                        Boolean bool2;
                        JSONObject jSONObject1 = jSONArray.getJSONObject(i);
                        if (paramInt == 0) {
                            bool2 = Boolean.valueOf(false);
                            bool1 = Boolean.valueOf(true);
                        } else {
                            bool2 = Boolean.valueOf(true);
                            bool1 = Boolean.valueOf(false);
                        }
                        String str4 = jSONObject1.getString("pk");
                        if (paramInt == 0) {
                            Person person = new Person(jSONObject1.getString("full_name"), str4, jSONObject1.getString("username"), jSONObject1.getString("profile_pic_url"), bool1, bool2, Boolean.valueOf(jSONObject1.getBoolean("is_verified")), 0L);

                            personList.add(person);
                        } else if (!isInList(str4).booleanValue()) {
                            Log.i("json_obj", jSONObject1.toString());
                            Person person = new Person(jSONObject1.getString("full_name"), str4, jSONObject1.getString("username"), jSONObject1.getString("profile_pic_url"), bool1, bool2, Boolean.valueOf(jSONObject1.getBoolean("is_verified")), 0L);

                            personList.add(person);
                        }
                        StringBuilder stringBuilder1 = new StringBuilder();

                        stringBuilder1.append(i);
                        stringBuilder1.append(" ");
                        stringBuilder1.append(jSONObject1.toString());
                        Log.i("data", stringBuilder1.toString());
                    }

                    StringBuilder stringBuilder = new StringBuilder();

                    stringBuilder.append("d");
                    stringBuilder.append(paramInt);
                    String str = stringBuilder.toString();
                    stringBuilder = new StringBuilder();

                    stringBuilder.append(jSONArray.length());
                    stringBuilder.append("\n");
                    stringBuilder.append(jSONObject.toString());
                    pref.write(str, stringBuilder.toString());
                    try {
                        String str4 = jSONObject.getString("next_max_id");
                        stringBuilder = new StringBuilder();

                        stringBuilder.append(str1);
                        stringBuilder.append("?max_id=");
                        stringBuilder.append(str4);
                        String str5 = stringBuilder.toString();
                        if (++b > 0 && b % 6 == 0) {
                            try {
                                Thread.sleep(4000L);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        } else {
                            Thread.sleep(700L);
                        }
                        i = (int)(personList.size() / prelistSize * 100.0D);
                        if (i < 100) {
                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append("Fetching followers and following : ");
                            stringBuilder1.append(i);
                            stringBuilder1.append("%");
                            String str6 = stringBuilder1.toString();
                            EventBus.getDefault().post(new Str(str6, 0));
                        } else {
                            EventBus.getDefault().post(new Str("Fetching followers and following : 100%", 0));
                            EventBus.getDefault().post(new Str("finish", 0));
                        }
                        stopSelf();
                        continue;
                    } catch (JSONException jSONException) {}
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            return;
        }
    }*/


    public Boolean isInList(String str) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getId().equals(str)) {
                TMPersonModel person = personList.get(i);
                person.setFollowedByMe(true);
                personList.set(i, person);
                return true;
            }
        }
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
