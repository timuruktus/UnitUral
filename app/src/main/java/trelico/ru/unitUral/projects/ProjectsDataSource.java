package trelico.ru.unitUral.projects;

import android.util.Log;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;
import trelico.ru.unitUral.models.web.Project;
import trelico.ru.unitUral.repositories.ProjectsRepository;

import static trelico.ru.unitUral.MyApplication.LOG_TAG;

public class ProjectsDataSource extends PositionalDataSource<Project> {

    @Inject
    ProjectsRepository projectsRepository;

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Project> callback) {
        Log.d(LOG_TAG, "ProjectsDataSource.loadInitial, requestedStartPosition = "
                + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize);

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Project> callback) {
        Log.d(LOG_TAG, "ProjectsDataSource.loadRange, startPosition = "
                + params.startPosition + ", loadSize = " + params.loadSize);
    }
}
