package anywheresoftware.b4j.object;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.Version(2.06f)
@BA.ShortName("JavaObject")
public class JavaObject extends AbsObjectWrapper<Object> {
    private static Field context;
    private static final FieldCache fieldCache = new FieldCache();
    private static final MethodCache methodCache = new MethodCache();
    private static final HashMap<Class<?>, Class<?>> primitiveToBoxed;
    private static final HashMap<String, Class<?>> primitives;

    static {
        HashMap<String, Class<?>> map = new HashMap<>();
        primitives = map;
        HashMap<Class<?>, Class<?>> map2 = new HashMap<>();
        primitiveToBoxed = map2;
        map2.put(Byte.TYPE, Byte.class);
        map2.put(Character.TYPE, Character.class);
        map2.put(Short.TYPE, Short.class);
        map2.put(Integer.TYPE, Integer.class);
        map2.put(Long.TYPE, Long.class);
        map2.put(Float.TYPE, Float.class);
        map2.put(Double.TYPE, Double.class);
        map2.put(Boolean.TYPE, Boolean.class);
        map.put("byte", Byte.TYPE);
        map.put("char", Character.TYPE);
        map.put("short", Short.TYPE);
        map.put("int", Integer.TYPE);
        map.put("long", Long.TYPE);
        map.put("float", Float.TYPE);
        map.put("double", Double.TYPE);
        map.put("boolean", Boolean.TYPE);
    }

