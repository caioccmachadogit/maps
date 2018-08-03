package base.imonitore.com.br.mapsapplication.login.presenter;

import android.app.Activity;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BasePresenter;
import base.imonitore.com.br.mapsapplication.login.contract.ILogin;
import base.imonitore.com.br.mapsapplication.login.domain.DadosUsuarioEnum;
import base.imonitore.com.br.mapsapplication.login.model.LoginModel;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;

@EBean
public class LoginPresenter extends BasePresenter implements ILogin.LoginPresenter{

    private ILogin.LoginView view;

    @Bean
    protected LoginModel model;

    @Override
    public void init(Object view) {
        this.view = (ILogin.LoginView) view;
        this.mActivity = (Activity) view;
        this.TAG = getClass().getSimpleName();
        this.model.init(this,mActivity);
    }

    @Override
    public void btEntrarLogin(LoginIn loginIn) {
        if(validarCampos(loginIn)){
            model.efetuarLogin(loginIn);
        }
    }

    private boolean validarCampos(LoginIn loginIn) {
        view.clearTextInput();

        if(loginIn.getCodigoEmpresa().isEmpty()){
            view.setErrorTextInput(DadosUsuarioEnum.CODIGO, mActivity.getResources().getString(R.string.campo_obrigatorio));
            return false;
        }
        if(loginIn.getLogin().isEmpty()){
            view.setErrorTextInput(DadosUsuarioEnum.USUARIO, mActivity.getResources().getString(R.string.campo_obrigatorio));
            return false;
        }
        if(loginIn.getSenha().isEmpty()){
            view.setErrorTextInput(DadosUsuarioEnum.SENHA, mActivity.getResources().getString(R.string.campo_obrigatorio));
            return false;
        }

        return true;
    }

    @Override
    public void dialogMensagem(String msg) {
        showDialogMensagem(msg);
    }

    @Override
    public void efetuarLoginSucesso(Class activity) {
        dismissLoading();
        view.navigateActivity(activity);
    }
}
