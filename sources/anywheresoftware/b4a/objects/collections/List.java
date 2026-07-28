package anywheresoftware.b4a.objects.collections;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("List")
public class List extends AbsObjectWrapper<java.util.List<Object>> implements BA.IterableList {
    public void Initialize() {
        setObject(new ArrayList());
    }

    public void Initialize2(List list) {
        setObject(list.getObject());
    }

    public void Clear() {
        getObject().clear();
    }

    public void Add(Object obj) {
        Object objGet;
        if (BA.debugMode && getObject().size() > 0 && (objGet = Get(getSize() - 1)) != null && objGet == obj && !(objGet instanceof String) && !(objGet instanceof Number) && !(objGet instanceof Boolean)) {
            BA.LogInfo("Warning: same object added to list multiple times.");
        }
        getObject().add(obj);
    }

    public void AddAll(List list) {
        getObject().addAll(list.getObject());
    }

    public void AddAllAt(int i, List list) {
        getObject().addAll(i, list.getObject());
    }

    public void RemoveAt(int i) {
        getObject().remove(i);
    }

    public void InsertAt(int i, Object obj) {
        getObject().add(i, obj);
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public Object Get(int i) {
        return getObject().get(i);
    }

    public void Set(int i, Object obj) {
        getObject().set(i, obj);
    }

    @Override // anywheresoftware.b4a.BA.IterableList
    public int getSize() {
        return getObject().size();
    }

    public int IndexOf(Object obj) {
        return getObject().indexOf(obj);
    }

    public void Sort(boolean z) {
        if (z) {
            Collections.sort(getObject());
        } else {
            Collections.sort(getObject(), new Comparator<Comparable>() { // from class: anywheresoftware.b4a.objects.collections.List.1
                @Override // java.util.Comparator
                public int compare(Comparable comparable, Comparable comparable2) {
                    return comparable2.compareTo(comparable);
                }
            });
        }
    }

    public void SortType(String str, boolean z) throws NoSuchFieldException, SecurityException {
        sortList(str, z, false);
    }

    public void SortTypeCaseInsensitive(String str, boolean z) throws NoSuchFieldException, SecurityException {
        sortList(str, z, true);
    }

    private void sortList(String str, final boolean z, final boolean z2) throws NoSuchFieldException, SecurityException {
        if (getSize() == 0) {
            return;
        }
        final Field declaredField = Get(0).getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        Collections.sort(getObject(), new Comparator<Object>() { // from class: anywheresoftware.b4a.objects.collections.List.2
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                int iCompareTo;
                try {
                    if (z2) {
                        iCompareTo = String.valueOf(declaredField.get(obj)).compareToIgnoreCase(String.valueOf(declaredField.get(obj2)));
                    } else {
                        iCompareTo = ((Comparable) declaredField.get(obj)).compareTo(declaredField.get(obj2));
                    }
                    return iCompareTo * (z ? 1 : -1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void SortCaseInsensitive(boolean z) {
        if (z) {
            Collections.sort(getObject(), new Comparator<Comparable>() { // from class: anywheresoftware.b4a.objects.collections.List.3
                @Override // java.util.Comparator
                public int compare(Comparable comparable, Comparable comparable2) {
                    return comparable.toString().compareToIgnoreCase(comparable2.toString());
                }
            });
        } else {
            Collections.sort(getObject(), new Comparator<Comparable>() { // from class: anywheresoftware.b4a.objects.collections.List.4
                @Override // java.util.Comparator
                public int compare(Comparable comparable, Comparable comparable2) {
                    return comparable2.toString().compareToIgnoreCase(comparable.toString());
                }
            });
        }
    }
}
