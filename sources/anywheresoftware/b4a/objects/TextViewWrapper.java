package anywheresoftware.b4a.objects;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextViewWrapper<T extends TextView> extends ViewWrapper<T> implements LayoutBuilder.DesignerTextSizeMethod {
    private static final HashMap<String, Typeface> cachedTypefaces = new HashMap<>();
    public static String fontAwesomeFile = "b4x_fontawesome.otf";
    public static String materialIconsFile = "b4x_materialicons.ttf";

    public String getText() {
        return getObject().getText().toString();
    }

    public void setText(CharSequence charSequence) {
        getObject().setText(charSequence);
    }

    public void setText(Object obj) {
        setText(BA.ObjectToCharSequence(obj));
    }

    public void setTextColor(int i) {
        getObject().setTextColor(i);
    }

    public int getTextColor() {
        return getObject().getTextColors().getDefaultColor();
    }

    public void setEllipsize(String str) {
        getObject().setEllipsize(str.equals("NONE") ? null : TextUtils.TruncateAt.valueOf(str));
    }

    public String getEllipsize() {
        TextUtils.TruncateAt ellipsize = getObject().getEllipsize();
        return ellipsize == null ? "NONE" : ellipsize.toString();
    }

    public void setSingleLine(boolean z) {
        getObject().setSingleLine(z);
    }

    public void SetTextColorAnimated(int i, int i2) {
        if (Build.VERSION.SDK_INT >= 11 && i > 0) {
            final T object = getObject();
            final float[] fArr = new float[3];
            final float[] fArr2 = new float[3];
            int textColor = getTextColor();
            Color.colorToHSV(textColor, fArr);
            Color.colorToHSV(i2, fArr2);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(i);
            final float[] fArr3 = new float[3];
            final int iAlpha = Color.alpha(textColor);
            final int iAlpha2 = Color.alpha(i2);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: anywheresoftware.b4a.objects.TextViewWrapper.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float[] fArr4 = fArr3;
                    float f = fArr[0];
                    fArr4[0] = f + ((fArr2[0] - f) * valueAnimator.getAnimatedFraction());
                    float[] fArr5 = fArr3;
                    float f2 = fArr[1];
                    fArr5[1] = f2 + ((fArr2[1] - f2) * valueAnimator.getAnimatedFraction());
                    float[] fArr6 = fArr3;
                    float f3 = fArr[2];
                    fArr6[2] = f3 + ((fArr2[2] - f3) * valueAnimator.getAnimatedFraction());
                    object.setTextColor(Color.HSVToColor((int) (iAlpha + ((iAlpha2 - r0) * valueAnimator.getAnimatedFraction())), fArr3));
                }
            });
            valueAnimatorOfFloat.start();
            return;
        }
        setTextColor(i2);
    }

    public void SetTextSizeAnimated(int i, float f) {
        if (Build.VERSION.SDK_INT < 11 || i <= 0) {
            setTextSize(f);
        } else {
            ObjectAnimator.ofFloat(getObject(), "TextSize", getTextSize(), f).setDuration(i).start();
        }
    }

    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public void setTextSize(float f) {
        getObject().setTextSize(f);
    }

    @Override // anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod
    public float getTextSize() {
        return getObject().getTextSize() / getObject().getContext().getResources().getDisplayMetrics().scaledDensity;
    }

    public void setGravity(int i) {
        getObject().setGravity(i);
    }

    public int getGravity() {
        return getObject().getGravity();
    }

    public void setTypeface(Typeface typeface) {
        getObject().setTypeface(typeface);
    }

    public Typeface getTypeface() {
        return getObject().getTypeface();
    }

    @Override // anywheresoftware.b4a.objects.ViewWrapper, anywheresoftware.b4a.AbsObjectWrapper
    public String toString() {
        String string = super.toString();
        if (!IsInitialized()) {
            return string;
        }
        return String.valueOf(string) + ", Text=" + getText();
    }

    public static Typeface getTypeface(String str) {
        HashMap<String, Typeface> map = cachedTypefaces;
        Typeface typeface = map.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(BA.applicationContext.getAssets(), str);
        map.put(str, typefaceCreateFromAsset);
        return typefaceCreateFromAsset;
    }

    public static View build(Object obj, Map<String, Object> map, boolean z) throws Exception {
        Typeface typeface;
        TextView textView = (TextView) ViewWrapper.build(obj, map, z);
        ColorStateList colorStateList = z ? (ColorStateList) ViewWrapper.getDefault(textView, "textColor", textView.getTextColors()) : null;
        String str = (String) map.get("typeface");
        if (str.contains(".")) {
            if (z) {
                typeface = Typeface.createFromFile(File.Combine(File.getDirInternal(), str.toLowerCase(BA.cul)));
            } else {
                typeface = TypefaceWrapper.LoadFromAssets(str);
            }
        } else if (str.equals("FontAwesome")) {
            typeface = getTypeface(fontAwesomeFile);
            map.put("text", map.get("fontAwesome"));
        } else if (str.equals("Material Icons")) {
            typeface = getTypeface(materialIconsFile);
            map.put("text", map.get("materialIcons"));
        } else {
            typeface = (Typeface) Typeface.class.getField(str).get(null);
        }
        textView.setText((CharSequence) map.get("text"));
        int iIntValue = ((Integer) Typeface.class.getField((String) map.get("style")).get(null)).intValue();
        textView.setTextSize(((Float) map.get("fontsize")).floatValue());
        textView.setTypeface(typeface, iIntValue);
        textView.setGravity(((Integer) Gravity.class.getField((String) map.get("vAlignment")).get(null)).intValue() | ((Integer) Gravity.class.getField((String) map.get("hAlignment")).get(null)).intValue());
        int iIntValue2 = ((Integer) map.get("textColor")).intValue();
        if (iIntValue2 != -984833) {
            textView.setTextColor(iIntValue2);
        }
        if (z && iIntValue2 == -984833) {
            textView.setTextColor(colorStateList);
        }
        if (z) {
            setHint(textView, (String) map.get("name"));
        }
        textView.setSingleLine(((Boolean) BA.gm(map, "singleLine", false)).booleanValue());
        String str2 = (String) BA.gm(map, "ellipsize", "NONE");
        if (!str2.equals("NONE")) {
            textView.setEllipsize(TextUtils.TruncateAt.valueOf(str2));
            return textView;
        }
        if (z) {
            textView.setEllipsize(null);
        }
        return textView;
    }

    public static void setHint(TextView textView, String str) {
        if (textView.getText().length() != 0 || (textView instanceof EditText)) {
            return;
        }
        textView.setText(str);
        textView.setTextColor(-7829368);
    }
}
