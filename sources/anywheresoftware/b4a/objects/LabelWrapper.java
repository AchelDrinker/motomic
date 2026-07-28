package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Label")
public class LabelWrapper extends TextViewWrapper<TextView> {
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            setObject(new TextView(ba.context));
        }
        super.innerInitialize(ba, str, true);
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        Drawable drawable;
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, TextView.class, map, z);
        }
        TextView textView = (TextView) TextViewWrapper.build(obj, map, z);
        HashMap map2 = (HashMap) map.get("drawable");
        if (map2 != null && (drawable = (Drawable) DynamicBuilder.build(obj, map2, z, null)) != null) {
            textView.setBackgroundDrawable(drawable);
        }
        return textView;
    }
}
