package anywheresoftware.b4a.remotelogger;

import android.util.Log;
import anywheresoftware.b4a.ConnectorConsumer;
import anywheresoftware.b4a.ConnectorUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class Connector implements Runnable, ConnectorConsumer {
    public static final int ALTERNATE_PORT_DELTA = 517;
    public static final int PING = 1;
    private MessageHandler handler;
    private final int port;
    private volatile Writer writer;
    public volatile boolean working = true;
    BlockingQueue<byte[]> writerQ = new ArrayBlockingQueue(20);
    private AtomicReference<Long> lastReadtime = new AtomicReference<>(0L);

    public interface MessageHandler {
        void handleIncomingData(int i, InputStream inputStream);
    }

    @Override // anywheresoftware.b4a.ConnectorConsumer
    public boolean shouldAddPrefix() {
        return true;
    }

    public Connector(MessageHandler messageHandler, int i) {
        this.handler = messageHandler;
        this.port = i;
    }

    public synchronized void UpdateMessageHandler(MessageHandler messageHandler) {
        this.handler = messageHandler;
    }

    @Override // anywheresoftware.b4a.ConnectorConsumer
    public void putTask(byte[] bArr) {
        BlockingQueue<byte[]> blockingQueue = this.writerQ;
        if (blockingQueue == null || blockingQueue.offer(bArr)) {
            return;
        }
        Log.w("", "clearing writerQ   ");
        this.writerQ.clear();
        this.writerQ.add(bArr);
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        mainLoop();
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x017b A[Catch: Exception -> 0x0177, TryCatch #7 {Exception -> 0x0177, blocks: (B:108:0x0173, B:112:0x017b, B:114:0x0181, B:115:0x0184, B:117:0x018a, B:118:0x018d), top: B:131:0x0173 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0173 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x013d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0115 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0115 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0002 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0002 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f8 A[Catch: Exception -> 0x00f4, TryCatch #9 {Exception -> 0x00f4, blocks: (B:61:0x00f0, B:65:0x00f8, B:67:0x00fe, B:68:0x0101, B:70:0x0107, B:71:0x010a), top: B:135:0x00f0 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0145 A[Catch: Exception -> 0x0141, TryCatch #18 {Exception -> 0x0141, blocks: (B:85:0x013d, B:89:0x0145, B:91:0x014b, B:92:0x014e, B:94:0x0154, B:95:0x0157), top: B:139:0x013d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void mainLoop() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 414
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anywheresoftware.b4a.remotelogger.Connector.mainLoop():void");
    }

    private void readData(InputStream inputStream) throws IOException {
        while (this.working) {
            int i = inputStream.read();
            this.lastReadtime.set(Long.valueOf(System.currentTimeMillis()));
            if (i == 1) {
                ConnectorUtils.startMessage((byte) 1);
                ConnectorUtils.sendMessage(this);
            }
            if (i == -1) {
                Log.w("", "-1 received");
                return;
            }
            if (i > 0) {
                this.handler.handleIncomingData(i, inputStream);
            }
            if (this.writer == null) {
                return;
            }
        }
    }

    void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Writer implements Runnable {
        private OutputStream out;
        public volatile boolean writerWorking = true;

        public Writer(OutputStream outputStream) {
            this.out = outputStream;
        }

        @Override // java.lang.Runnable
        public void run() {
            while (Connector.this.working && this.writerWorking) {
                try {
                    this.out.write(Connector.this.writerQ.take());
                } catch (Exception e) {
                    System.err.println("writer error");
                    e.printStackTrace();
                }
            }
            Connector.this.writer = null;
        }
    }
}
