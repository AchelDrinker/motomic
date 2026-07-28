package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.collections.List;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("AutoCompleteEditText")
public class AutoCompleteEditTextWrapper extends EditTextWrapper {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.EditTextWrapper, anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        this.ba = ba;
        if (!z) {
            setObject(new AutoCompleteTextView(ba.context));
            ((EditText) getObject()).setSingleLine(true);
            ((EditText) getObject()).setImeOptions(6);
        }
        super.innerInitialize(ba, str, true);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) getObject();
        if ((autoCompleteTextView.getInputType() & 15) == 1) {
            autoCompleteTextView.setInputType(autoCompleteTextView.getInputType() | 524288);
        }
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: anywheresoftware.b4a.objects.AutoCompleteEditTextWrapper.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ((InputMethodManager) BA.applicationContext.getSystemService("input_method")).hideSoftInputFromWindow(((EditText) AutoCompleteEditTextWrapper.this.getObject()).getWindowToken(), 0);
                ba.raiseEventFromUI(AutoCompleteEditTextWrapper.this.getObject(), String.valueOf(str) + "_itemclick", String.valueOf(autoCompleteTextView.getAdapter().getItem(i)));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void SetItems(BA ba, List list) {
        ((AutoCompleteTextView) getObject()).setAdapter(new MyArrayAdapter(ba.context, list.getObject(), getTextSize(), getTypeface(), getGravity(), getTextColor()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void SetItems2(BA ba, List list, Typeface typeface, int i, float f, int i2) {
        ((AutoCompleteTextView) getObject()).setAdapter(new MyArrayAdapter(ba.context, list.getObject(), f, typeface, i, i2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void ShowDropDown() {
        ((AutoCompleteTextView) getObject()).showDropDown();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void DismissDropDown() {
        ((AutoCompleteTextView) getObject()).dismissDropDown();
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        AutoCompleteTextView autoCompleteTextView;
        if (obj == null) {
            autoCompleteTextView = (AutoCompleteTextView) ViewWrapper.buildNativeView((Context) obj2, AutoCompleteTextView.class, map, z);
        } else {
            autoCompleteTextView = (AutoCompleteTextView) obj;
        }
        return EditTextWrapper.build(autoCompleteTextView, map, z, obj2);
    }

    public static class MyArrayAdapter extends ArrayAdapter<String> {
        int gravity;
        int textColor;
        float textSize;
        private Typeface typeface;

        public MyArrayAdapter(Context context, java.util.List list, float f, Typeface typeface, int i, int i2) {
            super(context, 0, list);
            this.typeface = typeface;
            this.textColor = i2;
            this.textSize = f;
            this.gravity = i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                textView = new TextView(getContext());
                textView.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
                int iDipToCurrent = Common.DipToCurrent(10);
                textView.setPadding(iDipToCurrent, iDipToCurrent, iDipToCurrent, iDipToCurrent);
                textView.setTextColor(this.textColor);
                textView.setTextSize(this.textSize);
                textView.setTypeface(this.typeface);
                textView.setGravity(this.gravity);
            } else {
                textView = (TextView) view;
            }
            textView.setText(getItem(i));
            return textView;
        }
    }
}
