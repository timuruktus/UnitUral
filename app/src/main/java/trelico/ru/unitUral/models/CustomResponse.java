package trelico.ru.unitUral.models;

import androidx.lifecycle.LiveData;

public class CustomResponse<T> {

    private boolean isError = false;
    private String errorText;
    private T data;
    private boolean isFromWeb;

    public boolean isFromWeb() {
        return isFromWeb;
    }

    public void setFromWeb(boolean fromWeb) {
        isFromWeb = fromWeb;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
