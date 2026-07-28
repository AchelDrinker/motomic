package anywheresoftware.b4a.remotelogger;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.remotelogger.Connector;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class RemoteLogger implements Connector.MessageHandler {
    private static final byte LOGCAT_DATA = 8;
    private static final byte START_LOGCAT = 6;
    private static final byte STOP_LOGCAT = 7;
    private Connector connector = new Connector(this, calcPort());
    private Process lc;
    private Thread logcatReader;
    private volatile InputStream logcatStream;
    private volatile boolean logcatWorking;

    public RemoteLogger() {
        if (BA.bridgeLog == null) {
            BA.bridgeLog = new BA.IBridgeLog() { // from class: anywheresoftware.b4a.remotelogger.RemoteLogger.1
                @Override // anywheresoftware.b4a.BA.IBridgeLog
                public void offer(String str) {
                }
            };
        }
    }

    public void Start() {
        Thread thread = new Thread(this.connector);
        thread.setDaemon(true);
        thread.start();
    }

    private int calcPort() {
        try {
            int i = 7;
            for (byte b : BA.packageName.getBytes("UTF8")) {
                i = (i * 31) + (b & UByte.MAX_VALUE);
            }
            return (Math.abs(i) % 63500) + 1500;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // anywheresoftware.b4a.remotelogger.Connector.MessageHandler
    public void handleIncomingData(int i, InputStream inputStream) {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            if (i == 6) {
                LogCatStart(ConnectorUtils.readString(dataInputStream).split(","));
            } else if (i == 7) {
                LogCatStop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logcatData(byte[] bArr, int i) {
        ConnectorUtils.startMessage((byte) 8).put(bArr, 0, i);
        ConnectorUtils.sendMessage(this.connector);
    }

    private void LogCatStart(String[] strArr) throws InterruptedException, IOException {
        LogCatStop();
        if (this.logcatReader != null) {
            int i = 10;
            while (this.logcatReader.isAlive()) {
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                }
                Thread.sleep(50L);
                this.logcatReader.interrupt();
                i = i2;
            }
        }
        final String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = "/system/bin/logcat";
        System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
        Thread thread = new Thread(new Runnable() { // from class: anywheresoftware.b4a.remotelogger.RemoteLogger.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    RemoteLogger.this.lc = Runtime.getRuntime().exec(strArr2);
                    RemoteLogger remoteLogger = RemoteLogger.this;
                    remoteLogger.logcatStream = remoteLogger.lc.getInputStream();
                    InputStream inputStream = RemoteLogger.this.logcatStream;
                    RemoteLogger.this.logcatWorking = true;
                    byte[] bArr = new byte[8192];
                    while (true) {
                        if (!RemoteLogger.this.logcatWorking) {
                            break;
                        }
                        int i3 = inputStream.read(bArr);
                        Thread.sleep(20L);
                        while (i3 > 0 && inputStream.available() > 0 && i3 < 8192) {
                            i3 += inputStream.read(bArr, i3, 8192 - i3);
                            Thread.sleep(20L);
                        }
                        if (i3 == -1) {
                            RemoteLogger.this.logcatWorking = false;
                            break;
                        }
                        RemoteLogger.this.logcatData(bArr, i3);
                    }
                    RemoteLogger.this.lc.destroy();
                } catch (Exception e) {
                    System.out.println("Log reader error: " + e.toString());
                    if (RemoteLogger.this.lc != null) {
                        RemoteLogger.this.lc.destroy();
                    }
                }
            }
        });
        this.logcatReader = thread;
        thread.setDaemon(true);
        this.logcatReader.start();
    }

    public void LogCatStop() throws IOException {
        this.logcatWorking = false;
        if (this.logcatStream != null) {
            this.logcatStream.close();
        }
        Process process = this.lc;
        if (process != null) {
            process.destroy();
        }
    }
}
