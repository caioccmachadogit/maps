package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

/**
 * Created by aluiz on 19/03/2018.
 */

public class LogoutIn {

    private String token;

    private String imei;

    public LogoutIn(String token, String imei) {
        this.token = token;
        this.imei = imei;
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
}



