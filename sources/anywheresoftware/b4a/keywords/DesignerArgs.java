package anywheresoftware.b4a.keywords;

import android.view.View;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.collections.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("DesignerArgs")
public class DesignerArgs {
    public static final HashMap<String, B4AClass> targetsCache = new HashMap<>();
    private List args;
    private int height;
    private Object layoutTarget;
    private View parent;
    private Map<String, Object> props;
    private LayoutValues variant;
    public Map<String, LayoutBuilder.ViewWrapperAndAnchor> views;
    private int width;

    public boolean getFirstRun() {
        return true;
    }

    public View GetViewFromArgs(int i) {
        return GetViewByName((String) this.args.Get(i));
    }

    public View GetViewByName(String str) {
        try {
            return (View) this.views.get(str.toLowerCase(BA.cul)).vw.getObject();
        } catch (NullPointerException unused) {
            throw new RuntimeException("No such view: " + str);
        }
    }

    public int getParentWidth() {
        return this.width;
    }

    public int getParentHeight() {
        return this.height;
    }

    public List getArguments() {
        return this.args;
    }

    public View getParent() {
        return this.parent;
    }

    public LayoutValues getChosenVariant() {
        return this.variant;
    }

    public anywheresoftware.b4a.objects.collections.Map getDesignerProperties() {
        anywheresoftware.b4a.objects.collections.Map map = new anywheresoftware.b4a.objects.collections.Map();
        map.setObject(this.props);
        return map;
    }

    public List getViewsNames() {
        List list = new List();
        list.Initialize();
        Iterator<String> it = this.views.keySet().iterator();
        while (it.hasNext()) {
            list.Add(it.next());
        }
        return list;
    }

    public List getViews() {
        List list = new List();
        list.Initialize();
        Iterator<LayoutBuilder.ViewWrapperAndAnchor> it = this.views.values().iterator();
        while (it.hasNext()) {
            list.Add(it.next().vw.getObject());
        }
        return list;
    }

    public Object getLayoutModule() {
        return this.layoutTarget;
    }

    public static String callsub(BA ba, View view, LayoutValues layoutValues, Map<String, Object> map, String str, String str2, int i, int i2, Map<String, LayoutBuilder.ViewWrapperAndAnchor> map2, Object[] objArr) throws Exception {
        DesignerArgs designerArgs = new DesignerArgs();
        designerArgs.views = map2;
        designerArgs.args = Common.ArrayToList(objArr);
        designerArgs.width = i;
        designerArgs.height = i2;
        designerArgs.parent = view;
        designerArgs.variant = layoutValues;
        designerArgs.props = map;
        designerArgs.layoutTarget = ba.eventsTarget == null ? ba.context.getClass() : ba.eventsTarget;
        HashMap<String, B4AClass> map3 = targetsCache;
        B4AClass b4AClass = map3.get(str);
        if (b4AClass == null) {
            Class<?> cls = Class.forName(String.valueOf(BA.packageName) + "." + str);
            B4AClass b4AClass2 = (B4AClass) cls.newInstance();
            if (BA.isShellModeRuntimeCheck(ba)) {
                ba.raiseEvent2(null, true, "CREATE_CLASS_INSTANCE", true, b4AClass2, ba);
            } else {
                cls.getMethod("_initialize", BA.class).invoke(b4AClass2, ba);
            }
            map3.put(str, b4AClass2);
            b4AClass = b4AClass2;
        }
        return String.valueOf(b4AClass.getBA().raiseEvent2(null, true, str2, true, designerArgs));
    }
}
