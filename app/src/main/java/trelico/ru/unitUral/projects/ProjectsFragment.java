package trelico.ru.unitUral.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.surfstudio.android.easyadapter.EasyAdapter;
import ru.surfstudio.android.easyadapter.ItemList;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.models.CustomResponse;
import trelico.ru.unitUral.models.local.DataSourceType;
import trelico.ru.unitUral.models.local.InternetConnection;
import trelico.ru.unitUral.models.web.Project;
import trelico.ru.unitUral.repositories.PhoneInfoRepository;

public class ProjectsFragment extends Fragment {

    public static String SCOPE_NAME = "projectsScope";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorIcon)
    ImageView errorIcon;
    @BindView(R.id.errorText)
    TextView errorText;
    @BindView(R.id.repeat)
    TextView repeat;
    @BindView(R.id.errorLayout)
    ConstraintLayout errorLayout;
    @BindView(R.id.projectsLent)
    RecyclerView projectsLent;

    private Unbinder unbinder;
    private EasyAdapter adapter;
    private ProjectsViewModel viewModel;
    private ProjectsItemController projectsItemController;
    @Inject
    PhoneInfoRepository phoneInfoRepository;
    LiveData<InternetConnection> internetStateLiveData;


    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Toothpick.inject(this, MyApplication.INSTANCE.getScopeStorage().projectsScope);
        View view = inflater.inflate(R.layout.projects_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        Toothpick.inject(viewModel, MyApplication.INSTANCE.getScopeStorage().projectsScope);
        internetStateLiveData = phoneInfoRepository.getInternetConnectionLiveData();
        viewModel.configureAdapter();
//        viewModel.getLoadingState().observe(this, getLoadingStateObserver());
        viewModel.getProjects().observe(this, new ProjectsObserver());
        adapter = new EasyAdapter();
    }


    private class ProjectsObserver implements Observer<CustomResponse<List<Project>>>{
        @Override
        public void onChanged(CustomResponse<List<Project>> customResponse){
            if(customResponse == null){
                showInitialLayout();
            }
            changeMainScreenLayout(customResponse.getDataSourceType());
            ItemList items = ItemList.create()
                    .addAll(customResponse.getData(), ProjectsItemController.create(getProjectClickListener()));
            adapter.setItems(items);
        }
    }

    private View.OnClickListener getProjectClickListener(){
        return v -> {
            //TODO
        };
    }

    private void changeMainScreenLayout(DataSourceType dataSourceType){
        if(dataSourceType == DataSourceType.CACHE){
            Toast.makeText(this.getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }else if(dataSourceType == DataSourceType.ERROR){
            showErrorLayout();
        }else{
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projectsLent.setAdapter(viewModel.getAdapter());
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
