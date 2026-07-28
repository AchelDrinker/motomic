package anywheresoftware.b4a.objects;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Selection;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.DesignerArgs;
import anywheresoftware.b4a.keywords.constants.TypefaceWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.drawable.ColorDrawable;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
@BA.Version(2.32f)
@BA.ShortName("B4XView")
public class B4XViewWrapper extends AbsObjectWrapper<Object> {
    public static final int TOUCH_ACTION_DOWN = 0;
    public static final int TOUCH_ACTION_MOVE = 2;
    public static final int TOUCH_ACTION_MOVE_NOTOUCH = 100;
    public static final int TOUCH_ACTION_UP = 1;
    private static Field solidColorField;
    private ConcreteViewWrapper nodeWrapper = new ConcreteViewWrapper();

    private ConcreteViewWrapper asViewWrapper() {
        this.nodeWrapper.setObject((View) getObject());
        return this.nodeWrapper;
    }

    @Override // anywheresoftware.b4a.AbsObjectWrapper, anywheresoftware.b4a.ObjectWrapper
    public void setObject(Object obj) {
        if (obj instanceof ObjectWrapper) {
            obj = ((ObjectWrapper) obj).getObjectOrNull();
        }
        super.setObject(obj);
    }

    public View getViewObject() {
        return (View) getObject();
    }

    private PanelWrapper asPanelWrapper() {
        View viewObject = getViewObject();
        if (viewObject instanceof ViewGroup) {
            return (PanelWrapper) AbsObjectWrapper.ConvertToWrapper(new PanelWrapper(), viewObject);
        }
        throw typeDoesNotMatch();
    }

    public boolean getVisible() {
        return asViewWrapper().getVisible();
    }

    public void setVisible(boolean z) {
        asViewWrapper().setVisible(z);
    }

    public boolean getEnabled() {
        return asViewWrapper().getEnabled();
    }

    public void setEnabled(boolean z) {
        asViewWrapper().setEnabled(z);
    }

    public void setLeft(int i) {
        asViewWrapper().setLeft(i);
    }

    public void setTop(int i) {
        asViewWrapper().setTop(i);
    }

    public void setWidth(int i) {
        asViewWrapper().setWidth(i);
    }

    public void setHeight(int i) {
        asViewWrapper().setHeight(i);
    }

    public int getLeft() {
        return asViewWrapper().getLeft();
    }

    public int getTop() {
        return asViewWrapper().getTop();
    }

    public int getWidth() {
        return asViewWrapper().getWidth();
    }

    public int getHeight() {
        return asViewWrapper().getHeight();
    }

    public void SetLayoutAnimated(int i, int i2, int i3, int i4, int i5) {
        View viewObject = getViewObject();
        BALayout.LayoutParams layoutParams = (BALayout.LayoutParams) viewObject.getLayoutParams();
        if (layoutParams == null) {
            asViewWrapper().SetLayout(i2, i3, i4, i5);
            return;
        }
        int i6 = layoutParams.left;
        int i7 = layoutParams.top;
        int i8 = layoutParams.width;
        int i9 = layoutParams.height;
        asViewWrapper().SetLayout(i2, i3, i4, i5);
        BALayout.LayoutParams layoutParams2 = (BALayout.LayoutParams) viewObject.getLayoutParams();
        if (Build.VERSION.SDK_INT < 11 || i <= 0 || i8 < 0 || i9 < 0) {
            return;
        }
        viewObject.setPivotX(0.0f);
        viewObject.setPivotY(0.0f);
        WeakReference weakReference = (WeakReference) AbsObjectWrapper.getExtraTags(viewObject).get("prevSet");
        AnimatorSet animatorSet = weakReference != null ? (AnimatorSet) weakReference.get() : null;
        if (animatorSet != null && animatorSet.isRunning()) {
            animatorSet.end();
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        AbsObjectWrapper.getExtraTags(viewObject).put("prevSet", new WeakReference(animatorSet2));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(viewObject, "translationX", i6 - layoutParams2.left, 0.0f), ObjectAnimator.ofFloat(viewObject, "translationY", i7 - layoutParams2.top, 0.0f), ObjectAnimator.ofFloat(viewObject, "scaleX", i8 / layoutParams2.width, 1.0f), ObjectAnimator.ofFloat(viewObject, "scaleY", i9 / layoutParams2.height, 1.0f));
        animatorSet2.setDuration(i);
        animatorSet2.setInterpolator(new LinearInterpolator());
        animatorSet2.start();
    }

