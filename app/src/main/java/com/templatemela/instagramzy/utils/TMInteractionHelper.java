package com.templatemela.instagramzy.utils;

import android.content.Context;
import android.util.Log;

import com.templatemela.instagramzy.models.TMLikerCommenterModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMInteractionHelper {
    Context context;
    Pref pref;
    String tag = "InteractionHelper";

    public TMInteractionHelper(Context context2) {
        this.context = context2;
        this.pref = new Pref(context2);
    }

    public List<TMLikerCommenterModel> getLikeCommentList(JSONArray jSONArray) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = null;
            try {
                jSONObject = jSONArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jSONArray3 = new JSONArray();
            try {
                jSONArray3 = jSONObject.getJSONArray("likers");
            } catch (Exception unused) {
            }
            int i2 = 0;
            while (true) {
                boolean z2 = true;
                if (i2 >= jSONArray3.length()) {
                    break;
                }
                JSONObject jSONObject2 = null;
                try {
                    jSONObject2 = jSONArray3.getJSONObject(i2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = jSONObject2.getString("pk");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= arrayList.size()) {
                        z2 = false;
                        break;
                    } else if (((TMLikerCommenterModel) arrayList.get(i3)).getId().equals(string)) {
                        TMLikerCommenterModel likerCommenterModel = (TMLikerCommenterModel) arrayList.get(i3);
                        likerCommenterModel.setLikeCount(likerCommenterModel.getLikeCount() + 1);
                        arrayList.set(i3, likerCommenterModel);
                        break;
                    } else {
                        i3++;
                    }
                }
                if (!z2) {
                    try {
                        arrayList.add(new TMLikerCommenterModel(jSONObject2.getString("pk"), jSONObject2.getString("username"), jSONObject2.getString("full_name"), jSONObject2.getString("profile_pic_url"), 1, 0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                i2++;
            }
            JSONObject jSONObject3 = null;
            try {
                jSONObject3 = jSONArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jSONArray4 = new JSONArray();
            try {
                jSONArray4 = jSONObject3.getJSONArray("commenters");
            } catch (Exception unused2) {
            }
            int i4 = 0;
            while (i4 < jSONArray4.length()) {
                try {
                    JSONObject jSONObject4 = jSONArray4.getJSONObject(i4);
                    int i5 = 0;
                    while (true) {
                        if (i5 >= arrayList.size()) {
                            z = false;
                            break;
                        } else if (((TMLikerCommenterModel) arrayList.get(i5)).getId().equals(jSONObject4.getString("pk"))) {
                            TMLikerCommenterModel likerCommenterModel2 = (TMLikerCommenterModel) arrayList.get(i5);
                            likerCommenterModel2.setCommentCount(likerCommenterModel2.getCommentCount() + 1);
                            arrayList.set(i5, likerCommenterModel2);
                            z = true;
                            break;
                        } else {
                            i5++;
                        }
                    }
                    if (!z) {
                        arrayList.add(new TMLikerCommenterModel(jSONObject4.getString("pk"), jSONObject4.getString("username"), jSONObject4.getString("full_name"), jSONObject4.getString("profile_pic_url"), 0, 1));
                        Log.i("interaction_", jSONObject4.toString());
                    }
                    i4++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public void saveWhoRemoveLikeOrComment(JSONArray jSONArray, JSONObject jSONObject) {
        JSONArray jSONArray2;
        String str;
        String str2;
        JSONArray jSONArray3 = jSONArray;
        JSONObject jSONObject2 = jSONObject;
        try {
            Log.i(this.tag, "saveWhoRemoveLikeOrComment");
            String string = jSONObject2.getString("id");
            JSONArray jSONArray4 = jSONObject2.getJSONArray("likers");
            JSONArray jSONArray5 = jSONObject2.getJSONArray("commenters");
            int i = 0;
            while (i < jSONArray.length()) {
                if (!string.equals(jSONArray3.getJSONObject(i).getString("id"))) {
                    i++;
                } else {
                    Log.i(this.tag, "saveWhoRemoveLikeOrComment1");
                    JSONObject jSONObject3 = jSONArray3.getJSONObject(i);
                    JSONArray jSONArray6 = jSONObject3.getJSONArray("likers");
                    JSONArray jSONArray7 = jSONObject3.getJSONArray("commenters");
                    String str3 = "full_name";
                    String str4 = "pk";
                    if (!jSONArray6.toString().equals(jSONArray4.toString())) {
                        int i2 = 0;
                        while (i2 < jSONArray6.length()) {
                            int i3 = 0;
                            while (true) {
                                if (i3 >= jSONArray4.length()) {
                                    break;
                                } else if (jSONArray6.getJSONObject(i2).getString(str4).equals(jSONArray4.getJSONObject(i3).getString(str4))) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                            if (i3 == jSONArray4.length()) {
                                Log.i(this.tag, jSONArray6.getJSONObject(i2).getString(str3));
                                Pref pref2 = this.pref;
                                String string2 = jSONArray6.getJSONObject(i2).getString(str4);
                                String string3 = jSONArray6.getJSONObject(i2).getString("username");
                                String string4 = jSONArray6.getJSONObject(i2).getString(str3);
                                jSONArray2 = jSONArray6;
                                str2 = str4;
                                str = str3;
                                pref2.saveWhoDeleteLikes(string2, string3, string4, jSONArray6.getJSONObject(i2).getString("profile_pic_url"), string);
                            } else {
                                jSONArray2 = jSONArray6;
                                str2 = str4;
                                str = str3;
                            }
                            i2++;
                            str4 = str2;
                            str3 = str;
                            jSONArray6 = jSONArray2;
                        }
                    }
                    String str5 = str4;
                    String str6 = str3;
                    if (!jSONArray7.toString().equals(jSONArray5.toString())) {
                        Log.i(this.tag, "saveWhoRemoveLikeOrComment2");
                        for (int i4 = 0; i4 < jSONArray7.length(); i4++) {
                            int i5 = 0;
                            while (true) {
                                if (i5 >= jSONArray5.length()) {
                                    break;
                                } else if (jSONArray7.getJSONObject(i4).getString(str5).equals(jSONArray5.getJSONObject(i5).getString(str5))) {
                                    break;
                                } else {
                                    i5++;
                                }
                            }
                            if (i5 == jSONArray5.length()) {
                                Log.i(this.tag, "saveWhoRemoveLikeOrComment3");
                                Log.i(this.tag, jSONArray7.getJSONObject(i4).getString(str6));
                                this.pref.saveWhoDeleteComments(jSONArray7.getJSONObject(i4).getString(str5), jSONArray7.getJSONObject(i4).getString("username"), jSONArray7.getJSONObject(i4).getString(str6), jSONArray7.getJSONObject(i4).getString("profile_pic_url"), string);
                            }
                        }
                        return;
                    }
                    return;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