    public JavaObject InitializeContext(BA ba) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        BA ba2;
        Object obj = BA.class.getDeclaredField("sharedProcessBA").get(ba);
        WeakReference weakReference = (WeakReference) obj.getClass().getDeclaredField("activityBA").get(obj);
        if (weakReference != null && (ba2 = (BA) weakReference.get()) != null) {
            ba = ba2;
        }
        if (context == null) {
            context = BA.class.getDeclaredField("context");
        }
        setObject(context.get(ba));
        return this;
    }

    public JavaObject InitializeStatic(String str) throws ClassNotFoundException {
        setObject(getCorrectClassName(str));
        return this;
    }

    public JavaObject InitializeNewInstance(String str, Object[] objArr) throws Exception {
        Class<?> correctClassName = getCorrectClassName(str);
        if (objArr == null || objArr.length == 0) {
            setObject(correctClassName.newInstance());
            return this;
        }
        for (Constructor<?> constructor : correctClassName.getConstructors()) {
            if (arrangeAndCheckMatch(constructor.getParameterTypes(), objArr)) {
                setObject(constructor.newInstance(objArr));
                return this;
            }
        }
        throw new RuntimeException("Constructor not found.");
    }

    public JavaObject InitializeArray(String str, Object[] objArr) throws ClassNotFoundException {
        Class<?> correctClassName = primitives.get(str);
        if (correctClassName == null) {
            correctClassName = getCorrectClassName(str);
        }
        Object objNewInstance = Array.newInstance(correctClassName, objArr.length);
        for (int i = 0; i < objArr.length; i++) {
            Array.set(objNewInstance, i, objArr[i]);
        }
        setObject(objNewInstance);
        return this;
    }

    public Object RunMethod(String str, Object[] objArr) throws Exception {
        Method method;
        Iterator<Method> it = methodCache.getMethod(getCurrentClass().getName(), str, objArr).iterator();
        while (true) {
            if (!it.hasNext()) {
                method = null;
                break;
            }
            Method next = it.next();
            if (arrangeAndCheckMatch(next.getParameterTypes(), objArr)) {
                method = next;
                break;
            }
        }
        if (method == null) {
            throw new RuntimeException("Method: " + str + " not matched.");
        }
        return method.invoke(getObject(), objArr);
    }

    public JavaObject RunMethodJO(String str, Object[] objArr) throws Exception {
        return (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), RunMethod(str, objArr));
    }

    private boolean arrangeAndCheckMatch(Class<?>[] clsArr, Object[] objArr) {
        if (objArr == null) {
            return clsArr.length == 0;
        }
        if (objArr.length != clsArr.length) {
            return false;
        }
        int i = 0;
        while (i < objArr.length) {
            Object obj = objArr[i];
            if (obj != null) {
                Class<?> cls = obj.getClass();
                if (clsArr[i].isPrimitive()) {
                    clsArr[i] = primitiveToBoxed.get(clsArr[i]);
                }
                if (clsArr[i].isEnum() && cls == String.class) {
                    objArr[i] = Enum.valueOf(clsArr[i], (String) objArr[i]);
                } else if (!clsArr[i].isAssignableFrom(cls)) {
                    break;
                }
            }
            i++;
        }
        return i == objArr.length;
    }

    public void SetField(String str, Object obj) throws Exception {
        fieldCache.getField(getCurrentClass().getName(), str).set(getObject(), obj);
    }

    public Object GetField(String str) throws Exception {
        return fieldCache.getField(getCurrentClass().getName(), str).get(getObject());
    }

    public JavaObject GetFieldJO(String str) throws Exception {
        return (JavaObject) AbsObjectWrapper.ConvertToWrapper(new JavaObject(), GetField(str));
    }

    public Object CreateEvent(BA ba, String str, String str2, Object obj) throws Exception {
        return createEvent(ba, str, str2, false, obj);
    }

    public Object CreateEventFromUI(BA ba, String str, String str2, Object obj) throws Exception {
        return createEvent(ba, str, str2, true, obj);
    }

    private Object createEvent(BA ba, String str, String str2, boolean z, Object obj) throws Exception {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{getCorrectClassName(str)}, new InvocationHandler(str2, z, ba, getObject(), obj) { // from class: anywheresoftware.b4j.object.JavaObject.1
            String eventName;
            Thread t = Thread.currentThread();
            private final /* synthetic */ BA val$ba;
            private final /* synthetic */ boolean val$fromUi;
            private final /* synthetic */ Object val$obj;
            private final /* synthetic */ Object val$returnValue;

            {
                this.val$fromUi = z;
                this.val$ba = ba;
                this.val$obj = obj;
                this.val$returnValue = obj;
                this.eventName = String.valueOf(str2.toLowerCase(BA.cul)) + "_event";
            }

            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj2, Method method, Object[] objArr) throws Throwable {
                Object[] objArr2 = {method.getName(), objArr};
                if (Thread.currentThread() == this.t) {
                    if (!this.val$fromUi) {
                        Object objRaiseEvent = this.val$ba.raiseEvent(this.val$obj, this.eventName, objArr2);
                        return objRaiseEvent == null ? this.val$returnValue : objRaiseEvent;
                    }
                    this.val$ba.raiseEventFromUI(this.val$obj, this.eventName, objArr2);
                    return this.val$returnValue;
                }
                this.val$ba.raiseEventFromDifferentThread(this.val$obj, null, 0, this.eventName, false, objArr2);
                return this.val$returnValue;
            }
        });
    }

    private Class<?> getCurrentClass() {
        if (getObject() instanceof Class) {
            return (Class) getObject();
        }
        return getObject().getClass();
    }

    private static Class<?> getCorrectClassName(String str) throws ClassNotFoundException {
        if (str.equals("Object")) {
            return Object.class;
        }
        if (str.equals("String")) {
            return String.class;
        }
        for (int i = 0; i < 3; i++) {
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException e) {
                int iLastIndexOf = str.lastIndexOf(".");
                if (iLastIndexOf != -1) {
                    str = String.valueOf(str.substring(0, iLastIndexOf)) + "$" + str.substring(iLastIndexOf + 1);
                } else {
                    if (i != 0) {
                        throw e;
                    }
                    str = "java.lang." + str;
                }
            }
        }
        throw new ClassNotFoundException(str);
    }

    static class FieldCache {
        private ConcurrentHashMap<String, HashMap<String, Field>> cache = new ConcurrentHashMap<>();

        FieldCache() {
        }

        public Field getField(String str, String str2) throws Exception {
            HashMap<String, Field> map = this.cache.get(str);
            if (map == null) {
                HashMap<String, Field> map2 = new HashMap<>();
                for (Field field : Class.forName(str).getFields()) {
                    map2.put(field.getName(), field);
                }
                this.cache.put(str, map2);
                map = map2;
            }
            Field field2 = map.get(str2);
            if (field2 != null) {
                return field2;
            }
            throw new RuntimeException("Field: " + str2 + " not found in: " + str);
        }
    }

    static class MethodCache {
        private static final HashMap<String, ArrayList<Method>> cantGetAllMethods = new HashMap<>();
        private ConcurrentHashMap<String, HashMap<String, ArrayList<Method>>> cache = new ConcurrentHashMap<>();

        MethodCache() {
        }

        public List<Method> getMethod(String str, String str2, Object[] objArr) throws Exception {
            HashMap<String, ArrayList<Method>> map = this.cache.get(str);
            if (map == null) {
                map = new HashMap<>();
                Class<?> cls = Class.forName(str);
                if ((cls.getModifiers() & 1) == 0) {
                    fillNonPublicB4JMethods(str, map, cls);
                } else {
                    Method[] methods = null;
                    try {
                        methods = cls.getMethods();
                    } catch (Throwable unused) {
                        BA.LogError("Cannot get methods of class: " + str + ", disabling cache.");
                        map = cantGetAllMethods;
                    }
                    fillMethods(methods, map);
                }
                this.cache.put(str, map);
            }
            if (map == cantGetAllMethods) {
                Class<?> cls2 = Class.forName(str);
                Class<?>[] clsArr = new Class[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    Object obj = objArr[i];
                    clsArr[i] = obj == null ? Object.class : obj.getClass();
                }
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    try {
                        return Arrays.asList(cls2.getMethod(str2, clsArr));
                    } catch (NoSuchMethodException unused2) {
                        Class<?> cls3 = clsArr[i2];
                        Class<? super Object> superclass = cls3.getSuperclass();
                        if (superclass != null) {
                            clsArr[i2] = superclass;
                            try {
                                return Arrays.asList(cls2.getMethod(str2, clsArr));
                            } catch (NoSuchMethodException unused3) {
                                clsArr[i2] = cls3;
                            }
                        }
                    }
                }
            }
            ArrayList<Method> arrayList = map.get(str2);
            if (arrayList != null) {
                return arrayList;
            }
            throw new RuntimeException("Method: " + str2 + " not found in: " + str);
        }

        private void fillMethods(Method[] methodArr, HashMap<String, ArrayList<Method>> map) {
            if (methodArr == null) {
                return;
            }
            for (Method method : methodArr) {
                ArrayList<Method> arrayList = map.get(method.getName());
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    map.put(method.getName(), arrayList);
                }
                arrayList.add(method);
            }
        }

        private void fillNonPublicB4JMethods(String str, HashMap<String, ArrayList<Method>> map, Class<?> cls) {
            for (Class<?> cls2 : cls.getInterfaces()) {
                fillMethods(cls2.getMethods(), map);
            }
            for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
                if ((superclass.getModifiers() & 1) != 0) {
                    fillMethods(superclass.getMethods(), map);
                    return;
                }
            }
        }
    }
}
