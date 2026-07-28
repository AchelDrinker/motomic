package anywheresoftware.b4a.phone;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.objects.collections.List;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("PackageManager")
public class PackageManagerWrapper {
    private PackageManager pm = BA.applicationContext.getPackageManager();

    public List GetInstalledPackages() {
        List list = new List();
        java.util.List<PackageInfo> installedPackages = this.pm.getInstalledPackages(0);
        list.Initialize();
        Iterator<PackageInfo> it = installedPackages.iterator();
        while (it.hasNext()) {
            list.Add(it.next().packageName);
        }
        return list;
    }

    public int GetVersionCode(String str) throws PackageManager.NameNotFoundException {
        return this.pm.getPackageInfo(str, 0).versionCode;
    }

    public String GetVersionName(String str) throws PackageManager.NameNotFoundException {
        return this.pm.getPackageInfo(str, 0).versionName;
    }

    public String GetApplicationLabel(String str) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = this.pm;
        CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0));
        return applicationLabel == null ? "" : applicationLabel.toString();
    }

    public IntentWrapper GetApplicationIntent(String str) {
        IntentWrapper intentWrapper = new IntentWrapper();
        intentWrapper.setObject(this.pm.getLaunchIntentForPackage(str));
        return intentWrapper;
    }

    public Drawable GetApplicationIcon(String str) throws PackageManager.NameNotFoundException {
        return this.pm.getApplicationIcon(str);
    }

    public List QueryIntentActivities(Intent intent) {
        java.util.List<ResolveInfo> listQueryIntentActivities = this.pm.queryIntentActivities(intent, 0);
        List list = new List();
        list.Initialize();
        for (ResolveInfo resolveInfo : listQueryIntentActivities) {
            list.Add(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name).flattenToShortString());
        }
        return list;
    }
}
