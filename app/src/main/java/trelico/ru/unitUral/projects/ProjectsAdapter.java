package trelico.ru.unitUral.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trelico.ru.unitUral.R;
import trelico.ru.unitUral.models.interfaces.RecyclerItem;
import trelico.ru.unitUral.models.modelObjects.LoadMoreStub;
import trelico.ru.unitUral.models.modelObjects.Project;
import trelico.ru.unitUral.models.viewObjects.LoadMoreViewHolder;

public class ProjectsAdapter extends RecyclerView.Adapter{

    private List<RecyclerItem> data;
    private ProjectAdapterListener projectAdapterListener;
    private boolean isFinalDataSet = false;
    @Inject
    private Context context;
    public static final int PROJECT = 1;
    public static final int LOAD_MORE_STUB = 0;
    private static final int ITEMS_BEFORE_LOAD_MORE = 4;

    public ProjectsAdapter(List data, ProjectAdapterListener projectAdapterListener){
        this.data = data;
        this.projectAdapterListener = projectAdapterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == LOAD_MORE_STUB){
            View view = inflater.inflate(R.layout.load_more_stub, parent, false);
            return new LoadMoreViewHolder(view);
        } else{
            View view = inflater.inflate(R.layout.project_post, parent, false);
            return new ProjectViewHolder(view, projectAdapterListener);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(isFinalDataSet) return PROJECT;
        else if(position == data.size() - 1 && hasLoadMoreStub()) return LOAD_MORE_STUB;
        else return PROJECT;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        if(holder instanceof ProjectViewHolder){
            ((ProjectViewHolder) holder).setData((Project) data.get(position));
        }
        if(data.size() - position <= ITEMS_BEFORE_LOAD_MORE && !hasLoadMoreStub()){
            projectAdapterListener.onLoadMore(data.size());
            addLoadMoreStub();
        }
    }

    @Override
    public int getItemCount(){
        if(isFinalDataSet) return data.size();
        if(hasLoadMoreStub()){
            return data.size() - 1;
        } else{
            return data.size();
        }
    }

    public void updateData(List<Project> projects){
        data.clear();
        data.addAll(projects);
    }

    public void addData(List<Project> projects){
        addData(projects, false);
    }

    public void addData(List<Project> projects, boolean isFinalDataSet){
        if(hasLoadMoreStub()){
            hideLoadMoreStub();
        }
        data.addAll(projects);
        if(!isFinalDataSet) addLoadMoreStub();
        this.isFinalDataSet = isFinalDataSet;
    }

    public void addLoadMoreStub(){
        data.add(new LoadMoreStub());
    }

    public void hideLoadMoreStub(){
        if(hasLoadMoreStub()){
            data.remove(data.size() - 1);
            notifyItemRemoved(data.size() - 1);
        }
    }

    public boolean hasLoadMoreStub(){
        if(data.size() == 0) return false;
        return data.get(data.size() - 1) instanceof LoadMoreStub;
    }

    public List<Project> getData(){
        List<Project> dataWithoutStubs = new ArrayList<>();
        for(RecyclerItem item : data){
            if(!(item instanceof LoadMoreStub)){
                dataWithoutStubs.add((Project) item);
            }
        }
        return dataWithoutStubs;
    }



    interface ProjectAdapterListener{
        void onProjectClicked(Project project);
        void onLoadMore(int offset);
    }


    static class ProjectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.projectTeamName)
        AppCompatTextView projectTeamName;
        @BindView(R.id.projectName)
        AppCompatTextView projectName;
        @BindView(R.id.projectColor)
        AppCompatImageView projectColor;
        @BindView(R.id.container)
        ConstraintLayout container;
        private View view;
        private ProjectAdapterListener projectClickListener;
        private Project project;

        ProjectViewHolder(View view, ProjectAdapterListener projectClickListener){
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
            this.projectClickListener = projectClickListener;
        }

        protected void setData(Project project){
            this.project = project;
        }

        @OnClick(R.id.container)
        public void onClick(){
            projectClickListener.onProjectClicked(project);
        }
    }
}
