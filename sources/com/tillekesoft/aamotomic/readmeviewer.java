package com.tillekesoft.aamotomic;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.B4AMenuItem;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.Msgbox;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.Regex;
import anywheresoftware.b4a.keywords.StringBuilderWrapper;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ScrollViewWrapper;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class readmeviewer extends Activity implements B4AActivity {
    public static String _readme_file = "";
    static boolean afterFirstLayout = false;
    public static boolean dontPause = false;
    public static final boolean fullScreen = true;
    public static final boolean includeTitle = false;
    static boolean isFirst = true;
    public static readmeviewer mostCurrent = null;
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
    public ScrollViewWrapper _vvvvvvvvvvvvvvv2 = null;
    public LabelWrapper _vvvvvvvvvvvvvvv3 = null;
    public ImageViewWrapper _vv1 = null;
    public LabelWrapper _vvvvvvvvvvvvv1 = null;
    public main _vvvvvv5 = null;
    public starter _vvvvvv2 = null;
    public audioservice _vvvvv5 = null;
    public btreceiver _vvvvvv3 = null;
    public diaglog _vvvvvv4 = null;
    public aggressivemode _vvvvv1 = null;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        Activity activity;
        super.onCreate(bundle);
        mostCurrent = this;
        if (processBA == null) {
            BA ba = new BA(getApplicationContext(), (BALayout) null, (BA) null, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.readmeviewer");
            processBA = ba;
            ba.loadHtSubs(getClass());
            BALayout.setDeviceScale(getApplicationContext().getResources().getDisplayMetrics().density);
        } else {
            WeakReference<Activity> weakReference = previousOne;
            if (weakReference != null && (activity = weakReference.get()) != null && activity != this) {
                BA.LogInfo("Killing previous instance (readmeviewer).");
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
            if (readmeviewer.afterFirstLayout || readmeviewer.mostCurrent == null) {
                return;
            }
            if (readmeviewer.mostCurrent.layout.getWidth() == 0) {
                BA.handler.postDelayed(this, 5L);
                return;
            }
            readmeviewer.mostCurrent.layout.getLayoutParams().height = readmeviewer.mostCurrent.layout.getHeight();
            readmeviewer.mostCurrent.layout.getLayoutParams().width = readmeviewer.mostCurrent.layout.getWidth();
            readmeviewer.afterFirstLayout = true;
            readmeviewer.mostCurrent.afterFirstLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void afterFirstLayout() {
        if (this != mostCurrent) {
            return;
        }
        this.activityBA = new BA(this, this.layout, processBA, "com.tillekesoft.aamotomic", "com.tillekesoft.aamotomic.readmeviewer");
        processBA.sharedProcessBA.activityBA = new WeakReference<>(this.activityBA);
        ViewWrapper.lastId = 0;
        this._activity = new ActivityWrapper(this.activityBA, "activity");
        Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
            if (isFirst) {
                processBA.raiseEvent2(null, true, "SHELL", false, new Object[0]);
            }
            BA ba = processBA;
            ba.raiseEvent2(null, true, "CREATE", true, "com.tillekesoft.aamotomic.readmeviewer", ba, this.activityBA, this._activity, Float.valueOf(Common.Density), mostCurrent);
            this._activity.reinitializeForShell(this.activityBA, "activity");
        }
        initializeProcessGlobals();
        initializeGlobals();
        StringBuilder sb = new StringBuilder("** Activity (readmeviewer) Create ");
        sb.append(isFirst ? "(first time)" : "");
        sb.append(" **");
        BA.LogInfo(sb.toString());
        processBA.raiseEvent2(null, true, "activity_create", false, Boolean.valueOf(isFirst));
        isFirst = false;
        if (this != mostCurrent) {
            return;
        }
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (readmeviewer) Resume **");
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
            readmeviewer.processBA.raiseEventFromUI(menuItem.getTitle(), this.eventName + "_click", new Object[0]);
            return true;
        }
    }

    public static Class<?> getObject() {
        return readmeviewer.class;
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
            Boolean bool = (Boolean) readmeviewer.processBA.raiseEvent2(readmeviewer.this._activity, false, "activity_keypress", false, Integer.valueOf(i));
            if (bool == null || bool.booleanValue()) {
                return true;
            }
            if (i != 4) {
                return false;
            }
            readmeviewer.this.finish();
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
                BA.LogInfo("** Activity (readmeviewer) Pause, UserClosed = " + this.activityBA.activity.isFinishing() + " **");
            } else {
                BA.LogInfo("** Activity (readmeviewer) Pause event (activity is not paused). **");
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
            readmeviewer readmeviewerVar = readmeviewer.mostCurrent;
            if (readmeviewerVar == null || readmeviewerVar != this.activity.get()) {
                return;
            }
            readmeviewer.processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (readmeviewer) Resume **");
            if (readmeviewerVar != readmeviewer.mostCurrent) {
                return;
            }
            readmeviewer.processBA.raiseEvent(readmeviewerVar._activity, "activity_resume", null);
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
        readmeviewer readmeviewerVar = mostCurrent;
        readmeviewerVar._vvvvvvvvvvvvv1.Initialize(readmeviewerVar.activityBA, "btnBack");
        mostCurrent._vvvvvvvvvvvvv1.setText(BA.ObjectToCharSequence("←"));
        mostCurrent._vvvvvvvvvvvvv1.setTextSize(32.0f);
        LabelWrapper labelWrapper = mostCurrent._vvvvvvvvvvvvv1;
        Colors colors = Common.Colors;
        labelWrapper.setTextColor(-1);
        LabelWrapper labelWrapper2 = mostCurrent._vvvvvvvvvvvvv1;
        Gravity gravity = Common.Gravity;
        labelWrapper2.setGravity(17);
        panelWrapper.AddView((View) mostCurrent._vvvvvvvvvvvvv1.getObject(), Common.DipToCurrent(5), Common.DipToCurrent(10), Common.DipToCurrent(50), Common.DipToCurrent(50));
        readmeviewer readmeviewerVar2 = mostCurrent;
        readmeviewerVar2._vv1.Initialize(readmeviewerVar2.activityBA, "imgIcon");
        panelWrapper.AddView((View) mostCurrent._vv1.getObject(), Common.DipToCurrent(60), Common.DipToCurrent(10), Common.DipToCurrent(50), Common.DipToCurrent(50));
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
        labelWrapper3.setText(BA.ObjectToCharSequence("Read Me"));
        labelWrapper3.setTextSize(22.0f);
        TypefaceWrapper typefaceWrapper = Common.Typeface;
        labelWrapper3.setTypeface(TypefaceWrapper.DEFAULT_BOLD);
        Colors colors3 = Common.Colors;
        labelWrapper3.setTextColor(-1);
        Gravity gravity2 = Common.Gravity;
        Gravity gravity3 = Common.Gravity;
        labelWrapper3.setGravity(19);
        panelWrapper.AddView((View) labelWrapper3.getObject(), Common.DipToCurrent(120), Common.DipToCurrent(10), Common.PerXToCurrent(60.0f, mostCurrent.activityBA), Common.DipToCurrent(50));
        readmeviewer readmeviewerVar3 = mostCurrent;
        readmeviewerVar3._vvvvvvvvvvvvvvv2.Initialize(readmeviewerVar3.activityBA, Common.DipToCurrent(10000));
        readmeviewer readmeviewerVar4 = mostCurrent;
        readmeviewerVar4._activity.AddView((View) readmeviewerVar4._vvvvvvvvvvvvvvv2.getObject(), 0, Common.DipToCurrent(75), Common.PerXToCurrent(100.0f, mostCurrent.activityBA), Common.PerYToCurrent(100.0f, mostCurrent.activityBA) - Common.DipToCurrent(75));
        mostCurrent._vvvvvvvvvvvvvvv2.getPanel().setColor(-16777216);
        readmeviewer readmeviewerVar5 = mostCurrent;
        readmeviewerVar5._vvvvvvvvvvvvvvv3.Initialize(readmeviewerVar5.activityBA, "");
        mostCurrent._vvvvvvvvvvvvvvv3.setColor(-16777216);
        mostCurrent._vvvvvvvvvvvvvvv3.setTextColor(-2236963);
        mostCurrent._vvvvvvvvvvvvvvv3.setTextSize(14.0f);
        LabelWrapper labelWrapper4 = mostCurrent._vvvvvvvvvvvvvvv3;
        Gravity gravity4 = Common.Gravity;
        Gravity gravity5 = Common.Gravity;
        labelWrapper4.setGravity(51);
        mostCurrent._vvvvvvvvvvvvvvv3.setText(BA.ObjectToCharSequence(_vvvvvvvvvvvvvvv5(_vvvvvvvvvvvvvvv4())));
        mostCurrent._vvvvvvvvvvvvvvv2.getPanel().AddView((View) mostCurrent._vvvvvvvvvvvvvvv3.getObject(), Common.DipToCurrent(10), Common.DipToCurrent(10), mostCurrent._vvvvvvvvvvvvvvv2.getWidth() - Common.DipToCurrent(20), Common.DipToCurrent(9000));
        return "";
    }

    public static String _activity_pause(boolean z) throws Exception {
        return "";
    }

    public static String _activity_resume() throws Exception {
        return "";
    }

    public static String _btnback_click() throws Exception {
        mostCurrent._activity.Finish();
        return "";
    }

    public static String _globals() throws Exception {
        mostCurrent._vvvvvvvvvvvvvvv2 = new ScrollViewWrapper();
        mostCurrent._vvvvvvvvvvvvvvv3 = new LabelWrapper();
        mostCurrent._vv1 = new ImageViewWrapper();
        mostCurrent._vvvvvvvvvvvvv1 = new LabelWrapper();
        return "";
    }

    public static String _vvvvvvvvvvvvvvv4() throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirAssets(), _readme_file)) {
            return "README.md file not found.\n\nPlease ensure README.md is in the Files folder of the project.";
        }
        File file3 = Common.File;
        File file4 = Common.File;
        String strReadString = File.ReadString(File.getDirAssets(), _readme_file);
        int iIndexOf = strReadString.indexOf("## Technical Details");
        if (iIndexOf > 0) {
            return strReadString.substring(0, iIndexOf).trim();
        }
        return strReadString.trim();
    }

    public static String _process_globals() throws Exception {
        _readme_file = main.vvv13(new byte[]{1, 0, 69, -19, 30, 24, 34, -111, 56}, 997289);
        return "";
    }

    public static String _vvvvvvvvvvvvvvv5(String str) throws Exception {
        String strReplace = str.replace(Common.CRLF, BA.ObjectToString(Character.valueOf(Common.Chr(10)))).replace(BA.ObjectToString(Character.valueOf(Common.Chr(13))), "");
        Regex regex = Common.Regex;
        String[] strArrSplit = Regex.Split(BA.ObjectToString(Character.valueOf(Common.Chr(10))), strReplace);
        StringBuilderWrapper stringBuilderWrapper = new StringBuilderWrapper();
        stringBuilderWrapper.Initialize();
        int length = strArrSplit.length - 1;
        for (int i = 0; i <= length; i++) {
            String strSubstring = strArrSplit[i];
            while (strSubstring.length() > 0 && strSubstring.startsWith("#")) {
                strSubstring = strSubstring.substring(1);
            }
            String strReplace2 = strSubstring.trim().replace("**", "").replace("`", "").replace("✅", "- ");
            if (i > 0) {
                stringBuilderWrapper.Append(Common.CRLF);
            }
            stringBuilderWrapper.Append(strReplace2);
        }
        return stringBuilderWrapper.ToString();
    }
}
