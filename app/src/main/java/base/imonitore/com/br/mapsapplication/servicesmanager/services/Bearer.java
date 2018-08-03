package base.imonitore.com.br.mapsapplication.servicesmanager.services;

import android.util.Log;

import com.google.gson.Gson;

import base.imonitore.com.br.mapsapplication.utils.RSAUtils;

public class Bearer {

    private String token;

    private String imei;

    private String datahora;

    public Bearer(String token, String imei, String datahora) {
        this.token = token;
        this.imei = imei;
        this.datahora = datahora;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    @Override
    public String toString() {
        try {
            String json = new Gson().toJson(this);
            Log.d(getClass().getSimpleName(),json);
            String encrypt = RSAUtils.encryptByPublicKey(json);
            return encrypt;
        }
        catch (Exception ex){
            Log.e("Bearer", "Token Nao gerado!", ex);
            return  "";
        }
    }
}
