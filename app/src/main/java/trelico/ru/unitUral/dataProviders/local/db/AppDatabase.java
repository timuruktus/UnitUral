package trelico.ru.unitUral.dataProviders.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import trelico.ru.unitUral.dataProviders.local.dao.ProjectDAO;
import trelico.ru.unitUral.dataProviders.local.dao.UserDAO;
import trelico.ru.unitUral.models.modelObjects.Project;
import trelico.ru.unitUral.models.modelObjects.User;

@Database(entities = {Project.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProjectDAO projectDAO();
    public abstract UserDAO userDAO();
}
