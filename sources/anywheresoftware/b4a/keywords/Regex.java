package anywheresoftware.b4a.keywords;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class Regex {
    public static final int CASE_INSENSITIVE = 2;
    public static final int MULTILINE = 8;
    private static LinkedHashMap<PatternAndOptions, Pattern> cachedPatterns;

    private static Pattern getPattern(String str, int i) {
        if (cachedPatterns == null) {
            cachedPatterns = new LinkedHashMap<>();
        }
        PatternAndOptions patternAndOptions = new PatternAndOptions(str, i);
        Pattern pattern = cachedPatterns.get(patternAndOptions);
        if (pattern != null) {
            return pattern;
        }
        Pattern patternCompile = Pattern.compile(str, i);
        cachedPatterns.put(patternAndOptions, patternCompile);
        if (cachedPatterns.size() > 50) {
            Iterator<Map.Entry<PatternAndOptions, Pattern>> it = cachedPatterns.entrySet().iterator();
            for (int i2 = 0; i2 < 25; i2++) {
                it.next();
                it.remove();
            }
        }
        return patternCompile;
    }

    public static boolean IsMatch(String str, String str2) {
        return IsMatch2(str, 0, str2);
    }

    public static boolean IsMatch2(String str, int i, String str2) {
        return getPattern(str, i).matcher(str2).matches();
    }

    public static String Replace(String str, String str2, String str3) {
        return Replace2(str, 0, str2, str3);
    }

    public static String Replace2(String str, int i, String str2, String str3) {
        return getPattern(str, i).matcher(str2).replaceAll(str3);
    }

    public static String[] Split(String str, String str2) {
        return Split2(str, 0, str2);
    }

    public static String[] Split2(String str, int i, String str2) {
        return getPattern(str, i).split(str2);
    }

    public static MatcherWrapper Matcher(String str, String str2) {
        return Matcher2(str, 0, str2);
    }

    public static MatcherWrapper Matcher2(String str, int i, String str2) {
        MatcherWrapper matcherWrapper = new MatcherWrapper();
        matcherWrapper.setObject(getPattern(str, i).matcher(str2));
        return matcherWrapper;
    }

    @BA.ShortName("Matcher")
    public static class MatcherWrapper extends AbsObjectWrapper<Matcher> {
        public boolean Find() {
            return getObject().find();
        }

        public String Group(int i) {
            return getObject().group(i);
        }

        public int getGroupCount() {
            return getObject().groupCount();
        }

        public String getMatch() {
            return getObject().group();
        }

        public int GetStart(int i) {
            return getObject().start(i);
        }

        public int GetEnd(int i) {
            return getObject().end(i);
        }
    }

    private static class PatternAndOptions {
        public final int options;
        public final String pattern;

        public PatternAndOptions(String str, int i) {
            this.pattern = str;
            this.options = i;
        }

        public int hashCode() {
            int i = (this.options + 31) * 31;
            String str = this.pattern;
            return i + (str == null ? 0 : str.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            PatternAndOptions patternAndOptions = (PatternAndOptions) obj;
            if (this.options != patternAndOptions.options) {
                return false;
            }
            String str = this.pattern;
            if (str == null) {
                if (patternAndOptions.pattern != null) {
                    return false;
                }
            } else if (!str.equals(patternAndOptions.pattern)) {
                return false;
            }
            return true;
        }
    }
}
