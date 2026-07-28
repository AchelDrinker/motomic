package anywheresoftware.b4a.keywords;

import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;

/* JADX INFO: loaded from: classes.dex */
public class B4AApplication {
    private static CanvasWrapper.BitmapWrapper loadedIcon;

    public static String getLabelName() throws PackageManager.NameNotFoundException {
        return String.valueOf(BA.applicationContext.getPackageManager().getApplicationLabel(BA.applicationContext.getPackageManager().getApplicationInfo(BA.packageName, 0)));
    }

    public static String getVersionName() throws PackageManager.NameNotFoundException {
        return BA.applicationContext.getPackageManager().getPackageInfo(BA.packageName, 0).versionName;
    }

    public static int getVersionCode() throws PackageManager.NameNotFoundException {
        return BA.applicationContext.getPackageManager().getPackageInfo(BA.packageName, 0).versionCode;
    }

    public static String getPackageName() {
        return BA.packageName;
    }

    public static CanvasWrapper.BitmapWrapper getIcon() throws PackageManager.NameNotFoundException {
        CanvasWrapper.BitmapWrapper bitmapWrapper = loadedIcon;
        if (bitmapWrapper != null) {
            return bitmapWrapper;
        }
        loadedIcon = new CanvasWrapper.BitmapWrapper();
        Drawable applicationIcon = BA.applicationContext.getPackageManager().getApplicationIcon(BA.packageName);
        if (applicationIcon instanceof BitmapDrawable) {
            loadedIcon.setObject(((BitmapDrawable) applicationIcon).getBitmap());
        } else {
            loadedIcon.InitializeMutable(Common.DipToCurrent(108), Common.DipToCurrent(108));
            CanvasWrapper canvasWrapper = new CanvasWrapper();
            canvasWrapper.Initialize2(loadedIcon.getObject());
            canvasWrapper.DrawDrawable(applicationIcon, new Rect(0, 0, loadedIcon.getWidth(), loadedIcon.getHeight()));
        }
        return loadedIcon;
    }
}
