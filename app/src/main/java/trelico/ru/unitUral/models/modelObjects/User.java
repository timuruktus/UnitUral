package trelico.ru.unitUral.models.modelObjects;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import trelico.ru.unitUral.models.interfaces.RecyclerItem;

@Entity
public class User implements RecyclerItem{


    public static final String TABLE_NAME = "user";
    @PrimaryKey
    @ColumnInfo(index = true)
    @NonNull
    private String email;
    private String name;
    private String role;
    @ColumnInfo(name = "is_leader")
    private boolean isLeader;
    @ColumnInfo(name = "project_id")
    private String projectId;

    public User(String email, String name, String role, boolean isLeader) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.isLeader = isLeader;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getProjectId(){
        return projectId;
    }

    public void setProjectId(String projectId){
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof User)) return false;
        User user = (User) o;
        return isLeader == user.isLeader &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(role, user.role) &&
                Objects.equals(projectId, user.projectId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(email, name, role, isLeader, projectId);
    }

}
