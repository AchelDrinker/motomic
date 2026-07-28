package anywheresoftware.b4a.keywords;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import anywheresoftware.b4a.BA;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class DateTime {
    public static final long TicksPerDay = 86400000;
    public static final long TicksPerHour = 3600000;
    public static final long TicksPerMinute = 60000;
    public static final long TicksPerSecond = 1000;
    private static DateTime _instance;
    private static long lastTimeSetEvent;
    private Calendar cal = Calendar.getInstance(Locale.US);
    private Date date;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private TimeZone timeZone;
    private static TimeZone zeroTimeZone = new SimpleTimeZone(0, "13256");
    private static boolean listenToTimeZone = false;

    private static DateTime getInst() {
        if (_instance == null) {
            _instance = new DateTime();
        }
        return _instance;
    }

    private DateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.dateFormat = simpleDateFormat;
        simpleDateFormat.setLenient(false);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        this.timeFormat = simpleDateFormat2;
        simpleDateFormat2.setLenient(false);
        this.date = new Date(0L);
        this.timeZone = TimeZone.getDefault();
    }

    public static void ListenToExternalTimeChanges(final BA ba) {
        if (listenToTimeZone) {
            return;
        }
        listenToTimeZone = true;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: anywheresoftware.b4a.keywords.DateTime.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("time-zone")) {
                    DateTime.setTimeZoneInternal(TimeZone.getTimeZone(intent.getStringExtra("time-zone")));
                }
                if (DateTime.getNow() - DateTime.lastTimeSetEvent > 100) {
                    ba.raiseEventFromDifferentThread(null, null, 0, "datetime_timechanged", false, null);
                }
                DateTime.lastTimeSetEvent = DateTime.getNow();
            }
        };
        IntentFilter intentFilter = new IntentFilter("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.TIME_SET");
        BA.applicationContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    public static long getNow() {
        return System.currentTimeMillis();
    }

    public static String Date(long j) {
        DateTime inst = getInst();
        inst.date.setTime(j);
        return inst.dateFormat.format(inst.date);
    }

    public static String Time(long j) {
        DateTime inst = getInst();
        inst.date.setTime(j);
        return inst.timeFormat.format(inst.date);
    }

    public static String getTimeFormat() {
        return getInst().timeFormat.toPattern();
    }

    public static void setTimeFormat(String str) {
        getInst().timeFormat.applyPattern(str);
    }

    public static String getDateFormat() {
        return getInst().dateFormat.toPattern();
    }

    public static void setDateFormat(String str) {
        getInst().dateFormat.applyPattern(str);
    }

    public static long DateParse(String str) throws ParseException {
        return getInst().dateFormat.parse(str).getTime();
    }

    public static String getDeviceDefaultDateFormat() {
        return ((SimpleDateFormat) DateFormat.getDateInstance()).toPattern();
    }

    public static String getDeviceDefaultTimeFormat() {
        return ((SimpleDateFormat) DateFormat.getTimeInstance()).toPattern();
    }

    public static long TimeParse(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = getInst().timeFormat;
        simpleDateFormat.setTimeZone(zeroTimeZone);
        try {
            long time = simpleDateFormat.parse(str).getTime();
            simpleDateFormat.setTimeZone(getInst().timeZone);
            long jRound = Math.round(getTimeZoneOffset() * 60.0d);
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = jRound * TicksPerMinute;
            long j2 = jCurrentTimeMillis + j;
            return ((j2 - (j2 % TicksPerDay)) - j) + (time % TicksPerDay);
        } catch (Throwable th) {
            simpleDateFormat.setTimeZone(getInst().timeZone);
            throw th;
        }
    }

    public static long DateTimeParse(String str, String str2) throws ParseException {
        SimpleDateFormat simpleDateFormat = getInst().dateFormat;
        SimpleDateFormat simpleDateFormat2 = getInst().timeFormat;
        simpleDateFormat.setTimeZone(zeroTimeZone);
        simpleDateFormat2.setTimeZone(zeroTimeZone);
        try {
            long jDateParse = DateParse(str) + simpleDateFormat2.parse(str2).getTime();
            int iGetTimeZoneOffsetAt = (int) (GetTimeZoneOffsetAt(jDateParse) * 3600000.0d);
            long j = jDateParse - ((long) iGetTimeZoneOffsetAt);
            long jGetTimeZoneOffsetAt = j + ((long) (iGetTimeZoneOffsetAt - ((int) (GetTimeZoneOffsetAt(j) * 3600000.0d))));
            return jGetTimeZoneOffsetAt;
        } finally {
            simpleDateFormat2.setTimeZone(getInst().timeZone);
            simpleDateFormat.setTimeZone(getInst().timeZone);
        }
    }

    public static void SetTimeZone(double d) {
        setTimeZoneInternal(new SimpleTimeZone((int) Math.round(d * 3600.0d * 1000.0d), ""));
    }

    public static void SetTimeZone(int i) {
        setTimeZoneInternal(new SimpleTimeZone(i * 3600000, ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTimeZoneInternal(TimeZone timeZone) {
        getInst().timeZone = timeZone;
        getInst().cal.setTimeZone(getInst().timeZone);
        getInst().dateFormat.setTimeZone(getInst().timeZone);
        getInst().timeFormat.setTimeZone(getInst().timeZone);
    }

    public static double getTimeZoneOffset() {
        double offset = getInst().timeZone.getOffset(System.currentTimeMillis());
        Double.isNaN(offset);
        return offset / 3600000.0d;
    }

    public static double GetTimeZoneOffsetAt(long j) {
        double offset = getInst().timeZone.getOffset(j);
        Double.isNaN(offset);
        return offset / 3600000.0d;
    }

    public static int GetYear(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(1);
    }

    public static int GetMonth(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(2) + 1;
    }

    public static int GetDayOfMonth(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(5);
    }

    public static int GetDayOfYear(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(6);
    }

    public static int GetDayOfWeek(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(7);
    }

    public static int GetHour(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(11);
    }

    public static int GetSecond(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(13);
    }

    public static int GetMinute(long j) {
        getInst().cal.setTimeInMillis(j);
        return getInst().cal.get(12);
    }

    public static long Add(long j, int i, int i2, int i3) {
        Calendar calendar = getInst().cal;
        calendar.setTimeInMillis(j);
        calendar.add(1, i);
        calendar.add(2, i2);
        calendar.add(6, i3);
        return calendar.getTimeInMillis();
    }
}
