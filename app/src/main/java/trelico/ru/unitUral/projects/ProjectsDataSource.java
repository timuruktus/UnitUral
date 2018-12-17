package trelico.ru.unitUral.projects;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trelico.ru.unitUral.dataProviders.local.dao.ProjectDAO;
import trelico.ru.unitUral.dataProviders.local.db.AppDatabase;
import trelico.ru.unitUral.dataProviders.web.BackendlessAPI;
import trelico.ru.unitUral.models.local.LoadingState;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsDataSource extends PositionalDataSource<Project> {

    @Inject
    BackendlessAPI backendlessAPI;
    @Inject
    AppDatabase appDatabase;

    private MutableLiveData<LoadingState> loadingState;

    public MutableLiveData<LoadingState> getLoadingState() {
        return loadingState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<Project> callback) {
//        Log.d(LOG_TAG, "ProjectsDataSource.loadInitial, requestedStartPosition = "
//                + params.requestedStartPosition + ", requestedLoadSize = "
//                + params.requestedLoadSize);
        if(loadingState == null){
            loadingState = new MutableLiveData<>();
        }
        loadingState.postValue(LoadingState.LOADING_INITIAL);
        backendlessAPI.getProjects(params.requestedStartPosition, params.pageSize)
                .enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.body() == null){
                    loadingState.postValue(LoadingState.ERROR_INITIAL_FROM_WEB);
                    loadInitialFromCache(params, callback);
                }else{
                    loadingState.postValue(LoadingState.FINISHED);
                    callback.onResult(response.body(), params.requestedStartPosition);
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                loadingState.postValue(LoadingState.ERROR_INITIAL_FROM_WEB);
                loadInitialFromCache(params, callback);
            }
        });
        //params.requestedStartPosition
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<Project> callback) {
//        Log.d(LOG_TAG, "ProjectsDataSource.loadRange, startPosition = "
//                + params.startPosition + ", loadSize = " + params.loadSize);
        if(loadingState == null){
            loadingState = new MutableLiveData<>();
        }
        loadingState.postValue(LoadingState.LOADING);
        backendlessAPI.getProjects(params.startPosition, params.loadSize)
                .enqueue(new Callback<List<Project>>() {
                    @Override
                    public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                        if(response.body() == null){
                            loadingState.postValue(LoadingState.ERROR_FROM_WEB);
                            loadRangeFromCache(params, callback);
                        }else{
                            loadingState.postValue(LoadingState.FINISHED);
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Project>> call, Throwable t) {
                        loadingState.postValue(LoadingState.ERROR_FROM_WEB);
                        loadRangeFromCache(params, callback);
                    }
                });

    }

    private void loadInitialFromCache(@NonNull LoadInitialParams params,
                                      @NonNull LoadInitialCallback<Project> callback){
        ProjectDAO projectDAO = appDatabase.projectDAO();
        if(projectDAO.count() == 0) {
            loadingState.postValue(LoadingState.ERROR_INITIAL_FROM_LOCAL);
        }else {
            callback.onResult(projectDAO.getProjectsOffset(params.requestedStartPosition,
                    params.pageSize), params.requestedStartPosition);
        }
    }

    private void loadRangeFromCache(@NonNull LoadRangeParams params,
                                    @NonNull LoadRangeCallback<Project> callback) {
        ProjectDAO projectDAO = appDatabase.projectDAO();
        if (projectDAO.count() == 0) {
            loadingState.postValue(LoadingState.ERROR_FROM_LOCAL);
        } else {
            callback.onResult(projectDAO.getProjectsOffset(params.startPosition,
                    params.loadSize));
        }

    }
}
