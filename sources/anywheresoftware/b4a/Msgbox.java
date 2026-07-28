package anywheresoftware.b4a;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import anywheresoftware.b4a.BA;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Msgbox {
    private static Field flagsF = null;
    public static boolean isDismissing = false;
    private static Method nextM = null;
    public static WeakReference<ProgressDialog> pd = null;
    private static Method recycleUnchecked = null;
    private static boolean stopCodeAfterDismiss = false;
    private static boolean visible = false;
    private static WeakReference<AlertDialog> visibleAD;
    private static Field whenF;
    private static Object closeMyLoop = new Object();
    private static final ArrayList<WeakReference<Dialog>> listOfAsyncDialogs = new ArrayList<>();

    static {
        try {
            Method declaredMethod = MessageQueue.class.getDeclaredMethod("next", null);
            nextM = declaredMethod;
            declaredMethod.setAccessible(true);
            Field declaredField = Message.class.getDeclaredField("when");
            whenF = declaredField;
            declaredField.setAccessible(true);
            flagsF = null;
            try {
                Field declaredField2 = Message.class.getDeclaredField("flags");
                flagsF = declaredField2;
                declaredField2.setAccessible(true);
                Method declaredMethod2 = Message.class.getDeclaredMethod("recycleUnchecked", null);
                recycleUnchecked = declaredMethod2;
                declaredMethod2.setAccessible(true);
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean msgboxIsVisible() {
        return visible;
    }

    public static boolean isItReallyAMsgboxAndNotDebug() {
        return visibleAD != null;
    }

    public static void dismiss(boolean z) {
        dismissProgressDialog();
        if (BA.debugMode) {
            try {
                Class.forName("anywheresoftware.b4a.debug.Debug").getMethod("hideProgressDialogToAvoidLeak", null).invoke(null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isDismissing = true;
        if (visible) {
            WeakReference<AlertDialog> weakReference = visibleAD;
            if (weakReference != null) {
                AlertDialog alertDialog = weakReference.get();
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            } else {
                sendCloseMyLoopMessage();
            }
            stopCodeAfterDismiss = z;
        }
        Iterator<WeakReference<Dialog>> it = listOfAsyncDialogs.iterator();
        while (it.hasNext()) {
            Dialog dialog = it.next().get();
            if (dialog != null && dialog.isShowing()) {
                try {
                    dialog.dismiss();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void trackAsyncDialog(Dialog dialog) {
        Iterator<WeakReference<Dialog>> it = listOfAsyncDialogs.iterator();
        while (it.hasNext()) {
            if (it.next().get() == null) {
                it.remove();
            }
        }
        listOfAsyncDialogs.add(new WeakReference<>(dialog));
    }

    public static void sendCloseMyLoopMessage() {
        Message messageObtain = Message.obtain();
        messageObtain.setTarget(BA.handler);
        messageObtain.obj = closeMyLoop;
        messageObtain.sendToTarget();
    }

    public static void dismissProgressDialog() {
        ProgressDialog progressDialog;
        WeakReference<ProgressDialog> weakReference = pd;
        if (weakReference == null || (progressDialog = weakReference.get()) == null) {
            return;
        }
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            BA.LogInfo("Error while dismissing ProgressDialog");
            e.printStackTrace();
        }
        pd = null;
    }

    public static class DialogResponse implements DialogInterface.OnClickListener {
        private boolean dismiss;
        public int res = -3;

        public DialogResponse(boolean z) {
            this.dismiss = z;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            this.res = i;
            if (this.dismiss) {
                ((AlertDialog) Msgbox.visibleAD.get()).dismiss();
            }
        }
    }

    public static void msgbox(AlertDialog alertDialog, boolean z) {
        if (visible) {
            return;
        }
        try {
            if (!isDismissing) {
                stopCodeAfterDismiss = false;
                Message messageObtain = Message.obtain();
                messageObtain.setTarget(BA.handler);
                messageObtain.obj = closeMyLoop;
                alertDialog.setDismissMessage(messageObtain);
                visible = true;
                visibleAD = new WeakReference<>(alertDialog);
                alertDialog.show();
                waitForMessage(false);
                if (stopCodeAfterDismiss && !z) {
                    throw new B4AUncaughtException();
                }
            }
        } finally {
            visible = false;
            visibleAD = null;
        }
    }

    public static void debugWait(Dialog dialog) {
        if (visible) {
            System.out.println("already visible");
            return;
        }
        try {
            if (isDismissing) {
                return;
            }
            stopCodeAfterDismiss = false;
            visible = true;
            waitForMessage(true);
            if (stopCodeAfterDismiss) {
                Log.w("", "throwing b4a uncaught exception");
                throw new B4AUncaughtException();
            }
        } finally {
            visible = false;
        }
    }

    public static void waitForMessage(boolean z, boolean z2) {
        waitForMessage(z2);
    }

    private static void waitForMessage(boolean z) {
        try {
            MessageQueue messageQueueMyQueue = Looper.myQueue();
            while (true) {
                Message message = (Message) nextM.invoke(messageQueueMyQueue, null);
                if (message != null) {
                    if (message.obj == closeMyLoop) {
                        recycle(message);
                        return;
                    }
                    if (message.getCallback() != null && (message.getCallback() instanceof BA.B4ARunnable)) {
                        skipMessage(message);
                    } else if (z && ((message.obj == null || !(message.obj instanceof Drawable)) && message.what >= 100 && message.what <= 150)) {
                        skipMessage(message);
                    } else {
                        message.getTarget().dispatchMessage(message);
                        recycle(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void recycle(Message message) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = recycleUnchecked;
        if (method != null) {
            method.invoke(message, null);
        } else {
            message.recycle();
        }
    }

    private static void skipMessage(Message message) throws IllegalAccessException, IllegalArgumentException {
        whenF.set(message, 0);
        Field field = flagsF;
        if (field != null) {
            flagsF.setInt(message, field.getInt(message) & (-2));
        }
        message.getTarget().sendMessage(message);
    }
}
