package com.tillekesoft.aamotomic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.B4AException;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.collections.Map;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class starter extends Service {
    static starter mostCurrent;
    public static BA processBA;
    private ServiceHelper _service;
    public Common __c = null;
    public main _vvvvvv5 = null;
    public audioservice _vvvvv5 = null;
    public btreceiver _vvvvvv3 = null;
    public diaglog _vvvvvv4 = null;
    public readmeviewer _vvvvv2 = null;
    public aggressivemode _vvvvv1 = null;

    public static boolean _application_error(B4AException b4AException, String str) throws Exception {
        return true;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class starter_BR extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BA.LogInfo("** Receiver (starter) OnReceive **");
            Intent intent2 = new Intent(context, (Class<?>) starter.class);
            if (intent != null) {
                intent2.putExtra("b4a_internal_intent", intent);
            }
            ServiceHelper.StarterHelper.startServiceFromReceiver(context, intent2, true, BA.class);
        }
    }

    public static Class<?> getObject() {
        return starter.class;
    }

    @Override // android.app.Service
    public void onCreate() {
        starter starterVar;
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
            starterVar = this;
            BA ba = new BA(starterVar, (BALayout) null, (BA) null, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.starter");
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
            starterVar = this;
        }
        starterVar._service = new ServiceHelper(this);
        processBA.service = starterVar;
        if (BA.isShellModeRuntimeCheck(processBA)) {
            BA ba2 = processBA;
            ba2.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.starter", ba2, starterVar._service, Float.valueOf(Common.Density));
        }
        processBA.setActivityPaused(false);
        BA.LogInfo("*** Service (starter) Create ***");
        processBA.raiseEvent(null, "service_create", new Object[0]);
        processBA.runHook("oncreate", this, null);
        if (ServiceHelper.StarterHelper.runWaitForLayouts()) {
            return;
        }
        BA.LogInfo("stopping spontaneous created service");
        stopSelf();
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        onStartCommand(intent, 0, 0);
    }

    @Override // android.app.Service
    public int onStartCommand(final Intent intent, int i, int i2) {
        if (!ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() { // from class: com.tillekesoft.aamotomic.starter.1
            @Override // java.lang.Runnable
            public void run() {
                starter.this.handleStart(intent);
            }
        })) {
            ServiceHelper.StarterHelper.addWaitForLayout(new Runnable() { // from class: com.tillekesoft.aamotomic.starter.2
                @Override // java.lang.Runnable
                public void run() {
                    starter.processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (starter) Create **");
                    starter.processBA.raiseEvent(null, "service_create", new Object[0]);
                    starter.this.handleStart(intent);
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
        processBA.raiseEvent(null, "service_taskremoved", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStart(Intent intent) {
        BA.LogInfo("** Service (starter) Start **");
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
        BA.LogInfo("** Service (starter) Timeout **");
        Map map = new Map();
        map.Initialize();
        map.Put("StartId", Integer.valueOf(i));
        processBA.raiseEvent(null, "service_timeout", map);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        BA.LogInfo("** Service (starter) Destroy (ignored)**");
    }

    public static String _process_globals() throws Exception {
        return "";
    }

    public static String _service_create() throws Exception {
        return "";
    }

    public static String _service_destroy() throws Exception {
        return "";
    }

    public static String _service_start(IntentWrapper intentWrapper) throws Exception {
        mostCurrent._service.StopAutomaticForeground();
        return "";
    }

    public static String _service_taskremoved() throws Exception {
        return "";
    }
}
