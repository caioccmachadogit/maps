package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

import java.io.Serializable;

/**
 * Created by aluiz on 24/10/2017.
 */

public class LoginIn implements Serializable {

    private String codigoEmpresa;

    private String login;

    private String senha;

    private String versao;

    private String imei;

    private String macAddress;

    public LoginIn(String codigoEmpresa, String login, String senha) {
        this.codigoEmpresa = codigoEmpresa;
        this.login = login;
        this.senha = senha;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
