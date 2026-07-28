package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import anywheresoftware.b4a.BA;
import java.io.IOException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("ImageView")
public class ImageViewWrapper extends ViewWrapper<ImageView> {
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            setObject(new ImageView(ba.context));
        }
        super.innerInitialize(ba, str, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getGravity() {
        Drawable background = ((ImageView) getObject()).getBackground();
        if (background == null) {
            return 0;
        }
        if (background instanceof BitmapDrawable) {
            return ((BitmapDrawable) background).getGravity();
        }
        if (background instanceof ColorDrawable) {
            return background.getLevel();
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setGravity(int i) {
        BitmapDrawable background = ((ImageView) getObject()).getBackground();
        if (background == null || !(background instanceof BitmapDrawable)) {
            anywheresoftware.b4a.objects.drawable.BitmapDrawable bitmapDrawable = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
            bitmapDrawable.Initialize(null);
            ((ImageView) getObject()).setBackgroundDrawable(bitmapDrawable.getObject());
            background = bitmapDrawable.getObject();
        }
        ((BitmapDrawable) background).setGravity(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Bitmap getBitmap() {
        Drawable background = ((ImageView) getObject()).getBackground();
        if (background == null || !(background instanceof BitmapDrawable)) {
            return null;
        }
        return ((BitmapDrawable) background).getBitmap();
    }

    public void setBitmap(Bitmap bitmap) {
        SetBackgroundImage(bitmap);
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public anywheresoftware.b4a.objects.drawable.BitmapDrawable SetBackgroundImageNew(Bitmap bitmap) {
        int gravity = getGravity();
        anywheresoftware.b4a.objects.drawable.BitmapDrawable bitmapDrawable = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
        bitmapDrawable.Initialize(bitmap);
        bitmapDrawable.setGravity(gravity);
        setBackground(bitmapDrawable.getObject());
        return bitmapDrawable;
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void SetBackgroundImage(Bitmap bitmap) {
        SetBackgroundImageNew(bitmap);
    }

    public static void setImage(View view, HashMap<String, Object> map, boolean z) {
        try {
            Drawable drawableBuild = anywheresoftware.b4a.objects.drawable.BitmapDrawable.build(view, map, z, null);
            if (drawableBuild == null) {
                ColorDrawable colorDrawable = new ColorDrawable(-1);
                Integer num = (Integer) map.get("gravity");
                if (num == null) {
                    num = 0;
                }
                view.setBackgroundDrawable(colorDrawable);
                colorDrawable.setLevel(num.intValue());
                return;
            }
            view.setBackgroundDrawable(drawableBuild);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, ImageView.class, map, z);
        }
        ImageView imageView = (ImageView) ViewWrapper.build(obj, map, z);
        setImage(imageView, (HashMap) map.get("drawable"), z);
        return imageView;
    }
}
