package base.imonitore.com.br.mapsapplication.servicesmanager.domain;

import java.util.List;

import base.imonitore.com.br.mapsapplication.BaseApplication;
import base.imonitore.com.br.mapsapplication.R;

/**
 * Created by ccouto on 20/03/2018.
 */

public class APIError {

    private int statusCode;

    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    public void buildMsgErrorExibirApp(List<Notificacoes> notificacoes){
        StringBuilder builder = new StringBuilder();
        for (Notificacoes n:notificacoes) {
            if(n.isExibirApp()){
                builder.append(n.getMensagem());
                builder.append(" ");
            }
        }
        if(builder.length() > 0)
            message = builder.toString();
        else
            msgPadrao();
    }

    public void msgPadrao(){
        message = BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.mensagem_erro_desconhecido);
    }
}
