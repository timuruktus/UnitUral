package trelico.ru.unitUral.repositories;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trelico.ru.unitUral.dataProviders.local.db.AppDatabase;
import trelico.ru.unitUral.dataProviders.web.BackendlessAPI;
import trelico.ru.unitUral.models.CustomResponse;
import trelico.ru.unitUral.models.local.DataSourceType;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsRepository{


    @Inject
    BackendlessAPI backendlessAPI;
    @Inject
    AppDatabase appDatabase;

    public MutableLiveData<CustomResponse<List<Project>>> getProjects(int offset, int pageSize){
        MutableLiveData<CustomResponse<List<Project>>> responseData = new MutableLiveData<>();
        backendlessAPI.getProjects(offset, pageSize).enqueue(new Callback<List<Project>>(){
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response){
                if(response.body() == null){
                    onFailure(call, new Throwable());
                } else{
                    CustomResponse<List<Project>> customResponse = new CustomResponse<>();
                    customResponse.setData(response.body());
                    customResponse.setDataSourceType(DataSourceType.WEB);
                    responseData.setValue(customResponse);
                    cacheProjects(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t){
                CustomResponse<List<Project>> customResponse = new CustomResponse<>();
                List<Project> data = getProjectsFromCache(offset, pageSize);
                if(offset == 0 && data.size() == 0){
                    customResponse.setDataSourceType(DataSourceType.ERROR);
                    customResponse.setErrorText(t.getMessage());
                }else{
                    customResponse.setDataSourceType(DataSourceType.CACHE);
                    customResponse.setData(data);
                }
                responseData.setValue(customResponse);
            }
        });
        return responseData;
    }

    private List<Project> getProjectsFromCache(int offset, int pageSize){
        return appDatabase.projectDAO().getProjectsOffset(offset, pageSize);
    }


    public void cacheProjects(List<Project> projects){
        appDatabase.projectDAO().deleteAll();
        appDatabase.projectDAO().insertList(projects);
    }


}
