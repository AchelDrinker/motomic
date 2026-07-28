package anywheresoftware.b4a.objects;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import anywheresoftware.b4a.BA;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("SeekBar")
public class SeekBarWrapper extends ViewWrapper<SeekBar> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new SeekBar(ba.context));
        }
        super.innerInitialize(ba, str, true);
        if (ba.subExists(String.valueOf(str) + "_valuechanged")) {
            ((SeekBar) getObject()).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: anywheresoftware.b4a.objects.SeekBarWrapper.1
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z2) {
                    ba.raiseEventFromUI(SeekBarWrapper.this.getObject(), String.valueOf(str) + "_valuechanged", Integer.valueOf(i), Boolean.valueOf(z2));
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMax() {
        return ((SeekBar) getObject()).getMax();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMax(int i) {
        ((SeekBar) getObject()).setMax(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getValue() {
        return ((SeekBar) getObject()).getProgress();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setValue(int i) {
        ((SeekBar) getObject()).setProgress(i);
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        if (obj == null) {
            obj = ViewWrapper.buildNativeView((Context) obj2, SeekBar.class, map, z);
        }
        SeekBar seekBar = (SeekBar) ViewWrapper.build(obj, map, z);
        int max = seekBar.getMax();
        seekBar.setMax(((Integer) map.get("max")).intValue());
        if (seekBar.getMax() != max) {
            seekBar.setProgress(-1);
        }
        seekBar.setProgress(((Integer) map.get("value")).intValue());
        return seekBar;
    }
}
