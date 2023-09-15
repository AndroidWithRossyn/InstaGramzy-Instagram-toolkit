package com.templatemela.instagramzy.handeler;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Toast;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.common.net.HttpHeaders;
import com.templatemela.instagramzy.common.TMLoginRequiredDialog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Functions {
    Context context;
    int cookieAccessCount = 0;
    Handler handler;
    TMLoginRequiredDialog loginRequired;
    String ua;

    public Functions(Context context2) {
        this.context = context2;
        try {
            this.ua = new WebView(context2).getSettings().getUserAgentString();
        } catch (Exception unused) {
            this.ua = "";
        }
        this.loginRequired = new TMLoginRequiredDialog(context2);
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static String generateUserAgent() {
        String format = String.format("%s Android (%s/%s; %s; %s; %s; %s; %s; %s; %s_%s ;%s)", new Object[]{"Instagram 107.0.0.27.121", Build.VERSION.RELEASE, Build.VERSION.SDK, 320 + "dpi", 720 + "x" + 1280, Build.MODEL, Build.MANUFACTURER, Build.BRAND, Build.DEVICE, Locale.getDefault().getLanguage(), Locale.getDefault().getCountry(), 38});
        Log.i("useragent", format);
        return format;
    }

    public String getUserAgent() {
        return this.ua;
    }

    public String getCookie() {
        try {
            String cookie = CookieManager.getInstance().getCookie("https://www.instagram.com");
            Log.i("cookies", cookie);
            return cookie;
        } catch (NullPointerException | StringIndexOutOfBoundsException unused) {
            return "null";
        }
    }

    public String getCSRF() {
        String cookie = getCookie();
        int indexOf = cookie.indexOf("csrftoken") + 10;
        return cookie.substring(indexOf, cookie.indexOf(";", indexOf));
    }

    public String getUserId() {
        try {
            String cookie = getCookie();
            int indexOf = cookie.indexOf("ds_user_id") + 11;
            return cookie.substring(indexOf, cookie.indexOf(";", indexOf));
        } catch (Exception unused) {
            return "null";
        }
    }

    public class GetSourceCode implements Callable<Document> {
        String url;

        public GetSourceCode(String str) {
            this.url = str;
        }

        public Document call() throws Exception {
            HashMap hashMap = new HashMap();
            Log.i("Like", "Calling from server");
            if (this.url.contains("www")) {
                hashMap.put("User-Agent", Functions.this.ua);
                hashMap.put("X-Instagram-AJAX", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
                hashMap.put("X-CSRFToken", Functions.this.getCSRF());
                hashMap.put(HttpHeaders.REFERER, "https://www.instagram.com/");
                hashMap.put("Connection", "keep-alive");
                hashMap.put("Proxy-Connection", "keep-alive");
                hashMap.put("Accept", "/");
                hashMap.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US");
                hashMap.put("cookie", Functions.this.getCookie());
                hashMap.put("x-ig-app-id", "936619743392459");
            } else {
                hashMap.put("User-Agent", Functions.generateUserAgent());
                hashMap.put("Connection", "close");
                hashMap.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US");
                hashMap.put(HttpHeaders.REFERER, "https://www.instagram.com/");
                hashMap.put(HttpHeaders.ORIGIN, "https://www.instagram.com");
                hashMap.put("Accept", "*/*");
                hashMap.put("X-IG-Capabilities", "3QI=");
                hashMap.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
                hashMap.put("cookie", Functions.this.getCookie());
                hashMap.put("X-IG-App-ID", "936619743392459");
                hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
            }
            try {
                return Jsoup.connect(this.url).ignoreContentType(true).headers(hashMap).get();
            } catch (Exception e) {
                System.out.println(e);
                Functions.this.loginRequired.showDialog();
                return Jsoup.connect("https://www.google.com").get();
            }
        }
    }

    public Document page(String str) {
        try {
            return (Document) Executors.newSingleThreadExecutor().submit(new GetSourceCode(str)).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class PostRequest implements Callable<String> {
        String q;

        public PostRequest(String str) {
            this.q = str;
        }

        public String call() throws Exception {
            HashMap hashMap = new HashMap();
            hashMap.put("User-Agent", Functions.generateUserAgent());
            hashMap.put("Connection", "close");
            hashMap.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US");
            hashMap.put("Accept", "*/*");
            hashMap.put("X-IG-Capabilities", "3QI=");
            hashMap.put("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            hashMap.put("cookie", Functions.this.getCookie());
            hashMap.put("X-IG-App-ID", "936619743392459");
            hashMap.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
            try {
                String body = Jsoup.connect(this.q).userAgent(Functions.this.ua).headers(hashMap).ignoreContentType(true).method(Connection.Method.POST).execute().body();
                Log.i("post_data", body);
                return body;
            } catch (Exception e) {
                Log.i("post_error", e.toString());
                e.printStackTrace();
                return null;
            }
        }
    }

    public String getPostData(String str) {
        try {
            return (String) Executors.newSingleThreadExecutor().submit(new PostRequest(str)).get();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isInternetOn() {
        try {
            Jsoup.connect("https://google.com").get();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getDecodedName(String str) {
        try {
            Matcher matcher = Pattern.compile("\\\\u([0-9a-f]{4})").matcher(str);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(stringBuffer, String.valueOf((char) Integer.parseInt(matcher.group(1), 16)));
            }
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public void printBigData(String str, String str2) {
        int length = str2.length();
        while (length > 0) {
            if (length > 100) {
                Log.i(str, str2.substring(0, 100));
                str2 = str2.substring(100);
                length = str2.length();
            } else {
                Log.i(str, str2);
                return;
            }
        }
    }

    public String withSuffix(long j) {
        if (j < 1000) {
            return "" + j;
        }
        double d = (double) j;
        int log = (int) (Math.log(d) / Math.log(1000.0d));
        return String.format("%.1f %context", new Object[]{Double.valueOf(d / Math.pow(1000.0d, (double) log)), Character.valueOf("kMBTPE".charAt(log - 1))});
    }


    public static String prettyCount(long number) {
        DecimalFormat df = new DecimalFormat("#.#");
        String numberString = "";

        if (Math.abs(number / 1000000) >= 1) {
            numberString = df.format(number / 1000000.0) + "m";

        } else if (Math.abs(number / 1000.0) >= 1) {
            numberString = df.format(number / 1000.0) + "k";

        } else {
            numberString =String.valueOf(number) ;

        }
        return numberString;
    }

    public String humanReadableByteCountBin(long j) {
        long abs = j == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(j);
        if (abs < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return j + " B";
        }
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("KMGTPE");
        int i = 40;
        long j2 = abs;
        while (i >= 0 && abs > (115286520 >> i)) {
            j2 >>= 10;
            stringCharacterIterator.next();
            i -= 10;
        }
        return String.format("%.1f %ciB", new Object[]{Double.valueOf(((double) (j2 * ((long) Long.signum(j)))) / 1024.0d), Character.valueOf(stringCharacterIterator.current())});
    }

    public String getFormatedTime(int i) {
        return (i / 60) + " min " + (i % 60) + " s";
    }

    public String readFromAssets(String str) {

        Log.e("file name======",str);
        String str2;
        try {
            InputStream open = this.context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            str2 = new String(bArr);
        } catch (IOException e) {
            e.printStackTrace();
            str2 = "";
        }
        return decode(str2);
    }
    public String readFromAssetsJson(String str) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(str);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String decode(String str) {
        return new String(Base64.decode(str, 0), StandardCharsets.UTF_8);
    }

    public String getStringForNumber(int i) {
        String str = "";
        while (i > 0) {
            int i2 = i % 10;
            i /= 10;
            str = ((i2 < 0 || i2 >= 27) ? null : String.valueOf((char) (i2 + 65))) + str;
        }
        return str.toLowerCase();

    }

    public void copy(String str) {
        ((ClipboardManager) this.context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", str));
        Toast.makeText(this.context, "Copied", Toast.LENGTH_SHORT).show();
    }

    public int getColor(int i) {
        return Color.parseColor(new String[]{"#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79f", "#fcf3cf", "#d5f5e3", "#abebc6", "#d6eaf8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f5eef8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f9e79f", "#fdf2e9", "#fcf3cf", "#ebf5fb", "#fdedec", "#f9ebea", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79f", "#fcf3cf", "#fdedec", "#f9ebea", "#d2b4de", "#d6eaf8", "#d1f2eb", "#fadbd8", "#f4ecf7", "#fdedec", "#f9ebea", "#fdebd0", "#f9e79", "#fcf3cf", "#d5f5e3", "#abebc6", "#d6eaf8", "#d5f5e3", "#fdebd0", "#fcf3cf", "#f9e79f", "#aeb6bf", "#d6dbdf"}[i % 68]);
    }

    public String getParentDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Unfollowers for instagram" + File.separator;
    }

    public String getDateTime() {
        Date time = Calendar.getInstance().getTime();
        PrintStream printStream = System.out;
        printStream.println("Current time => " + time);
        return new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss", Locale.getDefault()).format(time);
    }

    public String toTimeAgo(long j) {
        String str;
        long currentTimeMillis = System.currentTimeMillis() - j;
        List asList = Arrays.asList(new String[]{"year", "month", "day", "hour", "minute", "second"});
        int i = 0;
        List asList2 = Arrays.asList(new Long[]{Long.valueOf(TimeUnit.DAYS.toMillis(365)), Long.valueOf(TimeUnit.DAYS.toMillis(30)), Long.valueOf(TimeUnit.DAYS.toMillis(1)), Long.valueOf(TimeUnit.HOURS.toMillis(1)), Long.valueOf(TimeUnit.MINUTES.toMillis(1)), Long.valueOf(TimeUnit.SECONDS.toMillis(1))});
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (i >= asList2.size()) {
                break;
            }
            long longValue = currentTimeMillis / ((Long) asList2.get(i)).longValue();
            if (longValue > 0) {
                stringBuffer.append(longValue);
                stringBuffer.append(" ");
                stringBuffer.append((String) asList.get(i));
                if (longValue != 1) {
                    str = "s";
                } else {
                    str = "";
                }
                stringBuffer.append(str);
                stringBuffer.append(" ago");
            } else {
                i++;
            }
        }
        if ("".equals(stringBuffer.toString())) {
            return "0 seconds ago";
        }
        return stringBuffer.toString();
    }

    public String toTAgo(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        List asList = Arrays.asList(new String[]{"y", "m", "d", "h", "m", "s"});
        int i = 0;
        List asList2 = Arrays.asList(new Long[]{Long.valueOf(TimeUnit.DAYS.toMillis(365)), Long.valueOf(TimeUnit.DAYS.toMillis(30)), Long.valueOf(TimeUnit.DAYS.toMillis(1)), Long.valueOf(TimeUnit.HOURS.toMillis(1)), Long.valueOf(TimeUnit.MINUTES.toMillis(1)), Long.valueOf(TimeUnit.SECONDS.toMillis(1))});
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            if (i >= asList2.size()) {
                break;
            }
            long longValue = currentTimeMillis / ((Long) asList2.get(i)).longValue();
            if (longValue > 0) {
                stringBuffer.append(longValue);
                stringBuffer.append(" ");
                stringBuffer.append((String) asList.get(i));
                int i2 = (longValue > 1 ? 1 : (longValue == 1 ? 0 : -1));
                stringBuffer.append("");
                stringBuffer.append(" ago");
                break;
            }
            i++;
        }
        if ("".equals(stringBuffer.toString())) {
            return "0 s ago";
        }
        return stringBuffer.toString();
    }

    @SuppressLint("MissingPermission")
    public boolean internetIsConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }
}
