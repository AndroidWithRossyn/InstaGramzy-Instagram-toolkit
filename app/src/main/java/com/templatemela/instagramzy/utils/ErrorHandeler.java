package com.templatemela.instagramzy.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

public final class ErrorHandeler {
    static String COMMA_SEPARATED_EMAIL_ADDRESSES = null;
    private static final String DEFAULT_HANDLER_PACKAGE_NAME = "com.android.internal.os";
    static final String EXTRA_ACTIVITY_LOG = "EXTRA_ACTIVITY_LOG";
    static final String EXTRA_STACK_TRACE = "EXTRA_STACK_TRACE";
    private static final int MAX_ACTIVITIES_IN_LOG = 50;
    private static final int MAX_STACK_TRACE_SIZE = 131071;
    private static final String SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_crash_timestamp";
    private static final String SHARED_PREFERENCES_FILE = "uceh_preferences";
    private static final String TAG = "UCEHandler";
    private static final String UCE_HANDLER_PACKAGE_NAME = "com.rohitss.uceh";
   
    public static final Deque<String> activityLog = new ArrayDeque(50);
   
    public static Application application;
   
    public static boolean isBackgroundMode;
   
    public static boolean isInBackground = true;
   
    public static boolean isTrackActivitiesEnabled;
   
    public static boolean isUCEHEnabled;
   
    public static WeakReference<Activity> lastActivityCreated = new WeakReference<>( null);

    ErrorHandeler(Builder builder) {
        isUCEHEnabled = builder.isUCEHEnabled;
        isTrackActivitiesEnabled = builder.isTrackActivitiesEnabled;
        isBackgroundMode = builder.isBackgroundModeEnabled;
        COMMA_SEPARATED_EMAIL_ADDRESSES = builder.commaSeparatedEmailAddresses;
        setUCEHandler(builder.context);
    }

