package androidx.core.os;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes.dex */
public interface OutcomeReceiverCompat<R, E extends Throwable> {

    /* JADX INFO: renamed from: androidx.core.os.OutcomeReceiverCompat$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onError(OutcomeReceiverCompat _this, Throwable th) {
        }
    }

    void onError(E e);

    void onResult(R r);
}
