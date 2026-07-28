package anywheresoftware.b4a.objects.streams;

import android.net.Uri;
import android.os.Environment;
import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Bit;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.objects.collections.List;
import anywheresoftware.b4a.objects.collections.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: classes.dex */
public class File {
    public static final String ContentDir = "ContentDir";
    private static final String assetsDir = "AssetsDir";
    public static String virtualAssetsFolder;

    public static String getDirAssets() {
        return assetsDir;
    }

    public static String getDirInternal() {
        return BA.applicationContext.getFilesDir().toString();
    }

    public static String getDirInternalCache() {
        java.io.File cacheDir = BA.applicationContext.getCacheDir();
        if (cacheDir == null) {
            return getDirInternal();
        }
        return cacheDir.toString();
    }

    public static String getDirRootExternal() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static String getDirDefaultExternal() {
        java.io.File file = new java.io.File(Environment.getExternalStorageDirectory(), "/Android/data/" + BA.packageName + "/files/");
        file.mkdirs();
        return file.toString();
    }

    public static boolean Exists(String str, String str2) throws IOException {
        if (!str.equals(assetsDir)) {
            return new java.io.File(str, str2).exists();
        }
        if (virtualAssetsFolder != null) {
            return new java.io.File(virtualAssetsFolder, str2).exists();
        }
        return Arrays.asList(BA.applicationContext.getAssets().list("")).indexOf(str2.toLowerCase(BA.cul)) > -1;
    }

    public static boolean Delete(String str, String str2) {
        return new java.io.File(str, str2).delete();
    }

    public static void MakeDir(String str, String str2) {
        new java.io.File(str, str2).mkdirs();
    }

    public static long Size(String str, String str2) {
        return new java.io.File(str, str2).length();
    }

    public static long LastModified(String str, String str2) {
        return new java.io.File(str, str2).lastModified();
    }

    public static boolean IsDirectory(String str, String str2) {
        return new java.io.File(str, str2).isDirectory();
    }

    public static String Combine(String str, String str2) {
        return new java.io.File(str, str2).toString();
    }

    public static List ListFiles(String str) throws IOException {
        List list = new List();
        if (!str.equals(assetsDir)) {
            java.io.File file = new java.io.File(str);
            if (!file.isDirectory()) {
                throw new IOException(String.valueOf(str) + " is not a folder.");
            }
            String[] list2 = file.list();
            if (list2 != null) {
                list.setObject(Arrays.asList(list2));
            }
            return list;
        }
        String str2 = virtualAssetsFolder;
        if (str2 != null) {
            return ListFiles(str2);
        }
        list.setObject(Arrays.asList(BA.applicationContext.getAssets().list("")));
        return list;
    }

    public static boolean getExternalWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean getExternalReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static InputStreamWrapper OpenInput(String str, String str2) throws IOException {
        InputStreamWrapper inputStreamWrapper = new InputStreamWrapper();
        if (str.equals(assetsDir)) {
            if (virtualAssetsFolder != null) {
                inputStreamWrapper.setObject(new GZIPInputStream(new FileInputStream(new java.io.File(virtualAssetsFolder, str2.toLowerCase(BA.cul)))));
                return inputStreamWrapper;
            }
            inputStreamWrapper.setObject(BA.applicationContext.getAssets().open(str2.toLowerCase(BA.cul).replace('/', '\\')));
            return inputStreamWrapper;
        }
        if (str.equals(ContentDir)) {
            inputStreamWrapper.setObject(BA.applicationContext.getContentResolver().openInputStream(Uri.parse(str2)));
            return inputStreamWrapper;
        }
        inputStreamWrapper.setObject(new BufferedInputStream(new FileInputStream(new java.io.File(str, str2)), 4096));
        return inputStreamWrapper;
    }

    public static String GetText(String str, String str2) throws IOException {
        InputStreamWrapper inputStreamWrapperOpenInput = OpenInput(str, str2);
        TextReaderWrapper textReaderWrapper = new TextReaderWrapper();
        textReaderWrapper.Initialize(inputStreamWrapperOpenInput.getObject());
        return textReaderWrapper.ReadAll();
    }

    public static List ReadList(String str, String str2) throws IOException {
        InputStreamWrapper inputStreamWrapperOpenInput = OpenInput(str, str2);
        TextReaderWrapper textReaderWrapper = new TextReaderWrapper();
        textReaderWrapper.Initialize(inputStreamWrapperOpenInput.getObject());
        return textReaderWrapper.ReadList();
    }

