package anywheresoftware.b4a;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.keywords.Common;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Thread;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public class BA {
    public static Application applicationContext = null;
    public static IBridgeLog bridgeLog = null;
    private static int checkStackTraceEvery50 = 0;
    public static String debugLine = null;
    public static int debugLineNum = 0;
    public static boolean debugMode = false;
    public static float density = 1.0f;
    public static NumberFormat numberFormat = null;
    public static NumberFormat numberFormat2 = null;
    public static String packageName = null;
    public static boolean shellMode = false;
    private static volatile B4AThreadPool threadPool;
    private static HashMap<String, ArrayList<Runnable>> uninitializedActivitiesMessagesDuringPaused;
    public static WarningEngine warningEngine;
    public final Activity activity;
    public final String className;
    public final Context context;
    public final Object eventsTarget;
    public final HashMap<String, Method> htSubs;
    public final BA processBA;
    public Service service;
    public final SharedProcessBA sharedProcessBA;
    public final BALayout vg;
    public HashMap<String, LinkedList<WaitForEvent>> waitForEvents;
    public static final Handler handler = new Handler();
    public static final Locale cul = Locale.US;
    public static final ThreadLocal<Object> senderHolder = new ThreadLocal<>();

    public @interface ActivityObject {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Author {
        String value();
    }

    public interface B4ARunnable extends Runnable {
    }

    public interface B4aDebuggable {
        Object[] debug(int i, boolean[] zArr);
    }

    public interface CheckForReinitialize {
        boolean IsInitialized();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CustomClass {
        String fileNameWithoutExtension();

        String name();

        int priority() default 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CustomClasses {
        CustomClass[] values();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DependsOn {
        String[] values();
    }

    public @interface DesignerName {
        String value();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DesignerProperties {
        Property[] values();
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DontInheritEvents {
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Events {
        String[] values();
    }

    public @interface Hide {
    }

    public interface IBridgeLog {
        void offer(String str);
    }

    public interface IterableList {
        Object Get(int i);

        int getSize();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Permissions {
        String[] values();
    }

    public @interface Pixel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Property {
        String defaultValue();

        String description() default "";

        String displayName();

        String fieldType();

        String key();

        String list() default "";

        String maxRange() default "";

        String minRange() default "";
    }

    @Target({ElementType.METHOD})
    public @interface RaisesSynchronousEvents {
    }

    public static abstract class ResumableSub {
        public int catchState;
        public boolean completed;
        public int state;
        public BA waitForBA;

        public abstract void resume(BA ba, Object[] objArr) throws Exception;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ShortName {
        String value();
    }

    public interface SubDelegator {
        public static final Object SubNotFound = new Object();

        Object callSub(String str, Object obj, Object[] objArr) throws Exception;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Version {
        float value();
    }

    static {
        Thread.setDefaultUncaughtExceptionHandler(new B4AExceptionHandler());
    }

    public static class SharedProcessBA {
        public WeakReference<BA> activityBA;
        ArrayList<Runnable> messagesDuringPaused;
        public final ModuleType moduleType;
        HashMap<Integer, WeakReference<IOnActivityResult>> onActivityResultMap;
        public Object sender;
        int numberOfStackedEvents = 0;
        Exception lastException = null;
        boolean ignoreEventsFromOtherThreadsDuringMsgboxError = false;
        volatile boolean isActivityPaused = true;
        int onActivityResultCode = 1;

        public enum ModuleType {
            ACTIVITY,
            SERVICE,
            RECEIVER;

            /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
            public static ModuleType[] valuesCustom() {
                ModuleType[] moduleTypeArrValuesCustom = values();
                int length = moduleTypeArrValuesCustom.length;
                ModuleType[] moduleTypeArr = new ModuleType[length];
                System.arraycopy(moduleTypeArrValuesCustom, 0, moduleTypeArr, 0, length);
                return moduleTypeArr;
            }
        }

        public SharedProcessBA(ModuleType moduleType) {
            this.moduleType = moduleType;
        }
    }

    public BA(BA ba, Object obj, HashMap<String, Method> map, String str) {
        this.vg = ba.vg;
        this.eventsTarget = obj;
        this.htSubs = map == null ? new HashMap<>() : map;
        this.processBA = null;
        this.activity = ba.activity;
        this.context = ba.context;
        this.service = ba.service;
        SharedProcessBA sharedProcessBA = ba.sharedProcessBA;
        this.sharedProcessBA = sharedProcessBA == null ? ba.processBA.sharedProcessBA : sharedProcessBA;
        this.className = str;
    }

    public BA(Context context, BALayout bALayout, BA ba, String str, String str2) {
        this(context, bALayout, ba, (context == null || !(context instanceof Service)) ? SharedProcessBA.ModuleType.ACTIVITY : SharedProcessBA.ModuleType.SERVICE, str2);
    }

    public BA(Context context, BALayout bALayout, BA ba, SharedProcessBA.ModuleType moduleType, String str) {
        Activity activity;
        if (context != null) {
            density = context.getResources().getDisplayMetrics().density;
            try {
                Class.forName("anywheresoftware.b4a.keywords.Common").getField("Density").set(null, Float.valueOf(density));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (context == null || !(context instanceof Activity)) {
            activity = null;
        } else {
            activity = (Activity) context;
            applicationContext = activity.getApplication();
        }
        if (moduleType == SharedProcessBA.ModuleType.SERVICE) {
            applicationContext = ((Service) context).getApplication();
        } else if (moduleType == SharedProcessBA.ModuleType.RECEIVER && applicationContext == null) {
            if (context.getApplicationContext() instanceof Application) {
                applicationContext = (Application) context.getApplicationContext();
            } else {
                LogInfo("application context not set.");
            }
        }
        if (context != null && packageName == null) {
            packageName = context.getPackageName();
            try {
                Class<?> cls = Class.forName("anywheresoftware.b4a.remotelogger.RemoteLogger");
                cls.getMethod("Start", null).invoke(cls.newInstance(), null);
            } catch (ClassNotFoundException unused) {
                System.out.println("Bridge logger not enabled.");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.eventsTarget = null;
        if (str.endsWith(".starter")) {
            this.context = applicationContext;
        } else {
            this.context = context;
        }
        this.activity = activity;
        this.htSubs = new HashMap<>();
        this.className = str;
        this.processBA = ba;
        this.vg = bALayout;
        if (ba == null) {
            this.sharedProcessBA = new SharedProcessBA(moduleType);
        } else {
            this.sharedProcessBA = null;
        }
    }

    public boolean subExists(String str) {
        BA ba = this.processBA;
        if (ba != null) {
            return ba.subExists(str);
        }
        return this.htSubs.containsKey(str);
    }

    public boolean runHook(String str, Object obj, Object[] objArr) {
        if (!subExists(str)) {
            return false;
        }
        try {
            Boolean bool = (Boolean) this.htSubs.get(str).invoke(obj, objArr);
            if (bool != null) {
                if (bool.booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object raiseEvent(Object obj, String str, Object... objArr) {
        return raiseEvent2(obj, false, str, false, objArr);
    }

    public Object raiseEvent2(Object obj, boolean z, String str, boolean z2, Object... objArr) {
        ThreadLocal<Object> threadLocal;
        BA ba = this.processBA;
        if (ba != null) {
            return ba.raiseEvent2(obj, z, str, z2, objArr);
        }
        if (this.sharedProcessBA.isActivityPaused && !z) {
            System.out.println("ignoring event: " + str);
            return null;
        }
        try {
            try {
                this.sharedProcessBA.numberOfStackedEvents++;
                threadLocal = senderHolder;
                threadLocal.set(obj);
            } finally {
                this.sharedProcessBA.numberOfStackedEvents--;
                senderHolder.set(null);
            }
        } catch (B4AUncaughtException e) {
            throw e;
        } catch (Throwable th) {
            Throwable cause = th;
            if (cause instanceof InvocationTargetException) {
                cause = cause.getCause();
            }
            if (cause instanceof B4AUncaughtException) {
                if (this.sharedProcessBA.numberOfStackedEvents > 1) {
                    throw ((B4AUncaughtException) cause);
                }
                System.out.println("catching B4AUncaughtException");
                return null;
            }
            String strPrintException = printException(cause, !debugMode);
            if (!debugMode) {
                try {
                    if (Boolean.TRUE.equals((Boolean) Class.forName("anywheresoftware.b4a.objects.ServiceHelper$StarterHelper").getDeclaredMethod("handleUncaughtException", Throwable.class, BA.class).invoke(null, cause, this))) {
                        return null;
                    }
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            if (this.sharedProcessBA.activityBA == null) {
                throw new RuntimeException(cause);
            }
            ShowErrorMsgbox(cause.toString(), strPrintException);
        }
        if (this.waitForEvents != null && checkAndRunWaitForEvent(obj, str, objArr)) {
            this.sharedProcessBA.numberOfStackedEvents--;
            threadLocal.set(null);
            return null;
        }
        Method method = this.htSubs.get(str);
        if (method != null) {
            try {
                Object objInvoke = method.invoke(this.eventsTarget, objArr);
                this.sharedProcessBA.numberOfStackedEvents--;
                threadLocal.set(null);
                return objInvoke;
            } catch (IllegalArgumentException unused) {
                throw new Exception("Sub " + str + " signature does not match expected signature.");
            }
        }
        if (z2) {
            throw new Exception("Sub " + str + " was not found.");
        }
        this.sharedProcessBA.numberOfStackedEvents--;
        threadLocal.set(null);
        return null;
    }

    public boolean checkAndRunWaitForEvent(Object obj, String str, Object[] objArr) throws Exception {
        LinkedList<WaitForEvent> linkedList = this.waitForEvents.get(str);
        if (linkedList == null) {
            return false;
        }
        Iterator<WaitForEvent> it = linkedList.iterator();
        while (it.hasNext()) {
            WaitForEvent next = it.next();
            if (next.senderFilter == null || (obj != null && obj == next.senderFilter.get())) {
                it.remove();
                next.rs.resume(this, objArr);
                senderHolder.set(null);
                return true;
            }
        }
        return false;
    }

    public void ShowErrorMsgbox(String str, String str2) {
        String str3;
        boolean z = true;
        this.sharedProcessBA.ignoreEventsFromOtherThreadsDuringMsgboxError = true;
        try {
            LogError(str);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.sharedProcessBA.activityBA.get().context);
            builder.setTitle("Error occurred");
            if (str2 != null) {
                str3 = "An error has occurred in sub:" + str2 + Common.CRLF;
            } else {
                str3 = "";
            }
            builder.setMessage(String.valueOf(str3) + str + "\nContinue?");
            Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
            builder.setPositiveButton("Yes", dialogResponse);
            builder.setNegativeButton("No", dialogResponse);
            AlertDialog alertDialogCreate = builder.create();
            if (this.sharedProcessBA.numberOfStackedEvents != 1) {
                z = false;
            }
            Msgbox.msgbox(alertDialogCreate, z);
            if (dialogResponse.res == -2) {
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        } finally {
            this.sharedProcessBA.ignoreEventsFromOtherThreadsDuringMsgboxError = false;
        }
    }

    public static String printException(Throwable th, boolean z) {
        String str;
        if (!shellMode) {
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                if (stackTraceElement.getClassName().startsWith(packageName)) {
                    String str2 = String.valueOf(stackTraceElement.getClassName().substring(packageName.length() + 1)) + stackTraceElement.getMethodName();
                    str = debugLine != null ? String.valueOf(str2) + " (B4A line: " + debugLineNum + ")\n" + debugLine : String.valueOf(str2) + " (java line: " + stackTraceElement.getLineNumber() + ")";
                }
            }
            str = "";
        } else {
            str = "";
        }
        if (z) {
            if (str.length() > 0) {
                LogError(str);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
            th.printStackTrace(printWriter);
            printWriter.close();
            try {
                LogError(new String(byteArrayOutputStream.toByteArray(), "UTF8"));
                return str;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void raiseEventFromUI(final Object obj, final String str, final Object... objArr) {
        BA ba = this.processBA;
        if (ba != null) {
            ba.raiseEventFromUI(obj, str, objArr);
        } else {
            handler.post(new B4ARunnable() { // from class: anywheresoftware.b4a.BA.1
                @Override // java.lang.Runnable
                public void run() {
                    if (BA.this.sharedProcessBA.ignoreEventsFromOtherThreadsDuringMsgboxError) {
                        BA.LogInfo("Event: " + str + ", was ignored.");
                        return;
                    }
                    if (BA.this.sharedProcessBA.moduleType == SharedProcessBA.ModuleType.ACTIVITY && BA.this.sharedProcessBA.activityBA == null) {
                        BA.LogInfo("Reposting event: " + str);
                        BA.handler.post(this);
                        return;
                    }
                    if (BA.this.sharedProcessBA.isActivityPaused) {
                        BA.LogInfo("Ignoring event: " + str);
                        return;
                    }
                    BA.this.raiseEvent2(obj, false, str, false, objArr);
                }
            });
        }
    }

    public Object raiseEventFromDifferentThread(final Object obj, final Object obj2, final int i, final String str, final boolean z, final Object[] objArr) {
        BA ba = this.processBA;
        if (ba != null) {
            return ba.raiseEventFromDifferentThread(obj, obj2, i, str, z, objArr);
        }
        handler.post(new B4ARunnable() { // from class: anywheresoftware.b4a.BA.2
            @Override // java.lang.Runnable
            public void run() {
                if (BA.this.sharedProcessBA.ignoreEventsFromOtherThreadsDuringMsgboxError) {
                    BA.Log("Event: " + str + ", was ignored.");
                    return;
                }
                if (BA.this.sharedProcessBA.moduleType == SharedProcessBA.ModuleType.ACTIVITY && BA.this.sharedProcessBA.activityBA == null) {
                    BA.Log("Reposting event: " + str);
                    BA.handler.post(this);
                    return;
                }
                if (BA.this.sharedProcessBA.isActivityPaused) {
                    if (BA.this.sharedProcessBA.moduleType == SharedProcessBA.ModuleType.SERVICE) {
                        BA.Log("Ignoring event as service was destroyed: " + str);
                        return;
                    }
                    BA.this.addMessageToPausedMessageQueue(str, this);
                    return;
                }
                Object obj3 = obj2;
                if (obj3 != null) {
                    BA.markTaskAsFinish(obj3, i);
                }
                BA.this.raiseEvent2(obj, false, str, z, objArr);
            }
        });
        return null;
    }

    public static void addMessageToUninitializeActivity(String str, String str2, Object obj, Object[] objArr) {
        if (uninitializedActivitiesMessagesDuringPaused == null) {
            uninitializedActivitiesMessagesDuringPaused = new HashMap<>();
        }
        ArrayList<Runnable> arrayList = uninitializedActivitiesMessagesDuringPaused.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            uninitializedActivitiesMessagesDuringPaused.put(str, arrayList);
        }
        if (arrayList.size() < 30) {
            RaiseEventWhenFirstCreate raiseEventWhenFirstCreate = new RaiseEventWhenFirstCreate(null);
            raiseEventWhenFirstCreate.eventName = str2;
            raiseEventWhenFirstCreate.arguments = objArr;
            raiseEventWhenFirstCreate.sender = obj;
            Log("sending message to waiting queue of uninitialized activity (" + str2 + ")");
            arrayList.add(raiseEventWhenFirstCreate);
        }
    }

    private static class RaiseEventWhenFirstCreate implements Runnable {
        Object[] arguments;
        BA ba;
        String eventName;
        Object sender;

        private RaiseEventWhenFirstCreate() {
        }

        /* synthetic */ RaiseEventWhenFirstCreate(RaiseEventWhenFirstCreate raiseEventWhenFirstCreate) {
            this();
        }

        @Override // java.lang.Runnable
        public void run() {
            this.ba.raiseEvent2(this.sender, true, this.eventName, true, this.arguments);
        }
    }

    public void addMessageToPausedMessageQueue(String str, Runnable runnable) {
        BA ba = this.processBA;
        if (ba != null) {
            ba.addMessageToPausedMessageQueue(str, runnable);
            return;
        }
        Log("sending message to waiting queue (" + str + ")");
        if (this.sharedProcessBA.messagesDuringPaused == null) {
            this.sharedProcessBA.messagesDuringPaused = new ArrayList<>();
        }
        if (this.sharedProcessBA.messagesDuringPaused.size() > 20) {
            Log("Ignoring event (too many queued events: " + str + ")");
            return;
        }
        this.sharedProcessBA.messagesDuringPaused.add(runnable);
    }

    public void setActivityPaused(boolean z) {
        HashMap<String, ArrayList<Runnable>> map;
        BA ba = this.processBA;
        if (ba != null) {
            ba.setActivityPaused(z);
            return;
        }
        this.sharedProcessBA.isActivityPaused = z;
        if (z || this.sharedProcessBA.moduleType != SharedProcessBA.ModuleType.ACTIVITY) {
            return;
        }
        if (this.sharedProcessBA.messagesDuringPaused == null && (map = uninitializedActivitiesMessagesDuringPaused) != null) {
            String str = this.className;
            this.sharedProcessBA.messagesDuringPaused = map.get(str);
            uninitializedActivitiesMessagesDuringPaused.remove(str);
        }
        if (this.sharedProcessBA.messagesDuringPaused == null || this.sharedProcessBA.messagesDuringPaused.size() <= 0) {
            return;
        }
        try {
            Log("running waiting messages (" + this.sharedProcessBA.messagesDuringPaused.size() + ")");
            for (Runnable runnable : this.sharedProcessBA.messagesDuringPaused) {
                if (runnable instanceof RaiseEventWhenFirstCreate) {
                    ((RaiseEventWhenFirstCreate) runnable).ba = this;
                }
                runnable.run();
            }
        } finally {
            this.sharedProcessBA.messagesDuringPaused.clear();
        }
    }

    public String getClassNameWithoutPackage() {
        String str = this.className;
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public static void runAsync(final BA ba, final Object obj, String str, final Object[] objArr, final Callable<Object[]> callable) {
        final String lowerCase = str.toLowerCase(cul);
        submitRunnable(new Runnable() { // from class: anywheresoftware.b4a.BA.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Object[] objArr2 = (Object[]) callable.call();
                    Object objectOrNull = obj;
                    if (objectOrNull instanceof ObjectWrapper) {
                        objectOrNull = ((ObjectWrapper) objectOrNull).getObjectOrNull();
                    }
                    ba.raiseEventFromDifferentThread(objectOrNull, null, 0, lowerCase, false, objArr2);
                } catch (Exception e) {
                    e.printStackTrace();
                    ba.setLastException(e);
                    Object objectOrNull2 = obj;
                    if (objectOrNull2 instanceof ObjectWrapper) {
                        objectOrNull2 = ((ObjectWrapper) objectOrNull2).getObjectOrNull();
                    }
                    ba.raiseEventFromDifferentThread(objectOrNull2, null, 0, lowerCase, false, objArr);
                }
            }
        }, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void markTaskAsFinish(Object obj, int i) {
        if (threadPool == null) {
            return;
        }
        threadPool.markTaskAsFinished(obj, i);
    }

    public static Future<?> submitRunnable(Runnable runnable, Object obj, int i) {
        if (threadPool == null) {
            synchronized (BA.class) {
                if (threadPool == null) {
                    threadPool = new B4AThreadPool();
                }
            }
        }
        if (obj instanceof ObjectWrapper) {
            obj = ((ObjectWrapper) obj).getObject();
        }
        threadPool.submit(runnable, obj, i);
        return null;
    }

    public static boolean isTaskRunning(Object obj, int i) {
        if (threadPool == null) {
            return false;
        }
        return threadPool.isRunning(obj, i);
    }

    public void loadHtSubs(Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getName().startsWith("_")) {
                this.htSubs.put(method.getName().substring(1).toLowerCase(cul), method);
            }
        }
    }

    public boolean isActivityPaused() {
        BA ba = this.processBA;
        if (ba != null) {
            return ba.isActivityPaused();
        }
        return this.sharedProcessBA.isActivityPaused;
    }

    public static boolean isAnyActivityVisible() {
        try {
            if (packageName == null) {
                return false;
            }
            return ((Boolean) Class.forName(String.valueOf(packageName) + ".main").getMethod("isAnyActivityVisible", null).invoke(null, null)).booleanValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void startActivityForResult(IOnActivityResult iOnActivityResult, Intent intent) {
        BA ba = this.processBA;
        if (ba != null) {
            ba.startActivityForResult(iOnActivityResult, intent);
            return;
        }
        if (this.sharedProcessBA.activityBA == null) {
            return;
        }
        BA ba2 = this.sharedProcessBA.activityBA.get();
        if (ba2 == null) {
            return;
        }
        if (this.sharedProcessBA.onActivityResultMap == null) {
            this.sharedProcessBA.onActivityResultMap = new HashMap<>();
        }
        this.sharedProcessBA.onActivityResultMap.put(Integer.valueOf(this.sharedProcessBA.onActivityResultCode), new WeakReference<>(iOnActivityResult));
        try {
            Activity activity = ba2.activity;
            SharedProcessBA sharedProcessBA = this.sharedProcessBA;
            int i = sharedProcessBA.onActivityResultCode;
            sharedProcessBA.onActivityResultCode = i + 1;
            activity.startActivityForResult(intent, i);
        } catch (ActivityNotFoundException unused) {
            this.sharedProcessBA.onActivityResultMap.remove(Integer.valueOf(this.sharedProcessBA.onActivityResultCode - 1));
            iOnActivityResult.ResultArrived(0, null);
        }
    }

    public void onActivityResult(int i, final int i2, final Intent intent) {
        if (this.sharedProcessBA.onActivityResultMap != null) {
            WeakReference<IOnActivityResult> weakReference = this.sharedProcessBA.onActivityResultMap.get(Integer.valueOf(i));
            if (weakReference == null) {
                Log("onActivityResult: wi is null");
                return;
            }
            this.sharedProcessBA.onActivityResultMap.remove(Integer.valueOf(i));
            final IOnActivityResult iOnActivityResult = weakReference.get();
            if (iOnActivityResult == null) {
                Log("onActivityResult: IOnActivityResult was released");
            } else {
                addMessageToPausedMessageQueue("OnActivityResult", new Runnable() { // from class: anywheresoftware.b4a.BA.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iOnActivityResult.ResultArrived(i2, intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public static void Log(String str) {
        if (str == null) {
            str = "null";
        }
        Log.i("B4A", str);
        if (str.length() > 4000) {
            LogInfo("Message longer than Log limit (4000). Message was truncated.");
        }
        IBridgeLog iBridgeLog = bridgeLog;
        if (iBridgeLog != null) {
            iBridgeLog.offer(str);
        }
    }

    public static void addLogPrefix(String str, String str2) {
        String str3 = "~" + str + ":";
        if (str2 == null) {
            str2 = "(null string)";
        }
        if (str2.length() < 3900) {
            StringBuilder sb = new StringBuilder();
            String[] strArrSplit = str2.split("\\n");
            for (String str4 : strArrSplit) {
                if (str4.length() > 0) {
                    sb.append(str3);
                    sb.append(str4);
                }
                sb.append(Common.CRLF);
            }
            str2 = sb.toString();
        }
        Log(str2);
    }

    public static void LogError(String str) {
        addLogPrefix("e", str);
    }

    public static void LogInfo(String str) {
        addLogPrefix("i", str);
    }

    public static boolean parseBoolean(String str) {
        if (str.equals("true")) {
            return true;
        }
        if (str.equals("false")) {
            return false;
        }
        throw new RuntimeException("Cannot parse: " + str + " as boolean");
    }

    public static char CharFromString(String str) {
        if (str == null || str.length() == 0) {
            return (char) 0;
        }
        return str.charAt(0);
    }

    public Object getSender() {
        return senderHolder.get();
    }

    public Exception getLastException() {
        BA ba = this.processBA;
        if (ba != null) {
            return ba.getLastException();
        }
        return this.sharedProcessBA.lastException;
    }

    public void setLastException(Exception exc) {
        while (exc != null && exc.getCause() != null && (exc instanceof Exception)) {
            exc = (Exception) exc.getCause();
        }
        this.sharedProcessBA.lastException = exc;
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> cls, String str) {
        return (T) Enum.valueOf(cls, str);
    }

    public static String NumberToString(double d) {
        String string = Double.toString(d);
        return (string.length() > 2 && string.charAt(string.length() - 2) == '.' && string.charAt(string.length() + (-1)) == '0') ? string.substring(0, string.length() - 2) : string;
    }

    public static String NumberToString(float f) {
        return NumberToString(f);
    }

    public static String NumberToString(int i) {
        return String.valueOf(i);
    }

    public static String NumberToString(long j) {
        return String.valueOf(j);
    }

    public static String NumberToString(Number number) {
        return String.valueOf(number);
    }

    public static double ObjectToNumber(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return Double.parseDouble(String.valueOf(obj));
    }

    public static long ObjectToLongNumber(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        return Long.parseLong(String.valueOf(obj));
    }

    public static boolean ObjectToBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        return parseBoolean(String.valueOf(obj));
    }

    public static char ObjectToChar(Object obj) {
        if (obj instanceof Character) {
            return ((Character) obj).charValue();
        }
        return CharFromString(obj.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0075 A[Catch: Exception -> 0x009e, PHI: r8
      0x0075: PHI (r8v1 java.lang.String) = (r8v0 java.lang.String), (r8v3 java.lang.String) binds: [B:23:0x0061, B:28:0x0072] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x009e, blocks: (B:3:0x0002, B:15:0x002c, B:17:0x0042, B:19:0x0049, B:20:0x0051, B:22:0x005b, B:24:0x0063, B:27:0x006a, B:30:0x0075, B:32:0x0091, B:33:0x0096, B:6:0x000f, B:11:0x0023, B:14:0x002a), top: B:41:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String TypeToString(java.lang.Object r10, boolean r11) {
        /*
            java.lang.String r0 = "_"
            int r1 = anywheresoftware.b4a.BA.checkStackTraceEvery50     // Catch: java.lang.Exception -> L9e
            r2 = 1
            int r1 = r1 + r2
            anywheresoftware.b4a.BA.checkStackTraceEvery50 = r1     // Catch: java.lang.Exception -> L9e
            int r3 = r1 % 50
            r4 = 0
            if (r3 == 0) goto Lf
            if (r1 >= 0) goto L2c
        Lf:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: java.lang.Exception -> L9e
            java.lang.StackTraceElement[] r1 = r1.getStackTrace()     // Catch: java.lang.Exception -> L9e
            int r1 = r1.length     // Catch: java.lang.Exception -> L9e
            int r3 = anywheresoftware.b4a.BA.checkStackTraceEvery50     // Catch: java.lang.Exception -> L9e
            if (r3 >= 0) goto L1f
            r3 = 20
            goto L21
        L1f:
            r3 = 150(0x96, float:2.1E-43)
        L21:
            if (r1 < r3) goto L2a
            r11 = -100
            anywheresoftware.b4a.BA.checkStackTraceEvery50 = r11     // Catch: java.lang.Exception -> L9e
            java.lang.String r10 = ""
            return r10
        L2a:
            anywheresoftware.b4a.BA.checkStackTraceEvery50 = r4     // Catch: java.lang.Exception -> L9e
        L2c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L9e
            r1.<init>()     // Catch: java.lang.Exception -> L9e
            java.lang.String r3 = "["
            r1.append(r3)     // Catch: java.lang.Exception -> L9e
            java.lang.Class r3 = r10.getClass()     // Catch: java.lang.Exception -> L9e
            java.lang.reflect.Field[] r3 = r3.getDeclaredFields()     // Catch: java.lang.Exception -> L9e
            int r5 = r3.length     // Catch: java.lang.Exception -> L9e
            r6 = 0
        L40:
            if (r4 < r5) goto L5b
            int r11 = r1.length()     // Catch: java.lang.Exception -> L9e
            r0 = 2
            if (r11 < r0) goto L51
            int r11 = r1.length()     // Catch: java.lang.Exception -> L9e
            int r11 = r11 - r0
            r1.setLength(r11)     // Catch: java.lang.Exception -> L9e
        L51:
            java.lang.String r11 = "]"
            r1.append(r11)     // Catch: java.lang.Exception -> L9e
            java.lang.String r10 = r1.toString()     // Catch: java.lang.Exception -> L9e
            return r10
        L5b:
            r7 = r3[r4]     // Catch: java.lang.Exception -> L9e
            java.lang.String r8 = r7.getName()     // Catch: java.lang.Exception -> L9e
            if (r11 == 0) goto L75
            boolean r9 = r8.startsWith(r0)     // Catch: java.lang.Exception -> L9e
            if (r9 != 0) goto L6a
            goto L9b
        L6a:
            java.lang.String r8 = r8.substring(r2)     // Catch: java.lang.Exception -> L9e
            boolean r9 = r8.startsWith(r0)     // Catch: java.lang.Exception -> L9e
            if (r9 == 0) goto L75
            goto L9b
        L75:
            r7.setAccessible(r2)     // Catch: java.lang.Exception -> L9e
            r1.append(r8)     // Catch: java.lang.Exception -> L9e
            java.lang.String r8 = "="
            r1.append(r8)     // Catch: java.lang.Exception -> L9e
            java.lang.Object r7 = r7.get(r10)     // Catch: java.lang.Exception -> L9e
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch: java.lang.Exception -> L9e
            r1.append(r7)     // Catch: java.lang.Exception -> L9e
            int r6 = r6 + 1
            int r7 = r6 % 3
            if (r7 != 0) goto L96
            java.lang.String r7 = "\n"
            r1.append(r7)     // Catch: java.lang.Exception -> L9e
        L96:
            java.lang.String r7 = ", "
            r1.append(r7)     // Catch: java.lang.Exception -> L9e
        L9b:
            int r4 = r4 + 1
            goto L40
        L9e:
            if (r10 == 0) goto Lbe
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.Class r0 = r10.getClass()
            r11.append(r0)
            java.lang.String r0 = ": "
            r11.append(r0)
            int r10 = java.lang.System.identityHashCode(r10)
            r11.append(r10)
            java.lang.String r10 = r11.toString()
            return r10
        Lbe:
            java.lang.String r10 = "N/A"
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: anywheresoftware.b4a.BA.TypeToString(java.lang.Object, boolean):java.lang.String");
    }

    public static <T> T gm(Map map, Object obj, T t) {
        T t2 = (T) map.get(obj);
        return t2 == null ? t : t2;
    }

    public static String returnString(String str) {
        return str == null ? "" : str;
    }

    public static String ObjectToString(Object obj) {
        return String.valueOf(obj);
    }

    public static CharSequence ObjectToCharSequence(Object obj) {
        if (obj instanceof CharSequence) {
            return (CharSequence) obj;
        }
        return String.valueOf(obj);
    }

    public static int switchObjectToInt(Object obj, Object... objArr) {
        int i = 0;
        if (obj instanceof Number) {
            double dDoubleValue = ((Number) obj).doubleValue();
            while (i < objArr.length) {
                if (dDoubleValue == ((Number) objArr[i]).doubleValue()) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        while (i < objArr.length) {
            if (obj.equals(objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static boolean fastSubCompare(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str.length() != str2.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) & 223) != (str2.charAt(i) & 223)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isShellModeRuntimeCheck(BA ba) {
        BA ba2 = ba.processBA;
        if (ba2 != null) {
            return isShellModeRuntimeCheck(ba2);
        }
        return ba.getClass().getName().endsWith("ShellBA");
    }

    public static class B4AExceptionHandler implements Thread.UncaughtExceptionHandler {
        public final Thread.UncaughtExceptionHandler original = Thread.getDefaultUncaughtExceptionHandler();

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            BA.printException(th, true);
            if (BA.bridgeLog != null) {
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException unused) {
                }
            }
            this.original.uncaughtException(thread, th);
        }
    }

    public static abstract class WarningEngine {
        public static final int FULLSCREEN_MISMATCH = 1004;
        public static final int OBJECT_ALREADY_INITIALIZED = 1003;
        public static final int SAME_OBJECT_ADDED_TO_LIST = 1002;
        public static final int ZERO_SIZE_PANEL = 1001;

        public abstract void checkFullScreenInLayout(boolean z, boolean z2);

        protected abstract void warnImpl(int i);

        public static void warn(int i) {
            if (BA.warningEngine != null) {
                BA.warningEngine.warnImpl(i);
            }
        }
    }

    public static class WaitForEvent {
        public ResumableSub rs;
        public WeakReference<Object> senderFilter;

        public WaitForEvent(ResumableSub resumableSub, Object obj) {
            this.rs = resumableSub;
            if (obj == null) {
                this.senderFilter = null;
            } else {
                this.senderFilter = new WeakReference<>(obj);
            }
        }

        public boolean noFilter() {
            return this.senderFilter == null;
        }

        public boolean cleared() {
            WeakReference<Object> weakReference = this.senderFilter;
            return weakReference != null && weakReference.get() == null;
        }
    }
}
