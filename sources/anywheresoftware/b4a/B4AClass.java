package anywheresoftware.b4a;

/* JADX INFO: loaded from: classes.dex */
public interface B4AClass {
    boolean IsInitialized();

    BA getActivityBA();

    BA getBA();

    public static abstract class ImplB4AClass implements B4AClass {
        public BA ba;
        protected ImplB4AClass mostCurrent;

        @Override // anywheresoftware.b4a.B4AClass
        public BA getBA() {
            return this.ba;
        }

        @Override // anywheresoftware.b4a.B4AClass
        public BA getActivityBA() {
            BA ba = this.ba.sharedProcessBA.activityBA != null ? this.ba.sharedProcessBA.activityBA.get() : null;
            return ba == null ? this.ba : ba;
        }

        public String toString() {
            return BA.TypeToString(this, true);
        }

        @Override // anywheresoftware.b4a.B4AClass
        public boolean IsInitialized() {
            return this.ba != null;
        }
    }
}
