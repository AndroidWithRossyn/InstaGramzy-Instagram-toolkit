package com.templatemela.instagramzy.handeler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.templatemela.instagramzy.interfaces.HashTagInterface;
import com.templatemela.instagramzy.models.TMHashTagModel;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TMHashTagSearchHandeler {
    Context context;
    Functions functions;
    Handler handler;
    HashTagInterface hashTagInterface;
    String maxid = "";
    List<TMHashTagModel> relatedHshtagList;

    public TMHashTagSearchHandeler(Context context2) {
        context = context2;
        functions = new Functions(context2);
        hashTagInterface = (HashTagInterface) context2;
        handler = new Handler(Looper.getMainLooper());
    }

    public void getSearchResult(final List<String> list) {
        final ArrayList arrayList = new ArrayList();
        relatedHshtagList = new ArrayList();
        new Thread(new Runnable() {
            public void run() {
                for (String str : list) {
                    str.replaceAll("#", "");
                    str.replaceAll(" ", "");
                    String str2 = "https://i.instagram.com/api/v1/tags/search?q=" + str;
                    Log.i("HashTag", str2);
                    try {
                        JSONArray jSONArray = new JSONObject(functions.page(str2).text()).getJSONArray("results");
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            arrayList.add(new TMHashTagModel(jSONObject.getString("name"), jSONObject.getInt("media_count")));
                        }
                        handler.post(new Runnable() {
                            public void run() {
                                hashTagInterface.onSearchComplete(arrayList);
                            }
                        });
                        Thread.sleep(2000);
                       // generateRelatedList(str);
                    } catch (InterruptedException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    
    public void generateRelatedList(final String str) {
        new Thread(new Runnable() {
            public void run() {
                processRelatedHashtag(functions.getPostData("https://i.instagram.com/api/v1/tags/" + str + "/sections/"));
                if (!maxid.equals("")) {
                    Functions functions2 = functions;
                    processRelatedHashtag(functions2.getPostData("https://i.instagram.com/api/v1/tags/" + str + "/sections/?max_id=" + maxid));
                }
            }
        }).start();
    }

    
    public void processRelatedHashtag(String str) {
        while (str.contains("#")) {
            int indexOf = str.indexOf("#") + 1;
            int indexOf2 = str.indexOf(" ", indexOf);
            int indexOf3 = str.indexOf("\\n", indexOf);
            if (indexOf3 - indexOf < indexOf2 - indexOf) {
                indexOf2 = indexOf3;
            }
            try {
                putOnSelectedList(str.substring(indexOf, indexOf2));
                str = str.substring(indexOf2);
            } catch (Exception unused) {
            }
        }
        try {
            int lastIndexOf = str.lastIndexOf("max_id\":") + 9;
            String trim = str.substring(lastIndexOf, str.indexOf("\"", lastIndexOf)).trim();
            maxid = trim;
            Log.i("max_id", trim);
        } catch (Exception unused2) {
        }
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < relatedHshtagList.size(); i++) {
            if (relatedHshtagList.get(i).getPostCount() > 2) {
                arrayList.add(relatedHshtagList.get(i));
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                hashTagInterface.onRelatedListComplete(arrayList);
            }
        });
    }



    public void putOnSelectedList(String str) {
        if (!str.contains("\"") && !str.contains(":") && !str.contains("{") && !str.contains("}") && !str.contains("[") && !str.contains("]") && !str.contains("type") && !str.contains("false") && !str.contains("true")) {
            String decodedName = functions.getDecodedName(str);
            int i = 0;
            while (true) {
                if (i >= relatedHshtagList.size()) {
                    break;
                } else if (relatedHshtagList.get(i).getName().equals(decodedName)) {
                    TMHashTagModel hashTagModel = relatedHshtagList.get(i);
                    hashTagModel.setPostCount(hashTagModel.getPostCount() + 1);
                    relatedHshtagList.set(i, hashTagModel);
                    break;
                } else {
                    i++;
                }
            }
            if (i == relatedHshtagList.size()) {
                relatedHshtagList.add(new TMHashTagModel(decodedName, 1));
            }
        }
    }
}
