package anywheresoftware.b4a.keywords.constants;

import android.graphics.Typeface;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Typeface")
public class TypefaceWrapper extends AbsObjectWrapper<Typeface> {
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_BOLD_ITALIC = 3;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_NORMAL = 0;
    public static final Typeface DEFAULT = Typeface.DEFAULT;
    public static final Typeface DEFAULT_BOLD = Typeface.DEFAULT_BOLD;
    public static final Typeface SANS_SERIF = Typeface.SANS_SERIF;
    public static final Typeface SERIF = Typeface.SERIF;
    public static final Typeface MONOSPACE = Typeface.MONOSPACE;

    public static Typeface CreateNew(Typeface typeface, int i) {
        return Typeface.create(typeface, i);
    }

    public static Typeface LoadFromAssets(String str) throws IOException {
        if (File.virtualAssetsFolder != null) {
            return Typeface.createFromFile(new java.io.File(File.virtualAssetsFolder, File.getUnpackedVirtualAssetFile(str)));
        }
        return Typeface.createFromAsset(BA.applicationContext.getAssets(), str.toLowerCase(BA.cul));
    }

    public static Typeface getFONTAWESOME() {
        return TextViewWrapper.getTypeface(TextViewWrapper.fontAwesomeFile);
    }

    public static Typeface getMATERIALICONS() {
        return TextViewWrapper.getTypeface(TextViewWrapper.materialIconsFile);
    }
}
