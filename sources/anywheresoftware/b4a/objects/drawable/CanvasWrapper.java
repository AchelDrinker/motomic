package anywheresoftware.b4a.objects.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.streams.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Canvas")
public class CanvasWrapper {
    private BitmapWrapper bw;
    public Canvas canvas;
    public PorterDuffXfermode eraseMode;
    public Paint paint;
    private RectF rectF;

    public void Initialize(View view) {
        this.paint = new Paint();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(layoutParams.width, layoutParams.height, Bitmap.Config.ARGB_8888);
        android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(bitmapCreateBitmap);
        this.canvas = new Canvas(bitmapCreateBitmap);
        if (view.getBackground() != null) {
            view.getBackground().setBounds(0, 0, layoutParams.width, layoutParams.height);
            view.getBackground().draw(this.canvas);
        }
        view.setBackgroundDrawable(bitmapDrawable);
        BitmapWrapper bitmapWrapper = new BitmapWrapper();
        this.bw = bitmapWrapper;
        bitmapWrapper.setObject(bitmapCreateBitmap);
    }

    public void setAntiAlias(boolean z) {
        this.paint.setAntiAlias(z);
    }

    public boolean getAntiAlias() {
        return this.paint.isAntiAlias();
    }

    public void checkAndSetTransparent(int i) {
        if (i != 0) {
            this.paint.setXfermode(null);
            return;
        }
        if (this.eraseMode == null) {
            this.eraseMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        }
        this.paint.setXfermode(this.eraseMode);
    }

    public void Initialize2(Bitmap bitmap) {
        this.paint = new Paint();
        if (!bitmap.isMutable()) {
            throw new RuntimeException("Bitmap is not mutable.");
        }
        this.canvas = new Canvas(bitmap);
        BitmapWrapper bitmapWrapper = new BitmapWrapper();
        this.bw = bitmapWrapper;
        bitmapWrapper.setObject(bitmap);
    }

    public void DrawLine(float f, float f2, float f3, float f4, int i, float f5) {
        checkAndSetTransparent(i);
        this.paint.setColor(i);
        this.paint.setStrokeWidth(f5);
        this.canvas.drawLine(f, f2, f3, f4, this.paint);
    }

    public void DrawColor(int i) {
        if (i == 0) {
            this.canvas.drawColor(i, PorterDuff.Mode.CLEAR);
        } else {
            this.canvas.drawColor(i);
        }
    }

