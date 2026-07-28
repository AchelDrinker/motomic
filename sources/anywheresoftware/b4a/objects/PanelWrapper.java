package anywheresoftware.b4a.objects;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Panel")
public class PanelWrapper extends ViewWrapper<ViewGroup> implements BA.IterableList {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UP = 1;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new BALayout(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_touch")) {
            ((ViewGroup) getObject()).setOnTouchListener(new View.OnTouchListener() { // from class: anywheresoftware.b4a.objects.PanelWrapper.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ba.raiseEventFromUI(PanelWrapper.this.getObject(), String.valueOf(str) + "_touch", Integer.valueOf(motionEvent.getAction()), Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()));
                    return true;
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AddView(View view, int i, int i2, int i3, int i4) {
        ((ViewGroup) getObject()).addView(view, new BALayout.LayoutParams(i, i2, i3, i4));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConcreteViewWrapper GetView(int i) {
        ConcreteViewWrapper concreteViewWrapper = new ConcreteViewWrapper();
        concreteViewWrapper.setObject(((ViewGroup) getObject()).getChildAt(i));
        return concreteViewWrapper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveAllViews() {
        ((ViewGroup) getObject()).removeAllViews();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveViewAt(int i) {
        ((ViewGroup) getObject()).removeViewAt(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getNumberOfViews() {
        return ((ViewGroup) getObject()).getChildCount();
    }

    public float getElevation() throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((Float) View.class.getDeclaredMethod("getElevation", null).invoke(getObject(), null)).floatValue();
        }
        return 0.0f;
    }

    public void setElevation(float f) throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            View.class.getDeclaredMethod("setElevation", Float.TYPE).invoke(getObject(), Float.valueOf(f));
        }
    }

    public void SetElevationAnimated(int i, float f) throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            float elevation = getElevation();
            setElevation(f);
            ObjectAnimator.ofFloat(getObject(), "translationZ", elevation - f, 0.0f).setDuration(i).start();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public anywheresoftware.b4a.keywords.LayoutValues LoadLayout(java.lang.String r6, anywheresoftware.b4a.BA r7) throws java.lang.Exception {
        /*
            r5 = this;
            java.lang.Object r0 = r5.getObject()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L19
            int r3 = r0.width
            if (r3 == 0) goto L19
            int r3 = r0.height
            if (r3 != 0) goto L17
            goto L19
        L17:
            r3 = 0
            goto L1a
        L19:
            r3 = 1
        L1a:
            r4 = -1
            if (r3 != 0) goto L58
            int r0 = r0.width
            if (r0 != r4) goto L58
            java.lang.Object r0 = r5.getObject()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L59
            java.lang.Object r0 = r5.getObject()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.ViewParent r0 = r0.getParent()
            android.view.View r0 = (android.view.View) r0
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            if (r0 != 0) goto L40
            goto L59
        L40:
            java.lang.Object r0 = r5.getObject()
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.ViewParent r0 = r0.getParent()
            android.view.View r0 = (android.view.View) r0
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            int r0 = r0.width
            r5.setWidth(r0)
            r1 = r3
            r0 = 1
            goto L5a
        L58:
            r1 = r3
        L59:
            r0 = 0
        L5a:
            if (r1 == 0) goto L61
            java.lang.String r1 = "Panel size is unknown. Layout may not be loaded correctly."
            anywheresoftware.b4a.BA.LogInfo(r1)
        L61:
            java.lang.Object r1 = r5.getObject()
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r3 = 0
            anywheresoftware.b4a.keywords.LayoutBuilder$LayoutValuesAndMap r6 = anywheresoftware.b4a.keywords.LayoutBuilder.loadLayout(r6, r7, r2, r1, r3)
            anywheresoftware.b4a.keywords.LayoutValues r6 = r6.layoutValues
            if (r0 == 0) goto L73
            r5.setWidth(r4)
        L73:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: anywheresoftware.b4a.objects.PanelWrapper.LoadLayout(java.lang.String, anywheresoftware.b4a.BA):anywheresoftware.b4a.keywords.LayoutValues");
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public Object Get(int i) {
        return GetView(i).getObject();
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public int getSize() {
        return getNumberOfViews();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BA.IterableList GetAllViewsRecursive() {
        return new ActivityWrapper.AllViewsIterator((ViewGroup) getObject());
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        View view = (View) obj;
        if (view == null) {
            view = (View) ViewWrapper.buildNativeView((Context) obj2, BALayout.class, map, z);
        }
        View viewBuild = ViewWrapper.build(view, map, z);
        Drawable drawable = (Drawable) DynamicBuilder.build(viewBuild, (HashMap) map.get("drawable"), z, null);
        if (drawable != null) {
            viewBuild.setBackgroundDrawable(drawable);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View.class.getDeclaredMethod("setElevation", Float.TYPE).invoke(viewBuild, Float.valueOf(BALayout.getDeviceScale() * ((Float) BA.gm(map, "elevation", Float.valueOf(0.0f))).floatValue()));
        }
        return viewBuild;
    }
}