    public B4XViewWrapper getParent() {
        return (B4XViewWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper(), asViewWrapper().getParent());
    }

    public void RemoveViewFromParent() {
        asViewWrapper().RemoveView();
    }

    public boolean RequestFocus() {
        return asViewWrapper().RequestFocus();
    }

    public void SetVisibleAnimated(int i, boolean z) {
        asViewWrapper().SetVisibleAnimated(i, z);
    }

    public void setProgress(int i) {
        ((ProgressBar) getObject()).setProgress(i);
    }

    public int getProgress() {
        return ((ProgressBar) getObject()).getProgress();
    }

    private LabelWrapper asLabelWrapper() {
        if (getObject() instanceof TextView) {
            return (LabelWrapper) AbsObjectWrapper.ConvertToWrapper(new LabelWrapper(), getObject());
        }
        throw typeDoesNotMatch();
    }

    public String getEditTextHint() {
        EditText editText = (EditText) getObject();
        return String.valueOf(editText.getHint() == null ? "" : editText.getHint());
    }

    public void setEditTextHint(CharSequence charSequence) {
        ((EditText) getObject()).setHint(charSequence);
    }

    public void setText(CharSequence charSequence) {
        asLabelWrapper().setText(charSequence);
    }

    public String getText() {
        return asLabelWrapper().getText();
    }

    public void setTextColor(int i) {
        asLabelWrapper().setTextColor(i);
    }

    public int getTextColor() {
        return asLabelWrapper().getTextColor();
    }

    public void SetTextSizeAnimated(int i, float f) {
        asLabelWrapper().SetTextSizeAnimated(i, f);
    }

    public void setAlpha(float f) {
        getViewObject().setAlpha(f);
    }

    public float getAlpha() {
        return getViewObject().getAlpha();
    }

    public void SetAlphaAnimated(int i, float f) {
        if (i > 0) {
            ObjectAnimator.ofFloat(getViewObject(), "Alpha", getAlpha(), f).setDuration(i).start();
        } else {
            setAlpha(f);
        }
    }

    public float getTextSize() {
        return asLabelWrapper().getTextSize();
    }

    public void setTextSize(float f) {
        asLabelWrapper().setTextSize(f);
    }

    public B4XFont getFont() {
        LabelWrapper labelWrapperAsLabelWrapper = asLabelWrapper();
        return XUI.CreateFont(labelWrapperAsLabelWrapper.getTypeface(), labelWrapperAsLabelWrapper.getTextSize());
    }

    public void setFont(B4XFont b4XFont) {
        LabelWrapper labelWrapperAsLabelWrapper = asLabelWrapper();
        labelWrapperAsLabelWrapper.setTextSize(b4XFont.textSize);
        labelWrapperAsLabelWrapper.setTypeface(b4XFont.getTypeface());
    }

    public void SetTextAlignment(String str, String str2) {
        int i;
        int i2;
        if (str.equals("TOP")) {
            i = 48;
        } else {
            i = str.equals("CENTER") ? 16 : 80;
        }
        if (str2.equals("LEFT")) {
            i2 = 3;
        } else {
            i2 = str2.equals("CENTER") ? 1 : 5;
        }
        asLabelWrapper().setGravity(i | i2);
    }

    public void setChecked(boolean z) {
        ((CompoundButton) getObject()).setChecked(z);
    }

    public boolean getChecked() {
        return ((CompoundButton) getObject()).isChecked();
    }

    public BA.IterableList GetAllViewsRecursive() {
        return asPanelWrapper().GetAllViewsRecursive();
    }

    public void LoadLayout(String str, BA ba) throws Exception {
        asPanelWrapper().LoadLayout(str, ba);
    }

    public B4XViewWrapper GetView(int i) {
        return (B4XViewWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper(), asPanelWrapper().GetView(i).getObject());
    }

    public void AddView(View view, int i, int i2, int i3, int i4) {
        asPanelWrapper().AddView(view, i, i2, i3, i4);
    }

    public void RemoveAllViews() {
        asPanelWrapper().RemoveAllViews();
    }

