package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.utils.WebViewDialog;
import com.templatemela.instagramzy.interfaces.FriendShipChangedInterface;
import com.templatemela.instagramzy.models.TMPersonModel;
import com.templatemela.instagramzy.handeler.TMFollowUnfollowHandler;

import java.util.List;

public class TMFollowerAdapter extends RecyclerView.Adapter<TMFollowerAdapter.ViewHolder> {
    Context context;
    TMFollowUnfollowHandler followUnfollow;
    FriendShipChangedInterface friendShipChangedInterface;
    Handler handler;
    List<TMPersonModel> list;
    List<TMPersonModel> mainList;
    Pref pref;
    RateDialog rateDialog;
    int screen;
    WebViewDialog webviewDialog;

    public TMFollowerAdapter(Context context2, List<TMPersonModel> list2, List<TMPersonModel> list3, int i) {
        context = context2;
        list = list2;
        mainList = list3;
        followUnfollow = new TMFollowUnfollowHandler(context2);
        handler = new Handler(context2.getMainLooper());
        pref = new Pref(context2);
        friendShipChangedInterface = (FriendShipChangedInterface) context2;
        screen = i;
        webviewDialog = new WebViewDialog(context2);
        rateDialog = new RateDialog(context2);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final TMPersonModel person = list.get(i);
        Glide.with(context).load(person.getDp()).into((ImageView) viewHolder.dpCircleImageView);
        viewHolder.name.setText(person.getName());
        viewHolder.username.setText(person.getUsername());
        int i2 = screen;
        if (i2 == 1 || i2 == 5 || i2 == 4) {
            viewHolder.button.setVisibility(View.GONE);
            viewHolder.newBudge.setVisibility(View.GONE);
            if (screen != 1) {
                if (pref.getFollowerListUpdateTime() - person.getTime() < 3600000 || pref.getFollowerListUpdateTime() - person.getTime() < -3600000) {
                    viewHolder.newBudge.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.newBudge.setVisibility(View.GONE);
                }
            }
        } else if (person.getFollowedByMe().booleanValue()) {
            viewHolder.button.setText("Unfollow");
        } else {
            viewHolder.button.setText("Follow");
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int i;
                        if (viewHolder.button.getText().toString().toLowerCase().equals("unfollow")) {
                            handler.post(new Runnable() {
                                public void run() {
                                    viewHolder.button.setText("wait");
                                }
                            });
                            i = followUnfollow.changeFriendship(person.getId(), person.getUsername(), 0);
                            if (i == 2) {
                                changeLocal(person, i, false);
                            }
                        } else if (viewHolder.button.getText().toString().toLowerCase().equals("follow")) {
                            handler.post(new Runnable() {
                                public void run() {
                                    viewHolder.button.setText("wait");
                                }
                            });
                            i = followUnfollow.changeFriendship(person.getId(), person.getUsername(), 1);
                            if (i == 2) {
                                changeLocal(person, i, true);
                            } else if (i == 1) {
                                viewHolder.button.setText("requested");
                            }
                        } else {
                            i = followUnfollow.changeFriendship(person.getId(), person.getUsername(), 0);
                            if (i == 2) {
                                changeLocal(person, i, false);
                            }
                        }
                        handler.post(new Runnable() {
                            public void run() {
                                if (i == 0) {
                                    friendShipChangedInterface.onFriendShipChangedError();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        viewHolder.itemC.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                webviewDialog.showWebView("https://www.instagram.com/" + person.getUsername() + "/");
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return list.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView button;
        ImageView dpCircleImageView;
        LinearLayout itemC;
        TextView name;
        LinearLayout newBudge;
        TextView username;

        public ViewHolder(View view) {
            super(view);
            dpCircleImageView = (ImageView) view.findViewById(R.id.dp);
            username = (TextView) view.findViewById(R.id.usernameTv);
            name = (TextView) view.findViewById(R.id.nameTv);
            button = (TextView) view.findViewById(R.id.button);
            itemC = (LinearLayout) view.findViewById(R.id.itemv);
            newBudge = (LinearLayout) view.findViewById(R.id.new_budge);
        }
    }

   
    public void changeLocal(final TMPersonModel person, int i, Boolean bool) {
        person.setFollowedByMe(bool);
        list.set(i, person);
        handler.post(new Runnable() {
            public void run() {
                notifyDataSetChanged();
                friendShipChangedInterface.onFriendShipSuccessfullyChanged(person);
            }
        });
    }

    public void setList(List<TMPersonModel> list2) {
        list = list2;
    }

    public void setMainList(List<TMPersonModel> list2) {
        mainList = list2;
    }
}
