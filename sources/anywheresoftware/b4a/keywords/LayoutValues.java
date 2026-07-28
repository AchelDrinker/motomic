package anywheresoftware.b4a.keywords;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.ConnectorUtils;
import java.io.DataInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("LayoutValues")
public class LayoutValues {
    public int Height;
    public float Scale;
    public int Width;

    public double getApproximateScreenSize() {
        if (this.Scale == 0.0f) {
            throw new RuntimeException("Scale = 0");
        }
        return Math.sqrt(Math.pow(this.Width / r0, 2.0d) + Math.pow(this.Height / this.Scale, 2.0d)) / 160.0d;
    }

    public static LayoutValues readFromStream(DataInputStream dataInputStream) throws IOException {
        LayoutValues layoutValues = new LayoutValues();
        layoutValues.Scale = Float.intBitsToFloat(ConnectorUtils.readInt(dataInputStream));
        layoutValues.Width = ConnectorUtils.readInt(dataInputStream);
        layoutValues.Height = ConnectorUtils.readInt(dataInputStream);
        return layoutValues;
    }

    public float calcDistance(LayoutValues layoutValues) {
        float f = layoutValues.Scale / this.Scale;
        float f2 = this.Width * f;
        float f3 = this.Height * f;
        double d = f2;
        int i = layoutValues.Width;
        double d2 = i;
        Double.isNaN(d2);
        if (d > d2 * 1.2d) {
            return Float.MAX_VALUE;
        }
        double d3 = f3;
        int i2 = layoutValues.Height;
        double d4 = i2;
        Double.isNaN(d4);
        if (d3 > d4 * 1.2d) {
            return Float.MAX_VALUE;
        }
        if (f2 > i) {
            f2 += 50.0f;
        }
        if (f3 > i2) {
            f3 += 50.0f;
        }
        return Math.abs(f2 - layoutValues.Width) + Math.abs(f3 - layoutValues.Height) + (Math.abs(this.Scale - layoutValues.Scale) * 100.0f) + (Math.signum(f2 - f3) == Math.signum((float) (layoutValues.Width - layoutValues.Height)) ? 0 : 100);
    }

    public String toString() {
        return this.Width + " x " + this.Height + ", scale = " + this.Scale + " (" + ((int) (this.Scale * 160.0f)) + " dpi)";
    }
}
