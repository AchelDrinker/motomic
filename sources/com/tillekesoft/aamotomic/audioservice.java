package com.tillekesoft.aamotomic;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.os.EnvironmentCompat;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DateTime;
import anywheresoftware.b4a.keywords.constants.KeyCodes;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.NotificationWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.streams.File;
import anywheresoftware.b4a.phone.Phone;
import anywheresoftware.b4j.object.JavaObject;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class audioservice extends Service {
    public static String _diag_enabled_file = "";
    public static String _diag_file = "";
    public static int _fast_interval = 0;
    public static boolean _vvvvvv0 = false;
    public static Object _vvvvvv7 = null;
    public static int _vvvvvvv0 = 0;
    public static JavaObject _vvvvvvv1 = null;
    public static boolean _vvvvvvv3 = false;
    public static String _vvvvvvv4 = "";
    public static boolean _vvvvvvv5 = false;
    public static int _vvvvvvv7 = 0;
    public static long _vvvvvvvv1 = 0;
    public static long _vvvvvvvv4 = 0;
    public static int _vvvvvvvvv0 = 0;
    public static long _vvvvvvvvv4 = 0;
    public static boolean _vvvvvvvvv5 = false;
    public static int _vvvvvvvvv6 = 0;
    public static int _vvvvvvvvvv0 = 0;
    public static NotificationWrapper _vvvvvvvvvv2 = null;
    public static Timer _vvvvvvvvvv3 = null;
    public static int _vvvvvvvvvv4 = 0;
    public static Phone.PhoneWakeState _vvvvvvvvvv5 = null;
    public static long _vvvvvvvvvv6 = 0;
    public static long _vvvvvvvvvv7 = 0;
    public static int _vvvvvvvvvvv0 = 0;
    public static int _vvvvvvvvvvv1 = 0;
    public static boolean _vvvvvvvvvvv2 = false;
    public static boolean _vvvvvvvvvvv3 = false;
    public static long _vvvvvvvvvvv4 = 0;
    public static boolean _vvvvvvvvvvv5 = false;
    public static long _vvvvvvvvvvv6 = 0;
    public static boolean _vvvvvvvvvvv7 = false;
    public static boolean _vvvvvvvvvvvv1 = false;
    public static boolean _vvvvvvvvvvvv2 = false;
    public static boolean _vvvvvvvvvvvv3 = false;
    public static boolean _vvvvvvvvvvvv4 = false;
    static audioservice mostCurrent;
    public static BA processBA;
    private ServiceHelper _service;
    public Common __c = null;
    public main _vvvvvv5 = null;
    public starter _vvvvvv2 = null;
    public btreceiver _vvvvvv3 = null;
    public diaglog _vvvvvv4 = null;
    public readmeviewer _vvvvv2 = null;
    public aggressivemode _vvvvv1 = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class audioservice_BR extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BA.LogInfo("** Receiver (audioservice) OnReceive **");
            Intent intent2 = new Intent(context, (Class<?>) audioservice.class);
            if (intent != null) {
                intent2.putExtra("b4a_internal_intent", intent);
            }
            ServiceHelper.StarterHelper.startServiceFromReceiver(context, intent2, false, BA.class);
        }
    }

    public static Class<?> getObject() {
        return audioservice.class;
    }

    @Override // android.app.Service
    public void onCreate() {
        audioservice audioserviceVar;
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
            audioserviceVar = this;
            BA ba = new BA(audioserviceVar, (BALayout) null, (BA) null, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.audioservice");
            processBA = ba;
            if (BA.isShellModeRuntimeCheck(ba)) {
                processBA.raiseEvent2(null, true, "SHELL", false, new Object[0]);
            }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals", null).invoke(null, null);
                processBA.loadHtSubs(getClass());
                ServiceHelper.init();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            audioserviceVar = this;
        }
        audioserviceVar._service = new ServiceHelper(this);
        processBA.service = audioserviceVar;
        if (BA.isShellModeRuntimeCheck(processBA)) {
            BA ba2 = processBA;
            ba2.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.audioservice", ba2, audioserviceVar._service, Float.valueOf(Common.Density));
        }
        if (ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false)) {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (audioservice) Create ***");
            processBA.raiseEvent(null, "service_create", new Object[0]);
        }
        processBA.runHook("oncreate", this, null);
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        onStartCommand(intent, 0, 0);
    }

    @Override // android.app.Service
    public int onStartCommand(final Intent intent, int i, int i2) {
        if (!ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() { // from class: com.tillekesoft.aamotomic.audioservice.1
            @Override // java.lang.Runnable
            public void run() {
                audioservice.this.handleStart(intent);
            }
        })) {
            ServiceHelper.StarterHelper.addWaitForLayout(new Runnable() { // from class: com.tillekesoft.aamotomic.audioservice.2
                @Override // java.lang.Runnable
                public void run() {
                    audioservice.processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (audioservice) Create **");
                    audioservice.processBA.raiseEvent(null, "service_create", new Object[0]);
                    audioservice.this.handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
                }
            });
        }
        processBA.runHook("onstartcommand", this, new Object[]{intent, Integer.valueOf(i), Integer.valueOf(i2)});
        return 2;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStart(Intent intent) {
        BA.LogInfo("** Service (audioservice) Start **");
        Method method = processBA.htSubs.get("service_start");
        if (method != null) {
            if (method.getParameterTypes().length > 0) {
                processBA.raiseEvent(null, "service_start", ServiceHelper.StarterHelper.handleStartIntent(intent, this._service, processBA));
            } else {
                processBA.raiseEvent(null, "service_start", new Object[0]);
            }
        }
    }

    @Override // android.app.Service
    public void onTimeout(int i) {
        BA.LogInfo("** Service (audioservice) Timeout **");
        Map map = new Map();
        map.Initialize();
        map.Put("StartId", Integer.valueOf(i));
        processBA.raiseEvent(null, "service_timeout", map);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (audioservice) Destroy **");
        processBA.raiseEvent(null, "service_destroy", new Object[0]);
        processBA.service = null;
        mostCurrent = null;
        processBA.setActivityPaused(true);
        processBA.runHook("ondestroy", this, null);
    }

    public static String _vvvvvv6() throws Exception {
        Object obj = _vvvvvv7;
        if (obj == null || _vvvvvv0) {
            return "";
        }
        try {
            boolean z = true;
            int iObjectToNumber = (int) BA.ObjectToNumber(_vvvvvvv1.RunMethod("requestAudioFocus", new Object[]{obj, 3, 4}));
            if (iObjectToNumber != 1) {
                z = false;
            }
            _vvvvvv0 = z;
            if (!z) {
                Common.LogImpl("43538955", "[FOCUS] Shield denied (res=" + BA.NumberToString(iObjectToNumber) + ")", 0);
            } else {
                Common.LogImpl("43538953", "[FOCUS] Shield acquired", 0);
            }
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("43538958", "[FOCUS] Acquire failed: " + BA.ObjectToString(Common.LastException(processBA)), 0);
        }
        return "";
    }

    public static Object _af_event(String str, Object[] objArr) throws Exception {
        return Common.Null;
    }

    public static String _vvvvvvv2() throws Exception {
        boolean zObjectToBoolean;
        new JavaObject();
        JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), _vvvvvvv1.RunMethod("getCommunicationDevice", (Object[]) Common.Null));
        String strObjectToString = javaObject.IsInitialized() ? BA.ObjectToString(javaObject.RunMethod("getAddress", (Object[]) Common.Null)) : "";
        try {
            zObjectToBoolean = BA.ObjectToBoolean(_vvvvvvv1.RunMethod("isBluetoothScoOn", (Object[]) Common.Null));
        } catch (Exception e) {
            e = e;
            zObjectToBoolean = false;
        }
        try {
            if (zObjectToBoolean != _vvvvvvv3) {
                _vvvvv3("[SCO] State: " + BA.ObjectToString(Boolean.valueOf(_vvvvvvv3)) + " -> " + BA.ObjectToString(Boolean.valueOf(zObjectToBoolean)));
                _vvvvvvv3 = zObjectToBoolean;
            }
        } catch (Exception e2) {
            e = e2;
            processBA.setLastException(e);
            Common.LogImpl("43145747", "[SCO] isBluetoothScoOn check failed: " + Common.LastException(processBA).getMessage(), 0);
            _vvvvv3("[ERROR] isBluetoothScoOn: " + Common.LastException(processBA).getMessage());
        }
        if (strObjectToString.equalsIgnoreCase(_vvvvvvv4)) {
            if (!_vvvvvvv5) {
                _vvvvvvv5 = true;
                Common.LogImpl("43145755", ">>> SUCCESS: Mic routed to target device", 0);
                BA ba = processBA;
                main mainVar = mostCurrent._vvvvvv5;
                Common.CallSubDelayed2(ba, main.getObject(), "LogToScreen", ">>> SUCCESS: Mic routed to target device");
            }
            if (!zObjectToBoolean) {
                Common.LogImpl("43145762", "[SCO] SCO dropped while session active (device=" + strObjectToString + ") - re-establishing", 0);
                _vvvvvvv1.RunMethod("startBluetoothSco", (Object[]) Common.Null);
                _vvvvvvv1.RunMethod("setBluetoothScoOn", new Object[]{true});
                _vvvvvvv6();
            }
            aggressivemode aggressivemodeVar = mostCurrent._vvvvv1;
            if (aggressivemode._v5(processBA)) {
                int i = _vvvvvvv7 + 1;
                _vvvvvvv7 = i;
                if (i >= 3) {
                    _vvvvvvv7 = 0;
                    boolean zObjectToBoolean2 = BA.ObjectToBoolean(_vvvvvvv1.RunMethod("setCommunicationDevice", new Object[]{javaObject.getObject()}));
                    _vvvvvvv0++;
                    _vvvvv3("[AGGRESSIVE] Periodic re-assert result=" + BA.ObjectToString(Boolean.valueOf(zObjectToBoolean2)));
                }
            }
        } else {
            if (_vvvvvvv5 && strObjectToString.length() < 2) {
                return "";
            }
            DateTime dateTime = Common.DateTime;
            if (DateTime.getNow() > _vvvvvvvv1 + 2000) {
                Common.LogImpl("43145796", "Detected Wrong Mic (" + strObjectToString + "). Forcing...", 0);
                DateTime dateTime2 = Common.DateTime;
                _vvvvvvvv1 = DateTime.getNow();
            }
            _vvvvvvv6();
        }
        return "";
    }

    public static String _vvvvv3(String str) throws Exception {
        if (!_vvvv0()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = Common.DateTime;
        DateTime dateTime2 = Common.DateTime;
        sb.append(DateTime.Date(DateTime.getNow()));
        sb.append(" ");
        DateTime dateTime3 = Common.DateTime;
        DateTime dateTime4 = Common.DateTime;
        sb.append(DateTime.Time(DateTime.getNow()));
        String str2 = "[" + sb.toString() + "] " + str;
        try {
            File.TextWriterWrapper textWriterWrapper = new File.TextWriterWrapper();
            File file = Common.File;
            File file2 = Common.File;
            textWriterWrapper.Initialize(File.OpenOutput(File.getDirInternal(), _diag_file, true).getObject());
            textWriterWrapper.WriteLine(str2);
            textWriterWrapper.Close();
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("43473418", "DiagAppend error: " + Common.LastException(processBA).getMessage(), 0);
        }
        return "";
    }

    public static String _vvvv7() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirInternal(), _diag_enabled_file)) {
            File file3 = Common.File;
            File file4 = Common.File;
            File.WriteString(File.getDirInternal(), _diag_enabled_file, BA.ObjectToString(false));
            return "";
        }
        return "";
    }

    public static boolean _vvvv0() throws Exception {
        _vvvv7();
        try {
            File file = Common.File;
            File file2 = Common.File;
            return File.ReadString(File.getDirInternal(), _diag_enabled_file).toLowerCase().equals("true");
        } catch (Exception e) {
            processBA.setLastException(e);
            return true;
        }
    }

    public static String _vvvvvvvv2() throws Exception {
        String strObjectToString;
        try {
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getAvailableCommunicationDevices", (Object[]) Common.Null));
            new JavaObject();
            int size = list.getSize();
            String str = "";
            for (int i = 0; i < size; i++) {
                JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i));
                int iObjectToNumber = (int) BA.ObjectToNumber(javaObject.RunMethod("getType", (Object[]) Common.Null));
                String strObjectToString2 = BA.ObjectToString(javaObject.RunMethod("getAddress", (Object[]) Common.Null));
                try {
                    strObjectToString = BA.ObjectToString(javaObject.RunMethod("getProductName", (Object[]) Common.Null));
                } catch (Exception e) {
                    processBA.setLastException(e);
                }
                if (strObjectToString == null || strObjectToString.length() == 0) {
                    strObjectToString = "Unknown";
                }
                if (str.length() > 0) {
                    str = str + " | ";
                }
                str = str + "type=" + BA.NumberToString(iObjectToNumber) + ",addr=" + strObjectToString2 + ",name=" + strObjectToString;
            }
            _vvvvv3("[DEVICES] Available: [" + str + "]");
        } catch (Exception e2) {
            processBA.setLastException(e2);
            _vvvvv3("[DEVICES] Query failed: " + Common.LastException(processBA).getMessage());
        }
        return "";
    }

    public static String _vvvvvvvv3(String str) throws Exception {
        String str2;
        DateTime dateTime = Common.DateTime;
        if (DateTime.getNow() < _vvvvvvvv4 + 2000) {
            return "";
        }
        DateTime dateTime2 = Common.DateTime;
        _vvvvvvvv4 = DateTime.getNow();
        try {
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getActivePlaybackConfigurations", (Object[]) Common.Null));
            if (list.IsInitialized()) {
                new JavaObject();
                int size = list.getSize();
                str2 = "";
                for (int i = 0; i < size; i++) {
                    JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i));
                    new JavaObject();
                    JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getAudioAttributes", (Object[]) Common.Null));
                    int iObjectToNumber = (int) BA.ObjectToNumber(javaObject2.RunMethod("getUsage", (Object[]) Common.Null));
                    int iObjectToNumber2 = (int) BA.ObjectToNumber(javaObject2.RunMethod("getContentType", (Object[]) Common.Null));
                    int iObjectToNumber3 = (int) BA.ObjectToNumber(javaObject2.RunMethod("getFlags", (Object[]) Common.Null));
                    if (str2.length() > 0) {
                        str2 = str2 + " | ";
                    }
                    str2 = str2 + "u=" + BA.NumberToString(iObjectToNumber) + ",c=" + BA.NumberToString(iObjectToNumber2) + ",f=" + BA.NumberToString(iObjectToNumber3);
                }
            } else {
                str2 = "";
            }
            _vvvvv3("[PB] " + str + " attrs=[" + str2 + "]");
        } catch (Exception e) {
            processBA.setLastException(e);
            _vvvvv3("[PB] " + str + " error=" + Common.LastException(processBA).getMessage());
        }
        return "";
    }

    public static String _vvvvvvvv5(String str) throws Exception {
        List list;
        int iObjectToNumber;
        int iObjectToNumber2;
        try {
            new List();
            list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getActivePlaybackConfigurations", (Object[]) Common.Null));
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("43014696", "DumpPlaybackConfigs error: " + BA.ObjectToString(Common.LastException(processBA)), 0);
        }
        if (!list.IsInitialized()) {
            Common.LogImpl("43014660", "[PB] " + str + " | configs: <not init>", 0);
            return "";
        }
        if (list.getSize() == 0) {
            Common.LogImpl("43014664", "[PB] " + str + " | configs: 0", 0);
            return "";
        }
        Common.LogImpl("43014668", "[PB] " + str + " | configs: " + BA.NumberToString(list.getSize()), 0);
        new JavaObject();
        int size = list.getSize();
        for (int i = 0; i < size; i++) {
            JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i));
            new JavaObject();
            int iObjectToNumber3 = (int) BA.ObjectToNumber(((JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getAudioAttributes", (Object[]) Common.Null))).RunMethod("getUsage", (Object[]) Common.Null));
            int iObjectToNumber4 = -1;
            try {
                iObjectToNumber = (int) BA.ObjectToNumber(javaObject.RunMethod("getPlayerState", (Object[]) Common.Null));
            } catch (Exception e2) {
                processBA.setLastException(e2);
                Common.LogImpl("43014680", "[PB] getPlayerState failed: " + Common.LastException(processBA).getMessage(), 0);
                iObjectToNumber = -1;
            }
            try {
                iObjectToNumber2 = (int) BA.ObjectToNumber(javaObject.RunMethod("getClientUid", (Object[]) Common.Null));
            } catch (Exception e3) {
                processBA.setLastException(e3);
                Common.LogImpl("43014685", "[PB] getClientUid failed: " + Common.LastException(processBA).getMessage(), 0);
                iObjectToNumber2 = -1;
            }
            try {
                iObjectToNumber4 = (int) BA.ObjectToNumber(javaObject.RunMethod("getClientPid", (Object[]) Common.Null));
            } catch (Exception e4) {
                processBA.setLastException(e4);
                Common.LogImpl("43014690", "[PB] getClientPid failed: " + Common.LastException(processBA).getMessage(), 0);
            }
            Common.LogImpl("43014693", "[PB] usage=" + BA.NumberToString(iObjectToNumber3) + " state=" + _vvvvvvvv6(iObjectToNumber) + " uid=" + BA.NumberToString(iObjectToNumber2) + " pid=" + BA.NumberToString(iObjectToNumber4), 0);
        }
        return "";
    }

    public static String _vvvvvvv6() throws Exception {
        _vvvvvvv1.RunMethod("startBluetoothSco", (Object[]) Common.Null);
        _vvvvvvv1.RunMethod("setBluetoothScoOn", new Object[]{true});
        _vvvvvvvv2();
        try {
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getAvailableCommunicationDevices", (Object[]) Common.Null));
            new JavaObject();
            int size = list.getSize();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i));
                int iObjectToNumber = (int) BA.ObjectToNumber(javaObject.RunMethod("getType", (Object[]) Common.Null));
                if (iObjectToNumber == 7) {
                    String strObjectToString = BA.ObjectToString(javaObject.RunMethod("getAddress", (Object[]) Common.Null));
                    if (strObjectToString.equalsIgnoreCase(_vvvvvvv4)) {
                        if (BA.ObjectToBoolean(_vvvvvvv1.RunMethod("setCommunicationDevice", new Object[]{javaObject.getObject()}))) {
                            _vvvvvvv5 = true;
                            Common.LogImpl("43211289", "[DEBUG] setCommunicationDevice Success", 0);
                            _vvvvv3("[SCO] setCommunicationDevice SUCCESS addr=" + strObjectToString);
                        } else {
                            Common.LogImpl("43211292", "[DEBUG] setCommunicationDevice FAILED", 0);
                            _vvvvv3("[SCO] setCommunicationDevice FAILED type=" + BA.NumberToString(iObjectToNumber) + " addr=" + strObjectToString);
                        }
                    }
                }
                i++;
            }
            _vvvvv3("[SCO] Target device not in available list (target=" + _vvvvvvv4 + ")");
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("43211303", "Error setting device: " + BA.ObjectToString(Common.LastException(processBA)), 0);
            _vvvvv3("[SCO] ForceSCO error: " + Common.LastException(processBA).getMessage());
        }
        aggressivemode aggressivemodeVar = mostCurrent._vvvvv1;
        if (aggressivemode._v5(processBA)) {
            try {
                new JavaObject();
                JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), _vvvvvvv1.RunMethod("getCommunicationDevice", (Object[]) Common.Null));
                String strObjectToString2 = javaObject2.IsInitialized() ? BA.ObjectToString(javaObject2.RunMethod("getAddress", (Object[]) Common.Null)) : "";
                _vvvvv3("[AGGRESSIVE] Post-set verify addr=" + strObjectToString2 + " match=" + BA.ObjectToString(Boolean.valueOf(strObjectToString2.equalsIgnoreCase(_vvvvvvv4))));
            } catch (Exception e2) {
                processBA.setLastException(e2);
                _vvvvv3("[AGGRESSIVE] Post-set verify failed");
            }
        }
        return "";
    }

    public static String _vvvvvvvv7() throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeStatic("android.os.Build$VERSION");
            return BA.ObjectToString(javaObject.GetField("RELEASE"));
        } catch (Exception e) {
            processBA.setLastException(e);
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static String _vvvvvvvv0() throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeStatic("android.os.Build");
            return BA.ObjectToString(javaObject.GetField("MANUFACTURER"));
        } catch (Exception e) {
            processBA.setLastException(e);
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static String _vvvvvvvvv1() throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeStatic("android.os.Build");
            return BA.ObjectToString(javaObject.GetField("MODEL"));
        } catch (Exception e) {
            processBA.setLastException(e);
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static int _vvvvvvvvv2() throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeStatic("android.os.Build$VERSION");
            return (int) BA.ObjectToNumber(javaObject.GetField("SDK_INT"));
        } catch (Exception e) {
            processBA.setLastException(e);
            return 0;
        }
    }

    public static boolean _vvvvvvvvv3() throws Exception {
        try {
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getActivePlaybackConfigurations", (Object[]) Common.Null));
            if (list.IsInitialized()) {
                new JavaObject();
                int size = list.getSize();
                for (int i = 0; i < size; i++) {
                    JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i));
                    new JavaObject();
                    int iObjectToNumber = (int) BA.ObjectToNumber(((JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getAudioAttributes", (Object[]) Common.Null))).RunMethod("getUsage", (Object[]) Common.Null));
                    if (iObjectToNumber != 2 && iObjectToNumber != 16 && iObjectToNumber != 12 && iObjectToNumber != 11) {
                        if (_vvvvvvvvv5 && iObjectToNumber == 1) {
                            if (_vvvvvvvvv4 > 0) {
                                DateTime dateTime = Common.DateTime;
                                if (DateTime.getNow() - _vvvvvvvvv4 < _vvvvvvvvv6) {
                                    DateTime dateTime2 = Common.DateTime;
                                    if (DateTime.getNow() > _vvvvvvvv1 + 1000) {
                                        Common.LogImpl("42818084", "[DEBUG] Found Media Usage 1 (shield mode, hold period)", 0);
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                    if (_vvvvvvvvv4 > 0) {
                        _vvvvvvvvv4 = 0L;
                    }
                    DateTime dateTime3 = Common.DateTime;
                    if (DateTime.getNow() > _vvvvvvvv1 + 1000) {
                        Common.LogImpl("42818066", "[DEBUG] Found Speaking Usage: " + BA.NumberToString(iObjectToNumber), 0);
                    }
                    _vvvvv3("[PLAYBACK] Assistant usage=" + BA.NumberToString(iObjectToNumber));
                    return true;
                }
            }
            if (_vvvvvvvvv4 == 0 && _vvvvvvvvv5) {
                DateTime dateTime4 = Common.DateTime;
                _vvvvvvvvv4 = DateTime.getNow();
                Common.LogImpl("42818108", "[SESSION] Assistant speech ended - starting minimum hold timer", 0);
                _vvvvv3("[SESSION] Assistant speech ended - start hold timer");
            }
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("42818112", "Error checking output: " + BA.ObjectToString(Common.LastException(processBA)), 0);
            _vvvvv3("[ERROR] IsAssistantPlaying: " + Common.LastException(processBA).getMessage());
        }
        return false;
    }

    public static boolean _vvvvvvvvv7() throws Exception {
        try {
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) _vvvvvvv1.RunMethod("getActiveRecordingConfigurations", (Object[]) Common.Null));
            if (list.IsInitialized() && list.getSize() > 0) {
                new JavaObject();
                if (list.getSize() <= 0) {
                    return true;
                }
                try {
                    int iObjectToNumber = (int) BA.ObjectToNumber(((JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(0))).RunMethod("getClientUid", (Object[]) Common.Null));
                    if (iObjectToNumber == _vvvvvvvvv0) {
                        return true;
                    }
                    _vvvvv3("[RECORDING] Active uid=" + BA.NumberToString(iObjectToNumber));
                    _vvvvvvvvv0 = iObjectToNumber;
                    return true;
                } catch (Exception e) {
                    processBA.setLastException(e);
                    Common.LogImpl("42752526", "getClientUid failed: " + Common.LastException(processBA).getMessage(), 0);
                    return true;
                }
            }
            if (_vvvvvvvvv0 != -1) {
                _vvvvv3("[RECORDING] Stopped (was uid=" + BA.NumberToString(_vvvvvvvvv0) + ")");
                _vvvvvvvvv0 = -1;
            }
        } catch (Exception e2) {
            processBA.setLastException(e2);
            Common.LogImpl("42752539", "Error checking input: " + BA.ObjectToString(Common.LastException(processBA)), 0);
            _vvvvv3("[ERROR] IsAudioRecordingActive: " + Common.LastException(processBA).getMessage());
        }
        return false;
    }

    public static String _vvvvvvvvvv1(int i, boolean z, boolean z2) throws Exception {
        try {
            Common.LogImpl("42686982", "[STATE] Mode: " + BA.NumberToString(i) + " | SCO: " + BA.ObjectToString(Boolean.valueOf(BA.ObjectToBoolean(_vvvvvvv1.RunMethod("isBluetoothScoOn", (Object[]) Common.Null)))) + " | Music: " + BA.ObjectToString(Boolean.valueOf(BA.ObjectToBoolean(_vvvvvvv1.RunMethod("isMusicActive", (Object[]) Common.Null)))) + " | Speaker: " + BA.ObjectToString(Boolean.valueOf(BA.ObjectToBoolean(_vvvvvvv1.RunMethod("isSpeakerphoneOn", (Object[]) Common.Null)))) + " | Rec: " + BA.ObjectToString(Boolean.valueOf(z)) + " | Speak: " + BA.ObjectToString(Boolean.valueOf(z2)), 0);
            return "";
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("42686984", "Error logging state", 0);
            return "";
        }
    }

    public static String _vvvvvvvv6(int i) throws Exception {
        if (i == 0) {
            return "IDLE(0)";
        }
        if (i == 1) {
            return "STOPPED(1)";
        }
        if (i == 2) {
            return "STARTED(2)";
        }
        if (i == 3) {
            return "PAUSED(3)";
        }
        return "UNKNOWN(" + BA.NumberToString(i) + ")";
    }

    public static String _process_globals() throws Exception {
        _vvvvvvvvvv2 = new NotificationWrapper();
        _vvvvvvvvvv3 = new Timer();
        _vvvvvvv1 = new JavaObject();
        _vvvvvvv4 = "";
        _vvvvvvvvvv4 = -1;
        _vvvvvvv5 = false;
        _vvvvvvvvvv5 = new Phone.PhoneWakeState();
        _vvvvvvvvvv6 = 5000L;
        _vvvvvvvvvv7 = 0L;
        _vvvvvvvvvv0 = 1000;
        _fast_interval = 250;
        _vvvvvvvv1 = 0L;
        _vvvvvvvv4 = 0L;
        _vvvvvvvvvvv1 = -1;
        _vvvvvvvvvvv2 = false;
        _vvvvvvvvvvv3 = false;
        _vvvvvvv3 = false;
        _vvvvvvvvv0 = -1;
        _diag_file = main.vvv13(new byte[]{55, 41, -75, 51, ConnectorUtils.NULL, 52, -77, 102, 114, 39, -70, 116}, 50784);
        _diag_enabled_file = main.vvv13(new byte[]{55, 45, -98, -79, ConnectorUtils.NULL, 57, -103, -30, 62, 59, -116, -26, 113, 57, -98, -94}, 870618);
        _vvvvvvvvvvv4 = 0L;
        _vvvvvvvvvvv5 = false;
        _vvvvvvvvvvv6 = 0L;
        _vvvvvvvvvvv7 = true;
        _vvvvvvvvvvv0 = 1200;
        _vvvvvv7 = new Object();
        _vvvvvv0 = false;
        _vvvvvvvvvvvv1 = false;
        _vvvvvvvvv5 = false;
        _vvvvvvvvv4 = 0L;
        _vvvvvvvvv6 = 10000;
        _vvvvvvvvvvvv2 = false;
        _vvvvvvvvvvvv3 = true;
        _vvvvvvvvvvvv4 = false;
        _vvvvvvv7 = 0;
        _vvvvvvv0 = 0;
        return "";
    }

    public static String _vvvvvvvvvvvv5() throws Exception {
        Object obj = _vvvvvv7;
        if (obj == null || !_vvvvvv0) {
            return "";
        }
        try {
            Common.LogImpl("43604485", "[FOCUS] Shield released (res=" + BA.NumberToString((int) BA.ObjectToNumber(_vvvvvvv1.RunMethod("abandonAudioFocus", new Object[]{obj}))) + ")", 0);
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("43604487", "[FOCUS] Release failed: " + BA.ObjectToString(Common.LastException(processBA)), 0);
        }
        _vvvvvv0 = false;
        return "";
    }

    public static void _vvvvvvvvvvvv6() throws Exception {
        new ResumableSub_ReleaseSCO(null).resume(processBA, null);
    }

    public static class ResumableSub_ReleaseSCO extends BA.ResumableSub {
        audioservice parent;

        public ResumableSub_ReleaseSCO(audioservice audioserviceVar) {
            this.parent = audioserviceVar;
        }

        @Override // anywheresoftware.b4a.BA.ResumableSub
        public void resume(BA ba, Object[] objArr) throws Exception {
            while (true) {
                switch (this.state) {
                    case -1:
                        return;
                    case 0:
                        this.state = 1;
                        break;
                    case 1:
                        this.state = 6;
                        if (audioservice._vvvvvvvvvvv5) {
                            this.state = 3;
                        }
                        break;
                    case 3:
                        this.state = 6;
                        return;
                    case 6:
                        this.state = 7;
                        audioservice._vvvvvvvvvvv5 = true;
                        Common.LogImpl("43276805", "Timeout Expired. Releasing Mic.", 0);
                        break;
                    case 7:
                        this.state = 10;
                        DateTime dateTime = Common.DateTime;
                        if (DateTime.getNow() - audioservice._vvvvvvvvvvv6 >= 5000) {
                            this.state = 9;
                        }
                        break;
                    case 9:
                        this.state = 10;
                        BA ba2 = audioservice.processBA;
                        main mainVar = audioservice.mostCurrent._vvvvvv5;
                        Common.CallSubDelayed2(ba2, main.getObject(), "LogToScreen", "> Session Ended. Releasing Mic.");
                        DateTime dateTime2 = Common.DateTime;
                        audioservice._vvvvvvvvvvv6 = DateTime.getNow();
                        break;
                    case 10:
                        this.state = 11;
                        audioservice._vvvvv3("[SCO] Release start");
                        audioservice._vvvvvvvv5("pre-release");
                        Phone.PhoneWakeState phoneWakeState = audioservice._vvvvvvvvvv5;
                        Phone.PhoneWakeState.ReleaseKeepAlive();
                        Common.LogImpl("43276819", "[SCREEN] Screen WakeLock Released", 0);
                        audioservice._vvvvv3("[SCREEN] WakeLock released");
                        DateTime dateTime3 = Common.DateTime;
                        audioservice._vvvvvvvvvvv4 = DateTime.getNow() + 3000;
                        break;
                    case 11:
                        this.state = 26;
                        if (audioservice._vvvvvvvvvvvv3 && audioservice._vvvvvvvvv5) {
                            this.state = 13;
                        } else {
                            this.state = 15;
                        }
                        break;
                    case 13:
                        this.state = 26;
                        Common.LogImpl("43276836", "[CLEANUP] ZERO cleanup (WasMusicActive=True) - letting system timeout naturally", 0);
                        audioservice._vvvvv3("[CLEANUP] Zero (media mode)");
                        break;
                    case 15:
                        this.state = 16;
                        Common.LogImpl("43276842", "[CLEANUP] Full cleanup (WasMusicActive=False)", 0);
                        audioservice._vvvvv3("[CLEANUP] Full cleanup");
                        break;
                    case 16:
                        this.state = 21;
                        if (audioservice._vvvvvvvvvvv7) {
                            this.state = 18;
                        }
                        break;
                    case 18:
                        this.state = 21;
                        audioservice._vvvvvv6();
                        break;
                    case 21:
                        this.state = 22;
                        audioservice._vvvvvvv1.RunMethod("clearCommunicationDevice", (Object[]) Common.Null);
                        Common.Sleep(audioservice.processBA, this, 200);
                        this.state = 34;
                        return;
                    case 22:
                        this.state = 25;
                        if (audioservice._vvvvvv0) {
                            this.state = 24;
                        }
                        break;
                    case 24:
                        this.state = 25;
                        Common.Sleep(audioservice.processBA, this, audioservice._vvvvvvvvvvv0);
                        this.state = 36;
                        return;
                    case 25:
                        this.state = 26;
                        break;
                    case 26:
                        this.state = 27;
                        audioservice._vvvvvvv5 = false;
                        audioservice._vvvvvvvv5("post-release");
                        break;
                    case 27:
                        this.state = 30;
                        if (audioservice._vvvvvvvvvvvv2) {
                            this.state = 29;
                        }
                        break;
                    case KeyCodes.KEYCODE_A /* 29 */:
                        this.state = 30;
                        Common.Sleep(audioservice.processBA, this, 500);
                        this.state = 37;
                        return;
                    case 30:
                        this.state = 33;
                        aggressivemode aggressivemodeVar = audioservice.mostCurrent._vvvvv1;
                        if (aggressivemode._v5(audioservice.processBA) && audioservice._vvvvvvv0 > 0) {
                            this.state = 32;
                        }
                        break;
                    case 32:
                        this.state = 33;
                        audioservice._vvvvv3("[AGGRESSIVE] Session end | re-asserts=" + BA.NumberToString(audioservice._vvvvvvv0));
                        break;
                    case 33:
                        this.state = -1;
                        audioservice._vvvvvvv0 = 0;
                        audioservice._vvvvvvvvvvvv1 = false;
                        audioservice._vvvvvvvvv5 = false;
                        audioservice._vvvvvvvvv4 = 0L;
                        audioservice._vvvvvvvvvvv5 = false;
                        audioservice._vvvvv3("[SESSION] Closed");
                        break;
                    case 34:
                        this.state = 22;
                        audioservice._vvvvvvv1.RunMethod("stopBluetoothSco", (Object[]) Common.Null);
                        audioservice._vvvvvvv1.RunMethod("setBluetoothScoOn", new Object[]{false});
                        Common.Sleep(audioservice.processBA, this, 200);
                        this.state = 35;
                        return;
                    case 35:
                        this.state = 22;
                        audioservice._vvvvvvv1.RunMethod("setMode", new Object[]{0});
                        break;
                    case 36:
                        this.state = 25;
                        audioservice._vvvvvvvvvvvv5();
                        break;
                    case 37:
                        this.state = 30;
                        audioservice._vvvvvvvv5("post-release +500ms");
                        Common.Sleep(audioservice.processBA, this, 1000);
                        this.state = 38;
                        return;
                    case 38:
                        this.state = 30;
                        audioservice._vvvvvvvv5("post-release +1500ms");
                        break;
                }
            }
        }
    }

    public static String _service_create() throws Exception {
        main mainVar = mostCurrent._vvvvvv5;
        main._v0 = true;
        _vvvvvvvvvv2.Initialize2(2);
        _vvvvvvvvvv2.setIcon("icon");
        NotificationWrapper notificationWrapper = _vvvvvvvvvv2;
        BA ba = processBA;
        CharSequence charSequenceObjectToCharSequence = BA.ObjectToCharSequence("AAMotoMic");
        CharSequence charSequenceObjectToCharSequence2 = BA.ObjectToCharSequence("Monitoring Audio Routing...");
        main mainVar2 = mostCurrent._vvvvvv5;
        notificationWrapper.SetInfoNew(ba, charSequenceObjectToCharSequence, charSequenceObjectToCharSequence2, main.getObject());
        _vvvvvvvvvv3.Initialize(processBA, "timerMonitor", 1000L);
        JavaObject javaObject = new JavaObject();
        javaObject.InitializeContext(processBA);
        JavaObject javaObjectRunMethodJO = javaObject.RunMethodJO("getSystemService", new Object[]{"audio"});
        _vvvvvvv1 = javaObjectRunMethodJO;
        try {
            _vvvvvv7 = javaObjectRunMethodJO.CreateEvent(processBA, "android.media.AudioManager$OnAudioFocusChangeListener", "af", Common.Null);
            return "";
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("42490393", "Focus listener init failed: " + BA.ObjectToString(Common.LastException(processBA)), 0);
            return "";
        }
    }

    public static String _service_destroy() throws Exception {
        main mainVar = mostCurrent._vvvvvv5;
        main._v0 = false;
        _vvvvvvvvvv3.setEnabled(false);
        Phone.PhoneWakeState.ReleaseKeepAlive();
        _vvvvvvv1.RunMethod("stopBluetoothSco", (Object[]) Common.Null);
        _vvvvvvv1.RunMethod("setBluetoothScoOn", new Object[]{false});
        _vvvvvvv1.RunMethod("setMode", new Object[]{0});
        _vvvvvvv1.RunMethod("clearCommunicationDevice", (Object[]) Common.Null);
        _vvvvvvv5 = false;
        Common.LogImpl("43997711", "Service Destroyed", 0);
        BA ba = processBA;
        main mainVar2 = mostCurrent._vvvvvv5;
        Common.CallSubDelayed2(ba, main.getObject(), "LogToScreen", "> Service STOPPED");
        BA ba2 = processBA;
        main mainVar3 = mostCurrent._vvvvvv5;
        Common.CallSubDelayed(ba2, main.getObject(), "CheckStatus");
        return "";
    }

    public static String _service_start(IntentWrapper intentWrapper) throws Exception {
        main mainVar = mostCurrent._vvvvvv5;
        main._v0 = true;
        mostCurrent._service.StartForeground(1, (Notification) _vvvvvvvvvv2.getObject());
        File file = Common.File;
        File file2 = Common.File;
        if (File.Exists(File.getDirInternal(), "mac.txt")) {
            File file3 = Common.File;
            File file4 = Common.File;
            _vvvvvvv4 = File.ReadString(File.getDirInternal(), "mac.txt");
            _vvvvvvvvvv0 = 1000;
            File file5 = Common.File;
            File file6 = Common.File;
            if (File.Exists(File.getDirInternal(), "interval.txt")) {
                File file7 = Common.File;
                File file8 = Common.File;
                int i = (int) Double.parseDouble(File.ReadString(File.getDirInternal(), "interval.txt"));
                if (i == 0) {
                    _vvvvvvvvvv0 = 500;
                } else if (i == 1) {
                    _vvvvvvvvvv0 = 1000;
                } else if (i == 2) {
                    _vvvvvvvvvv0 = 2000;
                } else if (i == 3) {
                    _vvvvvvvvvv0 = 5000;
                }
            }
            if (_vvvvvvvvvv0 > 2000) {
                _vvvvvvvvvv0 = 2000;
            }
            _vvvvvvvvvv6 = 5000L;
            File file9 = Common.File;
            File file10 = Common.File;
            if (File.Exists(File.getDirInternal(), "timeout.txt")) {
                File file11 = Common.File;
                File file12 = Common.File;
                int i2 = (int) Double.parseDouble(File.ReadString(File.getDirInternal(), "timeout.txt"));
                if (i2 == 0) {
                    _vvvvvvvvvv6 = 1500L;
                } else if (i2 == 1) {
                    _vvvvvvvvvv6 = 3000L;
                } else if (i2 == 2) {
                    _vvvvvvvvvv6 = 5000L;
                } else if (i2 == 3) {
                    _vvvvvvvvvv6 = 10000L;
                }
            }
            _vvvvvvvvvv3.setInterval(_vvvvvvvvvv0);
            Common.LogImpl("42555947", "Monitoring: " + _vvvvvvv4 + " | Idle: " + BA.NumberToString(_vvvvvvvvvv0) + "ms", 0);
            BA ba = processBA;
            main mainVar2 = mostCurrent._vvvvvv5;
            Common.CallSubDelayed2(ba, main.getObject(), "LogToScreen", "> Service STARTED (Delay: " + BA.NumberToString(_vvvvvvvvvv6) + "ms)");
            _vvvvv3("Service started | target=" + _vvvvvvv4 + " | idle=" + BA.NumberToString(_vvvvvvvvvv0) + "ms | timeout=" + BA.NumberToString(_vvvvvvvvvv6) + "ms");
            _vvvvv3("=== DEVICE INFO ===");
            StringBuilder sb = new StringBuilder("Manufacturer: ");
            sb.append(_vvvvvvvv0());
            _vvvvv3(sb.toString());
            StringBuilder sb2 = new StringBuilder("Model: ");
            sb2.append(_vvvvvvvvv1());
            _vvvvv3(sb2.toString());
            _vvvvv3("Android: " + _vvvvvvvv7() + " (SDK " + BA.NumberToString(_vvvvvvvvv2()) + ")");
            _vvvvv3("===================");
            _vvvvvvvvvv3.setEnabled(true);
            BA ba2 = processBA;
            main mainVar3 = mostCurrent._vvvvvv5;
            Common.CallSubDelayed(ba2, main.getObject(), "CheckStatus");
            return "";
        }
        Common.LogImpl("42555963", "No MAC saved. Stopping.", 0);
        BA ba3 = processBA;
        main mainVar4 = mostCurrent._vvvvvv5;
        Common.CallSubDelayed2(ba3, main.getObject(), "LogToScreen", "> Error: No MAC saved. Service stopping.");
        _vvvvv3("Service start failed (no MAC saved)");
        Common.StopService(processBA, getObject());
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0373  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String _timermonitor_tick() throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tillekesoft.aamotomic.audioservice._timermonitor_tick():java.lang.String");
    }
}
