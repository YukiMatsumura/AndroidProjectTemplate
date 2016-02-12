package yuki.android.ormasample.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import yuki.android.ormasample.domain.executor.PostExecutionThread;
import yuki.android.ormasample.domain.executor.ThreadExecutor;
import yuki.android.ormasample.domain.repository.HistoryRepository;

public class HistoryViewUseCaseImplTest {

    @Mock
    HistoryRepository historyRepository;

    @Mock
    ThreadExecutor threadExecutor;

    @Mock
    PostExecutionThread postExecutionThread;

    @InjectMocks
    HistoryViewUseCaseImpl historyViewUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void UseCaseをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(null, threadExecutor, postExecutionThread);
    }

    @Test(expected = NullPointerException.class)
    public void ThreadExecutorをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(historyRepository, null,
                postExecutionThread);
    }

    @Test(expected = NullPointerException.class)
    public void PostExecutionThreadをnullで初期化() throws Exception {
        new HistoryViewUseCaseImpl(historyRepository, threadExecutor, null);
    }

    @Test
    public void 最近のヒストリを取得() throws Exception {
        // TODO: リファクタリングの続き. Domain層にmodelとmapperをかませる
//        final List<History> expect = Arrays.asList(
//                new History().setId(1L).setLabel("Test1").setActiveDate(1234L),
//                new History().setId(2L).setLabel("Test2").setActiveDate(6789L));
//        when(historyRepository.findBetween(anyLong(), anyLong())).thenReturn(Observable.from(expect));
//
//        HistoryViewUseCase useCase = new HistoryViewUseCaseImpl(
//                historyRepository,
//                new TestCurrentThreadExecutor(),
//                new TestExecutionThread());
//        useCase.showLatestHistory(new DefaultSubscriber<List<History>>() {
//            @Override
//            public void onNext(List<History> histories) {
//                assertThat("準備したクエリ結果とは異なるiteratorが返却された", histories, equalTo(expect));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                fail("最近のヒストリ取得に失敗した");
//            }
//        });
    }
}