    public void DrawOval(Rect rect, int i, boolean z, float f) {
        checkAndSetTransparent(i);
        this.paint.setColor(i);
        this.paint.setStyle(z ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(f);
        if (this.rectF == null) {
            this.rectF = new RectF();
        }
        this.rectF.set(rect);
        this.canvas.drawOval(this.rectF, this.paint);
    }

    public void DrawOvalRotated(Rect rect, int i, boolean z, float f, float f2) {
        this.canvas.save();
        try {
            this.canvas.rotate(f2, rect.centerX(), rect.centerY());
            DrawOval(rect, i, z, f);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawRect(Rect rect, int i, boolean z, float f) {
        checkAndSetTransparent(i);
        this.paint.setColor(i);
        this.paint.setStyle(z ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(f);
        this.canvas.drawRect(rect, this.paint);
    }

    public void DrawRectRotated(Rect rect, int i, boolean z, float f, float f2) {
        this.canvas.save();
        try {
            this.canvas.rotate(f2, rect.centerX(), rect.centerY());
            DrawRect(rect, i, z, f);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawCircle(float f, float f2, float f3, int i, boolean z, float f4) {
        checkAndSetTransparent(i);
        this.paint.setColor(i);
        this.paint.setStyle(z ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(f4);
        this.canvas.drawCircle(f, f2, f3, this.paint);
    }

    public void DrawBitmap(Bitmap bitmap, Rect rect, Rect rect2) {
        this.canvas.drawBitmap(bitmap, rect, rect2, (Paint) null);
    }

    public void DrawBitmapRotated(Bitmap bitmap, Rect rect, Rect rect2, float f) {
        this.canvas.save();
        try {
            this.canvas.rotate(f, rect2.centerX(), rect2.centerY());
            DrawBitmap(bitmap, rect, rect2);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawBitmapFlipped(Bitmap bitmap, Rect rect, Rect rect2, boolean z, boolean z2) {
        this.canvas.save();
        try {
            Canvas canvas = this.canvas;
            int i = -1;
            float f = z2 ? -1 : 1;
            if (!z) {
                i = 1;
            }
            canvas.scale(f, i, rect2.centerX(), rect2.centerY());
            DrawBitmap(bitmap, rect, rect2);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawText(BA ba, String str, float f, float f2, Typeface typeface, float f3, int i, Paint.Align align) {
        checkAndSetTransparent(i);
        this.paint.setTextAlign(align);
        this.paint.setTextSize(f3 * ba.context.getResources().getDisplayMetrics().scaledDensity);
        this.paint.setTypeface(typeface);
        this.paint.setColor(i);
        boolean zIsAntiAlias = this.paint.isAntiAlias();
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(0.0f);
        this.paint.setStyle(Paint.Style.FILL);
        this.canvas.drawText(str, f, f2, this.paint);
        this.paint.setAntiAlias(zIsAntiAlias);
    }

    public void DrawTextRotated(BA ba, String str, float f, float f2, Typeface typeface, float f3, int i, Paint.Align align, float f4) {
        this.canvas.save();
        try {
            this.canvas.rotate(f4, f, f2);
            DrawText(ba, str, f, f2, typeface, f3, i, align);
        } finally {
            this.canvas.restore();
        }
    }

    public float MeasureStringWidth(String str, Typeface typeface, float f) {
        this.paint.setTextSize(f * BA.applicationContext.getResources().getDisplayMetrics().scaledDensity);
        this.paint.setTypeface(typeface);
        this.paint.setStrokeWidth(0.0f);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setTextAlign(Paint.Align.LEFT);
        return this.paint.measureText(str);
    }

    public float MeasureStringHeight(String str, Typeface typeface, float f) {
        this.paint.setTextSize(f * BA.applicationContext.getResources().getDisplayMetrics().scaledDensity);
        this.paint.setTypeface(typeface);
        this.paint.setStrokeWidth(0.0f);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setTextAlign(Paint.Align.LEFT);
        this.paint.getTextBounds(str, 0, str.length(), new Rect());
        return r4.height();
    }

    public void DrawPoint(float f, float f2, int i) {
        checkAndSetTransparent(i);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(0.0f);
        this.paint.setColor(i);
        this.canvas.drawPoint(f, f2, this.paint);
    }

    public void DrawDrawable(Drawable drawable, Rect rect) {
        Rect rectCopyBounds = drawable.copyBounds();
        drawable.setBounds(rect);
        drawable.draw(this.canvas);
        drawable.setBounds(rectCopyBounds);
    }

    public void DrawDrawableRotate(Drawable drawable, Rect rect, float f) {
        this.canvas.save();
        try {
            this.canvas.rotate(f, rect.centerX(), rect.centerY());
            DrawDrawable(drawable, rect);
        } finally {
            this.canvas.restore();
        }
    }

    public void DrawPath(Path path, int i, boolean z, float f) {
        checkAndSetTransparent(i);
        this.paint.setColor(i);
        this.paint.setStyle(z ? Paint.Style.FILL : Paint.Style.STROKE);
        this.paint.setStrokeWidth(f);
        this.canvas.drawPath(path, this.paint);
    }

    public void ClipPath(Path path) {
        this.canvas.save();
        this.canvas.clipPath(path);
    }

    public void RemoveClip() {
        this.canvas.restore();
    }

    public BitmapWrapper getBitmap() {
        return this.bw;
    }

    @BA.ShortName("Bitmap")
    public static class BitmapWrapper extends AbsObjectWrapper<Bitmap> {
        public void Initialize(String str, String str2) throws IOException {
            File.InputStreamWrapper inputStreamWrapperOpenInput = null;
            try {
                File file = Common.File;
                inputStreamWrapperOpenInput = File.OpenInput(str, str2);
                Initialize2(inputStreamWrapperOpenInput.getObject());
                inputStreamWrapperOpenInput.Close();
            } catch (OutOfMemoryError unused) {
                System.gc();
                inputStreamWrapperOpenInput.Close();
                BA.Log("Downsampling image due to lack of memory.");
                Display defaultDisplay = ((WindowManager) BA.applicationContext.getSystemService("window")).getDefaultDisplay();
                InitializeSample(str, str2, defaultDisplay.getWidth() / 2, defaultDisplay.getHeight() / 2);
            }
        }

        public void Initialize2(InputStream inputStream) {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
            if (bitmapDecodeStream == null) {
                throw new RuntimeException("Error loading bitmap.");
            }
            bitmapDecodeStream.setDensity(160);
            setObject(bitmapDecodeStream);
        }

        public void InitializeResize(String str, String str2, int i, int i2, boolean z) throws IOException {
            setObject(initializeSampleImpl(str, str2, i, i2));
            setObject(Resize(i, i2, z).getObject());
        }

        public BitmapWrapper Resize(float f, float f2, boolean z) {
            if (z) {
                float width = getObject().getWidth();
                float height = getObject().getHeight();
                float fMax = Math.max(height / f2, width / f);
                f2 = height / fMax;
                f = width / fMax;
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(getObject(), Math.round(f), Math.round(f2), true);
            bitmapCreateScaledBitmap.setDensity(Math.round(BA.applicationContext.getResources().getDisplayMetrics().density * 160.0f));
            return (BitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new BitmapWrapper(), bitmapCreateScaledBitmap);
        }

        public BitmapWrapper Rotate(float f) {
            Matrix matrix = new Matrix();
            matrix.postRotate(f);
            return (BitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new BitmapWrapper(), Bitmap.createBitmap(getObject(), 0, 0, getObject().getWidth(), getObject().getHeight(), matrix, true));
        }

        public BitmapWrapper Crop(int i, int i2, int i3, int i4) {
            return (BitmapWrapper) AbsObjectWrapper.ConvertToWrapper(new BitmapWrapper(), Bitmap.createBitmap(getObject(), i, i2, Math.min(i3, getObject().getWidth() - i), Math.min(i4, getObject().getHeight() - i2), (Matrix) null, true));
        }

        public void InitializeSample(String str, String str2, int i, int i2) throws IOException {
            setObject(initializeSampleImpl(str, str2, i, i2));
        }

        private static Bitmap initializeSampleImpl(String str, String str2, int i, int i2) throws IOException {
            BitmapFactory.Options options;
            File file = Common.File;
            File.InputStreamWrapper inputStreamWrapperOpenInput = File.OpenInput(str, str2);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamWrapperOpenInput.getObject(), null, options2);
            inputStreamWrapperOpenInput.Close();
            float fMax = Math.max(options2.outWidth / i, options2.outHeight / i2);
            if (fMax > 1.0f) {
                options = new BitmapFactory.Options();
                options.inSampleSize = (int) fMax;
            } else {
                options = null;
            }
            boolean z = false;
            int i3 = 5;
            Bitmap bitmapDecodeStream = null;
            while (i3 > 0) {
                try {
                    File file2 = Common.File;
                    inputStreamWrapperOpenInput = File.OpenInput(str, str2);
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamWrapperOpenInput.getObject(), null, options);
                    inputStreamWrapperOpenInput.Close();
                    break;
                } catch (OutOfMemoryError unused) {
                    if (inputStreamWrapperOpenInput != null) {
                        inputStreamWrapperOpenInput.Close();
                    }
                    System.gc();
                    if (options == null) {
                        BitmapFactory.Options options3 = new BitmapFactory.Options();
                        options3.inSampleSize = 1;
                        options = options3;
                    }
                    options.inSampleSize *= 2;
                    BA.Log("Downsampling image due to lack of memory: " + options.inSampleSize);
                    i3 += -1;
                    z = true;
                }
            }
            if (bitmapDecodeStream != null) {
                bitmapDecodeStream.setDensity(160);
                return bitmapDecodeStream;
            }
            if (z) {
                throw new RuntimeException("Error loading bitmap (OutOfMemoryError)");
            }
            throw new RuntimeException("Error loading bitmap.");
        }

        public void Initialize3(Bitmap bitmap) {
            setObject(Bitmap.createBitmap(bitmap));
        }

        public void InitializeMutable(int i, int i2) {
            setObject(Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888));
        }

        public int GetPixel(int i, int i2) {
            return getObject().getPixel(i, i2);
        }

        public int getWidth() {
            return getObject().getWidth();
        }

        public int getHeight() {
            return getObject().getHeight();
        }

        public float getScale() {
            return getObject().getDensity() / 160.0f;
        }

        public void WriteToStream(OutputStream outputStream, int i, Bitmap.CompressFormat compressFormat) {
            getObject().compress(compressFormat, i, outputStream);
        }

        @Override // anywheresoftware.b4a.AbsObjectWrapper
        public String toString() {
            String strBaseToString = baseToString();
            if (!IsInitialized()) {
                return strBaseToString;
            }
            return String.valueOf(strBaseToString) + ": " + getWidth() + " x " + getHeight() + ", scale = " + String.format("%.2f", Float.valueOf(getScale()));
        }
    }

    @BA.ShortName("Rect")
    public static class RectWrapper extends AbsObjectWrapper<Rect> {
        public void Initialize(int i, int i2, int i3, int i4) {
            setObject(new Rect(i, i2, i3, i4));
        }

        public int getLeft() {
            return getObject().left;
        }

        public void setLeft(int i) {
            getObject().left = i;
        }

        public int getTop() {
            return getObject().top;
        }

        public void setTop(int i) {
            getObject().top = i;
        }

        public int getRight() {
            return getObject().right;
        }

        public void setRight(int i) {
            getObject().right = i;
        }

        public int getBottom() {
            return getObject().bottom;
        }

        public void setBottom(int i) {
            getObject().bottom = i;
        }

        public int getWidth() {
            return getObject().right - getObject().left;
        }

        public void setWidth(int i) {
            getObject().right = getObject().left + i;
        }

        public int getHeight() {
            return getObject().bottom - getObject().top;
        }

        public void setHeight(int i) {
            getObject().bottom = getObject().top + i;
        }

        public int getCenterX() {
            return getObject().centerX();
        }

        public int getCenterY() {
            return getObject().centerY();
        }

        @Override // anywheresoftware.b4a.AbsObjectWrapper
        public String toString() {
            String strBaseToString = baseToString();
            if (!IsInitialized()) {
                return strBaseToString;
            }
            return String.valueOf(strBaseToString) + "(" + getLeft() + ", " + getTop() + ", " + getRight() + ", " + getBottom() + ")";
        }
    }

    @BA.ShortName("Path")
    public static class PathWrapper extends AbsObjectWrapper<Path> {
        public void Initialize(float f, float f2) {
            Path path = new Path();
            path.moveTo(f, f2);
            setObject(path);
        }

        public void LineTo(float f, float f2) {
            getObject().lineTo(f, f2);
        }
    }
}
