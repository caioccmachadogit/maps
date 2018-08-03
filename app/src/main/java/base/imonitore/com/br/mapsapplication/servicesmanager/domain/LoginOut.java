package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

import android.arch.persistence.room.Ignore;
import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by ccouto on 03/01/2018.
 */

public class LoginOut {

    private String token;

    private Date expiracao;

    private String urlBrasaoEstado;

    private String urlLogoDetran;

    private List<PerfilUserEnum> roles;

    private String codigoEmpresa;

    private List<Notificacoes> notificacoes;

    public boolean validaToken(){
        try {
            if(token == null || token.isEmpty()){
                Log.d("validaToken","->token null<-");
                return false;
            }
            else if(expiracao.after(new Date())){
                Log.d("validaToken","token OK-> "+expiracao);
                return true;
            }
            else {
                Log.d("validaToken","->token NOK<-");
                return false;
            }
        }
        catch (Exception ex){
            Log.e("validaToken","->token NOK<-",ex);
            return false;
        }
    }

    @Ignore
    public LoginOut(List<PerfilUserEnum> roles, String codigoEmpresa, String token, Date expiracao) {
        this.roles = roles;
        this.codigoEmpresa = codigoEmpresa;
        this.token = token;
        this.expiracao = expiracao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlBrasaoEstado() {
        return urlBrasaoEstado;
    }

    public void setUrlBrasaoEstado(String urlBrasaoEstado) {
        this.urlBrasaoEstado = urlBrasaoEstado;
    }

    public String getUrlLogoDetran() {
        return urlLogoDetran;
    }

    public void setUrlLogoDetran(String urlLogoDetran) {
        this.urlLogoDetran = urlLogoDetran;
    }

    public List<PerfilUserEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<PerfilUserEnum> roles) {
        this.roles = roles;
    }

    public Date getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(Date expiracao) {
        this.expiracao = expiracao;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public List<Notificacoes> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacoes> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public PerfilUserEnum getPerfilUserEnum(){
        if(roles != null)
            return roles.get(0);
        return null;
    }
}
