package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("ScrollView")
public class ScrollViewWrapper extends ViewWrapper<ScrollView> {
    private PanelWrapper pw = new PanelWrapper();

    public void Initialize(BA ba, int i) {
        Initialize2(ba, i, "");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Initialize2(BA ba, int i, String str) {
        super.Initialize(ba, str);
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(ba, "");
        ((ScrollView) getObject()).addView((View) panelWrapper.getObject(), -1, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            setObject(new MyScrollView(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_scrollchanged") && (getObject() instanceof MyScrollView)) {
            MyScrollView myScrollView = (MyScrollView) getObject();
            myScrollView.ba = ba;
            myScrollView.eventName = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PanelWrapper getPanel() {
        this.pw.setObject((ViewGroup) ((ScrollView) getObject()).getChildAt(0));
        return this.pw;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void FullScroll(boolean z) {
        ((ScrollView) getObject()).fullScroll(z ? 130 : 33);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getScrollPosition() {
        return ((ScrollView) getObject()).getScrollY();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setScrollPosition(int i) {
        ((ScrollView) getObject()).smoothScrollTo(0, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void ScrollToNow(int i) {
        ((ScrollView) getObject()).scrollTo(0, i);
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, MyScrollView.class, map, z);
        }
        ScrollView scrollView = (ScrollView) ViewWrapper.build(obj, map, z);
        if (scrollView.getChildCount() > 0) {
            scrollView.removeAllViews();
        }
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("drawable"), z, null);
        if (map.containsKey("innerHeight")) {
            BALayout bALayout = new BALayout((Context) obj2);
            scrollView.addView(bALayout, -1, (int) (BALayout.getDeviceScale() * ((Integer) map.get("innerHeight")).intValue()));
            bALayout.setBackgroundDrawable(drawable);
            return scrollView;
        }
        scrollView.setBackgroundDrawable(drawable);
        return scrollView;
    }

    public static class MyScrollView extends ScrollView {
        public BA ba;
        public String eventName;

        public MyScrollView(Context context) {
            super(context);
        }

        @Override // android.view.View
        protected void onScrollChanged(int i, int i2, int i3, int i4) {
            super.onScrollChanged(i, i2, i3, i4);
            BA ba = this.ba;
            if (ba != null) {
                ba.raiseEventFromUI(this, String.valueOf(this.eventName) + "_scrollchanged", Integer.valueOf(i2));
            }
        }
    }
}
