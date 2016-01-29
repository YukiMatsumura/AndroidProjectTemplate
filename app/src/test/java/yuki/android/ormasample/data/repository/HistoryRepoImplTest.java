package yuki.android.ormasample.data.repository;

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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static yuki.android.ormasample.data.repository.TestOrmaUtils.TestModelFactory;
import static yuki.android.ormasample.test.util.TestDateUtils.YEAR_2016;
import static yuki.android.ormasample.test.util.TestDateUtils.YEAR_2017;
import static yuki.android.ormasample.test.util.TestDateUtils.YEAR_2100;
import static yuki.android.ormasample.test.util.TestDateUtils.YEAR_2400;

@RunWith(CustomRobolectricTestRunner.class)
@Config(sdk = RobolectricConfig.RUNNING_SDK_VERSION)
@MediumTest
public class HistoryRepoImplTest {

    @Rule
    public TestRule rule = new DefaultRobolectricRule();

    private Context context = RuntimeEnvironment.application;

    @Test(expected = NullPointerException.class)
    public void DI引数_NULL() throws Exception {
        new HistoryRepoImpl(null);
    }

    @Test
    public void History情報の全件取得() throws Exception {
        OrmaDatabase orma = TestOrmaUtils.getDatabase(context);
        History inserted = orma.createHistory(new TestModelFactory(
                new History()
                        .setDate(YEAR_2016)
                        .setLabel("TestLabel")));

        HistoryRepoImpl repo = new HistoryRepoImpl(orma);
        List<History> found = repo.findAll();
        assertThat("予め用意したレコード数以上の件数が取得された", found.size(), is(1));
        assertThat("予め用意したレコードのidではないものが取得された", found.get(0).id, equalTo(inserted.id));
        assertThat("予め用意したレコードのdateではないものが取得された", found.get(0).date, equalTo(inserted.date));
        assertThat("予め用意したレコードのlabelではないものが取得された", found.get(0).label, equalTo(inserted.label));
    }

    @Test
    public void History情報の追加() throws Exception {
        OrmaDatabase orma = TestOrmaUtils.getDatabase(context);

        HistoryRepoImpl repo = new HistoryRepoImpl(orma);
        long rowId = repo.insertAt(new History()
                .setDate(YEAR_2017)
                .setLabel("Test1"));
        assertThat("History情報の追加ができない", rowId, not(-1L));

        List<History> histories = orma.selectFromHistory().toList();
        assertThat("History情報の1件追加に失敗した", histories.size(), is(1));

        History inserted = histories.get(0);
        assertThat("History情報のidが正しく設定されていない", inserted.id, equalTo(rowId));
        assertThat("History情報のdateが正しく追加されていない", inserted.date, equalTo(YEAR_2017));
        assertThat("History情報がlabelが正しく追加されていない", inserted.label, equalTo("Test1"));
    }

    @Test
    public void History情報の更新() throws Exception {
        OrmaDatabase orma = TestOrmaUtils.getDatabase(context);
        History target = orma.createHistory(new TestModelFactory(
                new History()
                        .setDate(YEAR_2016)
                        .setLabel("TestLabel2")));
        History nonTarget = orma.createHistory(new TestModelFactory(
                new History()
                        .setDate(YEAR_2400)
                        .setLabel("TestLabel3")));

        HistoryRepoImpl repo = new HistoryRepoImpl(orma);
        int count = repo.updateAt(target.id,
                new History().setDate(YEAR_2100).setLabel("TEST_LABEL2"));
        assertThat("History情報の更新に失敗した", count, is(1));

        History targetHistory = orma.selectFromHistory().idEq(target.id).value();
        assertThat("History情報のidが不正に更新されている", targetHistory.id, equalTo(target.id));
        assertThat("History情報のdateが正しく更新されていない", targetHistory.date, equalTo(YEAR_2100));
        assertThat("History情報がlabelが正しく更新されていない", targetHistory.label, equalTo("TEST_LABEL2"));

        History nonTargetHistory = orma.selectFromHistory().idEq(nonTarget.id).value();
        assertThat("更新対象外のHistory情報のidが不正に更新されている", nonTargetHistory.id, equalTo(nonTarget.id));
        assertThat("更新対象外のHistory情報のdateが不正に更新されている", nonTargetHistory.date, equalTo(nonTarget.date));
        assertThat("更新対象外のHistory情報がlabelが不正に更新されている", nonTargetHistory.label, equalTo(nonTarget.label));
    }

    @Test
    public void History情報の削除() throws Exception {
        OrmaDatabase orma = TestOrmaUtils.getDatabase(context);
        History target = orma.createHistory(new TestModelFactory(
                new History()
                        .setDate(YEAR_2016)
                        .setLabel("TestLabel2")));
        History nonTarget = orma.createHistory(new TestModelFactory(
                new History()
                        .setDate(YEAR_2400)
                        .setLabel("TestLabel3")));

        HistoryRepoImpl repo = new HistoryRepoImpl(orma);
        int deleteCount = repo.deleteAt(target.id);
        assertThat("History情報の削除に失敗した", deleteCount, is(1));

        int targetCount = orma.selectFromHistory().idEq(target.id).count();
        assertThat("削除したはずのレコードが存在している", targetCount, is(0));

        History nonTargetHistory = orma.selectFromHistory().idEq(nonTarget.id).value();
        assertThat("削除対象外のHistory情報のidが不正に更新されている", nonTargetHistory.id, equalTo(nonTarget.id));
        assertThat("削除対象外のHistory情報のdateが不正に更新されている", nonTargetHistory.date, equalTo(nonTarget.date));
        assertThat("削除対象外のHistory情報がlabelが不正に更新されている", nonTargetHistory.label, equalTo(nonTarget.label));
    }
}