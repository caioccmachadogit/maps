package base.imonitore.com.br.mapsapplication.servicesmanager.services;

import org.androidannotations.annotations.EBean;

import base.imonitore.com.br.mapsapplication.servicesmanager.CallBackGeneric;
import base.imonitore.com.br.mapsapplication.servicesmanager.ServiceEnum;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginOut;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LogoutIn;
import base.imonitore.com.br.mapsapplication.servicesmanager.rest.AutenticarREST;
import base.imonitore.com.br.mapsapplication.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ccouto on 02/01/2018.
 */
@EBean(scope= EBean.Scope.Singleton)
public class AutenticarService extends BaseService {

    private AutenticarREST rest = (AutenticarREST) retrofitCreate(AutenticarREST.class, ServiceEnum.MOCK);

    public void efetuarLogin(final CallBackGeneric callBackGeneric, LoginIn loginIn, boolean md5){
        if(md5)
            loginIn.setSenha(Utils.md5(loginIn.getSenha()));
        loginIn.setImei(Utils.getDeviceImei());
        loginIn.setVersao(Utils.getVersionName());
        loginIn.setMacAddress(Utils.getMacAddress());
        Call<LoginOut> call = rest.login(loginIn);
        call.enqueue(new Callback<LoginOut>() {
            @Override
            public void onResponse(Call<LoginOut> call, Response<LoginOut> response) {
                onResponseValidation(callBackGeneric, response, call.request().url().toString());
            }

            @Override
            public void onFailure(Call<LoginOut> call, Throwable t) {
                onFailureDefault(callBackGeneric,t, call.request().url().toString());
            }
        });
    }

    public void efetuarLogout(final CallBackGeneric callBackGeneric, LogoutIn logoutIn){
        Call call = rest.logout(logoutIn);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                onResponseValidation(callBackGeneric, response, call.request().url().toString());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                onFailureDefault(callBackGeneric,t, call.request().url().toString());
            }
        });
    }
}
