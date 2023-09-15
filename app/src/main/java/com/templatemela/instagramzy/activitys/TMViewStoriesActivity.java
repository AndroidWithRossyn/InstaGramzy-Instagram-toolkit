package com.templatemela.instagramzy.activitys;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import com.google.android.exoplayer2.ui.PlayerView;

import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.TMDownloadHelper;
import com.templatemela.instagramzy.models.TMStoryResModel;
import com.templatemela.instagramzy.handeler.TMStoryHandeler;

import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;
import java.util.Random;
import jp.shts.android.storiesprogressview.StoriesProgressView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class TMViewStoriesActivity extends AppCompatActivity {
    CardView cardView;
    int count;

    public int counter = 0;
    int d = 0;
    TMDownloadHelper downloadHelper;
    CircleImageView dp;
    LinearLayout emptyView;
    final GestureDetector gestureDetector1 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
     @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.i(tag, "Longpress detected");
            pause();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            Log.i(tag, "single press detected");
            preTap = true;
            pre();
            return super.onSingleTapConfirmed(motionEvent);
        }
    });
    final GestureDetector gestureDetector2 = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent motionEvent) {
            Log.i(tag, "Longpress detected");
            pause();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            Log.i(tag, "single press detected");
            nextTap = true;
            return super.onSingleTapConfirmed(motionEvent);
        }
    });
    private ImageView image;
    boolean isFirst = true;
    Boolean isOActivity;
    Boolean isPause = false;
    boolean isStarted = false;
    boolean isThreadRunning = false;
    List<TMStoryResModel> list;
    Player.Listener listener = new Player.Listener() {

        @Override
        public void onPlayerStateChanged(boolean z, int i) {
            if (i == 3 && !isStarted) {
                
                startTimer((int) player.getDuration());
                isStarted = true;
                Log.i(tag, "ready");
            }
        }
    };
    
    
    
    TextView name;
    Boolean nextTap = false;
    private View.OnTouchListener onTouchListener1 = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector1.onTouchEvent(motionEvent);
        }
    };
    private View.OnTouchListener onTouchListener2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector2.onTouchEvent(motionEvent);
        }
    };
    SimpleExoPlayer player;
    Boolean preTap = false;
    long stopPosition = 0;
    private StoriesProgressView storiesProgressView;
    RelativeLayout storiesView;
    TMStoryHandeler storyHandeler;
    String tag = "view_stories";
    int type = 0;
    String username;
    PlayerView v;

  @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.activity_view_stories);
        v = (PlayerView) findViewById(R.id.video_view);
        SimpleExoPlayer build = new SimpleExoPlayer.Builder(this).build();
        player = build;
        v.setPlayer(build);
        v.hideController();
        dp = (CircleImageView) findViewById(R.id.dp);
        name = (TextView) findViewById(R.id.name);
        storiesView = (RelativeLayout) findViewById(R.id.story_view);
        emptyView = (LinearLayout) findViewById(R.id.empty_list);
        cardView = (CardView) findViewById(R.id.card);
        Glide.with((FragmentActivity) this).load(getIntent().getStringExtra("dp")).into((ImageView) dp);
        String stringExtra = getIntent().getStringExtra("name");
        username = stringExtra;
        name.setText(stringExtra);
        type = getIntent().getIntExtra("type", 0);
        isOActivity = true;
        String stringExtra2 = getIntent().getStringExtra("id");
        storyHandeler = new TMStoryHandeler(this);
        downloadHelper = new TMDownloadHelper(this);
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        image = (ImageView) findViewById(R.id.image);
        View findViewById = findViewById(R.id.reverse);
        findViewById.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (isPause.booleanValue()) {
                    resume();
                }
            }
        });
        findViewById.setOnTouchListener(onTouchListener1);
        View findViewById2 = findViewById(R.id.skip);
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPause.booleanValue()) {
                    resume();
                }
            }
        });
        findViewById2.setOnTouchListener(onTouchListener2);
        storyHandeler.getStoriesDetails(stringExtra2, type);
        cardView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if (list.size() == 0) {
                    Toast.makeText(TMViewStoriesActivity.this, "List is not fetched yet", Toast.LENGTH_SHORT).show();
                    return;
                }
                String str = list.get(counter).getType() == 1 ? ".mp4" : ".jpg";
                String url = list.get(counter).getUrl();
                downloadHelper.startDownload(url, "story_of_" + username + new Random().nextInt() + str);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteListGeneration(List<TMStoryResModel> list2) {
        try {
            if (list2.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                storiesView.setVisibility(View.GONE);
            }
            list2.get(0).getUrl();
            list = list2;
            storiesProgressView.setStoriesCount(list2.size());
            playStory(0);
        } catch (Exception unused) {
        }
    }


    public void playStory(int i) {
        TMStoryResModel storyResModel = list.get(i);
        if (storyResModel.getType() == 0) {
            v.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(storyResModel.getUrl()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    startTimer(5000);
                    return false;
                }
            }).into(image);
            return;
        }
        image.setVisibility(View.GONE);
        v.setVisibility(View.VISIBLE);
        playvideo(storyResModel.getUrl());
    }

    public void playvideo(String str) {
        Log.e("entered", "playvide");
        Log.e("path is", "" + str);
        try {
            player.setMediaItem(MediaItem.fromUri(str));
            player.addListener(listener);
            player.prepare();
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startTimer(final int i) {
        new Thread(new Runnable() {
           @Override
            public void run() {
                while (isThreadRunning) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        startTimers(i);
                    }
                });
            }
        }).start();
    }


    public void startTimers(int i) {
        isThreadRunning = true;
        d = i;
        String str = tag;
        Log.i(str, "duration : " + i);
        storiesProgressView.setStoryDuration((long) i);
        if (isFirst) {
            storiesProgressView.startStories(counter);
        }
        isFirst = false;
        if (preTap.booleanValue()) {
            storiesProgressView.reverse();
        }
        if (nextTap.booleanValue()) {
            storiesProgressView.skip();
        }
        nextTap = false;
        preTap = false;
        isStarted = false;
        final int i2 = i / 200;
        count = 0;
        new Thread(new Runnable() {
           @Override
            public void run() {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isPause.booleanValue()) {
                        count++;
                    }
                    if (count <= i2 && !nextTap.booleanValue() && !preTap.booleanValue() && isOActivity.booleanValue()) {
                        Log.i(tag, "count : " + count);
                        isThreadRunning = true;
                    }
                    count = 0;
                    runOnUiThread(new Runnable() {
                    public void run() {
                        if (!preTap.booleanValue()) {
                            next();
                        }
                        isThreadRunning = false;
                    }
                });
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        storiesProgressView.destroy();
        isOActivity = false;
        player.release();
        super.onDestroy();
    }

    public void next() {
        if (counter >= list.size() - 1) {
            finish();
            return;
        }
        if (player.isPlaying()) {
            player.stop();
        }
        int i = counter + 1;
        counter = i;
        playStory(i);
    }

    public void pre() {
        if (counter <= 0) {
            finish();
            return;
        }
        if (player.isPlaying()) {
            player.stop();
        }
        int i = counter - 1;
        counter = i;
        playStory(i);
    }

    public void pause() {
        storiesProgressView.pause();
        isPause = true;
        stopPosition = player.getCurrentPosition();
        String str = tag;
        Log.i(str, "stop position " + stopPosition);
        player.pause();
    }


    public void resume() {
        isPause = false;
        player.seekTo(stopPosition);
        String str = tag;
        Log.i(str, "start position " + stopPosition);
        player.play();
        storiesProgressView.resume();
    }
    
    
}


