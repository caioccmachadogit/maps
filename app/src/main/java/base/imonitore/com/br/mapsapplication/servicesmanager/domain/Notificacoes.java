package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

import java.io.Serializable;

/**
 * Created by ccouto on 13/03/2018.
 */

public class Notificacoes implements Serializable {

    private String propriedade;

    private String mensagem;

    private boolean exibirApp;

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isExibirApp() {
        return exibirApp;
    }

    public void setExibirApp(boolean exibirApp) {
        this.exibirApp = exibirApp;
    }
}
