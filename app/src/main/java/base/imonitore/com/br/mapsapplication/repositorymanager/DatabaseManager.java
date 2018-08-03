package base.imonitore.com.br.mapsapplication.repositorymanager;

import android.arch.persistence.room.Room;
import android.content.Context;

import base.imonitore.com.br.mapsapplication.BaseApplication;
import base.imonitore.com.br.mapsapplication.repositorymanager.dao.AdminDAO;

/**
 * Created by ccouto on 27/10/2017.
 */

public class DatabaseManager {

    private static DataBase dbInstance;
    private static DatabaseManager instance;
    private static final String DB_NAME = "db.sqlite";

    public static DatabaseManager getInstance() {

        if (instance == null)
            instance = new DatabaseManager();

        initDB();

        return instance;
    }

    private static void initDB() {
        Context appContext = BaseApplication.getInstance().getApplicationContext();
        // Configura o Room
        if (dbInstance == null)
            dbInstance = Room.databaseBuilder(appContext, DataBase.class, DB_NAME).build();
    }

    public AdminDAO getAdminDAO() {
        return dbInstance.getAdminDAO();
    }
}
