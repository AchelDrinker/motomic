package anywheresoftware.b4a.objects;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.io.Serializable;
import java.net.URISyntaxException;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Intent")
public class IntentWrapper extends AbsObjectWrapper<Intent> {
    public static final String ACTION_APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String ACTION_CALL = "android.intent.action.CALL";
    public static final String ACTION_EDIT = "android.intent.action.EDIT";
    public static final String ACTION_MAIN = "android.intent.action.MAIN";
    public static final String ACTION_PICK = "android.intent.action.PICK";
    public static final String ACTION_SEND = "android.intent.action.SEND";
    public static final String ACTION_VIEW = "android.intent.action.VIEW";

    public void Initialize(String str, String str2) {
        setObject(new Intent(str, str2.length() > 0 ? Uri.parse(str2) : null));
    }

    public void Initialize2(String str, int i) throws URISyntaxException {
        setObject(Intent.parseUri(str, i));
    }

    public String getAction() {
        return getObject().getAction() == null ? "" : getObject().getAction();
    }

    public void setAction(String str) {
        getObject().setAction(str);
    }

    public void SetType(String str) {
        getObject().setDataAndType(getObject().getData(), str);
    }

    public int getFlags() {
        return getObject().getFlags();
    }

    public void setFlags(int i) {
        getObject().setFlags(i);
    }

    public void AddCategory(String str) {
        getObject().addCategory(str);
    }

    public String GetData() {
        return getObject().getDataString();
    }

    public void PutExtra(String str, Object obj) {
        Intent object = getObject();
        if (obj instanceof Serializable) {
            object.putExtra(str, (Serializable) obj);
        } else if (obj instanceof Parcelable) {
            object.putExtra(str, (Parcelable) obj);
        }
    }

    public Object GetExtra(String str) {
        return getObject().getSerializableExtra(str);
    }

    public boolean HasExtra(String str) {
        return getObject().hasExtra(str);
    }

    public String ExtrasToString() {
        if (!IsInitialized()) {
            return "not initialized";
        }
        Bundle extras = getObject().getExtras();
        if (extras == null) {
            return "no extras";
        }
        extras.size();
        return extras.toString();
    }

    public void WrapAsIntentChooser(String str) {
        setObject(Intent.createChooser(getObject(), str));
    }

    public void SetComponent(String str) {
        getObject().setComponent(ComponentName.unflattenFromString(str));
    }

    public void SetPackage(String str) {
        getObject().setPackage(str);
    }
}
