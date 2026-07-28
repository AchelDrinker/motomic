package anywheresoftware.b4a.keywords;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.DialogResponse;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.keywords.constants.KeyCodes;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.B4AException;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.drawable.BitmapDrawable;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
@BA.Version(13.4f)
public class Common {
    public static final B4AApplication Application;
    public static final Bit Bit;
    public static final String CRLF = "\n";
    public static final Colors Colors;
    public static final DateTime DateTime;
    public static float Density = 0.0f;
    public static final DialogResponse DialogResponse;
    public static final boolean False = false;
    public static final File File;
    public static final Gravity Gravity;
    public static KeyCodes KeyCodes = null;
    private static int LogStub = 0;
    public static final Object Null;
    public static final String QUOTE = "\"";
    public static final Regex Regex;
    public static final String TAB = "\t";
    public static final boolean True = true;
    public static final TypefaceWrapper Typeface;
    public static final double cE = 2.718281828459045d;
    public static final double cPI = 3.141592653589793d;
    private static Random random;
    private static final Map<Class<?>, Integer> testedClassesForIsInitialized;

    public interface DesignerCustomView {
        void DesignerCreateView(PanelWrapper panelWrapper, LabelWrapper labelWrapper, anywheresoftware.b4a.objects.collections.Map map);

        void _initialize(BA ba, Object obj, String str);
    }

    public static void Array() {
    }

    public static int Asc(char c) {
        return c;
    }

    public static void Catch() {
    }

    public static char Chr(int i) {
        return (char) i;
    }

    public static RemoteViews ConfigureHomeWidget(String str, String str2, int i, String str3, boolean z) {
        return null;
    }

    public static void Continue() {
    }

    public static void CreateMap() {
    }

    public static void Dim() {
    }

    public static void Exit() {
    }

    public static void For() {
    }

    public static Object IIf(boolean z, Object obj, Object obj2) {
        return null;
    }

    public static void If() {
    }

    public static void Is() {
    }

    public static Object Me(BA ba) {
        return null;
    }

    public static boolean Not(boolean z) {
        return !z;
    }

    public static void Return() {
    }

    public static void Select() {
    }

    public static void Sleep(int i) {
    }

    public static void Sub() {
    }

    public static void Try() {
    }

    public static void Type() {
    }

    public static void Until() {
    }

    public static void While() {
    }

    static {
        System.out.println("common created.");
        Null = null;
        KeyCodes = null;
        Density = BA.density;
        Colors = null;
        Gravity = null;
        File = null;
        Application = null;
        Bit = null;
        Typeface = null;
        DateTime = null;
        DialogResponse = null;
        Regex = null;
        testedClassesForIsInitialized = new HashMap();
    }

    public static String NumberFormat(double d, int i, int i2) {
        if (BA.numberFormat == null) {
            BA.numberFormat = NumberFormat.getInstance(Locale.US);
        }
        BA.numberFormat.setMaximumFractionDigits(i2);
        BA.numberFormat.setMinimumIntegerDigits(i);
        return BA.numberFormat.format(d);
    }

    public static String NumberFormat2(double d, int i, int i2, int i3, boolean z) {
        if (BA.numberFormat2 == null) {
            BA.numberFormat2 = NumberFormat.getInstance(Locale.US);
        }
        BA.numberFormat2.setMaximumFractionDigits(i2);
        BA.numberFormat2.setMinimumIntegerDigits(i);
        BA.numberFormat2.setMinimumFractionDigits(i3);
        BA.numberFormat2.setGroupingUsed(z);
        return BA.numberFormat2.format(d);
    }

    public static void Log(String str) {
        BA.Log(str);
    }

    public static void LogImpl(String str, String str2, int i) {
        String str3;
        LogStub = (LogStub + 1) % 10;
        if (i == 0) {
            str3 = "l" + LogStub + str;
        } else {
            str3 = "L" + LogStub + str + "~" + i;
        }
        BA.addLogPrefix(str3, str2);
    }

    public static void LogColor(String str, int i) {
        BA.addLogPrefix("c" + i, str);
    }

    public static Object Sender(BA ba) {
        return ba.getSender();
    }

    public static void RndSeed(long j) {
        Random random2 = random;
        if (random2 == null) {
            random = new Random(j);
        } else {
            random2.setSeed(j);
        }
    }

    public static int Rnd(int i, int i2) {
        if (random == null) {
            random = new Random();
        }
        return i + random.nextInt(i2 - i);
    }

    public static double Abs(double d) {
        return Math.abs(d);
    }

    public static int Abs(int i) {
        return Math.abs(i);
    }

    public static double Max(double d, double d2) {
        return Math.max(d, d2);
    }

    public static double Max(int i, int i2) {
        return Math.max(i, i2);
    }

    public static double Min(double d, double d2) {
        return Math.min(d, d2);
    }

    public static double Min(int i, int i2) {
        return Math.min(i, i2);
    }

    public static double Sin(double d) {
        return Math.sin(d);
    }

    public static double SinD(double d) {
        return Math.sin((d / 180.0d) * 3.141592653589793d);
    }

