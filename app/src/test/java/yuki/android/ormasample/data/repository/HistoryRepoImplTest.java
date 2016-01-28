package yuki.android.ormasample.data.repository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.test.suitebuilder.annotation.MediumTest;

import java.util.List;

import yuki.android.ormasample.data.entity.History;
import yuki.android.ormasample.data.entity.OrmaDatabase;
import yuki.android.ormasample.test.CustomRobolectricTestRunner;
import yuki.android.ormasample.test.DefaultRobolectricRule;
import yuki.android.ormasample.test.RobolectricConfig;

@RunWith(CustomRobolectricTestRunner.class)
@Config(sdk = RobolectricConfig.RUNNING_SDK_VERSION)
@MediumTest
public class HistoryRepoImplTest {

    @Rule
    public TestRule rule = new DefaultRobolectricRule();

    @Test(expected = NullPointerException.class)
    public void DI引数NULL() throws Exception {
        new HistoryRepoImpl(null);
    }


    @Test
    public void History情報の全件取得() throws Exception {
        OrmaDatabase orma = OrmaDatabase.builder(RuntimeEnvironment.application).build();
        HistoryRepoImpl repo = new HistoryRepoImpl(orma);
        List<History> histories = repo.findAll();
    }
}