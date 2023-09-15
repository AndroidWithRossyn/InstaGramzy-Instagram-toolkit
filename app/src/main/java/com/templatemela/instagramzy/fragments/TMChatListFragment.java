package com.templatemela.instagramzy.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatListModel;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMChatNotificationModel;
import com.templatemela.instagramzy.models.TMStrModel;

import com.templatemela.instagramzy.myadapter.TMChatAdapter;

import com.templatemela.instagramzy.chat_popup.TMFloatingWidgetService;
import com.templatemela.instagramzy.handeler.TMChatListHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;
import java.util.HashMap;
import java.util.LinkedList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMChatListFragment extends Fragment {
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;
    int a = 0;
    TextView autoReply;
    TMChatAdapter chatAdapter;
    FloatingActionButton chatHead;
    LinkedList<TMChatModel> chatList;
    TMChatListHandeler chatListHandeler;
    Functions functions;
    HashMap<String, Integer> hashMap;
    boolean isFetcting = false;
    boolean isNotificationPermissonEnabled = false;
    LinearLayout linearLayout;
    LinearLayout logInReq;
    LinearLayout noInternet;
    LinearLayout permission;
    Switch permissionTaker;
    Pref pref;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    String tag = "Message";
    TMUserDetails userDetails;



    public void Message()
    {}





    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        EventBus.getDefault().register(this);
        View inflate = layoutInflater.inflate(R.layout.message_fragment, viewGroup, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeContainer);
        progressBar = (ProgressBar) inflate.findViewById(R.id.progress_circular);
         linearLayout = (LinearLayout) inflate.findViewById(R.id.loading);
         linearLayout.setVisibility(View.VISIBLE);
        permission = (LinearLayout) inflate.findViewById(R.id.permission);
        permissionTaker = (Switch) inflate.findViewById(R.id.switcha);
        autoReply = (TextView) inflate.findViewById(R.id.auto_reply);
        chatHead = (FloatingActionButton) inflate.findViewById(R.id.chat_head);
        noInternet = (LinearLayout) inflate.findViewById(R.id.no_internet);
        logInReq = (LinearLayout) inflate.findViewById(R.id.login_re);
        functions = new Functions(getContext());
        userDetails = new TMUserDetails(getContext());
        chatAdapter = new TMChatAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);
        chatListHandeler = new TMChatListHandeler(getContext());
        chatList = new LinkedList<>();
        if (isMyServiceRunning(getActivity(), TMFloatingWidgetService.class)){
            chatHead.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.heart)));
            chatHead.setImageResource(R.drawable.ic_chat_close);
        }
        else{
            chatHead.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_chat)));
            chatHead.setImageResource(R.drawable.ic_chat_open);
        }

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        permissionTaker.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

        chatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(tag, pref.isPremium() + "");
                Log.i(tag, pref.isFTE() + "");
                if (pref.isPremium()) {
                    createFloatingWidget();
                }  else {
                    createFloatingWidget();
                }
            }
        });
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(TMChatNotificationModel chatNotificationModel) {
        int i = 0;
        if (!chatNotificationModel.getUsername().equals("")) {
            while (i < chatList.size()) {
                if (chatNotificationModel.getUsername().equals(chatList.get(i).getUsername())) {
                    insertMsg(i, chatNotificationModel);
                    return;
                }
                i++;
            }
            return;
        }
        while (i < chatList.size()) {
            if (chatNotificationModel.getName().equals(chatList.get(i).getName())) {
                insertMsg(i, chatNotificationModel);
                return;
            }
            i++;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(TMStrModel str) {
        if (str.getType() == 99) {
            chatAdapter.setList(chatList);
        }
    }

    
    public void insertMsg(int i, TMChatNotificationModel chatNotificationModel) {
        TMChatModel chatModel = chatList.get(i);
        chatModel.setLastMessage(chatNotificationModel.getMsg());
        chatModel.setSeen(false);
        chatModel.setTime(System.currentTimeMillis());
        chatList.remove(i);
        chatList.addFirst(chatModel);
        chatAdapter.setList(chatList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(final TMChatListModel chatListModel) {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if (!chatListModel.isShouldAdd()) {
            chatList.clear();
        }
        Log.i(tag, "list got");
        for (TMChatModel next : chatListModel.getList()) {
            if (next.isSeen()) {
                try {
                    hashMap.put(next.getUsername(), 0);
                } catch (Exception unused) {
                }
            }
            chatList.add(next);
        }
        pref.saveUnseenCount(hashMap);
        chatAdapter.setList(chatList);
        pref.saveChatList(chatList);
        if (!chatListModel.getCursor().equals("f")) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (!recyclerView.canScrollVertically(1) && i == 0) {
                        Log.d("-----......", "end");
                        recyclerView.removeOnScrollListener(this);
                        progressBar.setVisibility(View.VISIBLE);
                        chatListHandeler.fetchChatList(chatListModel.getCursor());
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(tag, "on pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(tag, "on resume");
        pref = new Pref(getContext());
        hashMap = pref.getUnseenCount();
        chatAdapter.notifyChange();
        String string = Settings.Secure.getString(getContext().getContentResolver(), "enabled_notification_listeners");
        String packageName = getContext().getPackageName();

        permission.setVisibility(View.GONE);
            if (functions.internetIsConnected()) {
                noInternet.setVisibility(View.GONE);
                if (!userDetails.isLoggedIn()) {
                    logInReq.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    permission.setVisibility(View.GONE);
                } else if (a == 0) {
                    chatListHandeler.fetchChatList("");
                    linearLayout.setVisibility(View.VISIBLE);
                    a = 1;
                }
            } else {
                logInReq.setVisibility(View.GONE);
                noInternet.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                permission.setVisibility(View.GONE);
            }
            recyclerView.setVisibility(View.VISIBLE);
            isNotificationPermissonEnabled = true;
            permissionTaker.setChecked(true);

        if (a == 1) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    chatListHandeler.fetchChatList("");
                }
            });
        }
    }


    public void createFloatingWidget() {
        if (!userDetails.isLoggedIn()) {
            Toast.makeText(getContext(), "Please login with your instagram account", Toast.LENGTH_LONG).show();
        } else if (Build.VERSION.SDK_INT < 23) {
            startFloatingWidgetService();
        } else if (!Settings.canDrawOverlays(getContext())) {
            new AlertDialog.Builder(getContext()).setTitle("Permission Needed").setMessage("Enable overlay permission").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (isNotificationPermissonEnabled) {
                        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getContext().getPackageName())), TMChatListFragment.DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
                        return;
                    }
                    Toast.makeText(getContext(), "Enable notification permission", Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton(android.R.string.no, (DialogInterface.OnClickListener) null).setIcon(android.R.drawable.ic_dialog_alert).show();
        } else if (isNotificationPermissonEnabled) {
            startFloatingWidgetService();
        } else {
            Toast.makeText(getContext(), "Enable notification permission", Toast.LENGTH_LONG).show();
        }
    }

    private void startFloatingWidgetService() {
        if(isMyServiceRunning(getActivity(), TMFloatingWidgetService.class)){

            chatHead.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.heart)));
            chatHead.setImageResource(R.drawable.ic_chat_close);
           getActivity().stopService(new Intent(getContext(), TMFloatingWidgetService.class));
       }
       else {


            chatHead.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_chat)));
            chatHead.setImageResource(R.drawable.ic_chat_open);
           getContext().startService(new Intent(getContext(), TMFloatingWidgetService.class));
       }

    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            startFloatingWidgetService();
        }
    }



    public static boolean isMyServiceRunning(Activity activity, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
