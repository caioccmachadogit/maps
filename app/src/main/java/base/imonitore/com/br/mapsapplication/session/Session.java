package base.imonitore.com.br.mapsapplication.session;

import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginOut;
import base.imonitore.com.br.mapsapplication.utils.DateUtils;

/**
 * Created by ccouto on 13/12/2017.
 */

public class Session {

    private static Session instance = null;

    private LoginOut user;

    private String dataLogin;

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setDataLogin() {
        this.dataLogin = DateUtils.dateNowStr();
    }

    public String getDataLogin() {
        return dataLogin;
    }

    public LoginOut getUser() {
        return user;
    }

    public void setUser(LoginOut user) {
        this.user = user;
    }
}
