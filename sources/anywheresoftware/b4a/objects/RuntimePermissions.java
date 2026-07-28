package anywheresoftware.b4a.objects;

import android.app.Activity;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import anywheresoftware.b4a.BA;
import java.io.File;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@BA.Version(1.2f)
@BA.ShortName("RuntimePermissions")
public class RuntimePermissions {
    public static final String PERMISSION_ACCESS_CHECKIN_PROPERTIES = "android.permission.ACCESS_CHECKIN_PROPERTIES";
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String PERMISSION_ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String PERMISSION_ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    public static final String PERMISSION_BODY_SENSORS = "android.permission.BODY_SENSORS";
    public static final String PERMISSION_CALL_PHONE = "android.permission.CALL_PHONE";
    public static final String PERMISSION_CAMERA = "android.permission.CAMERA";
    public static final String PERMISSION_GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";
    public static final String PERMISSION_POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";
    public static final String PERMISSION_PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
    public static final String PERMISSION_READ_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String PERMISSION_READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    public static final String PERMISSION_READ_CONTACTS = "android.permission.READ_CONTACTS";
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String PERMISSION_READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    public static final String PERMISSION_READ_SMS = "android.permission.READ_SMS";
    public static final String PERMISSION_RECEIVE_MMS = "android.permission.RECEIVE_MMS";
    public static final String PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String PERMISSION_RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO";
    public static final String PERMISSION_SEND_SMS = "android.permission.SEND_SMS";
    public static final String PERMISSION_USE_SIP = "android.permission.USE_SIP";
    public static final String PERMISSION_WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
    public static final String PERMISSION_WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    public static final String PERMISSION_WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private RequestHandler reqHandler;

    public boolean Check(String str) {
        return ContextCompat.checkSelfPermission(BA.applicationContext, str) == 0;
    }

    static class RequestHandler implements Runnable {
        public BA aba;
        public ArrayList<String> permissions = new ArrayList<>();

        public RequestHandler(BA ba) {
            this.aba = ba;
        }

        @Override // java.lang.Runnable
        public void run() {
            Activity activity = this.aba.activity;
            ArrayList<String> arrayList = this.permissions;
            ActivityCompat.requestPermissions(activity, (String[]) arrayList.toArray(new String[arrayList.size()]), 0);
            this.aba = null;
        }
    }

    public void CheckAndRequest(BA ba, String str) {
        BA ba2 = ba.sharedProcessBA.activityBA.get();
        if (ba2 == null) {
            throw new RuntimeException("Can only be called from an Activity");
        }
        boolean zCheck = Check(str);
        if (!zCheck && Build.VERSION.SDK_INT >= 23) {
            RequestHandler requestHandler = this.reqHandler;
            if (requestHandler == null || requestHandler.aba == null || this.reqHandler.aba != ba2) {
                this.reqHandler = new RequestHandler(ba2);
                BA.handler.postDelayed(this.reqHandler, 200L);
            }
            this.reqHandler.permissions.add(str);
            return;
        }
        ba2.processBA.raiseEventFromDifferentThread(null, null, 0, "activity_permissionresult", true, new Object[]{str, Boolean.valueOf(zCheck)});
    }

    public String GetSafeDirDefaultExternal(String str) {
        File file;
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(BA.applicationContext, str);
        if (externalFilesDirs == null || externalFilesDirs.length == 0 || (file = externalFilesDirs[0]) == null) {
            return anywheresoftware.b4a.objects.streams.File.Combine(anywheresoftware.b4a.objects.streams.File.getDirInternal(), str);
        }
        return file.toString();
    }

    public String[] GetAllSafeDirsExternal(String str) {
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(BA.applicationContext, str);
        if (externalFilesDirs == null || externalFilesDirs.length == 0) {
            return new String[0];
        }
        String[] strArr = new String[externalFilesDirs.length];
        for (int i = 0; i < externalFilesDirs.length; i++) {
            File file = externalFilesDirs[i];
            strArr[i] = file == null ? "" : file.toString();
        }
        return strArr;
    }
}
