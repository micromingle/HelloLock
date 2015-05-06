package micromingle.com.hellolock.HelloLockView;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 5/4/2015.
 */

public class LockPatternUtils {

    private static final String PREFERENCES_LOCK_PATTERN = "PreferencesLockPattern";
    private static final String KEY_LOCK_PATTERN = "KeyLockPattern";

    public final static int MIN_LENGTH_OF_CELLS = 4;

    public static List<LockPatternView.Cell> stringToPattern(String string) {
        List<LockPatternView.Cell> result = new ArrayList<LockPatternView.Cell>();

        final byte[] bytes = string.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            result.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return result;
    }

    public static String patternToString(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return "";
        }
        final int patternSize = pattern.size();

        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return new String(res);
    }

    public static void saveToPreferences(Context context, String lockPattern) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_LOCK_PATTERN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_LOCK_PATTERN, lockPattern);
        editor.commit();
    }

    public static String loadFromPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_LOCK_PATTERN, Context.MODE_PRIVATE);
        return sp.getString(KEY_LOCK_PATTERN, null);
    }

    public static String convertToSequence(List<LockPatternView.Cell> pattern) {
        if (pattern == null || pattern.size() < MIN_LENGTH_OF_CELLS) {
            return null;
        }
        StringBuffer strBuff = new StringBuffer(pattern.size());
        for (LockPatternView.Cell cell : pattern) {
            strBuff.append(cell.getRow());
            strBuff.append(cell.getColumn());
        }
        return strBuff.toString();
    }
}
