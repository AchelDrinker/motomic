package anywheresoftware.b4a.objects;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.keywords.LayoutBuilder;
import anywheresoftware.b4a.objects.drawable.BitmapDrawable;
import anywheresoftware.b4a.objects.drawable.ColorDrawable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ViewWrapper<T extends View> extends AbsObjectWrapper<T> implements BA.B4aDebuggable {
    public static int animationDuration = 400;
    public static final int defaultColor = -984833;
    public static int lastId;
    protected BA ba;

    public void Initialize(BA ba, String str) {
        innerInitialize(ba, str.toLowerCase(BA.cul), false);
    }

    public void innerInitialize(final BA ba, final String str, boolean z) {
        this.ba = ba;
        T object = getObject();
        int i = lastId + 1;
        lastId = i;
        object.setId(i);
        if (ba.subExists(String.valueOf(str) + "_click")) {
            getObject().setOnClickListener(new View.OnClickListener() { // from class: anywheresoftware.b4a.objects.ViewWrapper.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ba.raiseEvent(view, String.valueOf(str) + "_click", new Object[0]);
                }
            });
        }
        if (ba.subExists(String.valueOf(str) + "_longclick")) {
            getObject().setOnLongClickListener(new View.OnLongClickListener() { // from class: anywheresoftware.b4a.objects.ViewWrapper.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    ba.raiseEvent(view, String.valueOf(str) + "_longclick", new Object[0]);
                    return true;
                }
            });
        }
    }

    public Drawable getBackground() {
        return getObject().getBackground();
    }

    public void setBackground(Drawable drawable) {
        getObject().setBackgroundDrawable(drawable);
    }

    public BitmapDrawable SetBackgroundImageNew(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        bitmapDrawable.Initialize(bitmap);
        getObject().setBackgroundDrawable(bitmapDrawable.getObject());
        return bitmapDrawable;
    }

    public void SetBackgroundImage(Bitmap bitmap) {
        SetBackgroundImageNew(bitmap);
    }

    public void Invalidate() {
        getObject().invalidate();
    }

    public void Invalidate2(Rect rect) {
        getObject().invalidate(rect);
    }

    public void Invalidate3(int i, int i2, int i3, int i4) {
        getObject().invalidate(i, i2, i3, i4);
    }

    public void setWidth(int i) {
        getLayoutParams().width = i;
        requestLayout();
    }

    public int getWidth() {
        return getLayoutParams().width;
    }

    public int getHeight() {
        return getLayoutParams().height;
    }

    public int getLeft() {
        return ((BALayout.LayoutParams) getLayoutParams()).left;
    }

    public int getTop() {
        return ((BALayout.LayoutParams) getLayoutParams()).top;
    }

    public void setHeight(int i) {
        getLayoutParams().height = i;
        requestLayout();
    }

    public void setLeft(int i) {
        ((BALayout.LayoutParams) getLayoutParams()).left = i;
        requestLayout();
    }

    public void setTop(int i) {
        ((BALayout.LayoutParams) getLayoutParams()).top = i;
        requestLayout();
    }

    private void requestLayout() {
        ViewParent parent = getObject().getParent();
        if (parent != null) {
            parent.requestLayout();
        }
    }

    public void setPadding(int[] iArr) {
        getObject().setPadding(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public int[] getPadding() {
        return new int[]{getObject().getPaddingLeft(), getObject().getPaddingTop(), getObject().getPaddingRight(), getObject().getPaddingBottom()};
    }

    public void setColor(int i) {
        Drawable background = getObject().getBackground();
        if (background != null && (background instanceof GradientDrawable)) {
            if (background instanceof ColorDrawable.GradientDrawableWithCorners) {
                ColorDrawable.GradientDrawableWithCorners gradientDrawableWithCorners = (ColorDrawable.GradientDrawableWithCorners) background;
                if (gradientDrawableWithCorners.borderWidth != 0) {
                    gradientDrawableWithCorners.setColor(i);
                    getObject().invalidate();
                    getObject().requestLayout();
                    return;
                }
            }
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.Initialize(i, (int) findRadius());
            getObject().setBackgroundDrawable(colorDrawable.getObject());
            return;
        }
        getObject().setBackgroundColor(i);
    }

    private float findRadius() {
        Drawable background = getObject().getBackground();
        if (background == null || !(background instanceof GradientDrawable)) {
            return 0.0f;
        }
        if (background instanceof ColorDrawable.GradientDrawableWithCorners) {
            return ((ColorDrawable.GradientDrawableWithCorners) background).cornerRadius;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) getObject().getBackground();
        try {
            Field declaredField = gradientDrawable.getClass().getDeclaredField("mGradientState");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(gradientDrawable);
            return ((Float) obj.getClass().getDeclaredField("mRadius").get(obj)).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public void setTag(Object obj) {
        getObject().setTag(obj);
    }

    public Object getTag() {
        return getObject().getTag();
    }

    public Object getParent() {
        return getObject().getParent();
    }

    public void setVisible(boolean z) {
        getObject().setVisibility(z ? 0 : 8);
    }

    public boolean getVisible() {
        return getObject().getVisibility() == 0;
    }

    public void setEnabled(boolean z) {
        getObject().setEnabled(z);
    }

    public boolean getEnabled() {
        return getObject().isEnabled();
    }

    public void BringToFront() {
        if (getObject().getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) getObject().getParent();
            viewGroup.removeView(getObject());
            viewGroup.addView(getObject());
        }
    }

    public void SendToBack() {
        if (getObject().getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) getObject().getParent();
            viewGroup.removeView(getObject());
            viewGroup.addView(getObject(), 0);
        }
    }

    public void RemoveView() {
        if (getObject().getParent() instanceof ViewGroup) {
            ((ViewGroup) getObject().getParent()).removeView(getObject());
        }
    }

    public void SetLayout(int i, int i2, int i3, int i4) {
        BALayout.LayoutParams layoutParams = (BALayout.LayoutParams) getLayoutParams();
        layoutParams.left = i;
        layoutParams.top = i2;
        layoutParams.width = i3;
        layoutParams.height = i4;
        if (getObject().getParent() != null) {
            getObject().getParent().requestLayout();
        }
    }

    private ViewGroup.LayoutParams getLayoutParams() {
        ViewGroup.LayoutParams layoutParams = getObject().getLayoutParams();
        if (layoutParams != null) {
            return layoutParams;
        }
        BALayout.LayoutParams layoutParams2 = new BALayout.LayoutParams(0, 0, 0, 0);
        getObject().setLayoutParams(layoutParams2);
        return layoutParams2;
    }

    public void SetLayoutAnimated(int i, int i2, int i3, int i4, int i5) {
        BALayout.LayoutParams layoutParams = (BALayout.LayoutParams) getObject().getLayoutParams();
        if (layoutParams == null) {
            SetLayout(i2, i3, i4, i5);
            return;
        }
        int i6 = layoutParams.left;
        int i7 = layoutParams.top;
        int i8 = layoutParams.width;
        int i9 = layoutParams.height;
        SetLayout(i2, i3, i4, i5);
        AnimateFrom(getObject(), i, i6, i7, i8, i9);
    }

    public void SetColorAnimated(int i, int i2, int i3) {
        ColorDrawable.GradientDrawableWithCorners gradientDrawableWithCorners;
        if (Build.VERSION.SDK_INT >= 11 && i > 0) {
            final T object = getObject();
            if (object.getBackground() instanceof ColorDrawable.GradientDrawableWithCorners) {
                gradientDrawableWithCorners = (ColorDrawable.GradientDrawableWithCorners) object.getBackground();
            } else {
                gradientDrawableWithCorners = new ColorDrawable.GradientDrawableWithCorners();
            }
            final ColorDrawable.GradientDrawableWithCorners gradientDrawableWithCorners2 = gradientDrawableWithCorners;
            gradientDrawableWithCorners2.setColor(i2);
            object.setBackgroundDrawable(gradientDrawableWithCorners2);
            final float[] fArr = new float[3];
            final float[] fArr2 = new float[3];
            Color.colorToHSV(i2, fArr);
            Color.colorToHSV(i3, fArr2);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(i);
            final float[] fArr3 = new float[3];
            final int iAlpha = Color.alpha(i2);
            final int iAlpha2 = Color.alpha(i3);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: anywheresoftware.b4a.objects.ViewWrapper.3
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
                    gradientDrawableWithCorners2.setColor(Color.HSVToColor((int) (iAlpha + ((iAlpha2 - r0) * valueAnimator.getAnimatedFraction())), fArr3));
                    object.invalidate();
                }
            });
            valueAnimatorOfFloat.start();
            return;
        }
        setColor(i3);
    }

    public void SetVisibleAnimated(int i, final boolean z) {
        if (z == getVisible()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 11 && i > 0) {
            final T object = getObject();
            ObjectAnimator objectAnimatorOfFloat = z ? ObjectAnimator.ofFloat(object, "alpha", 0.0f, 1.0f) : ObjectAnimator.ofFloat(object, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: anywheresoftware.b4a.objects.ViewWrapper.4
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (!z) {
                        object.setVisibility(8);
                    }
                    object.setAlpha(1.0f);
                }
            });
            objectAnimatorOfFloat.setDuration(i).start();
            if (z) {
                object.setVisibility(0);
                return;
            }
            return;
        }
        setVisible(z);
    }

    public static void AnimateFrom(View view, int i, int i2, int i3, int i4, int i5) {
        BALayout.LayoutParams layoutParams = (BALayout.LayoutParams) view.getLayoutParams();
        if (Build.VERSION.SDK_INT < 11 || i <= 0 || i4 < 0 || i5 < 0) {
            return;
        }
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        WeakReference weakReference = (WeakReference) AbsObjectWrapper.getExtraTags(view).get("prevSet");
        AnimatorSet animatorSet = weakReference != null ? (AnimatorSet) weakReference.get() : null;
        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.end();
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        AbsObjectWrapper.getExtraTags(view).put("prevSet", new WeakReference(animatorSet2));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, "translationX", i2 - layoutParams.left, 0.0f), ObjectAnimator.ofFloat(view, "translationY", i3 - layoutParams.top, 0.0f), ObjectAnimator.ofFloat(view, "scaleX", i4 / Math.max(1, layoutParams.width), 1.0f), ObjectAnimator.ofFloat(view, "scaleY", i5 / Math.max(1, layoutParams.height), 1.0f));
        animatorSet2.setDuration(i);
        animatorSet2.start();
    }

    public boolean RequestFocus() {
        return getObject().requestFocus();
    }

    @Override // anywheresoftware.b4a.AbsObjectWrapper
    public String toString() {
        String str;
        String strBaseToString = baseToString();
        if (!IsInitialized()) {
            return strBaseToString;
        }
        String str2 = String.valueOf(strBaseToString) + ": ";
        if (!getEnabled()) {
            str2 = String.valueOf(str2) + "Enabled=false, ";
        }
        if (!getVisible()) {
            str2 = String.valueOf(str2) + "Visible=false, ";
        }
        if (getObject().getLayoutParams() == null || !(getObject().getLayoutParams() instanceof BALayout.LayoutParams)) {
            str = String.valueOf(str2) + "Layout not available";
        } else {
            str = String.valueOf(str2) + "Left=" + getLeft() + ", Top=" + getTop() + ", Width=" + getWidth() + ", Height=" + getHeight();
        }
        if (getTag() == null) {
            return str;
        }
        return String.valueOf(str) + ", Tag=" + getTag().toString();
    }

    public static View build(Object obj, Map<String, Object> map, boolean z) throws Exception {
        View view = (View) obj;
        if (view.getTag() == null && z) {
            HashMap map2 = new HashMap();
            map2.put("background", view.getBackground());
            map2.put("padding", new int[]{view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()});
            view.setTag(map2);
        }
        BALayout.LayoutParams layoutParams = (BALayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new BALayout.LayoutParams();
            view.setLayoutParams(layoutParams);
        }
        layoutParams.setFromUserPlane(((Integer) map.get("left")).intValue(), ((Integer) map.get("top")).intValue(), ((Integer) map.get("width")).intValue(), ((Integer) map.get("height")).intValue());
        view.setEnabled(((Boolean) map.get("enabled")).booleanValue());
        if (!z) {
            view.setVisibility(!((Boolean) map.get("visible")).booleanValue() ? 8 : 0);
            view.setTag(map.get("tag"));
        }
        if (((int[]) map.get("padding")) != null) {
            view.setPadding(Math.round(BALayout.getDeviceScale() * r7[0]), Math.round(BALayout.getDeviceScale() * r7[1]), Math.round(BALayout.getDeviceScale() * r7[2]), Math.round(BALayout.getDeviceScale() * r7[3]));
            return view;
        }
        if (z) {
            int[] iArr = (int[]) getDefault(view, "padding", null);
            view.setPadding(iArr[0], iArr[1], iArr[2], iArr[3]);
        }
        return view;
    }

    public static void fixAnchor(int i, int i2, LayoutBuilder.ViewWrapperAndAnchor viewWrapperAndAnchor) {
        if (viewWrapperAndAnchor.hanchor == LayoutBuilder.ViewWrapperAndAnchor.RIGHT) {
            viewWrapperAndAnchor.right = viewWrapperAndAnchor.vw.getLeft();
            viewWrapperAndAnchor.vw.setLeft((i - viewWrapperAndAnchor.right) - viewWrapperAndAnchor.vw.getWidth());
        } else if (viewWrapperAndAnchor.hanchor == LayoutBuilder.ViewWrapperAndAnchor.BOTH) {
            viewWrapperAndAnchor.right = viewWrapperAndAnchor.vw.getWidth();
            viewWrapperAndAnchor.vw.setWidth((i - viewWrapperAndAnchor.right) - viewWrapperAndAnchor.vw.getLeft());
        }
        if (viewWrapperAndAnchor.vanchor == LayoutBuilder.ViewWrapperAndAnchor.BOTTOM) {
            viewWrapperAndAnchor.bottom = viewWrapperAndAnchor.vw.getTop();
            viewWrapperAndAnchor.vw.setTop((i2 - viewWrapperAndAnchor.bottom) - viewWrapperAndAnchor.vw.getHeight());
        } else if (viewWrapperAndAnchor.vanchor == LayoutBuilder.ViewWrapperAndAnchor.BOTH) {
            viewWrapperAndAnchor.bottom = viewWrapperAndAnchor.vw.getHeight();
            viewWrapperAndAnchor.vw.setHeight((i2 - viewWrapperAndAnchor.bottom) - viewWrapperAndAnchor.vw.getTop());
        }
    }

    @Override // anywheresoftware.b4a.BA.B4aDebuggable
    public Object[] debug(int i, boolean[] zArr) {
        Object[] objArr = {"ToString", toString()};
        zArr[0] = true;
        return objArr;
    }

    public static <T> T buildNativeView(Context context, Class<T> cls, HashMap<String, Object> map, boolean z) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        String str = (String) map.get("nativeClass");
        if (str != null && str.startsWith(".")) {
            str = String.valueOf(BA.applicationContext.getPackageName()) + str;
        }
        if (!z && str != null) {
            try {
                if (str.length() != 0) {
                    cls = (Class<T>) Class.forName(str);
                }
            } catch (ClassNotFoundException unused) {
                int iLastIndexOf = str.lastIndexOf(".");
                cls = (Class<T>) Class.forName(String.valueOf(str.substring(0, iLastIndexOf)) + "$" + str.substring(iLastIndexOf + 1));
            }
        }
        return cls.getConstructor(Context.class).newInstance(context);
    }

    public static Object getDefault(View view, String str, Object obj) {
        HashMap map = (HashMap) view.getTag();
        if (map.containsKey(str)) {
            return map.get(str);
        }
        map.put(str, obj);
        return obj;
    }
}
