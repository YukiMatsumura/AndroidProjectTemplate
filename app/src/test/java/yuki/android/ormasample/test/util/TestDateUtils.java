package yuki.android.ormasample.test.util;

import java.util.Calendar;
import java.util.Locale;

public class TestDateUtils {

    /**
     * 100で割り切れない通常の平年.
     */
    public static final long YEAR_2017;

    /**
     * 4で割り切れ, 400で割り切れない通常の閏年.
     */
    public static final long YEAR_2016;

    /**
     * 100で割り切れ, 400で割り切れない平年.
     */
    public static final long YEAR_2100;

    /**
     * 400で割り切れる閏年.
     */
    public static final long YEAR_2400;


    static {
        Calendar calendar = Calendar.getInstance(Locale.JAPAN);

        // 平年 2017/1/1 00:00:00
        calendar.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        YEAR_2017 = calendar.getTimeInMillis();

        // 閏年 2016/1/1 00:00:00
        calendar.set(2016, Calendar.JANUARY, 1, 0, 0, 0);
        YEAR_2016 = calendar.getTimeInMillis();

        // 100年平年 2100/1/1 00:00:00
        calendar.set(2100, Calendar.JANUARY, 1, 0, 0, 0);
        YEAR_2100 = calendar.getTimeInMillis();

        // 400年閏年 2400/1/1 00:00:00
        calendar.set(2400, Calendar.JANUARY, 1, 0, 0, 0);
        YEAR_2400 = calendar.getTimeInMillis();
    }
}
