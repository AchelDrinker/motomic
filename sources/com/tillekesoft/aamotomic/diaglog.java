package com.tillekesoft.aamotomic;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.B4AMenuItem;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.Regex;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.streams.File;
import anywheresoftware.b4j.object.JavaObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.io.encoding.Base64;

/* JADX INFO: loaded from: classes.dex */
public class diaglog extends Activity implements B4AActivity {
    public static String _diag_enabled_file = "";
    public static String _diag_file = "";
    public static int _scroll_bar_width = 0;
    public static int _thumb_min_height = 0;
    public static Timer _vvvvvvvvvvvvv7 = null;
    static boolean afterFirstLayout = false;
    public static boolean dontPause = false;
    public static final boolean fullScreen = true;
    public static final boolean includeTitle = false;
    static boolean isFirst = true;
    public static diaglog mostCurrent = null;
    public static WeakReference<Activity> previousOne = null;
    public static BA processBA = null;
    private static boolean processGlobalsRun = false;
    ActivityWrapper _activity;
    BA activityBA;
    BALayout layout;
    ArrayList<B4AMenuItem> menuItems;
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
    public Common __c = null;
    public ScrollViewWrapper _vvvvvvvvvvvvv3 = null;
    public LabelWrapper _vvvvvvvvvvvvv4 = null;
    public LabelWrapper _vvvvvvvvvvvvv2 = null;
    public ImageViewWrapper _vv1 = null;
    public LabelWrapper _vvvvvvvvvvvvv1 = null;
    public LabelWrapper _vv3 = null;
    public PanelWrapper _vvvvvvvvvvvvv5 = null;
    public PanelWrapper _vvvvvvvvvvvvv6 = null;
    public main _vvvvvv5 = null;
    public starter _vvvvvv2 = null;
    public audioservice _vvvvv5 = null;
    public btreceiver _vvvvvv3 = null;
    public readmeviewer _vvvvv2 = null;
    public aggressivemode _vvvvv1 = null;

