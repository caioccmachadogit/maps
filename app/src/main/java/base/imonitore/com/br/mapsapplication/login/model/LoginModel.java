package base.imonitore.com.br.mapsapplication.login.model;

import android.app.Activity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseModel;
import base.imonitore.com.br.mapsapplication.login.contract.ILogin;
import base.imonitore.com.br.mapsapplication.main.view.MainActivity_;
import base.imonitore.com.br.mapsapplication.servicesmanager.CallBackGeneric;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginOut;
import base.imonitore.com.br.mapsapplication.servicesmanager.services.AutenticarService;

@EBean
public class LoginModel extends BaseModel implements ILogin.LoginModel{

    private ILogin.LoginPresenter presenter;

    @Bean
    protected AutenticarService autenticarService;

    @Override
    public void init(Object presenter, Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.presenter = (ILogin.LoginPresenter) presenter;
        this.mActivity = activity;
    }

    @Override
    public void efetuarLogin(LoginIn loginIn) {
     autenticarService.efetuarLogin(new CallBackGeneric() {
         @Override
         public void callBackSuccess(Object response) {
             LoginOut loginOut = (LoginOut) response;
             mSession.setUser(loginOut);
             presenter.efetuarLoginSucesso(MainActivity_.class);
         }

         @Override
         public void callBackError(Object response) {
            presenter.dialogMensagem(mActivity.getResources().getString(R.string.usuario_n_encontrado));
         }
     },loginIn, true);
    }
}
