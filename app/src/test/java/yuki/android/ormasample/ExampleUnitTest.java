package yuki.android.ormasample;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import org.junit.Test;

import rx.Observable;


public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        hoge().subscribe();
    }

    @RxLogObservable
    private Observable<Integer> hoge() {
        return Observable.range(0, 3);
    }
}