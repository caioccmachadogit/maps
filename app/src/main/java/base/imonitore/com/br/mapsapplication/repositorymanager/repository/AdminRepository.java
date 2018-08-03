package base.imonitore.com.br.mapsapplication.repositorymanager.repository;

import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.Date;

import base.imonitore.com.br.mapsapplication.repositorymanager.DatabaseManager;
import base.imonitore.com.br.mapsapplication.repositorymanager.dao.AdminDAO;
import base.imonitore.com.br.mapsapplication.repositorymanager.entity.Admin;
import base.imonitore.com.br.mapsapplication.servicesmanager.CallBackGeneric;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;
import base.imonitore.com.br.mapsapplication.utils.DateUtils;
import base.imonitore.com.br.mapsapplication.utils.Utils;

/**
 * Created by ccouto on 19/02/2018.
 */
@EBean
public class AdminRepository {

    private String TAG = getClass().getSimpleName();

    private AdminDAO dao = DatabaseManager.getInstance().getAdminDAO();

    @Background
    public void insertAndDeleteTransation(CallBackGeneric callBack, Admin admin){
        try {
            Long id = dao.insertAndDeleteTransation(admin);
            if(id > 0){
                if(callBack != null) {
                    callBack.callBackSuccess(id);
                }
            }
            else {
                if(callBack != null) {
                    callBack.callBackError(null);
                }
            }
            Log.d(TAG, "insertAndDeleteTransation-> "+id);
        }
        catch (Exception e){
            Log.e(TAG, "insertAndDeleteTransation->", e);
            if(callBack != null)
                callBack.callBackError(null);
        }
    }

    @Background
    public void updateDataSincronismoAgendaTransation(CallBackGeneric callBack, Date dataSincronismoAgenda){
        try {
            int affect = dao.updateDataSincronismoAgendaTransation(dataSincronismoAgenda);
            if(affect > 0){
                if(callBack != null) {
                    callBack.callBackSuccess(affect);
                }
            }
            else {
                if(callBack != null) {
                    callBack.callBackError(null);
                }
            }
            Log.d(TAG, "updateTransation-> "+affect);
        }
        catch (Exception e){
            Log.e(TAG, "updateTransation", e);
            if(callBack != null)
                callBack.callBackError(null);
        }
    }

    @Background
    public void verifySincronismoAgenda(CallBackGeneric callBack){
        try {
            Admin admin = dao.verifySincronismoAgenda(DateUtils.getDateNowMaxMin(true), DateUtils.getDateNowMaxMin(false));
            if(admin != null){
                Log.d(TAG,"verifySincronismoAgenda-> "+admin.getDataSincronismoAgenda());
                if(callBack != null) {
                    callBack.callBackSuccess(admin);
                }
            }
            else{
                Log.d(TAG,"verifySincronismoAgenda->NAO ENCONTRADO");
                if(callBack != null) {
                    callBack.callBackError(null);
                }
            }
        }
        catch (Exception ex){
            Log.e(TAG, "verifySincronismoAgenda", ex);
            if(callBack != null) {
                callBack.callBackError(null);
            }
        }
    }

    public Admin readLimit(){
        try {
            Admin admin = dao.readLimit();
            if(admin != null){
                Log.d(TAG,"readLimit-> "+admin.getDataSincronismoAgenda());
                return admin;
            }
            else {
                Log.e(TAG, "readLimit->NAO ENCONTRADO");
                return null;
            }
        }
        catch (Exception ex){
            Log.e(TAG, "readLimit", ex);
            return null;
        }
    }

    public Admin authAdmin(LoginIn in){
        String senhaMd5 = Utils.md5(in.getSenha());
        try {
            Admin find = dao.authAdmin(in.getCodigoEmpresa(),in.getLogin(),senhaMd5);
            if(find != null){
                Log.d(TAG,"authAdmin-> "+find.getNomePessoa());
                return find;
            }
            else {
                Log.d(TAG, "authAdmin->NAO ENCONTRADO");
                return null;
            }
        }
        catch (Exception ex){
            Log.e(TAG, "authAdmin", ex);
            return null;
        }
    }

    @Background
    public void update(CallBackGeneric callBack, Admin admin){
        try {
            int affect = dao.update(admin);
            if(affect > 0){
                Log.d(TAG, "update-> "+affect);
                if(callBack != null)
                    callBack.callBackSuccess(affect);
            }
            else
            if(callBack != null)
                callBack.callBackError(null);
        }
        catch (Exception e){
            Log.e(TAG, "update", e);
            if(callBack != null)
                callBack.callBackError(null);
        }
    }
}