    public static double Cos(double d) {
        return Math.cos(d);
    }

    public static double CosD(double d) {
        return Math.cos((d / 180.0d) * 3.141592653589793d);
    }

    public static double Tan(double d) {
        return Math.tan(d);
    }

    public static double TanD(double d) {
        return Math.tan((d / 180.0d) * 3.141592653589793d);
    }

    public static double Power(double d, double d2) {
        return Math.pow(d, d2);
    }

    public static double Sqrt(double d) {
        return Math.sqrt(d);
    }

    public static double ASin(double d) {
        return Math.asin(d);
    }

    public static double ASinD(double d) {
        return (Math.asin(d) / 3.141592653589793d) * 180.0d;
    }

    public static double ACos(double d) {
        return Math.acos(d);
    }

    public static double ACosD(double d) {
        return (Math.acos(d) / 3.141592653589793d) * 180.0d;
    }

    public static double ATan(double d) {
        return Math.atan(d);
    }

    public static double ATanD(double d) {
        return (Math.atan(d) / 3.141592653589793d) * 180.0d;
    }

    public static double ATan2(double d, double d2) {
        return Math.atan2(d, d2);
    }

    public static double ATan2D(double d, double d2) {
        return (Math.atan2(d, d2) / 3.141592653589793d) * 180.0d;
    }

    public static double Logarithm(double d, double d2) {
        return Math.log(d) / Math.log(d2);
    }

    public static long Round(double d) {
        return Math.round(d);
    }

    public static double Round2(double d, int i) {
        double dPow = Math.pow(10.0d, i);
        double dRound = Math.round(d * dPow);
        Double.isNaN(dRound);
        return dRound / dPow;
    }

    public static double Floor(double d) {
        return Math.floor(d);
    }

    public static double Ceil(double d) {
        return Math.ceil(d);
    }

    public static void DoEvents() {
        Msgbox.sendCloseMyLoopMessage();
        Msgbox.waitForMessage(false, true);
    }

    public static void ToastMessageShow(CharSequence charSequence, boolean z) {
        Toast.makeText(BA.applicationContext, charSequence, z ? 1 : 0).show();
    }

    public static void Msgbox(CharSequence charSequence, CharSequence charSequence2, BA ba) {
        Msgbox2(charSequence, charSequence2, "OK", "", "", null, ba);
    }

    public static int Msgbox2(CharSequence charSequence, CharSequence charSequence2, String str, String str2, String str3, Bitmap bitmap, BA ba) {
        Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
        Msgbox.msgbox(createMsgboxAlertDialog(charSequence, charSequence2, str, str2, str3, bitmap, ba, dialogResponse), false);
        return dialogResponse.res;
    }

    public static void MsgboxAsync(CharSequence charSequence, CharSequence charSequence2, BA ba) {
        Msgbox2Async(charSequence, charSequence2, "OK", "", "", null, ba, true);
    }

