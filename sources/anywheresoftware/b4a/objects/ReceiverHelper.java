package anywheresoftware.b4a.objects;

import android.content.BroadcastReceiver;

/* JADX INFO: loaded from: classes.dex */
public class ReceiverHelper {
    public BroadcastReceiver.PendingResult pendingResult;
    public BroadcastReceiver receiver;

    public ReceiverHelper(BroadcastReceiver broadcastReceiver) {
        this.receiver = broadcastReceiver;
    }

    public void GoAsync() {
        this.pendingResult = this.receiver.goAsync();
    }

    public void FinishAsync() {
        this.pendingResult.finish();
    }
}
