package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("HorizontalScrollView")
public class HorizontalScrollViewWrapper extends ViewWrapper<HorizontalScrollView> {
    private PanelWrapper pw = new PanelWrapper();

    /* JADX WARN: Multi-variable type inference failed */
    public void Initialize(BA ba, int i, String str) {
        super.Initialize(ba, str);
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(ba, "");
        ((HorizontalScrollView) getObject()).addView((View) panelWrapper.getObject(), i, -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            setObject(new MyHScrollView(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_scrollchanged") && (getObject() instanceof MyHScrollView)) {
            MyHScrollView myHScrollView = (MyHScrollView) getObject();
            myHScrollView.ba = ba;
            myHScrollView.eventName = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PanelWrapper getPanel() {
        this.pw.setObject((ViewGroup) ((HorizontalScrollView) getObject()).getChildAt(0));
        return this.pw;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void FullScroll(boolean z) {
        ((HorizontalScrollView) getObject()).fullScroll(z ? 66 : 17);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getScrollPosition() {
        return ((HorizontalScrollView) getObject()).getScrollX();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setScrollPosition(int i) {
        ((HorizontalScrollView) getObject()).smoothScrollTo(i, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void ScrollToNow(int i) {
        ((HorizontalScrollView) getObject()).scrollTo(i, 0);
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, MyHScrollView.class, map, z);
        }
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) ViewWrapper.build(obj, map, z);
        if (horizontalScrollView.getChildCount() > 0) {
            horizontalScrollView.removeAllViews();
        }
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("drawable"), z, null);
        if (map.containsKey("innerWidth")) {
            BALayout bALayout = new BALayout((Context) obj2);
            horizontalScrollView.addView(bALayout, (int) (BALayout.getDeviceScale() * ((Integer) map.get("innerWidth")).intValue()), -1);
            if (drawable != null) {
                bALayout.setBackgroundDrawable(drawable);
                return horizontalScrollView;
            }
        } else if (drawable != null) {
            horizontalScrollView.setBackgroundDrawable(drawable);
        }
        return horizontalScrollView;
    }

    public static class MyHScrollView extends HorizontalScrollView {
        public BA ba;
        public String eventName;

        public MyHScrollView(Context context) {
            super(context);
        }

        @Override // android.view.View
        protected void onScrollChanged(int i, int i2, int i3, int i4) {
            super.onScrollChanged(i, i2, i3, i4);
            BA ba = this.ba;
            if (ba != null) {
                ba.raiseEventFromUI(this, String.valueOf(this.eventName) + "_scrollchanged", Integer.valueOf(i));
            }
        }
    }
}
