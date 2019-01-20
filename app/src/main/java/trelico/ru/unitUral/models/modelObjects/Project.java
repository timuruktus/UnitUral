package trelico.ru.unitUral.models.modelObjects;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import trelico.ru.unitUral.models.interfaces.RecyclerItem;

@Entity
public class Project implements RecyclerItem{

    public static final String TABLE_NAME = "project";
    private String description;
    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true)
    private String id;
    private String name;
    @ColumnInfo(name = "is_able_to_edit")
    private boolean isAbleToEdit;
    @ColumnInfo(name = "team_name")
    private String teamName;
    private long created;
    private long updated;
    private int color;

    public Project(){
    }

    public Project(String description, String id, String name, boolean isAbleToEdit,
                   List<User> users, String teamName, long created, long updated, int color){
        this.description = description;
        this.id = id;
        this.name = name;
        this.isAbleToEdit = isAbleToEdit;
        this.teamName = teamName;
        this.created = created;
        this.updated = updated;
        this.color = color;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isAbleToEdit(){
        return isAbleToEdit;
    }

    public void setAbleToEdit(boolean ableToEdit){
        isAbleToEdit = ableToEdit;
    }

    public long getCreated(){
        return created;
    }

    public void setCreated(long created){
        this.created = created;
    }

    public long getUpdated(){
        return updated;
    }

    public void setUpdated(long updated){
        this.updated = updated;
    }

    public String getTeamName(){
        return teamName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
    }

    public boolean isTheSame(Project anotherProject){
        return description.equals(anotherProject.description)
                && id.equals(anotherProject.id)
                && name.equals(anotherProject.name)
                && isAbleToEdit == anotherProject.isAbleToEdit
                && teamName.equals(anotherProject.teamName)
                && created == anotherProject.created
                && updated == anotherProject.updated;
    }

    @Override
    public boolean equals(@Nullable Object obj){
        if(obj instanceof Project){
            return isTheSame((Project) obj);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode(){
        return Objects.hash(description, id, name, isAbleToEdit, teamName, created, updated, color);
    }

}
