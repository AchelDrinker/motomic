package anywheresoftware.b4a.objects.drawable;

import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("StateListDrawable")
public class StateListDrawable extends AbsObjectWrapper<android.graphics.drawable.StateListDrawable> {
    public static final int State_Checked = 16842912;
    public static final int State_Disabled = -16842910;
    public static final int State_Enabled = 16842910;
    public static final int State_Focused = 16842908;
    public static final int State_Pressed = 16842919;
    public static final int State_Selected = 16842913;
    public static final int State_Unchecked = -16842912;

    public void Initialize() {
        setObject(new android.graphics.drawable.StateListDrawable());
    }

    public void AddState(int i, Drawable drawable) {
        getObject().addState(new int[]{i}, drawable);
    }

    public void AddState2(int[] iArr, Drawable drawable) {
        getObject().addState(iArr, drawable);
    }

    public void AddCatchAllState(Drawable drawable) {
        getObject().addState(new int[0], drawable);
    }

    public static Drawable build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) {
        android.graphics.drawable.StateListDrawable stateListDrawable = new android.graphics.drawable.StateListDrawable();
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("disabledDrawable"), z, obj2);
        Drawable drawable2 = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("enabledDrawable"), z, obj2);
        Drawable drawable3 = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("pressedDrawable"), z, obj2);
        stateListDrawable.addState(new int[]{State_Disabled}, drawable);
        stateListDrawable.addState(new int[]{16842919}, drawable3);
        stateListDrawable.addState(new int[0], drawable2);
        return stateListDrawable;
    }
}
