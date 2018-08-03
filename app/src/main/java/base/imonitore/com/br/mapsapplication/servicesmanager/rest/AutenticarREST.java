package base.imonitore.com.br.mapsapplication.servicesmanager.rest;

import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginOut;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LogoutIn;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LogoutOut;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ccouto on 27/10/2017.
 */

public interface AutenticarREST {

    String versao = "v1/";
    String url = "seguranca/"+versao+"login/";

    @POST(url+"acessar")
    Call<LoginOut> login(@Body LoginIn loginIn);

    @POST(url+"encerrar")
    Call<LogoutOut> logout(@Body LogoutIn logoutIn);
}
