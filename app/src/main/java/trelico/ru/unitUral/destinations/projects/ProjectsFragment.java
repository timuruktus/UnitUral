package trelico.ru.unitUral.destinations.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.models.local.DataSourceType;
import trelico.ru.unitUral.models.local.InternetConnection;
import trelico.ru.unitUral.models.modelObjects.CustomResponse;
import trelico.ru.unitUral.models.modelObjects.Project;
import trelico.ru.unitUral.repositories.PhoneInfoRepository;

import static trelico.ru.unitUral.dataProviders.web.BackendlessAPI.DEFAULT_PAGE_SIZE;

public class ProjectsFragment extends Fragment{

    public static String SCOPE_NAME = "projectsScope";
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.errorIcon)
    AppCompatImageView errorIcon;
    @BindView(R.id.errorText)
    AppCompatTextView errorText;
    @BindView(R.id.repeat)
    AppCompatTextView repeat;
    @BindView(R.id.errorLayout)
    ConstraintLayout errorLayout;
    @BindView(R.id.projectsLent)
    RecyclerView projectsLent;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;


    private Unbinder unbinder;
    private ProjectsViewModel viewModel;
    @Inject
    PhoneInfoRepository phoneInfoRepository;
    LiveData<InternetConnection> internetStateLiveData;


    public static ProjectsFragment newInstance(){
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        Toothpick.inject(this, MyApplication.INSTANCE.getScopeStorage().projectsScope);
        View view = inflater.inflate(R.layout.projects_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        Toothpick.inject(viewModel, MyApplication.INSTANCE.getScopeStorage().projectsScope);
        projectsLent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        projectsLent.setAdapter(viewModel.getAdapter());
        internetStateLiveData = phoneInfoRepository.getInternetConnectionLiveData();
        viewModel.configureAdapter();
//        viewModel.getLoadingState().observe(this, getLoadingStateObserver());
        viewModel.getProjectsLiveData().observe(this, new ProjectsObserver());
        refreshLayout.setOnRefreshListener(getRefreshListener());
    }


    private class ProjectsObserver implements Observer<CustomResponse<List<Project>>>{
        @Override
        public void onChanged(CustomResponse<List<Project>> customResponse){
            if(customResponse == null){
                showInitialLayout();
            }
            changeMainScreenLayout(customResponse.getDataSourceType());
            List<Project> oldData = viewModel.getAdapter().getData();
            List<Project> newData = customResponse.getData();
            ProjectsDiffUtil projectDiffUtil = new ProjectsDiffUtil(oldData, newData);
            DiffUtil.DiffResult productDiffResult = DiffUtil.calculateDiff(projectDiffUtil);
            viewModel.getAdapter().updateData(newData);
            productDiffResult.dispatchUpdatesTo(viewModel.getAdapter());
            refreshLayout.setRefreshing(false);
        }
    }

    private SwipeRefreshLayout.OnRefreshListener getRefreshListener(){
        return () -> viewModel.loadMoreData(0, DEFAULT_PAGE_SIZE);
    }


    private void changeMainScreenLayout(DataSourceType dataSourceType){
        if(dataSourceType == DataSourceType.CACHE){
            Toast.makeText(this.getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else if(dataSourceType == DataSourceType.ERROR){
            showErrorLayout();
        } else{
            showProjectsLayout();
        }
    }

    private void showErrorLayout(){
        errorLayout.setVisibility(View.VISIBLE);
        projectsLent.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showInitialLayout(){
        errorLayout.setVisibility(View.INVISIBLE);
        projectsLent.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showProjectsLayout(){
        errorLayout.setVisibility(View.INVISIBLE);
        projectsLent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

//    private Observer<LoadingState> getLoadingStateObserver(){
//        return o -> {
//            InternetConnection internetState = internetStateLiveData.getValue();
//            if(o == LoadingState.LOADING_INITIAL){
//                showInitialLayout();
//            }else if(o == LoadingState.ERROR_FROM_WEB || o == LoadingState.ERROR_INITIAL_FROM_WEB){
//                if(internetState == InternetConnection.NOTHING){
//                    Toast.makeText(this.getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this.getContext(), R.string.data_fetch_error, Toast.LENGTH_SHORT).show();
//                }
//            }else if(o == LoadingState.ERROR_FROM_LOCAL){
//                Toast.makeText(this.getContext(), R.string.data_fetch_error, Toast.LENGTH_SHORT).show();
//            }else if(o == LoadingState.ERROR_INITIAL_FROM_LOCAL){
//                showErrorLayout();
//            }else if(o == LoadingState.FINISHED_INITIAL){
//                showProjectsLayout();
//            }
//        };
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView(){
        unbinder.unbind();
        super.onDestroyView();
    }


}
