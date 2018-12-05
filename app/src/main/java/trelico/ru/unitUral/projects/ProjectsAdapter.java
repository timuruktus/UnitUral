package trelico.ru.unitUral.projects;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import trelico.ru.unitUral.models.web.Project;

public class ProjectsAdapter extends PagedListAdapter<Project, ProjectsAdapter.ProjectsViewHolder> {


    protected ProjectsAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
        super(diffCallback);

    }

    @NonNull
    @Override
    public ProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position) {

    }



    class ProjectsViewHolder extends RecyclerView.ViewHolder{

        public ProjectsViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }
}
