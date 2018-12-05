package trelico.ru.unitUral.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import toothpick.Toothpick;
import trelico.ru.unitUral.MyApplication;
import trelico.ru.unitUral.R;

import static trelico.ru.unitUral.dataSources.web.BackendlessAPI.DEFAULT_PAGE_SIZE;

public class ProjectsFragment extends Fragment {

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

    private ProjectsViewModel viewModel;
    public static String SCOPE_NAME = "projectsScope";

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MyApplication.INSTANCE.getInjector().inject(this);
        return inflater.inflate(R.layout.projects_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        Toothpick.inject(viewModel, MyApplication.INSTANCE.getScopeStorage().projectsScope);
        viewModel.configureAdapter();
        viewModel.getAllProjects(0, DEFAULT_PAGE_SIZE);

        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projectsLent.setAdapter(viewModel.getAdapter());
    }
}
