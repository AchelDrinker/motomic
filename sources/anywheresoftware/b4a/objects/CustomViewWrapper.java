package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.collections.Map;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CustomViewWrapper extends ViewWrapper<BALayout> implements LayoutBuilder.DesignerTextSizeMethod {
    public Object customObject;
    private String eventName;
    public HashMap<String, Object> props;

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        this.ba = ba;
        this.eventName = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AfterDesignerScript() throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = this.customObject.getClass();
        boolean z = this.customObject instanceof B4AClass;
        Map map = new Map();
        map.Initialize();
        map.Put("defaultcolor", Integer.valueOf(ViewWrapper.defaultColor));
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.setObject((ViewGroup) getObject());
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.setObject((TextView) getTag());
        labelWrapper.setTextSize(((Float) this.props.get("fontsize")).floatValue());
        panelWrapper.setTag(this.props.get("tag"));
        map.Put("activity", this.ba.vg);
        map.Put("ba", this.ba);
        String lowerCase = this.eventName.toLowerCase(BA.cul);
        this.eventName = lowerCase;
        map.Put("eventName", lowerCase);
        if (this.props.containsKey("customProperties")) {
            for (Map.Entry entry : ((HashMap) this.props.get("customProperties")).entrySet()) {
                map.Put(entry.getKey(), entry.getValue());
            }
        }
        Object obj = this.ba.eventsTarget != null ? this.ba.eventsTarget : this.ba.activity.getClass();
        if (BA.isShellModeRuntimeCheck(this.ba) && z) {
            this.ba.raiseEvent2(null, true, "CREATE_CUSTOM_VIEW", true, this.customObject, this.ba, obj, this.eventName, panelWrapper, labelWrapper, map);
            return;
        }
        cls.getMethod("_initialize", BA.class, Object.class, String.class).invoke(this.customObject, this.ba, obj, this.eventName);
        if (z) {
            ((B4AClass) this.customObject).getBA().raiseEvent2(null, true, "designercreateview", true, panelWrapper, labelWrapper, map);
        } else {
            ((Common.DesignerCustomView) this.customObject).DesignerCreateView(panelWrapper, labelWrapper, map);
        }
    }

    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public float getTextSize() {
        return ((Float) this.props.get("fontsize")).floatValue();
    }

    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public void setTextSize(float f) {
        this.props.put("fontsize", Float.valueOf(f));
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, BALayout.class, map, z);
        }
        ViewGroup viewGroup = (ViewGroup) ViewWrapper.build(obj, map, z);
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("drawable"), z, null);
        if (drawable != null) {
            viewGroup.setBackgroundDrawable(drawable);
        }
        TextView textView = (TextView) TextViewWrapper.build(ViewWrapper.buildNativeView((Context) obj2, TextView.class, map, z), map, z);
        if (!z) {
            viewGroup.setTag(textView);
        }
        if (z) {
            viewGroup.removeAllViews();
            viewGroup.addView(textView, new BALayout.LayoutParams(0, 0, -1, -1));
        }
        return viewGroup;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public static void replaceBaseWithView(PanelWrapper panelWrapper, View view) {
        ViewGroup viewGroup = (ViewGroup) panelWrapper.getObject();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
        viewGroup2.addView(view, viewGroup2.indexOfChild(viewGroup), viewGroup.getLayoutParams());
        panelWrapper.RemoveView();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void replaceBaseWithView2(PanelWrapper panelWrapper, View view) {
        ViewGroup viewGroup = (ViewGroup) panelWrapper.getObject();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
        viewGroup2.addView(view, viewGroup2.indexOfChild(viewGroup), viewGroup.getLayoutParams());
        view.setTag(viewGroup.getTag());
        panelWrapper.RemoveView();
    }
}
