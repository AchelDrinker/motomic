package anywheresoftware.b4a.phone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Contacts")
public class ContactsWrapper {
    private static final String[] people_projection = {"times_contacted", "number", "last_time_contacted", "display_name", "name", "notes", "starred", "_id"};

    public List GetAll() {
        return getAllContacts(null, null);
    }

    public List FindByName(String str, boolean z) {
        if (z) {
            return getAllContacts("name = ?", new String[]{str});
        }
        return getAllContacts("name LIKE ?", new String[]{"%" + str + "%"});
    }

    public List FindByMail(String str, boolean z) {
        String str2;
        ContentResolver contentResolver = BA.applicationContext.getContentResolver();
        if (!z) {
            str = "%" + str + "%";
            str2 = " LIKE ?";
        } else {
            str2 = " = ?";
        }
        Cursor cursorQuery = contentResolver.query(Contacts.ContactMethods.CONTENT_EMAIL_URI, new String[]{"person", "data"}, "data".concat(str2), new String[]{str}, null);
        StringBuilder sb = new StringBuilder();
        while (cursorQuery.moveToNext()) {
            for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
                sb.append(cursorQuery.getString(0));
                sb.append(",");
            }
        }
        int count = cursorQuery.getCount();
        cursorQuery.close();
        if (count == 0) {
            List list = new List();
            list.Initialize();
            return list;
        }
        sb.setLength(sb.length() - 1);
        return getAllContacts("_id IN (" + sb.toString() + ")", null);
    }

    public Contact GetById(int i) {
        List allContacts = getAllContacts("_id = ?", new String[]{String.valueOf(i)});
        if (allContacts.getSize() == 0) {
            return null;
        }
        return (Contact) allContacts.Get(0);
    }

    private List getAllContacts(String str, String[] strArr) {
        Cursor cursorQuery = BA.applicationContext.getContentResolver().query(Contacts.People.CONTENT_URI, people_projection, str, strArr, null);
        List list = new List();
        list.Initialize();
        HashMap map = new HashMap();
        for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
            map.put(cursorQuery.getColumnName(i), Integer.valueOf(i));
        }
        while (cursorQuery.moveToNext()) {
            list.Add(new Contact(cursorQuery.getString(((Integer) map.get("display_name")).intValue()), cursorQuery.getString(((Integer) map.get("number")).intValue()), cursorQuery.getInt(((Integer) map.get("starred")).intValue()) > 0, cursorQuery.getInt(((Integer) map.get("_id")).intValue()), cursorQuery.getString(((Integer) map.get("notes")).intValue()), cursorQuery.getInt(((Integer) map.get("times_contacted")).intValue()), cursorQuery.getLong(((Integer) map.get("last_time_contacted")).intValue()), cursorQuery.getString(((Integer) map.get("name")).intValue())));
        }
        cursorQuery.close();
        return list;
    }

    @BA.ShortName("Contact")
    public static class Contact {
        public static final int EMAIL_CUSTOM = 0;
        public static final int EMAIL_HOME = 1;
        public static final int EMAIL_MOBILE = 4;
        public static final int EMAIL_OTHER = 3;
        public static final int EMAIL_WORK = 2;
        public static final int PHONE_CUSTOM = 0;
        public static final int PHONE_FAX_HOME = 5;
        public static final int PHONE_FAX_WORK = 4;
        public static final int PHONE_HOME = 1;
        public static final int PHONE_MOBILE = 2;
        public static final int PHONE_OTHER = 7;
        public static final int PHONE_PAGER = 6;
        public static final int PHONE_WORK = 3;
        public String DisplayName;
        public int Id;
        public long LastTimeContacted;
        public String Name;
        public String Notes;
        public String PhoneNumber;
        public boolean Starred;
        public int TimesContacted;

        public Contact() {
            this.PhoneNumber = "";
            this.Id = -1;
        }

        Contact(String str, String str2, boolean z, int i, String str3, int i2, long j, String str4) {
            this.PhoneNumber = "";
            this.Id = -1;
            this.DisplayName = str == null ? "" : str;
            this.PhoneNumber = str2 == null ? "" : str2;
            this.Starred = z;
            this.Id = i;
            this.Notes = str3 == null ? "" : str3;
            this.TimesContacted = i2;
            this.LastTimeContacted = j;
            this.Name = str4 == null ? "" : str4;
        }

        public CanvasWrapper.BitmapWrapper GetPhoto() {
            CanvasWrapper.BitmapWrapper bitmapWrapper;
            byte[] blob;
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, this.Id), "photo"), new String[]{"data"}, null, null, null);
            if (!cursorQuery.moveToNext() || (blob = cursorQuery.getBlob(0)) == null) {
                bitmapWrapper = null;
            } else {
                File.InputStreamWrapper inputStreamWrapper = new File.InputStreamWrapper();
                inputStreamWrapper.InitializeFromBytesArray(blob, 0, blob.length);
                bitmapWrapper = new CanvasWrapper.BitmapWrapper();
                bitmapWrapper.Initialize2(inputStreamWrapper.getObject());
            }
            cursorQuery.close();
            return bitmapWrapper;
        }

        public Map GetEmails() {
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, this.Id), "contact_methods"), new String[]{"data", "type", "kind"}, "kind = 1", null, null);
            Map map = new Map();
            map.Initialize();
            while (cursorQuery.moveToNext()) {
                map.Put(cursorQuery.getString(0), Integer.valueOf(cursorQuery.getInt(1)));
            }
            cursorQuery.close();
            return map;
        }

        public Map GetPhones() {
            if (this.Id == -1) {
                throw new RuntimeException("Contact object should be set by calling one of the Contacts methods.");
            }
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(Uri.withAppendedPath(ContentUris.withAppendedId(Contacts.People.CONTENT_URI, this.Id), "phones"), new String[]{"number", "type"}, null, null, null);
            Map map = new Map();
            map.Initialize();
            while (cursorQuery.moveToNext()) {
                map.Put(cursorQuery.getString(0), Integer.valueOf(cursorQuery.getInt(1)));
            }
            cursorQuery.close();
            return map;
        }

        public String toString() {
            return "DisplayName=" + this.DisplayName + ", PhoneNumber=" + this.PhoneNumber + ", Starred=" + this.Starred + ", Id=" + this.Id + ", Notes=" + this.Notes + ", TimesContacted=" + this.TimesContacted + ", LastTimeContacted=" + this.LastTimeContacted + ", Name=" + this.Name;
        }
    }
}
