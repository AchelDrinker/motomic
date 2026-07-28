package anywheresoftware.b4a.phone;

import android.database.Cursor;
import android.provider.CallLog;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("CallLog")
public class CallLogWrapper {
    private static final String[] calls_projection = {"date", "type", "duration", "number", "_id", "name"};

    public List GetAll(int i) {
        return getAllCalls(null, null, i);
    }

    public CallItem GetById(int i) {
        List allCalls = getAllCalls("_id = ?", new String[]{String.valueOf(i)}, 0);
        if (allCalls.getSize() == 0) {
            return null;
        }
        return (CallItem) allCalls.Get(0);
    }

    public List GetSince(long j, int i) {
        return getAllCalls("date >= ?", new String[]{Long.toString(j)}, i);
    }

    private List getAllCalls(String str, String[] strArr, int i) {
        Cursor cursorQuery = BA.applicationContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, calls_projection, str, strArr, "date DESC");
        List list = new List();
        list.Initialize();
        HashMap map = new HashMap();
        int i2 = 0;
        for (int i3 = 0; i3 < cursorQuery.getColumnCount(); i3++) {
            map.put(cursorQuery.getColumnName(i3), Integer.valueOf(i3));
        }
        while (cursorQuery.moveToNext()) {
            list.Add(new CallItem(cursorQuery.getString(((Integer) map.get("number")).intValue()), cursorQuery.getInt(((Integer) map.get("_id")).intValue()), cursorQuery.getLong(((Integer) map.get("duration")).intValue()), cursorQuery.getInt(((Integer) map.get("type")).intValue()), cursorQuery.getLong(((Integer) map.get("date")).intValue()), cursorQuery.getString(((Integer) map.get("name")).intValue())));
            if (i > 0 && (i2 = i2 + 1) >= i) {
                break;
            }
        }
        cursorQuery.close();
        return list;
    }

    @BA.ShortName("CallItem")
    public static class CallItem {
        public static final int TYPE_INCOMING = 1;
        public static final int TYPE_MISSED = 3;
        public static final int TYPE_OUTGOING = 2;
        public String CachedName;
        public int CallType;
        public long Date;
        public long Duration;
        public int Id;
        public String Number;

        public CallItem() {
            this.Id = -1;
            this.CachedName = "";
        }

        CallItem(String str, int i, long j, int i2, long j2, String str2) {
            this.Id = -1;
            this.CachedName = "";
            this.Number = str == null ? "" : str;
            this.Id = i;
            this.CallType = i2;
            this.Duration = j;
            this.Date = j2;
            this.CachedName = str2 == null ? "" : str2;
        }

        public String toString() {
            return "Id=" + this.Id + ", Number=" + this.Number + ",CachedName=" + this.CachedName + ", Type=" + this.CallType + ", Date=" + this.Date + ", Duration=" + this.Duration;
        }
    }
}
