package anywheresoftware.b4a.objects;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import anywheresoftware.b4a.BA;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("ProgressBar")
public class ProgressBarWrapper extends ViewWrapper<ProgressBar> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(BA ba, String str, boolean z) {
        if (!z) {
            ProgressBar progressBar = new ProgressBar(ba.context, null, R.attr.progressBarStyleHorizontal);
            progressBar.setIndeterminateDrawable(new ProgressBar(ba.context, null, R.attr.progressBarStyle).getIndeterminateDrawable());
            setObject(progressBar);
            ((ProgressBar) getObject()).setMax(100);
            ((ProgressBar) getObject()).setIndeterminate(false);
        }
        super.innerInitialize(ba, str, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getProgress() {
        return ((ProgressBar) getObject()).getProgress();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setProgress(int i) {
        ((ProgressBar) getObject()).setProgress(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setIndeterminate(boolean z) {
        ((ProgressBar) getObject()).setIndeterminate(z);
        ((ProgressBar) getObject()).invalidate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getIndeterminate() {
        return ((ProgressBar) getObject()).isIndeterminate();
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        boolean zBooleanValue = ((Boolean) map.get("indeterminate")).booleanValue();
        Object obj3 = obj;
        if (obj == null) {
            String str = (String) map.get("nativeClass");
            if (str != null && str.length() > 0) {
                ViewWrapper.buildNativeView((Context) obj2, ProgressBar.class, map, z);
                obj3 = obj;
            } else {
                Context context = (Context) obj2;
                ProgressBar progressBar = new ProgressBar(context, null, R.attr.progressBarStyleHorizontal);
                progressBar.setIndeterminateDrawable(new ProgressBar(context, null, R.attr.progressBarStyle).getIndeterminateDrawable());
                obj3 = progressBar;
            }
        }
        ProgressBar progressBar2 = (ProgressBar) ViewWrapper.build(obj3, map, z);
        progressBar2.setIndeterminate(zBooleanValue);
        progressBar2.setMax(100);
        if (z) {
            progressBar2.setProgress(20);
        }
        return progressBar2;
    }
}
