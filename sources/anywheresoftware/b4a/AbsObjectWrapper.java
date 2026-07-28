package anywheresoftware.b4a;

import anywheresoftware.b4a.BA;
import java.util.HashMap;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class AbsObjectWrapper<T> implements ObjectWrapper<T> {
    private T object;
    private static final WeakHashMap<Object, HashMap<String, Object>> extraMap = new WeakHashMap<>();
    public static boolean Activity_LoadLayout_Was_Called = false;

    public boolean IsInitialized() {
        return this.object != null;
    }

    @Override // anywheresoftware.b4a.ObjectWrapper
    public T getObjectOrNull() {
        return this.object;
    }

    @Override // anywheresoftware.b4a.ObjectWrapper
    public T getObject() {
        String str;
        T t = this.object;
        if (t != null) {
            return t;
        }
        BA.ShortName shortName = (BA.ShortName) getClass().getAnnotation(BA.ShortName.class);
        if (shortName == null) {
            str = "Object should first be initialized.";
        } else {
            str = "Object should first be initialized (" + shortName.value() + ").";
        }
        try {
            if (Class.forName("anywheresoftware.b4a.objects.ViewWrapper").isInstance(this) && !Activity_LoadLayout_Was_Called) {
                str = String.valueOf(str) + "\nDid you forget to call Activity.LoadLayout?";
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(str);
    }

    public static HashMap<String, Object> getExtraTags(Object obj) {
        WeakHashMap<Object, HashMap<String, Object>> weakHashMap = extraMap;
        HashMap<String, Object> map = weakHashMap.get(obj);
        if (map != null) {
            return map;
        }
        HashMap<String, Object> map2 = new HashMap<>();
        weakHashMap.put(obj, map2);
        return map2;
    }

    @Override // anywheresoftware.b4a.ObjectWrapper
    public void setObject(T t) {
        this.object = t;
    }

    public int hashCode() {
        T t = this.object;
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return this.object == null;
        }
        if (obj instanceof AbsObjectWrapper) {
            AbsObjectWrapper absObjectWrapper = (AbsObjectWrapper) obj;
            T t = this.object;
            if (t == null) {
                return absObjectWrapper.object == null;
            }
            return t.equals(absObjectWrapper.object);
        }
        T t2 = this.object;
        if (t2 == null) {
            return false;
        }
        return t2.equals(obj);
    }

    public String baseToString() {
        String simpleName;
        T t = this.object;
        if (t != null) {
            simpleName = t.getClass().getSimpleName();
        } else {
            BA.ShortName shortName = (BA.ShortName) getClass().getAnnotation(BA.ShortName.class);
            if (shortName != null) {
                simpleName = shortName.value();
            } else {
                simpleName = getClass().getSimpleName();
            }
        }
        int iLastIndexOf = simpleName.lastIndexOf(".");
        if (iLastIndexOf > -1) {
            simpleName = simpleName.substring(iLastIndexOf + 1);
        }
        String str = "(" + simpleName + ")";
        if (this.object != null) {
            return str;
        }
        return String.valueOf(str) + " Not initialized";
    }

    public String toString() {
        String strBaseToString = baseToString();
        if (this.object == null) {
            return strBaseToString;
        }
        return String.valueOf(strBaseToString) + " " + this.object.toString();
    }

    public static ObjectWrapper ConvertToWrapper(ObjectWrapper objectWrapper, Object obj) {
        objectWrapper.setObject(obj);
        return objectWrapper;
    }
}
