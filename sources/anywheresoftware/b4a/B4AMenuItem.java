package anywheresoftware.b4a;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class B4AMenuItem {
    public final boolean addToBar;
    public final Drawable drawable;
    public final String eventName;
    public final CharSequence title;

    public B4AMenuItem(CharSequence charSequence, Drawable drawable, String str, boolean z) {
        this.title = charSequence;
        this.drawable = drawable;
        this.eventName = str;
        this.addToBar = z;
    }
}