    private static void setUCEHandler(Context context) {
        /*if (context != null) {
            try {
                final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                if (defaultUncaughtExceptionHandler == null || !defaultUncaughtExceptionHandler.getClass().getName().startsWith(UCE_HANDLER_PACKAGE_NAME)) {
                    if (defaultUncaughtExceptionHandler != null && !defaultUncaughtExceptionHandler.getClass().getName().startsWith(DEFAULT_HANDLER_PACKAGE_NAME)) {
                        Log.e(TAG, "You already have an UncaughtExceptionHandler. If you use a custom UncaughtExceptionHandler, it should be initialized after UCEHandler! Installing anyway, but your original handler will not be called.");
                    }
                    application = (Application) context.getApplicationContext();
                    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                        public void uncaughtException(Thread thread, Throwable th) {
                            if (ErrorHandeler.isUCEHEnabled) {
                                Log.e(ErrorHandeler.TAG, "App crashed, executing UCEHandler's UncaughtExceptionHandler", th);
                                if (ErrorHandeler.hasCrashedInTheLastSeconds(ErrorHandeler.application)) {
                                    Log.e(ErrorHandeler.TAG, "App already crashed recently, not starting custom error activity because we could enter a restart loop. Are you sure that your app does not crash directly on init?", th);
                                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = defaultUncaughtExceptionHandler;
                                    if (uncaughtExceptionHandler != null) {
                                        uncaughtExceptionHandler.uncaughtException(thread, th);
                                        return;
                                    }
                                } else {
                                    ErrorHandeler.setLastCrashTimestamp(ErrorHandeler.application, new Date().getTime());
                                    if (!ErrorHandeler.isInBackground || ErrorHandeler.isBackgroundMode) {
                                        Intent intent = new Intent(ErrorHandeler.application, ErrorActivity.class);
                                        StringWriter stringWriter = new StringWriter();
                                        th.printStackTrace(new PrintWriter(stringWriter));
                                        String stringWriter2 = stringWriter.toString();
                                        if (stringWriter2.length() > ErrorHandeler.MAX_STACK_TRACE_SIZE) {
                                            stringWriter2 = stringWriter2.substring(0, 131047) + " [stack trace too large]";
                                        }
                                        intent.putExtra(ErrorHandeler.EXTRA_STACK_TRACE, stringWriter2);
                                        if (ErrorHandeler.isTrackActivitiesEnabled) {
                                            StringBuilder sb = new StringBuilder();
                                            while (!ErrorHandeler.activityLog.isEmpty()) {
                                                sb.append((String) ErrorHandeler.activityLog.poll());
                                            }
                                            intent.putExtra(ErrorHandeler.EXTRA_ACTIVITY_LOG, sb.toString());
                                        }
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        ErrorHandeler.application.startActivity(intent);
                                    } else {
                                        Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = defaultUncaughtExceptionHandler;
                                        if (uncaughtExceptionHandler2 != null) {
                                            uncaughtExceptionHandler2.uncaughtException(thread, th);
                                            return;
                                        }
                                    }
                                }
                                Activity activity = (Activity) ErrorHandeler.lastActivityCreated.get();
                                if (activity != null) {
                                    activity.finish();
                                    ErrorHandeler.lastActivityCreated.clear();
                                }
                                ErrorHandeler.killCurrentProcess();
                                return;
                            }
                            Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = defaultUncaughtExceptionHandler;
                            if (uncaughtExceptionHandler3 != null) {
                                uncaughtExceptionHandler3.uncaughtException(thread, th);
                            }
                        }
                    });
                    application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                        int currentlyStartedActivities = 0;
                        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

                        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                        }

                        public void onActivityCreated(Activity activity, Bundle bundle) {
                            if (activity.getClass() != ErrorActivity.class) {
                                WeakReference unused = ErrorHandeler.lastActivityCreated = new WeakReference(activity);
                            }
                            if (ErrorHandeler.isTrackActivitiesEnabled) {
                                Deque access$1200 = ErrorHandeler.activityLog;
                                access$1200.add(this.dateFormat.format(new Date()) + ": " + activity.getClass().getSimpleName() + " created\n");
                            }
                        }

                        public void onActivityStarted(Activity activity) {
                            boolean z = true;
                            int i = this.currentlyStartedActivities + 1;
                            this.currentlyStartedActivities = i;
                            if (i != 0) {
                                z = false;
                            }
                            boolean unused = ErrorHandeler.isInBackground = z;
                        }

                        public void onActivityResumed(Activity activity) {
                            if (ErrorHandeler.isTrackActivitiesEnabled) {
                                Deque access$1200 = ErrorHandeler.activityLog;
                                access$1200.add(this.dateFormat.format(new Date()) + ": " + activity.getClass().getSimpleName() + " resumed\n");
                            }
                        }

                        public void onActivityPaused(Activity activity) {
                            if (ErrorHandeler.isTrackActivitiesEnabled) {
                                Deque access$1200 = ErrorHandeler.activityLog;
                                access$1200.add(this.dateFormat.format(new Date()) + ": " + activity.getClass().getSimpleName() + " paused\n");
                            }
                        }

                        public void onActivityStopped(Activity activity) {
                            boolean z = true;
                            int i = this.currentlyStartedActivities - 1;
                            this.currentlyStartedActivities = i;
                            if (i != 0) {
                                z = false;
                            }
                            boolean unused = ErrorHandeler.isInBackground = z;
                        }

                        public void onActivityDestroyed(Activity activity) {
                            if (ErrorHandeler.isTrackActivitiesEnabled) {
                                Deque access$1200 = ErrorHandeler.activityLog;
                                access$1200.add(this.dateFormat.format(new Date()) + ": " + activity.getClass().getSimpleName() + " destroyed\n");
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "UCEHandler was already installed, doing nothing!");
                }
                Log.i(TAG, "UCEHandler has been installed.");
            } catch (Throwable th) {
                Log.e(TAG, "UCEHandler can not be initialized. Help making it better by reporting this as a bug.", th);
            }
        } else {
            Log.e(TAG, "Context can not be null");*/
        //    }
    }

   
    public static boolean hasCrashedInTheLastSeconds(Context context) {
        long lastCrashTimestamp = getLastCrashTimestamp(context);
        long time = new Date().getTime();
        return lastCrashTimestamp <= time && time - lastCrashTimestamp < 3000;
    }

   
    public static void setLastCrashTimestamp(Context context, long j) {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, 0).edit().putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, j).commit();
    }

   
    public static void killCurrentProcess() {
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    private static long getLastCrashTimestamp(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE, 0).getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP, -1);
    }

    static void closeApplication(Activity activity) {
        activity.finish();
        killCurrentProcess();
    }

    public static class Builder {
       
        public String commaSeparatedEmailAddresses;
       
        public Context context;
       
        public boolean isBackgroundModeEnabled = true;
       
        public boolean isTrackActivitiesEnabled = false;
       
        public boolean isUCEHEnabled = true;

        public Builder(Context context2) {
            this.context = context2;
        }

        public Builder setUCEHEnabled(boolean z) {
            this.isUCEHEnabled = z;
            return this;
        }

        public Builder setTrackActivitiesEnabled(boolean z) {
            this.isTrackActivitiesEnabled = z;
            return this;
        }

        public Builder setBackgroundModeEnabled(boolean z) {
            this.isBackgroundModeEnabled = z;
            return this;
        }

        public Builder addCommaSeparatedEmailAddresses(String str) {
            if (str == null) {
                str = "";
            }
            this.commaSeparatedEmailAddresses = str;
            return this;
        }

        public ErrorHandeler build() {
            return new ErrorHandeler(this);
        }
    }
}
