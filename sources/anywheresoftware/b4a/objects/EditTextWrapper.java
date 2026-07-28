package anywheresoftware.b4a.objects;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("EditText")
public class EditTextWrapper extends TextViewWrapper<EditText> {
    public static final int INPUT_TYPE_DECIMAL_NUMBERS = 12290;
    public static final int INPUT_TYPE_NONE = 0;
    public static final int INPUT_TYPE_NUMBERS = 2;
    public static final int INPUT_TYPE_PHONE = 3;
    public static final int INPUT_TYPE_TEXT = 1;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        this.ba = ba;
        if (!z) {
            setObject(new EditText(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_textchanged")) {
            ((EditText) getObject()).addTextChangedListener(new TextWatcher() { // from class: anywheresoftware.b4a.objects.EditTextWrapper.1
                private CharSequence old;

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    ba.raiseEvent2(EditTextWrapper.this.getObject(), false, String.valueOf(str) + "_textchanged", true, this.old, ((EditText) EditTextWrapper.this.getObject()).getText().toString());
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    this.old = charSequence.toString();
                }
            });
        }
        if (ba.subExists(String.valueOf(str) + "_enterpressed")) {
            ((EditText) getObject()).setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: anywheresoftware.b4a.objects.EditTextWrapper.2
                @Override // android.widget.TextView.OnEditorActionListener
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    ba.raiseEvent(EditTextWrapper.this.getObject(), String.valueOf(str) + "_enterpressed", new Object[0]);
                    return false;
                }
            });
        }
        if (ba.subExists(String.valueOf(str) + "_focuschanged")) {
            ((EditText) getObject()).setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: anywheresoftware.b4a.objects.EditTextWrapper.3
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view, boolean z2) {
                    ba.raiseEventFromUI(EditTextWrapper.this.getObject(), String.valueOf(str) + "_focuschanged", Boolean.valueOf(z2));
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setForceDoneButton(boolean z) {
        if (z) {
            ((EditText) getObject()).setImeOptions(6);
        } else {
            ((EditText) getObject()).setImeOptions(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.TextViewWrapper
    public void setSingleLine(boolean z) {
        ((EditText) getObject()).setSingleLine(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setPasswordMode(boolean z) {
        if (z) {
            ((EditText) getObject()).setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            ((EditText) getObject()).setTransformationMethod(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getSelectionStart() {
        return Selection.getSelectionStart(((EditText) getObject()).getText());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSelectionStart(int i) {
        ((EditText) getObject()).setSelection(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getSelectionLength() {
        return Math.max(0, Selection.getSelectionEnd(((EditText) getObject()).getText()) - getSelectionStart());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void SetSelection(int i, int i2) {
        Selection.setSelection(((EditText) getObject()).getText(), i, i2 + i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void SelectAll() {
        Selection.selectAll(((EditText) getObject()).getText());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setInputType(int i) {
        ((EditText) getObject()).setInputType(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getInputType() {
        return ((EditText) getObject()).getInputType();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setWrap(boolean z) {
        ((EditText) getObject()).setHorizontallyScrolling(!z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setHint(String str) {
        ((EditText) getObject()).setHint(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getHint() {
        CharSequence hint = ((EditText) getObject()).getHint();
        return hint == null ? "" : String.valueOf(hint);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setHintColor(int i) {
        ((EditText) getObject()).setHintTextColor(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getHintColor() {
        return ((EditText) getObject()).getCurrentHintTextColor();
    }

    @Override // anywheresoftware.b4a.objects.TextViewWrapper
    public void setText(CharSequence charSequence) {
        super.setText(charSequence);
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        EditText editText;
        if (obj == null) {
            editText = (EditText) ViewWrapper.buildNativeView((Context) obj2, EditText.class, map, z);
        } else {
            editText = (EditText) obj;
        }
        TextViewWrapper.build(editText, map, z);
        ColorStateList colorStateList = z ? (ColorStateList) ViewWrapper.getDefault(editText, "hintColor", editText.getHintTextColors()) : null;
        int iIntValue = ((Integer) BA.gm(map, "hintColor", Integer.valueOf(ViewWrapper.defaultColor))).intValue();
        if (iIntValue != -984833) {
            editText.setHintTextColor(iIntValue);
        } else if (z) {
            editText.setHintTextColor(colorStateList);
        }
        String str = (String) BA.gm(map, "hint", "");
        if (z && str.length() == 0) {
            str = (String) map.get("name");
        }
        editText.setHint(str);
        String str2 = (String) map.get("inputType");
        if (str2 != null) {
            editText.setInputType(((Integer) EditTextWrapper.class.getField("INPUT_TYPE_" + str2).get(null)).intValue());
        }
        boolean zBooleanValue = ((Boolean) map.get("singleLine")).booleanValue();
        editText.setSingleLine(zBooleanValue);
        if (z && zBooleanValue) {
            editText.setInputType(524288);
        }
        if (((Boolean) map.get("password")).booleanValue()) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(null);
        }
        editText.setHorizontallyScrolling(!((Boolean) BA.gm(map, "wrap", true)).booleanValue());
        if (((Boolean) BA.gm(map, "forceDone", false)).booleanValue()) {
            editText.setImeOptions(6);
            return editText;
        }
        editText.setImeOptions(0);
        return editText;
    }
}
