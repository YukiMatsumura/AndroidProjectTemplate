package yuki.android.ormasample.domain.exception;

public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();
}
