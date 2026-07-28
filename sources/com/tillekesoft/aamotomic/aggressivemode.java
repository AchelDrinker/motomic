package com.tillekesoft.aamotomic;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.streams.File;

/* JADX INFO: loaded from: classes.dex */
public class aggressivemode {
    private static aggressivemode mostCurrent = new aggressivemode();
    public static String _aggressive_mode_file = "";
    public Common __c = null;
    public main _vvvvvv5 = null;
    public starter _vvvvvv2 = null;
    public audioservice _vvvvv5 = null;
    public btreceiver _vvvvvv3 = null;
    public diaglog _vvvvvv4 = null;
    public readmeviewer _vvvvv2 = null;

    public static Object getObject() {
        throw new RuntimeException("Code module does not support this method.");
    }

    public static boolean _v5(BA ba) throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        if (!File.Exists(File.getDirInternal(), _aggressive_mode_file)) {
            return false;
        }
        try {
            File file3 = Common.File;
            File file4 = Common.File;
            return File.ReadString(File.getDirInternal(), _aggressive_mode_file).toLowerCase().equals("true");
        } catch (Exception e) {
            if (ba.processBA != null) {
                ba = ba.processBA;
            }
            ba.setLastException(e);
            return false;
        }
    }

    public static String _process_globals() throws Exception {
        _aggressive_mode_file = main.vvv13(new byte[]{50, 34, 48, 96, 54, 46, 44, 46, 42, 51, 30, 43, 48, 40, 43, 60, 50, 45, 42}, 937640);
        return "";
    }

    public static String _v6(BA ba, boolean z) throws Exception {
        File file = Common.File;
        File file2 = Common.File;
        File.WriteString(File.getDirInternal(), _aggressive_mode_file, BA.ObjectToString(Boolean.valueOf(z)));
        return "";
    }
}
