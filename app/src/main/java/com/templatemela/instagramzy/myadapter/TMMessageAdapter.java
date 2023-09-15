package com.templatemela.instagramzy.myadapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.models.TMMessageItemModel;
import com.templatemela.instagramzy.handeler.TMUserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TMMessageAdapter extends RecyclerView.Adapter<TMMessageAdapter.ViewHolder> {
    public static final int Msg_type_left = 0;
    public static final int Msg_type_right = 1;
    Context context;
    private int currentPlayingPosition;
    HashMap<String, String> dpMap;
    Handler handler;
    List<TMMessageItemModel> list;
    MediaPlayer mPlayer;
    int type = 0;
    TMUserDetails userDetails;
    String userId;

    public TMMessageAdapter(Context context2) {
        this.context = context2;
        TMUserDetails userDetails2 = new TMUserDetails(context2);
        this.userDetails = userDetails2;
        this.userId = userDetails2.getUser(false).getId();
        this.list = new ArrayList();
        this.handler = new Handler(Looper.getMainLooper());
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mPlayer = mediaPlayer;
        mediaPlayer.setAudioStreamType(3);
        this.dpMap = new HashMap<>();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.right_message_item, viewGroup, false));
        }
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.left_messge_item, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final TMMessageItemModel messageItemModel = this.list.get(i);
        try {
            if (this.userId.equals(messageItemModel.getUserid())) {
                viewHolder.dp.setVisibility(View.GONE);
            } else if (i == this.list.size() - 1) {
                Glide.with(this.context).load(this.dpMap.get(messageItemModel.getUserid())).into((ImageView) viewHolder.dpimage);
                viewHolder.dp.setVisibility(View.VISIBLE);
            } else if (!messageItemModel.getUserid().equals(this.list.get(i + 1).getUserid())) {
                Glide.with(this.context).load(this.dpMap.get(messageItemModel.getUserid())).into((ImageView) viewHolder.dpimage);
                viewHolder.dp.setVisibility(View.VISIBLE);
            } else {
                viewHolder.dp.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (messageItemModel.getType().equals("text")) {
            viewHolder.textView.setText(messageItemModel.getText());
            viewHolder.imageCard.setVisibility(View.GONE);
            viewHolder.textHolder.setVisibility(View.VISIBLE);
            viewHolder.voiceCard.setVisibility(View.GONE);
            viewHolder.videoCard.setVisibility(View.GONE);
            viewHolder.textHolder.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String text = messageItemModel.getText();
                    if (text.contains("http")) {
                        String substring = text.substring(text.indexOf("http"));
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(substring));
                        intent.setPackage("com.instagram.android");
                        TMMessageAdapter messageAdapter = TMMessageAdapter.this;
                        if (messageAdapter.isIntentAvailable(messageAdapter.context, intent)) {
                            TMMessageAdapter.this.context.startActivity(intent);
                        } else {
                            TMMessageAdapter.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(substring)));
                        }
                    }
                }
            });
        } else if (messageItemModel.getType().equals("image")) {
            Glide.with(this.context).load(messageItemModel.getUrl()).into(viewHolder.image);
            viewHolder.imageCard.setVisibility(View.VISIBLE);
            viewHolder.textHolder.setVisibility(View.GONE);
            viewHolder.videoCard.setVisibility(View.GONE);
            viewHolder.voiceCard.setVisibility(View.GONE);
        } else if (messageItemModel.getType().equals("video")) {
            Glide.with(this.context).load(messageItemModel.getUrl()).into(viewHolder.video);
            viewHolder.imageCard.setVisibility(View.GONE);
            viewHolder.textHolder.setVisibility(View.GONE);
            viewHolder.videoCard.setVisibility(View.VISIBLE);
            viewHolder.voiceCard.setVisibility(View.GONE);
            viewHolder.videoCard.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TMMessageAdapter.this.showVideoView(messageItemModel.getUrl());
                }
            });
        } else if (messageItemModel.getType().equals("animated_media")) {
            Glide.with(this.context).asGif().load(messageItemModel.getUrl()).into(viewHolder.image);
            viewHolder.imageCard.setVisibility(View.VISIBLE);
            viewHolder.textHolder.setVisibility(View.GONE);
            viewHolder.videoCard.setVisibility(View.GONE);
            viewHolder.voiceCard.setVisibility(View.GONE);
        } else if (messageItemModel.getType().equals("voice_media")) {
            viewHolder.voiceCard.setVisibility(View.VISIBLE);
            viewHolder.imageCard.setVisibility(View.GONE);
            viewHolder.textHolder.setVisibility(View.GONE);
            viewHolder.videoCard.setVisibility(View.GONE);
            viewHolder.pause.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TMMessageAdapter.this.mPlayer.pause();
                    TMMessageAdapter.this.mPlayer.reset();
                    viewHolder.seekBar.setProgress(0);
                    viewHolder.play.setVisibility(View.VISIBLE);
                    viewHolder.pause.setVisibility(View.GONE);
                }
            });
            viewHolder.play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        TMMessageAdapter.this.mPlayer.reset();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (TMMessageAdapter.this.mPlayer.isPlaying()) {
                            TMMessageAdapter.this.mPlayer.stop();
                        }
                        TMMessageAdapter.this.mPlayer.setDataSource(messageItemModel.getUrl());
                        TMMessageAdapter.this.mPlayer.prepare();
                        TMMessageAdapter.this.mPlayer.start();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    viewHolder.seekBar.setMax((int) ((long) TMMessageAdapter.this.mPlayer.getDuration()));
                    viewHolder.play.setVisibility(View.GONE);
                    viewHolder.pause.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        public void run() {
                            while (TMMessageAdapter.this.mPlayer != null && TMMessageAdapter.this.mPlayer.isPlaying()) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                TMMessageAdapter.this.handler.post(new Runnable() {
                                    public void run() {
                                        viewHolder.seekBar.setProgress(TMMessageAdapter.this.mPlayer.getCurrentPosition());
                                        if (!TMMessageAdapter.this.mPlayer.isPlaying()) {
                                            viewHolder.play.setVisibility(View.VISIBLE);
                                            viewHolder.pause.setVisibility(View.GONE);
                                            viewHolder.seekBar.setProgress(0);
                                        }
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    /* access modifiers changed from: package-private */
    public void showVideoView(String str) {
        Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.message_media_view);
        VideoView videoView = (VideoView) dialog.findViewById(R.id.video);
        MediaController mediaController = new MediaController(this.context);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(str));
        videoView.requestFocus();
        videoView.start();
        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView dp;
        ImageView image;
        ImageView dpimage;
        CardView imageCard;
        ImageView pause;
        ImageView play;
        SeekBar seekBar;
        LinearLayout textHolder;
        TextView textView;
        ImageView video;
        CardView videoCard;
        CardView voiceCard;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.text);
            this.imageCard = (CardView) view.findViewById(R.id.image_card);
            this.dpimage = (ImageView) view.findViewById(R.id.dpimage);
            this.videoCard = (CardView) view.findViewById(R.id.video_card);
            this.image = (ImageView) view.findViewById(R.id.image);
            this.video = (ImageView) view.findViewById(R.id.video);
            this.textHolder = (LinearLayout) view.findViewById(R.id.textView);
            this.voiceCard = (CardView) view.findViewById(R.id.audio_holder);
            this.play = (ImageView) view.findViewById(R.id.play);
            this.pause = (ImageView) view.findViewById(R.id.pause);
            this.seekBar = (SeekBar) view.findViewById(R.id.seekBar);
            if (TMMessageAdapter.this.type == 0) {
                this.dp = (CardView) view.findViewById(R.id.dp);
            }
        }
    }

    public int getItemViewType(int i) {
        if (this.list.get(i).getUserid().equals(this.userId)) {
            this.type = 1;
            return 1;
        }
        this.type = 0;
        return 0;
    }

    public void setList(List<TMMessageItemModel> list2) {
        this.list = list2;
        notifyDataSetChanged();
    }

    public void setDpMap(HashMap<String, String> hashMap) {
        this.dpMap = hashMap;
    }


    public boolean isIntentAvailable(Context context2, Intent intent) {
        return context2.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