    public static void _inputlist_result(int i) throws Exception {
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        Activity activity;
        super.onCreate(bundle);
        mostCurrent = this;
        if (processBA == null) {
            BA ba = new BA(getApplicationContext(), (BALayout) null, (BA) null, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.diaglog");
            processBA = ba;
            ba.loadHtSubs(getClass());
            BALayout.setDeviceScale(getApplicationContext().getResources().getDisplayMetrics().density);
        } else {
            WeakReference<Activity> weakReference = previousOne;
            if (weakReference != null && (activity = weakReference.get()) != null && activity != this) {
                BA.LogInfo("Killing previous instance (diaglog).");
                activity.finish();
            }
        }
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
        getWindow().requestFeature(1);
        getWindow().setFlags(1024, 1024);
        processBA.sharedProcessBA.activityBA = null;
        BALayout bALayout = new BALayout(this);
        this.layout = bALayout;
        setContentView(bALayout);
        afterFirstLayout = false;
        WaitForLayout waitForLayout = new WaitForLayout();
        if (ServiceHelper.StarterHelper.startFromActivity(this, processBA, waitForLayout, false)) {
            BA.handler.postDelayed(waitForLayout, 5L);
        }
    }

    static class WaitForLayout implements Runnable {
        WaitForLayout() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (diaglog.afterFirstLayout || diaglog.mostCurrent == null) {
                return;
            }
            if (diaglog.mostCurrent.layout.getWidth() == 0) {
                BA.handler.postDelayed(this, 5L);
                return;
            }
            diaglog.mostCurrent.layout.getLayoutParams().height = diaglog.mostCurrent.layout.getHeight();
            diaglog.mostCurrent.layout.getLayoutParams().width = diaglog.mostCurrent.layout.getWidth();
            diaglog.afterFirstLayout = true;
            diaglog.mostCurrent.afterFirstLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void afterFirstLayout() {
        if (this != mostCurrent) {
            return;
        }
        this.activityBA = new BA(this, this.layout, processBA, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.diaglog");
        processBA.sharedProcessBA.activityBA = new WeakReference<>(this.activityBA);
        ViewWrapper.lastId = 0;
        this._activity = new ActivityWrapper(this.activityBA, "activity");
        Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
            if (isFirst) {
                processBA.raiseEvent2(null, true, "SHELL", false, new Object[0]);
            }
            BA ba = processBA;
            ba.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.diaglog", ba, this.activityBA, this._activity, Float.valueOf(Common.Density), mostCurrent);
            this._activity.reinitializeForShell(this.activityBA, "activity");
        }
        initializeProcessGlobals();
        initializeGlobals();
        StringBuilder sb = new StringBuilder("** Activity (diaglog) Create ");
        sb.append(isFirst ? "(first time)" : "");
        sb.append(" **");
        BA.LogInfo(sb.toString());
        processBA.raiseEvent2(null, true, "activity_create", false, Boolean.valueOf(isFirst));
        isFirst = false;
        if (this != mostCurrent) {
            return;
        }
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (diaglog) Resume **");
        processBA.raiseEvent(null, "activity_resume", new Object[0]);
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                Activity.class.getMethod("invalidateOptionsMenu", null).invoke(this, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // anywheresoftware.b4a.B4AActivity
    public void addMenuItem(B4AMenuItem b4AMenuItem) {
        if (this.menuItems == null) {
            this.menuItems = new ArrayList<>();
        }
        this.menuItems.add(b4AMenuItem);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", Boolean.TYPE).invoke(getClass().getMethod("getActionBar", null).invoke(this, null), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[]{menu})) {
            return true;
        }
        ArrayList<B4AMenuItem> arrayList = this.menuItems;
        if (arrayList == null) {
            return false;
        }
        for (B4AMenuItem b4AMenuItem : arrayList) {
            MenuItem menuItemAdd = menu.add(b4AMenuItem.title);
            if (b4AMenuItem.drawable != null) {
                menuItemAdd.setIcon(b4AMenuItem.drawable);
            }
            if (Build.VERSION.SDK_INT >= 11) {
                try {
                    if (b4AMenuItem.addToBar) {
                        MenuItem.class.getMethod("setShowAsAction", Integer.TYPE).invoke(menuItemAdd, 1);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            menuItemAdd.setOnMenuItemClickListener(new B4AMenuItemsClickListener(b4AMenuItem.eventName.toLowerCase(BA.cul)));
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            processBA.raiseEvent(null, "activity_actionbarhomeclick", new Object[0]);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        processBA.runHook("onprepareoptionsmenu", this, new Object[]{menu});
        return true;
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        processBA.runHook("onstart", this, null);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        processBA.runHook("onstop", this, null);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (processBA.subExists("activity_windowfocuschanged")) {
            processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, Boolean.valueOf(z));
        }
    }

    private class B4AMenuItemsClickListener implements MenuItem.OnMenuItemClickListener {
        private final String eventName;

        public B4AMenuItemsClickListener(String str) {
            this.eventName = str;
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            diaglog.processBA.raiseEventFromUI(menuItem.getTitle(), this.eventName + "_click", new Object[0]);
            return true;
        }
    }

    public static Class<?> getObject() {
        return diaglog.class;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (processBA.runHook("onkeydown", this, new Object[]{Integer.valueOf(i), keyEvent})) {
            return true;
        }
        if (this.onKeySubExist == null) {
            this.onKeySubExist = Boolean.valueOf(processBA.subExists("activity_keypress"));
        }
        if (this.onKeySubExist.booleanValue()) {
            if (i == 4 && Build.VERSION.SDK_INT >= 18) {
                HandleKeyDelayed handleKeyDelayed = new HandleKeyDelayed();
                handleKeyDelayed.kc = i;
                BA.handler.post(handleKeyDelayed);
                return true;
            }
            if (new HandleKeyDelayed().runDirectly(i)) {
                return true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    private class HandleKeyDelayed implements Runnable {
        int kc;

        private HandleKeyDelayed() {
        }

        @Override // java.lang.Runnable
        public void run() {
            runDirectly(this.kc);
        }

        public boolean runDirectly(int i) {
            Boolean bool = (Boolean) diaglog.processBA.raiseEvent2(diaglog.this._activity, false, "activity_keypress", false, Integer.valueOf(i));
            if (bool == null || bool.booleanValue()) {
                return true;
            }
            if (i != 4) {
                return false;
            }
            diaglog.this.finish();
            return true;
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        Boolean bool;
        if (processBA.runHook("onkeyup", this, new Object[]{Integer.valueOf(i), keyEvent})) {
            return true;
        }
        if (this.onKeyUpSubExist == null) {
            this.onKeyUpSubExist = Boolean.valueOf(processBA.subExists("activity_keyup"));
        }
        if (this.onKeyUpSubExist.booleanValue() && ((bool = (Boolean) processBA.raiseEvent2(this._activity, false, "activity_keyup", false, Integer.valueOf(i))) == null || bool.booleanValue())) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[]{intent});
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        if (this._activity != null && this == mostCurrent) {
            Msgbox.dismiss(true);
            if (!dontPause) {
                BA.LogInfo("** Activity (diaglog) Pause, UserClosed = " + this.activityBA.activity.isFinishing() + " **");
            } else {
                BA.LogInfo("** Activity (diaglog) Pause event (activity is not paused). **");
            }
            if (mostCurrent != null) {
                processBA.raiseEvent2(this._activity, true, "activity_pause", false, Boolean.valueOf(this.activityBA.activity.isFinishing()));
            }
            if (!dontPause) {
                processBA.setActivityPaused(true);
                mostCurrent = null;
            }
            if (!this.activityBA.activity.isFinishing()) {
                previousOne = new WeakReference<>(this);
            }
            Msgbox.isDismissing = false;
            processBA.runHook("onpause", this, null);
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        previousOne = null;
        processBA.runHook("ondestroy", this, null);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        mostCurrent = this;
        Msgbox.isDismissing = false;
        if (this.activityBA != null) {
            BA.handler.post(new ResumeMessage(mostCurrent));
        }
        processBA.runHook("onresume", this, null);
    }

    private static class ResumeMessage implements Runnable {
        private final WeakReference<Activity> activity;

        public ResumeMessage(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override // java.lang.Runnable
        public void run() {
            diaglog diaglogVar = diaglog.mostCurrent;
            if (diaglogVar == null || diaglogVar != this.activity.get()) {
                return;
            }
            diaglog.processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (diaglog) Resume **");
            if (diaglogVar != diaglog.mostCurrent) {
                return;
            }
            diaglog.processBA.raiseEvent(diaglogVar._activity, "activity_resume", null);
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        processBA.onActivityResult(i, i2, intent);
        processBA.runHook("onactivityresult", this, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
    }

    private static void initializeGlobals() {
        processBA.raiseEvent2(null, true, "globals", false, null);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            processBA.raiseEventFromDifferentThread(null, null, 0, "activity_permissionresult", true, new Object[]{strArr[i2], Boolean.valueOf(iArr[i2] == 0)});
        }
    }

    public static void initializeProcessGlobals() {
        try {
            Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals", null).invoke(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String _activity_create(boolean z) throws Exception {
        mostCurrent._activity.setColor(-16777216);
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(mostCurrent.activityBA, "");
        panelWrapper.setColor(-16777216);
        mostCurrent._activity.AddView((View) panelWrapper.getObject(), 0, 0, Common.PerXToCurrent(100.0f, mostCurrent.activityBA), Common.DipToCurrent(70));
        diaglog diaglogVar = mostCurrent;
        diaglogVar._vvvvvvvvvvvvv1.Initialize(diaglogVar.activityBA, "btnBack");
        mostCurrent._vvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("←"));
        mostCurrent._vvvvvvvvvvvvv1.setTextSize(24.0f);
        LabelWrapper labelWrapper = mostCurrent._vvvvvvvvvvvvv1;
        Colors colors = Common.Colors;
        labelWrapper.setTextColor(-1);
        LabelWrapper labelWrapper2 = mostCurrent._vvvvvvvvvvvvv1;
        Gravity gravity = Common.Gravity;
        labelWrapper2.setGravity(17);
        panelWrapper.AddView((View) mostCurrent._vvvvvvvvvvvvv1.getObject(), Common.DipToCurrent(5), Common.DipToCurrent(12), Common.DipToCurrent(35), Common.DipToCurrent(45));
        diaglog diaglogVar2 = mostCurrent;
        diaglogVar2._vv1.Initialize(diaglogVar2.activityBA, "imgIcon");
        panelWrapper.AddView((View) mostCurrent._vv1.getObject(), Common.DipToCurrent(42), Common.DipToCurrent(10), Common.DipToCurrent(50), Common.DipToCurrent(50));
        try {
            ImageViewWrapper imageViewWrapper = mostCurrent._vv1;
            File file = Common.File;
            imageViewWrapper.setBitmap(Common.LoadBitmapResize(File.getDirAssets(), "icon.png", Common.DipToCurrent(50), Common.DipToCurrent(50), true).getObject());
        } catch (Exception e) {
            processBA.setLastException(e);
            ImageViewWrapper imageViewWrapper2 = mostCurrent._vv1;
            Colors colors2 = Common.Colors;
            imageViewWrapper2.setColor(-12303292);
        }
        LabelWrapper labelWrapper3 = new LabelWrapper();
        labelWrapper3.Initialize(mostCurrent.activityBA, "");
        labelWrapper3.setText(BA.ObjectToCharSequence("Diagnostic Log"));
        labelWrapper3.setTextSize(18.0f);
        TypefaceWrapper typefaceWrapper = Common.Typeface;
        labelWrapper3.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        labelWrapper3.setTextColor(-1);
        Gravity gravity2 = Common.Gravity;
        Gravity gravity3 = Common.Gravity;
        labelWrapper3.setGravity(19);
        panelWrapper.AddView((View) labelWrapper3.getObject(), Common.DipToCurrent(100), Common.DipToCurrent(10), Common.PerXToCurrent(55.0f, mostCurrent.activityBA), Common.DipToCurrent(28));
        diaglog diaglogVar3 = mostCurrent;
        diaglogVar3._vvvvvvvvvvvvv2.Initialize(diaglogVar3.activityBA, "");
        mostCurrent._vvvvvvvvvvvvv2.setTextSize(11.0f);
        mostCurrent._vvvvvvvvvvvvv2.setTextColor(-5592406);
        LabelWrapper labelWrapper4 = mostCurrent._vvvvvvvvvvvvv2;
        Gravity gravity4 = Common.Gravity;
        Gravity gravity5 = Common.Gravity;
        labelWrapper4.setGravity(19);
        panelWrapper.AddView((View) mostCurrent._vvvvvvvvvvvvv2.getObject(), Common.DipToCurrent(100), Common.DipToCurrent(38), Common.PerXToCurrent(55.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        diaglog diaglogVar4 = mostCurrent;
        diaglogVar4._vv3.Initialize(diaglogVar4.activityBA, "btnMenu");
        mostCurrent._vv3.setText(BA.ObjectToCharSequence("⋮"));
        mostCurrent._vv3.setTextSize(28.0f);
        LabelWrapper labelWrapper5 = mostCurrent._vv3;
        Colors colors3 = Common.Colors;
        labelWrapper5.setTextColor(-1);
        LabelWrapper labelWrapper6 = mostCurrent._vv3;
        Gravity gravity6 = Common.Gravity;
        labelWrapper6.setGravity(17);
        panelWrapper.AddView((View) mostCurrent._vv3.getObject(), Common.PerXToCurrent(100.0f, mostCurrent.activityBA) - Common.DipToCurrent(50), Common.DipToCurrent(10), Common.DipToCurrent(40), Common.DipToCurrent(50));
        int iDipToCurrent = Common.DipToCurrent(75);
        int iPerYToCurrent = (Common.PerYToCurrent(100.0f, mostCurrent.activityBA) - Common.DipToCurrent(80)) - iDipToCurrent;
        int iPerXToCurrent = (Common.PerXToCurrent(100.0f, mostCurrent.activityBA) - _scroll_bar_width) - Common.DipToCurrent(4);
        int iPerXToCurrent2 = iPerXToCurrent - Common.PerXToCurrent(2.0f, mostCurrent.activityBA);
        diaglog diaglogVar5 = mostCurrent;
        diaglogVar5._vvvvvvvvvvvvv3.Initialize(diaglogVar5.activityBA, Common.DipToCurrent(10000));
        diaglog diaglogVar6 = mostCurrent;
        diaglogVar6._activity.AddView((View) diaglogVar6._vvvvvvvvvvvvv3.getObject(), Common.PerXToCurrent(2.0f, mostCurrent.activityBA), iDipToCurrent, iPerXToCurrent2, iPerYToCurrent);
        mostCurrent._vvvvvvvvvvvvv3.getPanel().setColor(-15658735);
        diaglog diaglogVar7 = mostCurrent;
        diaglogVar7._vvvvvvvvvvvvv4.Initialize(diaglogVar7.activityBA, "");
        mostCurrent._vvvvvvvvvvvvv4.setColor(-15658735);
        mostCurrent._vvvvvvvvvvvvv4.setTextColor(-16711936);
        mostCurrent._vvvvvvvvvvvvv4.setTextSize(12.0f);
        LabelWrapper labelWrapper7 = mostCurrent._vvvvvvvvvvvvv4;
        TypefaceWrapper typefaceWrapper2 = Common.Typeface;
        labelWrapper7.setTypeface(TypefaceWrapper.MONOSPACE);
        LabelWrapper labelWrapper8 = mostCurrent._vvvvvvvvvvvvv4;
        Gravity gravity7 = Common.Gravity;
        Gravity gravity8 = Common.Gravity;
        labelWrapper8.setGravity(51);
        mostCurrent._vvvvvvvvvvvvv3.getPanel().AddView((View) mostCurrent._vvvvvvvvvvvvv4.getObject(), Common.DipToCurrent(10), Common.DipToCurrent(10), iPerXToCurrent2 - Common.DipToCurrent(20), Common.DipToCurrent(9000));
        diaglog diaglogVar8 = mostCurrent;
        diaglogVar8._vvvvvvvvvvvvv5.Initialize(diaglogVar8.activityBA, "pnlTrack");
        mostCurrent._vvvvvvvvvvvvv5.setColor(-13421773);
        diaglog diaglogVar9 = mostCurrent;
        diaglogVar9._activity.AddView((View) diaglogVar9._vvvvvvvvvvvvv5.getObject(), iPerXToCurrent, iDipToCurrent, _scroll_bar_width, iPerYToCurrent);
        diaglog diaglogVar10 = mostCurrent;
        diaglogVar10._vvvvvvvvvvvvv6.Initialize(diaglogVar10.activityBA, "");
        mostCurrent._vvvvvvvvvvvvv6.setColor(-10066330);
        mostCurrent._vvvvvvvvvvvvv6.SetLayout(0, 0, _scroll_bar_width, _thumb_min_height);
        diaglog diaglogVar11 = mostCurrent;
        diaglogVar11._vvvvvvvvvvvvv5.AddView((View) diaglogVar11._vvvvvvvvvvvvv6.getObject(), 0, 0, _scroll_bar_width, _thumb_min_height);
        if (!_vvvvvvvvvvvvv7.IsInitialized()) {
            _vvvvvvvvvvvvv7.Initialize(processBA, "timerScroll", 150L);
        }
        _vvvvvvvvvvvvv0();
        return "";
    }

    public static String _activity_pause(boolean z) throws Exception {
        _vvvvvvvvvvvvv7.setEnabled(false);
        return "";
    }

    public static String _activity_resume() throws Exception {
        _vvvvvvvvvvvvv0();
        _vvvvvvvvvvvvv7.setEnabled(true);
        return "";
    }

    public static String _btnback_click() throws Exception {
        mostCurrent._activity.Finish();
        return "";
    }

    public static void _btnmenu_click() throws Exception {
        new ResumableSub_btnMenu_Click(null).resume(processBA, null);
    }

    public static class ResumableSub_btnMenu_Click extends BA.ResumableSub {
        diaglog parent;
        List _options = null;
        int _index = 0;

        public ResumableSub_btnMenu_Click(diaglog diaglogVar) {
            this.parent = diaglogVar;
        }

        @Override // anywheresoftware.b4a.BA.ResumableSub
        public void resume(BA ba, Object[] objArr) throws Exception {
            while (true) {
                switch (this.state) {
                    case -1:
                        return;
                    case 0:
                        this.state = 1;
                        List list = new List();
                        this._options = list;
                        list.Initialize();
                        this._options.Add("📄 Copy Full Log (private)");
                        this._options.Add("🔒 Copy Sanitized Log (safe to share)");
                        this._options.Add("🔄 Refresh Log");
                        this._options.Add("🗑️ Delete Log");
                        break;
                    case 1:
                        this.state = 6;
                        if (diaglog._vvvvvvvvvvvvvv1()) {
                            this.state = 3;
                        } else {
                            this.state = 5;
                        }
                        break;
                    case 3:
                        this.state = 6;
                        this._options.Add("Disable logging");
                        break;
                    case 5:
                        this.state = 6;
                        this._options.Add("Enable logging");
                        break;
                    case 6:
                        this.state = 7;
                        this._options.Add("❌ Cancel");
                        Common.InputListAsync(this._options, BA.ObjectToCharSequence("Menu"), -1, diaglog.processBA, false);
                        Common.WaitFor("inputlist_result", diaglog.processBA, this, null);
                        this.state = 19;
                        return;
                    case 7:
                        this.state = 18;
                        int i = this._index;
                        if (i == 0) {
                            this.state = 9;
                        } else if (i == 1) {
                            this.state = 11;
                        } else if (i == 2) {
                            this.state = 13;
                        } else if (i == 3) {
                            this.state = 15;
                        } else if (i == 4) {
                            this.state = 17;
                        }
                        break;
                    case 9:
                        this.state = 18;
                        diaglog._mnucopy_click();
                        break;
                    case 11:
                        this.state = 18;
                        diaglog._mnucopysanitized_click();
                        break;
                    case 13:
                        this.state = 18;
                        diaglog._mnurefresh_click();
                        break;
                    case 15:
                        this.state = 18;
                        diaglog._mnudelete_click();
                        break;
                    case 17:
                        this.state = 18;
                        diaglog._mnudiagtoggle_click();
                        break;
                    case 18:
                        this.state = -1;
                        break;
                    case 19:
                        this.state = 7;
                        this._index = ((Integer) objArr[0]).intValue();
                        break;
                }
            }
        }
    }

    public static int _vvvvvvvvvvvvvv2() throws Exception {
        int height = mostCurrent._vvvvvvvvvvvvv3.getPanel().getHeight() - mostCurrent._vvvvvvvvvvvvv3.getHeight();
        if (height < 0) {
            return 0;
        }
        return height;
    }

    public static int _vvvvvvvvvvvvvv3() throws Exception {
        return mostCurrent._vvvvvvvvvvvvv3.getScrollPosition();
    }

    public static String _globals() throws Exception {
        mostCurrent._vvvvvvvvvvvvv3 = new ScrollViewWrapper();
        mostCurrent._vvvvvvvvvvvvv4 = new LabelWrapper();
        mostCurrent._vvvvvvvvvvvvv2 = new LabelWrapper();
        mostCurrent._vv1 = new ImageViewWrapper();
        mostCurrent._vvvvvvvvvvvvv1 = new LabelWrapper();
        mostCurrent._vv3 = new LabelWrapper();
        mostCurrent._vvvvvvvvvvvvv5 = new PanelWrapper();
        mostCurrent._vvvvvvvvvvvvv6 = new PanelWrapper();
        _scroll_bar_width = 24;
        _thumb_min_height = 40;
        return "";
    }

    public static String _vvvvvvvvvvvvv0() throws Exception {
        String str;
        mostCurrent._vvvvvvvvvvvvv4.setText(BA.ObjectToCharSequence(_vvvvvvvvvvvvvv4()));
        boolean z_vvvvvvvvvvvvvv1 = _vvvvvvvvvvvvvv1();
        File file = Common.File;
        File file2 = Common.File;
        long jRound = 0;
        if (File.Exists(File.getDirInternal(), _diag_file)) {
            try {
                File file3 = Common.File;
                File file4 = Common.File;
                double dSize = File.Size(File.getDirInternal(), _diag_file);
                Double.isNaN(dSize);
                jRound = Common.Round(dSize / 1024.0d);
            } catch (Exception e) {
                processBA.setLastException(e);
            }
        }
        if (z_vvvvvvvvvvvvvv1) {
            str = "Logging: ON";
        } else {
            str = "Logging: OFF";
        }
        mostCurrent._vvvvvvvvvvvvv2.setText(BA.ObjectToCharSequence(str + " | Size: " + BA.NumberToString(jRound) + " KB"));
        return "";
    }

    public static String _vvvvvvvvvvvvvv5(String str) throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeContext(processBA);
            new JavaObject();
            JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getSystemService", new Object[]{"clipboard"}));
            JavaObject javaObject3 = new JavaObject();
            javaObject3.InitializeStatic("android.content.ClipData");
            new JavaObject();
            javaObject2.RunMethod("setPrimaryClip", new Object[]{((JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject3.RunMethod("newPlainText", new Object[]{"AAMotoMic Log", str}))).getObject()});
            return "";
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("45308426", "Clipboard copy failed: " + Common.LastException(mostCurrent.activityBA).getMessage(), 0);
            return "";
        }
    }

    public static String _vvvvvvvvvvvvvv6() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (File.Exists(File.getDirInternal(), _diag_file)) {
            File file3 = Common.File;
            File file4 = Common.File;
            File.Delete(File.getDirInternal(), _diag_file);
            return "";
        }
        return "";
    }

    public static boolean _vvvvvvvvvvvvvv1() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirInternal(), _diag_enabled_file)) {
            return false;
        }
        try {
            File file3 = Common.File;
            File file4 = Common.File;
            return File.ReadString(File.getDirInternal(), _diag_enabled_file).toLowerCase().equals("true");
        } catch (Exception e) {
            processBA.setLastException(e);
            return false;
        }
    }

    public static String _vvvvvvvvvvvvvv4() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirInternal(), _diag_file)) {
            return "No diagnostic log found.";
        }
        File file3 = Common.File;
        File file4 = Common.File;
        return File.ReadString(File.getDirInternal(), _diag_file);
    }

    public static String _vvvvvvvvvvvvvv7(boolean z) throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        File.WriteString(File.getDirInternal(), _diag_enabled_file, BA.ObjectToString(Boolean.valueOf(z)));
        return "";
    }

    public static String _mnucopy_click() throws Exception {
        String str_vvvvvvvvvvvvvv4 = _vvvvvvvvvvvvvv4();
        if (str_vvvvvvvvvvvvvv4.length() == 0 || str_vvvvvvvvvvvvvv4.equals("No diagnostic log found.")) {
            Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic log is empty."), true);
            return "";
        }
        _vvvvvvvvvvvvvv5(str_vvvvvvvvvvvvvv4);
        Common.ToastMessageShow(BA.ObjectToCharSequence("Full log copied - DO NOT share publicly!"), true);
        return "";
    }

    public static String _mnucopysanitized_click() throws Exception {
        String str_vvvvvvvvvvvvvv4 = _vvvvvvvvvvvvvv4();
        if (str_vvvvvvvvvvvvvv4.length() == 0 || str_vvvvvvvvvvvvvv4.equals("No diagnostic log found.")) {
            Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic log is empty."), true);
            return "";
        }
        _vvvvvvvvvvvvvv5(_vvvvvvvvvvvvvv0(str_vvvvvvvvvvvvvv4));
        Common.ToastMessageShow(BA.ObjectToCharSequence("Sanitized log copied (safe to share publicly)."), true);
        return "";
    }

    public static String _mnudelete_click() throws Exception {
        _vvvvvvvvvvvvvv6();
        _vvvvvvvvvvvvv0();
        Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic log deleted."), true);
        return "";
    }

    public static String _mnudiagtoggle_click() throws Exception {
        boolean z_vvvvvvvvvvvvvv1 = _vvvvvvvvvvvvvv1();
        _vvvvvvvvvvvvvv7(Common.Not(z_vvvvvvvvvvvvvv1));
        _vvvvvvvvvvvvv0();
        if (z_vvvvvvvvvvvvvv1) {
            Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic logging disabled."), true);
            return "";
        }
        Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic logging enabled."), true);
        return "";
    }

    public static String _mnurefresh_click() throws Exception {
        _vvvvvvvvvvvvv0();
        Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic log refreshed."), false);
        return "";
    }

    public static boolean _pnltrack_touch(int i, float f, float f2) throws Exception {
        int i_vvvvvvvvvvvvvv2 = _vvvvvvvvvvvvvv2();
        if (i_vvvvvvvvvvvvvv2 <= 0) {
            return true;
        }
        int height = mostCurrent._vvvvvvvvvvvvv3.getPanel().getHeight();
        int height2 = mostCurrent._vvvvvvvvvvvvv3.getHeight();
        int height3 = mostCurrent._vvvvvvvvvvvvv5.getHeight();
        double d = _thumb_min_height;
        double d2 = height2 * height3;
        double dMax = Common.Max(1, height);
        Double.isNaN(d2);
        int iMax = (int) Common.Max(d, d2 / dMax);
        if (iMax > height3) {
            iMax = height3;
        }
        int i2 = height3 - iMax;
        if (i2 <= 0) {
            return true;
        }
        double d3 = f2;
        double d4 = iMax;
        Double.isNaN(d4);
        Double.isNaN(d3);
        double d5 = (float) (d3 - (d4 / 2.0d));
        double d6 = i2;
        Double.isNaN(d5);
        Double.isNaN(d6);
        float f3 = (float) (d5 / d6);
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        _vvvvvvvvvvvvvvv1((int) (f3 * i_vvvvvvvvvvvvvv2));
        return true;
    }

    public static String _process_globals() throws Exception {
        _diag_file = main.vvv13(new byte[]{55, 41, -73, -64, ConnectorUtils.NULL, 52, -79, -107, 114, 39, -72, -121}, 52653);
        _diag_enabled_file = main.vvv13(new byte[]{55, 43, 14, -30, ConnectorUtils.NULL, 63, 9, -79, 62, Base64.padSymbol, 28, -75, 113, 63, 14, -15}, 391251);
        _vvvvvvvvvvvvv7 = new Timer();
        return "";
    }

    public static String _vvvvvvvvvvvvvv0(String str) throws Exception {
        new Regex.MatcherWrapper();
        Regex regex = Common.Regex;
        Regex regex2 = Common.Regex;
        Regex.MatcherWrapper matcherWrapperMatcher2 = Regex.Matcher2("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})", 2, str);
        List list = new List();
        list.Initialize();
        while (matcherWrapperMatcher2.Find()) {
            String match = matcherWrapperMatcher2.getMatch();
            if (list.IndexOf(match) == -1) {
                list.Add(match);
            }
        }
        int size = list.getSize();
        for (int i = 0; i < size; i++) {
            String strObjectToString = BA.ObjectToString(list.Get(i));
            str = str.replace(strObjectToString, "**:**:**:**:**:" + strObjectToString.substring(strObjectToString.length() - 2));
        }
        new Regex.MatcherWrapper();
        Regex regex3 = Common.Regex;
        Regex.MatcherWrapper matcherWrapperMatcher = Regex.Matcher("name=([^,\\|\\]\\n]+)", str);
        new Map().Initialize();
        List list2 = new List();
        list2.Initialize();
        while (matcherWrapperMatcher.Find()) {
            String strTrim = matcherWrapperMatcher.Group(1).trim();
            if (!strTrim.equals("Unknown") && !strTrim.equals("Speaker") && !strTrim.equals("Earpiece") && list2.IndexOf(strTrim) == -1) {
                list2.Add(strTrim);
            }
        }
        int size2 = list2.getSize();
        int i2 = 1;
        for (int i3 = 0; i3 < size2; i3++) {
            String strObjectToString2 = BA.ObjectToString(list2.Get(i3));
            String str2 = "name=" + strObjectToString2;
            str = str.replace(str2, "name=" + ("BT_Device_" + BA.NumberToString(i2)));
            i2++;
        }
        return "[SANITIZED LOG - MAC addresses and device names anonymized for privacy]\n\n" + str;
    }

    public static String _vvvvvvvvvvvvvvv1(int i) throws Exception {
        mostCurrent._vvvvvvvvvvvvv3.setScrollPosition(i);
        return "";
    }

    public static String _timerscroll_tick() throws Exception {
        int i_vvvvvvvvvvvvvv2 = _vvvvvvvvvvvvvv2();
        if (i_vvvvvvvvvvvvvv2 <= 0) {
            mostCurrent._vvvvvvvvvvvvv5.setVisible(false);
            return "";
        }
        mostCurrent._vvvvvvvvvvvvv5.setVisible(true);
        int height = mostCurrent._vvvvvvvvvvvvv3.getPanel().getHeight();
        int height2 = mostCurrent._vvvvvvvvvvvvv3.getHeight();
        int i_vvvvvvvvvvvvvv3 = _vvvvvvvvvvvvvv3();
        if (i_vvvvvvvvvvvvvv3 < 0) {
            i_vvvvvvvvvvvvvv3 = 0;
        }
        if (i_vvvvvvvvvvvvvv3 > i_vvvvvvvvvvvvvv2) {
            i_vvvvvvvvvvvvvv3 = i_vvvvvvvvvvvvvv2;
        }
        int height3 = mostCurrent._vvvvvvvvvvvvv5.getHeight();
        double d = _thumb_min_height;
        double d2 = height2 * height3;
        double dMax = Common.Max(1, height);
        Double.isNaN(d2);
        int iMax = (int) Common.Max(d, d2 / dMax);
        if (iMax > height3) {
            iMax = height3;
        }
        int i = height3 - iMax;
        if (i <= 0) {
            mostCurrent._vvvvvvvvvvvvv6.SetLayout(0, 0, _scroll_bar_width, height3);
            return "";
        }
        double d3 = i_vvvvvvvvvvvvvv3;
        double d4 = i_vvvvvvvvvvvvvv2;
        Double.isNaN(d3);
        Double.isNaN(d4);
        mostCurrent._vvvvvvvvvvvvv6.SetLayout(0, (int) (((float) (d3 / d4)) * i), _scroll_bar_width, iMax);
        return "";
    }
}
