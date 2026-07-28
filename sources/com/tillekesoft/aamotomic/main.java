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
import anywheresoftware.b4a.keywords.B4AApplication;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DateTime;
import anywheresoftware.b4a.keywords.Regex;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.B4XViewWrapper;
import anywheresoftware.b4a.objects.ButtonWrapper;
import anywheresoftware.b4a.objects.CompoundButtonWrapper;
import anywheresoftware.b4a.objects.EditTextWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.RuntimePermissions;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.SpinnerWrapper;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import anywheresoftware.b4a.objects.drawable.ColorDrawable;
import anywheresoftware.b4a.objects.streams.File;
import anywheresoftware.b4j.object.JavaObject;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class main extends Activity implements B4AActivity {
    public static String _diag_enabled_file = "";
    public static String _diag_file = "";
    public static boolean _v0 = false;
    public static RuntimePermissions _v7 = null;
    public static Timer _vvvv6 = null;
    public static B4XViewWrapper.XUI _vvvvvv1 = null;
    static boolean afterFirstLayout = false;
    private static byte[][] bb = null;
    public static boolean dontPause = false;
    public static final boolean fullScreen = true;
    public static final boolean includeTitle = false;
    static boolean isFirst = true;
    public static main mostCurrent = null;
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
    public PanelWrapper _vv5 = null;
    public LabelWrapper _vv6 = null;
    public LabelWrapper _vv0 = null;
    public EditTextWrapper _vvv2 = null;
    public ButtonWrapper _vvv1 = null;
    public SpinnerWrapper _vvv6 = null;
    public SpinnerWrapper _vvv0 = null;
    public ButtonWrapper _vvvv1 = null;
    public ButtonWrapper _vvvv2 = null;
    public CompoundButtonWrapper.CheckBoxWrapper _vvvv3 = null;
    public LabelWrapper _vvvv4 = null;
    public ImageViewWrapper _vv1 = null;
    public LabelWrapper _vvv4 = null;
    public LabelWrapper _vvv7 = null;
    public LabelWrapper _vv3 = null;
    public ButtonWrapper _vvvv5 = null;
    public ScrollViewWrapper _vv4 = null;
    public starter _vvvvvv2 = null;
    public audioservice _vvvvv5 = null;
    public btreceiver _vvvvvv3 = null;
    public diaglog _vvvvvv4 = null;
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
            BA ba = new BA(getApplicationContext(), (BALayout) null, (BA) null, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.main");
            processBA = ba;
            ba.loadHtSubs(getClass());
            BALayout.setDeviceScale(getApplicationContext().getResources().getDisplayMetrics().density);
        } else {
            WeakReference<Activity> weakReference = previousOne;
            if (weakReference != null && (activity = weakReference.get()) != null && activity != this) {
                BA.LogInfo("Killing previous instance (main).");
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
            if (main.afterFirstLayout || main.mostCurrent == null) {
                return;
            }
            if (main.mostCurrent.layout.getWidth() == 0) {
                BA.handler.postDelayed(this, 5L);
                return;
            }
            main.mostCurrent.layout.getLayoutParams().height = main.mostCurrent.layout.getHeight();
            main.mostCurrent.layout.getLayoutParams().width = main.mostCurrent.layout.getWidth();
            main.afterFirstLayout = true;
            main.mostCurrent.afterFirstLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void afterFirstLayout() {
        if (this != mostCurrent) {
            return;
        }
        this.activityBA = new BA(this, this.layout, processBA, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.main");
        processBA.sharedProcessBA.activityBA = new WeakReference<>(this.activityBA);
        ViewWrapper.lastId = 0;
        this._activity = new ActivityWrapper(this.activityBA, "activity");
        Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
            if (isFirst) {
                processBA.raiseEvent2(null, true, "SHELL", false, new Object[0]);
            }
            BA ba = processBA;
            ba.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.main", ba, this.activityBA, this._activity, Float.valueOf(Common.Density), mostCurrent);
            this._activity.reinitializeForShell(this.activityBA, "activity");
        }
        initializeProcessGlobals();
        initializeGlobals();
        StringBuilder sb = new StringBuilder("** Activity (main) Create ");
        sb.append(isFirst ? "(first time)" : "");
        sb.append(" **");
        BA.LogInfo(sb.toString());
        processBA.raiseEvent2(null, true, "activity_create", false, Boolean.valueOf(isFirst));
        isFirst = false;
        if (this != mostCurrent) {
            return;
        }
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
            main.processBA.raiseEventFromUI(menuItem.getTitle(), this.eventName + "_click", new Object[0]);
            return true;
        }
    }

    public static Class<?> getObject() {
        return main.class;
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
            Boolean bool = (Boolean) main.processBA.raiseEvent2(main.this._activity, false, "activity_keypress", false, Integer.valueOf(i));
            if (bool == null || bool.booleanValue()) {
                return true;
            }
            if (i != 4) {
                return false;
            }
            main.this.finish();
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
                BA.LogInfo("** Activity (main) Pause, UserClosed = " + this.activityBA.activity.isFinishing() + " **");
            } else {
                BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mainVar = main.mostCurrent;
            if (mainVar == null || mainVar != this.activity.get()) {
                return;
            }
            main.processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mainVar != main.mostCurrent) {
                return;
            }
            main.processBA.raiseEvent(mainVar._activity, "activity_resume", null);
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

    public static boolean isAnyActivityVisible() {
        return (mostCurrent != null) | (diaglog.mostCurrent != null) | (readmeviewer.mostCurrent != null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String _activity_create(boolean z) throws Exception {
        mostCurrent._activity.setColor(-16777216);
        PanelWrapper panelWrapper = new PanelWrapper();
        panelWrapper.Initialize(mostCurrent.activityBA, "");
        panelWrapper.setColor(-16777216);
        mostCurrent._activity.AddView((View) panelWrapper.getObject(), 0, 0, Common.PerXToCurrent(100.0f, mostCurrent.activityBA), Common.DipToCurrent(90));
        main mainVar = mostCurrent;
        mainVar._vv1.Initialize(mainVar.activityBA, "imgIcon");
        panelWrapper.AddView((View) mostCurrent._vv1.getObject(), Common.DipToCurrent(10), Common.DipToCurrent(10), Common.DipToCurrent(70), Common.DipToCurrent(70));
        try {
            ImageViewWrapper imageViewWrapper = mostCurrent._vv1;
            File file = Common.File;
            imageViewWrapper.setBitmap(Common.LoadBitmapResize(File.getDirAssets(), "icon.png", Common.DipToCurrent(70), Common.DipToCurrent(70), true).getObject());
        } catch (Exception e) {
            processBA.setLastException(e);
            ImageViewWrapper imageViewWrapper2 = mostCurrent._vv1;
            Colors colors = Common.Colors;
            imageViewWrapper2.setColor(-12303292);
        }
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.Initialize(mostCurrent.activityBA, "");
        labelWrapper.setText(BA.ObjectToCharSequence("AAMotoMic"));
        labelWrapper.setTextSize(22.0f);
        TypefaceWrapper typefaceWrapper = Common.Typeface;
        labelWrapper.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        Colors colors2 = Common.Colors;
        labelWrapper.setTextColor(-1);
        Gravity gravity = Common.Gravity;
        Gravity gravity2 = Common.Gravity;
        labelWrapper.setGravity(19);
        panelWrapper.AddView((View) labelWrapper.getObject(), Common.DipToCurrent(90), Common.DipToCurrent(15), Common.PerXToCurrent(60.0f, mostCurrent.activityBA), Common.DipToCurrent(30));
        LabelWrapper labelWrapper2 = new LabelWrapper();
        labelWrapper2.Initialize(mostCurrent.activityBA, "");
        StringBuilder sb = new StringBuilder("v");
        B4AApplication b4AApplication = Common.Application;
        sb.append(B4AApplication.getVersionName());
        sb.append(" Beta - ");
        sb.append(_vv2());
        labelWrapper2.setText(BA.ObjectToCharSequence(sb.toString()));
        labelWrapper2.setTextSize(12.0f);
        labelWrapper2.setTextColor(-7829368);
        Gravity gravity3 = Common.Gravity;
        Gravity gravity4 = Common.Gravity;
        labelWrapper2.setGravity(51);
        panelWrapper.AddView((View) labelWrapper2.getObject(), Common.DipToCurrent(90), Common.DipToCurrent(50), Common.PerXToCurrent(60.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        main mainVar2 = mostCurrent;
        mainVar2._vv3.Initialize(mainVar2.activityBA, "btnMenu");
        mostCurrent._vv3.setText(BA.ObjectToCharSequence("⋮"));
        mostCurrent._vv3.setTextSize(28.0f);
        LabelWrapper labelWrapper3 = mostCurrent._vv3;
        Colors colors3 = Common.Colors;
        labelWrapper3.setTextColor(-1);
        LabelWrapper labelWrapper4 = mostCurrent._vv3;
        Gravity gravity5 = Common.Gravity;
        labelWrapper4.setGravity(17);
        panelWrapper.AddView((View) mostCurrent._vv3.getObject(), Common.PerXToCurrent(100.0f, mostCurrent.activityBA) - Common.DipToCurrent(50), Common.DipToCurrent(20), Common.DipToCurrent(40), Common.DipToCurrent(50));
        main mainVar3 = mostCurrent;
        mainVar3._vv4.Initialize(mainVar3.activityBA, Common.DipToCurrent(550));
        mostCurrent._vv4.getPanel().setColor(-16777216);
        main mainVar4 = mostCurrent;
        mainVar4._activity.AddView((View) mainVar4._vv4.getObject(), 0, Common.DipToCurrent(90), Common.PerXToCurrent(100.0f, mostCurrent.activityBA), Common.PerYToCurrent(100.0f, mostCurrent.activityBA) - Common.DipToCurrent(90));
        main mainVar5 = mostCurrent;
        mainVar5._vv5.Initialize(mainVar5.activityBA, "");
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vv5.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(10), Common.PerXToCurrent(90.0f, mostCurrent.activityBA), Common.DipToCurrent(80));
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.Initialize2(-14540254, Common.DipToCurrent(15), 0, 0);
        mostCurrent._vv5.setBackground(colorDrawable.getObject());
        mostCurrent._vv6 = _vv7("STATUS: WAITING", -7829368, 18, true);
        main mainVar6 = mostCurrent;
        mainVar6._vv5.AddView((View) mainVar6._vv6.getObject(), 0, Common.DipToCurrent(10), mostCurrent._vv5.getWidth(), Common.DipToCurrent(30));
        mostCurrent._vv0 = _vv7("(Listening for Bluetooth...)", -5592406, 14, true);
        main mainVar7 = mostCurrent;
        mainVar7._vv5.AddView((View) mainVar7._vv0.getObject(), 0, Common.DipToCurrent(40), mostCurrent._vv5.getWidth(), Common.DipToCurrent(20));
        new LabelWrapper();
        mostCurrent._vv4.getPanel().AddView((View) _vv7("TARGET DEVICE", -3355444, 14, false).getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(115), Common.PerXToCurrent(55.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        main mainVar8 = mostCurrent;
        mainVar8._vvv1.Initialize(mainVar8.activityBA, "btnScan");
        mostCurrent._vvv1.setText(BA.ObjectToCharSequence("SCAN"));
        mostCurrent._vvv1.setTextSize(14.0f);
        ButtonWrapper buttonWrapper = mostCurrent._vvv1;
        TypefaceWrapper typefaceWrapper2 = Common.Typeface;
        buttonWrapper.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        ButtonWrapper buttonWrapper2 = mostCurrent._vvv1;
        Colors colors4 = Common.Colors;
        buttonWrapper2.setTextColor(-1);
        ButtonWrapper buttonWrapper3 = mostCurrent._vvv1;
        Gravity gravity6 = Common.Gravity;
        buttonWrapper3.setGravity(17);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv1.getObject(), Common.PerXToCurrent(69.0f, mostCurrent.activityBA), Common.DipToCurrent(100), Common.PerXToCurrent(26.0f, mostCurrent.activityBA), Common.DipToCurrent(32));
        ColorDrawable colorDrawable2 = new ColorDrawable();
        colorDrawable2.Initialize2(-16750900, Common.DipToCurrent(10), 0, 0);
        mostCurrent._vvv1.setBackground(colorDrawable2.getObject());
        mostCurrent._vvv1.setPadding(new int[]{0, 0, 0, 0});
        mostCurrent._vvv2 = _vvv3("XX:XX:XX:XX:XX:XX");
        mostCurrent._vvv2.setTextSize(14.0f);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv2.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(140), Common.PerXToCurrent(90.0f, mostCurrent.activityBA), Common.DipToCurrent(40));
        ColorDrawable colorDrawable3 = new ColorDrawable();
        colorDrawable3.Initialize2(-15658735, Common.DipToCurrent(8), Common.DipToCurrent(1), -12303292);
        mostCurrent._vvv2.setBackground(colorDrawable3.getObject());
        mostCurrent._vvv2.setPadding(new int[]{Common.DipToCurrent(15), 0, 0, 0});
        File file2 = Common.File;
        File file3 = Common.File;
        if (File.Exists(File.getDirInternal(), "mac.txt")) {
            EditTextWrapper editTextWrapper = mostCurrent._vvv2;
            File file4 = Common.File;
            File file5 = Common.File;
            editTextWrapper.setText(BA.ObjectToCharSequence(File.ReadString(File.getDirInternal(), "mac.txt")));
        }
        new LabelWrapper();
        mostCurrent._vv4.getPanel().AddView((View) _vv7("CHECK INTERVAL", -3355444, 14, false).getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(195), Common.PerXToCurrent(85.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        mostCurrent._vvv4 = _vvv5("imgInfoInterval");
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv4.getObject(), Common.PerXToCurrent(90.0f, mostCurrent.activityBA) - Common.DipToCurrent(38), Common.DipToCurrent(181), Common.DipToCurrent(35), Common.DipToCurrent(35));
        main mainVar9 = mostCurrent;
        mainVar9._vvv6.Initialize(mainVar9.activityBA, "spnInterval");
        SpinnerWrapper spinnerWrapper = mostCurrent._vvv6;
        Colors colors5 = Common.Colors;
        spinnerWrapper.setTextColor(-1);
        mostCurrent._vvv6.setTextSize(14.0f);
        mostCurrent._vvv6.setDropdownBackgroundColor(-13421773);
        SpinnerWrapper spinnerWrapper2 = mostCurrent._vvv6;
        Colors colors6 = Common.Colors;
        spinnerWrapper2.setDropdownTextColor(-1);
        mostCurrent._vvv6.Add("500ms (Very Fast)");
        mostCurrent._vvv6.Add("1000ms (Default)");
        mostCurrent._vvv6.Add("2000ms (Relaxed)");
        mostCurrent._vvv6.Add("5000ms (Battery Saver)");
        mostCurrent._vvv6.setBackground(colorDrawable3.getObject());
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv6.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(220), Common.PerXToCurrent(90.0f, mostCurrent.activityBA), Common.DipToCurrent(40));
        File file6 = Common.File;
        File file7 = Common.File;
        if (File.Exists(File.getDirInternal(), "interval.txt")) {
            SpinnerWrapper spinnerWrapper3 = mostCurrent._vvv6;
            File file8 = Common.File;
            File file9 = Common.File;
            spinnerWrapper3.setSelectedIndex((int) Double.parseDouble(File.ReadString(File.getDirInternal(), "interval.txt")));
        } else {
            mostCurrent._vvv6.setSelectedIndex(1);
        }
        new LabelWrapper();
        mostCurrent._vv4.getPanel().AddView((View) _vv7("RELEASE DELAY (Music Resume Speed)", -3355444, 14, false).getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(275), Common.PerXToCurrent(85.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        mostCurrent._vvv7 = _vvv5("imgInfoTimeout");
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv7.getObject(), Common.PerXToCurrent(90.0f, mostCurrent.activityBA) - Common.DipToCurrent(38), Common.DipToCurrent(261), Common.DipToCurrent(35), Common.DipToCurrent(35));
        main mainVar10 = mostCurrent;
        mainVar10._vvv0.Initialize(mainVar10.activityBA, "spnTimeout");
        SpinnerWrapper spinnerWrapper4 = mostCurrent._vvv0;
        Colors colors7 = Common.Colors;
        spinnerWrapper4.setTextColor(-1);
        mostCurrent._vvv0.setTextSize(14.0f);
        mostCurrent._vvv0.setDropdownBackgroundColor(-13421773);
        SpinnerWrapper spinnerWrapper5 = mostCurrent._vvv0;
        Colors colors8 = Common.Colors;
        spinnerWrapper5.setDropdownTextColor(-1);
        mostCurrent._vvv0.Add("1.5 Seconds (Fast)");
        mostCurrent._vvv0.Add("3.0 Seconds (Standard)");
        mostCurrent._vvv0.Add("5.0 Seconds (Default)");
        mostCurrent._vvv0.Add("10.0 Seconds (Very Safe)");
        mostCurrent._vvv0.setBackground(colorDrawable3.getObject());
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvv0.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(300), Common.PerXToCurrent(90.0f, mostCurrent.activityBA), Common.DipToCurrent(40));
        File file10 = Common.File;
        File file11 = Common.File;
        if (File.Exists(File.getDirInternal(), "timeout.txt")) {
            SpinnerWrapper spinnerWrapper6 = mostCurrent._vvv0;
            File file12 = Common.File;
            File file13 = Common.File;
            spinnerWrapper6.setSelectedIndex((int) Double.parseDouble(File.ReadString(File.getDirInternal(), "timeout.txt")));
        } else {
            mostCurrent._vvv0.setSelectedIndex(2);
        }
        main mainVar11 = mostCurrent;
        mainVar11._vvvv1.Initialize(mainVar11.activityBA, "btnSave");
        mostCurrent._vvvv1.setText(BA.ObjectToCharSequence("SAVE & RESTART SERVICE"));
        mostCurrent._vvvv1.setTextSize(14.0f);
        ButtonWrapper buttonWrapper4 = mostCurrent._vvvv1;
        TypefaceWrapper typefaceWrapper3 = Common.Typeface;
        buttonWrapper4.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        ButtonWrapper buttonWrapper5 = mostCurrent._vvvv1;
        Colors colors9 = Common.Colors;
        buttonWrapper5.setTextColor(-1);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvvv1.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(360), Common.PerXToCurrent(45.0f, mostCurrent.activityBA), Common.DipToCurrent(55));
        ColorDrawable colorDrawable4 = new ColorDrawable();
        colorDrawable4.Initialize2(-16750900, Common.DipToCurrent(25), 0, 0);
        mostCurrent._vvvv1.setBackground(colorDrawable4.getObject());
        main mainVar12 = mostCurrent;
        mainVar12._vvvv2.Initialize(mainVar12.activityBA, "btnStop");
        mostCurrent._vvvv2.setText(BA.ObjectToCharSequence("STOP SERVICE"));
        mostCurrent._vvvv2.setTextSize(14.0f);
        ButtonWrapper buttonWrapper6 = mostCurrent._vvvv2;
        TypefaceWrapper typefaceWrapper4 = Common.Typeface;
        buttonWrapper6.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        ButtonWrapper buttonWrapper7 = mostCurrent._vvvv2;
        Colors colors10 = Common.Colors;
        buttonWrapper7.setTextColor(-1);
        mostCurrent._vvvv2.setEnabled(false);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvvv2.getObject(), Common.PerXToCurrent(52.0f, mostCurrent.activityBA), Common.DipToCurrent(360), Common.PerXToCurrent(43.0f, mostCurrent.activityBA), Common.DipToCurrent(55));
        ColorDrawable colorDrawable5 = new ColorDrawable();
        colorDrawable5.Initialize2(-48060, Common.DipToCurrent(25), 0, 0);
        mostCurrent._vvvv2.setBackground(colorDrawable5.getObject());
        main mainVar13 = mostCurrent;
        mainVar13._vvvv3.Initialize(mainVar13.activityBA, "chkAutoStart");
        mostCurrent._vvvv3.setText(BA.ObjectToCharSequence("Auto-Start"));
        mostCurrent._vvvv3.setTextColor(-2236963);
        File file14 = Common.File;
        File file15 = Common.File;
        if (File.Exists(File.getDirInternal(), "autostart.txt")) {
            CompoundButtonWrapper.CheckBoxWrapper checkBoxWrapper = mostCurrent._vvvv3;
            File file16 = Common.File;
            File file17 = Common.File;
            checkBoxWrapper.setChecked(BA.ObjectToBoolean(File.ReadString(File.getDirInternal(), "autostart.txt")));
        } else {
            mostCurrent._vvvv3.setChecked(true);
        }
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvvv3.getObject(), Common.PerXToCurrent(5.0f, mostCurrent.activityBA), Common.DipToCurrent(430), Common.PerXToCurrent(45.0f, mostCurrent.activityBA), Common.DipToCurrent(40));
        mostCurrent._vvvv4 = _vv7("Foreground (always on)", -7829368, 12, false);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvvv4.getObject(), Common.PerXToCurrent(50.0f, mostCurrent.activityBA), Common.DipToCurrent(442), Common.PerXToCurrent(45.0f, mostCurrent.activityBA), Common.DipToCurrent(20));
        main mainVar14 = mostCurrent;
        mainVar14._vvvv5.Initialize(mainVar14.activityBA, "btnReadMe");
        mostCurrent._vvvv5.setText(BA.ObjectToCharSequence("Read Me!"));
        mostCurrent._vvvv5.setTextSize(16.0f);
        ButtonWrapper buttonWrapper8 = mostCurrent._vvvv5;
        TypefaceWrapper typefaceWrapper5 = Common.Typeface;
        buttonWrapper8.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        ButtonWrapper buttonWrapper9 = mostCurrent._vvvv5;
        Colors colors11 = Common.Colors;
        buttonWrapper9.setTextColor(-1);
        ButtonWrapper buttonWrapper10 = mostCurrent._vvvv5;
        Gravity gravity7 = Common.Gravity;
        buttonWrapper10.setGravity(17);
        mostCurrent._vv4.getPanel().AddView((View) mostCurrent._vvvv5.getObject(), Common.PerXToCurrent(25.0f, mostCurrent.activityBA), Common.DipToCurrent(480), Common.PerXToCurrent(50.0f, mostCurrent.activityBA), Common.DipToCurrent(50));
        ColorDrawable colorDrawable6 = new ColorDrawable();
        colorDrawable6.Initialize2(-9826899, Common.DipToCurrent(15), 0, 0);
        mostCurrent._vvvv5.setBackground(colorDrawable6.getObject());
        _logtoscreen("> App Started...");
        _v7.CheckAndRequest(processBA, "android.permission.BLUETOOTH_CONNECT");
        if (!_vvvv6.IsInitialized()) {
            _vvvv6.Initialize(processBA, "timerUI", 2000L);
        }
        _vvvv6.setEnabled(true);
        _vvvv7();
        _checkstatus();
        return "";
    }

    public static String _activity_pause(boolean z) throws Exception {
        if (_vvvv6.IsInitialized()) {
            _vvvv6.setEnabled(false);
            return "";
        }
        return "";
    }

    public static String _activity_permissionresult(String str, boolean z) throws Exception {
        if (str.equals("android.permission.BLUETOOTH_CONNECT")) {
            if (z) {
                _logtoscreen("> Bluetooth Granted.");
                _logtoscreen("> Requesting Audio Permission...");
                _v7.CheckAndRequest(processBA, RuntimePermissions.PERMISSION_RECORD_AUDIO);
                return "";
            }
            Common.ToastMessageShow(BA.ObjectToCharSequence("Bluetooth Permission Denied."), true);
            return "";
        }
        if (!str.equals(RuntimePermissions.PERMISSION_RECORD_AUDIO)) {
            return "";
        }
        if (z) {
            _logtoscreen("> Microphone Permission Granted.");
            _checkstatus();
            return "";
        }
        _logtoscreen("> Audio Permission Denied. Backup trigger disabled.");
        Common.ToastMessageShow(BA.ObjectToCharSequence("Audio Permission Denied. Detection may fail."), true);
        return "";
    }

    public static String _activity_resume() throws Exception {
        _checkstatus();
        if (_vvvv6.IsInitialized()) {
            _vvvv6.setEnabled(true);
            return "";
        }
        return "";
    }

    public static void _btnmenu_click() throws Exception {
        new ResumableSub_btnMenu_Click(null).resume(processBA, null);
    }

    public static class ResumableSub_btnMenu_Click extends BA.ResumableSub {
        main parent;
        List _options = null;
        int _index = 0;

        public ResumableSub_btnMenu_Click(main mainVar) {
            this.parent = mainVar;
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
                        this._options.Add("📋 View Diagnostic Log");
                        this._options.Add("🗑️ Delete Diagnostic Log");
                        break;
                    case 1:
                        this.state = 6;
                        if (main._vvvv0()) {
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
                        this.state = 11;
                        aggressivemode aggressivemodeVar = main.mostCurrent._vvvvv1;
                        if (aggressivemode._v5(main.mostCurrent.activityBA)) {
                            this.state = 8;
                        } else {
                            this.state = 10;
                        }
                        break;
                    case 8:
                        this.state = 11;
                        this._options.Add("Disable aggressive mode");
                        break;
                    case 10:
                        this.state = 11;
                        this._options.Add("Enable aggressive mode");
                        break;
                    case 11:
                        this.state = 12;
                        this._options.Add("❌ Cancel");
                        Common.InputListAsync(this._options, BA.ObjectToCharSequence("Menu"), -1, main.processBA, false);
                        Common.WaitFor("inputlist_result", main.processBA, this, null);
                        this.state = 22;
                        return;
                    case 12:
                        this.state = 21;
                        int i = this._index;
                        if (i == 0) {
                            this.state = 14;
                        } else if (i == 1) {
                            this.state = 16;
                        } else if (i == 2) {
                            this.state = 18;
                        } else if (i == 3) {
                            this.state = 20;
                        }
                        break;
                    case 14:
                        this.state = 21;
                        main._mnudiagview_click();
                        break;
                    case 16:
                        this.state = 21;
                        main._mnudiagdelete_click();
                        break;
                    case 18:
                        this.state = 21;
                        main._mnudiagtoggle_click();
                        break;
                    case 20:
                        this.state = 21;
                        main._mnuaggressivetoggle_click();
                        break;
                    case 21:
                        this.state = -1;
                        break;
                    case 22:
                        this.state = 12;
                        this._index = ((Integer) objArr[0]).intValue();
                        break;
                }
            }
        }
    }

    public static String _btnreadme_click() throws Exception {
        BA ba = processBA;
        readmeviewer readmeviewerVar = mostCurrent._vvvvv2;
        Common.StartActivity(ba, readmeviewer.getObject());
        return "";
    }

    public static void _btnsave_click() throws Exception {
        new ResumableSub_btnSave_Click(null).resume(processBA, null);
    }

    public static class ResumableSub_btnSave_Click extends BA.ResumableSub {
        main parent;
        String _mac = "";
        boolean _macvalid = false;
        boolean _deviceconnected = false;

        public ResumableSub_btnSave_Click(main mainVar) {
            this.parent = mainVar;
        }

        @Override // anywheresoftware.b4a.BA.ResumableSub
        public void resume(BA ba, Object[] objArr) throws Exception {
            while (true) {
                switch (this.state) {
                    case -1:
                        return;
                    case 0:
                        this.state = 1;
                        Common.LogImpl("41376257", "btnSave_Click triggered", 0);
                        this._mac = main.mostCurrent._vvv2.getText().trim().toUpperCase();
                        Regex regex = Common.Regex;
                        this._macvalid = Regex.IsMatch("^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$", this._mac);
                        Common.LogImpl("41376262", "MAC validation result: " + BA.ObjectToString(Boolean.valueOf(this._macvalid)), 0);
                        break;
                    case 1:
                        this.state = 18;
                        if (this._macvalid) {
                            this.state = 3;
                        } else {
                            this.state = 17;
                        }
                        break;
                    case 3:
                        this.state = 4;
                        File file = Common.File;
                        File file2 = Common.File;
                        File.WriteString(File.getDirInternal(), "mac.txt", this._mac);
                        File file3 = Common.File;
                        File file4 = Common.File;
                        File.WriteString(File.getDirInternal(), "interval.txt", BA.NumberToString(main.mostCurrent._vvv6.getSelectedIndex()));
                        File file5 = Common.File;
                        File file6 = Common.File;
                        File.WriteString(File.getDirInternal(), "timeout.txt", BA.NumberToString(main.mostCurrent._vvv0.getSelectedIndex()));
                        File file7 = Common.File;
                        File file8 = Common.File;
                        File.WriteString(File.getDirInternal(), "autostart.txt", BA.ObjectToString(Boolean.valueOf(main.mostCurrent._vvvv3.getChecked())));
                        main._vvvvv3("[USER] Settings saved: interval=" + BA.NumberToString(main.mostCurrent._vvv6.getSelectedIndex()) + " timeout=" + BA.NumberToString(main.mostCurrent._vvv0.getSelectedIndex()) + " autostart=" + BA.ObjectToString(Boolean.valueOf(main.mostCurrent._vvvv3.getChecked())));
                        main._logtoscreen("> Settings Saved.");
                        this._deviceconnected = main._vvvvv4(this._mac);
                        StringBuilder sb = new StringBuilder("Device connected check: ");
                        sb.append(BA.ObjectToString(Boolean.valueOf(this._deviceconnected)));
                        Common.LogImpl("41376280", sb.toString(), 0);
                        break;
                    case 4:
                        this.state = 15;
                        if (this._deviceconnected) {
                            this.state = 6;
                        } else {
                            this.state = 8;
                        }
                        break;
                    case 6:
                        this.state = 15;
                        main._logtoscreen("> Device is online. Restarting Service...");
                        BA ba2 = main.processBA;
                        audioservice audioserviceVar = main.mostCurrent._vvvvv5;
                        Common.StopService(ba2, audioservice.getObject());
                        Common.Sleep(main.mostCurrent.activityBA, this, 500);
                        this.state = 19;
                        return;
                    case 8:
                        this.state = 9;
                        break;
                    case 9:
                        this.state = 14;
                        if (main.mostCurrent._vvvv3.getChecked()) {
                            this.state = 11;
                        } else {
                            this.state = 13;
                        }
                        break;
                    case 11:
                        this.state = 14;
                        main._logtoscreen("> Device not found. Service waiting for device...");
                        BA ba3 = main.processBA;
                        audioservice audioserviceVar2 = main.mostCurrent._vvvvv5;
                        Common.StartService(ba3, audioservice.getObject());
                        Common.LogImpl("41376292", "Service started (waiting for device)", 0);
                        break;
                    case 13:
                        this.state = 14;
                        main._logtoscreen("> Device not found. Auto-Start is OFF.");
                        Common.LogImpl("41376295", "Service not started (auto-start off)", 0);
                        break;
                    case 14:
                        this.state = 15;
                        break;
                    case 15:
                        this.state = 18;
                        main._checkstatus();
                        break;
                    case 17:
                        this.state = 18;
                        Common.LogImpl("41376301", "Invalid MAC format: " + this._mac, 0);
                        Common.MsgboxAsync(BA.ObjectToCharSequence("Invalid MAC format. Use XX:XX:XX:XX:XX:XX"), BA.ObjectToCharSequence("Error"), main.processBA);
                        break;
                    case 18:
                        this.state = -1;
                        break;
                    case 19:
                        this.state = 15;
                        BA ba4 = main.processBA;
                        audioservice audioserviceVar3 = main.mostCurrent._vvvvv5;
                        Common.StartService(ba4, audioservice.getObject());
                        Common.LogImpl("41376287", "Service restarted", 0);
                        break;
                }
            }
        }
    }

    public static void _btnscan_click() throws Exception {
        new ResumableSub_btnScan_Click(null).resume(processBA, null);
    }

    public static class ResumableSub_btnScan_Click extends BA.ResumableSub {
        BA.IterableList group11;
        int groupLen11;
        int index11;
        main parent;
        Map _paireddevices = null;
        List _names = null;
        String _displayname = "";
        int _index = 0;
        String _selecteddisplay = "";
        String _mac = "";

        public ResumableSub_btnScan_Click(main mainVar) {
            this.parent = mainVar;
        }

        @Override // anywheresoftware.b4a.BA.ResumableSub
        public void resume(BA ba, Object[] objArr) throws Exception {
            while (true) {
                switch (this.state) {
                    case -1:
                        return;
                    case 0:
                        this.state = 1;
                        main._logtoscreen("> Scanning paired devices...");
                        this._paireddevices = new Map();
                        this._paireddevices = main._vvvvv6();
                        break;
                    case 1:
                        this.state = 4;
                        if (this._paireddevices.getSize() == 0) {
                            this.state = 3;
                        }
                        break;
                    case 3:
                        this.state = 4;
                        Common.ToastMessageShow(BA.ObjectToCharSequence("No paired devices found."), true);
                        main._logtoscreen("> No paired devices found.");
                        return;
                    case 4:
                        this.state = 5;
                        List list = new List();
                        this._names = list;
                        list.Initialize();
                        this._names.Add("❌ CANCEL / EXIT");
                        break;
                    case 5:
                        this.state = 8;
                        BA.IterableList iterableListKeys = this._paireddevices.Keys();
                        this.group11 = iterableListKeys;
                        this.index11 = 0;
                        this.groupLen11 = iterableListKeys.getSize();
                        this.state = 15;
                        break;
                    case 7:
                        this.state = 16;
                        this._names.Add(this._displayname);
                        break;
                    case 8:
                        this.state = 9;
                        Common.InputListAsync(this._names, BA.ObjectToCharSequence("Select Device (or tap Cancel)"), -1, main.processBA, false);
                        Common.WaitFor("inputlist_result", main.processBA, this, null);
                        this.state = 17;
                        return;
                    case 9:
                        this.state = 14;
                        if (this._index > 0) {
                            this.state = 11;
                        } else {
                            this.state = 13;
                        }
                        break;
                    case 11:
                        this.state = 14;
                        String strObjectToString = BA.ObjectToString(this._names.Get(this._index));
                        this._selecteddisplay = strObjectToString;
                        this._mac = BA.ObjectToString(this._paireddevices.Get(strObjectToString));
                        main.mostCurrent._vvv2.setText(BA.ObjectToCharSequence(this._mac));
                        main._logtoscreen("> Selected: " + this._selecteddisplay);
                        break;
                    case 13:
                        this.state = 14;
                        main._logtoscreen("> Scan cancelled.");
                        break;
                    case 14:
                        this.state = -1;
                        break;
                    case 15:
                        this.state = 8;
                        if (this.index11 < this.groupLen11) {
                            this.state = 7;
                            this._displayname = BA.ObjectToString(this.group11.Get(this.index11));
                        }
                        break;
                    case 16:
                        this.state = 15;
                        this.index11++;
                        break;
                    case 17:
                        this.state = 9;
                        this._index = ((Integer) objArr[0]).intValue();
                        break;
                }
            }
        }
    }

    public static void _btnstop_click() throws Exception {
        new ResumableSub_btnStop_Click(null).resume(processBA, null);
    }

    public static class ResumableSub_btnStop_Click extends BA.ResumableSub {
        main parent;
        List _options = null;
        int _index = 0;

        public ResumableSub_btnStop_Click(main mainVar) {
            this.parent = mainVar;
        }

        @Override // anywheresoftware.b4a.BA.ResumableSub
        public void resume(BA ba, Object[] objArr) throws Exception {
            while (true) {
                switch (this.state) {
                    case -1:
                        return;
                    case 0:
                        this.state = 1;
                        Common.LogImpl("41310721", "btnStop_Click triggered", 0);
                        break;
                    case 1:
                        this.state = 8;
                        if (!main._v0) {
                            this.state = 3;
                        }
                        break;
                    case 3:
                        this.state = 4;
                        break;
                    case 4:
                        this.state = 7;
                        File file = Common.File;
                        File file2 = Common.File;
                        if (!File.Exists(File.getDirInternal(), "mac.txt")) {
                            this.state = 6;
                        }
                        break;
                    case 6:
                        this.state = 7;
                        Common.MsgboxAsync(BA.ObjectToCharSequence("No device saved. Please scan and save a device first."), BA.ObjectToCharSequence("Start Service"), main.processBA);
                        return;
                    case 7:
                        this.state = 8;
                        main._logtoscreen("> Starting Service...");
                        BA ba2 = main.processBA;
                        audioservice audioserviceVar = main.mostCurrent._vvvvv5;
                        Common.StartService(ba2, audioservice.getObject());
                        Common.LogImpl("41310730", "Service start requested", 0);
                        main._checkstatus();
                        return;
                    case 8:
                        this.state = 9;
                        List list = new List();
                        this._options = list;
                        list.Initialize();
                        this._options.Add("Stop & Disable Auto-Start");
                        this._options.Add("Stop Only");
                        this._options.Add("❌ CANCEL");
                        Common.InputListAsync(this._options, BA.ObjectToCharSequence("Stop Service"), -1, main.processBA, false);
                        Common.WaitFor("inputlist_result", main.processBA, this, null);
                        this.state = 17;
                        return;
                    case 9:
                        this.state = 16;
                        int i = this._index;
                        if (i == 0) {
                            this.state = 11;
                        } else if (i == 1) {
                            this.state = 13;
                        } else {
                            this.state = 15;
                        }
                        break;
                    case 11:
                        this.state = 16;
                        Common.LogImpl("41310754", "Executing: Stop & Disable Auto-Start", 0);
                        main._logtoscreen("> Stopping Service (Auto-Start disabled)...");
                        BA ba3 = main.processBA;
                        audioservice audioserviceVar2 = main.mostCurrent._vvvvv5;
                        Common.StopService(ba3, audioservice.getObject());
                        main.mostCurrent._vvvv3.setChecked(false);
                        File file3 = Common.File;
                        File file4 = Common.File;
                        File.WriteString(File.getDirInternal(), "autostart.txt", BA.ObjectToString(false));
                        main._logtoscreen("> Service Stopped. Auto-Start disabled.");
                        main._checkstatus();
                        break;
                    case 13:
                        this.state = 16;
                        Common.LogImpl("41310763", "Executing: Stop Only", 0);
                        main._logtoscreen("> Stopping Service...");
                        BA ba4 = main.processBA;
                        audioservice audioserviceVar3 = main.mostCurrent._vvvvv5;
                        Common.StopService(ba4, audioservice.getObject());
                        main._logtoscreen("> Service Stopped. Auto-Start setting unchanged.");
                        main._checkstatus();
                        break;
                    case 15:
                        this.state = 16;
                        Common.LogImpl("41310770", "User cancelled stop action", 0);
                        main._logtoscreen("> Stop cancelled.");
                        break;
                    case 16:
                        this.state = -1;
                        break;
                    case 17:
                        this.state = 9;
                        this._index = ((Integer) objArr[0]).intValue();
                        Common.LogImpl("41310746", "InputList result index: " + BA.NumberToString(this._index), 0);
                        Common.LogImpl("41310747", "Service stop action: " + BA.NumberToString(this._index), 0);
                        break;
                }
            }
        }
    }

    public static String _checkstatus() throws Exception {
        ColorDrawable colorDrawable = new ColorDrawable();
        if (_v0) {
            colorDrawable.Initialize2(-14983648, Common.DipToCurrent(15), 0, 0);
            mostCurrent._vv6.setText(BA.ObjectToCharSequence("STATUS: ACTIVE"));
            mostCurrent._vv6.setTextColor(-11751600);
            mostCurrent._vv0.setText(BA.ObjectToCharSequence("(Monitoring Audio...)"));
            mostCurrent._vvvv2.setEnabled(true);
            mostCurrent._vvvv2.setText(BA.ObjectToCharSequence("STOP SERVICE"));
            ColorDrawable colorDrawable2 = new ColorDrawable();
            colorDrawable2.Initialize2(-48060, Common.DipToCurrent(25), 0, 0);
            mostCurrent._vvvv2.setBackground(colorDrawable2.getObject());
        } else {
            colorDrawable.Initialize2(-14540254, Common.DipToCurrent(15), 0, 0);
            mostCurrent._vv6.setText(BA.ObjectToCharSequence("STATUS: WAITING"));
            mostCurrent._vv6.setTextColor(-7829368);
            mostCurrent._vv0.setText(BA.ObjectToCharSequence("(Listening for Bluetooth...)"));
            mostCurrent._vvvv2.setEnabled(true);
            mostCurrent._vvvv2.setText(BA.ObjectToCharSequence("START SERVICE"));
            ColorDrawable colorDrawable3 = new ColorDrawable();
            colorDrawable3.Initialize2(-16750900, Common.DipToCurrent(25), 0, 0);
            mostCurrent._vvvv2.setBackground(colorDrawable3.getObject());
        }
        mostCurrent._vv5.setBackground(colorDrawable.getObject());
        return "";
    }

    public static EditTextWrapper _vvv3(String str) throws Exception {
        EditTextWrapper editTextWrapper = new EditTextWrapper();
        editTextWrapper.Initialize(mostCurrent.activityBA, "");
        editTextWrapper.setHint(str);
        editTextWrapper.setInputType(1);
        Colors colors = Common.Colors;
        editTextWrapper.setTextColor(-1);
        editTextWrapper.setHintColor(-5592406);
        editTextWrapper.setColor(-13421773);
        return editTextWrapper;
    }

    public static LabelWrapper _vvv5(String str) throws Exception {
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.Initialize(mostCurrent.activityBA, str);
        labelWrapper.setText(BA.ObjectToCharSequence("?"));
        labelWrapper.setTextColor(-16750900);
        labelWrapper.setTextSize(28.0f);
        TypefaceWrapper typefaceWrapper = Common.Typeface;
        labelWrapper.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        Gravity gravity = Common.Gravity;
        labelWrapper.setGravity(17);
        return labelWrapper;
    }

    public static LabelWrapper _vv7(String str, int i, int i2, boolean z) throws Exception {
        LabelWrapper labelWrapper = new LabelWrapper();
        labelWrapper.Initialize(mostCurrent.activityBA, "");
        labelWrapper.setText(BA.ObjectToCharSequence(str));
        labelWrapper.setTextColor(i);
        labelWrapper.setTextSize(i2);
        if (z) {
            Gravity gravity = Common.Gravity;
            labelWrapper.setGravity(17);
        }
        return labelWrapper;
    }

    public static String _vvvvv3(String str) throws Exception {
        if (!_vvvv0()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        DateTime dateTime = Common.DateTime;
        DateTime dateTime2 = Common.DateTime;
        sb.append(DateTime.Date(DateTime.getNow()));
        sb.append(" ");
        DateTime dateTime3 = Common.DateTime;
        DateTime dateTime4 = Common.DateTime;
        sb.append(DateTime.Time(DateTime.getNow()));
        String str2 = "[" + sb.toString() + "] " + str;
        try {
            File.TextWriterWrapper textWriterWrapper = new File.TextWriterWrapper();
            File file = Common.File;
            File file2 = Common.File;
            textWriterWrapper.Initialize(File.OpenOutput(File.getDirInternal(), _diag_file, true).getObject());
            textWriterWrapper.WriteLine(str2);
            textWriterWrapper.Close();
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("4851978", "DiagAppend error: " + Common.LastException(mostCurrent.activityBA).getMessage(), 0);
        }
        return "";
    }

    public static String _vvvvv7() throws Exception {
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

    public static String _vvvv7() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirInternal(), _diag_enabled_file)) {
            File file3 = Common.File;
            File file4 = Common.File;
            File.WriteString(File.getDirInternal(), _diag_enabled_file, BA.ObjectToString(false));
            return "";
        }
        return "";
    }

    public static boolean _vvvv0() throws Exception {
        _vvvv7();
        try {
            File file = Common.File;
            File file2 = Common.File;
            return File.ReadString(File.getDirInternal(), _diag_enabled_file).toLowerCase().equals("true");
        } catch (Exception e) {
            processBA.setLastException(e);
            return true;
        }
    }

    public static String _vvvvv0(boolean z) throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        File.WriteString(File.getDirInternal(), _diag_enabled_file, BA.ObjectToString(Boolean.valueOf(z)));
        return "";
    }

    public static String _vv2() throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeStatic("java.text.DateFormat");
            new JavaObject();
            JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getDateInstance", new Object[]{javaObject.GetField("SHORT")}));
            JavaObject javaObject3 = new JavaObject();
            javaObject3.InitializeNewInstance("java.util.Date", (Object[]) Common.Null);
            return BA.ObjectToString(javaObject2.RunMethod("format", new Object[]{javaObject3.getObject()}));
        } catch (Exception e) {
            processBA.setLastException(e);
            DateTime dateTime = Common.DateTime;
            DateTime dateTime2 = Common.DateTime;
            return DateTime.Date(DateTime.getNow());
        }
    }

    public static Map _vvvvv6() throws Exception {
        Map map = new Map();
        map.Initialize();
        try {
            JavaObject javaObject = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), new JavaObject().InitializeStatic("android.bluetooth.BluetoothAdapter").RunMethod("getDefaultAdapter", (Object[]) Common.Null));
            if (javaObject.IsInitialized()) {
                new JavaObject();
                JavaObject javaObject2 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), javaObject.RunMethod("getBondedDevices", (Object[]) Common.Null));
                if (javaObject2.IsInitialized()) {
                    Object[] objArr = (Object[]) javaObject2.RunMethod("toArray", (Object[]) Common.Null);
                    new JavaObject();
                    for (Object obj : objArr) {
                        JavaObject javaObject3 = (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), obj);
                        String strObjectToString = BA.ObjectToString(javaObject3.RunMethod("getName", (Object[]) Common.Null));
                        String strObjectToString2 = BA.ObjectToString(javaObject3.RunMethod("getAddress", (Object[]) Common.Null));
                        map.Put(strObjectToString + " (" + strObjectToString2 + ")", strObjectToString2);
                    }
                }
            }
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("4917525", "Error getting paired devices: " + BA.ObjectToString(Common.LastException(mostCurrent.activityBA)), 0);
        }
        return map;
    }

    public static String _globals() throws Exception {
        mostCurrent._vv5 = new PanelWrapper();
        mostCurrent._vv6 = new LabelWrapper();
        mostCurrent._vv0 = new LabelWrapper();
        mostCurrent._vvv2 = new EditTextWrapper();
        mostCurrent._vvv1 = new ButtonWrapper();
        mostCurrent._vvv6 = new SpinnerWrapper();
        mostCurrent._vvv0 = new SpinnerWrapper();
        mostCurrent._vvvv1 = new ButtonWrapper();
        mostCurrent._vvvv2 = new ButtonWrapper();
        mostCurrent._vvvv3 = new CompoundButtonWrapper.CheckBoxWrapper();
        mostCurrent._vvvv4 = new LabelWrapper();
        mostCurrent._vv1 = new ImageViewWrapper();
        mostCurrent._vvv4 = new LabelWrapper();
        mostCurrent._vvv7 = new LabelWrapper();
        mostCurrent._vv3 = new LabelWrapper();
        mostCurrent._vvvv5 = new ButtonWrapper();
        mostCurrent._vv4 = new ScrollViewWrapper();
        return "";
    }

    public static String _imginfointerval_click() throws Exception {
        Common.MsgboxAsync(BA.ObjectToCharSequence("How often the app checks for Google Assistant when you're not using it.\n\n• Faster = Quicker detection, but uses more battery\n• Slower = Better battery life, but may take longer to detect\n\nOnce Assistant is active, the app automatically checks very quickly (250ms) regardless of this setting.\n\nRecommended: Use 'Default' (1000ms) for most users."), BA.ObjectToCharSequence("Check Interval"), processBA);
        return "";
    }

    public static String _imginfotimeout_click() throws Exception {
        Common.MsgboxAsync(BA.ObjectToCharSequence((("How long the app keeps the microphone connection after Assistant finishes speaking.\n\nThis prevents Android Auto from interfering with your commands. For example, when you ask to play a new song, a longer delay gives the new song time to start before Android Auto tries to resume the old one.\n\n• Fast (1.5s) = Quick release, may not work for all commands\n• Standard (3s) = Faster release, may work for some setups\n• Default (5s) = Balanced protection, works for most cases\n") + "• Very Safe (10s) = Maximum protection for slower devices\n\n") + "Recommended: Use 'Default' (5 seconds) - this works best for most users."), BA.ObjectToCharSequence("Release Delay"), processBA);
        return "";
    }

    public static boolean _vvvvv4(String str) throws Exception {
        try {
            JavaObject javaObject = new JavaObject();
            javaObject.InitializeContext(processBA);
            JavaObject javaObjectRunMethodJO = javaObject.RunMethodJO("getSystemService", new Object[]{"audio"});
            new List();
            List list = (List) AbsObjectWrapper.ConvertToWrapper(new List(), (java.util.List) javaObjectRunMethodJO.RunMethod("getAvailableCommunicationDevices", (Object[]) Common.Null));
            new JavaObject();
            int size = list.getSize();
            for (int i = 0; i < size; i++) {
                if (BA.ObjectToString(((JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), list.Get(i))).RunMethod("getAddress", (Object[]) Common.Null)).equalsIgnoreCase(str)) {
                    return true;
                }
            }
        } catch (Exception e) {
            processBA.setLastException(e);
            Common.LogImpl("41441803", "Error checking device: " + BA.ObjectToString(Common.LastException(mostCurrent.activityBA)), 0);
        }
        return false;
    }

    public static String _logtoscreen(String str) throws Exception {
        _vvvvv3(str);
        Common.LogImpl("41572866", str, 0);
        return "";
    }

    public static String _mnuaggressivetoggle_click() throws Exception {
        main mainVar = mostCurrent;
        aggressivemode aggressivemodeVar = mainVar._vvvvv1;
        boolean z_v5 = aggressivemode._v5(mainVar.activityBA);
        main mainVar2 = mostCurrent;
        aggressivemode aggressivemodeVar2 = mainVar2._vvvvv1;
        aggressivemode._v6(mainVar2.activityBA, Common.Not(z_v5));
        if (z_v5) {
            Common.ToastMessageShow(BA.ObjectToCharSequence("Aggressive routing disabled."), true);
            return "";
        }
        Common.ToastMessageShow(BA.ObjectToCharSequence("Aggressive routing enabled."), true);
        return "";
    }

    public static String _mnudiagdelete_click() throws Exception {
        _vvvvv7();
        Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic log deleted."), true);
        return "";
    }

    public static String _mnudiagtoggle_click() throws Exception {
        boolean z_vvvv0 = _vvvv0();
        _vvvvv0(Common.Not(z_vvvv0));
        if (z_vvvv0) {
            Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic logging disabled."), true);
            return "";
        }
        Common.ToastMessageShow(BA.ObjectToCharSequence("Diagnostic logging enabled."), true);
        return "";
    }

    public static String _mnudiagview_click() throws Exception {
        _vvvvv3("Opening Diagnostic Log viewer");
        Common.StartActivity(processBA, "DiagLog");
        return "";
    }

    public static void initializeProcessGlobals() {
        if (processGlobalsRun) {
            return;
        }
        processGlobalsRun = true;
        try {
            _process_globals();
            starter._process_globals();
            audioservice._process_globals();
            btreceiver._process_globals();
            diaglog._process_globals();
            readmeviewer._process_globals();
            aggressivemode._process_globals();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String vvv13(byte[] bArr, int i) throws Exception {
        new Runnable(i, bArr) { // from class: com.tillekesoft.aamotomic.main.1
            final /* synthetic */ byte[] val$_b;
            final /* synthetic */ int val$i;

            @Override // java.lang.Runnable
            public void run() {
            }

            {
                this.val$i = i;
                this.val$_b = bArr;
                int i2 = (i / 3) + 540202;
                if (main.bb == null) {
                    byte[][] unused = main.bb = new byte[4][];
                    main.bb[0] = BA.packageName.getBytes("UTF8");
                    main.bb[1] = BA.applicationContext.getPackageManager().getPackageInfo(BA.packageName, 0).versionName.getBytes("UTF8");
                    if (main.bb[1].length == 0) {
                        main.bb[1] = "jsdkfh".getBytes("UTF8");
                    }
                    main.bb[2] = new byte[]{(byte) BA.applicationContext.getPackageManager().getPackageInfo(BA.packageName, 0).versionCode};
                }
                main.bb[3] = new byte[]{(byte) (i2 >>> 24), (byte) (i2 >>> 16), (byte) (i2 >>> 8), (byte) i2};
                for (int i3 = 0; i3 < 4; i3++) {
                    int i4 = 0;
                    while (true) {
                        try {
                            byte[] bArr2 = this.val$_b;
                            if (i4 < bArr2.length) {
                                bArr2[i4] = (byte) (bArr2[i4] ^ main.bb[i3][i4 % main.bb[i3].length]);
                                i4++;
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        return new String(bArr, "UTF8");
    }

    public static String _process_globals() throws Exception {
        _v7 = new RuntimePermissions();
        _vvvvvv1 = new B4XViewWrapper.XUI();
        _vvvv6 = new Timer();
        _v0 = false;
        _diag_file = vvv13(new byte[]{55, 40, 42, 7, ConnectorUtils.NULL, 53, 44, 82, 114, 38, 37, 64}, 173053);
        _diag_enabled_file = vvv13(new byte[]{55, 42, 120, 44, ConnectorUtils.NULL, 62, ByteCompanionObject.MAX_VALUE, ByteCompanionObject.MAX_VALUE, 62, 60, 106, 123, 113, 62, 120, 63}, 601475);
        return "";
    }

    public static String _timerui_tick() throws Exception {
        _checkstatus();
        return "";
    }
}
