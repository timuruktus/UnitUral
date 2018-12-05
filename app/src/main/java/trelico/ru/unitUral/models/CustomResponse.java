package trelico.ru.unitUral.models;

import androidx.lifecycle.LiveData;

public class CustomResponse {

    private boolean isError = false;
    private String errorText;
    private Object data;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
