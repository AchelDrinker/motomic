package anywheresoftware.b4a.objects;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.drawable.BitmapDrawable;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("TabHost")
public class TabHostWrapper extends ViewWrapper<TabHost> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new TabHost(ba.context, null));
        }
        super.innerInitialize(ba, str, true);
        initializeTabWidget(ba.context, (TabHost) getObject());
        if (ba.subExists(String.valueOf(str) + "_tabchanged")) {
            ((TabHost) getObject()).setOnTabChangedListener(new TabHost.OnTabChangeListener() { // from class: anywheresoftware.b4a.objects.TabHostWrapper.1
                @Override // android.widget.TabHost.OnTabChangeListener
                public void onTabChanged(String str2) {
                    ba.raiseEvent2(TabHostWrapper.this.getObject(), false, String.valueOf(str) + "_tabchanged", false, new Object[0]);
                }
            });
        }
        MyContentFactory myContentFactory = new MyContentFactory(new View(ba.context));
        TabHost.TabSpec tabSpecNewTabSpec = ((TabHost) getObject()).newTabSpec("~temp");
        tabSpecNewTabSpec.setContent(myContentFactory);
        tabSpecNewTabSpec.setIndicator("");
        ((TabHost) getObject()).addTab(tabSpecNewTabSpec);
    }

    private static void initializeTabWidget(Context context, TabHost tabHost) {
        TabWidget tabWidget = new TabWidget(context);
        LinearLayout linearLayout = new LinearLayout(context);
        int iDipToCurrent = Common.DipToCurrent(5);
        linearLayout.setPadding(iDipToCurrent, iDipToCurrent, iDipToCurrent, iDipToCurrent);
        linearLayout.setOrientation(1);
        tabWidget.setId(R.id.tabs);
        linearLayout.addView(tabWidget, new ViewGroup.LayoutParams(-1, -2));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(R.id.tabcontent);
        frameLayout.setPadding(iDipToCurrent, iDipToCurrent, iDipToCurrent, iDipToCurrent);
        linearLayout.addView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
        tabHost.addView(linearLayout, new ViewGroup.LayoutParams(-1, -1));
        tabHost.setup();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AddTab2(String str, View view) {
        if (((TabHost) getObject()).getCurrentTabTag().equals("~temp")) {
            ((TabHost) getObject()).clearAllTabs();
        }
        MyContentFactory myContentFactory = new MyContentFactory(view);
        TabHost.TabSpec tabSpecNewTabSpec = ((TabHost) getObject()).newTabSpec("");
        tabSpecNewTabSpec.setContent(myContentFactory);
        tabSpecNewTabSpec.setIndicator(str);
        ((TabHost) getObject()).addTab(tabSpecNewTabSpec);
    }

    public void AddTab(BA ba, String str, String str2) throws Exception {
        AddTab2(str, createPanelForLayoutFile(ba, str2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private View createPanelForLayoutFile(BA ba, String str) throws Exception {
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(ba, "");
        ((ViewGroup) panelWrapper.getObject()).setLayoutParams(new ViewGroup.LayoutParams(getWidth() - Common.DipToCurrent(20), getHeight() - Common.DipToCurrent((BA.applicationContext.getApplicationInfo().targetSdkVersion < 11 || Build.VERSION.SDK_INT < 11 || Common.GetDeviceLayoutValues(ba).getApproximateScreenSize() >= 5.0d) ? 84 : 68)));
        panelWrapper.LoadLayout(str, ba);
        return (View) panelWrapper.getObject();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void AddTabWithIcon2(String str, Bitmap bitmap, Bitmap bitmap2, View view) {
        if (((TabHost) getObject()).getCurrentTabTag().equals("~temp")) {
            ((TabHost) getObject()).clearAllTabs();
        }
        MyContentFactory myContentFactory = new MyContentFactory(view);
        TabHost.TabSpec tabSpecNewTabSpec = ((TabHost) getObject()).newTabSpec("");
        tabSpecNewTabSpec.setContent(myContentFactory);
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        bitmapDrawable.Initialize(bitmap);
        BitmapDrawable bitmapDrawable2 = new BitmapDrawable();
        bitmapDrawable2.Initialize(bitmap2);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842913}, bitmapDrawable2.getObject());
        stateListDrawable.addState(new int[0], bitmapDrawable.getObject());
        tabSpecNewTabSpec.setIndicator(str, stateListDrawable);
        ((TabHost) getObject()).addTab(tabSpecNewTabSpec);
    }

    public void AddTabWithIcon(BA ba, String str, Bitmap bitmap, Bitmap bitmap2, String str2) throws Exception {
        AddTabWithIcon2(str, bitmap, bitmap2, createPanelForLayoutFile(ba, str2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getCurrentTab() {
        return ((TabHost) getObject()).getCurrentTab();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setCurrentTab(int i) {
        ((TabHost) getObject()).setCurrentTab(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getTabCount() {
        return ((TabHost) getObject()).getTabWidget().getTabCount();
    }

    private static class MyContentFactory implements TabHost.TabContentFactory {
        private View view;

        public MyContentFactory(View view) {
            this.view = view;
        }

        @Override // android.widget.TabHost.TabContentFactory
        public View createTabContent(String str) {
            return this.view;
        }
    }

    public static class MyTabHost extends TabHost {
        public MyTabHost(Context context) {
            super(context, null);
        }
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        boolean z2;
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, MyTabHost.class, map, z);
            z2 = true;
        } else {
            z2 = false;
        }
        TabHost tabHost = (TabHost) ViewWrapper.build(obj, map, z);
        if (z && z2) {
            Context context = (Context) obj2;
            initializeTabWidget(context, tabHost);
            TextView textView = new TextView(context);
            textView.setText("This is an example page.\nTab pages should be added programmatically.");
            for (int i = 1; i <= 3; i++) {
                MyContentFactory myContentFactory = new MyContentFactory(textView);
                TabHost.TabSpec tabSpecNewTabSpec = tabHost.newTabSpec("");
                tabSpecNewTabSpec.setContent(myContentFactory);
                tabSpecNewTabSpec.setIndicator("Page " + i);
                tabHost.addTab(tabSpecNewTabSpec);
            }
        }
        return tabHost;
    }
}
