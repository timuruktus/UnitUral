package trelico.ru.unitUral.dataProviders.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import trelico.ru.unitUral.dataProviders.local.dao.ProjectDAO;
import trelico.ru.unitUral.dataProviders.local.dao.UserDAO;
import trelico.ru.unitUral.models.modelObjects.Project;

@Database(entities = {Project.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProjectDAO projectDAO();
    public abstract UserDAO userDAO();
}
