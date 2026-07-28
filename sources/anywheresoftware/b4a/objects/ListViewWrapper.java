package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.objects.SimpleListAdapter;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("ListView")
public class ListViewWrapper extends ViewWrapper<SimpleListView> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new SimpleListView(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_itemclick")) {
            ((SimpleListView) getObject()).setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: anywheresoftware.b4a.objects.ListViewWrapper.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    ba.raiseEventFromUI(ListViewWrapper.this.getObject(), String.valueOf(str) + "_itemclick", Integer.valueOf(i), ((SimpleListView) ListViewWrapper.this.getObject()).adapter.getItem(i));
                }
            });
        }
        if (ba.subExists(String.valueOf(str) + "_itemlongclick")) {
            ((SimpleListView) getObject()).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: anywheresoftware.b4a.objects.ListViewWrapper.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.widget.AdapterView.OnItemLongClickListener
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                    ba.raiseEventFromUI(ListViewWrapper.this.getObject(), String.valueOf(str) + "_itemlongclick", Integer.valueOf(i), ((SimpleListView) ListViewWrapper.this.getObject()).adapter.getItem(i));
                    return true;
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getSize() {
        return ((SimpleListView) getObject()).adapter.getCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleListAdapter.SingleLineLayout getSingleLineLayout() {
        return ((SimpleListView) getObject()).adapter.SingleLine;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleListAdapter.TwoLinesLayout getTwoLinesLayout() {
        return ((SimpleListView) getObject()).adapter.TwoLines;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleListAdapter.TwoLinesAndBitmapLayout getTwoLinesAndBitmap() {
        return ((SimpleListView) getObject()).adapter.TwoLinesAndBitmap;
    }

    public void AddSingleLine(CharSequence charSequence) {
        AddSingleLine2(charSequence, null);
    }

    public void AddSingleLine2(CharSequence charSequence, Object obj) {
        SimpleListAdapter.SingleLineData singleLineData = new SimpleListAdapter.SingleLineData();
        singleLineData.Text = charSequence;
        singleLineData.ReturnValue = obj;
        add(singleLineData);
    }

    public void AddTwoLines(CharSequence charSequence, CharSequence charSequence2) {
        AddTwoLines2(charSequence, charSequence2, null);
    }

    public void AddTwoLines2(CharSequence charSequence, CharSequence charSequence2, Object obj) {
        SimpleListAdapter.TwoLinesData twoLinesData = new SimpleListAdapter.TwoLinesData();
        twoLinesData.Text = charSequence;
        twoLinesData.ReturnValue = obj;
        twoLinesData.SecondLineText = charSequence2;
        add(twoLinesData);
    }

    public void AddTwoLinesAndBitmap(CharSequence charSequence, CharSequence charSequence2, Bitmap bitmap) {
        AddTwoLinesAndBitmap2(charSequence, charSequence2, bitmap, null);
    }

    public void AddTwoLinesAndBitmap2(CharSequence charSequence, CharSequence charSequence2, Bitmap bitmap, Object obj) {
        SimpleListAdapter.TwoLinesAndBitmapData twoLinesAndBitmapData = new SimpleListAdapter.TwoLinesAndBitmapData();
        twoLinesAndBitmapData.Text = charSequence;
        twoLinesAndBitmapData.ReturnValue = obj;
        twoLinesAndBitmapData.SecondLineText = charSequence2;
        twoLinesAndBitmapData.Bitmap = bitmap;
        add(twoLinesAndBitmapData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void add(SimpleListAdapter.SimpleItem simpleItem) {
        ((SimpleListView) getObject()).adapter.items.add(simpleItem);
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object GetItem(int i) {
        return ((SimpleListView) getObject()).adapter.getItem(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void RemoveAt(int i) {
        ((SimpleListView) getObject()).adapter.items.remove(i);
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Clear() {
        ((SimpleListView) getObject()).adapter.items.clear();
        ((SimpleListView) getObject()).adapter.notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFastScrollEnabled(boolean z) {
        ((SimpleListView) getObject()).setFastScrollEnabled(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getFastScrollEnabled() {
        return ((SimpleListView) getObject()).isFastScrollEnabled();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setScrollingBackgroundColor(int i) {
        ((SimpleListView) getObject()).setCacheColorHint(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void SetSelection(int i) {
        ((SimpleListView) getObject()).setSelection(i);
    }

    public static class SimpleListView extends ListView {
        public SimpleListAdapter adapter;

        public SimpleListView(Context context) {
            super(context);
            SimpleListAdapter simpleListAdapter = new SimpleListAdapter(context);
            this.adapter = simpleListAdapter;
            setAdapter((ListAdapter) simpleListAdapter);
        }
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, SimpleListView.class, map, z);
        }
        ListView listView = (ListView) ViewWrapper.build(obj, map, z);
        Drawable drawable = (Drawable) DynamicBuilder.build(listView, (HashMap) map.get("drawable"), z, null);
        if (drawable != null) {
            listView.setBackgroundDrawable(drawable);
        }
        listView.setFastScrollEnabled(((Boolean) map.get("fastScrollEnabled")).booleanValue());
        if (z) {
            SimpleListView simpleListView = (SimpleListView) listView;
            if (simpleListView.adapter.items.size() == 0) {
                for (int i = 1; i <= 10; i++) {
                    SimpleListAdapter.SingleLineData singleLineData = new SimpleListAdapter.SingleLineData();
                    singleLineData.Text = "Item #" + i;
                    simpleListView.adapter.items.add(singleLineData);
                }
                simpleListView.adapter.notifyDataSetChanged();
                return listView;
            }
        }
        return listView;
    }
}
