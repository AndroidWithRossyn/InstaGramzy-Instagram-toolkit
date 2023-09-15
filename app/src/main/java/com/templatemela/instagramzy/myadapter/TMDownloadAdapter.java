package com.templatemela.instagramzy.myadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.exoplayer2.util.MimeTypes;
import com.templatemela.instagramzy.handeler.Functions;
import com.templatemela.instagramzy.R;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.Status;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.LinkedList;

public class TMDownloadAdapter extends RecyclerView.Adapter<TMDownloadAdapter.ViewHolder> {
    Context context;
    LinkedList<Download> downloads;
    Fetch fetch;
    Functions functions;
    Handler handler;

    public TMDownloadAdapter(LinkedList<Download> linkedList, Context context2, Fetch fetch2) {
        downloads = linkedList;
        context = context2;
        fetch = fetch2;
        handler = new Handler(context2.getMainLooper());
        functions = new Functions(context2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.download_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Download download = downloads.get(i);
        Log.i(NotificationCompat.CATEGORY_STATUS, download.getStatus() + "");
        if (download.getStatus() == Status.DOWNLOADING || download.getStatus() == Status.PAUSED) {
            viewHolder.downloadedLayout.setVisibility(View.GONE);
            viewHolder.downloadingLayout.setVisibility(View.VISIBLE);
            ((RequestBuilder) Glide.with(context).load(download.getUrl()).error((int) R.drawable.ic_baseline_movie_24)).into(viewHolder.thum);
        } else {
            viewHolder.downloadingLayout.setVisibility(View.GONE);
            viewHolder.downloadedLayout.setVisibility(View.VISIBLE);
            ((RequestBuilder) Glide.with(context).load(download.getFile()).error((int) R.drawable.ic_baseline_movie_24)).into(viewHolder.thum);
            TextView textView = viewHolder.status;
            textView.setText(download.getStatus() + "");
        }
        if (download.getStatus() == Status.DOWNLOADING) {
            viewHolder.pause.setVisibility(View.VISIBLE);
            viewHolder.play.setVisibility(View.GONE);
        }
        if (download.getStatus() == Status.PAUSED) {
            viewHolder.play.setVisibility(View.VISIBLE);
            viewHolder.pause.setVisibility(View.GONE);
        }
        if (download.getStatus() == Status.FAILED) {
            viewHolder.play.setVisibility(View.GONE);
            viewHolder.pause.setVisibility(View.GONE);
            viewHolder.share.setVisibility(View.GONE);
            viewHolder.retry.setVisibility(View.VISIBLE);
        }
        if (download.getStatus() == Status.COMPLETED) {
            viewHolder.play.setVisibility(View.GONE);
            viewHolder.pause.setVisibility(View.GONE);
            viewHolder.share.setVisibility(View.VISIBLE);
            viewHolder.retry.setVisibility(View.GONE);
        }
        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch.resume(download.getId());
            }
        });
        viewHolder.pause.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                fetch.pause(download.getId());
            }
        });
        viewHolder.delete1.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                fetch.delete(download.getId());
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                fetch.delete(download.getId());
            }
        });
        viewHolder.retry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fetch.retry(download.getId());
                notifyDataSetChanged();
            }
        });
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
                shareFile(new File(download.getFile()));
            }
        });
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(download.getFile()), getFileType(download.getFile()));
                    context.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(context, "No App found to open this file", 0).show();
                }
            }
        });
        viewHolder.progressBar.setProgress(download.getProgress());
        TextView textView2 = viewHolder.progressText;
        textView2.setText(download.getProgress() + "%");
        viewHolder.title.setText(getFileName(download));
    }

    @Override
    public int getItemCount() {
        return downloads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardView;
        ImageView delete;
        LinearLayout delete1;
        LinearLayout downloadedLayout;
        LinearLayout downloadingLayout;
        ImageView pause;
        ImageView play;
        ProgressBar progressBar;
        TextView progressText;
        ImageView retry;
        LinearLayout share;
        TextView status;
        ImageView thum;
        TextView title;
        public ViewHolder(View view) {
            super(view);
            thum = (ImageView) view.findViewById(R.id.thum);
            play = (ImageView) view.findViewById(R.id.play);
            pause = (ImageView) view.findViewById(R.id.pause);
            delete = (ImageView) view.findViewById(R.id.delete);
            delete1 = (LinearLayout) view.findViewById(R.id.delete1);
            share = (LinearLayout) view.findViewById(R.id.share);
            title = (TextView) view.findViewById(R.id.title);
            progressText = (TextView) view.findViewById(R.id.progressText);
            progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
            downloadedLayout = (LinearLayout) view.findViewById(R.id.downloaded_layout);
            downloadingLayout = (LinearLayout) view.findViewById(R.id.downloading_layout);
            retry = (ImageView) view.findViewById(R.id.retry);
            status = (TextView) view.findViewById(R.id.status);
            cardView = (LinearLayout) view.findViewById(R.id.card);
        }
    }

    public void setDownloads(LinkedList<Download> linkedList) {
        downloads = linkedList;
    }

   
    public void shareFile(File file) {
        String str = functions.getParentDir() + ".share" + File.separator;
        String substring = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
        try {
            File file2 = new File(str);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(str + "aaa" + substring);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("tag", e.getMessage());
        } catch (Exception e2) {
            Log.e("tag", e2.getMessage());
        }
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(URLConnection.guessContentTypeFromName("aaa" + substring));
        intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + str + "aaa" + substring));
        context.startActivity(Intent.createChooser(intent, "Share File"));
    }

    
    public String getFileName(Download download) {
        return download.getFile().replace(functions.getParentDir() + "downloads/", "");
    }

    
    public String getFileType(String str) {
        return str.substring(str.lastIndexOf(".") + 1).equals("jpg") ? "image/jpg" : MimeTypes.VIDEO_MP4;
    }
}
