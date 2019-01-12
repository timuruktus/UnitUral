package trelico.ru.unitUral.dataProviders.local.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import trelico.ru.unitUral.models.web.Project;

@Dao
public interface ProjectDAO {

    @Query("SELECT * FROM " + Project.TABLE_NAME)
    List<Project> getAll();

    @Query("SELECT COUNT(*) FROM " + Project.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + Project.TABLE_NAME + " LIMIT :count OFFSET :offset")
    List<Project> getProjectsOffset(int offset, int count);

    @Query("SELECT * FROM " + Project.TABLE_NAME + " WHERE id = :id")
    Project getById(long id);

    @Query("DELETE FROM " + Project.TABLE_NAME)
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Project> projects);

    @Update
    void update(Project project);

    @Update
    void updateList(List<Project> projects);

    @Delete
    void delete(Project project);

    @Delete
    void deleteList(List<Project> projects);

}
