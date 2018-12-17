package trelico.ru.unitUral.models.web;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class User {


    public static final String TABLE_NAME = "user";
    @PrimaryKey
    @ColumnInfo(index = true)
    @NonNull
    private String email;
    private String name;
    private String role;
    @ColumnInfo(name = "is_leader")
    private boolean isLeader;

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
}
