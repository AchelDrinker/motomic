package anywheresoftware.b4a.objects;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.B4AApplication;
import anywheresoftware.b4a.keywords.Common;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Notification")
public class NotificationWrapper extends AbsObjectWrapper<Object> {
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MIN = 1;
    private static Method methodSetLastEvent = null;
    private static int pendingId = 1;

    public static class NotificationData {
        public int defaults;
        public int flags;
        public int icon;
        public int importanceLevel;
        public int number;
    }

    public void Initialize() {
        Initialize2(3);
    }

    public void Initialize2(int i) {
        NotificationData notificationData = new NotificationData();
        notificationData.importanceLevel = i;
        notificationData.defaults = -1;
        setObject(notificationData);
    }

    private NotificationData getND() {
        Object object = getObject();
        if (!(object instanceof NotificationData)) {
            throw new RuntimeException("Cannot change properties after call to SetInfo. Initialize the notification again.");
        }
        return (NotificationData) object;
    }

    public void setVibrate(boolean z) {
        setValue(z, 2);
    }

    public void setSound(boolean z) {
        setValue(z, 1);
    }

    public void setLight(boolean z) {
        setValue(z, 4);
        setFlag(z, 1);
    }

    private void setValue(boolean z, int i) {
        if (z) {
            NotificationData nd = getND();
            nd.defaults = i | nd.defaults;
        } else {
            NotificationData nd2 = getND();
            nd2.defaults = (i ^ (-1)) & nd2.defaults;
        }
    }

    public void setAutoCancel(boolean z) {
        setFlag(z, 16);
    }

    public void setInsistent(boolean z) {
        setFlag(z, 4);
    }

    public void setOnGoingEvent(boolean z) {
        setFlag(z, 2);
    }

    public int getNumber() {
        return getND().number;
    }

    public void setNumber(int i) {
        getND().number = i;
    }

    private void setFlag(boolean z, int i) {
        if (z) {
            NotificationData nd = getND();
            nd.flags = i | nd.flags;
        } else {
            NotificationData nd2 = getND();
            nd2.flags = (i ^ (-1)) & nd2.flags;
        }
    }

    public void setIcon(String str) {
        getND().icon = BA.applicationContext.getResources().getIdentifier(str, "drawable", BA.packageName);
    }

    public void SetInfoNew(BA ba, CharSequence charSequence, CharSequence charSequence2, Object obj) throws ClassNotFoundException {
        SetInfo2New(ba, charSequence, charSequence2, null, obj);
    }

    public void SetInfo2New(BA ba, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Object obj) throws ClassNotFoundException {
        int i;
        Notification notificationBuild;
        Notification.Builder builder;
        Intent componentIntent = Common.getComponentIntent(ba, obj);
        componentIntent.addFlags(268435456);
        componentIntent.addFlags(131072);
        if (charSequence3 != null) {
            componentIntent.putExtra("Notification_Tag", charSequence3);
        }
        int i2 = Build.VERSION.SDK_INT >= 31 ? 201326592 : 134217728;
        Context context = ba.context;
        if (charSequence3 == null) {
            i = 0;
        } else {
            i = pendingId;
            pendingId = i + 1;
        }
        PendingIntent activity = PendingIntent.getActivity(context, i, componentIntent, i2);
        NotificationData nd = getND();
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= 26) {
                try {
                    String str = "channel_" + nd.importanceLevel;
                    builder = new Notification.Builder(BA.applicationContext, str);
                    B4AApplication b4AApplication = Common.Application;
                    ((NotificationManager) BA.applicationContext.getSystemService("notification")).createNotificationChannel(new NotificationChannel(str, B4AApplication.getLabelName(), nd.importanceLevel));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                builder = new Notification.Builder(BA.applicationContext);
            }
            builder.setContentTitle(charSequence).setContentText(charSequence2).setContentIntent(activity);
            notificationBuild = builder.build();
            notificationBuild.defaults = nd.defaults;
            notificationBuild.flags = nd.flags;
            notificationBuild.icon = nd.icon;
            notificationBuild.when = System.currentTimeMillis();
            notificationBuild.number = nd.number;
            notificationBuild.extras.putBoolean(NotificationCompat.EXTRA_SHOW_WHEN, true);
        } else {
            Notification notification = new Notification();
            notification.defaults = nd.defaults;
            notification.flags = nd.flags;
            notification.icon = nd.icon;
            notification.when = System.currentTimeMillis();
            notification.number = nd.number;
            try {
                if (methodSetLastEvent == null) {
                    methodSetLastEvent = Notification.class.getDeclaredMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                }
                methodSetLastEvent.invoke(notification, ba.context, charSequence, charSequence2, activity);
                notificationBuild = notification;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        setObject(notificationBuild);
    }

    public void SetInfo(BA ba, String str, String str2, Object obj) throws ClassNotFoundException {
        SetInfo2New(ba, str, str2, null, obj);
    }

    public void SetInfo2(BA ba, String str, String str2, String str3, Object obj) throws ClassNotFoundException {
        SetInfo2New(ba, str, str2, str3, obj);
    }

    public void Notify(int i) {
        NotificationManager notificationManager = (NotificationManager) BA.applicationContext.getSystemService("notification");
        if (!(getObject() instanceof Notification)) {
            throw new RuntimeException("You must first call SetInfo or SetInfo2");
        }
        notificationManager.notify(i, (Notification) getObject());
    }

    public void Cancel(int i) {
        ((NotificationManager) BA.applicationContext.getSystemService("notification")).cancel(i);
    }
}
