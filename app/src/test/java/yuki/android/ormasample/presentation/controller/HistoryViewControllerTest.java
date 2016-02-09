package yuki.android.ormasample.presentation.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.List;

import rx.SingleSubscriber;
import rx.Subscriber;
import yuki.android.ormasample.data.executor.TestCurrentThreadExecutor;
import yuki.android.ormasample.data.repository.HistoryRepositoryImpl;
import yuki.android.ormasample.data.repository.TestOrmaUtils;
import yuki.android.ormasample.di.component.ActivityComponent;
import yuki.android.ormasample.domain.executor.TestPostCurrentThread;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCase;
import yuki.android.ormasample.domain.usecase.HistoryViewUseCaseImpl;
import yuki.android.ormasample.presentation.presenter.HistoryViewPresenter;
import yuki.android.ormasample.presentation.view.view.HistoryListView;
import yuki.android.ormasample.test.CustomRobolectricTestRunner;
import yuki.android.ormasample.test.DefaultRobolectricRule;
import yuki.android.ormasample.test.RobolectricConfig;

import static org.mockito.Mockito.*;

@RunWith(CustomRobolectricTestRunner.class)
@Config(sdk = RobolectricConfig.RUNNING_SDK_VERSION)
@MediumTest
public class HistoryViewControllerTest {

    @Rule
    public TestRule rule = new DefaultRobolectricRule();

    private Context context = RuntimeEnvironment.application;

    @Mock
    HistoryListView mockListView;

    @Mock
    ActivityComponent mockActivityComponent;

    HistoryViewUseCase useCase;

    HistoryViewController presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        useCase = spy(new HistoryViewUseCaseImpl(
                new HistoryRepositoryImpl(TestOrmaUtils.getDatabase(context)),
                new TestCurrentThreadExecutor(),
                new TestPostCurrentThread()));
        presenter = new HistoryViewController(new HistoryViewPresenter(), useCase);
    }

    @Test
    public void ヒストリの表示() throws Exception {
        presenter.showHistory();

        verify(useCase, times(1)).showLatestHistory(any(Subscriber.class));
        verify(useCase, times(1)).getHistoryCount(any(SingleSubscriber.class));
    }

    @Test
    public void ヒストリの削除() throws Exception {

    }

    @Test
    public void ヒストリの追加() throws Exception {

    }
}