    public int getNumberOfViews() {
        return asPanelWrapper().getNumberOfViews();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public B4XBitmapWrapper Snapshot() {
        int left;
        int top;
        ViewGroup viewGroup;
        int iIndexOfChild;
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        ConcreteViewWrapper concreteViewWrapperAsViewWrapper = asViewWrapper();
        bitmapWrapper.InitializeMutable(concreteViewWrapperAsViewWrapper.getWidth(), concreteViewWrapperAsViewWrapper.getHeight());
        CanvasWrapper canvasWrapper = new CanvasWrapper();
        canvasWrapper.Initialize2(bitmapWrapper.getObject());
        View viewObject = getViewObject();
        if (viewObject.getLayoutParams() instanceof BALayout.LayoutParams) {
            left = getLeft();
            top = getTop();
        } else {
            left = 0;
            top = 0;
        }
        viewObject.measure(View.MeasureSpec.makeMeasureSpec(concreteViewWrapperAsViewWrapper.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(concreteViewWrapperAsViewWrapper.getHeight(), 1073741824));
        viewObject.layout(0, 0, concreteViewWrapperAsViewWrapper.getWidth(), concreteViewWrapperAsViewWrapper.getHeight());
        ((View) concreteViewWrapperAsViewWrapper.getObject()).draw(canvasWrapper.canvas);
        viewObject.layout(left, top, concreteViewWrapperAsViewWrapper.getWidth(), concreteViewWrapperAsViewWrapper.getHeight());
        if ((viewObject.getParent() instanceof ViewGroup) && (iIndexOfChild = (viewGroup = (ViewGroup) viewObject.getParent()).indexOfChild(viewObject)) > -1) {
            viewGroup.removeViewAt(iIndexOfChild);
            viewGroup.addView(viewObject, iIndexOfChild);
        }
        return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), bitmapWrapper.getObject());
    }

    public void SetBitmap(Bitmap bitmap) {
        asViewWrapper().SetBackgroundImageNew(bitmap).setGravity(17);
    }

    public B4XBitmapWrapper GetBitmap() {
        B4XBitmapWrapper b4XBitmapWrapper = new B4XBitmapWrapper();
        Drawable background = getViewObject().getBackground();
        if (background instanceof BitmapDrawable) {
            b4XBitmapWrapper.setObject(((BitmapDrawable) background).getBitmap());
        }
        return b4XBitmapWrapper;
    }

    private RuntimeException typeDoesNotMatch() {
        return new RuntimeException("Type does not match (" + getObject().getClass() + ")");
    }

    public void setColor(int i) {
        asViewWrapper().setColor(i);
    }

