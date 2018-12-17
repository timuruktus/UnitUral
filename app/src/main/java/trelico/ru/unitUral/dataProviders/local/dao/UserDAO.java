package trelico.ru.unitUral.dataProviders.local.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import trelico.ru.unitUral.models.web.User;

@Dao
public interface UserDAO {


    @Query("SELECT * FROM " + User.TABLE_NAME)
    List<User> getAll();

    @Query("SELECT COUNT(*) FROM " + User.TABLE_NAME)
    int count();

    @Query("SELECT * FROM " + User.TABLE_NAME +" WHERE email = :email")
    User getById(String email);

    @Query("SELECT * FROM " + User.TABLE_NAME + " LIMIT :count OFFSET :offset")
    List<User> getUsersOffset(int offset, int count);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<User> users);

    @Update
    int update(User user);

    @Update
    int[] updateList(List<User> users);

    @Delete
    void delete(User user);

    @Delete
    void deleteList(List<User> users);
}
