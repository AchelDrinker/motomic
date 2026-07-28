package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Button")
public class ButtonWrapper extends TextViewWrapper<Button> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new Button(ba.context));
            removeCaps((Button) getObject());
        }
        super.innerInitialize(ba, str, true);
        if (!ba.subExists(String.valueOf(str) + "_down")) {
            if (!ba.subExists(String.valueOf(str) + "_up")) {
                return;
            }
        }
        ((Button) getObject()).setOnTouchListener(new View.OnTouchListener() { // from class: anywheresoftware.b4a.objects.ButtonWrapper.1
            private boolean down = false;

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int[] drawableState;
                if (motionEvent.getAction() == 0) {
                    this.down = true;
                    ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(str) + "_down", new Object[0]);
                } else if (this.down && (motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
                    this.down = false;
                    ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(str) + "_up", new Object[0]);
                } else {
                    if (motionEvent.getAction() != 2 || (drawableState = view.getDrawableState()) == null) {
                        return false;
                    }
                    for (int i : drawableState) {
                        if (i == 16842919) {
                            if (this.down) {
                                return false;
                            }
                            ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(str) + "_down", new Object[0]);
                            this.down = true;
                            return false;
                        }
                    }
                    if (this.down) {
                        ba.raiseEventFromUI(ButtonWrapper.this.getObject(), String.valueOf(str) + "_up", new Object[0]);
                        this.down = false;
                    }
                }
                return false;
            }
        });
    }

    private static void removeCaps(Button button) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                TextView.class.getDeclaredMethod("setAllCaps", Boolean.TYPE).invoke(button, false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, Button.class, map, z);
            removeCaps((Button) obj);
        }
        TextView textView = (TextView) TextViewWrapper.build(obj, map, z);
        Drawable drawable = (Drawable) DynamicBuilder.build(obj, (HashMap) map.get("drawable"), z, null);
        if (drawable != null) {
            textView.setBackgroundDrawable(drawable);
        }
        if (z) {
            textView.setPressed(((Boolean) map.get("pressed")).booleanValue());
        }
        return textView;
    }
}
