package kotlin.internal;

import androidx.core.app.Person$$ExternalSyntheticBackport0;
import kotlin.Metadata;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.UInt;
import kotlin.ULong;

/* JADX INFO: compiled from: UProgressionUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0005\u0010\u0006\u001a'\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\b\u0010\t\u001a'\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001¢\u0006\u0004\b\u000f\u0010\u0006\u001a'\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001¢\u0006\u0004\b\u0011\u0010\t¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UProgressionUtilKt {
    /* JADX INFO: renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m1252differenceModuloWZ9TVnA(int i, int i2, int i3) {
        int iM$1 = UByte$$ExternalSyntheticBackport0.m$1(i, i3);
        int iM$12 = UByte$$ExternalSyntheticBackport0.m$1(i2, i3);
        int iM = Person$$ExternalSyntheticBackport0.m(iM$1 ^ Integer.MIN_VALUE, iM$12 ^ Integer.MIN_VALUE);
        int iM141constructorimpl = UInt.m141constructorimpl(iM$1 - iM$12);
        return iM >= 0 ? iM141constructorimpl : UInt.m141constructorimpl(iM141constructorimpl + i3);
    }

    /* JADX INFO: renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m1253differenceModulosambcqE(long j, long j2, long j3) {
        long jM114m = UByte$$ExternalSyntheticBackport0.m114m(j, j3);
        long jM114m2 = UByte$$ExternalSyntheticBackport0.m114m(j2, j3);
        int iM = UByte$$ExternalSyntheticBackport0.m(jM114m, jM114m2);
        long jM220constructorimpl = ULong.m220constructorimpl(jM114m - jM114m2);
        return iM >= 0 ? jM220constructorimpl : ULong.m220constructorimpl(jM220constructorimpl + j3);
    }

    /* JADX INFO: renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m1255getProgressionLastElementNkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            if (Person$$ExternalSyntheticBackport0.m(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) < 0) {
                return UInt.m141constructorimpl(i2 - m1252differenceModuloWZ9TVnA(i2, i, UInt.m141constructorimpl(i3)));
            }
        } else if (i3 < 0) {
            if (Person$$ExternalSyntheticBackport0.m(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) > 0) {
                return UInt.m141constructorimpl(i2 + m1252differenceModuloWZ9TVnA(i, i2, UInt.m141constructorimpl(-i3)));
            }
        } else {
            throw new IllegalArgumentException("Step is zero.");
        }
        return i2;
    }

    /* JADX INFO: renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m1254getProgressionLastElement7ftBX0g(long j, long j2, long j3) {
        if (j3 > 0) {
            return UByte$$ExternalSyntheticBackport0.m(j, j2) >= 0 ? j2 : ULong.m220constructorimpl(j2 - m1253differenceModulosambcqE(j2, j, ULong.m220constructorimpl(j3)));
        }
        if (j3 < 0) {
            return UByte$$ExternalSyntheticBackport0.m(j, j2) <= 0 ? j2 : ULong.m220constructorimpl(j2 + m1253differenceModulosambcqE(j, j2, ULong.m220constructorimpl(-j3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
