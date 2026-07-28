package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class CompoundButtonWrapper<T extends CompoundButton> extends TextViewWrapper<T> {
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        innerInitialize(ba, str, z, true);
    }

    protected void innerInitialize(final BA ba, final String str, boolean z, boolean z2) {
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_checkedchange")) {
            getObject().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: anywheresoftware.b4a.objects.CompoundButtonWrapper.1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    ba.raiseEvent2(CompoundButtonWrapper.this.getObject(), false, String.valueOf(str) + "_checkedchange", false, Boolean.valueOf(z3));
                }
            });
        }
    }

    public boolean getChecked() {
        return getObject().isChecked();
    }

    public void setChecked(boolean z) {
        getObject().setChecked(z);
    }

    @Override // anywheresoftware.b4a.objects.TextViewWrapper, anywheresoftware.b4a.objects.ViewWrapper, anywheresoftware.b4a.AbsObjectWrapper
    public String toString() {
        String string = super.toString();
        if (!IsInitialized()) {
            return string;
        }
        return String.valueOf(string) + ", Checked=" + getChecked();
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z) throws Exception {
        Drawable drawable;
        CompoundButton compoundButton = (CompoundButton) TextViewWrapper.build(obj, map, z);
        compoundButton.setChecked(((Boolean) map.get("isChecked")).booleanValue());
        HashMap map2 = (HashMap) map.get("drawable");
        if (map2 != null && (drawable = (Drawable) DynamicBuilder.build(obj, map2, z, null)) != null) {
            compoundButton.setBackgroundDrawable(drawable);
        }
        return compoundButton;
    }

    @BA.ShortName("CheckBox")
    public static class CheckBoxWrapper extends CompoundButtonWrapper<CheckBox> {
        @Override // anywheresoftware.b4a.objects.CompoundButtonWrapper, anywheresoftware.b4a.objects.ViewWrapper
        public void innerInitialize(BA ba, String str, boolean z) {
            if (!z) {
                setObject(new CheckBox(ba.context));
            }
            super.innerInitialize(ba, str, true);
        }

        public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
            if (obj == null) {
                obj = ViewWrapper.buildNativeView((Context) obj2, CheckBox.class, map, z);
            }
            return (CheckBox) CompoundButtonWrapper.build(obj, map, z);
        }
    }

    @BA.ShortName("RadioButton")
    public static class RadioButtonWrapper extends CompoundButtonWrapper<RadioButton> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // anywheresoftware.b4a.objects.CompoundButtonWrapper, anywheresoftware.b4a.objects.ViewWrapper
        public void innerInitialize(BA ba, String str, boolean z) {
            if (!z) {
                setObject(new RadioButton(ba.context));
            }
            super.innerInitialize(ba, str, true, false);
            ((RadioButton) getObject()).setOnCheckedChangeListener(new RadioButtonListener(str, ba, (RadioButton) getObject()));
        }

        private static class RadioButtonListener implements CompoundButton.OnCheckedChangeListener {
            private BA ba;
            private RadioButton current;
            private String eventName;

            public RadioButtonListener(String str, BA ba, RadioButton radioButton) {
                this.eventName = str;
                this.ba = ba;
                this.current = radioButton;
            }

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    ViewParent parent = this.current.getParent();
                    if (parent instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) parent;
                        for (int i = 0; i < viewGroup.getChildCount(); i++) {
                            View childAt = viewGroup.getChildAt(i);
                            if ((childAt instanceof RadioButton) && childAt != this.current) {
                                RadioButton radioButton = (RadioButton) childAt;
                                if (radioButton.isChecked()) {
                                    radioButton.setChecked(false);
                                }
                            }
                        }
                    }
                    if (this.eventName.length() > 0) {
                        this.ba.raiseEvent2(this.current, false, String.valueOf(this.eventName) + "_checkedchange", false, Boolean.valueOf(z));
                    }
                }
            }
        }

        public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
            if (obj == null) {
                obj = ViewWrapper.buildNativeView((Context) obj2, RadioButton.class, map, z);
            }
            return (RadioButton) CompoundButtonWrapper.build(obj, map, z);
        }
    }

    @BA.ShortName("ToggleButton")
    public static class ToggleButtonWrapper extends CompoundButtonWrapper<ToggleButton> {
        @Override // anywheresoftware.b4a.objects.TextViewWrapper
        public void setText(CharSequence charSequence) {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // anywheresoftware.b4a.objects.CompoundButtonWrapper, anywheresoftware.b4a.objects.ViewWrapper
        public void innerInitialize(BA ba, String str, boolean z) {
            if (!z) {
                setObject(new ToggleButton(ba.context));
                ((ToggleButton) getObject()).setText("");
            }
            super.innerInitialize(ba, str, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public String getTextOn() {
            return String.valueOf(((ToggleButton) getObject()).getTextOn());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setTextOn(CharSequence charSequence) {
            ((ToggleButton) getObject()).setTextOn(charSequence);
            setChecked(getChecked());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public String getTextOff() {
            return String.valueOf(((ToggleButton) getObject()).getTextOff());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setTextOff(CharSequence charSequence) {
            ((ToggleButton) getObject()).setTextOff(charSequence);
            setChecked(getChecked());
        }

        @Override // anywheresoftware.b4a.objects.TextViewWrapper
        public String getText() {
            return "";
        }

        public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
            if (obj == null) {
                obj = ViewWrapper.buildNativeView((Context) obj2, ToggleButton.class, map, z);
            }
            ToggleButton toggleButton = (ToggleButton) obj;
            toggleButton.setTextOn((String) map.get("textOn"));
            toggleButton.setTextOff((String) map.get("textOff"));
            ToggleButton toggleButton2 = (ToggleButton) CompoundButtonWrapper.build(obj, map, z);
            toggleButton2.setTextColor(((Integer) map.get("textColor")).intValue());
            return toggleButton2;
        }
    }
}
