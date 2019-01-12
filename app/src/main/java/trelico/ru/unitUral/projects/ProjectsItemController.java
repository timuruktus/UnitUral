package trelico.ru.unitUral.projects;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.surfstudio.android.easyadapter.controller.BindableItemController;
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsItemController extends BindableItemController<Project, ProjectsItemController.ProjectViewHolder>{

    private static View.OnClickListener onProjectClickListener;

    private ProjectsItemController(){
    }

    public static ProjectsItemController create(View.OnClickListener onProjectClickListener){
        ProjectsItemController projectsItemController = new ProjectsItemController();
        ProjectsItemController.onProjectClickListener = onProjectClickListener;
        return projectsItemController;
    }

    @Override
    protected String getItemId(Project project){
        return String.valueOf(project.getId().hashCode());
    }

    @Override
    public ProjectViewHolder createViewHolder(ViewGroup viewGroup){
        return new ProjectViewHolder(viewGroup);
    }

    class ProjectViewHolder extends BindableViewHolder<Project>{
        @BindView(R.id.projectTeamName)
        AppCompatTextView projectTeamName;
        @BindView(R.id.projectName)
        AppCompatTextView projectName;
        @BindView(R.id.projectColor)
        AppCompatImageView projectColor;
        @BindView(R.id.container)
        ConstraintLayout container;

        ProjectViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        ProjectViewHolder(ViewGroup viewGroup){
            super(viewGroup, R.layout.project_post);
            ButterKnife.bind(this, viewGroup);
        }

        @Override
        public void bind(Project project){
            projectTeamName.setText(project.getTeamName());
            projectName.setText(project.getName());
            projectColor.setBackgroundColor((int) project.getColor());
            container.setOnClickListener(ProjectsItemController.onProjectClickListener);
        }
    }
}
