package base.imonitore.com.br.mapsapplication.repositorymanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import base.imonitore.com.br.mapsapplication.repositorymanager.dao.AdminDAO;
import base.imonitore.com.br.mapsapplication.repositorymanager.entity.Admin;

/**
 * Created by ccouto on 27/10/2017.
 */
@Database(entities = {Admin.class},
        version = 1)
@TypeConverters({Converters.class})
public abstract class DataBase extends RoomDatabase {

    public abstract AdminDAO getAdminDAO();

}
