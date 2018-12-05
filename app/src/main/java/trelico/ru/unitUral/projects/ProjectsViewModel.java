package trelico.ru.unitUral.projects;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import trelico.ru.unitUral.models.CustomResponse;
import trelico.ru.unitUral.models.web.Project;
import trelico.ru.unitUral.repositories.ProjectsRepository;
import trelico.ru.unitUral.utils.MainThreadExecutor;

import static trelico.ru.unitUral.dataSources.web.BackendlessAPI.DEFAULT_PAGE_SIZE;

public class ProjectsViewModel extends ViewModel {

    private LiveData<List<Project>> allProjects;
    private ProjectsAdapter adapter;
    @Inject
    ProjectsRepository projectsRepository;
    @Inject
    ProjectsDataSource projectsDataSource;

    public LiveData<List<Project>> getAllProjects(int offset, int pageSize) {
        LiveData<CustomResponse> response = projectsRepository.getAllProjects(offset, pageSize);
        response.observeForever(customResponse -> {
            if(customResponse.isError()){
                response = projectsRepository.getAllProjectsFromCache(offset, pageSize);
            }
        });

        LiveData<List<Project>> projects
        if(response.isError()){
            response = projectsRepository.getAllProjectsFromCache(offset, pageSize);
        }
        return allProjects;
    }

    public ProjectsAdapter getAdapter(){
        return adapter;
    }

    public void configureAdapter(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(DEFAULT_PAGE_SIZE)
                .build();

        PagedList<Project> pagedList = new PagedList.Builder<>(projectsDataSource, config)
                .setNotifyExecutor(new MainThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();

        // Adapter
        ProjectsDiffUtil projectsDiffUtil = new ProjectsDiffUtil();
        adapter = new ProjectsAdapter(projectsDiffUtil);
        adapter.submitList(pagedList);
    }
}
