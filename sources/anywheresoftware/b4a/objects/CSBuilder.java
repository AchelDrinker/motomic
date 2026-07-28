package anywheresoftware.b4a.objects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("CSBuilder")
public class CSBuilder extends AbsObjectWrapper<SpannableStringBuilder> implements BA.B4aDebuggable {
    private LinkedList<SpanMark> spanOpenings() {
        return (LinkedList) AbsObjectWrapper.getExtraTags(getObject()).get("marks");
    }

    public CSBuilder Initialize() {
        setObject(new SpannableStringBuilder() { // from class: anywheresoftware.b4a.objects.CSBuilder.1
            @Override // android.text.SpannableStringBuilder
            public boolean equals(Object obj) {
                return this == obj;
            }

            @Override // android.text.SpannableStringBuilder
            public int hashCode() {
                return System.identityHashCode(this);
            }
        });
        AbsObjectWrapper.getExtraTags(getObject()).put("marks", new LinkedList());
        return this;
    }

    public CSBuilder Append(CharSequence charSequence) {
        getObject().append(charSequence);
        return this;
    }

    public CSBuilder Underline() {
        return open(new UnderlineSpan());
    }

    public CSBuilder Clickable(final BA ba, String str, final Object obj) {
        final String str2 = String.valueOf(str.toLowerCase(BA.cul)) + "_click";
        return open(new ClickableSpan() { // from class: anywheresoftware.b4a.objects.CSBuilder.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                ba.raiseEventFromUI(CSBuilder.this.getObject(), str2, obj);
            }
        });
    }

    public CSBuilder Alignment(Layout.Alignment alignment) {
        return open(new AlignmentSpan.Standard(alignment));
    }

    public CSBuilder Bold() {
        return open(new StyleSpan(1));
    }

    public CSBuilder open(Object obj) {
        spanOpenings().add(new SpanMark(obj, getObject().length()));
        return this;
    }

    public CSBuilder Pop() {
        LinkedList<SpanMark> linkedListSpanOpenings = spanOpenings();
        SpanMark spanMarkRemoveLast = linkedListSpanOpenings.removeLast();
        spanMarkRemoveLast.markEnd = getObject().length();
        linkedListSpanOpenings.addFirst(spanMarkRemoveLast);
        if (linkedListSpanOpenings.getLast().markEnd == -1) {
            return this;
        }
        for (SpanMark spanMark : linkedListSpanOpenings) {
            getObject().setSpan(spanMark.span, spanMark.markStart, spanMark.markEnd, 0);
        }
        linkedListSpanOpenings.clear();
        return this;
    }

    public CSBuilder PopAll() {
        LinkedList<SpanMark> linkedListSpanOpenings = spanOpenings();
        while (linkedListSpanOpenings.size() > 0) {
            Pop();
        }
        return this;
    }

    public CSBuilder Color(int i) {
        return open(new ForegroundColorSpan(i));
    }

    public CSBuilder BackgroundColor(int i) {
        return open(new BackgroundColorSpan(i));
    }

    public CSBuilder Size(int i) {
        return open(new AbsoluteSizeSpan(i, true));
    }

    public CSBuilder RelativeSize(float f) {
        return open(new RelativeSizeSpan(f));
    }

    public CSBuilder Typeface(Typeface typeface) {
        return open(new CustomTypefaceSpan(typeface));
    }

    public CSBuilder Strikethrough() {
        return open(new StrikethroughSpan());
    }

    public CSBuilder VerticalAlign(int i) {
        return open(new VerticalAlignedSpan(i));
    }

    public CSBuilder Image(Bitmap bitmap, int i, int i2, boolean z) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(BA.applicationContext.getResources(), bitmap);
        bitmapDrawable.setBounds(0, 0, i, i2);
        return open(new ImageSpan(bitmapDrawable, z ? 1 : 0)).Append("_").Pop();
    }

    public CSBuilder ScaleX(float f) {
        return open(new ScaleXSpan(f));
    }

    public void EnableClickEvents(TextView textView) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public int getLength() {
        return getObject().length();
    }

    public String ToString() {
        return getObject().toString();
    }

    @Override // anywheresoftware.b4a.AbsObjectWrapper
    public String toString() {
        return ToString();
    }

    @Override // anywheresoftware.b4a.BA.B4aDebuggable
    public Object[] debug(int i, boolean[] zArr) {
        Object[] objArr = {"Length", Integer.valueOf(getLength()), "ToString", ToString()};
        zArr[0] = true;
        return objArr;
    }

    public static class SpanMark {
        public int markEnd = -1;
        public final int markStart;
        public final Object span;

        public SpanMark(Object obj, int i) {
            this.span = obj;
            this.markStart = i;
        }

        public String toString() {
            return this.span.getClass() + " " + this.markStart + " -> " + this.markEnd;
        }
    }

    public static class CustomTypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;

        public CustomTypefaceSpan(Typeface typeface) {
            this.typeface = typeface;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            apply(textPaint);
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            apply(textPaint);
        }

        private void apply(Paint paint) {
            Typeface typeface = paint.getTypeface();
            int style = (typeface != null ? typeface.getStyle() : 0) & (this.typeface.getStyle() ^ (-1));
            if ((style & 1) != 0) {
                paint.setFakeBoldText(true);
            }
            if ((style & 2) != 0) {
                paint.setTextSkewX(-0.25f);
            }
            paint.setTypeface(this.typeface);
        }
    }

    public static class VerticalAlignedSpan extends MetricAffectingSpan {
        int shift;

        public VerticalAlignedSpan(int i) {
            this.shift = i;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.baselineShift += this.shift;
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            textPaint.baselineShift += this.shift;
        }
    }
}
