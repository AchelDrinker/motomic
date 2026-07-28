package anywheresoftware.b4a.objects;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.collections.List;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Spinner")
public class SpinnerWrapper extends ViewWrapper<B4ASpinner> implements LayoutBuilder.DesignerTextSizeMethod {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            setObject(new B4ASpinner(ba.context));
        }
        super.innerInitialize(ba, str, true);
        ((B4ASpinner) getObject()).ba = ba;
        ((B4ASpinner) getObject()).eventName = str;
        ((B4ASpinner) getObject()).disallowItemClick = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getSize() {
        return ((B4ASpinner) getObject()).adapter.getCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getSelectedIndex() {
        return ((B4ASpinner) getObject()).selectedItem;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setSelectedIndex(int i) {
        ((B4ASpinner) getObject()).disallowItemClick = true;
        try {
            ((B4ASpinner) getObject()).setSelection(i);
            ((B4ASpinner) getObject()).selectedItem = i;
        } finally {
            ((B4ASpinner) getObject()).disallowItemClick = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getSelectedItem() {
        Object itemAtPosition = ((B4ASpinner) getObject()).getItemAtPosition(((B4ASpinner) getObject()).selectedItem);
        if (itemAtPosition == null) {
            itemAtPosition = "";
        }
        return String.valueOf(itemAtPosition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int IndexOf(String str) {
        return ((B4ASpinner) getObject()).adapter.items.indexOf(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getPrompt() {
        return (String) ((B4ASpinner) getObject()).getPrompt();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setPrompt(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            charSequence = null;
        }
        ((B4ASpinner) getObject()).setPrompt(charSequence);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Add(String str) {
        ((B4ASpinner) getObject()).disallowItemClick = true;
        try {
            ((B4ASpinner) getObject()).adapter.items.add(str);
            ((B4ASpinner) getObject()).adapter.notifyDataSetChanged();
            if (((B4ASpinner) getObject()).selectedItem == -1) {
                ((B4ASpinner) getObject()).selectedItem = 0;
            }
        } finally {
            ((B4ASpinner) getObject()).disallowItemClick = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AddAll(List list) {
        ((B4ASpinner) getObject()).disallowItemClick = true;
        try {
            ((B4ASpinner) getObject()).adapter.items.addAll(list.getObject());
            ((B4ASpinner) getObject()).adapter.notifyDataSetChanged();
            if (((B4ASpinner) getObject()).selectedItem == -1) {
                ((B4ASpinner) getObject()).selectedItem = 0;
            }
        } finally {
            ((B4ASpinner) getObject()).disallowItemClick = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String GetItem(int i) {
        return String.valueOf(((B4ASpinner) getObject()).adapter.getItem(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveAt(int i) {
        ((B4ASpinner) getObject()).disallowItemClick = true;
        try {
            ((B4ASpinner) getObject()).adapter.items.remove(i);
            ((B4ASpinner) getObject()).adapter.notifyDataSetChanged();
            if (((B4ASpinner) getObject()).selectedItem == ((B4ASpinner) getObject()).adapter.getCount()) {
                ((B4ASpinner) getObject()).selectedItem--;
            }
        } finally {
            ((B4ASpinner) getObject()).disallowItemClick = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Clear() {
        ((B4ASpinner) getObject()).disallowItemClick = true;
        try {
            ((B4ASpinner) getObject()).adapter.items.clear();
            ((B4ASpinner) getObject()).adapter.notifyDataSetChanged();
            ((B4ASpinner) getObject()).selectedItem = -1;
        } finally {
            ((B4ASpinner) getObject()).disallowItemClick = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTextColor(int i) {
        ((B4ASpinner) getObject()).adapter.textColor = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getTextColor() {
        return ((B4ASpinner) getObject()).adapter.textColor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDropdownTextColor(int i) {
        ((B4ASpinner) getObject()).adapter.dropdownTextColor = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getDropdownTextColor() {
        return ((B4ASpinner) getObject()).adapter.dropdownTextColor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDropdownBackgroundColor(int i) {
        ((B4ASpinner) getObject()).adapter.ddbackgroundColor = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getDropdownBackgroundColor() {
        return ((B4ASpinner) getObject()).adapter.ddbackgroundColor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public void setTextSize(float f) {
        ((B4ASpinner) getObject()).adapter.textSize = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public float getTextSize() {
        return ((B4ASpinner) getObject()).adapter.textSize;
    }

    public static class B4ASpinner extends Spinner {
        public B4ASpinnerAdapter adapter;
        public BA ba;
        public boolean disallowItemClick;
        public String eventName;
        int selectedItem;

        public B4ASpinner(Context context) {
            super(context);
            this.selectedItem = -1;
            this.disallowItemClick = true;
            B4ASpinnerAdapter b4ASpinnerAdapter = new B4ASpinnerAdapter(context);
            this.adapter = b4ASpinnerAdapter;
            setAdapter((SpinnerAdapter) b4ASpinnerAdapter);
        }

        @Override // android.widget.AbsSpinner, android.widget.AdapterView
        public void setSelection(int i) {
            super.setSelection(i);
            this.selectedItem = i;
            BA ba = this.ba;
            if (ba == null || this.disallowItemClick) {
                return;
            }
            ba.raiseEventFromUI(this, String.valueOf(this.eventName) + "_itemclick", Integer.valueOf(this.selectedItem), this.adapter.getItem(this.selectedItem));
        }
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, B4ASpinner.class, map, z);
        }
        B4ASpinner b4ASpinner = (B4ASpinner) ViewWrapper.build(obj, map, z);
        Float f = (Float) map.get("fontsize");
        if (f != null) {
            b4ASpinner.adapter.textSize = f.floatValue();
            b4ASpinner.adapter.textColor = ((Integer) map.get("textColor")).intValue();
            if (Color.alpha(b4ASpinner.adapter.textColor) == 0 || b4ASpinner.adapter.textColor == -984833) {
                b4ASpinner.adapter.textColor = 0;
            }
        }
        if (z) {
            b4ASpinner.adapter.items.clear();
            b4ASpinner.adapter.items.add(map.get("name"));
            b4ASpinner.adapter.notifyDataSetChanged();
        }
        String str = (String) map.get("prompt");
        if (str != null && str.length() > 0) {
            b4ASpinner.setPrompt(str);
        }
        return b4ASpinner;
    }

    public static class B4ASpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
        private LayoutInflater inflater;
        ArrayList<Object> items = new ArrayList<>();
        public float textSize = 16.0f;
        public int textColor = 0;
        public int dropdownTextColor = 0;
        public int ddbackgroundColor = 0;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public B4ASpinnerAdapter(Context context) {
            this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.items.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.items.get(i);
        }

        @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.inflater.inflate(R.layout.simple_spinner_dropdown_item, viewGroup, false);
                TextView textView = (TextView) view;
                textView.setTextSize(this.textSize);
                int i2 = this.textColor;
                if (i2 != 0 && this.dropdownTextColor == 0) {
                    textView.setTextColor(i2);
                } else {
                    int i3 = this.dropdownTextColor;
                    if (i3 != 0) {
                        textView.setTextColor(i3);
                    }
                }
                int i4 = this.ddbackgroundColor;
                if (i4 != 0) {
                    view.setBackgroundColor(i4);
                }
            }
            TextView textView2 = (TextView) view;
            Object obj = this.items.get(i);
            if (obj instanceof CharSequence) {
                textView2.setText((CharSequence) obj);
                return view;
            }
            textView2.setText(String.valueOf(obj));
            return view;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.inflater.inflate(R.layout.simple_spinner_item, viewGroup, false);
                TextView textView = (TextView) view;
                textView.setTextSize(this.textSize);
                int i2 = this.textColor;
                if (i2 != 0) {
                    textView.setTextColor(i2);
                }
            }
            TextView textView2 = (TextView) view;
            Object obj = this.items.get(i);
            if (obj instanceof CharSequence) {
                textView2.setText((CharSequence) obj);
                return view;
            }
            textView2.setText(String.valueOf(obj));
            return view;
        }
    }
}
