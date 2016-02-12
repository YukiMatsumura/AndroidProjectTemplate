package yuki.android.ormasample.crosscut.i18n;

import org.junit.After;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class DateTimeUtilsTest {

    @Test
    public void 現在時刻のフリーズ() throws Exception {
        DateTimeUtils.stopTheTime(123456789L);
        assertThat("現在時刻のフリーズに失敗している", DateTimeUtils.now(), is(123456789L));
    }

    @Test
    public void 未来時間の取得_年() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .plus(DateTimeUtils.now(), 2, DateTimeUtils.FIELD_YEAR));

        assertThat("未来時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2019));
        assertThat("未来時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.JANUARY));
        assertThat("未来時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(1));
    }

    @Test
    public void 未来時間の取得_月() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .plus(DateTimeUtils.now(), 2, DateTimeUtils.FIELD_MONTH));

        assertThat("未来時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2017));
        assertThat("未来時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.MARCH));
        assertThat("未来時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(1));
    }

    @Test
    public void 未来時間の取得_日() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .plus(DateTimeUtils.now(), 2, DateTimeUtils.FIELD_DAY));

        assertThat("未来時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2017));
        assertThat("未来時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.JANUARY));
        assertThat("未来時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(3));
    }

    @Test
    public void 過去時間の取得_年() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .minus(DateTimeUtils.now(), -2, DateTimeUtils.FIELD_YEAR));

        assertThat("過去時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2015));
        assertThat("過去時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.JANUARY));
        assertThat("過去時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(1));
    }

    @Test
    public void 過去時間の取得_月() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .minus(DateTimeUtils.now(), -2, DateTimeUtils.FIELD_MONTH));

        assertThat("過去時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2016));
        assertThat("過去時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.NOVEMBER));
        assertThat("過去時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(1));
    }

    @Test
    public void 過去時間の取得_日() throws Exception {
        Calendar fakeTime = Calendar.getInstance(Locale.JAPAN);
        fakeTime.set(2017, Calendar.JANUARY, 1, 0, 0,
                0);  // 平年 2017/1/1 00:00:00
        DateTimeUtils.stopTheTime(fakeTime.getTimeInMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(DateTimeUtils
                .minus(DateTimeUtils.now(), -2, DateTimeUtils.FIELD_DAY));

        assertThat("過去時間の取得で年の設定が不正", cal.get(Calendar.YEAR), is(2016));
        assertThat("過去時間の取得で月の設定が不正", cal.get(Calendar.MONTH),
                is(Calendar.DECEMBER));
        assertThat("過去時間の取得で日の設定が不正", cal.get(Calendar.DATE), is(30));
    }

    @After
    public void tearDown() {
        DateTimeUtils.startTheTime();
    }
}