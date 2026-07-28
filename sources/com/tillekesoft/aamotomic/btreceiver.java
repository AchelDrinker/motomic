package com.tillekesoft.aamotomic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.ReceiverHelper;
import anywheresoftware.b4a.objects.streams.File;
import anywheresoftware.b4j.object.JavaObject;

/* JADX INFO: loaded from: classes.dex */
public class btreceiver extends BroadcastReceiver {
    private static boolean firstTime = true;
    static btreceiver mostCurrent;
    public static BA processBA;
    private ReceiverHelper _receiver;
    public Common __c = null;
    public ReceiverHelper _vvvvvvvvvvvv0 = null;
    public main _vvvvvv5 = null;
    public starter _vvvvvv2 = null;
    public audioservice _vvvvv5 = null;
    public diaglog _vvvvvv4 = null;
    public readmeviewer _vvvvv2 = null;
    public aggressivemode _vvvvv1 = null;

    public static Class<?> getObject() {
        return btreceiver.class;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        mostCurrent = this;
        if (processBA == null) {
            BA ba = new BA(context, (BALayout) null, (BA) null, BA.SharedProcessBA.ModuleType.RECEIVER, "com.tillekesoft.aamotomic.btreceiver");
            processBA = ba;
            if (BA.isShellModeRuntimeCheck(ba)) {
                processBA.raiseEvent2(null, true, "SHELL", false, new Object[0]);
            }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals", null).invoke(null, null);
                processBA.loadHtSubs(getClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this._receiver = new ReceiverHelper(this);
        if (BA.isShellModeRuntimeCheck(processBA)) {
            BA ba2 = processBA;
            ba2.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.btreceiver", ba2, this._receiver, Float.valueOf(Common.Density));
        }
        processBA.setActivityPaused(false);
        StringBuilder sb = new StringBuilder("*** Receiver (btreceiver) Receive ");
        sb.append(firstTime ? "(first time)" : "");
        sb.append(" ***");
        BA.LogInfo(sb.toString());
        IntentWrapper intentWrapper = new IntentWrapper();
        intentWrapper.setObject(intent);
        processBA.raiseEvent(null, "receiver_receive", Boolean.valueOf(firstTime), intentWrapper);
        firstTime = false;
    }

    public static String _vvvvvvvvvvvv7(IntentWrapper intentWrapper) throws Exception {
        try {
            new JavaObject();
            JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), intentWrapper.getObject());
            new JavaObject();
            JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getParcelableExtra", new Object[]{"android.bluetooth.device.extra.DEVICE"}));
            if (javaObject2.IsInitialized()) {
                return BA.ObjectToString(javaObject2.RunMethod("getAddress", (Object[]) Common.Null));
            }
            Common.LogImpl("44194315", "Error: Intent did not contain a valid BluetoothDevice", 0);
            return "";
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("44194319", "Error parsing MAC: " + BA.ObjectToString(Common.LastException(processBA)), 0);
            return "";
        }
    }

    public static String _process_globals() throws Exception {
        return "";
    }

    public static String _receiver_receive(boolean z, IntentWrapper intentWrapper) throws Exception {
        String action;
        boolean zObjectToBoolean;
        try {
            action = intentWrapper.getAction();
            Common.LogImpl("44128771", "Receiver Event: " + action, 0);
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("44128833", "Receiver Error: " + BA.ObjectToString(Common.LastException(processBA)), 0);
        }
        if (!action.contains("BOOT_COMPLETED") && !action.contains("QUICKBOOT")) {
            File file = Common.File;
            File file2 = Common.File;
            if (!File.Exists(File.getDirInternal(), "mac.txt")) {
                return "";
            }
            File file3 = Common.File;
            File file4 = Common.File;
            String strReadString = File.ReadString(File.getDirInternal(), "mac.txt");
            if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                String str_vvvvvvvvvvvv7 = _vvvvvvvvvvvv7(intentWrapper);
                Common.LogImpl("44128790", "Device Connected: " + str_vvvvvvvvvvvv7, 0);
                if (!str_vvvvvvvvvvvv7.equals("") && str_vvvvvvvvvvvv7.equalsIgnoreCase(strReadString)) {
                    File file5 = Common.File;
                    File file6 = Common.File;
                    if (File.Exists(File.getDirInternal(), "autostart.txt")) {
                        File file7 = Common.File;
                        File file8 = Common.File;
                        zObjectToBoolean = BA.ObjectToBoolean(File.ReadString(File.getDirInternal(), "autostart.txt"));
                    } else {
                        zObjectToBoolean = true;
                    }
                    if (zObjectToBoolean) {
                        Common.LogImpl("44128801", "Target Device Found! Starting AudioService...", 0);
                        BA ba = processBA;
                        main mainVar = mostCurrent._vvvvvv5;
                        Common.CallSubNew2(ba, main.getObject(), "LogToScreen", "> Cardo Connected! Starting Service...");
                        BA ba2 = processBA;
                        audioservice audioserviceVar = mostCurrent._vvvvv5;
                        Common.StartService(ba2, audioservice.getObject());
                    } else {
                        Common.LogImpl("44128809", "Target Device Found, but Auto-Start is disabled. Service not started.", 0);
                        BA ba3 = processBA;
                        main mainVar2 = mostCurrent._vvvvvv5;
                        Common.CallSubNew2(ba3, main.getObject(), "LogToScreen", "> Cardo Connected (Auto-Start OFF - Service not started)");
                    }
                }
            } else if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                String str_vvvvvvvvvvvv72 = _vvvvvvvvvvvv7(intentWrapper);
                Common.LogImpl("44128817", "Device Disconnected: " + str_vvvvvvvvvvvv72, 0);
                if (!str_vvvvvvvvvvvv72.equals("") && str_vvvvvvvvvvvv72.equalsIgnoreCase(strReadString)) {
                    Common.LogImpl("44128821", "Target Device Gone. Stopping AudioService.", 0);
                    BA ba4 = processBA;
                    main mainVar3 = mostCurrent._vvvvvv5;
                    Common.CallSubNew2(ba4, main.getObject(), "LogToScreen", "> Cardo Disconnected. Stopping Service.");
                    BA ba5 = processBA;
                    audioservice audioserviceVar2 = mostCurrent._vvvvv5;
                    Common.StopService(ba5, audioservice.getObject());
                }
            }
            return "";
        }
        Common.LogImpl("44128777", "Boot detected. System ready.", 0);
        return "";
    }
}
