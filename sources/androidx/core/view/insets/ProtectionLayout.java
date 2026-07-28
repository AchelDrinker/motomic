package androidx.core.view.insets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ProtectionLayout extends FrameLayout {
    private static final Object PROTECTION_VIEW = new Object();
    private ProtectionGroup mGroup;
    private final List<Protection> mProtections;

    public ProtectionLayout(Context context) {
        super(context);
        this.mProtections = new ArrayList();
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ProtectionLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProtections = new ArrayList();
    }

    public ProtectionLayout(Context context, List<Protection> list) {
        super(context);
        this.mProtections = new ArrayList();
        setProtections(list);
    }

    public void setProtections(List<Protection> list) {
        this.mProtections.clear();
        this.mProtections.addAll(list);
        if (isAttachedToWindow()) {
            removeProtectionViews();
            addProtectionViews();
            requestApplyInsets();
        }
    }

    private SystemBarStateMonitor getOrInstallSystemBarStateMonitor() {
        ViewGroup viewGroup = (ViewGroup) getRootView();
        Object tag = viewGroup.getTag(R.id.tag_system_bar_state_monitor);
        if (tag instanceof SystemBarStateMonitor) {
            return (SystemBarStateMonitor) tag;
        }
        SystemBarStateMonitor systemBarStateMonitor = new SystemBarStateMonitor(viewGroup);
        viewGroup.setTag(R.id.tag_system_bar_state_monitor, systemBarStateMonitor);
        return systemBarStateMonitor;
    }

    private void maybeUninstallSystemBarStateMonitor() {
        ViewGroup viewGroup = (ViewGroup) getRootView();
        Object tag = viewGroup.getTag(R.id.tag_system_bar_state_monitor);
        if (tag instanceof SystemBarStateMonitor) {
            SystemBarStateMonitor systemBarStateMonitor = (SystemBarStateMonitor) tag;
            if (systemBarStateMonitor.hasCallback()) {
                return;
            }
            systemBarStateMonitor.detachFromWindow();
            viewGroup.setTag(R.id.tag_system_bar_state_monitor, null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mGroup != null) {
            removeProtectionViews();
        }
        addProtectionViews();
        requestApplyInsets();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeProtectionViews();
        maybeUninstallSystemBarStateMonitor();
    }

    private void addProtectionViews() {
        if (this.mProtections.isEmpty()) {
            return;
        }
        this.mGroup = new ProtectionGroup(getOrInstallSystemBarStateMonitor(), this.mProtections);
        int childCount = getChildCount();
        int size = this.mGroup.size();
        for (int i = 0; i < size; i++) {
            addProtectionView(getContext(), i + childCount, this.mGroup.getProtection(i));
        }
    }

    private void removeProtectionViews() {
        if (this.mGroup != null) {
            removeViews(getChildCount() - this.mGroup.size(), this.mGroup.size());
            int size = this.mGroup.size();
            for (int i = 0; i < size; i++) {
                this.mGroup.getProtection(i).getAttributes().setCallback(null);
            }
            this.mGroup.dispose();
            this.mGroup = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void addProtectionView(android.content.Context r6, int r7, androidx.core.view.insets.Protection r8) {
        /*
            r5 = this;
            androidx.core.view.insets.Protection$Attributes r0 = r8.getAttributes()
            int r1 = r8.getSide()
            r2 = 1
            r3 = 4
            r4 = -1
            if (r1 == r2) goto L42
            r2 = 2
            if (r1 == r2) goto L3b
            if (r1 == r3) goto L35
            r2 = 8
            if (r1 != r2) goto L1d
            int r8 = r0.getHeight()
            r1 = 80
            goto L49
        L1d:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r0 = "Unexpected side: "
            r7.<init>(r0)
            int r8 = r8.getSide()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L35:
            int r8 = r0.getWidth()
            r1 = 5
            goto L47
        L3b:
            int r8 = r0.getHeight()
            r1 = 48
            goto L49
        L42:
            int r8 = r0.getWidth()
            r1 = 3
        L47:
            r4 = r8
            r8 = -1
        L49:
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r2.<init>(r4, r8, r1)
            androidx.core.graphics.Insets r8 = r0.getMargin()
            int r1 = r8.left
            r2.leftMargin = r1
            int r1 = r8.top
            r2.topMargin = r1
            int r1 = r8.right
            r2.rightMargin = r1
            int r8 = r8.bottom
            r2.bottomMargin = r8
            android.view.View r8 = new android.view.View
            r8.<init>(r6)
            java.lang.Object r6 = androidx.core.view.insets.ProtectionLayout.PROTECTION_VIEW
            r8.setTag(r6)
            float r6 = r0.getTranslationX()
            r8.setTranslationX(r6)
            float r6 = r0.getTranslationY()
            r8.setTranslationY(r6)
            float r6 = r0.getAlpha()
            r8.setAlpha(r6)
            boolean r6 = r0.isVisible()
            if (r6 == 0) goto L88
            r3 = 0
        L88:
            r8.setVisibility(r3)
            android.graphics.drawable.Drawable r6 = r0.getDrawable()
            r8.setBackground(r6)
            androidx.core.view.insets.ProtectionLayout$1 r6 = new androidx.core.view.insets.ProtectionLayout$1
            r6.<init>()
            r0.setCallback(r6)
            r5.addView(r8, r7, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.insets.ProtectionLayout.addProtectionView(android.content.Context, int, androidx.core.view.insets.Protection):void");
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view != null && view.getTag() != PROTECTION_VIEW) {
            ProtectionGroup protectionGroup = this.mGroup;
            int childCount = getChildCount() - (protectionGroup != null ? protectionGroup.size() : 0);
            if (i > childCount || i < 0) {
                i = childCount;
            }
        }
        super.addView(view, i, layoutParams);
    }
}
