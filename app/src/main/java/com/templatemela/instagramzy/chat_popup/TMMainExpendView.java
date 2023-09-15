package com.templatemela.instagramzy.chat_popup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMChatListModel;
import com.templatemela.instagramzy.models.TMChatModel;
import com.templatemela.instagramzy.models.TMChatNotificationModel;
import com.templatemela.instagramzy.models.TMStrModel;
import com.templatemela.instagramzy.myadapter.TMChatAdapter;
import com.templatemela.instagramzy.handeler.TMChatListHandeler;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import java.util.HashMap;
import java.util.LinkedList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TMMainExpendView {
    TextView autoReply;
    TMChatAdapter chatAdapter;
    TextView chatHead;
    LinkedList<TMChatModel> chatList;
    TMChatListHandeler chatListHandeler;
    Context context;
    TextView count;
    LinearLayout countHolder;
    ImageView dp;
    Functions functions;
    HashMap<String, Integer> hashMap;
    boolean isFetcting = false;
    LayoutInflater layoutInflater;
    LinearLayout linearLayout;
    LinearLayout logInReq;
    TextView name;
    LinearLayout noInternet;
    LinearLayout permission;
    Switch permissionTaker;
    Pref pref;
    ProgressBar progressBar;
    public RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    String tag = "Message";
    TMUserDetails userDetails;

    public TMMainExpendView(LayoutInflater layoutInflater2, Context context2) {
        this.layoutInflater = layoutInflater2;
        this.context = context2;
    }

    public View getView() {
        View inflate = this.layoutInflater.inflate(R.layout.chat_head_message_view, (ViewGroup) null);
        EventBus.getDefault().register(this);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recyler_view);
        this.swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeContainer);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progress_circular);
        LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.loading);
        this.linearLayout = linearLayout2;
        linearLayout2.setVisibility(View.VISIBLE);
        this.permission = (LinearLayout) inflate.findViewById(R.id.permission);
        this.permissionTaker = (Switch) inflate.findViewById(R.id.switcha);
        this.noInternet = (LinearLayout) inflate.findViewById(R.id.no_internet);
        this.logInReq = (LinearLayout) inflate.findViewById(R.id.login_re);
        this.functions = new Functions(this.context);
        this.userDetails = new TMUserDetails(this.context);
        this.dp = (ImageView) inflate.findViewById(R.id.dp);
        this.name = (TextView) inflate.findViewById(R.id.name);
        Glide.with(this.context).load(this.userDetails.getUser(false).getDp()).into((ImageView) this.dp);
        this.name.setText("Chats");
        this.chatAdapter = new TMChatAdapter(this.context);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.setAdapter(this.chatAdapter);
        this.pref = new Pref(this.context);
        this.hashMap = new HashMap<>();
        this.chatListHandeler = new TMChatListHandeler(this.context);
        this.chatList = new LinkedList<>();
        if (!this.functions.internetIsConnected()) {
            this.logInReq.setVisibility(View.GONE);
            this.noInternet.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.GONE);
            this.linearLayout.setVisibility(View.GONE);
        } else if (this.userDetails.isLoggedIn()) {
            this.isFetcting = true;
            this.chatListHandeler.fetchChatList("");
        } else {
            this.logInReq.setVisibility(View.VISIBLE);
            this.noInternet.setVisibility(View.GONE);
            this.progressBar.setVisibility(View.GONE);
            this.linearLayout.setVisibility(View.GONE);
        }
        this.swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                TMMainExpendView.this.chatListHandeler.fetchChatList("");
            }
        });
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(TMChatNotificationModel chatNotificationModel) {
        if (!chatNotificationModel.getUsername().equals("")) {
            for (int i = 0; i < this.chatList.size(); i++) {
                if (chatNotificationModel.getUsername().equals(this.chatList.get(i).getUsername())) {
                    insertMsg(i, chatNotificationModel);
                    return;
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(TMStrModel str) {
        if (str.getType() == 99) {
            this.chatAdapter.setList(this.chatList);
        }
    }

    
    public void insertMsg(int i, TMChatNotificationModel chatNotificationModel) {
        TMChatModel chatModel = this.chatList.get(i);
        chatModel.setLastMessage(chatNotificationModel.getMsg());
        chatModel.setSeen(false);
        chatModel.setTime(System.currentTimeMillis());
        this.chatList.remove(i);
        this.chatList.addFirst(chatModel);
        this.chatAdapter.setList(this.chatList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComplete(final TMChatListModel chatListModel) {
        this.pref = new Pref(this.context);
        this.progressBar.setVisibility(View.GONE);
        this.linearLayout.setVisibility(View.GONE);
        this.swipeRefreshLayout.setRefreshing(false);
        this.hashMap = this.pref.getUnseenCount();
        Log.i(this.tag, new Gson().toJson((Object) this.hashMap));
        if (!chatListModel.isShouldAdd()) {
            this.chatList.clear();
        }
        Log.i(this.tag, "list got");
        for (TMChatModel next : chatListModel.getList()) {
            if (next.isSeen()) {
                try {
                    this.hashMap.put(next.getUsername(), 0);
                } catch (Exception unused) {
                }
            }
            this.chatList.add(next);
        }
        this.pref.saveUnseenCount(this.hashMap);
        this.chatAdapter.setList(this.chatList);
        this.pref.saveChatList(this.chatList);
        if (!chatListModel.getCursor().equals("f")) {
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    super.onScrollStateChanged(recyclerView, i);
                    if (!recyclerView.canScrollVertically(1) && i == 0) {
                        Log.d("-----......", "end");
                        recyclerView.removeOnScrollListener(this);
                        TMMainExpendView.this.progressBar.setVisibility(View.VISIBLE);
                        TMMainExpendView.this.chatListHandeler.fetchChatList(chatListModel.getCursor());
                    }
                }
            });
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
