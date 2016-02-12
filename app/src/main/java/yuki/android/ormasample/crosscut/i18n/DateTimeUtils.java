package yuki.android.ormasample.crosscut.i18n;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.VisibleForTesting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

import timber.log.Timber;

public abstract class DateTimeUtils {

    /**
     * 時間操作時に指定されるフィールド(年・月・日)の種別.
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIELD_YEAR, FIELD_MONTH, FIELD_DAY})
    public @interface DateTimeField {

    }

    public static final int FIELD_YEAR = Calendar.YEAR;

    public static final int FIELD_MONTH = Calendar.MONTH;

    public static final int FIELD_DAY = Calendar.DATE;

    public static class Clock {

        /**
         * 現在時刻をepoch値で取得する.
         *
         * @return 現在時刻のepoch値.
         */
        public long now() {
            return System.currentTimeMillis();
        }
    }

    private static Clock clock = new Clock();

    public static long now() {
        return clock.now();
    }

    @VisibleForTesting
    public static void stopTheTime(final long milliseconds) {
        Timber.i("!STOP THE TIME! - %tF %tT", milliseconds, milliseconds);
        clock = new Clock() {
            @Override
            public long now() {
                return milliseconds;
            }
        };
    }

    @VisibleForTesting
    public static void startTheTime() {
        Timber.i("!START THE TIME!");
        clock = new Clock();
    }

    /**
     * baseMilliseconds の時間に value で指定された時間を加えたepoch値(UTC)を返す.
     *
     * @param baseMilliseconds 時間を加える基準時間.
     * @param field            加える時間の種別(年, 月, 日). {@link DateTimeField}から指定される.
     * @param value            加える時間の量. 1以上である必要がある.
     * @return baseMilliseconds に value で指定された時間を加えたepoch値(UTC)
     */
    public static long plus(long baseMilliseconds,
            @IntRange(from = 1) int value, @DateTimeField int field) {
        return add(baseMilliseconds, value, field);
    }

    /**
     * baseMilliseconds の時間に value で指定された時間を減じたepoch値(UTC)を返す.
     *
     * @param baseMilliseconds 時間を加える基準時間.
     * @param field            減じる時間の種別(年, 月, 日). {@link DateTimeField}から指定される.
     * @param value            減じる時間の量. -1以下である必要がある.
     * @return baseMilliseconds に value で指定された時間を減じたepoch値(UTC)
     */
    public static long minus(long baseMilliseconds,
            @IntRange(to = -1) int value, @DateTimeField int field) {
        return add(baseMilliseconds, value, field);
    }

    private static long add(long baseMilliseconds,
            int value, @DateTimeField int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(baseMilliseconds);
        cal.add(field, value);
        return cal.getTimeInMillis();
    }
}