    public static Object Msgbox2Async(CharSequence charSequence, CharSequence charSequence2, String str, String str2, String str3, CanvasWrapper.BitmapWrapper bitmapWrapper, final BA ba, boolean z) {
        AlertDialog alertDialogCreateMsgboxAlertDialog = createMsgboxAlertDialog(charSequence, charSequence2, str, str2, str3, bitmapWrapper == null ? null : bitmapWrapper.getObjectOrNull(), ba.sharedProcessBA.activityBA.get(), new DialogInterface.OnClickListener() { // from class: anywheresoftware.b4a.keywords.Common.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                ba.raiseEvent(dialogInterface, "msgbox_result", Integer.valueOf(i));
            }
        });
        alertDialogCreateMsgboxAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: anywheresoftware.b4a.keywords.Common.2
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                ba.raiseEvent(dialogInterface, "msgbox_result", -3);
            }
        });
        return showAndTrackDialog(alertDialogCreateMsgboxAlertDialog, z);
    }

    private static AlertDialog createMsgboxAlertDialog(CharSequence charSequence, CharSequence charSequence2, String str, String str2, String str3, Bitmap bitmap, BA ba, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ba.context);
        builder.setTitle(charSequence2).setMessage(charSequence);
        if (str.length() > 0) {
            builder.setPositiveButton(str, onClickListener);
        }
        if (str3.length() > 0) {
            builder.setNegativeButton(str3, onClickListener);
        }
        if (str2.length() > 0) {
            builder.setNeutralButton(str2, onClickListener);
        }
        if (bitmap != null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable();
            bitmapDrawable.Initialize(bitmap);
            builder.setIcon(bitmapDrawable.getObject());
        }
        return builder.create();
    }

    public static int InputList(List list, CharSequence charSequence, int i, BA ba) {
        Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(true);
        Msgbox.msgbox(createInputList(list, charSequence, i, ba, dialogResponse), false);
        return dialogResponse.res;
    }

    public static Object InputListAsync(List list, CharSequence charSequence, int i, final BA ba, boolean z) {
        AlertDialog alertDialogCreateInputList = createInputList(list, charSequence, i, ba.sharedProcessBA.activityBA.get(), new DialogInterface.OnClickListener() { // from class: anywheresoftware.b4a.keywords.Common.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
                ba.raiseEvent(dialogInterface, "inputlist_result", Integer.valueOf(i2));
            }
        });
        alertDialogCreateInputList.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: anywheresoftware.b4a.keywords.Common.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                ba.raiseEvent(dialogInterface, "inputlist_result", -3);
            }
        });
        return showAndTrackDialog(alertDialogCreateInputList, z);
    }

    private static AlertDialog createInputList(List list, CharSequence charSequence, int i, BA ba, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ba.context);
        CharSequence[] charSequenceArr = new CharSequence[list.getSize()];
        for (int i2 = 0; i2 < list.getSize(); i2++) {
            Object objGet = list.Get(i2);
            if (objGet instanceof CharSequence) {
                charSequenceArr[i2] = (CharSequence) objGet;
            } else {
                charSequenceArr[i2] = String.valueOf(objGet);
            }
        }
        builder.setSingleChoiceItems(charSequenceArr, i, onClickListener);
        builder.setTitle(charSequence);
        return builder.create();
    }

    public static Dialog showAndTrackDialog(Dialog dialog, boolean z) {
        dialog.setCancelable(z);
        dialog.setCanceledOnTouchOutside(z);
        dialog.show();
        Msgbox.trackAsyncDialog(dialog);
        return dialog;
    }

    public static void InputMap(anywheresoftware.b4a.objects.collections.Map map, CharSequence charSequence, BA ba) {
        Msgbox.msgbox(createInputMap(map, charSequence, ba, new Msgbox.DialogResponse(false)), false);
    }

    public static Object InputMapAsync(anywheresoftware.b4a.objects.collections.Map map, CharSequence charSequence, final BA ba, boolean z) {
        AlertDialog alertDialogCreateInputMap = createInputMap(map, charSequence, ba.sharedProcessBA.activityBA.get(), new DialogInterface.OnClickListener() { // from class: anywheresoftware.b4a.keywords.Common.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                ba.raiseEvent(dialogInterface, "inputmap_result", new Object[0]);
            }
        });
        alertDialogCreateInputMap.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: anywheresoftware.b4a.keywords.Common.6
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                ba.raiseEvent(dialogInterface, "inputmap_result", new Object[0]);
            }
        });
        return showAndTrackDialog(alertDialogCreateInputMap, z);
    }

    private static AlertDialog createInputMap(final anywheresoftware.b4a.objects.collections.Map map, CharSequence charSequence, BA ba, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ba.context);
        final CharSequence[] charSequenceArr = new CharSequence[map.getSize()];
        boolean[] zArr = new boolean[map.getSize()];
        int i = 0;
        for (Map.Entry<Object, Object> entry : ((Map.MyMap) map.getObject()).entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw new RuntimeException("Keys must be strings.");
            }
            charSequenceArr[i] = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                zArr[i] = ((Boolean) value).booleanValue();
            } else {
                zArr[i] = Boolean.parseBoolean(String.valueOf(value));
            }
            i++;
        }
        builder.setMultiChoiceItems(charSequenceArr, zArr, new DialogInterface.OnMultiChoiceClickListener() { // from class: anywheresoftware.b4a.keywords.Common.7
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                if (z) {
                    map.Put(charSequenceArr[i2], true);
                } else {
                    map.Put(charSequenceArr[i2], false);
                }
            }
        });
        builder.setTitle(charSequence);
        builder.setPositiveButton("Ok", onClickListener);
        return builder.create();
    }

    public static List InputMultiList(List list, CharSequence charSequence, BA ba) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ba.context);
        CharSequence[] charSequenceArr = new CharSequence[list.getSize()];
        for (int i = 0; i < list.getSize(); i++) {
            Object objGet = list.Get(i);
            if (objGet instanceof CharSequence) {
                charSequenceArr[i] = (CharSequence) objGet;
            } else {
                charSequenceArr[i] = String.valueOf(objGet);
            }
        }
        Msgbox.DialogResponse dialogResponse = new Msgbox.DialogResponse(false);
        final List list2 = new List();
        list2.Initialize();
        builder.setMultiChoiceItems(charSequenceArr, (boolean[]) null, new DialogInterface.OnMultiChoiceClickListener() { // from class: anywheresoftware.b4a.keywords.Common.8
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(DialogInterface dialogInterface, int i2, boolean z) {
                if (z) {
                    list2.Add(Integer.valueOf(i2));
                } else {
                    list2.RemoveAt(list2.IndexOf(Integer.valueOf(i2)));
                }
            }
        });
        builder.setTitle(charSequence);
        builder.setPositiveButton("Ok", dialogResponse);
        Msgbox.msgbox(builder.create(), false);
        if (dialogResponse.res != -1) {
            list2.Clear();
            return list2;
        }
        list2.Sort(true);
        return list2;
    }

    public static void ProgressDialogShow(BA ba, CharSequence charSequence) {
        ProgressDialogShow2(ba, charSequence, true);
    }

    public static void ProgressDialogShow2(BA ba, CharSequence charSequence, boolean z) {
        ProgressDialogHide();
        Msgbox.pd = new WeakReference<>(ProgressDialog.show(ba.context, "", charSequence, true, z));
    }

    public static void ProgressDialogHide() {
        Msgbox.dismissProgressDialog();
    }

    public static boolean Initialized(Object obj) {
        return !NotInitialized(obj);
    }

    public static boolean NotInitialized(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof ObjectWrapper) {
            return ((ObjectWrapper) obj).getObjectOrNull() == null;
        }
        if (obj instanceof B4AClass) {
            return !((B4AClass) obj).IsInitialized();
        }
        Class<?> cls = obj.getClass();
        java.util.Map<Class<?>, Integer> map = testedClassesForIsInitialized;
        Integer num = map.get(cls);
        if (num == null || num.intValue() > 0) {
            Object objInvoke = null;
            if (num == null || num.intValue() == 1) {
                try {
                    objInvoke = cls.getMethod("IsInitialized", null).invoke(obj, null);
                    if (num == null && (objInvoke instanceof Boolean)) {
                        map.put(cls, 1);
                    }
                } catch (Exception unused) {
                }
            }
            if (objInvoke == null && (num == null || num.intValue() == 2)) {
                try {
                    objInvoke = cls.getField("IsInitialized").get(obj);
                    if (num == null && (objInvoke instanceof Boolean)) {
                        testedClassesForIsInitialized.put(cls, 2);
                    }
                } catch (Exception unused2) {
                }
            }
            if (num == null && (objInvoke == null || !(objInvoke instanceof Boolean))) {
                testedClassesForIsInitialized.put(cls, 0);
            } else {
                return !((Boolean) objInvoke).booleanValue();
            }
        }
        return false;
    }

    public static String GetType(Object obj) {
        return obj.getClass().getName();
    }

    public static boolean IsDevTool(String str) {
        return str.toLowerCase(BA.cul).equals("b4a");
    }

    public static int DipToCurrent(int i) {
        return (int) (Density * i);
    }

    public static int PerXToCurrent(float f, BA ba) {
        return (int) ((f / 100.0f) * ba.vg.getWidth());
    }

    public static int PerYToCurrent(float f, BA ba) {
        return (int) ((f / 100.0f) * ba.vg.getHeight());
    }

    public static boolean IsNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static B4AException LastException(BA ba) {
        B4AException b4AException = new B4AException();
        b4AException.setObject(ba.getLastException());
        return b4AException;
    }

    public static LayoutValues GetDeviceLayoutValues(BA ba) {
        DisplayMetrics displayMetrics = BA.applicationContext.getResources().getDisplayMetrics();
        LayoutValues layoutValues = new LayoutValues();
        layoutValues.Scale = displayMetrics.density;
        layoutValues.Width = displayMetrics.widthPixels;
        layoutValues.Height = displayMetrics.heightPixels;
        return layoutValues;
    }

    public static void StartActivity(BA ba, Object obj) throws ClassNotFoundException {
        Intent componentIntent = getComponentIntent(ba, obj);
        BA ba2 = ba.sharedProcessBA.activityBA != null ? ba.sharedProcessBA.activityBA.get() : null;
        if (ba2 != null) {
            componentIntent.addFlags(131072);
            ba2.context.startActivity(componentIntent);
        } else {
            componentIntent.addFlags(268435456);
            ba.context.startActivity(componentIntent);
        }
    }

    public static void StartReceiver(final BA ba, final Object obj) {
        BA.handler.post(new Runnable() { // from class: anywheresoftware.b4a.keywords.Common.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    BA.applicationContext.sendBroadcast(Common.getComponentIntent(ba, obj));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void StartService(final BA ba, final Object obj) throws ClassNotFoundException {
        if (BA.shellMode) {
            BA.handler.post(new BA.B4ARunnable() { // from class: anywheresoftware.b4a.keywords.Common.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Common.StartServiceImpl(ba, obj);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return;
        }
        BA.handler.post(new Runnable() { // from class: anywheresoftware.b4a.keywords.Common.11
            @Override // java.lang.Runnable
            public void run() {
                Msgbox.isDismissing = false;
            }
        });
        StartServiceImpl(ba, obj);
        Msgbox.isDismissing = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void StartServiceImpl(BA ba, Object obj) throws ClassNotFoundException {
        Intent componentIntent = getComponentIntent(ba, obj);
        try {
            ba.context.startService(componentIntent);
        } catch (IllegalStateException e) {
            if (Build.VERSION.SDK_INT >= 26) {
                BA.LogInfo("Service started in the background. Trying to start again in foreground mode.");
                componentIntent.putExtra(ServiceHelper.FOREGROUND_KEY, true);
                ba.context.startForegroundService(componentIntent);
                return;
            }
            throw new RuntimeException(e);
        }
    }

    public static void StartServiceAt(BA ba, Object obj, long j, boolean z) throws ClassNotFoundException {
        StartReceiverAt(ba, obj, j, z);
    }

    public static void StartReceiverAt(BA ba, Object obj, long j, boolean z) throws ClassNotFoundException {
        AlarmManager alarmManager = (AlarmManager) BA.applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        PendingIntent pendingIntentCreatePendingIntentForAlarmManager = createPendingIntentForAlarmManager(ba, obj);
        if (Build.VERSION.SDK_INT >= 23 && z) {
            alarmManager.setAndAllowWhileIdle(0, j, pendingIntentCreatePendingIntentForAlarmManager);
        } else {
            alarmManager.set(!z ? 1 : 0, j, pendingIntentCreatePendingIntentForAlarmManager);
        }
    }

    public static void StartServiceAtExact(BA ba, Object obj, long j, boolean z) throws Exception {
        AlarmManager alarmManager = (AlarmManager) BA.applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM);
        PendingIntent pendingIntentCreatePendingIntentForAlarmManager = createPendingIntentForAlarmManager(ba, obj);
        if (Build.VERSION.SDK_INT >= 23 && z) {
            alarmManager.setExactAndAllowWhileIdle(0, j, pendingIntentCreatePendingIntentForAlarmManager);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(!z ? 1 : 0, j, pendingIntentCreatePendingIntentForAlarmManager);
        } else {
            alarmManager.set(!z ? 1 : 0, j, pendingIntentCreatePendingIntentForAlarmManager);
        }
    }

    private static PendingIntent createPendingIntentForAlarmManager(BA ba, Object obj) throws ClassNotFoundException {
        return PendingIntent.getBroadcast(ba.context, 1, new Intent(BA.applicationContext, getComponentClass(ba, obj, true)), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728);
    }

    public static void CancelScheduledService(BA ba, Object obj) throws ClassNotFoundException {
        ((AlarmManager) BA.applicationContext.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(createPendingIntentForAlarmManager(ba, obj));
    }

    public static Class<?> getComponentClass(BA ba, Object obj, boolean z) throws ClassNotFoundException {
        Class<?> cls;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else if (obj == null || obj.toString().length() == 0) {
            cls = Class.forName(ba.className);
        } else if (obj instanceof String) {
            cls = Class.forName(String.valueOf(BA.packageName) + "." + ((String) obj).toLowerCase(BA.cul));
        } else {
            cls = null;
        }
        if (cls == null) {
            return null;
        }
        if (!z || BroadcastReceiver.class.isAssignableFrom(cls)) {
            return cls;
        }
        return Class.forName(String.valueOf(cls.getName()) + "$" + cls.getName().substring(cls.getName().lastIndexOf(".") + 1) + "_BR");
    }

    public static Intent getComponentIntent(BA ba, Object obj) throws ClassNotFoundException {
        Class<?> componentClass = getComponentClass(ba, obj, false);
        if (componentClass != null) {
            return new Intent(ba.context, componentClass);
        }
        return (Intent) obj;
    }

    public static void StopService(BA ba, Object obj) throws ClassNotFoundException {
        ba.context.stopService(getComponentIntent(ba, obj));
    }

    public static boolean SubExists(BA ba, Object obj, String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException {
        BA componentBA;
        if (obj == null || (componentBA = getComponentBA(ba, obj)) == null) {
            return false;
        }
        return componentBA.subExists(str.toLowerCase(BA.cul));
    }

    public static Object CallSubNew(BA ba, Object obj, String str) throws Exception {
        return CallSub4(false, ba, obj, str, null);
    }

    public static Object CallSubNew2(BA ba, Object obj, String str, Object obj2) throws Exception {
        return CallSub4(false, ba, obj, str, new Object[]{obj2});
    }

    public static Object CallSubNew3(BA ba, Object obj, String str, Object obj2, Object obj3) throws Exception {
        return CallSub4(false, ba, obj, str, new Object[]{obj2, obj3});
    }

    public static Object CallSubDebug(BA ba, Object obj, String str) throws Exception {
        return Class.forName("anywheresoftware.b4a.debug.Debug").getDeclaredMethod("CallSubNew", BA.class, Object.class, String.class).invoke(null, ba, obj, str);
    }

    public static Object CallSubDebug2(BA ba, Object obj, String str, Object obj2) throws Exception {
        return Class.forName("anywheresoftware.b4a.debug.Debug").getDeclaredMethod("CallSubNew2", BA.class, Object.class, String.class, Object.class).invoke(null, ba, obj, str, obj2);
    }

    public static Object CallSubDebug3(BA ba, Object obj, String str, Object obj2, Object obj3) throws Exception {
        return Class.forName("anywheresoftware.b4a.debug.Debug").getDeclaredMethod("CallSubNew3", BA.class, Object.class, String.class, Object.class, Object.class).invoke(null, ba, obj, str, obj2, obj3);
    }

    private static Object CallSub4(boolean z, BA ba, Object obj, String str, Object[] objArr) throws Exception {
        Object objRaiseEvent2;
        Object objCallSub;
        if ((obj instanceof BA.SubDelegator) && (objCallSub = ((BA.SubDelegator) obj).callSub(str, ba.eventsTarget, objArr)) != BA.SubDelegator.SubNotFound) {
            return (objCallSub == null || !(objCallSub instanceof ObjectWrapper)) ? objCallSub : ((ObjectWrapper) objCallSub).getObject();
        }
        BA componentBA = getComponentBA(ba, obj);
        if (componentBA != null) {
            boolean z2 = obj instanceof B4AClass;
            objRaiseEvent2 = componentBA.raiseEvent2(ba.eventsTarget, z2, str.toLowerCase(BA.cul), z2, objArr);
        } else {
            objRaiseEvent2 = null;
        }
        if (!z) {
            return (objRaiseEvent2 == null || !(objRaiseEvent2 instanceof ObjectWrapper)) ? objRaiseEvent2 : ((ObjectWrapper) objRaiseEvent2).getObject();
        }
        if (objRaiseEvent2 == null) {
            objRaiseEvent2 = "";
        }
        return String.valueOf(objRaiseEvent2);
    }

    public static void CallSubDelayed(BA ba, Object obj, String str) {
        CallSubDelayed4(ba, obj, str, null);
    }

    public static void CallSubDelayed2(BA ba, Object obj, String str, Object obj2) {
        CallSubDelayed4(ba, obj, str, new Object[]{obj2});
    }

    public static void CallSubDelayed3(BA ba, Object obj, String str, Object obj2, Object obj3) {
        CallSubDelayed4(ba, obj, str, new Object[]{obj2, obj3});
    }

    private static void CallSubDelayed4(final BA ba, final Object obj, final String str, final Object[] objArr) {
        final Runnable runnable = new Runnable() { // from class: anywheresoftware.b4a.keywords.Common.12
            int retries = 5;

            @Override // java.lang.Runnable
            public void run() {
                try {
                    final BA componentBA = Common.getComponentBA(ba, obj);
                    final Object obj2 = ba.eventsTarget;
                    if (componentBA != null && !componentBA.isActivityPaused()) {
                        if (BA.shellMode) {
                            componentBA.raiseEventFromDifferentThread(obj2, null, 0, str.toLowerCase(BA.cul), false, objArr);
                            return;
                        } else {
                            componentBA.raiseEvent2(obj2, true, str.toLowerCase(BA.cul), false, objArr);
                            return;
                        }
                    }
                    Object obj3 = obj;
                    if (obj3 instanceof B4AClass) {
                        Common.Log("Object context is paused. Ignoring CallSubDelayed: " + str);
                        return;
                    }
                    ComponentName component = Common.getComponentIntent(ba, obj3).getComponent();
                    if (component == null) {
                        Common.Log("ComponentName = null");
                        return;
                    }
                    Class<?> cls = Class.forName(component.getClassName());
                    Field declaredField = cls.getDeclaredField("mostCurrent");
                    declaredField.setAccessible(true);
                    if (declaredField.get(null) == null && this.retries == 5) {
                        if (Activity.class.isAssignableFrom(cls)) {
                            if (BA.isAnyActivityVisible()) {
                                Common.StartActivity(ba, obj);
                            } else {
                                this.retries = 0;
                            }
                        } else if (Service.class.isAssignableFrom(cls)) {
                            Common.StartService(ba, obj);
                        } else if (BroadcastReceiver.class.isAssignableFrom(cls)) {
                            BA.applicationContext.sendBroadcast(Common.getComponentIntent(ba, obj));
                        }
                    }
                    int i = this.retries - 1;
                    this.retries = i;
                    if (i > 0) {
                        BA.handler.postDelayed(this, 100L);
                        return;
                    }
                    try {
                        if (componentBA != null) {
                            final String str2 = str;
                            final Object[] objArr2 = objArr;
                            componentBA.addMessageToPausedMessageQueue("CallSubDelayed - " + str, new Runnable() { // from class: anywheresoftware.b4a.keywords.Common.12.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    componentBA.raiseEvent2(obj2, true, str2.toLowerCase(BA.cul), true, objArr2);
                                }
                            });
                            return;
                        }
                        BA.addMessageToUninitializeActivity(component.getClassName(), str.toLowerCase(BA.cul), obj2, objArr);
                    } catch (Exception e) {
                        e = e;
                        throw new RuntimeException(e);
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
        };
        if (!BA.shellMode) {
            BA.handler.post(runnable);
        } else {
            BA.handler.post(new BA.B4ARunnable() { // from class: anywheresoftware.b4a.keywords.Common.13
                @Override // java.lang.Runnable
                public void run() {
                    runnable.run();
                }
            });
        }
    }

    public static boolean IsPaused(BA ba, Object obj) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException {
        BA componentBA = getComponentBA(ba, obj);
        return componentBA == null || componentBA.isActivityPaused();
    }

    public static BA getComponentBA(BA ba, Object obj) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException {
        Class<?> cls;
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            if (obj instanceof B4AClass) {
                return ((B4AClass) obj).getBA();
            }
            if (obj == null || obj.toString().length() == 0) {
                return ba;
            }
            cls = Class.forName(String.valueOf(BA.packageName) + "." + ((String) obj).toLowerCase(BA.cul));
        }
        return (BA) cls.getField("processBA").get(null);
    }

    public static String CharsToString(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    public static String BytesToString(byte[] bArr, int i, int i2, String str) throws UnsupportedEncodingException {
        return new String(bArr, i, i2, str);
    }

    public static anywheresoftware.b4a.objects.collections.Map createMap(Object[] objArr) {
        anywheresoftware.b4a.objects.collections.Map map = new anywheresoftware.b4a.objects.collections.Map();
        map.Initialize();
        for (int i = 0; i < objArr.length; i += 2) {
            map.Put(objArr[i], objArr[i + 1]);
        }
        return map;
    }

    public static List ArrayToList(Object[] objArr) {
        List list = new List();
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(int[] iArr) {
        List list = new List();
        Object[] objArr = new Object[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            objArr[i] = Integer.valueOf(iArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(long[] jArr) {
        List list = new List();
        Object[] objArr = new Object[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            objArr[i] = Long.valueOf(jArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(float[] fArr) {
        List list = new List();
        Object[] objArr = new Object[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            objArr[i] = Float.valueOf(fArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(double[] dArr) {
        List list = new List();
        Object[] objArr = new Object[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            objArr[i] = Double.valueOf(dArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(boolean[] zArr) {
        List list = new List();
        Object[] objArr = new Object[zArr.length];
        for (int i = 0; i < zArr.length; i++) {
            objArr[i] = Boolean.valueOf(zArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(short[] sArr) {
        List list = new List();
        Object[] objArr = new Object[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            objArr[i] = Short.valueOf(sArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static List ArrayToList(byte[] bArr) {
        List list = new List();
        Object[] objArr = new Object[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            objArr[i] = Byte.valueOf(bArr[i]);
        }
        list.setObject(Arrays.asList(objArr));
        return list;
    }

    public static boolean IsBackgroundTaskRunning(BA ba, Object obj, int i) {
        return BA.isTaskRunning(obj, i);
    }

    public static CanvasWrapper.BitmapWrapper LoadBitmap(String str, String str2) throws IOException {
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        bitmapWrapper.Initialize(str, str2);
        return bitmapWrapper;
    }

    public static CanvasWrapper.BitmapWrapper LoadBitmapSample(String str, String str2, int i, int i2) throws IOException {
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        bitmapWrapper.InitializeSample(str, str2, i, i2);
        return bitmapWrapper;
    }

    public static CanvasWrapper.BitmapWrapper LoadBitmapResize(String str, String str2, int i, int i2, boolean z) throws IOException {
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        bitmapWrapper.InitializeResize(str, str2, i, i2, z);
        return bitmapWrapper;
    }

    public static String SmartStringFormatter(String str, Object obj) {
        int i;
        int i2;
        if (str.length() == 0) {
            return BA.ObjectToString(obj);
        }
        if (str.equals("date")) {
            return DateTime.Date(BA.ObjectToLongNumber(obj));
        }
        if (str.equals("datetime")) {
            long jObjectToLongNumber = BA.ObjectToLongNumber(obj);
            return String.valueOf(DateTime.Date(jObjectToLongNumber)) + " " + DateTime.Time(jObjectToLongNumber);
        }
        if (str.equals("time")) {
            return DateTime.Time(BA.ObjectToLongNumber(obj));
        }
        if (str.equals("xml")) {
            StringBuilder sb = new StringBuilder();
            String strValueOf = String.valueOf(obj);
            for (int i3 = 0; i3 < strValueOf.length(); i3++) {
                char cCharAt = strValueOf.charAt(i3);
                if (cCharAt == '\"') {
                    sb.append("&quot;");
                } else if (cCharAt == '<') {
                    sb.append("&lt;");
                } else if (cCharAt == '>') {
                    sb.append("&gt;");
                } else if (cCharAt == '&') {
                    sb.append("&amp;");
                } else if (cCharAt == '\'') {
                    sb.append("&#39;");
                } else {
                    sb.append(cCharAt);
                }
            }
            return sb.toString();
        }
        int iIndexOf = str.indexOf(".");
        if (iIndexOf > -1) {
            i = Integer.parseInt(str.substring(0, iIndexOf));
            i2 = Integer.parseInt(str.substring(iIndexOf + 1));
        } else {
            i = Integer.parseInt(str);
            i2 = Integer.MAX_VALUE;
        }
        try {
            return NumberFormat(BA.ObjectToNumber(obj), i, i2);
        } catch (Exception unused) {
            return "NaN";
        }
    }

    public static void ExitApplication() {
        System.exit(0);
    }

    public static void Sleep(final BA ba, final BA.ResumableSub resumableSub, int i) {
        BA.handler.postDelayed(new BA.B4ARunnable() { // from class: anywheresoftware.b4a.keywords.Common.14
            /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
            
                if (r1 != r1.processBA.sharedProcessBA.activityBA.get()) goto L15;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r3 = this;
                    anywheresoftware.b4a.BA r0 = r1
                    if (r0 != 0) goto La
                    java.lang.String r0 = "Sleep failed to resume (ba = null)"
                    anywheresoftware.b4a.BA.LogError(r0)
                    return
                La:
                    anywheresoftware.b4a.BA r0 = r0.processBA
                    if (r0 == 0) goto L10
                    r0 = 1
                    goto L11
                L10:
                    r0 = 0
                L11:
                    if (r0 == 0) goto L47
                    anywheresoftware.b4a.BA r1 = r1
                    anywheresoftware.b4a.BA r1 = r1.processBA
                    anywheresoftware.b4a.BA$SharedProcessBA r1 = r1.sharedProcessBA
                    java.lang.ref.WeakReference<anywheresoftware.b4a.BA> r1 = r1.activityBA
                    if (r1 == 0) goto L2b
                    anywheresoftware.b4a.BA r1 = r1
                    anywheresoftware.b4a.BA r2 = r1.processBA
                    anywheresoftware.b4a.BA$SharedProcessBA r2 = r2.sharedProcessBA
                    java.lang.ref.WeakReference<anywheresoftware.b4a.BA> r2 = r2.activityBA
                    java.lang.Object r2 = r2.get()
                    if (r1 == r2) goto L47
                L2b:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "Sleep not resumed (context destroyed): "
                    r0.<init>(r1)
                    anywheresoftware.b4a.BA$ResumableSub r1 = r2
                    java.lang.Class r1 = r1.getClass()
                    java.lang.String r1 = r1.getName()
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    anywheresoftware.b4a.BA.LogInfo(r0)
                    return
                L47:
                    anywheresoftware.b4a.BA r1 = r1
                    boolean r1 = r1.isActivityPaused()
                    if (r1 == 0) goto L77
                    if (r0 == 0) goto L5b
                    anywheresoftware.b4a.BA r0 = r1
                    anywheresoftware.b4a.BA r0 = r0.processBA
                    java.lang.String r1 = "sleep"
                    r0.addMessageToPausedMessageQueue(r1, r3)
                    return
                L5b:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    java.lang.String r1 = "Sleep not resumed (context is paused): "
                    r0.<init>(r1)
                    anywheresoftware.b4a.BA$ResumableSub r1 = r2
                    java.lang.Class r1 = r1.getClass()
                    java.lang.String r1 = r1.getName()
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    anywheresoftware.b4a.BA.LogInfo(r0)
                    return
                L77:
                    anywheresoftware.b4a.BA$ResumableSub r0 = r2     // Catch: java.lang.Exception -> L80
                    anywheresoftware.b4a.BA r1 = r1     // Catch: java.lang.Exception -> L80
                    r2 = 0
                    r0.resume(r1, r2)     // Catch: java.lang.Exception -> L80
                    return
                L80:
                    r0 = move-exception
                    java.lang.RuntimeException r1 = new java.lang.RuntimeException
                    r1.<init>(r0)
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: anywheresoftware.b4a.keywords.Common.AnonymousClass14.run():void");
            }
        }, i);
    }

    public static void WaitFor(String str, BA ba, BA.ResumableSub resumableSub, Object obj) {
        if (ba.waitForEvents == null) {
            ba.waitForEvents = new HashMap<>();
        }
        if (obj instanceof ObjectWrapper) {
            obj = ((ObjectWrapper) obj).getObject();
        }
        if (obj instanceof BA.ResumableSub) {
            BA.ResumableSub resumableSub2 = (BA.ResumableSub) obj;
            if (resumableSub2.completed) {
                throw new RuntimeException("Resumable sub already completed");
            }
            resumableSub2.waitForBA = ba;
        }
        LinkedList<BA.WaitForEvent> linkedList = ba.waitForEvents.get(str);
        if (linkedList == null) {
            linkedList = new LinkedList<>();
            ba.waitForEvents.put(str, linkedList);
        }
        Iterator<BA.WaitForEvent> it = linkedList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            BA.WaitForEvent next = it.next();
            if (!z && ((obj == null && next.noFilter()) || (obj != null && obj == next.senderFilter.get()))) {
                next.rs = resumableSub;
                z = true;
            } else if (next.cleared()) {
                it.remove();
            }
        }
        if (z) {
            return;
        }
        BA.WaitForEvent waitForEvent = new BA.WaitForEvent(resumableSub, obj);
        if (waitForEvent.noFilter()) {
            linkedList.addLast(waitForEvent);
        } else {
            linkedList.addFirst(waitForEvent);
        }
    }

    public static void ReturnFromResumableSub(final BA.ResumableSub resumableSub, final Object obj) {
        BA.handler.post(new Runnable() { // from class: anywheresoftware.b4a.keywords.Common.15
            @Override // java.lang.Runnable
            public void run() {
                resumableSub.completed = true;
                if (resumableSub.waitForBA != null) {
                    resumableSub.waitForBA.raiseEvent(resumableSub, "complete", obj);
                }
            }
        });
    }

    @BA.ShortName("ResumableSub")
    public static class ResumableSubWrapper extends AbsObjectWrapper<BA.ResumableSub> {
        public boolean getCompleted() {
            return getObject().completed;
        }
    }
}
