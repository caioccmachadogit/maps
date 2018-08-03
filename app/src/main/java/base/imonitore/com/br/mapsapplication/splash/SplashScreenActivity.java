package base.imonitore.com.br.mapsapplication.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseActivity;
import base.imonitore.com.br.mapsapplication.login.view.LoginActivity_;
import base.imonitore.com.br.mapsapplication.remoteconfig.CallBackRemoteConfigFB;
import base.imonitore.com.br.mapsapplication.remoteconfig.RemoteConfigFB;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inicializa urls ambiente do remoteConfig fireBase
        RemoteConfigFB.getInstance().create(new CallBackRemoteConfigFB() {
            @Override
            public void finish() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity_.class);
                startActivity(i);
            }
        });

    }
}
