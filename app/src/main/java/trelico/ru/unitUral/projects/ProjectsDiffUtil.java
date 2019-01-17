package trelico.ru.unitUral.projects;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import trelico.ru.unitUral.models.modelObjects.Project;

public class ProjectsDiffUtil extends DiffUtil.Callback{


    private List<Project> oldList;
    private List<Project> newList;

    public ProjectsDiffUtil(List<Project> oldList, List<Project> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    public ProjectsDiffUtil(){
    }

    public ProjectsDiffUtil(List<Project> oldList){
        this.oldList = oldList;
    }

    public void updateData(List<Project> newList){
        this.oldList = this.newList;
        this.newList = newList;
    }


    @Override
    public int getOldListSize(){
        return oldList.size();
    }

    @Override
    public int getNewListSize(){
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition){
        Project oldProject = oldList.get(oldItemPosition);
        Project newProject = newList.get(newItemPosition);
        return oldProject.getId().equals(newProject.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition){
        Project oldProject = oldList.get(oldItemPosition);
        Project newProject = newList.get(newItemPosition);
        return oldProject.isTheSame(newProject);
    }
}