    public int getColor() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Drawable background = getViewObject().getBackground();
        if (background instanceof ColorDrawable) {
            return ((ColorDrawable) background).getColor();
        }
        if (background instanceof ColorDrawable.GradientDrawableWithCorners) {
            return ((ColorDrawable.GradientDrawableWithCorners) background).color;
        }
        if (!(background instanceof GradientDrawable)) {
            return 0;
        }
        if (Build.VERSION.SDK_INT > 28 && BA.applicationContext.getApplicationInfo().targetSdkVersion > 28) {
            return 0;
        }
        Drawable.ConstantState constantState = background.getConstantState();
        Class<?> cls = constantState.getClass();
        if (!cls.getName().equals("android.graphics.drawable.GradientDrawable$GradientState")) {
            return 0;
        }
        if (solidColorField == null) {
            try {
                try {
                    solidColorField = cls.getDeclaredField("mSolidColor");
                } catch (NoSuchFieldException unused) {
                    solidColorField = cls.getDeclaredField("mColorStateList");
                }
            } catch (NoSuchFieldException unused2) {
                solidColorField = cls.getDeclaredField("mSolidColors");
            }
            solidColorField.setAccessible(true);
        }
        Object obj = solidColorField.get(constantState);
        if (solidColorField.getName().equals("mSolidColor")) {
            return ((Integer) obj).intValue();
        }
        ColorStateList colorStateList = (ColorStateList) obj;
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        return 0;
    }

    public void SetColorAnimated(int i, int i2, final int i3) {
        ColorDrawable.GradientDrawableWithCorners gradientDrawableWithCorners;
        if (Build.VERSION.SDK_INT >= 11 && i > 0) {
            final View viewObject = getViewObject();
            if (viewObject.getBackground() instanceof ColorDrawable.GradientDrawableWithCorners) {
                gradientDrawableWithCorners = (ColorDrawable.GradientDrawableWithCorners) viewObject.getBackground();
            } else {
                gradientDrawableWithCorners = new ColorDrawable.GradientDrawableWithCorners();
            }
            final ColorDrawable.GradientDrawableWithCorners gradientDrawableWithCorners2 = gradientDrawableWithCorners;
            gradientDrawableWithCorners2.setColor(i2);
            viewObject.setBackgroundDrawable(gradientDrawableWithCorners2);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            final int iAlpha = Color.alpha(i2);
            final int iRed = Color.red(i2);
            final int iGreen = Color.green(i2);
            final int iBlue = Color.blue(i2);
            final int iAlpha2 = Color.alpha(i3);
            final int iRed2 = Color.red(i3);
            final int iGreen2 = Color.green(i3);
            final int iBlue2 = Color.blue(i3);
            valueAnimatorOfFloat.setDuration(i);
            valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: anywheresoftware.b4a.objects.B4XViewWrapper.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    gradientDrawableWithCorners2.setColor(Color.argb((int) (iAlpha + ((iAlpha2 - r0) * animatedFraction)), (int) (iRed + ((iRed2 - r1) * animatedFraction)), (int) (iGreen + ((iGreen2 - r2) * animatedFraction)), (int) (iBlue + ((iBlue2 - r3) * animatedFraction))));
                    viewObject.invalidate();
                }
            });
            valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: anywheresoftware.b4a.objects.B4XViewWrapper.2
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
                    gradientDrawableWithCorners2.setColor(i3);
                }
            });
            valueAnimatorOfFloat.start();
            return;
        }
        asViewWrapper().setColor(i3);
    }

    public void SetColorAndBorder(int i, int i2, int i3, int i4) {
        anywheresoftware.b4a.objects.drawable.ColorDrawable colorDrawable = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
        colorDrawable.Initialize2(i, i4, i2, i3);
        getViewObject().setBackgroundDrawable(colorDrawable.getObject());
    }

    private ScrollViewWrapper asVScrollViewWrapper() {
        return (ScrollViewWrapper) AbsObjectWrapper.ConvertToWrapper(new ScrollViewWrapper(), getObject());
    }

    private HorizontalScrollViewWrapper asHScrollViewWrapper() {
        return (HorizontalScrollViewWrapper) AbsObjectWrapper.ConvertToWrapper(new HorizontalScrollViewWrapper(), getObject());
    }

    public int getScrollViewOffsetY() {
        if (getObject() instanceof ScrollView) {
            return asVScrollViewWrapper().getScrollPosition();
        }
        return 0;
    }

    public void setScrollViewOffsetY(int i) {
        if (getObject() instanceof ScrollView) {
            asVScrollViewWrapper().ScrollToNow(i);
        }
    }

    public int getScrollViewOffsetX() {
        if (getObject() instanceof HorizontalScrollView) {
            return asHScrollViewWrapper().getScrollPosition();
        }
        return 0;
    }

    public void setScrollViewOffsetX(int i) {
        if (getObject() instanceof HorizontalScrollView) {
            asHScrollViewWrapper().ScrollToNow(i);
        }
    }

    private PanelWrapper getScrollViewPanel() {
        if (getObject() instanceof HorizontalScrollView) {
            return asHScrollViewWrapper().getPanel();
        }
        if (getObject() instanceof ScrollView) {
            return asVScrollViewWrapper().getPanel();
        }
        throw typeDoesNotMatch();
    }

    public B4XViewWrapper getScrollViewInnerPanel() {
        return (B4XViewWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper(), getScrollViewPanel().getObject());
    }

    public int getScrollViewContentHeight() {
        return getScrollViewInnerPanel().getHeight();
    }

    public void setScrollViewContentHeight(int i) {
        getScrollViewInnerPanel().setHeight(i);
    }

    public int getScrollViewContentWidth() {
        return getScrollViewInnerPanel().getWidth();
    }

    public void setScrollViewContentWidth(int i) {
        getScrollViewInnerPanel().setWidth(i);
    }

    public Object getTag() {
        return asViewWrapper().getTag();
    }

    public void setTag(Object obj) {
        asViewWrapper().setTag(obj);
    }

    public void SendToBack() {
        asViewWrapper().SendToBack();
    }

    public void BringToFront() {
        asViewWrapper().BringToFront();
    }

    public void SetRotationAnimated(int i, float f) {
        float rotation = getRotation();
        View viewObject = getViewObject();
        viewObject.setRotation(f);
        viewObject.setPivotX(getWidth() / 2);
        viewObject.setPivotY(getHeight() / 2);
        ObjectAnimator duration = ObjectAnimator.ofFloat(viewObject, "rotation", rotation, f).setDuration(i);
        duration.setInterpolator(new LinearInterpolator());
        duration.start();
    }

    public float getRotation() {
        return getViewObject().getRotation();
    }

    public void setRotation(float f) {
        View viewObject = getViewObject();
        viewObject.setRotation(f);
        viewObject.setPivotX(getWidth() / 2);
        viewObject.setPivotY(getHeight() / 2);
    }

    public void setSelectionStart(int i) {
        SetSelection(i, 0);
    }

    public int getSelectionStart() {
        return Selection.getSelectionStart(((EditText) getObject()).getText());
    }

    public int getSelectionLength() {
        return Math.max(0, Selection.getSelectionEnd(((EditText) getObject()).getText()) - getSelectionStart());
    }

    public void SetSelection(int i, int i2) {
        Selection.setSelection(((EditText) getObject()).getText(), i, i2 + i);
    }

    public void SelectAll() {
        Selection.selectAll(((EditText) getObject()).getText());
    }

    @BA.ShortName("B4XBitmap")
    public static class B4XBitmapWrapper extends AbsObjectWrapper<Bitmap> {
        public double getWidth() {
            return getObject().getWidth();
        }

        public double getHeight() {
            return getObject().getHeight();
        }

        private CanvasWrapper.BitmapWrapper asBitmapWrapper() {
            return (CanvasWrapper.BitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new CanvasWrapper.BitmapWrapper(), getObject());
        }

        public void WriteToStream(OutputStream outputStream, int i, Bitmap.CompressFormat compressFormat) {
            asBitmapWrapper().WriteToStream(outputStream, i, compressFormat);
        }

        public B4XBitmapWrapper Resize(int i, int i2, boolean z) {
            return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), asBitmapWrapper().Resize(i, i2, z).getObject());
        }

        public B4XBitmapWrapper Rotate(int i) {
            return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), asBitmapWrapper().Rotate(i).getObject());
        }

        public B4XBitmapWrapper Crop(int i, int i2, int i3, int i4) {
            return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), asBitmapWrapper().Crop(i, i2, i3, i4).getObject());
        }

        public float getScale() {
            return getObject().getDensity() / 160.0f;
        }
    }

    @BA.ShortName("B4XFont")
    public static class B4XFont {
        private float textSize;
        public Typeface typeface;

        public float getSize() {
            return this.textSize;
        }

        public boolean getIsInitialized() {
            return this.typeface != null;
        }

        public TypefaceWrapper ToNativeFont() {
            return (TypefaceWrapper) AbsObjectWrapper.ConvertToWrapper(new TypefaceWrapper(), this.typeface);
        }

        public Typeface getTypeface() {
            Typeface typeface = this.typeface;
            if (typeface != null) {
                return typeface;
            }
            throw new NullPointerException("font not set");
        }
    }

    @BA.ShortName("XUI")
    public static class XUI {
        public static final int Color_Black = -16777216;
        public static final int Color_Blue = -16776961;
        public static final int Color_Cyan = -16711681;
        public static final int Color_DarkGray = -12303292;
        public static final int Color_Gray = -7829368;
        public static final int Color_Green = -16711936;
        public static final int Color_LightGray = -3355444;
        public static final int Color_Magenta = -65281;
        public static final int Color_Red = -65536;
        public static final int Color_Transparent = 0;
        public static final int Color_White = -1;
        public static final int Color_Yellow = -256;
        public static final int DialogResponse_Cancel = -3;
        public static final int DialogResponse_Negative = -2;
        public static final int DialogResponse_Positive = -1;

        public static int Color_ARGB(int i, int i2, int i3, int i4) {
            return (i << 24) | (i2 << 16) | (i3 << 8) | i4;
        }

        public static void SetDataFolder(String str) {
        }

        public static boolean getIsB4A() {
            return true;
        }

        public static boolean getIsB4J() {
            return false;
        }

        public static boolean getIsB4i() {
            return false;
        }

        public static B4XBitmapWrapper LoadBitmap(String str, String str2) throws IOException {
            return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), Common.LoadBitmap(str, str2).getObject());
        }

        public static B4XBitmapWrapper LoadBitmapResize(String str, String str2, int i, int i2, boolean z) throws IOException {
            return (B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XBitmapWrapper(), Common.LoadBitmapResize(str, str2, i, i2, z).getObject());
        }

        public static String getDefaultFolder() {
            return File.getDirInternal();
        }

        public static boolean SubExists(BA ba, Object obj, String str, int i) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException {
            return Common.SubExists(ba, obj, str);
        }

        public static int PaintOrColorToColor(Object obj) {
            return ((Integer) obj).intValue();
        }

        public static B4XFont CreateFont(Typeface typeface, float f) {
            B4XFont b4XFont = new B4XFont();
            b4XFont.typeface = typeface;
            b4XFont.textSize = f;
            return b4XFont;
        }

        public static B4XFont CreateFont2(B4XFont b4XFont, float f) {
            return CreateFont(b4XFont.typeface, f);
        }

        public static B4XFont CreateDefaultFont(float f) {
            return CreateFont(TypefaceWrapper.DEFAULT, f);
        }

        public static B4XFont CreateDefaultBoldFont(float f) {
            return CreateFont(TypefaceWrapper.DEFAULT_BOLD, f);
        }

        public static B4XFont CreateFontAwesome(float f) {
            return CreateFont(TypefaceWrapper.getFONTAWESOME(), f);
        }

        public static B4XFont CreateMaterialIcons(float f) {
            return CreateFont(TypefaceWrapper.getMATERIALICONS(), f);
        }

        public static float getScale() {
            return Common.Density;
        }

        public static B4XViewWrapper CreatePanel(BA ba, String str) {
            PanelWrapper panelWrapper = new PanelWrapper();
            if (ba.eventsTarget != null && str.length() > 0) {
                if (ba.activity == null) {
                    throw new RuntimeException("Class must have an Activity context.");
                }
                panelWrapper.Initialize(ba, str);
            } else {
                panelWrapper.Initialize(ba.sharedProcessBA.activityBA.get(), str);
            }
            return (B4XViewWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper(), panelWrapper.getObject());
        }

        public static int Color_RGB(int i, int i2, int i3) {
            return Color_ARGB(255, i, i2, i3);
        }

        public static Object MsgboxAsync(BA ba, CharSequence charSequence, CharSequence charSequence2) throws Exception {
            return Common.Msgbox2Async(charSequence, charSequence2, "OK", "", "", null, ba, true);
        }

        public static Object Msgbox2Async(BA ba, CharSequence charSequence, CharSequence charSequence2, String str, String str2, String str3, CanvasWrapper.BitmapWrapper bitmapWrapper) {
            return Common.Msgbox2Async(charSequence, charSequence2, str, str2, str3, bitmapWrapper, ba, false);
        }

        public static String FileUri(String str, String str2) throws IOException {
            if (!str.equals(File.getDirAssets())) {
                return "file://" + File.Combine(str, urlencode(str2));
            }
            if (File.virtualAssetsFolder == null) {
                return "file:///android_asset/" + urlencode(str2.toLowerCase(BA.cul));
            }
            return "file://" + File.Combine(File.virtualAssetsFolder, urlencode(File.getUnpackedVirtualAssetFile(str2)));
        }

        private static String urlencode(String str) throws UnsupportedEncodingException {
            return URLEncoder.encode(str, "utf8").replace("+", "%20");
        }

        public static void RegisterDesignerClass(Object obj) {
            String name = obj.getClass().getName();
            if (!name.startsWith(BA.packageName)) {
                throw new RuntimeException("invalid class");
            }
            DesignerArgs.targetsCache.put(name.substring(BA.packageName.length() + 1), (B4AClass) obj);
        }

        public static Object GetRegisteredDesignerClass(String str) {
            return DesignerArgs.targetsCache.get(str.toLowerCase(BA.cul));
        }
    }
}
