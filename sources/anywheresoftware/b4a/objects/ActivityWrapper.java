package anywheresoftware.b4a.objects;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.B4AMenuItem;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.keywords.LayoutValues;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Activity")
public class ActivityWrapper extends ViewWrapper<BALayout> implements BA.IterableList {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 2;
    public static final int ACTION_UP = 1;

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void BringToFront() {
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void RemoveView() {
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void SendToBack() {
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public boolean getEnabled() {
        return true;
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public int getLeft() {
        return 0;
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public int getTop() {
        return 0;
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public boolean getVisible() {
        return true;
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void setEnabled(boolean z) {
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void setVisible(boolean z) {
    }

    public ActivityWrapper() {
    }

    public ActivityWrapper(BA ba, String str) {
        if (BA.shellMode) {
            return;
        }
        reinitializeForShell(ba, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void reinitializeForShell(final BA ba, String str) {
        if (IsInitialized()) {
            return;
        }
        setObject(ba.vg);
        innerInitialize(ba, str, true);
        if (ba.subExists("activity_touch")) {
            ((BALayout) getObject()).setOnTouchListener(new View.OnTouchListener() { // from class: anywheresoftware.b4a.objects.ActivityWrapper.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ba.raiseEventFromUI(ActivityWrapper.this, "activity_touch", Integer.valueOf(motionEvent.getAction()), Float.valueOf(motionEvent.getX()), Float.valueOf(motionEvent.getY()));
                    return true;
                }
            });
        }
    }

    public IntentWrapper GetStartingIntent() {
        IntentWrapper intentWrapper = new IntentWrapper();
        intentWrapper.setObject(getActivity().getIntent());
        return intentWrapper;
    }

    public void SetActivityResult(int i, IntentWrapper intentWrapper) {
        getActivity().setResult(i, intentWrapper.getObject());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AddView(View view, int i, int i2, int i3, int i4) {
        ((BALayout) getObject()).addView(view, new BALayout.LayoutParams(i, i2, i3, i4));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConcreteViewWrapper GetView(int i) {
        ConcreteViewWrapper concreteViewWrapper = new ConcreteViewWrapper();
        concreteViewWrapper.setObject(((BALayout) getObject()).getChildAt(i));
        return concreteViewWrapper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveAllViews() {
        ((BALayout) getObject()).removeAllViews();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveViewAt(int i) {
        ((BALayout) getObject()).removeViewAt(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getNumberOfViews() {
        return ((BALayout) getObject()).getChildCount();
    }

    public void AddMenuItem(CharSequence charSequence, String str) {
        AddMenuItem3(charSequence, str, null, false);
    }

    public void AddMenuItem2(CharSequence charSequence, String str, Bitmap bitmap) {
        AddMenuItem3(charSequence, str, bitmap, false);
    }

    public void AddMenuItem3(CharSequence charSequence, String str, Bitmap bitmap, boolean z) {
        BitmapDrawable object;
        if (bitmap != null) {
            anywheresoftware.b4a.objects.drawable.BitmapDrawable bitmapDrawable = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
            bitmapDrawable.Initialize(bitmap);
            object = bitmapDrawable.getObject();
        } else {
            object = null;
        }
        ((B4AActivity) getActivity()).addMenuItem(new B4AMenuItem(charSequence, object, str, z));
    }

    public LayoutValues LoadLayout(String str, BA ba) throws Exception {
        AbsObjectWrapper.Activity_LoadLayout_Was_Called = true;
        return LayoutBuilder.loadLayout(str, ba, true, ba.vg, null).layoutValues;
    }

    public void RerunDesignerScript(String str, BA ba, int i, int i2) throws Exception {
        BALayout bALayout = new BALayout(ba.context);
        bALayout.setLayoutParams(new ViewGroup.LayoutParams(i, i2));
        LayoutBuilder.LayoutHashMap layoutHashMap = new LayoutBuilder.LayoutHashMap();
        for (Field field : ba.activity.getClass().getFields()) {
            if (field.getName().startsWith("_") && ViewWrapper.class.isAssignableFrom(field.getType())) {
                layoutHashMap.put(field.getName().substring(1), new LayoutBuilder.ViewWrapperAndAnchor((ViewWrapper) field.get(ba.activity), null));
            }
        }
        LayoutBuilder.loadLayout(str, ba, false, bALayout, layoutHashMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Activity getActivity() {
        return (Activity) ((BALayout) getObject()).getContext();
    }

    public void OpenMenu() {
        getActivity().openOptionsMenu();
    }

    public void CloseMenu() {
        getActivity().closeOptionsMenu();
    }

    public void setTitle(CharSequence charSequence) {
        getActivity().setTitle(charSequence);
    }

    public CharSequence getTitle() {
        return getActivity().getTitle();
    }

    public int getTitleColor() {
        return getActivity().getTitleColor();
    }

    public void setTitleColor(int i) {
        getActivity().setTitleColor(i);
    }

    public void DisableAccessibility(boolean z) {
        BALayout.disableAccessibility = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public int getWidth() {
        return ((BALayout) getObject()).getWidth();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public int getHeight() {
        return ((BALayout) getObject()).getHeight();
    }

    public void Finish() {
        getActivity().finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BA.IterableList GetAllViewsRecursive() {
        return new AllViewsIterator((ViewGroup) getObject());
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public Object Get(int i) {
        return GetView(i).getObject();
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public int getSize() {
        return getNumberOfViews();
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("drawable"), z, null);
        View view = (View) obj;
        int iIntValue = z ? ((Integer) ViewWrapper.getDefault(view, "titleColor", Integer.valueOf(((Activity) view.getContext()).getTitleColor()))).intValue() : 0;
        if (drawable != null) {
            view.setBackgroundDrawable(drawable);
        }
        ((Activity) view.getContext()).setTitle((String) map.get("title"));
        int iIntValue2 = ((Integer) map.get("titleColor")).intValue();
        if (iIntValue2 != -984833) {
            ((Activity) view.getContext()).setTitleColor(iIntValue2);
        } else if (z) {
            ((Activity) view.getContext()).setTitleColor(iIntValue);
        }
        if (BA.debugMode) {
            BA.warningEngine.checkFullScreenInLayout(((Boolean) map.get("fullScreen")).booleanValue(), ((Boolean) map.get("includeTitle")).booleanValue());
        }
        if (z) {
            boolean zBooleanValue = ((Boolean) map.get("fullScreen")).booleanValue();
            boolean zBooleanValue2 = ((Boolean) map.get("includeTitle")).booleanValue();
            Class<?> cls = Class.forName("anywheresoftware.b4a.designer.Designer");
            boolean z2 = cls.getField("fullScreen").getBoolean(view.getContext());
            boolean z3 = cls.getField("includeTitle").getBoolean(view.getContext());
            if (z2 != zBooleanValue || zBooleanValue2 != z3) {
                Intent intent = new Intent(view.getContext().getApplicationContext(), cls);
                intent.putExtra("anywheresoftware.b4a.designer.includeTitle", zBooleanValue2);
                intent.putExtra("anywheresoftware.b4a.designer.fullScreen", zBooleanValue);
                cls.getMethod("restartActivity", Intent.class).invoke(view.getContext(), intent);
            }
        }
        return view;
    }

    public static class AllViewsIterator implements BA.IterableList {
        private ArrayList<View> views = new ArrayList<>();

        public AllViewsIterator(ViewGroup viewGroup) {
            addViews(viewGroup);
        }

        private void addViews(ViewGroup viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                this.views.add(childAt);
                if (childAt instanceof ViewGroup) {
                    addViews((ViewGroup) childAt);
                }
            }
        }

        @Override // anywheresoftware.b4a.BA.IterableList
        public Object Get(int i) {
            return this.views.get(i);
        }

        @Override // anywheresoftware.b4a.BA.IterableList
        public int getSize() {
            return this.views.size();
        }
    }
}
