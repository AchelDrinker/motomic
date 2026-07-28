package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi29Impl";

    private static int getMatchScore(FontStyle fontStyle, FontStyle fontStyle2) {
        return (Math.abs(fontStyle.getWeight() - fontStyle2.getWeight()) / 100) + (fontStyle.getSlant() == fontStyle2.getSlant() ? 0 : 2);
    }

    private Font findBaseFont(FontFamily fontFamily, int i) {
        FontStyle fontStyle = new FontStyle((i & 1) != 0 ? 700 : 400, (i & 2) != 0 ? 1 : 0);
        Font font = fontFamily.getFont(0);
        int matchScore = getMatchScore(fontStyle, font.getStyle());
        for (int i2 = 1; i2 < fontFamily.getSize(); i2++) {
            Font font2 = fontFamily.getFont(i2);
            int matchScore2 = getMatchScore(fontStyle, font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
        }
        return font;
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected Typeface createFromInputStream(Context context, InputStream inputStream) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        try {
            FontFamily fontFamily = getFontFamily(cancellationSignal, fontInfoArr, context.getContentResolver());
            if (fontFamily == null) {
                return null;
            }
            return new Typeface.CustomFallbackBuilder(fontFamily).setStyle(findBaseFont(fontFamily, i).getStyle()).build();
        } catch (Exception e) {
            Log.w(TAG, "Font load failed", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0016 A[Catch: IOException -> 0x0056, PHI: r3
      0x0016: PHI (r3v5 android.graphics.fonts.FontFamily$Builder) = (r3v3 android.graphics.fonts.FontFamily$Builder), (r3v1 android.graphics.fonts.FontFamily$Builder) binds: [B:14:0x0047, B:7:0x0014] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #1 {IOException -> 0x0056, blocks: (B:5:0x0008, B:8:0x0016, B:22:0x0055, B:21:0x0052, B:18:0x004d, B:10:0x001a, B:12:0x003d, B:13:0x0044), top: B:32:0x0008, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.fonts.FontFamily getFontFamily(android.os.CancellationSignal r8, androidx.core.provider.FontsContractCompat.FontInfo[] r9, android.content.ContentResolver r10) throws java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException {
        /*
            int r0 = r9.length
            r1 = 0
            r2 = 0
            r3 = r1
        L4:
            if (r2 >= r0) goto L61
            r4 = r9[r2]
            android.net.Uri r5 = r4.getUri()     // Catch: java.io.IOException -> L56
            java.lang.String r6 = "r"
            android.os.ParcelFileDescriptor r5 = r10.openFileDescriptor(r5, r6, r8)     // Catch: java.io.IOException -> L56
            if (r5 != 0) goto L1a
            if (r5 == 0) goto L5e
        L16:
            r5.close()     // Catch: java.io.IOException -> L56
            goto L5e
        L1a:
            android.graphics.fonts.Font$Builder r6 = new android.graphics.fonts.Font$Builder     // Catch: java.lang.Throwable -> L4a
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L4a
            int r7 = r4.getWeight()     // Catch: java.lang.Throwable -> L4a
            android.graphics.fonts.Font$Builder r6 = r6.setWeight(r7)     // Catch: java.lang.Throwable -> L4a
            boolean r7 = r4.isItalic()     // Catch: java.lang.Throwable -> L4a
            android.graphics.fonts.Font$Builder r6 = r6.setSlant(r7)     // Catch: java.lang.Throwable -> L4a
            int r4 = r4.getTtcIndex()     // Catch: java.lang.Throwable -> L4a
            android.graphics.fonts.Font$Builder r4 = r6.setTtcIndex(r4)     // Catch: java.lang.Throwable -> L4a
            android.graphics.fonts.Font r4 = r4.build()     // Catch: java.lang.Throwable -> L4a
            if (r3 != 0) goto L44
            android.graphics.fonts.FontFamily$Builder r6 = new android.graphics.fonts.FontFamily$Builder     // Catch: java.lang.Throwable -> L4a
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L4a
            r3 = r6
            goto L47
        L44:
            r3.addFont(r4)     // Catch: java.lang.Throwable -> L4a
        L47:
            if (r5 == 0) goto L5e
            goto L16
        L4a:
            r4 = move-exception
            if (r5 == 0) goto L55
            r5.close()     // Catch: java.lang.Throwable -> L51
            goto L55
        L51:
            r5 = move-exception
            androidx.core.app.Person$$ExternalSyntheticBackport0.m(r4, r5)     // Catch: java.io.IOException -> L56
        L55:
            throw r4     // Catch: java.io.IOException -> L56
        L56:
            r4 = move-exception
            java.lang.String r5 = "TypefaceCompatApi29Impl"
            java.lang.String r6 = "Font load failed"
            android.util.Log.w(r5, r6, r4)
        L5e:
            int r2 = r2 + 1
            goto L4
        L61:
            if (r3 != 0) goto L64
            return r1
        L64:
            android.graphics.fonts.FontFamily r8 = r3.build()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi29Impl.getFontFamily(android.os.CancellationSignal, androidx.core.provider.FontsContractCompat$FontInfo[], android.content.ContentResolver):android.graphics.fonts.FontFamily");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfoWithFallback(Context context, CancellationSignal cancellationSignal, List<FontsContractCompat.FontInfo[]> list, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            FontFamily fontFamily = getFontFamily(cancellationSignal, list.get(0), contentResolver);
            if (fontFamily == null) {
                return null;
            }
            Typeface.CustomFallbackBuilder customFallbackBuilder = new Typeface.CustomFallbackBuilder(fontFamily);
            for (int i2 = 1; i2 < list.size(); i2++) {
                FontFamily fontFamily2 = getFontFamily(cancellationSignal, list.get(i2), contentResolver);
                if (fontFamily2 != null) {
                    customFallbackBuilder.addCustomFallback(fontFamily2);
                }
            }
            return customFallbackBuilder.setStyle(findBaseFont(fontFamily, i).getStyle()).build();
        } catch (Exception e) {
            Log.w(TAG, "Font load failed", e);
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) {
        try {
            FontFamily.Builder builder = null;
            for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
                try {
                    Font fontBuild = new Font.Builder(resources, fontFileResourceEntry.getResourceId()).setWeight(fontFileResourceEntry.getWeight()).setSlant(fontFileResourceEntry.isItalic() ? 1 : 0).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings()).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(fontBuild);
                    } else {
                        builder.addFont(fontBuild);
                    }
                } catch (IOException unused) {
                }
            }
            if (builder == null) {
                return null;
            }
            FontFamily fontFamilyBuild = builder.build();
            return new Typeface.CustomFallbackBuilder(fontFamilyBuild).setStyle(findBaseFont(fontFamilyBuild, i).getStyle()).build();
        } catch (Exception e) {
            Log.w(TAG, "Font load failed", e);
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        try {
            Font fontBuild = new Font.Builder(resources, i).build();
            return new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).build()).setStyle(fontBuild.getStyle()).build();
        } catch (Exception e) {
            Log.w(TAG, "Font load failed", e);
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    Typeface createWeightStyle(Context context, Typeface typeface, int i, boolean z) {
        return Typeface.create(typeface, i, z);
    }
}
