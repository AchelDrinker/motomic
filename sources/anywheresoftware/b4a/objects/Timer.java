package anywheresoftware.b4a.objects;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.Msgbox;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Timer")
public class Timer implements BA.CheckForReinitialize {
    private BA ba;
    private String eventName;
    private long interval;
    private boolean enabled = false;
    private int relevantTimer = 0;
    private ParentReference myRef = new ParentReference();

    public void Initialize(BA ba, String str, long j) {
        this.interval = j;
        this.ba = ba;
        this.eventName = String.valueOf(str.toLowerCase(BA.cul)) + "_tick";
    }

    @Override // anywheresoftware.b4a.BA.CheckForReinitialize
    public boolean IsInitialized() {
        return this.ba != null;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setInterval(long j) {
        if (this.interval == j) {
            return;
        }
        this.interval = j;
        if (this.enabled) {
            stopTicking();
            startTicking();
        }
    }

    public long getInterval() {
        return this.interval;
    }

    private void startTicking() {
        BA.handler.postDelayed(new TickTack(this.relevantTimer, this.myRef, this.ba), this.interval);
    }

    public void setEnabled(boolean z) {
        if (z == this.enabled) {
            return;
        }
        if (z) {
            this.myRef.timer = this;
            if (this.interval <= 0) {
                throw new IllegalStateException("Interval must be larger than 0.");
            }
            startTicking();
        } else {
            this.myRef.timer = null;
            stopTicking();
        }
        this.enabled = z;
    }

    static class TickTack implements Runnable {
        private final BA ba;
        private final int currentTimer;
        private final ParentReference parent;

        public TickTack(int i, ParentReference parentReference, BA ba) {
            this.currentTimer = i;
            this.parent = parentReference;
            this.ba = ba;
        }

        @Override // java.lang.Runnable
        public void run() {
            Timer timer = this.parent.timer;
            if (timer == null || this.currentTimer != timer.relevantTimer) {
                return;
            }
            BA.handler.postDelayed(this, timer.interval);
            if (this.ba.isActivityPaused() || Msgbox.msgboxIsVisible()) {
                return;
            }
            this.ba.raiseEvent2(timer, false, timer.eventName, true, new Object[0]);
        }
    }

    static class ParentReference {
        public Timer timer;

        ParentReference() {
        }
    }

    private void stopTicking() {
        this.relevantTimer++;
    }
}
