package anywheresoftware.b4a.phone;

import android.database.Cursor;
import android.net.Uri;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("SmsMessages")
public class SmsWrapper {
    public static final int TYPE_DRAFT = 3;
    public static final int TYPE_FAILED = 5;
    public static final int TYPE_INBOX = 1;
    public static final int TYPE_OUTBOX = 4;
    public static final int TYPE_QUEUED = 6;
    public static final int TYPE_SENT = 2;
    public static final int TYPE_UNKNOWN = 0;
    private static final String[] projection = {"_id", "thread_id", "address", "read", "type", "body", "person", "date"};

    public List GetByType(int i) {
        return get("type = ?", new String[]{String.valueOf(i)});
    }

    public List GetByMessageId(int i) {
        return get("_id = ?", new String[]{String.valueOf(i)});
    }

    public List GetByThreadId(int i) {
        return get("thread_id = ?", new String[]{String.valueOf(i)});
    }

    public List GetByPersonId(int i) {
        return get("person = ?", new String[]{String.valueOf(i)});
    }

    public List GetUnreadMessages() {
        return get("read = 0", null);
    }

    public List GetAll() {
        return get(null, null);
    }

    public List GetAllSince(long j) {
        return get("date >= ?", new String[]{String.valueOf(j)});
    }

    public List GetBetweenDates(long j, long j2) {
        return get("date >= ? AND date < ?", new String[]{String.valueOf(j), String.valueOf(j2)});
    }

    private List get(String str, String[] strArr) {
        Cursor cursorQuery = BA.applicationContext.getContentResolver().query(Uri.parse("content://sms"), projection, str, strArr, "date DESC");
        HashMap map = new HashMap();
        for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
            map.put(cursorQuery.getColumnName(i), Integer.valueOf(i));
        }
        List list = new List();
        list.Initialize();
        while (cursorQuery.moveToNext()) {
            String string = cursorQuery.getString(((Integer) map.get("person")).intValue());
            list.Add(new Sms(cursorQuery.getInt(((Integer) map.get("_id")).intValue()), cursorQuery.getInt(((Integer) map.get("thread_id")).intValue()), string == null ? -1 : Integer.parseInt(string), cursorQuery.getLong(((Integer) map.get("date")).intValue()), cursorQuery.getInt(((Integer) map.get("read")).intValue()) > 0, cursorQuery.getInt(((Integer) map.get("type")).intValue()), cursorQuery.getString(((Integer) map.get("body")).intValue()), cursorQuery.getString(((Integer) map.get("address")).intValue())));
        }
        cursorQuery.close();
        return list;
    }

    @BA.ShortName("Sms")
    public static class Sms {
        public String Address;
        public String Body;
        public long Date;
        public int Id;
        public int PersonId;
        public boolean Read;
        public int ThreadId;
        public int Type;

        public Sms(int i, int i2, int i3, long j, boolean z, int i4, String str, String str2) {
            this.Id = i;
            this.ThreadId = i2;
            this.PersonId = i3;
            this.Date = j;
            this.Read = z;
            this.Type = i4;
            this.Body = str;
            this.Address = str2;
        }

        public Sms() {
        }

        public String toString() {
            return "Id=" + this.Id + ", ThreadId=" + this.ThreadId + ", PersonId=" + this.PersonId + ", Date=" + this.Date + ", Read=" + this.Read + ", Type=" + this.Type + ", Body=" + this.Body + ", Address=" + this.Address;
        }
    }
}
