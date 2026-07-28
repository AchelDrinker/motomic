package anywheresoftware.b4a.objects.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("GradientDrawable")
public class GradientDrawable extends AbsObjectWrapper<android.graphics.drawable.GradientDrawable> {
    public void Initialize(GradientDrawable.Orientation orientation, int[] iArr) {
        setObject(new android.graphics.drawable.GradientDrawable(orientation, iArr));
    }

    public void setCornerRadius(float f) {
        getObject().setCornerRadius(f);
    }

    public static Drawable build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) {
        android.graphics.drawable.GradientDrawable gradientDrawable = new android.graphics.drawable.GradientDrawable((GradientDrawable.Orientation) Enum.valueOf(GradientDrawable.Orientation.class, (String) map.get("orientation")), new int[]{((Integer) map.get("firstColor")).intValue(), ((Integer) map.get("secondColor")).intValue()});
        gradientDrawable.setCornerRadius((int) (BALayout.getDeviceScale() * ((Integer) map.get("cornerRadius")).intValue()));
        return gradientDrawable;
    }
}
