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

import java.util.List;

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

    @Before
    public void setup() {
        orma = TestOrmaUtils.getDatabase(context);
    }

    @Test
    public void 期間指定検索() throws Exception {
        orma.insertIntoHistory(
                new History().setLabel("Test01").setActiveDate(TestDateUtils.YEAR_2016));
        orma.insertIntoHistory(
                new History().setLabel("Test02").setActiveDate(TestDateUtils.YEAR_2017));
        orma.insertIntoHistory(
                new History().setLabel("Test02").setActiveDate(TestDateUtils.YEAR_2100));

        HistoryRepositoryImpl repository = new HistoryRepositoryImpl(orma);
        List<History> result = repository.findLatestHistory(
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

}