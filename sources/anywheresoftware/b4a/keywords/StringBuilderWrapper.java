package anywheresoftware.b4a.keywords;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("StringBuilder")
public class StringBuilderWrapper extends AbsObjectWrapper<StringBuilder> implements BA.B4aDebuggable {
    public void Initialize() {
        setObject(new StringBuilder());
    }

    public StringBuilderWrapper Append(String str) {
        getObject().append(str);
        return this;
    }

    public String ToString() {
        return getObject().toString();
    }

    public StringBuilderWrapper Remove(int i, int i2) {
        getObject().delete(i, i2);
        return this;
    }

    public StringBuilderWrapper Insert(int i, String str) {
        getObject().insert(i, str);
        return this;
    }

    public int getLength() {
        return getObject().length();
    }

    @Override // anywheresoftware.b4a.AbsObjectWrapper
    public String toString() {
        return getObjectOrNull() == null ? "null" : getObject().toString();
    }

    @Override // anywheresoftware.b4a.BA.B4aDebuggable
    public Object[] debug(int i, boolean[] zArr) {
        Object[] objArr = {"Length", Integer.valueOf(getLength()), "ToString", toString()};
        zArr[0] = true;
        return objArr;
    }
}
