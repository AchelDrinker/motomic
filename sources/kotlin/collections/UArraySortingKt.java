package kotlin.collections;

import androidx.core.app.Person$$ExternalSyntheticBackport0;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\t\u0010\n\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\f\u0010\r\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0013\u0010\u0014\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0015\u0010\u0016\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u0014\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0016\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0018\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UArraySortingKt {
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m502partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM122getw2LRezQ = UByteArray.m122getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM122getw2LRezQ = UByteArray.m122getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = bM122getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM122getw2LRezQ, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m122getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM122getw2LRezQ2 = UByteArray.m122getw2LRezQ(bArr, i);
                UByteArray.m127setVurrAj0(bArr, i, UByteArray.m122getw2LRezQ(bArr, i2));
                UByteArray.m127setVurrAj0(bArr, i2, bM122getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m506quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM502partition4UcCI2c = m502partition4UcCI2c(bArr, i, i2);
        int i3 = iM502partition4UcCI2c - 1;
        if (i < i3) {
            m506quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM502partition4UcCI2c < i2) {
            m506quickSort4UcCI2c(bArr, iM502partition4UcCI2c, i2);
        }
    }

    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m503partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM385getMh2AYeg = UShortArray.m385getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM385getMh2AYeg = UShortArray.m385getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM385getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM385getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m385getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM385getMh2AYeg2 = UShortArray.m385getMh2AYeg(sArr, i);
                UShortArray.m390set01HTLdE(sArr, i, UShortArray.m385getMh2AYeg(sArr, i2));
                UShortArray.m390set01HTLdE(sArr, i2, sM385getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m507quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM503partitionAa5vz7o = m503partitionAa5vz7o(sArr, i, i2);
        int i3 = iM503partitionAa5vz7o - 1;
        if (i < i3) {
            m507quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM503partitionAa5vz7o < i2) {
            m507quickSortAa5vz7o(sArr, iM503partitionAa5vz7o, i2);
        }
    }

    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m504partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM201getpVg5ArA = UIntArray.m201getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Person$$ExternalSyntheticBackport0.m(UIntArray.m201getpVg5ArA(iArr, i) ^ Integer.MIN_VALUE, iM201getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i++;
            }
            while (Person$$ExternalSyntheticBackport0.m(UIntArray.m201getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM201getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM201getpVg5ArA2 = UIntArray.m201getpVg5ArA(iArr, i);
                UIntArray.m206setVXSXFK8(iArr, i, UIntArray.m201getpVg5ArA(iArr, i2));
                UIntArray.m206setVXSXFK8(iArr, i2, iM201getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m508quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM504partitionoBK06Vg = m504partitionoBK06Vg(iArr, i, i2);
        int i3 = iM504partitionoBK06Vg - 1;
        if (i < i3) {
            m508quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM504partitionoBK06Vg < i2) {
            m508quickSortoBK06Vg(iArr, iM504partitionoBK06Vg, i2);
        }
    }

    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m501partitionnroSd4(long[] jArr, int i, int i2) {
        long jM280getsVKNKU = ULongArray.m280getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UByte$$ExternalSyntheticBackport0.m(ULongArray.m280getsVKNKU(jArr, i), jM280getsVKNKU) < 0) {
                i++;
            }
            while (UByte$$ExternalSyntheticBackport0.m(ULongArray.m280getsVKNKU(jArr, i2), jM280getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM280getsVKNKU2 = ULongArray.m280getsVKNKU(jArr, i);
                ULongArray.m285setk8EXiF4(jArr, i, ULongArray.m280getsVKNKU(jArr, i2));
                ULongArray.m285setk8EXiF4(jArr, i2, jM280getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m505quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM501partitionnroSd4 = m501partitionnroSd4(jArr, i, i2);
        int i3 = iM501partitionnroSd4 - 1;
        if (i < i3) {
            m505quickSortnroSd4(jArr, i, i3);
        }
        if (iM501partitionnroSd4 < i2) {
            m505quickSortnroSd4(jArr, iM501partitionnroSd4, i2);
        }
    }

    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m510sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m506quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m511sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m507quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m512sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m508quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m509sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m505quickSortnroSd4(array, i, i2 - 1);
    }
}
