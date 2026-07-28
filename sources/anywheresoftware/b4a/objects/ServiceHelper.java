package anywheresoftware.b4a.objects;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.B4AApplication;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DateTime;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ServiceHelper {
    public static final int AUTOMATIC_FOREGROUND_ALWAYS = 3;
    public static final int AUTOMATIC_FOREGROUND_NEVER = 1;
    public static final int AUTOMATIC_FOREGROUND_WHEN_NEEDED = 2;
    public static final String AUTO_WAKE_ID = "b4a_wakelock";
    public static final String FOREGROUND_KEY = "b4a_foreground";
    public Notification AutomaticForegroundNotification;
    public int autoNotificationId;
    private Service service;
    public int AutomaticForegroundMode = 2;
    NotificationManager mNM = (NotificationManager) BA.applicationContext.getSystemService("notification");

    public static void init() {
    }

    public ServiceHelper(Service service) {
        this.service = service;
    }

    public void StartForeground(int i, Notification notification) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.service.startForeground(i, notification);
    }

    public void StopForeground(int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.service.stopForeground(true);
    }

    public void StopAutomaticForeground() {
        if (this.autoNotificationId > 0) {
            this.service.stopForeground(true);
            this.autoNotificationId = 0;
        }
    }

    public static class StarterHelper {
        private static boolean alreadyRun;
        private static boolean insideHandler;
        private static BA serviceProcessBA;
        private static Runnable waitForLayouts;
        private static int wakeLockId;
        private static final HashMap<Integer, PowerManager.WakeLock> wakeLocks = new HashMap<>();

        public static void startServiceFromReceiver(Context context, Intent intent, boolean z, Class<?> cls) {
            if (z) {
                BA.LogError("The Starter service should never be started from a receiver.");
            }
            if (cls.getName().equals("anywheresoftware.b4a.ShellBA") && BA.applicationContext == null) {
                BA.LogError("Cannot start from a receiver in debug mode.");
                return;
            }
            boolean zIsAnyActivityVisible = BA.isAnyActivityVisible();
            if (!zIsAnyActivityVisible && context.getPackageManager().checkPermission("android.permission.WAKE_LOCK", BA.packageName) == 0) {
                wakeLockId++;
                PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, String.valueOf(intent.getComponent()));
                wakeLockNewWakeLock.setReferenceCounted(false);
                wakeLockNewWakeLock.acquire(DateTime.TicksPerMinute);
                wakeLocks.put(Integer.valueOf(wakeLockId), wakeLockNewWakeLock);
                intent.putExtra(ServiceHelper.AUTO_WAKE_ID, wakeLockId);
            }
            if (Build.VERSION.SDK_INT < 26 || zIsAnyActivityVisible) {
                try {
                    context.startService(intent);
                    return;
                } catch (IllegalStateException e) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        intent.putExtra(ServiceHelper.FOREGROUND_KEY, true);
                        context.startForegroundService(intent);
                        return;
                    }
                    throw new RuntimeException(e);
                }
            }
            intent.putExtra(ServiceHelper.FOREGROUND_KEY, true);
            context.startForegroundService(intent);
        }

        public static IntentWrapper handleStartIntent(Intent intent, ServiceHelper serviceHelper, BA ba) {
            IntentWrapper intentWrapper = new IntentWrapper();
            boolean z = false;
            if (intent != null) {
                boolean booleanExtra = intent.getBooleanExtra(ServiceHelper.FOREGROUND_KEY, false);
                int intExtra = intent.getIntExtra(ServiceHelper.AUTO_WAKE_ID, 0);
                if (intExtra > 0) {
                    wakeLocks.remove(Integer.valueOf(intExtra)).release();
                }
                if (intent.hasExtra("b4a_internal_intent")) {
                    intentWrapper.setObject((Intent) intent.getParcelableExtra("b4a_internal_intent"));
                } else {
                    intentWrapper.setObject(intent);
                }
                z = booleanExtra;
            }
            if (z) {
                BA.LogInfo("Service started in foreground mode.");
            }
            if (serviceHelper.AutomaticForegroundMode == 1 || !(serviceHelper.AutomaticForegroundMode == 3 || (serviceHelper.AutomaticForegroundMode == 2 && z))) {
                return intentWrapper;
            }
            if (serviceHelper.AutomaticForegroundNotification == null) {
                serviceHelper.AutomaticForegroundNotification = createAutoNotification(serviceHelper, ba);
            }
            serviceHelper.autoNotificationId = 51042;
            try {
                serviceHelper.StartForeground(serviceHelper.autoNotificationId, serviceHelper.AutomaticForegroundNotification);
                return intentWrapper;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static Notification createAutoNotification(ServiceHelper serviceHelper, BA ba) {
            NotificationWrapper notificationWrapper = new NotificationWrapper();
            notificationWrapper.Initialize2(2);
            notificationWrapper.setIcon("icon");
            try {
                B4AApplication b4AApplication = Common.Application;
                String labelName = B4AApplication.getLabelName();
                B4AApplication b4AApplication2 = Common.Application;
                notificationWrapper.SetInfoNew(ba, labelName, B4AApplication.getLabelName(), Class.forName(String.valueOf(BA.packageName) + ".main"));
                return (Notification) notificationWrapper.getObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static boolean startFromActivity(Activity activity, BA ba, Runnable runnable, boolean z) {
            if (alreadyRun || z) {
                return true;
            }
            alreadyRun = true;
            addWaitForLayout(runnable);
            try {
                Common.StartService(ba, "starter");
                return false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public static boolean startFromServiceCreate(BA ba, boolean z) {
            if (alreadyRun || z) {
                return true;
            }
            alreadyRun = true;
            serviceProcessBA = ba;
            return false;
        }

        public static boolean runWaitForLayouts() {
            if (waitForLayouts == null) {
                return false;
            }
            BA.handler.post(waitForLayouts);
            return true;
        }

        public static void addWaitForLayout(Runnable runnable) {
            waitForLayouts = runnable;
        }

        public static void removeWaitForLayout() {
            waitForLayouts = null;
        }

        public static boolean onStartCommand(BA ba, Runnable runnable) {
            if (ba != null && ba == serviceProcessBA) {
                try {
                    Common.StartService(ba, "starter");
                    serviceProcessBA = null;
                    return false;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ba.isActivityPaused() && waitForLayouts != null) {
                BA.handler.postDelayed(runnable, 500L);
                return true;
            }
            runnable.run();
            return true;
        }

        public static boolean handleUncaughtException(Throwable th, BA ba) throws Exception {
            if (insideHandler) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
                return true;
            }
            try {
                insideHandler = true;
                if (!alreadyRun) {
                    return false;
                }
                if (!Common.SubExists(ba, "starter", "application_error")) {
                    return false;
                }
                if (Common.IsPaused(ba, "starter")) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
                    return true;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
                th.printStackTrace(printWriter);
                printWriter.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                B4AException b4AException = new B4AException();
                if (th instanceof Exception) {
                    b4AException.setObject((Exception) th);
                } else {
                    b4AException.setObject(new Exception(th));
                }
                if (Boolean.TRUE.equals((Boolean) Common.CallSubNew3(ba, "starter", "application_error", b4AException, Common.BytesToString(byteArray, 0, byteArray.length, "UTF8")))) {
                    Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                    if (defaultUncaughtExceptionHandler instanceof BA.B4AExceptionHandler) {
                        ((BA.B4AExceptionHandler) defaultUncaughtExceptionHandler).original.uncaughtException(Thread.currentThread(), th);
                    } else {
                        defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
                    }
                }
                return true;
            } finally {
                insideHandler = false;
            }
        }

        public static void callOSExceptionHandler(B4AException b4AException) {
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler instanceof BA.B4AExceptionHandler) {
                ((BA.B4AExceptionHandler) defaultUncaughtExceptionHandler).original.uncaughtException(Thread.currentThread(), b4AException.getObject());
            } else {
                defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), b4AException.getObject());
            }
        }
    }
}
