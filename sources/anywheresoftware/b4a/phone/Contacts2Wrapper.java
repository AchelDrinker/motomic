package anywheresoftware.b4a.phone;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.phone.ContactsWrapper;
import java.io.ByteArrayInputStream;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Contacts2")
public class Contacts2Wrapper {
    private static final String[] people_projection = {"times_contacted", "last_time_contacted", "display_name", "has_phone_number", "starred", "_id", "photo_id"};
    private static final String[] phone_projection = {"is_primary", "data1", "contact_id"};

    public List GetAll(boolean z, boolean z2) {
        return getAllContacts(null, null, z, z2);
    }

    public ContactsWrapper.Contact GetById(int i, boolean z, boolean z2) {
        List allContacts = getAllContacts("_id = ?", new String[]{String.valueOf(i)}, z, z2);
        if (allContacts.getSize() == 0) {
            return null;
        }
        return (ContactsWrapper.Contact) allContacts.Get(0);
    }

    public List FindByMail(String str, boolean z, boolean z2, boolean z3) {
        String str2;
        ContentResolver contentResolver = BA.applicationContext.getContentResolver();
        if (!z) {
            str = "%" + str + "%";
            str2 = " LIKE ?";
        } else {
            str2 = " = ?";
        }
        Cursor cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{"contact_id"}, "data1".concat(str2), new String[]{str}, null);
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
        return getAllContacts("_id IN (" + sb.toString() + ")", null, z2, z3);
    }

    public List FindByName(String str, boolean z, boolean z2, boolean z3) {
        if (z) {
            return getAllContacts("display_name = ?", new String[]{str}, z2, z3);
        }
        return getAllContacts("display_name LIKE ?", new String[]{"%" + str + "%"}, z2, z3);
    }

    public void GetContactsAsync(final BA ba, final String str, final String str2, final String[] strArr, final boolean z, final boolean z2) {
        BA.submitRunnable(new Runnable() { // from class: anywheresoftware.b4a.phone.Contacts2Wrapper.1
            @Override // java.lang.Runnable
            public void run() {
                List listGetContactsByQuery = Contacts2Wrapper.this.GetContactsByQuery(str2, strArr, z, z2);
                ba.raiseEventFromDifferentThread(this, this, 0, String.valueOf(str.toLowerCase(BA.cul)) + "_complete", true, new Object[]{listGetContactsByQuery});
            }
        }, this, 0);
    }

    public List GetContactsByQuery(String str, String[] strArr, boolean z, boolean z2) {
        if (str.length() == 0) {
            str = null;
        }
        return getAllContacts(str, strArr, z, z2);
    }

    private List getAllContacts(String str, String[] strArr, boolean z, boolean z2) {
        String str2;
        Cursor cursorQuery = BA.applicationContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, people_projection, str, strArr, null);
        List list = new List();
        list.Initialize();
        HashMap map = new HashMap();
        for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
            map.put(cursorQuery.getColumnName(i), Integer.valueOf(i));
        }
        while (cursorQuery.moveToNext()) {
            int i2 = cursorQuery.getInt(((Integer) map.get("_id")).intValue());
            String string = "";
            if (!z || cursorQuery.getInt(((Integer) map.get("has_phone_number")).intValue()) == 0) {
                str2 = "";
            } else {
                Cursor cursorQuery2 = BA.applicationContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phone_projection, "contact_id = " + i2, null, null);
                String string2 = "";
                while (cursorQuery2.moveToNext()) {
                    string2 = cursorQuery2.getString(cursorQuery2.getColumnIndex("data1"));
                    if (cursorQuery2.getInt(cursorQuery2.getColumnIndex("is_primary")) != 0) {
                        break;
                    }
                }
                cursorQuery2.close();
                str2 = string2;
            }
            boolean z3 = true;
            if (z2) {
                Cursor cursorQuery3 = BA.applicationContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{"data1"}, "contact_id = " + i2 + " AND mimetype = ?", new String[]{"vnd.android.cursor.item/note"}, null);
                while (cursorQuery3.moveToNext()) {
                    string = cursorQuery3.getString(0);
                }
                cursorQuery3.close();
            }
            String str3 = string;
            String string3 = cursorQuery.getString(((Integer) map.get("display_name")).intValue());
            if (cursorQuery.getInt(((Integer) map.get("starred")).intValue()) <= 0) {
                z3 = false;
            }
            list.Add(new Contact2(string3, str2, z3, i2, str3, cursorQuery.getInt(((Integer) map.get("times_contacted")).intValue()), cursorQuery.getLong(((Integer) map.get("last_time_contacted")).intValue()), cursorQuery.getString(((Integer) map.get("display_name")).intValue()), cursorQuery.getInt(((Integer) map.get("photo_id")).intValue())));
        }
        cursorQuery.close();
        return list;
    }

    protected static class Contact2 extends ContactsWrapper.Contact {
        private int photoId;

        Contact2(String str, String str2, boolean z, int i, String str3, int i2, long j, String str4, int i3) {
            super(str, str2, z, i, str3, i2, j, str4);
            this.photoId = i3;
        }

        @Override // anywheresoftware.b4a.phone.ContactsWrapper.Contact
        public CanvasWrapper.BitmapWrapper GetPhoto() {
            CanvasWrapper.BitmapWrapper bitmapWrapper;
            byte[] blob;
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, "_id=" + this.photoId, null, null);
            if (!cursorQuery.moveToNext() || (blob = cursorQuery.getBlob(cursorQuery.getColumnIndex("data15"))) == null) {
                bitmapWrapper = null;
            } else {
                bitmapWrapper = new CanvasWrapper.BitmapWrapper();
                bitmapWrapper.Initialize2(new ByteArrayInputStream(blob));
            }
            cursorQuery.close();
            return bitmapWrapper;
        }

        @Override // anywheresoftware.b4a.phone.ContactsWrapper.Contact
        public Map GetEmails() {
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{"data1", "data2"}, "contact_id = " + this.Id, null, null);
            Map map = new Map();
            map.Initialize();
            while (cursorQuery.moveToNext()) {
                map.Put(cursorQuery.getString(0), Integer.valueOf(cursorQuery.getInt(1)));
            }
            cursorQuery.close();
            return map;
        }

        @Override // anywheresoftware.b4a.phone.ContactsWrapper.Contact
        public Map GetPhones() {
            Cursor cursorQuery = BA.applicationContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data1", "data2"}, "contact_id = " + this.Id, null, null);
            Map map = new Map();
            map.Initialize();
            while (cursorQuery.moveToNext()) {
                map.Put(cursorQuery.getString(0), Integer.valueOf(cursorQuery.getInt(1)));
            }
            cursorQuery.close();
            return map;
        }
    }
}
