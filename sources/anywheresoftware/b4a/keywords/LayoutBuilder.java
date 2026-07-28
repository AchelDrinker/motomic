package anywheresoftware.b4a.keywords;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.ConnectorUtils;
import anywheresoftware.b4a.DynamicBuilder;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import anywheresoftware.b4a.objects.CustomViewWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LayoutBuilder {
    private static double autoscale;
    private static LayoutValues chosen;
    private static HashMap<String, Field> classFields;
    private static String currentClass;
    private static List<CustomViewWrapper> customViewWrappers;
    private static BA tempBA;
    private static HashMap<String, Object> viewsToSendInShellMode;
    private static HashMap<String, WeakReference<MapAndCachedStrings>> cachedLayouts = new HashMap<>();
    private static double screenSize = 0.0d;

    public interface DesignerTextSizeMethod {
        float getTextSize();

        void setTextSize(float f);
    }

    public static class LayoutValuesAndMap {
        public final LayoutValues layoutValues;
        public final LinkedHashMap<String, ViewWrapperAndAnchor> map;

        public LayoutValuesAndMap(LayoutValues layoutValues, LinkedHashMap<String, ViewWrapperAndAnchor> linkedHashMap) {
            this.layoutValues = layoutValues;
            this.map = linkedHashMap;
        }
    }

    private static class MapAndCachedStrings {
        public final String[] cachedStrings;
        public final HashMap<String, Object> map;

        public MapAndCachedStrings(HashMap<String, Object> map, String[] strArr) {
            this.map = map;
            this.cachedStrings = strArr;
        }
    }

    public static LayoutValuesAndMap loadLayout(String str, BA ba, boolean z, ViewGroup viewGroup, LinkedHashMap<String, ViewWrapperAndAnchor> linkedHashMap) throws IOException {
        String[] strArr;
        int width;
        int height;
        int i;
        int i2;
        LinkedHashMap<String, ViewWrapperAndAnchor> linkedHashMap2;
        HashMap<String, Object> map;
        HashMap<String, Object> map2;
        HashMap<String, Object> map3;
        try {
            try {
                try {
                    tempBA = ba;
                    String lowerCase = str.toLowerCase(BA.cul);
                    if (!lowerCase.endsWith(".bal")) {
                        lowerCase = String.valueOf(lowerCase) + ".bal";
                    }
                    WeakReference<MapAndCachedStrings> weakReference = cachedLayouts.get(lowerCase);
                    MapAndCachedStrings mapAndCachedStrings = weakReference != null ? weakReference.get() : null;
                    DataInputStream dataInputStream = new DataInputStream(File.OpenInput(File.getDirAssets(), lowerCase).getObject());
                    int i3 = ConnectorUtils.readInt(dataInputStream);
                    int iSkip = ConnectorUtils.readInt(dataInputStream);
                    while (iSkip > 0) {
                        String str2 = lowerCase;
                        long j = iSkip;
                        iSkip = (int) (j - dataInputStream.skip(j));
                        lowerCase = str2;
                    }
                    int iIntValue = 0;
                    if (i3 < 3) {
                        strArr = null;
                    } else if (mapAndCachedStrings != null) {
                        strArr = mapAndCachedStrings.cachedStrings;
                        ConnectorUtils.readInt(dataInputStream);
                        for (int i4 = 0; i4 < strArr.length; i4++) {
                            dataInputStream.skipBytes(ConnectorUtils.readInt(dataInputStream));
                        }
                    } else {
                        int i5 = ConnectorUtils.readInt(dataInputStream);
                        String[] strArr2 = new String[i5];
                        for (int i6 = 0; i6 < i5; i6++) {
                            strArr2[i6] = ConnectorUtils.readString(dataInputStream);
                        }
                        strArr = strArr2;
                    }
                    int i7 = ConnectorUtils.readInt(dataInputStream);
                    chosen = null;
                    LayoutValues layoutValuesGetDeviceLayoutValues = Common.GetDeviceLayoutValues(ba);
                    float fCalcDistance = Float.MAX_VALUE;
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 < i7) {
                        String str3 = lowerCase;
                        LayoutValues fromStream = LayoutValues.readFromStream(dataInputStream);
                        if (chosen == null) {
                            chosen = fromStream;
                            fCalcDistance = fromStream.calcDistance(layoutValuesGetDeviceLayoutValues);
                            i9 = i8;
                        } else {
                            float fCalcDistance2 = fromStream.calcDistance(layoutValuesGetDeviceLayoutValues);
                            if (fCalcDistance2 < fCalcDistance) {
                                chosen = fromStream;
                                i9 = i8;
                                fCalcDistance = fCalcDistance2;
                            }
                        }
                        i8++;
                        lowerCase = str3;
                    }
                    BALayout.setUserScale(chosen.Scale);
                    if (z || viewGroup.getLayoutParams() == null) {
                        width = ba.vg.getWidth();
                        height = ba.vg.getHeight();
                    } else {
                        width = viewGroup.getLayoutParams().width;
                        height = viewGroup.getLayoutParams().height;
                    }
                    int i10 = width;
                    int i11 = height;
                    if (linkedHashMap == null) {
                        LayoutHashMap layoutHashMap = new LayoutHashMap();
                        if (mapAndCachedStrings != null) {
                            map2 = mapAndCachedStrings.map;
                        } else {
                            map2 = ConnectorUtils.readMap(dataInputStream, strArr);
                            cachedLayouts.put(lowerCase, new WeakReference<>(new MapAndCachedStrings(map2, strArr)));
                        }
                        i2 = i11;
                        i = i10;
                        loadLayoutHelper(map2, ba, ba.eventsTarget == null ? ba.activity : ba.eventsTarget, viewGroup, z, "variant" + i9, true, layoutHashMap, i, i2);
                        HashMap<String, Object> map4 = map2;
                        if (BA.isShellModeRuntimeCheck(ba) && (map3 = viewsToSendInShellMode) != null) {
                            ba.raiseEvent2(null, true, "SEND_VIEWS_AFTER_LAYOUT", true, map3);
                            viewsToSendInShellMode = null;
                        }
                        iIntValue = ((Integer) BA.gm(map4, "animationDuration", 0)).intValue();
                        map = map4;
                        linkedHashMap2 = layoutHashMap;
                    } else {
                        i = i10;
                        i2 = i11;
                        linkedHashMap2 = linkedHashMap;
                        map = null;
                    }
                    dataInputStream.close();
                    int i12 = i;
                    int i13 = i2;
                    runScripts(lowerCase, ba, chosen, viewGroup, linkedHashMap2, i12, i13, Common.Density, map);
                    BALayout.setUserScale(1.0f);
                    List<CustomViewWrapper> list = customViewWrappers;
                    if (list != null) {
                        Iterator<CustomViewWrapper> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().AfterDesignerScript();
                        }
                    }
                    animateLayout(linkedHashMap2, viewGroup, i12, i13, iIntValue);
                    LayoutValuesAndMap layoutValuesAndMap = new LayoutValuesAndMap(chosen, linkedHashMap2);
                    tempBA = null;
                    customViewWrappers = null;
                    return layoutValuesAndMap;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e2) {
                throw e2;
            }
        } catch (Throwable th) {
            tempBA = null;
            customViewWrappers = null;
            throw th;
        }
    }

    private static void animateLayout(LinkedHashMap<String, ViewWrapperAndAnchor> linkedHashMap, View view, int i, int i2, int i3) {
        int i4;
        int i5;
        if (i3 <= 0) {
            return;
        }
        for (ViewWrapperAndAnchor viewWrapperAndAnchor : linkedHashMap.values()) {
            if (viewWrapperAndAnchor.parent == view) {
                if (viewWrapperAndAnchor.hanchor == ViewWrapperAndAnchor.RIGHT) {
                    i4 = i;
                } else {
                    i4 = viewWrapperAndAnchor.hanchor == ViewWrapperAndAnchor.BOTH ? i / 2 : 0;
                }
                if (viewWrapperAndAnchor.vanchor == ViewWrapperAndAnchor.BOTTOM) {
                    i5 = i2;
                } else {
                    i5 = viewWrapperAndAnchor.vanchor == ViewWrapperAndAnchor.BOTH ? i2 / 2 : 0;
                }
                ViewWrapper.AnimateFrom((View) viewWrapperAndAnchor.vw.getObject(), i3, i4, i5, 0, 0);
            }
        }
    }

    private static void runScripts(String str, BA ba, LayoutValues layoutValues, View view, LinkedHashMap<String, ViewWrapperAndAnchor> linkedHashMap, int i, int i2, float f, Map<String, Object> map) throws IllegalAccessException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("LS_");
        for (int i3 = 0; i3 < str.length() - 4; i3++) {
            char cCharAt = str.charAt(i3);
            if (Character.isLetterOrDigit(cCharAt)) {
                sb.append(cCharAt);
            } else {
                sb.append("_");
            }
        }
        try {
            try {
                Class<?> cls = Class.forName(String.valueOf(BA.packageName) + ".designerscripts." + sb.toString());
                try {
                    runScriptMethod(cls, variantToMethod(null), layoutValues, ba, view, linkedHashMap, map, i, i2, f);
                } catch (NoSuchMethodException unused) {
                }
                runScriptMethod(cls, variantToMethod(layoutValues), layoutValues, ba, view, linkedHashMap, map, i, i2, f);
            } catch (ClassNotFoundException | NoSuchMethodException unused2) {
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            throw new RuntimeException(e2.getCause());
        }
    }

    private static void runScriptMethod(Class<?> cls, String str, LayoutValues layoutValues, BA ba, View view, Map<String, ViewWrapperAndAnchor> map, Map<String, Object> map2, int i, int i2, float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls2 = Integer.TYPE;
        cls.getMethod(str, BA.class, View.class, LayoutValues.class, Map.class, Map.class, cls2, cls2, Float.TYPE).invoke(null, ba, view, layoutValues, map2, map, Integer.valueOf(i), Integer.valueOf(i2), Float.valueOf(f));
    }

    public static void setScaleRate(double d) {
        double width = (tempBA.vg.getWidth() + tempBA.vg.getHeight()) / Common.Density;
        double d2 = ((chosen.Width + chosen.Height) - 25) / chosen.Scale;
        Double.isNaN(width);
        Double.isNaN(d2);
        double d3 = width / d2;
        if (System.getProperty("autoscaleall_old_behaviour", "false").equals("true")) {
            double width2 = ((tempBA.vg.getWidth() + tempBA.vg.getHeight()) / (Common.Density * 750.0f)) - 1.0f;
            Double.isNaN(width2);
            autoscale = (d * width2) + 1.0d;
        } else if (d3 > 0.95d && d3 < 1.05d) {
            autoscale = 1.0d;
        } else {
            Double.isNaN(width);
            Double.isNaN(d2);
            autoscale = ((d * ((width / 750.0d) - 1.0d)) + 1.0d) / ((((d2 / 750.0d) - 1.0d) * d) + 1.0d);
        }
        screenSize = 0.0d;
    }

    public static double getScreenSize() {
        if (screenSize == 0.0d) {
            double dSqrt = Math.sqrt(Math.pow(tempBA.vg.getWidth(), 2.0d) + Math.pow(tempBA.vg.getHeight(), 2.0d)) / 160.0d;
            double d = Common.Density;
            Double.isNaN(d);
            screenSize = dSqrt / d;
        }
        return screenSize;
    }

    public static boolean isPortrait() {
        return tempBA.vg.getHeight() >= tempBA.vg.getWidth();
    }

    public static void scaleAll(Map<String, ViewWrapperAndAnchor> map) {
        for (ViewWrapperAndAnchor viewWrapperAndAnchor : map.values()) {
            if (viewWrapperAndAnchor.vw.IsInitialized() && !(viewWrapperAndAnchor.vw instanceof ActivityWrapper)) {
                scaleView(viewWrapperAndAnchor);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static void scaleView(ViewWrapperAndAnchor viewWrapperAndAnchor) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ViewWrapper<?> viewWrapper = viewWrapperAndAnchor.vw;
        int left = viewWrapper.getLeft();
        int width = viewWrapper.getWidth();
        int height = viewWrapper.getHeight();
        int top = viewWrapper.getTop();
        int i6 = (viewWrapperAndAnchor.parent == null || viewWrapperAndAnchor.parent.getLayoutParams() == null) ? viewWrapperAndAnchor.pw : viewWrapperAndAnchor.parent.getLayoutParams().width;
        int i7 = (viewWrapperAndAnchor.parent == null || viewWrapperAndAnchor.parent.getLayoutParams() == null) ? viewWrapperAndAnchor.ph : viewWrapperAndAnchor.parent.getLayoutParams().height;
        int i8 = viewWrapperAndAnchor.right;
        int i9 = viewWrapperAndAnchor.bottom;
        if (viewWrapperAndAnchor.hanchor == ViewWrapperAndAnchor.LEFT) {
            double d = left;
            double d2 = autoscale;
            Double.isNaN(d);
            i2 = (int) ((d * d2) + 0.5d);
            double d3 = left + width;
            Double.isNaN(d3);
            i3 = ((int) ((d3 * d2) + 0.5d)) - i2;
        } else {
            if (viewWrapperAndAnchor.hanchor == ViewWrapperAndAnchor.RIGHT) {
                double d4 = i8;
                double d5 = autoscale;
                Double.isNaN(d4);
                int i10 = (int) ((d4 * d5) + 0.5d);
                double d6 = i8 + width;
                Double.isNaN(d6);
                i = ((int) ((d6 * d5) + 0.5d)) - i10;
                i2 = (i6 - i10) - i;
            } else {
                double d7 = left;
                double d8 = autoscale;
                Double.isNaN(d7);
                int i11 = (int) ((d7 * d8) + 0.5d);
                double d9 = i8;
                Double.isNaN(d9);
                i = (i6 - ((int) ((d9 * d8) + 0.5d))) - i11;
                i2 = i11;
            }
            i3 = i;
        }
        viewWrapper.setLeft(i2);
        viewWrapper.setWidth(i3);
        if (viewWrapperAndAnchor.vanchor == ViewWrapperAndAnchor.TOP) {
            double d10 = top;
            double d11 = autoscale;
            Double.isNaN(d10);
            i4 = (int) ((d10 * d11) + 0.5d);
            double d12 = top + height;
            Double.isNaN(d12);
            i5 = ((int) ((d12 * d11) + 0.5d)) - i4;
        } else if (viewWrapperAndAnchor.vanchor == ViewWrapperAndAnchor.BOTTOM) {
            double d13 = i9;
            double d14 = autoscale;
            Double.isNaN(d13);
            int i12 = (int) ((d13 * d14) + 0.5d);
            double d15 = i9 + height;
            Double.isNaN(d15);
            i5 = ((int) ((d15 * d14) + 0.5d)) - i12;
            i4 = (i7 - i12) - i5;
        } else {
            double d16 = top;
            double d17 = autoscale;
            Double.isNaN(d16);
            i4 = (int) ((d16 * d17) + 0.5d);
            double d18 = i9;
            Double.isNaN(d18);
            i5 = (i7 - i4) - ((int) ((d18 * d17) + 0.5d));
        }
        viewWrapper.setTop(i4);
        viewWrapper.setHeight(i5);
        if (viewWrapper instanceof DesignerTextSizeMethod) {
            DesignerTextSizeMethod designerTextSizeMethod = (DesignerTextSizeMethod) viewWrapper;
            double textSize = designerTextSizeMethod.getTextSize();
            double d19 = autoscale;
            Double.isNaN(textSize);
            designerTextSizeMethod.setTextSize((float) (textSize * d19));
        }
    }

    private static String variantToMethod(LayoutValues layoutValues) {
        String str;
        if (layoutValues == null) {
            str = "general";
        } else {
            str = String.valueOf(String.valueOf(layoutValues.Width)) + "x" + String.valueOf(layoutValues.Height) + "_" + BA.NumberToString(layoutValues.Scale).replace(".", "_");
        }
        return "LS_" + str;
    }

    private static void loadLayoutHelper(HashMap<String, Object> map, BA ba, Object obj, ViewGroup viewGroup, boolean z, String str, boolean z2, HashMap<String, ViewWrapperAndAnchor> map2, int i, int i2) throws Exception {
        Object obj2;
        HashMap<String, ViewWrapperAndAnchor> map3;
        View view;
        char c;
        Object obj3;
        Class<?> cls;
        BA ba2 = ba;
        ViewGroup viewGroup2 = viewGroup;
        HashMap map4 = (HashMap) map.get(str);
        int i3 = 0;
        if (z || !z2) {
            ViewGroup viewGroup3 = z ? viewGroup2 : null;
            map.put("left", map4.get("left"));
            map.put("top", map4.get("top"));
            map.put("width", map4.get("width"));
            map.put("height", map4.get("height"));
            View view2 = (View) DynamicBuilder.build(viewGroup3, map, false, viewGroup2.getContext());
            if (z) {
                obj2 = obj;
                map3 = map2;
            } else {
                String lowerCase = ((String) map.get("name")).toLowerCase(BA.cul);
                String str2 = (String) map.get("type");
                if (str2.startsWith(".")) {
                    str2 = "anywheresoftware.b4a.objects" + str2;
                }
                ViewWrapper viewWrapper = (ViewWrapper) Class.forName(str2).newInstance();
                ViewWrapperAndAnchor viewWrapperAndAnchor = new ViewWrapperAndAnchor(viewWrapper, z ? null : viewGroup2);
                if (map4.containsKey("hanchor")) {
                    viewWrapperAndAnchor.hanchor = ((Integer) map4.get("hanchor")).intValue();
                    viewWrapperAndAnchor.vanchor = ((Integer) map4.get("vanchor")).intValue();
                }
                viewWrapperAndAnchor.pw = i;
                viewWrapperAndAnchor.ph = i2;
                map3 = map2;
                map3.put(lowerCase, viewWrapperAndAnchor);
                if (classFields == null || currentClass != ba2.className) {
                    classFields = new HashMap<>();
                    currentClass = ba2.className;
                    Field[] declaredFields = Class.forName(ba2.className).getDeclaredFields();
                    int length = declaredFields.length;
                    c = 0;
                    while (i3 < length) {
                        Field[] fieldArr = declaredFields;
                        Field field = fieldArr[i3];
                        if (field.getName().startsWith("_")) {
                            classFields.put(field.getName(), field);
                        }
                        i3++;
                        ba2 = ba;
                        viewGroup2 = viewGroup;
                        declaredFields = fieldArr;
                    }
                } else {
                    c = 0;
                }
                Field field2 = classFields.get("_" + lowerCase);
                if (viewWrapper instanceof CustomViewWrapper) {
                    if (customViewWrappers == null) {
                        customViewWrappers = new ArrayList();
                    }
                    CustomViewWrapper customViewWrapper = (CustomViewWrapper) viewWrapper;
                    customViewWrappers.add(customViewWrapper);
                    String str3 = (String) map.get("customType");
                    if (str3 == null || str3.length() == 0) {
                        throw new RuntimeException("CustomView CustomType property was not set.");
                    }
                    try {
                        cls = Class.forName(str3);
                    } catch (ClassNotFoundException e) {
                        int iLastIndexOf = str3.lastIndexOf(".");
                        if (iLastIndexOf > -1) {
                            cls = Class.forName(String.valueOf(BA.packageName) + str3.substring(iLastIndexOf));
                        } else {
                            throw e;
                        }
                    }
                    Object objNewInstance = cls.newInstance();
                    customViewWrapper.customObject = objNewInstance;
                    customViewWrapper.props = new HashMap<>(map);
                    obj3 = objNewInstance;
                } else if (field2 == null || field2.getType() == viewWrapper.getClass()) {
                    obj3 = viewWrapper;
                } else {
                    if (BA.debugMode) {
                        Type genericSuperclass = viewWrapper.getClass().getGenericSuperclass();
                        if (genericSuperclass instanceof ParameterizedType) {
                            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                            if (parameterizedType.getActualTypeArguments().length > 0 && !((Class) ((ParameterizedType) field2.getType().getGenericSuperclass()).getActualTypeArguments()[c]).isAssignableFrom((Class) parameterizedType.getActualTypeArguments()[c])) {
                                throw new RuntimeException("Cannot convert: " + viewWrapper.getClass() + ", to: " + field2.getType());
                            }
                        }
                    }
                    ObjectWrapper objectWrapper = (ObjectWrapper) field2.getType().newInstance();
                    objectWrapper.setObject(view2);
                    obj3 = objectWrapper;
                }
                if (BA.isShellModeRuntimeCheck(ba2)) {
                    if (viewsToSendInShellMode == null) {
                        viewsToSendInShellMode = new HashMap<>();
                    }
                    viewsToSendInShellMode.put(lowerCase, obj3);
                }
                if (field2 != null) {
                    obj2 = obj;
                    try {
                        field2.set(obj2, obj3);
                    } catch (IllegalArgumentException unused) {
                        throw new RuntimeException("Field " + lowerCase + " was declared with the wrong type.");
                    }
                } else {
                    obj2 = obj;
                }
                viewWrapper.setObject(view2);
                viewWrapper.innerInitialize(ba2, ((String) map.get("eventName")).toLowerCase(BA.cul), true);
                viewGroup2.addView(view2, view2.getLayoutParams());
                if (viewWrapperAndAnchor.hanchor != 0 || viewWrapperAndAnchor.vanchor != 0) {
                    ViewWrapper.fixAnchor(i, i2, viewWrapperAndAnchor);
                }
            }
            view = view2;
        } else {
            viewGroup2.setBackgroundDrawable((Drawable) DynamicBuilder.build(viewGroup2, (HashMap) map.get("drawable"), false, null));
            obj2 = obj;
            map3 = map2;
            view = viewGroup2;
        }
        HashMap map5 = (HashMap) map.get(":kids");
        if (map5 != null) {
            int i4 = view.getLayoutParams() == null ? 0 : view.getLayoutParams().width;
            int i5 = view.getLayoutParams() == null ? 0 : view.getLayoutParams().height;
            int i6 = 0;
            while (i6 < map5.size()) {
                loadLayoutHelper((HashMap) map5.get(String.valueOf(i6)), ba, obj2, (ViewGroup) view, false, str, false, map3, i4, i5);
                i6++;
                obj2 = obj;
                map3 = map2;
            }
        }
    }

    public static class LayoutHashMap<K, V> extends LinkedHashMap<K, V> {
        @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
        public V get(Object obj) {
            V v = (V) super.get(obj);
            if (v != null) {
                return v;
            }
            throw new RuntimeException("Cannot find view: " + obj.toString() + "\nAll views in script should be declared.");
        }
    }

    public static class ViewWrapperAndAnchor {
        public static int BOTH = 2;
        public static int BOTTOM = 1;
        public static int LEFT = 0;
        public static int RIGHT = 1;
        public static int TOP;
        public int bottom;
        public int hanchor;
        public final View parent;
        public int ph;
        public int pw;
        public int right;
        public int vanchor;
        public final ViewWrapper<?> vw;

        public ViewWrapperAndAnchor(ViewWrapper<?> viewWrapper, View view) {
            this.vw = viewWrapper;
            this.parent = view;
        }
    }
}
