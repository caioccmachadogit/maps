package base.imonitore.com.br.mapsapplication.remoteconfig;

import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

import base.imonitore.com.br.mapsapplication.BuildConfig;

/**
 * Created by ccouto on 02/01/2018.
 */

public class MapRemoteConfig {
    private static MapRemoteConfig instance;

    public static MapRemoteConfig getInstance() {
        if(instance == null)
            instance = new MapRemoteConfig();

        return instance;
    }

    public final String CONFIG_BASE_URL_DESENV = "base_url_desenv";
    public final String CONFIG_BASE_URL_TESTE = "base_url_teste";
    public final String CONFIG_BASE_URL_PROD = "base_url_prod";

    public final String RSA_KEY_VALUE_DESENV = "rsa_key_value_desenv";
    public final String RSA_KEY_VALUE_TESTE = "rsa_key_value_teste";
    public final String RSA_KEY_VALUE_PROD = "rsa_key_value_prod";

    private Map<String,Object> map;

    private boolean isSuccess;

    public Map<String, Object> getMap() {
        return map;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setRemoteConfig(FirebaseRemoteConfig remoteConfig, boolean isSuccess){
        this.isSuccess = isSuccess;
        this.map = new HashMap<>();
        map.put(CONFIG_BASE_URL_DESENV, remoteConfig.getString(CONFIG_BASE_URL_DESENV));
        map.put(CONFIG_BASE_URL_TESTE, remoteConfig.getString(CONFIG_BASE_URL_TESTE));
        map.put(CONFIG_BASE_URL_PROD, remoteConfig.getString(CONFIG_BASE_URL_PROD));

        map.put(RSA_KEY_VALUE_DESENV, remoteConfig.getString(RSA_KEY_VALUE_DESENV));
        map.put(RSA_KEY_VALUE_TESTE, remoteConfig.getString(RSA_KEY_VALUE_TESTE));
        map.put(RSA_KEY_VALUE_PROD, remoteConfig.getString(RSA_KEY_VALUE_PROD));

        Log.d("BaseUrlFlavor->" ,getBaseUrlFlavor());
        Log.d("RSAKeyValueFlavor->" ,getRSAKeyValueFlavor());
    }

    public String getBaseUrlFlavor(){
        String ambiente = "";

        switch (BuildConfig.FLAVOR){
            case "appDesenv":
                ambiente = CONFIG_BASE_URL_DESENV;
                break;

            case "appTeste":
                ambiente = CONFIG_BASE_URL_TESTE;
                break;

            case "appProd":
                ambiente = CONFIG_BASE_URL_PROD;
                break;
        }

        return (String) map.get(ambiente);
    }

    public String getRSAKeyValueFlavor(){
        String value = "";

        switch (BuildConfig.FLAVOR){
            case "appDesenv":
                value = RSA_KEY_VALUE_DESENV;
                break;

            case "appTeste":
                value = RSA_KEY_VALUE_TESTE;
                break;

            case "appProd":
                value = RSA_KEY_VALUE_PROD;
                break;
        }

        return (String) map.get(value);
    }
}