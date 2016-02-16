package yuki.android.template.repository.exception;

import yuki.android.template.domain.exception.ErrorBundle;

public class RepositoryErrorBundle implements ErrorBundle {

    private final Exception exception;

    public RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return this.exception != null
                ? this.exception.getMessage()
                : "";
    }
}
