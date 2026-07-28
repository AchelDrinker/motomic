package anywheresoftware.b4a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class BALayout extends ViewGroup {
    public static final int BOTH = 2;
    public static final int BOTTOM = 1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 0;
    private static float deviceScale = 0.0f;
    public static boolean disableAccessibility = false;
    public static float scale;

    public BALayout(Context context) {
        super(context);
    }

    public static void setDeviceScale(float f) {
        deviceScale = f;
    }

    public static void setUserScale(float f) {
        if (Float.compare(deviceScale, f) == 0) {
            scale = 1.0f;
        } else {
            scale = deviceScale / f;
        }
    }

    public static float getDeviceScale() {
        return deviceScale;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                if (childAt.getLayoutParams() instanceof LayoutParams) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    childAt.layout(layoutParams.left, layoutParams.top, layoutParams.left + childAt.getMeasuredWidth(), layoutParams.top + childAt.getMeasuredHeight());
                } else {
                    childAt.layout(0, 0, getLayoutParams().width, getLayoutParams().height);
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
        if (disableAccessibility) {
            return;
        }
        super.addChildrenForAccessibility(arrayList);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        setMeasuredDimension(resolveSize(getLayoutParams().width, i), resolveSize(getLayoutParams().height, i2));
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int left;
        public int top;

        public LayoutParams(int i, int i2, int i3, int i4) {
            super(i3, i4);
            this.left = i;
            this.top = i2;
        }

        public LayoutParams() {
            super(0, 0);
        }

        public HashMap<String, Object> toDesignerMap() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("left", Integer.valueOf(Math.round(this.left / BALayout.scale)));
            map.put("top", Integer.valueOf(Math.round(this.top / BALayout.scale)));
            map.put("width", Integer.valueOf(Math.round(this.width / BALayout.scale)));
            map.put("height", Integer.valueOf(Math.round(this.height / BALayout.scale)));
            return map;
        }

        public void setFromUserPlane(int i, int i2, int i3, int i4) {
            this.left = Math.round(i * BALayout.scale);
            this.top = Math.round(i2 * BALayout.scale);
            if (i3 > 0) {
                i3 = Math.round(i3 * BALayout.scale);
            }
            this.width = i3;
            if (i4 > 0) {
                i4 = Math.round(i4 * BALayout.scale);
            }
            this.height = i4;
        }
    }
}
