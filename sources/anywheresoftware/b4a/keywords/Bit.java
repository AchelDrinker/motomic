package anywheresoftware.b4a.keywords;

import anywheresoftware.b4a.objects.streams.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class Bit {
    public static int And(int i, int i2) {
        return i & i2;
    }

    public static long AndLong(long j, long j2) {
        return j & j2;
    }

    public static int Not(int i) {
        return i ^ (-1);
    }

    public static long NotLong(long j) {
        return j ^ (-1);
    }

    public static int Or(int i, int i2) {
        return i | i2;
    }

    public static long OrLong(long j, long j2) {
        return j | j2;
    }

    public static int ShiftLeft(int i, int i2) {
        return i << i2;
    }

    public static long ShiftLeftLong(long j, int i) {
        return j << i;
    }

    public static int ShiftRight(int i, int i2) {
        return i >> i2;
    }

    public static long ShiftRightLong(long j, int i) {
        return j >> i;
    }

    public static int UnsignedShiftRight(int i, int i2) {
        return i >>> i2;
    }

    public static long UnsignedShiftRightLong(long j, int i) {
        return j >>> i;
    }

    public static int Xor(int i, int i2) {
        return i ^ i2;
    }

    public static long XorLong(long j, long j2) {
        return j ^ j2;
    }

    public static String ToBinaryString(int i) {
        return Integer.toBinaryString(i);
    }

    public static String ToOctalString(int i) {
        return Integer.toOctalString(i);
    }

    public static String ToHexString(int i) {
        return Integer.toHexString(i);
    }

    public static String ToHexStringLong(long j) {
        return Long.toHexString(j);
    }

    public static int ParseInt(String str, int i) {
        return Integer.parseInt(str, i);
    }

    public static long ParseLong(String str, int i) {
        return Long.parseLong(str, i);
    }

    public static byte[] InputStreamToBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File.Copy2(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void ArrayCopy(Object obj, int i, Object obj2, int i2, int i3) {
        System.arraycopy(obj, i, obj2, i2, i3);
    }
}
