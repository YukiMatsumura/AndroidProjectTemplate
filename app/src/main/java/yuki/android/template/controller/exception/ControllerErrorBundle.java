package yuki.android.template.controller.exception;

import yuki.android.template.model.exception.ErrorBundle;

public class ControllerErrorBundle implements ErrorBundle {

    private final Exception exception;

    public ControllerErrorBundle(Exception exception) {
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
