package anywheresoftware.b4a.objects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.B4XViewWrapper;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("B4XCanvas")
public class B4XCanvas {
    public CanvasWrapper cvs;
    public B4XViewWrapper target;
    private B4XRect targetRect;

    public void Release() {
    }

    public void Initialize(B4XViewWrapper b4XViewWrapper) {
        CanvasWrapper canvasWrapper = new CanvasWrapper();
        this.cvs = canvasWrapper;
        this.target = b4XViewWrapper;
        canvasWrapper.Initialize(b4XViewWrapper.getViewObject());
        this.cvs.setAntiAlias(true);
        B4XRect b4XRect = new B4XRect();
        this.targetRect = b4XRect;
        b4XRect.Initialize(0.0f, 0.0f, b4XViewWrapper.getWidth(), b4XViewWrapper.getHeight());
    }

    public void Resize(float f, float f2) {
        B4XViewWrapper b4XViewWrapper = this.target;
        int left = b4XViewWrapper.getLeft();
        int top = this.target.getTop();
        double d = f;
        Double.isNaN(d);
        double d2 = f2;
        Double.isNaN(d2);
        b4XViewWrapper.SetLayoutAnimated(0, left, top, (int) (d + 0.5d), (int) (d2 + 0.5d));
        Initialize(this.target);
    }

    public B4XRect getTargetRect() {
        return this.targetRect;
    }

    public B4XViewWrapper getTargetView() {
        return this.target;
    }

    public void Invalidate() {
        this.target.getViewObject().invalidate();
    }

    public void DrawLine(float f, float f2, float f3, float f4, int i, float f5) {
        this.cvs.DrawLine(f, f2, f3, f4, i, f5);
    }

    public B4XViewWrapper.B4XBitmapWrapper CreateBitmap() {
        return (B4XViewWrapper.B4XBitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new B4XViewWrapper.B4XBitmapWrapper(), this.cvs.getBitmap().getObject());
    }

    public void DrawRect(B4XRect b4XRect, int i, boolean z, float f) {
        this.cvs.DrawRect(b4XRect.toRect(), i, z, f);
    }

    public void DrawCircle(float f, float f2, float f3, int i, boolean z, float f4) {
        this.cvs.DrawCircle(f, f2, f3, i, z, f4);
    }

    public void DrawBitmap(Bitmap bitmap, B4XRect b4XRect) {
        this.cvs.DrawBitmap(bitmap, null, b4XRect.toRect());
    }

    public void DrawBitmapRotated(Bitmap bitmap, B4XRect b4XRect, float f) {
        this.cvs.DrawBitmapRotated(bitmap, null, b4XRect.toRect(), f);
    }

    public void ClipPath(B4XPath b4XPath) throws Exception {
        this.cvs.ClipPath(b4XPath.getObject());
    }

    public void RemoveClip() {
        this.cvs.RemoveClip();
    }

    public void DrawPath(B4XPath b4XPath, int i, boolean z, float f) throws Exception {
        this.cvs.DrawPath(b4XPath.getObject(), i, z, f);
    }

    public void DrawPathRotated(B4XPath b4XPath, int i, boolean z, float f, float f2, float f3, float f4) throws Exception {
        this.cvs.canvas.save();
        try {
            this.cvs.canvas.rotate(f2, f3, f4);
            this.cvs.DrawPath(b4XPath.getObject(), i, z, f);
        } finally {
            this.cvs.canvas.restore();
        }
    }

    public void ClearRect(B4XRect b4XRect) {
        this.cvs.DrawRect(b4XRect.toRect(), 0, true, 0.0f);
    }

    public void DrawText(BA ba, String str, float f, float f2, B4XViewWrapper.B4XFont b4XFont, int i, Paint.Align align) {
        this.cvs.DrawText(ba, str, f, f2, b4XFont.typeface, b4XFont.getSize(), i, align);
        this.cvs.setAntiAlias(true);
    }

    public void DrawTextRotated(BA ba, String str, float f, float f2, B4XViewWrapper.B4XFont b4XFont, int i, Paint.Align align, float f3) {
        this.cvs.DrawTextRotated(ba, str, f, f2, b4XFont.typeface, b4XFont.getSize(), i, align, f3);
        this.cvs.setAntiAlias(true);
    }

    public B4XRect MeasureText(String str, B4XViewWrapper.B4XFont b4XFont) {
        Paint paint = this.cvs.paint;
        paint.setTextSize(b4XFont.getSize() * BA.applicationContext.getResources().getDisplayMetrics().scaledDensity);
        paint.setTypeface(b4XFont.typeface);
        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.LEFT);
        Rect rect = new Rect();
        if (str.startsWith(" ")) {
            str = "." + str.substring(1);
        }
        if (str.endsWith(" ")) {
            str = String.valueOf(str.substring(0, str.length() - 1)) + ".";
        }
        paint.getTextBounds(str, 0, str.length(), rect);
        B4XRect b4XRect = new B4XRect();
        b4XRect.Initialize(rect.left, rect.top, rect.right, rect.bottom);
        return b4XRect;
    }

    @BA.ShortName("B4XRect")
    public static class B4XRect {
        private RectF rf;
        private Rect ri;

        public void Initialize(float f, float f2, float f3, float f4) {
            this.rf = new RectF(f, f2, f3, f4);
            this.ri = new Rect();
        }

        public float getLeft() {
            return this.rf.left;
        }

        public void setLeft(float f) {
            this.rf.left = f;
        }

        public float getTop() {
            return this.rf.top;
        }

        public void setTop(float f) {
            this.rf.top = f;
        }

        public float getRight() {
            return this.rf.right;
        }

        public void setRight(float f) {
            this.rf.right = f;
        }

        public float getBottom() {
            return this.rf.bottom;
        }

        public void setBottom(float f) {
            this.rf.bottom = f;
        }

        public float getWidth() {
            return this.rf.right - this.rf.left;
        }

        public void setWidth(float f) {
            RectF rectF = this.rf;
            rectF.right = rectF.left + f;
        }

        public float getHeight() {
            return this.rf.bottom - this.rf.top;
        }

        public void setHeight(float f) {
            RectF rectF = this.rf;
            rectF.bottom = rectF.top + f;
        }

        public float getCenterX() {
            return this.rf.centerX();
        }

        public float getCenterY() {
            return this.rf.centerY();
        }

        public Rect toRect() {
            this.rf.round(this.ri);
            return this.ri;
        }

        public String toString() {
            RectF rectF = this.rf;
            if (rectF != null) {
                return rectF.toString();
            }
            return "Not initialized";
        }
    }

    @BA.ShortName("B4XPath")
    public static class B4XPath extends AbsObjectWrapper<Path> {
        public B4XPath Initialize(float f, float f2) {
            Path path = new Path();
            path.moveTo(f, f2);
            setObject(path);
            return this;
        }

        public B4XPath InitializeOval(B4XRect b4XRect) {
            Path path = new Path();
            path.addOval(b4XRect.rf, Path.Direction.CW);
            setObject(path);
            return this;
        }

        public B4XPath InitializeArc(float f, float f2, float f3, float f4, float f5) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.arcTo(new RectF(f - f3, f2 - f3, f + f3, f2 + f3), f4, f5);
            setObject(path);
            return this;
        }

        public B4XPath InitializeRoundedRect(B4XRect b4XRect, float f) {
            Path path = new Path();
            path.addRoundRect(b4XRect.rf, f, f, Path.Direction.CW);
            setObject(path);
            return this;
        }

        public B4XPath LineTo(float f, float f2) {
            getObject().lineTo(f, f2);
            return this;
        }
    }
}
