package anywheresoftware.b4a.objects.collections;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("Map")
public class Map extends AbsObjectWrapper<java.util.Map> implements BA.B4aDebuggable {
    public void Initialize() {
        setObject(new MyMap());
    }

    public Object Put(Object obj, Object obj2) {
        return getObject().put(obj, obj2);
    }

    public Object Remove(Object obj) {
        return getObject().remove(obj);
    }

    public Object Get(Object obj) {
        return getObject().get(obj);
    }

    public Object GetDefault(Object obj, Object obj2) {
        Object obj3 = getObject().get(obj);
        return obj3 == null ? obj2 : obj3;
    }

    public void Clear() {
        getObject().clear();
    }

    public Object GetKeyAt(int i) {
        java.util.Map object = getObject();
        if (object instanceof MyMap) {
            return ((MyMap) object).getKey(i);
        }
        throw new RuntimeException("method not supported. Use For Each instead.");
    }

    public Object GetValueAt(int i) {
        java.util.Map object = getObject();
        if (object instanceof MyMap) {
            return ((MyMap) object).getValue(i);
        }
        throw new RuntimeException("method not supported. Use For Each instead.");
    }

    public int getSize() {
        return getObject().size();
    }

    public boolean ContainsKey(Object obj) {
        return getObject().containsKey(obj);
    }

    public BA.IterableList Keys() {
        return new IterableMap(true);
    }

    public BA.IterableList Values() {
        return new IterableMap(false);
    }

    public class IterableMap implements BA.IterableList {
        private final Iterator iterator;

        public IterableMap(boolean z) {
            if (z) {
                this.iterator = Map.this.getObject().keySet().iterator();
            } else {
                this.iterator = Map.this.getObject().values().iterator();
            }
        }

        @Override // anywheresoftware.b4a.BA.IterableList
        public int getSize() {
            return Map.this.getSize();
        }

        @Override // anywheresoftware.b4a.BA.IterableList
        public Object Get(int i) {
            return this.iterator.next();
        }
    }

    @Override // anywheresoftware.b4a.BA.B4aDebuggable
    public Object[] debug(int i, boolean[] zArr) {
        int i2 = 2;
        int iMin = (Math.min(getSize(), i) + 1) * 2;
        Object[] objArr = new Object[iMin];
        objArr[0] = "Size";
        objArr[1] = Integer.valueOf(getSize());
        for (Map.Entry entry : getObject().entrySet()) {
            if (i2 >= iMin - 1) {
                break;
            }
            String strValueOf = String.valueOf(entry.getKey());
            objArr[i2] = strValueOf;
            if (strValueOf.toString().length() == 0) {
                objArr[i2] = "(empty string)";
            }
            objArr[i2 + 1] = entry.getValue();
            i2 += 2;
        }
        zArr[0] = false;
        return objArr;
    }

    public static class MyMap implements java.util.Map<Object, Object> {
        private Map.Entry<Object, Object> currentEntry;
        private LinkedHashMap<Object, Object> innerMap = new LinkedHashMap<>();
        private Iterator<Map.Entry<Object, Object>> iterator;
        private int iteratorPosition;

        public Object getKey(int i) {
            return getEntry(i).getKey();
        }

        public Object getValue(int i) {
            return getEntry(i).getValue();
        }

        private Map.Entry<Object, Object> getEntry(int i) {
            int i2;
            Iterator<Map.Entry<Object, Object>> it = this.iterator;
            if (it != null && (i2 = this.iteratorPosition) != i) {
                if (i2 == i - 1) {
                    this.currentEntry = it.next();
                    this.iteratorPosition++;
                } else {
                    this.iterator = null;
                }
            }
            if (this.iterator == null) {
                this.iterator = this.innerMap.entrySet().iterator();
                for (int i3 = 0; i3 <= i; i3++) {
                    this.currentEntry = this.iterator.next();
                }
                this.iteratorPosition = i;
            }
            return this.currentEntry;
        }

        @Override // java.util.Map
        public void clear() {
            this.iterator = null;
            this.innerMap.clear();
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return this.innerMap.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return this.innerMap.containsValue(obj);
        }

        @Override // java.util.Map
        public Set<Map.Entry<Object, Object>> entrySet() {
            return this.innerMap.entrySet();
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            return this.innerMap.get(obj);
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.innerMap.isEmpty();
        }

        @Override // java.util.Map
        public Set<Object> keySet() {
            return this.innerMap.keySet();
        }

        @Override // java.util.Map
        public Object put(Object obj, Object obj2) {
            this.iterator = null;
            return this.innerMap.put(obj, obj2);
        }

        @Override // java.util.Map
        public void putAll(java.util.Map<? extends Object, ? extends Object> map) {
            this.iterator = null;
            this.innerMap.putAll(map);
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            this.iterator = null;
            return this.innerMap.remove(obj);
        }

        @Override // java.util.Map
        public int size() {
            return this.innerMap.size();
        }

        @Override // java.util.Map
        public Collection<Object> values() {
            return this.innerMap.values();
        }

        public String toString() {
            return this.innerMap.toString();
        }
    }
}
