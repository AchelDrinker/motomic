package anywheresoftware.b4a.phone;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.IOnActivityResult;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* JADX INFO: loaded from: classes.dex */
@BA.Version(2.6f)
@BA.ShortName("Phone")
public class Phone {
    public static final int RINGER_NORMAL = 2;
    public static final int RINGER_SILENT = 0;
    public static final int RINGER_VIBRATE = 1;
    public static final int VOLUME_ALARM = 4;
    public static final int VOLUME_MUSIC = 3;
    public static final int VOLUME_NOTIFICATION = 5;
    public static final int VOLUME_RING = 2;
    public static final int VOLUME_SYSTEM = 1;
    public static final int VOLUME_VOICE_CALL = 0;

    public static void LIBRARY_DOC() {
    }

    public static int Shell(String str, String[] strArr, StringBuilder sb, StringBuilder sb2) throws InterruptedException, IOException {
        Process processExec;
        if (strArr == null || strArr.length == 0) {
            processExec = Runtime.getRuntime().exec(str);
        } else {
            String[] strArr2 = new String[strArr.length + 1];
            strArr2[0] = str;
            System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
            processExec = Runtime.getRuntime().exec(strArr2);
        }
        byte[] bArr = new byte[4096];
        if (sb != null) {
            InputStream inputStream = processExec.getInputStream();
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                sb.append(new String(bArr, 0, i, "UTF8"));
            }
        }
        if (sb2 != null) {
            InputStream errorStream = processExec.getErrorStream();
            while (true) {
                int i2 = errorStream.read(bArr);
                if (i2 == -1) {
                    break;
                }
                sb2.append(new String(bArr, 0, i2, "UTF8"));
            }
        }
        processExec.waitFor();
        return processExec.exitValue();
    }

    public static Object ShellAsync(BA ba, final String str, final String[] strArr) {
        final StringBuilder sb = new StringBuilder();
        final StringBuilder sb2 = new StringBuilder();
        Object obj = new Object();
        BA.runAsync(ba, obj, "complete", new Object[]{false, -1, sb.toString(), sb2.toString()}, new Callable<Object[]>() { // from class: anywheresoftware.b4a.phone.Phone.1
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                return new Object[]{true, Integer.valueOf(Phone.Shell(str, strArr, sb, sb2)), sb.toString(), sb2.toString()};
            }
        });
        return obj;
    }

    public Drawable GetResourceDrawable(int i) {
        return BA.applicationContext.getResources().getDrawable(i);
    }

    public static boolean IsAirplaneModeOn() {
        return Settings.System.getInt(BA.applicationContext.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public static String GetSettings(String str) {
        String string = Settings.Secure.getString(BA.applicationContext.getContentResolver(), str);
        if (string != null) {
            return string;
        }
        String string2 = Settings.System.getString(BA.applicationContext.getContentResolver(), str);
        return string2 == null ? "" : string2;
    }

    public static void SendBroadcastIntent(Intent intent) {
        BA.applicationContext.sendBroadcast(intent);
    }

    public static void SetScreenBrightness(BA ba, float f) {
        WindowManager.LayoutParams attributes = ba.sharedProcessBA.activityBA.get().activity.getWindow().getAttributes();
        attributes.screenBrightness = f;
        ba.sharedProcessBA.activityBA.get().activity.getWindow().setAttributes(attributes);
    }

    public static void SetScreenOrientation(BA ba, int i) {
        ba.sharedProcessBA.activityBA.get().activity.setRequestedOrientation(i);
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void HideKeyboard(ActivityWrapper activityWrapper) {
        ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(((BALayout) activityWrapper.getObject()).getWindowToken(), 0);
    }

    public static String GetSimOperator() {
        String simOperator = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSimOperator();
        return simOperator == null ? "" : simOperator;
    }

    public static String GetNetworkOperatorName() {
        String networkOperatorName = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getNetworkOperatorName();
        return networkOperatorName == null ? "" : networkOperatorName;
    }

    public static boolean IsNetworkRoaming() {
        return ((TelephonyManager) BA.applicationContext.getSystemService("phone")).isNetworkRoaming();
    }

    public static String GetNetworkType() {
        switch (((TelephonyManager) BA.applicationContext.getSystemService("phone")).getNetworkType()) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO_0";
            case 6:
                return "EVDO_A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "IDEN";
            case 12:
                return "EVDO_B";
            case 13:
                return "LTE";
            case 14:
                return "EHRPD";
            case 15:
                return "HSPAP";
            default:
                return "UNKNOWN";
        }
    }

    public static String GetPhoneType() {
        int phoneType = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getPhoneType();
        if (phoneType == 1) {
            return "GSM";
        }
        if (phoneType == 2) {
            return "CDMA";
        }
        return "NONE";
    }

    public static String GetDataState() {
        int dataState = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getDataState();
        if (dataState == 1) {
            return "CONNECTING";
        }
        if (dataState == 2) {
            return "CONNECTED";
        }
        if (dataState == 3) {
            return DebugCoroutineInfoImplKt.SUSPENDED;
        }
        return "DISCONNECTED";
    }

    public static int GetMaxVolume(int i) {
        return getAudioManager().getStreamMaxVolume(i);
    }

    public static void SetVolume(int i, int i2, boolean z) {
        getAudioManager().setStreamVolume(i, i2, z ? 1 : 0);
    }

    public static int GetVolume(int i) {
        return getAudioManager().getStreamVolume(i);
    }

    public static void SetMute(int i, boolean z) {
        getAudioManager().setStreamMute(i, z);
    }

    public static void SetRingerMode(int i) {
        getAudioManager().setRingerMode(i);
    }

    public static int GetRingerMode() {
        return getAudioManager().getRingerMode();
    }

    private static AudioManager getAudioManager() {
        return (AudioManager) BA.applicationContext.getSystemService("audio");
    }

    @BA.ShortName("PhoneId")
    public static class PhoneId {
        public static String GetDeviceId() {
            String deviceId = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getDeviceId();
            return deviceId == null ? "" : deviceId;
        }

        public static String GetLine1Number() {
            String line1Number = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getLine1Number();
            return line1Number == null ? "" : line1Number;
        }

        public static String GetSubscriberId() {
            String subscriberId = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSubscriberId();
            return subscriberId == null ? "" : subscriberId;
        }

        public static String GetSimSerialNumber() {
            String simSerialNumber = ((TelephonyManager) BA.applicationContext.getSystemService("phone")).getSimSerialNumber();
            return simSerialNumber == null ? "" : simSerialNumber;
        }
    }

    @BA.ShortName("PhoneVibrate")
    public static class PhoneVibrate {
        public static void Vibrate(BA ba, long j) {
            ((Vibrator) ba.context.getSystemService("vibrator")).vibrate(j);
        }
    }

    @BA.ShortName("PhoneWakeState")
    public static class PhoneWakeState {
        private static PowerManager.WakeLock partialLock;
        private static PowerManager.WakeLock wakeLock;

        public static void KeepAlive(BA ba, boolean z) {
            PowerManager.WakeLock wakeLock2 = wakeLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                Common.Log("WakeLock already held.");
                return;
            }
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) ba.context.getSystemService("power")).newWakeLock((z ? 10 : 6) | 268435456, "B4A");
            wakeLock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.acquire();
        }

        public static void PartialLock(BA ba) {
            PowerManager.WakeLock wakeLock2 = partialLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                Common.Log("Partial wakeLock already held.");
                return;
            }
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) ba.context.getSystemService("power")).newWakeLock(1, "B4A-Partial");
            partialLock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.acquire();
        }

        public static void ReleaseKeepAlive() {
            PowerManager.WakeLock wakeLock2 = wakeLock;
            if (wakeLock2 == null || !wakeLock2.isHeld()) {
                Common.Log("No wakelock.");
            } else {
                wakeLock.release();
            }
        }

        public static void ReleasePartialLock() {
            PowerManager.WakeLock wakeLock2 = partialLock;
            if (wakeLock2 == null || !wakeLock2.isHeld()) {
                Common.Log("No partial wakelock.");
            } else {
                partialLock.release();
            }
        }
    }

    @BA.ShortName("PhoneIntents")
    public static class PhoneIntents {
        public static Intent OpenBrowser(String str) {
            return new Intent(IntentWrapper.ACTION_VIEW, Uri.parse(str));
        }

        public static Intent PlayAudio(String str, String str2) {
            Uri uri;
            if (str.equals(File.ContentDir)) {
                uri = Uri.parse(str2);
            } else {
                uri = Uri.parse("file://" + new java.io.File(str, str2).toString());
            }
            Intent intent = new Intent(IntentWrapper.ACTION_VIEW);
            intent.setDataAndType(uri, "audio/*");
            return intent;
        }

        public static Intent PlayVideo(String str, String str2) {
            Uri uri;
            if (str.equals(File.ContentDir)) {
                uri = Uri.parse(str2);
            } else {
                uri = Uri.parse("file://" + new java.io.File(str, str2).toString());
            }
            Intent intent = new Intent(IntentWrapper.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
            return intent;
        }
    }

    @BA.ShortName("PhoneCalls")
    public static class PhoneCalls {
        public static Intent Call(String str) {
            return new Intent(IntentWrapper.ACTION_CALL, Uri.parse("tel:" + str));
        }
    }

    @BA.ShortName("PhoneSms")
    public static class PhoneSms {
        public static void Send(String str, String str2) {
            Send2(str, str2, true, true);
        }

        public static void Send2(String str, String str2, boolean z, boolean z2) {
            SmsManager smsManager = SmsManager.getDefault();
            Intent intent = new Intent("b4a.smssent");
            intent.putExtra("phone", str);
            int i = Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728;
            PendingIntent broadcast = z ? PendingIntent.getBroadcast(BA.applicationContext, 0, intent, i) : null;
            Intent intent2 = new Intent("b4a.smsdelivered");
            intent2.putExtra("phone", str);
            smsManager.sendTextMessage(str, null, str2, broadcast, z2 ? PendingIntent.getBroadcast(BA.applicationContext, 0, intent2, i) : null);
        }
    }

    @BA.ShortName("Email")
    public static class Email {
        public String Subject = "";
        public String Body = "";
        public List To = new List();
        public List CC = new List();
        public List BCC = new List();
        public List Attachments = new List();

        public Email() {
            this.To.Initialize();
            this.CC.Initialize();
            this.BCC.Initialize();
            this.Attachments.Initialize();
        }

        public Intent GetHtmlIntent() {
            return getIntent(true);
        }

        private Intent getIntent(boolean z) {
            Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
            intent.setType(z ? "text/html" : "text/plain");
            intent.putExtra("android.intent.extra.EMAIL", (String[]) this.To.getObject().toArray(new String[0]));
            intent.putExtra("android.intent.extra.CC", (String[]) this.CC.getObject().toArray(new String[0]));
            intent.putExtra("android.intent.extra.BCC", (String[]) this.BCC.getObject().toArray(new String[0]));
            intent.putExtra("android.intent.extra.SUBJECT", this.Subject);
            intent.putExtra("android.intent.extra.TEXT", z ? Html.fromHtml(this.Body) : this.Body);
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (Object obj : this.Attachments.getObject()) {
                if (obj instanceof Uri) {
                    arrayList.add((Uri) obj);
                } else {
                    arrayList.add(Uri.fromFile(new java.io.File((String) obj)));
                }
            }
            if (arrayList.size() == 1) {
                intent.putExtra("android.intent.extra.STREAM", arrayList.get(0));
                intent.setAction(IntentWrapper.ACTION_SEND);
                return intent;
            }
            if (arrayList.size() > 1) {
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
            }
            return intent;
        }

        public Intent GetIntent() {
            return getIntent(false);
        }
    }

    @BA.ShortName("LogCat")
    public static class LogCat {
        private static Process lc;
        private static Thread logcatReader;
        private static volatile InputStream logcatStream;
        private static volatile boolean logcatWorking;

        public static void LogCatStart(final BA ba, String[] strArr, String str) throws InterruptedException, IOException {
            LogCatStop();
            if (logcatReader != null) {
                int i = 10;
                while (logcatReader.isAlive()) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    Thread.sleep(50L);
                    logcatReader.interrupt();
                    i = i2;
                }
            }
            final String[] strArr2 = new String[strArr.length + 1];
            strArr2[0] = "/system/bin/logcat";
            System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
            final String str2 = String.valueOf(str.toLowerCase(BA.cul)) + "_logcatdata";
            Thread thread = new Thread(new Runnable() { // from class: anywheresoftware.b4a.phone.Phone.LogCat.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        LogCat.lc = Runtime.getRuntime().exec(strArr2);
                        LogCat.logcatStream = LogCat.lc.getInputStream();
                        InputStream inputStream = LogCat.logcatStream;
                        LogCat.logcatWorking = true;
                        byte[] bArr = new byte[4092];
                        while (true) {
                            if (!LogCat.logcatWorking) {
                                break;
                            }
                            int i3 = inputStream.read(bArr);
                            Thread.sleep(100L);
                            while (i3 > 0 && inputStream.available() > 0 && i3 < 4092) {
                                i3 += inputStream.read(bArr, i3, 4092 - i3);
                                Thread.sleep(100L);
                            }
                            if (i3 == -1) {
                                LogCat.logcatWorking = false;
                                break;
                            }
                            ba.raiseEvent(null, str2, bArr, Integer.valueOf(i3));
                        }
                        LogCat.lc.destroy();
                    } catch (Exception unused) {
                        if (LogCat.lc != null) {
                            LogCat.lc.destroy();
                        }
                    }
                }
            });
            logcatReader = thread;
            thread.setDaemon(true);
            logcatReader.start();
        }

        public static void LogCatStop() throws IOException {
            logcatWorking = false;
            if (logcatStream != null) {
                logcatStream.close();
            }
            Process process = lc;
            if (process != null) {
                process.destroy();
            }
        }
    }

    @BA.ShortName("ContentChooser")
    public static class ContentChooser implements BA.CheckForReinitialize {
        private String eventName;
        private IOnActivityResult ion;

        public void Initialize(String str) {
            this.eventName = str.toLowerCase(BA.cul);
        }

        @Override // anywheresoftware.b4a.BA.CheckForReinitialize
        public boolean IsInitialized() {
            return this.eventName != null;
        }

        public void Show(final BA ba, String str, String str2) {
            if (this.eventName == null) {
                throw new RuntimeException("ContentChooser not initialized.");
            }
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType(str);
            intent.addCategory("android.intent.category.OPENABLE");
            Intent intentCreateChooser = Intent.createChooser(intent, str2);
            IOnActivityResult iOnActivityResult = new IOnActivityResult() { // from class: anywheresoftware.b4a.phone.Phone.ContentChooser.1
                /* JADX WARN: Removed duplicated region for block: B:17:0x003a  */
                @Override // anywheresoftware.b4a.IOnActivityResult
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void ResultArrived(int r10, android.content.Intent r11) {
                    /*
                        r9 = this;
                        r0 = -1
                        java.lang.String r1 = ""
                        r2 = 0
                        if (r10 != r0) goto L3a
                        if (r11 == 0) goto L3a
                        android.net.Uri r10 = r11.getData()
                        if (r10 == 0) goto L3a
                        android.net.Uri r10 = r11.getData()     // Catch: java.lang.Exception -> L33
                        java.lang.String r11 = r10.getScheme()     // Catch: java.lang.Exception -> L33
                        java.lang.String r0 = "file"
                        boolean r0 = r0.equals(r11)     // Catch: java.lang.Exception -> L33
                        if (r0 == 0) goto L24
                        java.lang.String r10 = r10.getPath()     // Catch: java.lang.Exception -> L33
                        r11 = r1
                        goto L3c
                    L24:
                        java.lang.String r0 = "content"
                        boolean r11 = r0.equals(r11)     // Catch: java.lang.Exception -> L33
                        if (r11 == 0) goto L3a
                        java.lang.String r11 = "ContentDir"
                        java.lang.String r10 = r10.toString()     // Catch: java.lang.Exception -> L33
                        goto L3c
                    L33:
                        r10 = move-exception
                        java.lang.RuntimeException r11 = new java.lang.RuntimeException
                        r11.<init>(r10)
                        throw r11
                    L3a:
                        r10 = r2
                        r11 = r10
                    L3c:
                        anywheresoftware.b4a.phone.Phone$ContentChooser r0 = anywheresoftware.b4a.phone.Phone.ContentChooser.this
                        anywheresoftware.b4a.phone.Phone.ContentChooser.access$0(r0, r2)
                        r0 = 2
                        r2 = 3
                        java.lang.String r3 = "_result"
                        r4 = 1
                        r5 = 0
                        if (r11 == 0) goto L75
                        if (r10 == 0) goto L75
                        anywheresoftware.b4a.BA r1 = r2
                        anywheresoftware.b4a.phone.Phone$ContentChooser r6 = anywheresoftware.b4a.phone.Phone.ContentChooser.this
                        java.lang.StringBuilder r7 = new java.lang.StringBuilder
                        anywheresoftware.b4a.phone.Phone$ContentChooser r8 = anywheresoftware.b4a.phone.Phone.ContentChooser.this
                        java.lang.String r8 = anywheresoftware.b4a.phone.Phone.ContentChooser.access$1(r8)
                        java.lang.String r8 = java.lang.String.valueOf(r8)
                        r7.<init>(r8)
                        r7.append(r3)
                        java.lang.String r3 = r7.toString()
                        java.lang.Boolean r7 = java.lang.Boolean.valueOf(r4)
                        java.lang.Object[] r2 = new java.lang.Object[r2]
                        r2[r5] = r7
                        r2[r4] = r11
                        r2[r0] = r10
                        r1.raiseEvent(r6, r3, r2)
                        return
                    L75:
                        anywheresoftware.b4a.BA r10 = r2
                        anywheresoftware.b4a.phone.Phone$ContentChooser r11 = anywheresoftware.b4a.phone.Phone.ContentChooser.this
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        anywheresoftware.b4a.phone.Phone$ContentChooser r7 = anywheresoftware.b4a.phone.Phone.ContentChooser.this
                        java.lang.String r7 = anywheresoftware.b4a.phone.Phone.ContentChooser.access$1(r7)
                        java.lang.String r7 = java.lang.String.valueOf(r7)
                        r6.<init>(r7)
                        r6.append(r3)
                        java.lang.String r3 = r6.toString()
                        java.lang.Boolean r6 = java.lang.Boolean.valueOf(r5)
                        java.lang.Object[] r2 = new java.lang.Object[r2]
                        r2[r5] = r6
                        r2[r4] = r1
                        r2[r0] = r1
                        r10.raiseEvent(r11, r3, r2)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: anywheresoftware.b4a.phone.Phone.ContentChooser.AnonymousClass1.ResultArrived(int, android.content.Intent):void");
                }
            };
            this.ion = iOnActivityResult;
            ba.startActivityForResult(iOnActivityResult, intentCreateChooser);
        }
    }

    @BA.ShortName("VoiceRecognition")
    public static class VoiceRecognition {
        private String eventName;
        private IOnActivityResult ion;
        private String language;
        private String prompt;

        public void Initialize(String str) {
            this.eventName = str.toLowerCase(BA.cul);
        }

        public void setPrompt(String str) {
            this.prompt = str;
        }

        public void setLanguage(String str) {
            this.language = str;
        }

        public boolean IsSupported() {
            return BA.applicationContext.getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() > 0;
        }

        public void Listen(BA ba) {
            if (this.eventName == null) {
                throw new RuntimeException("VoiceRecognition was not initialized.");
            }
            Listen2(ba, new Intent("android.speech.action.RECOGNIZE_SPEECH"));
        }

        public void Listen2(final BA ba, Intent intent) {
            String str = this.prompt;
            if (str != null && str.length() > 0) {
                intent.putExtra("android.speech.extra.PROMPT", this.prompt);
            }
            String str2 = this.language;
            if (str2 != null && str2.length() > 0) {
                intent.putExtra("android.speech.extra.LANGUAGE", this.language);
            }
            IOnActivityResult iOnActivityResult = new IOnActivityResult() { // from class: anywheresoftware.b4a.phone.Phone.VoiceRecognition.1
                @Override // anywheresoftware.b4a.IOnActivityResult
                public void ResultArrived(int i, Intent intent2) {
                    List list = new List();
                    if (i == -1) {
                        ArrayList<String> stringArrayListExtra = intent2.getStringArrayListExtra("android.speech.extra.RESULTS");
                        if (stringArrayListExtra.size() > 0) {
                            list.setObject(stringArrayListExtra);
                        }
                    }
                    VoiceRecognition.this.ion = null;
                    ba.raiseEvent(VoiceRecognition.this, String.valueOf(VoiceRecognition.this.eventName) + "_result", Boolean.valueOf(list.IsInitialized()), list);
                }
            };
            this.ion = iOnActivityResult;
            ba.startActivityForResult(iOnActivityResult, intent);
        }
    }

    @BA.ShortName("PhoneOrientation")
    public static class PhoneOrientation {
        private SensorEventListener listener;

        public void StartListening(final BA ba, String str) {
            SensorManager sensorManager = (SensorManager) ba.context.getSystemService("sensor");
            final String str2 = String.valueOf(str.toLowerCase(BA.cul)) + "_orientationchanged";
            SensorEventListener sensorEventListener = new SensorEventListener() { // from class: anywheresoftware.b4a.phone.Phone.PhoneOrientation.1
                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(Sensor sensor, int i) {
                }

                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    ba.raiseEvent(this, str2, Float.valueOf(sensorEvent.values[0]), Float.valueOf(sensorEvent.values[1]), Float.valueOf(sensorEvent.values[2]));
                }
            };
            this.listener = sensorEventListener;
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(3), 3);
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) ba.context.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }
    }

    @BA.ShortName("PhoneAccelerometer")
    public static class PhoneAccelerometer {
        private SensorEventListener listener;

        public void StartListening(final BA ba, String str) {
            SensorManager sensorManager = (SensorManager) ba.context.getSystemService("sensor");
            final String str2 = String.valueOf(str.toLowerCase(BA.cul)) + "_accelerometerchanged";
            SensorEventListener sensorEventListener = new SensorEventListener() { // from class: anywheresoftware.b4a.phone.Phone.PhoneAccelerometer.1
                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(Sensor sensor, int i) {
                }

                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    ba.raiseEvent(this, str2, Float.valueOf(sensorEvent.values[0]), Float.valueOf(sensorEvent.values[1]), Float.valueOf(sensorEvent.values[2]));
                }
            };
            this.listener = sensorEventListener;
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(1), 3);
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) ba.context.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }
    }

    @BA.ShortName("PhoneSensors")
    public static class PhoneSensors {
        public static int TYPE_ACCELEROMETER = 1;
        public static int TYPE_GYROSCOPE = 4;
        public static int TYPE_LIGHT = 5;
        public static int TYPE_MAGNETIC_FIELD = 2;
        public static int TYPE_ORIENTATION = 3;
        public static int TYPE_PRESSURE = 6;
        public static int TYPE_PROXIMITY = 8;
        public static int TYPE_TEMPERATURE = 7;
        private int currentType;
        private SensorEventListener listener;
        private int sensorDelay;
        public SensorEvent sensorEvent;

        public void Initialize(int i) {
            Initialize2(i, 3);
        }

        public void Initialize2(int i, int i2) {
            this.currentType = i;
            this.sensorDelay = i2;
        }

        public boolean StartListening(final BA ba, String str) {
            SensorManager sensorManager = (SensorManager) BA.applicationContext.getSystemService("sensor");
            final String str2 = String.valueOf(str.toLowerCase(BA.cul)) + "_sensorchanged";
            SensorEventListener sensorEventListener = new SensorEventListener() { // from class: anywheresoftware.b4a.phone.Phone.PhoneSensors.1
                @Override // android.hardware.SensorEventListener
                public void onAccuracyChanged(Sensor sensor, int i) {
                }

                @Override // android.hardware.SensorEventListener
                public void onSensorChanged(SensorEvent sensorEvent) {
                    PhoneSensors.this.sensorEvent = sensorEvent;
                    ba.raiseEvent(PhoneSensors.this, str2, sensorEvent.values);
                }
            };
            this.listener = sensorEventListener;
            return sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(this.currentType), this.sensorDelay);
        }

        public int getAccuracy() {
            SensorEvent sensorEvent = this.sensorEvent;
            if (sensorEvent == null) {
                return 0;
            }
            return sensorEvent.accuracy;
        }

        public long getTimestamp() {
            SensorEvent sensorEvent = this.sensorEvent;
            if (sensorEvent == null) {
                return 0L;
            }
            return sensorEvent.timestamp;
        }

        public void StopListening(BA ba) {
            if (this.listener != null) {
                ((SensorManager) BA.applicationContext.getSystemService("sensor")).unregisterListener(this.listener);
            }
        }

        public float getMaxValue() {
            java.util.List<Sensor> sensorList = ((SensorManager) BA.applicationContext.getSystemService("sensor")).getSensorList(this.currentType);
            if (sensorList == null || sensorList.size() == 0) {
                return -1.0f;
            }
            return sensorList.get(0).getMaximumRange();
        }
    }
}
