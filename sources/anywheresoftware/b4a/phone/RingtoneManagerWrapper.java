package anywheresoftware.b4a.phone;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.IOnActivityResult;
import anywheresoftware.b4a.objects.streams.File;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("RingtoneManager")
public class RingtoneManagerWrapper {
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    private IOnActivityResult ion;

    public String GetContentDir() {
        return File.ContentDir;
    }

    public String AddToMediaStore(String str, String str2, String str3, boolean z, boolean z2, boolean z3, boolean z4) {
        String mimeTypeFromExtension;
        java.io.File file = new java.io.File(str, str2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file.getAbsolutePath());
        contentValues.put("title", str3);
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
        if (fileExtensionFromUrl == null) {
            mimeTypeFromExtension = "audio/*";
        } else {
            mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        contentValues.put("mime_type", mimeTypeFromExtension);
        contentValues.put("is_ringtone", Boolean.valueOf(z3));
        contentValues.put("is_notification", Boolean.valueOf(z2));
        contentValues.put("is_alarm", Boolean.valueOf(z));
        contentValues.put("is_music", Boolean.valueOf(z4));
        return BA.applicationContext.getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath()), contentValues).toString();
    }

    public void SetDefault(int i, String str) {
        RingtoneManager.setActualDefaultRingtoneUri(BA.applicationContext, i, Uri.parse(str));
    }

    public String GetDefault(int i) {
        Uri defaultUri = RingtoneManager.getDefaultUri(i);
        if (defaultUri == null) {
            return "";
        }
        return defaultUri.toString();
    }

    public Object Play(BA ba, String str) {
        Ringtone ringtone = RingtoneManager.getRingtone(ba.context, Uri.parse(str));
        if (ringtone != null) {
            ringtone.play();
        }
        return ringtone;
    }

    public void Stop(Object obj) {
        if (obj != null) {
            ((Ringtone) obj).stop();
        }
    }

    public void DeleteRingtone(String str) {
        BA.applicationContext.getContentResolver().delete(Uri.parse(str), null, null);
    }

    public void ShowRingtonePicker(final BA ba, String str, int i, boolean z, String str2) {
        final String lowerCase = str.toLowerCase(BA.cul);
        Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
        intent.putExtra("android.intent.extra.ringtone.TYPE", i);
        intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", z);
        Uri defaultUri = RingtoneManager.getDefaultUri(i);
        if (defaultUri != null) {
            intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", defaultUri);
        }
        if (str2.length() > 0) {
            intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", Uri.parse(str2));
        }
        IOnActivityResult iOnActivityResult = new IOnActivityResult() { // from class: anywheresoftware.b4a.phone.RingtoneManagerWrapper.1
            @Override // anywheresoftware.b4a.IOnActivityResult
            public void ResultArrived(int i2, Intent intent2) {
                String string;
                if (i2 != -1 || intent2 == null) {
                    string = null;
                } else {
                    Uri uri = (Uri) intent2.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
                    if (uri == null) {
                        string = "";
                    } else {
                        string = uri.toString();
                    }
                }
                RingtoneManagerWrapper.this.ion = null;
                if (string != null) {
                    ba.raiseEvent(RingtoneManagerWrapper.this, String.valueOf(lowerCase) + "_pickerresult", true, string);
                    return;
                }
                ba.raiseEvent(RingtoneManagerWrapper.this, String.valueOf(lowerCase) + "_pickerresult", false, "");
            }
        };
        this.ion = iOnActivityResult;
        ba.startActivityForResult(iOnActivityResult, intent);
    }
}
