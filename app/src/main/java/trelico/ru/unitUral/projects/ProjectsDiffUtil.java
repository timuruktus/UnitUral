package trelico.ru.unitUral.projects;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsDiffUtil extends DiffUtil.ItemCallback {

    @Override
    public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        Project oldProject = (Project) oldItem;
        Project newProject = (Project) newItem;
        return oldProject.getId().equals(newProject.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        Project oldProject = (Project) oldItem;
        Project newProject = (Project) newItem;
        return oldProject.isTheSame(newProject);
    }

}

