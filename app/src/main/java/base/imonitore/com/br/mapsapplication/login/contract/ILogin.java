package base.imonitore.com.br.mapsapplication.login.contract;

import base.imonitore.com.br.mapsapplication.login.domain.DadosUsuarioEnum;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;

public interface ILogin {

    interface LoginPresenter {

        void btEntrarLogin(LoginIn loginIn);

        void dialogMensagem(String msg);

        void efetuarLoginSucesso(Class activity);
    }

    interface LoginView{
        void setErrorTextInput(DadosUsuarioEnum errorTextInput, String error);

        void clearTextInput();

        void navigateActivity(Class activity);
    }

    interface LoginModel{
        void efetuarLogin(LoginIn loginIn);
    }


}
