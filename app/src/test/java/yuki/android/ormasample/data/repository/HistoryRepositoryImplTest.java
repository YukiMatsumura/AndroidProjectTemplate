package yuki.android.ormasample.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;
import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.test.CustomRobolectricTestRunner;
import yuki.android.ormasample.test.DefaultRobolectricRule;
import yuki.android.ormasample.test.RobolectricConfig;
import yuki.android.ormasample.test.util.TestDateUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(CustomRobolectricTestRunner.class)
@Config(sdk = RobolectricConfig.RUNNING_SDK_VERSION)
@MediumTest
public class HistoryRepositoryImplTest {

    @Rule
    public TestRule rule = new DefaultRobolectricRule();

    private Context context = RuntimeEnvironment.application;

    private OrmaDatabase orma;

    private HistoryRepositoryImpl historyRepo;

    @Before
    public void setup() {
        orma = TestOrmaUtils.getDatabase(context);
        historyRepo = new HistoryRepositoryImpl(orma);

        // テスト前にすべてのHistoryレコードを消去する
        historyRepo.deleteAll();
    }

    @Test
    public void 期間指定検索() throws Exception {
        orma.insertIntoHistory(
                new History().setLabel("Test01").setActiveDate(TestDateUtils.YEAR_2016));
        orma.insertIntoHistory(
                new History().setLabel("Test02").setActiveDate(TestDateUtils.YEAR_2017));
        orma.insertIntoHistory(
                new History().setLabel("Test03").setActiveDate(TestDateUtils.YEAR_2100));

        List<History> result = historyRepo.findBetween(
                TestDateUtils.YEAR_2016, TestDateUtils.YEAR_2017).toList().toBlocking().single();

        assertThat("期間指定検索の結果が期待する件数と異なる", result.size(), is(2));
        for (History history : result) {
            if (TestDateUtils.YEAR_2016 <= history.activeDate &&
                    history.activeDate <= TestDateUtils.YEAR_2017) {
                continue;
            }
            fail("期間指定条件外の結果がヒットしている");
        }
    }

    @Test
    public void ヒストリの追加() throws Exception {
        historyRepo.insert(Collections.singletonList(
                new History().setLabel("Test1").setActiveDate(123456789L)))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long rowId) {
                        assertThat("1件のHistory追加でrowIdが1(開始値)以外のものが追加された", rowId, is(1L));
                        History history = historyRepo.findById(rowId)
                                .toBlocking()
                                .value();
                        assertThat("追加されたレコードのlabelがリクエスト時から編集されている",
                                history.label, equalTo("Test1"));
                        assertThat("追加されたレコードのactiveDateがリクエスト時から編集されている",
                                history.activeDate, equalTo(123456789L));
                    }
                });
    }
}