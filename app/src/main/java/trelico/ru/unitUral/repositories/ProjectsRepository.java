package trelico.ru.unitUral.repositories;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trelico.ru.unitUral.dataProviders.web.BackendlessAPI;
import trelico.ru.unitUral.models.CustomResponse;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsRepository {


    @Inject
    BackendlessAPI backendlessAPI;


    public LiveData<CustomResponse> getAllProjects(int offset, int pageSize){
        MutableLiveData<CustomResponse> data = new MutableLiveData<>();
        backendlessAPI.getProjects(offset, pageSize).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.body() == null){
                    onFailure(call, new Throwable());
                }else{
                    CustomResponse customResponse = new CustomResponse();
                    customResponse.setData(response.body());
                    data.setValue(customResponse);
                    cacheProjects(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                CustomResponse response = new CustomResponse();
                response.setError(true);
                response.setErrorText(t.getMessage());
                data.setValue(response);
            }
        });
        return data;
    }

    public MutableLiveData<CustomResponse> getAllProjectsFromCache(int offset, int pageSize){
        MutableLiveData<CustomResponse> data = new MutableLiveData<>();
        backendlessAPI.getProjects(offset, pageSize).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.body() == null) onFailure(call, new Throwable());

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });
        return data;
    }


    public void cacheProjects(List<Project> projects){

    }



}
