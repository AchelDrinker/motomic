package anywheresoftware.b4a;

import android.graphics.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ConnectorUtils {
    public static final byte BOOL = 5;
    public static final byte CACHED_STRING = 9;
    public static final byte COLOR = 6;
    public static final byte ENDOFMAP = 4;
    public static final byte FLOAT = 7;
    public static final byte INT = 1;
    public static final byte MAP = 3;
    public static final byte NULL = 12;
    public static final byte RECT32 = 11;
    public static final byte SCALED_INT = 8;
    public static final byte STRING = 2;
    private static ThreadLocal<ByteBuffer> myBb = new ThreadLocal<ByteBuffer>() { // from class: anywheresoftware.b4a.ConnectorUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public ByteBuffer initialValue() {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(51200);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            return byteBufferAllocate;
        }
    };
    private static Charset charset = Charset.forName("UTF8");

    public static ByteBuffer startMessage(byte b) {
        ByteBuffer byteBuffer = myBb.get();
        byteBuffer.clear();
        byteBuffer.put(b);
        return byteBuffer;
    }

    public static void sendMessage(ConnectorConsumer connectorConsumer) {
        byte[] bArr;
        if (connectorConsumer == null) {
            return;
        }
        ByteBuffer byteBuffer = myBb.get();
        byteBuffer.flip();
        if (connectorConsumer.shouldAddPrefix()) {
            int iLimit = byteBuffer.limit();
            bArr = new byte[iLimit + 4];
            byteBuffer.get(bArr, 4, iLimit);
            for (int i = 0; i <= 3; i++) {
                bArr[i] = (byte) (iLimit & 255);
                iLimit >>= 8;
            }
        } else {
            bArr = new byte[byteBuffer.limit()];
            byteBuffer.get(bArr);
        }
        connectorConsumer.putTask(bArr);
    }

    public static void writeInt(int i) {
        myBb.get().putInt(i);
    }

    public static void writeFloat(float f) {
        myBb.get().putFloat(f);
    }

    public static void mark() {
        myBb.get().mark();
    }

    public static void resetToMark() {
        myBb.get().reset();
    }

    public static boolean writeString(String str) {
        if (str == null) {
            str = "";
        }
        if (str.length() > 700) {
            str = String.valueOf(str.substring(0, 699)) + "......";
        }
        ByteBuffer byteBuffer = myBb.get();
        int iPosition = byteBuffer.position();
        ByteBuffer byteBufferEncode = charset.encode(str);
        if (byteBuffer.remaining() - byteBufferEncode.remaining() < 1000) {
            return false;
        }
        byteBuffer.putInt(0);
        byteBuffer.put(byteBufferEncode);
        byteBuffer.putInt(iPosition, (byteBuffer.position() - iPosition) - 4);
        return true;
    }

    public static int readInt(DataInputStream dataInputStream) throws IOException {
        return Integer.reverseBytes(dataInputStream.readInt());
    }

    public static short readShort(DataInputStream dataInputStream) throws IOException {
        return Short.reverseBytes(dataInputStream.readShort());
    }

    public static String readString(DataInputStream dataInputStream) throws IOException {
        int i = readInt(dataInputStream);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        dataInputStream.readFully(byteBufferAllocate.array());
        byteBufferAllocate.limit(i);
        return charset.decode(byteBufferAllocate).toString();
    }

    private static String readCacheString(DataInputStream dataInputStream, String[] strArr) throws IOException {
        if (strArr == null) {
            return readString(dataInputStream);
        }
        return strArr[readInt(dataInputStream)];
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [int[]] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.Float] */
    /* JADX WARN: Type inference failed for: r2v12, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v14, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.util.HashMap] */
    public static HashMap<String, Object> readMap(DataInputStream dataInputStream, String[] strArr) throws IOException {
        byte b;
        ?? ValueOf;
        HashMap map = new HashMap();
        while (true) {
            String cacheString = readCacheString(dataInputStream, strArr);
            b = dataInputStream.readByte();
            if (b != 1) {
                if (b != 9) {
                    if (b != 2) {
                        if (b != 7) {
                            if (b != 3) {
                                if (b != 5) {
                                    if (b != 6) {
                                        if (b != 12) {
                                            if (b != 11) {
                                                break;
                                            }
                                            ValueOf = new int[]{readShort(dataInputStream), readShort(dataInputStream), readShort(dataInputStream), readShort(dataInputStream)};
                                        } else {
                                            ValueOf = 0;
                                        }
                                    } else {
                                        ValueOf = Integer.valueOf(Color.argb(dataInputStream.readUnsignedByte(), dataInputStream.readUnsignedByte(), dataInputStream.readUnsignedByte(), dataInputStream.readUnsignedByte()));
                                    }
                                } else {
                                    ValueOf = Boolean.valueOf(dataInputStream.readByte() == 1);
                                }
                            } else {
                                ValueOf = readMap(dataInputStream, strArr);
                            }
                        } else {
                            ValueOf = Float.valueOf(Float.intBitsToFloat(readInt(dataInputStream)));
                        }
                    } else {
                        ValueOf = readString(dataInputStream);
                    }
                } else {
                    ValueOf = readCacheString(dataInputStream, strArr);
                }
            } else {
                ValueOf = Integer.valueOf(readInt(dataInputStream));
            }
            map.put(cacheString, ValueOf);
        }
        if (b == 4) {
            return map;
        }
        throw new RuntimeException("unknown type");
    }
}
