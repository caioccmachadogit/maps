package base.imonitore.com.br.mapsapplication.remoteconfig;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import base.imonitore.com.br.mapsapplication.BuildConfig;
import base.imonitore.com.br.mapsapplication.R;


/**
 * Created by ccouto on 02/01/2018.
 */

public class RemoteConfigFB {

    private static RemoteConfigFB instance;

    private FirebaseRemoteConfig mRemoteConfig;
    private long cacheExpiration = 0; //valor em segundos - default
    private CallBackRemoteConfigFB mCallBackRemoteConfigFB;

    public static RemoteConfigFB getInstance(){
        if(instance == null)
            instance = new RemoteConfigFB();

        return instance;
    }

    public void create(CallBackRemoteConfigFB callBackRemoteConfigFB){
        this.mCallBackRemoteConfigFB = callBackRemoteConfigFB;

        mRemoteConfig = FirebaseRemoteConfig.getInstance();

        mRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mRemoteConfig.setConfigSettings(remoteConfigSettings);
        fetchRemoteConfigValues();
    }

    private void fetchRemoteConfigValues() {
        //expire the cache immediately for development mode.
        if (mRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            // task successful. Activate the fetched data
                            mRemoteConfig.activateFetched();
                        }
                        MapRemoteConfig.getInstance().setRemoteConfig(mRemoteConfig, task.isSuccessful());

                        Log.d( "RemoteConfig ","updateData->"+task.isSuccessful());

                        if(mCallBackRemoteConfigFB != null)
                            mCallBackRemoteConfigFB.finish();
                    }
                });
    }
}


