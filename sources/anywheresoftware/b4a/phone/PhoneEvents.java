package anywheresoftware.b4a.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.IntentWrapper;
import anywheresoftware.b4a.phone.Phone;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("PhoneEvents")
public class PhoneEvents {
    private BA ba;
    private BroadcastReceiver br;
    private String ev;
    private HashMap<String, ActionHandler> map;

    public PhoneEvents() {
        HashMap<String, ActionHandler> map = new HashMap<>();
        this.map = map;
        map.put("android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_texttospeechfinish";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.net.conn.CONNECTIVITY_CHANGE", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_connectivitychanged";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                send(intent, new Object[]{networkInfo.getTypeName(), networkInfo.getState().toString()});
            }
        });
        this.map.put("android.intent.action.USER_PRESENT", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_userpresent";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.intent.action.ACTION_SHUTDOWN", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_shutdown";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.intent.action.SCREEN_ON", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_screenon";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.intent.action.SCREEN_OFF", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_screenoff";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.intent.action.PACKAGE_REMOVED", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_packageremoved";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getDataString()});
            }
        });
        this.map.put("android.intent.action.PACKAGE_ADDED", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_packageadded";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getDataString()});
            }
        });
        this.map.put("android.intent.action.DEVICE_STORAGE_LOW", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_devicestoragelow";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("b4a.smssent", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.10
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_smssentstatus";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                String str;
                int i = this.resultCode;
                if (i == -1) {
                    str = "OK";
                } else if (i == 1) {
                    str = "GENERIC_FAILURE";
                } else if (i == 2) {
                    str = "RADIO_OFF";
                } else if (i != 3) {
                    str = i != 4 ? "" : "NO_SERVICE";
                } else {
                    str = "NULL_PDU";
                }
                send(intent, new Object[]{Boolean.valueOf(this.resultCode == -1), str, intent.getStringExtra("phone")});
            }
        });
        this.map.put("b4a.smsdelivered", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.11
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_smsdelivered";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, new Object[]{intent.getStringExtra("phone")});
            }
        });
        this.map.put("android.intent.action.DEVICE_STORAGE_OK", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.12
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_devicestorageok";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, null);
            }
        });
        this.map.put("android.intent.action.BATTERY_CHANGED", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.13
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_batterychanged";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, new Object[]{Integer.valueOf(intent.getIntExtra("level", 0)), Integer.valueOf(intent.getIntExtra("scale", 1)), Boolean.valueOf(intent.getIntExtra("plugged", 0) > 0)});
            }
        });
        this.map.put("android.intent.action.AIRPLANE_MODE", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.14
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_airplanemodechanged";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                send(intent, new Object[]{Boolean.valueOf(intent.getBooleanExtra("state", false))});
            }
        });
        for (Map.Entry<String, ActionHandler> entry : this.map.entrySet()) {
            entry.getValue().action = entry.getKey();
        }
    }

    public void InitializeWithPhoneState(BA ba, String str, Phone.PhoneId phoneId) {
        this.map.put("android.intent.action.PHONE_STATE", new ActionHandler(this) { // from class: anywheresoftware.b4a.phone.PhoneEvents.15
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this, null);
                this.event = "_phonestatechanged";
            }

            @Override // anywheresoftware.b4a.phone.PhoneEvents.ActionHandler
            public void handle(Intent intent) {
                String stringExtra = intent.getStringExtra("state");
                String stringExtra2 = intent.getStringExtra("incoming_number");
                if (stringExtra2 == null) {
                    stringExtra2 = "";
                }
                send(intent, new Object[]{stringExtra, stringExtra2});
            }
        });
        this.map.get("android.intent.action.PHONE_STATE").action = "android.intent.action.PHONE_STATE";
        Initialize(ba, str);
    }

    public void Initialize(BA ba, String str) {
        this.ba = ba;
        this.ev = str.toLowerCase(BA.cul);
        StopListening();
        this.br = new BroadcastReceiver() { // from class: anywheresoftware.b4a.phone.PhoneEvents.16
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ActionHandler actionHandler;
                if (intent.getAction() == null || (actionHandler = (ActionHandler) PhoneEvents.this.map.get(intent.getAction())) == null) {
                    return;
                }
                actionHandler.resultCode = getResultCode();
                actionHandler.handle(intent);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        IntentFilter intentFilter2 = null;
        for (ActionHandler actionHandler : this.map.values()) {
            if (ba.subExists(String.valueOf(this.ev) + actionHandler.event)) {
                if (actionHandler.action == "android.intent.action.PACKAGE_ADDED" || actionHandler.action == "android.intent.action.PACKAGE_REMOVED") {
                    if (intentFilter2 == null) {
                        intentFilter2 = new IntentFilter();
                        intentFilter2.addDataScheme("package");
                    }
                    intentFilter2.addAction(actionHandler.action);
                }
                intentFilter.addAction(actionHandler.action);
            }
        }
        registerReceiver(this.br, intentFilter, true);
        if (intentFilter2 != null) {
            registerReceiver(this.br, intentFilter2, true);
        }
    }

    public static void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, boolean z) {
        if (Build.VERSION.SDK_INT >= 33) {
            BA.applicationContext.registerReceiver(broadcastReceiver, intentFilter, z ? 2 : 4);
        } else {
            BA.applicationContext.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public void StopListening() {
        if (this.br != null) {
            BA.applicationContext.unregisterReceiver(this.br);
        }
        this.br = null;
    }

    private abstract class ActionHandler {
        public String action;
        public String event;
        public int resultCode;

        public abstract void handle(Intent intent);

        private ActionHandler() {
        }

        /* synthetic */ ActionHandler(PhoneEvents phoneEvents, ActionHandler actionHandler) {
            this();
        }

        protected void send(Intent intent, Object[] objArr) {
            final Object[] objArr2;
            if (objArr == null) {
                objArr2 = new Object[1];
            } else {
                Object[] objArr3 = new Object[objArr.length + 1];
                System.arraycopy(objArr, 0, objArr3, 0, objArr.length);
                objArr2 = objArr3;
            }
            objArr2[objArr2.length - 1] = AbsObjectWrapper.ConvertToWrapper(new IntentWrapper(), intent);
            if (!BA.debugMode) {
                PhoneEvents.this.ba.raiseEvent(this, String.valueOf(PhoneEvents.this.ev) + this.event, objArr2);
                return;
            }
            BA.handler.post(new BA.B4ARunnable() { // from class: anywheresoftware.b4a.phone.PhoneEvents.ActionHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    PhoneEvents.this.ba.raiseEvent(this, String.valueOf(PhoneEvents.this.ev) + ActionHandler.this.event, objArr2);
                }
            });
        }
    }

    @BA.ShortName("SmsInterceptor")
    public static class SMSInterceptor {
        private BA ba;
        private BroadcastReceiver br;
        private String eventName;

        public void Initialize(String str, BA ba) {
            Initialize2(str, ba, 0);
        }

        public void ListenToOutgoingMessages() {
            final Uri uri = Uri.parse("content://sms");
            BA.applicationContext.getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) { // from class: anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    Cursor cursorQuery = BA.applicationContext.getContentResolver().query(uri, null, null, null, null);
                    if (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("protocol"));
                        int i = cursorQuery.getInt(cursorQuery.getColumnIndex("type"));
                        if (string == null && i == 2) {
                            SMSInterceptor.this.ba.raiseEvent(null, String.valueOf(SMSInterceptor.this.eventName) + "_messagesent", Integer.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndex("_id"))));
                            cursorQuery.close();
                        }
                    }
                }
            });
        }

        public void Initialize2(String str, final BA ba, int i) {
            this.ba = ba;
            this.eventName = str.toLowerCase(BA.cul);
            this.br = new BroadcastReceiver() { // from class: anywheresoftware.b4a.phone.PhoneEvents.SMSInterceptor.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Bundle extras;
                    if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") && (extras = intent.getExtras()) != null) {
                        for (Object obj : (Object[]) extras.get("pdus")) {
                            SmsMessage smsMessageCreateFromPdu = SmsMessage.createFromPdu((byte[]) obj);
                            Boolean bool = (Boolean) ba.raiseEvent(SMSInterceptor.this, String.valueOf(SMSInterceptor.this.eventName) + "_messagereceived", smsMessageCreateFromPdu.getOriginatingAddress(), smsMessageCreateFromPdu.getMessageBody());
                            if (bool != null && bool.booleanValue()) {
                                abortBroadcast();
                            }
                        }
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
            intentFilter.setPriority(i);
            PhoneEvents.registerReceiver(this.br, intentFilter, true);
        }

        public void StopListening() {
            if (this.br != null) {
                BA.applicationContext.unregisterReceiver(this.br);
            }
            this.br = null;
        }
    }
}
