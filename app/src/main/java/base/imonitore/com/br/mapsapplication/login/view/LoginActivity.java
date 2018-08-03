package base.imonitore.com.br.mapsapplication.login.view;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.Window;
import android.widget.Button;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseActivity;
import base.imonitore.com.br.mapsapplication.login.contract.ILogin;
import base.imonitore.com.br.mapsapplication.login.domain.DadosUsuarioEnum;
import base.imonitore.com.br.mapsapplication.login.presenter.LoginPresenter;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginIn;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.login_activity)
public class LoginActivity extends BaseActivity implements ILogin.LoginView{

    @ViewById
    protected Button btEntrar;

    @ViewById
    protected TextInputLayout tiCodigo;

    @ViewById
    protected TextInputLayout tiUsuario;

    @ViewById
    protected TextInputLayout tiSenha;

    @Bean
    protected LoginPresenter presenter;

    @AfterInject
    protected void init() {
        this.mActivity = this;
        this.TAG = getClass().getSimpleName();
        presenter.init(mActivity);
    }

    @Override
    public void setErrorTextInput(DadosUsuarioEnum errorTextInput, String error) {
        switch (errorTextInput){
            case CODIGO:
                tiCodigo.setError(error);
                break;
            case USUARIO:
                tiUsuario.setError(error);
                break;
            case SENHA:
                tiSenha.setError(error);
                break;
        }
    }

    @Override
    public void clearTextInput() {
        tiCodigo.setErrorEnabled(false);
        tiUsuario.setErrorEnabled(false);
        tiSenha.setErrorEnabled(false);
    }

    @Override
    public void navigateActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Click
    protected void btEntrar() {
        presenter.btEntrarLogin(new LoginIn(
                tiCodigo.getEditText().getText().toString(),
                tiUsuario.getEditText().getText().toString(),
                tiSenha.getEditText().getText().toString()));
    }
}
