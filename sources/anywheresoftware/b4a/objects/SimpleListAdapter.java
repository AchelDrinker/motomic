package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.keywords.Common;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SimpleListAdapter extends BaseAdapter implements ListAdapter {
    private static final int SINGLE_LINE_ITEM = 0;
    private static final int TWO_LINES_AND_BITMAP_ITEM = 2;
    private static final int TWO_LINES_ITEM = 1;
    public SingleLineLayout SingleLine;
    public TwoLinesLayout TwoLines;
    public TwoLinesAndBitmapLayout TwoLinesAndBitmap;
    public BALayout dummyParent;
    public ArrayList<SimpleItem> items = new ArrayList<>();
    private ItemLayout[] layouts;

    public interface ItemLayout {
        int getItemHeight();
    }

    public interface SimpleItem {
        void addNewToLayout(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter);

        Object getReturnValue();

        int getType();

        void updateExisting(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 3;
    }

    public SimpleListAdapter(Context context) {
        this.dummyParent = new BALayout(context);
        boolean z = false;
        TwoLinesAndBitmapLayout twoLinesAndBitmapLayout = null;
        this.SingleLine = new SingleLineLayout(this.dummyParent, z, twoLinesAndBitmapLayout, twoLinesAndBitmapLayout);
        this.TwoLines = new TwoLinesLayout(this.dummyParent, z, twoLinesAndBitmapLayout, twoLinesAndBitmapLayout);
        TwoLinesAndBitmapLayout twoLinesAndBitmapLayout2 = new TwoLinesAndBitmapLayout(this.dummyParent, twoLinesAndBitmapLayout);
        this.TwoLinesAndBitmap = twoLinesAndBitmapLayout2;
        this.layouts = new ItemLayout[]{this.SingleLine, this.TwoLines, twoLinesAndBitmapLayout2};
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return this.items.get(i).getType();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.items.get(i).getReturnValue();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        SimpleItem simpleItem = this.items.get(i);
        View view2 = view;
        if (view == null) {
            BALayout bALayout = new BALayout(viewGroup.getContext());
            bALayout.setLayoutParams(new AbsListView.LayoutParams(-1, this.layouts[simpleItem.getType()].getItemHeight()));
            simpleItem.addNewToLayout(bALayout, this);
            view2 = bALayout;
        }
        simpleItem.updateExisting((ViewGroup) view2, this);
        return view2;
    }

    public static class SingleLineLayout implements ItemLayout {
        public Drawable Background;
        public LabelWrapper Label;
        protected int itemHeight;

        private SingleLineLayout(ViewGroup viewGroup, boolean z) {
            TextView textView = new TextView(viewGroup.getContext());
            LabelWrapper labelWrapper = new LabelWrapper();
            this.Label = labelWrapper;
            labelWrapper.setObject(textView);
            if (z) {
                return;
            }
            textView.setTextSize(22.5f);
            textView.setTextColor(-1);
            this.itemHeight = Common.DipToCurrent(50);
            textView.setGravity(16);
            viewGroup.addView(textView, new BALayout.LayoutParams(Common.DipToCurrent(5), 0, -1, -1));
        }

        /* synthetic */ SingleLineLayout(ViewGroup viewGroup, boolean z, SingleLineLayout singleLineLayout) {
            this(viewGroup, z);
        }

        /* synthetic */ SingleLineLayout(ViewGroup viewGroup, boolean z, SingleLineLayout singleLineLayout, SingleLineLayout singleLineLayout2) {
            this(viewGroup, z);
        }

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.ItemLayout
        public int getItemHeight() {
            return this.itemHeight;
        }

        public void setItemHeight(int i) {
            this.itemHeight = i;
        }
    }

    public static class TwoLinesLayout extends SingleLineLayout {
        public LabelWrapper SecondLabel;

        /* synthetic */ TwoLinesLayout(ViewGroup viewGroup, boolean z, TwoLinesLayout twoLinesLayout) {
            this(viewGroup, z);
        }

        /* synthetic */ TwoLinesLayout(ViewGroup viewGroup, boolean z, TwoLinesLayout twoLinesLayout, TwoLinesLayout twoLinesLayout2) {
            this(viewGroup, z);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TwoLinesLayout(ViewGroup viewGroup, boolean z) {
            super(viewGroup, true, null);
            TextView textView = new TextView(viewGroup.getContext());
            LabelWrapper labelWrapper = new LabelWrapper();
            this.SecondLabel = labelWrapper;
            labelWrapper.setObject(textView);
            if (z) {
                return;
            }
            TextView textView2 = (TextView) this.Label.getObject();
            textView2.setTextSize(19.5f);
            textView.setTextSize(16.5f);
            this.itemHeight = Common.DipToCurrent(60);
            textView2.setGravity(80);
            textView.setGravity(48);
            textView2.setTextColor(-1);
            textView.setTextColor(-7829368);
            viewGroup.addView(textView2, new BALayout.LayoutParams(Common.DipToCurrent(5), 0, -1, Common.DipToCurrent(30)));
            viewGroup.addView(textView, new BALayout.LayoutParams(Common.DipToCurrent(5), Common.DipToCurrent(32), -1, Common.DipToCurrent(28)));
        }
    }

    public static class TwoLinesAndBitmapLayout extends TwoLinesLayout {
        public ImageViewWrapper ImageView;

        /* synthetic */ TwoLinesAndBitmapLayout(ViewGroup viewGroup, TwoLinesAndBitmapLayout twoLinesAndBitmapLayout) {
            this(viewGroup);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private TwoLinesAndBitmapLayout(ViewGroup viewGroup) {
            super(viewGroup, true, null);
            TextView textView = (TextView) this.Label.getObject();
            TextView textView2 = (TextView) this.SecondLabel.getObject();
            textView.setTextSize(19.5f);
            textView2.setTextSize(16.5f);
            this.itemHeight = Common.DipToCurrent(60);
            textView.setGravity(80);
            textView2.setGravity(48);
            textView.setTextColor(-1);
            textView2.setTextColor(-7829368);
            viewGroup.addView(textView, new BALayout.LayoutParams(Common.DipToCurrent(60), 0, -1, Common.DipToCurrent(30)));
            viewGroup.addView(textView2, new BALayout.LayoutParams(Common.DipToCurrent(60), Common.DipToCurrent(32), -1, Common.DipToCurrent(28)));
            ImageView imageView = new ImageView(viewGroup.getContext());
            ImageViewWrapper imageViewWrapper = new ImageViewWrapper();
            this.ImageView = imageViewWrapper;
            imageViewWrapper.setObject(imageView);
            viewGroup.addView(imageView, new BALayout.LayoutParams(Common.DipToCurrent(5), Common.DipToCurrent(5), Common.DipToCurrent(50), Common.DipToCurrent(50)));
        }
    }

    public static class SingleLineData implements SimpleItem {
        public Object ReturnValue;
        public CharSequence Text;

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public int getType() {
            return 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void addNewToLayout(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            viewGroup.setBackgroundDrawable(simpleListAdapter.SingleLine.Background);
            addNewToLayoutImpl(viewGroup, (TextView) simpleListAdapter.SingleLine.Label.getObject());
        }

        protected void addNewToLayoutImpl(ViewGroup viewGroup, TextView textView) {
            TextView textView2 = new TextView(viewGroup.getContext());
            viewGroup.addView(textView2, textView.getLayoutParams());
            textView2.setBackgroundDrawable(textView.getBackground());
            textView2.setTextSize(textView.getTextSize() / viewGroup.getContext().getResources().getDisplayMetrics().scaledDensity);
            textView2.setTextColor(textView.getTextColors());
            textView2.setGravity(textView.getGravity());
            textView2.setVisibility(textView.getVisibility());
            textView2.setTypeface(textView.getTypeface());
        }

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void updateExisting(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            ((TextView) viewGroup.getChildAt(0)).setText(this.Text);
        }

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public Object getReturnValue() {
            Object obj = this.ReturnValue;
            return obj == null ? String.valueOf(this.Text) : obj;
        }
    }

    public static class TwoLinesData extends SingleLineData {
        public CharSequence SecondLineText;

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public int getType() {
            return 1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void addNewToLayout(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            viewGroup.setBackgroundDrawable(simpleListAdapter.TwoLines.Background);
            super.addNewToLayoutImpl(viewGroup, (TextView) simpleListAdapter.TwoLines.Label.getObject());
            super.addNewToLayoutImpl(viewGroup, (TextView) simpleListAdapter.TwoLines.SecondLabel.getObject());
        }

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void updateExisting(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            super.updateExisting(viewGroup, simpleListAdapter);
            ((TextView) viewGroup.getChildAt(1)).setText(this.SecondLineText);
        }
    }

    public static class TwoLinesAndBitmapData extends TwoLinesData {
        public Bitmap Bitmap;

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.TwoLinesData, anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public int getType() {
            return 2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.TwoLinesData, anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void addNewToLayout(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            viewGroup.setBackgroundDrawable(simpleListAdapter.TwoLinesAndBitmap.Background);
            super.addNewToLayoutImpl(viewGroup, (TextView) simpleListAdapter.TwoLinesAndBitmap.Label.getObject());
            super.addNewToLayoutImpl(viewGroup, (TextView) simpleListAdapter.TwoLinesAndBitmap.SecondLabel.getObject());
            ImageView imageView = new ImageView(viewGroup.getContext());
            ImageView imageView2 = (ImageView) simpleListAdapter.TwoLinesAndBitmap.ImageView.getObject();
            viewGroup.addView(imageView, imageView2.getLayoutParams());
            imageView.setVisibility(imageView2.getVisibility());
        }

        @Override // anywheresoftware.b4a.objects.SimpleListAdapter.TwoLinesData, anywheresoftware.b4a.objects.SimpleListAdapter.SingleLineData, anywheresoftware.b4a.objects.SimpleListAdapter.SimpleItem
        public void updateExisting(ViewGroup viewGroup, SimpleListAdapter simpleListAdapter) {
            super.updateExisting(viewGroup, simpleListAdapter);
            ImageView imageView = (ImageView) viewGroup.getChildAt(2);
            imageView.setVisibility(this.Bitmap != null ? 0 : 8);
            if (this.Bitmap != null) {
                imageView.setBackgroundDrawable(new BitmapDrawable(this.Bitmap));
            }
        }
    }
}
