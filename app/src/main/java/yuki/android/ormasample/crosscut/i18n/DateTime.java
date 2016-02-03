package yuki.android.ormasample.crosscut.i18n;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

public class DateTime {

    /**
     * 時間操作時に指定されるフィールド(年・月・日)の種別.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIELD_YEAR, FIELD_MONTH, FIELD_DAY})
    public @interface CALENDAR_FIELD {

    }

    public static final int FIELD_YEAR = 0;

    public static final int FIELD_MONTH = 1;

    public static final int FIELD_DAY = 2;

    public static long now() {
        return System.currentTimeMillis();
    }

    /**
     * baseMilliseconds の時間に value で指定された時間を加えたepoch値(UTC)を返す.
     *
     * @param baseMilliseconds 時間を加える基準時間.
     * @param field            加える時間の種別(年, 月, 日). {@link CALENDAR_FIELD}から指定される.
     * @param value            加える時間の量. 1以上である必要がある.
     * @return baseMilliseconds に value で指定された時間を加えたepoch値(UTC)
     */
    public static long plus(long baseMilliseconds, @CALENDAR_FIELD int field, @IntRange(from = 1) int value) {
        return add(baseMilliseconds, field, value);
    }

    /**
     * baseMilliseconds の時間に value で指定された時間を減じたepoch値(UTC)を返す.
     *
     * @param baseMilliseconds 時間を加える基準時間.
     * @param field            減じる時間の種別(年, 月, 日). {@link CALENDAR_FIELD}から指定される.
     * @param value            減じる時間の量. 1以上である必要がある.
     * @return baseMilliseconds に value で指定された時間を減じたepoch値(UTC)
     */
    public static long minus(long baseMilliseconds, @CALENDAR_FIELD int field, @IntRange(from = 1) int value) {
        return add(baseMilliseconds, field, value);
    }

    private static long add(long baseMilliseconds, @CALENDAR_FIELD int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(baseMilliseconds);
        cal.add(field, value);
        return cal.getTimeInMillis();
    }
}
