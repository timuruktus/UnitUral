package trelico.ru.unitUral.projects;

import android.app.Application;
import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.surfstudio.android.easyadapter.EasyAdapter;
import ru.surfstudio.android.easyadapter.ItemList;
import ru.surfstudio.android.easyadapter.animator.BaseItemAnimator;
import trelico.ru.unitUral.models.CustomResponse;
import trelico.ru.unitUral.models.local.LoadingState;
import trelico.ru.unitUral.models.web.Project;
import trelico.ru.unitUral.repositories.ProjectsRepository;

import static trelico.ru.unitUral.dataProviders.web.BackendlessAPI.DEFAULT_PAGE_SIZE;

public class ProjectsViewModel extends AndroidViewModel{

    private MutableLiveData<CustomResponse<List<Project>>> projectsLiveData;
    private EasyAdapter adapter;
    private RecyclerView.ItemAnimator itemAnimator;
    private LinearLayoutManager linearLayoutManager;
    private Context appContext;
    @Inject
    ProjectsRepository projectsRepository;


    public ProjectsViewModel(@NonNull Application application){
        super(application);
        this.appContext = application;
    }

    LiveData<CustomResponse<List<Project>>> getProjects(){
        if(projectsLiveData == null){
            projectsLiveData = projectsRepository.getProjects(0, DEFAULT_PAGE_SIZE);
        }
        return projectsLiveData;
    }

    void loadMoreData(int offset, int pageSize){
        LiveData<CustomResponse<List<Project>>> newData = projectsRepository.getProjects(offset, pageSize);
        newData.observeForever(customResponse -> projectsLiveData.setValue(customResponse));
    }

    public EasyAdapter getAdapter(){
        if(adapter == null){
            adapter = new EasyAdapter();
            configureAdapter();
        }
        return adapter;
    }

    public void configureAdapter(){
        adapter.setInfiniteScroll(true);
    }
}
