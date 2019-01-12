package trelico.ru.unitUral.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import trelico.ru.unitUral.models.local.DataSourceType;

public class CustomResponse<T> {

    private String errorText;
    private T data;
    @NonNull
    private DataSourceType dataSourceType;

    @NonNull
    public DataSourceType getDataSourceType(){
        return dataSourceType;
    }

    public void setDataSourceType(@NonNull DataSourceType dataSourceType){
        this.dataSourceType = dataSourceType;
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
