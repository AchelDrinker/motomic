package anywheresoftware.b4a;

import android.util.Log;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class DynamicBuilder {
    public static <T> T build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) {
        String str = (String) map.get("type");
        try {
            if (str.startsWith(".")) {
                str = "anywheresoftware.b4a.objects" + str;
            }
            return (T) Class.forName(str).getMethod("build", Object.class, HashMap.class, Boolean.TYPE, Object.class).invoke(null, obj, map, Boolean.valueOf(z), obj2);
        } catch (InvocationTargetException e) {
            Log.e(e.getCause() instanceof FileNotFoundException ? "" : "B4A", "", e);
            return null;
        } catch (Exception e2) {
            Log.e("B4A", "", e2);
            return null;
        }
    }
}
