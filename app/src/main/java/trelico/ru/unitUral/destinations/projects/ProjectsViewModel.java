package trelico.ru.unitUral.destinations.projects;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.models.modelObjects.CustomResponse;
import trelico.ru.unitUral.models.modelObjects.Project;
import trelico.ru.unitUral.repositories.ProjectsRepository;

import static trelico.ru.unitUral.MyApplication.TAG;
import static trelico.ru.unitUral.dataProviders.web.BackendlessAPI.DEFAULT_PAGE_SIZE;

public class ProjectsViewModel extends AndroidViewModel{

    private MutableLiveData<CustomResponse<List<Project>>> projectsLiveData;
    private ProjectsAdapter adapter;
    private RecyclerView.ItemAnimator itemAnimator;
    private LinearLayoutManager linearLayoutManager;
    @Inject
    ProjectsRepository projectsRepository;


    public ProjectsViewModel(@NonNull Application application){
        super(application);
    }

    LiveData<CustomResponse<List<Project>>> getProjectsLiveData(){
        Log.d(TAG, "getProjectsLiveData in ProjectsViewModel");
        if(projectsLiveData == null){
            Toothpick.inject(projectsRepository, MyApplication.INSTANCE.getScopeStorage().projectsScope);
            projectsLiveData = projectsRepository.getProjects(0, DEFAULT_PAGE_SIZE);
        }
        return projectsLiveData;
    }

    void loadMoreData(int offset, int pageSize){
        Log.d(TAG, "loadMoreData in ProjectsViewModel");
        LiveData<CustomResponse<List<Project>>> newData = projectsRepository.getProjects(offset, pageSize);
        newData.observeForever(customResponse ->{
            List<Project> newProjects = customResponse.getData();
            List<Project> oldProjects = projectsLiveData.getValue().getData();
            if(offset < oldProjects.size()){
                oldProjects.clear();
            }
            oldProjects.addAll(newProjects);
            customResponse.setData(oldProjects);
            projectsLiveData.setValue(customResponse);

        });
    }

    public ProjectsAdapter getAdapter(){
        if(adapter == null){
            adapter = new ProjectsAdapter(new ArrayList(), getAdapterListener());
            Toothpick.inject(adapter, MyApplication.INSTANCE.getScopeStorage().projectsScope);
            configureAdapter();
        }
        Log.d(TAG, "getAdapter in ProjectsViewModel");
        return adapter;
    }


    private ProjectsAdapter.ProjectAdapterListener getAdapterListener(){
        return new ProjectsAdapter.ProjectAdapterListener(){
            @Override
            public void onProjectClicked(Project project){

            }

            @Override
            public void onLoadMore(int offset){
                loadMoreData(offset, DEFAULT_PAGE_SIZE);
            }
        };
    }

    public void configureAdapter(){

    }
}
