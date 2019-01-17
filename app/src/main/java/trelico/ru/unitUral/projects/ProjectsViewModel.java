package trelico.ru.unitUral.projects;

import android.app.Application;

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
        if(projectsLiveData == null){
            projectsLiveData = projectsRepository.getProjects(0, DEFAULT_PAGE_SIZE);
        }
        return projectsLiveData;
    }

    void loadMoreData(int offset, int pageSize){
        LiveData<CustomResponse<List<Project>>> newData = projectsRepository.getProjects(offset, pageSize);
        newData.observeForever(customResponse ->{
            List<Project> newProjects = customResponse.getData();
            List<Project> oldProjects = projectsLiveData.getValue().getData();
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
