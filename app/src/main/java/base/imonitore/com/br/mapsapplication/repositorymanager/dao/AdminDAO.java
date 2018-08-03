package base.imonitore.com.br.mapsapplication.repositorymanager.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.Date;

import base.imonitore.com.br.mapsapplication.repositorymanager.entity.Admin;

/**
 * Created by ccouto on 27/10/2017.
 */
@Dao
public abstract class AdminDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract Long insert(Admin admin);

    @Query("DELETE FROM ADMIN_CFC")
    abstract int delete();

    @Query("SELECT * FROM ADMIN_CFC LIMIT 1")
    public abstract Admin readLimit();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int update(Admin admin);

    @Transaction
    public Long insertAndDeleteTransation(Admin admin){
        delete();
        return insert(admin);
    }

    @Transaction
    public int updateDataSincronismoAgendaTransation(Date dataSincronismoAgenda){
        Admin admin = readLimit();
        admin.setDataSincronismoAgenda(dataSincronismoAgenda);
        return update(admin);
    }

    @Query("SELECT * FROM ADMIN_CFC WHERE dataSincronismoAgenda >= :dateAtualMin AND dataSincronismoAgenda <= :dateAtualMax")
    public abstract Admin verifySincronismoAgenda(Date dateAtualMax, Date dateAtualMin);

    @Query("SELECT * FROM ADMIN_CFC WHERE codigoEmpresa == :codigoEmpresa AND "+
                        "login == :login AND senha == :senha")
    public abstract Admin authAdmin(String codigoEmpresa, String login, String senha);
}
