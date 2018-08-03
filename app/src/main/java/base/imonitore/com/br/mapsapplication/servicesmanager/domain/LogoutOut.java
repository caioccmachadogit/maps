package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

import java.util.List;

/**
 * Created by aluiz on 21/03/2018.
 */

public class LogoutOut {

    private Long id;

    private String token;

    private String exceptionError;

    private boolean sucesso;

    private List<Notificacoes> notificacoes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExceptionError() {
        return exceptionError;
    }

    public void setExceptionError(String exceptionError) {
        this.exceptionError = exceptionError;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public List<Notificacoes> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacoes> notificacoes) {
        this.notificacoes = notificacoes;
    }
}