    public static void WriteList(String str, String str2, List list) throws IOException {
        OutputStreamWrapper outputStreamWrapperOpenOutput = OpenOutput(str, str2, false);
        TextWriterWrapper textWriterWrapper = new TextWriterWrapper();
        textWriterWrapper.Initialize(outputStreamWrapperOpenOutput.getObject());
        textWriterWrapper.WriteList(list);
        textWriterWrapper.Close();
    }

    public static void WriteString(String str, String str2, String str3) throws IOException {
        OutputStreamWrapper outputStreamWrapperOpenOutput = OpenOutput(str, str2, false);
        TextWriterWrapper textWriterWrapper = new TextWriterWrapper();
        textWriterWrapper.Initialize(outputStreamWrapperOpenOutput.getObject());
        textWriterWrapper.Write(str3);
        textWriterWrapper.Close();
    }

    public static String ReadString(String str, String str2) throws IOException {
        InputStreamWrapper inputStreamWrapperOpenInput = OpenInput(str, str2);
        TextReaderWrapper textReaderWrapper = new TextReaderWrapper();
        textReaderWrapper.Initialize(inputStreamWrapperOpenInput.getObject());
        String strReadAll = textReaderWrapper.ReadAll();
        inputStreamWrapperOpenInput.Close();
        return strReadAll;
    }

    public static void WriteMap(String str, String str2, Map map) throws IOException {
        OutputStreamWrapper outputStreamWrapperOpenOutput = OpenOutput(str, str2, false);
        Properties properties = new Properties();
        for (Map.Entry entry : map.getObject().entrySet()) {
            properties.setProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }
        properties.store(outputStreamWrapperOpenOutput.getObject(), (String) null);
        outputStreamWrapperOpenOutput.Close();
    }

    public static anywheresoftware.b4a.objects.collections.Map ReadMap(String str, String str2) throws IOException {
        return ReadMap2(str, str2, null);
    }

    public static anywheresoftware.b4a.objects.collections.Map ReadMap2(String str, String str2, anywheresoftware.b4a.objects.collections.Map map) throws IOException {
        InputStreamWrapper inputStreamWrapperOpenInput = OpenInput(str, str2);
        Properties properties = new Properties();
        properties.load(inputStreamWrapperOpenInput.getObject());
        if (map == null) {
            map = new anywheresoftware.b4a.objects.collections.Map();
        }
        if (!map.IsInitialized()) {
            map.Initialize();
        }
        for (Map.Entry entry : properties.entrySet()) {
            map.Put(entry.getKey(), entry.getValue());
        }
        inputStreamWrapperOpenInput.Close();
        return map;
    }

    public static void Copy(String str, String str2, String str3, String str4) throws IOException {
        Delete(str3, str4);
        InputStream object = OpenInput(str, str2).getObject();
        OutputStream object2 = OpenOutput(str3, str4, false).getObject();
        Copy2(object, object2);
        object.close();
        object2.close();
    }

    public static String getUnpackedVirtualAssetFile(String str) throws IOException {
        String lowerCase = str.toLowerCase(BA.cul);
        String str2 = String.valueOf(lowerCase) + ".unpacked";
        if (!Exists(virtualAssetsFolder, str2)) {
            Copy(getDirAssets(), lowerCase, virtualAssetsFolder, str2);
        }
        return str2;
    }

