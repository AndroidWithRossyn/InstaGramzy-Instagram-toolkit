package com.templatemela.instagramzy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.templatemela.instagramzy.utils.Pref;
import com.templatemela.instagramzy.R;
import com.templatemela.instagramzy.utils.RateDialog;
import com.templatemela.instagramzy.models.TMStringParcebleModel;
import com.templatemela.instagramzy.handeler.TMFollowUnfollowHandler;
import com.templatemela.instagramzy.handeler.TMPersonDetails;
import java.util.List;

public class TMCancelSentRequestsActivity extends AppCompatActivity {
    TMFollowUnfollowHandler followUnfollow;
    TextView log;
    TMPersonDetails personDetails;
    Pref pref;
    int r = 0;
    RateDialog rateDialog;
    Boolean shouldStop = false;
    List<TMStringParcebleModel> slist;
    int status;
    Button stop;
    String t = "";
    Thread thread = new Thread(new Runnable() {
       @Override
       public void run() {
            for (final TMStringParcebleModel next : slist) {
                r++;
                runOnUiThread(new Runnable() {
                    public void run() {
                        setLog("\n");
                        
                        setLog("Cancelling request of @" + next.getData());
                    }
                });
                String personId = personDetails.getPersonId(next.getData());
                
                status = followUnfollow.changeFriendship(personId, next.getData(), 0);
                Log.i(NotificationCompat.CATEGORY_STATUS, status + "");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (status == 2) {
                            
                            setLog("Follow request of @" + next.getData() + " is successfully canceled");
                            pref.removeFromSentRequestList(next.getData());
                            rateDialog.increaseCount();
                        } else {
                            
                            setLog("Error while canceling follow request of @" + next.getData());
                        }
                        if (slist.size() == r) {
                            setLog("Nothing left\n");
                            stop.setText("Go to home");
                            return;
                        }
                        
                        setLog((slist.size() - r) + " left\n");
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                if (shouldStop.booleanValue()) {
                    return;
                }
            }
        }
    });

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cancel_sent_requests);
        log = (TextView) findViewById(R.id.log);
        stop = (Button) findViewById(R.id.stop);
        personDetails = new TMPersonDetails(this);
        followUnfollow = new TMFollowUnfollowHandler(this);
        pref = new Pref(this);
        rateDialog = new RateDialog(this);
        slist = (List) getIntent().getSerializableExtra("sList");
        for (TMStringParcebleModel data : slist) {
            Log.i("c_data", data.getData());
        }
        stop.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                shouldStop = true;
                startActivity(new Intent(TMCancelSentRequestsActivity.this, TMSentRequestActivity.class));
                finish();
            }
        });
        thread.start();
    }

    public void setLog(String str) {
        String str2 = str + "\n" + t;
        t = str2;
        try {
            log.setText(str2);
        } catch (Exception unused) {
        }
    }
}
