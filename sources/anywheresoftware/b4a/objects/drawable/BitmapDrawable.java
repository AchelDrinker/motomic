package anywheresoftware.b4a.objects.drawable;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("BitmapDrawable")
public class BitmapDrawable extends AbsObjectWrapper<android.graphics.drawable.BitmapDrawable> {
    public void Initialize(Bitmap bitmap) {
        setObject(new android.graphics.drawable.BitmapDrawable(BA.applicationContext.getResources(), bitmap));
    }

    public Bitmap getBitmap() {
        return getObject().getBitmap();
    }

    public int getGravity() {
        return getObject().getGravity();
    }

    public void setGravity(int i) {
        getObject().setGravity(i);
    }

    public static Drawable build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws IOException {
        String dirAssets;
        String lowerCase = ((String) map.get("file")).toLowerCase(BA.cul);
        if (lowerCase.length() == 0) {
            return null;
        }
        if (z) {
            dirAssets = File.getDirInternal();
        } else {
            dirAssets = File.getDirAssets();
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        bitmapWrapper.Initialize(dirAssets, lowerCase);
        bitmapDrawable.Initialize(bitmapWrapper.getObject());
        Integer num = (Integer) map.get("gravity");
        if (num != null) {
            bitmapDrawable.getObject().setGravity(num.intValue());
        }
        return bitmapDrawable.getObject();
    }
}
