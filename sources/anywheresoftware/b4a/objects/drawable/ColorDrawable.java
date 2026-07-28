package anywheresoftware.b4a.objects.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.objects.ViewWrapper;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("ColorDrawable")
public class ColorDrawable extends AbsObjectWrapper<Drawable> {
    public void Initialize(int i, int i2) {
        Initialize2(i, i2, 0, 0);
    }

    public void Initialize2(int i, int i2, int i3, int i4) {
        GradientDrawableWithCorners gradientDrawableWithCorners = new GradientDrawableWithCorners();
        gradientDrawableWithCorners.setColor(i);
        gradientDrawableWithCorners.setCornerRadius(i2);
        gradientDrawableWithCorners.setStroke(i3, i4);
        setObject(gradientDrawableWithCorners);
    }

    public static class GradientDrawableWithCorners extends android.graphics.drawable.GradientDrawable {
        public int borderColor;
        public int borderWidth;
        public int color;
        public float cornerRadius;

        public GradientDrawableWithCorners() {
        }

        public GradientDrawableWithCorners(GradientDrawable.Orientation orientation, int[] iArr) {
            super(orientation, iArr);
        }

        @Override // android.graphics.drawable.GradientDrawable
        public void setCornerRadius(float f) {
            super.setCornerRadius(f);
            this.cornerRadius = f;
        }

        @Override // android.graphics.drawable.GradientDrawable
        public void setStroke(int i, int i2) {
            super.setStroke(i, i2);
            this.borderWidth = i;
            this.borderColor = i2;
        }

        @Override // android.graphics.drawable.GradientDrawable
        public void setColor(int i) {
            super.setColor(i);
            this.color = i;
        }
    }

    public static Drawable build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) {
        int iIntValue = ((Integer) map.get("color")).intValue();
        if (iIntValue == -984833) {
            if (!z) {
                return null;
            }
            Drawable drawable = (Drawable) ViewWrapper.getDefault((View) obj, "background", null);
            return drawable == null ? new android.graphics.drawable.ColorDrawable(0) : drawable;
        }
        if (map.containsKey("alpha")) {
            iIntValue = (((Integer) map.get("alpha")).intValue() << 24) | ((iIntValue << 8) >>> 8);
        }
        Integer num = (Integer) map.get("cornerRadius");
        if (num == null) {
            num = 0;
        }
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.Initialize2(iIntValue, (int) (BALayout.getDeviceScale() * num.intValue()), (int) (BALayout.getDeviceScale() * ((Integer) BA.gm(map, "borderWidth", 0)).intValue()), ((Integer) BA.gm(map, "borderColor", -16777216)).intValue());
        return colorDrawable.getObject();
    }
}
