package anywheresoftware.b4a.objects;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Parcel;
import android.widget.RemoteViews;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.LayoutValues;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("RemoteViews")
public class RemoteViewsWrapper {
    protected RemoteViews current;
    protected String eventName;
    protected Parcel original;

    public static RemoteViewsWrapper createRemoteViews(BA ba, int i, String str, String str2) throws Exception {
        String[] strArr;
        RemoteViews remoteViews = new RemoteViews(BA.packageName, i);
        String lowerCase = str.toLowerCase(BA.cul);
        if (!lowerCase.endsWith(".bal")) {
            lowerCase = String.valueOf(lowerCase) + ".bal";
        }
        InputStream inputStreamOpen = BA.applicationContext.getAssets().open(lowerCase);
        DataInputStream dataInputStream = new DataInputStream(inputStreamOpen);
        int i2 = ConnectorUtils.readInt(dataInputStream);
        int iSkip = ConnectorUtils.readInt(dataInputStream);
        while (iSkip > 0) {
            long j = iSkip;
            iSkip = (int) (j - inputStreamOpen.skip(j));
        }
        if (i2 >= 3) {
            int i3 = ConnectorUtils.readInt(dataInputStream);
            strArr = new String[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                strArr[i4] = ConnectorUtils.readString(dataInputStream);
            }
        } else {
            strArr = null;
        }
        String[] strArr2 = strArr;
        int i5 = ConnectorUtils.readInt(dataInputStream);
        for (int i6 = 0; i6 < i5; i6++) {
            LayoutValues.readFromStream(dataInputStream);
        }
        loadLayoutHelper(ba, ConnectorUtils.readMap(dataInputStream, strArr2), remoteViews);
        dataInputStream.close();
        RemoteViewsWrapper remoteViewsWrapper = new RemoteViewsWrapper();
        Parcel parcelObtain = Parcel.obtain();
        remoteViewsWrapper.original = parcelObtain;
        remoteViews.writeToParcel(parcelObtain, 0);
        remoteViewsWrapper.eventName = str2.toLowerCase(BA.cul);
        return remoteViewsWrapper;
    }

    private static void loadLayoutHelper(BA ba, HashMap<String, Object> map, RemoteViews remoteViews) throws Exception {
        String lowerCase = ((String) map.get("eventName")).toLowerCase(BA.cul);
        String lowerCase2 = ((String) map.get("name")).toLowerCase(BA.cul);
        HashMap map2 = (HashMap) map.get(":kids");
        if (map2 != null) {
            for (int i = 0; i < map2.size(); i++) {
                loadLayoutHelper(ba, (HashMap) map2.get(String.valueOf(i)), remoteViews);
            }
        }
        if (ba.htSubs.containsKey(String.valueOf(lowerCase) + "_click")) {
            Intent intent = new Intent(BA.applicationContext, Common.getComponentClass(ba, null, true));
            intent.putExtra("b4a_internal_event", String.valueOf(lowerCase) + "_click");
            int idForView = getIdForView(ba, lowerCase2);
            remoteViews.setOnClickPendingIntent(idForView, PendingIntent.getBroadcast(ba.context, idForView, intent, Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728));
        }
    }

    protected static int getIdForView(BA ba, String str) {
        try {
            return Class.forName(String.valueOf(BA.packageName) + ".R$id").getField(String.valueOf(ba.getClassNameWithoutPackage()) + "_" + str.toLowerCase(BA.cul)).getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void checkNull() {
        Parcel parcel = this.original;
        if (parcel == null) {
            throw new RuntimeException("RemoteViews should be set by calling ConfigureHomeWidget.");
        }
        if (this.current == null) {
            parcel.setDataPosition(0);
            this.current = new RemoteViews(this.original);
        }
    }

    public boolean HandleWidgetEvents(BA ba, Intent intent) {
        if (intent == null) {
            return false;
        }
        if (intent.hasExtra("b4a_internal_event")) {
            raiseEventWithDebuggingSupport(ba, intent.getStringExtra("b4a_internal_event"));
            return true;
        }
        if (IntentWrapper.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            raiseEventWithDebuggingSupport(ba, String.valueOf(this.eventName) + "_requestupdate");
            return true;
        }
        if (!"android.appwidget.action.APPWIDGET_DISABLED".equals(intent.getAction())) {
            return false;
        }
        raiseEventWithDebuggingSupport(ba, String.valueOf(this.eventName) + "_disabled");
        return true;
    }

    private void raiseEventWithDebuggingSupport(final BA ba, final String str) {
        if (BA.debugMode) {
            BA.handler.post(new BA.B4ARunnable() { // from class: anywheresoftware.b4a.objects.RemoteViewsWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    ba.raiseEvent(this, str, new Object[0]);
                }
            });
        } else {
            ba.raiseEvent(this, str, new Object[0]);
        }
    }

    public void SetText(BA ba, String str, CharSequence charSequence) {
        checkNull();
        this.current.setTextViewText(getIdForView(ba, str), charSequence);
    }

    public void SetVisible(BA ba, String str, boolean z) {
        checkNull();
        this.current.setViewVisibility(getIdForView(ba, str), z ? 0 : 4);
    }

    public void SetImage(BA ba, String str, Bitmap bitmap) {
        checkNull();
        this.current.setImageViewBitmap(getIdForView(ba, str), bitmap);
    }

    public void SetTextColor(BA ba, String str, int i) {
        checkNull();
        this.current.setTextColor(getIdForView(ba, str), i);
    }

    public void SetTextSize(BA ba, String str, float f) {
        checkNull();
        this.current.setFloat(getIdForView(ba, str), "setTextSize", f);
    }

    public void SetProgress(BA ba, String str, int i) {
        checkNull();
        this.current.setInt(getIdForView(ba, str), "setProgress", i);
    }

    public void UpdateWidget(BA ba) throws ClassNotFoundException {
        checkNull();
        AppWidgetManager.getInstance(ba.context).updateAppWidget(new ComponentName(ba.context, Class.forName(ba.className)), this.current);
        this.current = null;
    }
}