    public static void Copy2(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                outputStream.write(bArr, 0, i);
            } else {
                inputStream.close();
                return;
            }
        }
    }

    public static Object Copy2Async(BA ba, final InputStream inputStream, final OutputStream outputStream) {
        Object obj = new Object();
        BA.runAsync(ba, obj, "complete", new Object[]{false}, new Callable<Object[]>() { // from class: anywheresoftware.b4a.objects.streams.File.1
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                File.Copy2(inputStream, outputStream);
                return new Object[]{true};
            }
        });
        return obj;
    }

    public static Object ListFilesAsync(BA ba, final String str) {
        Object obj = new Object();
        BA.runAsync(ba, obj, "complete", new Object[]{false, new List()}, new Callable<Object[]>() { // from class: anywheresoftware.b4a.objects.streams.File.2
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                return new Object[]{true, File.ListFiles(str)};
            }
        });
        return obj;
    }

    public static Object CopyAsync(BA ba, final String str, final String str2, final String str3, final String str4) throws IOException {
        Object obj = new Object();
        BA.runAsync(ba, obj, "complete", new Object[]{false}, new Callable<Object[]>() { // from class: anywheresoftware.b4a.objects.streams.File.3
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                File.Copy(str, str2, str3, str4);
                return new Object[]{true};
            }
        });
        return obj;
    }

    public static byte[] ReadBytes(String str, String str2) throws IOException {
        return Bit.InputStreamToBytes(OpenInput(str, str2).getObject());
    }

    public static void WriteBytes(String str, String str2, byte[] bArr) throws IOException {
        OutputStreamWrapper outputStreamWrapperOpenOutput = OpenOutput(str, str2, false);
        try {
            outputStreamWrapperOpenOutput.WriteBytes(bArr, 0, bArr.length);
        } finally {
            outputStreamWrapperOpenOutput.Close();
        }
    }

    public static OutputStreamWrapper OpenOutput(String str, String str2, boolean z) throws FileNotFoundException {
        if (str == assetsDir) {
            throw new RuntimeException("The Assets folder is a read-only folder");
        }
        OutputStreamWrapper outputStreamWrapper = new OutputStreamWrapper();
        outputStreamWrapper.setObject(new BufferedOutputStream(new FileOutputStream(new java.io.File(str, str2), z)));
        return outputStreamWrapper;
    }

    @BA.ShortName("InputStream")
    public static class InputStreamWrapper extends AbsObjectWrapper<InputStream> {
        public void InitializeFromBytesArray(byte[] bArr, int i, int i2) {
            setObject(new ByteArrayInputStream(bArr, i, i2));
        }

        public void Close() throws IOException {
            getObject().close();
        }

        public int ReadBytes(byte[] bArr, int i, int i2) throws IOException {
            return getObject().read(bArr, i, i2);
        }

        public int BytesAvailable() throws IOException {
            return getObject().available();
        }
    }

    @BA.ShortName("OutputStream")
    public static class OutputStreamWrapper extends AbsObjectWrapper<OutputStream> {
        public void InitializeToBytesArray(int i) {
            setObject(new ByteArrayOutputStream(i));
        }

        public byte[] ToBytesArray() {
            if (!(getObject() instanceof ByteArrayOutputStream)) {
                throw new RuntimeException("ToBytes can only be called after InitializeToBytesArray.");
            }
            return ((ByteArrayOutputStream) getObject()).toByteArray();
        }

        public void Close() throws IOException {
            getObject().close();
        }

        public void Flush() throws IOException {
            getObject().flush();
        }

        public void WriteBytes(byte[] bArr, int i, int i2) throws IOException {
            getObject().write(bArr, i, i2);
        }
    }

    @BA.ShortName("TextWriter")
    public static class TextWriterWrapper extends AbsObjectWrapper<BufferedWriter> {
        public void Initialize(OutputStream outputStream) {
            setObject(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF8")), 4096));
        }

        public void Initialize2(OutputStream outputStream, String str) {
            setObject(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName(str)), 4096));
        }

        public void Write(String str) throws IOException {
            getObject().write(str);
        }

        public void WriteLine(String str) throws IOException {
            getObject().write(String.valueOf(str) + Common.CRLF);
        }

        public void WriteList(List list) throws IOException {
            Iterator<Object> it = list.getObject().iterator();
            while (it.hasNext()) {
                WriteLine(String.valueOf(it.next()));
            }
        }

        public void Close() throws IOException {
            getObject().close();
        }

        public void Flush() throws IOException {
            getObject().flush();
        }
    }

    @BA.ShortName("TextReader")
    public static class TextReaderWrapper extends AbsObjectWrapper<BufferedReader> {
        public void Initialize(InputStream inputStream) {
            setObject(new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF8")), 4096));
        }

        public void Initialize2(InputStream inputStream, String str) {
            setObject(new BufferedReader(new InputStreamReader(inputStream, Charset.forName(str)), 4096));
        }

        public String ReadLine() throws IOException {
            return getObject().readLine();
        }

        public int Read(char[] cArr, int i, int i2) throws IOException {
            return getObject().read(cArr, i, i2);
        }

        public boolean Ready() throws IOException {
            return getObject().ready();
        }

        public String ReadAll() throws IOException {
            char[] cArr = new char[1024];
            StringBuilder sb = new StringBuilder(1024);
            while (true) {
                int iRead = Read(cArr, 0, 1024);
                if (iRead == -1) {
                    Close();
                    return sb.toString();
                }
                if (iRead < 1024) {
                    sb.append(new String(cArr, 0, iRead));
                } else {
                    sb.append(cArr);
                }
            }
        }

        public List ReadList() throws IOException {
            List list = new List();
            list.Initialize();
            while (true) {
                String strReadLine = ReadLine();
                if (strReadLine != null) {
                    list.Add(strReadLine);
                } else {
                    Close();
                    return list;
                }
            }
        }

        public int Skip(int i) throws IOException {
            return (int) getObject().skip(i);
        }

        public void Close() throws IOException {
            getObject().close();
        }
    }
}
