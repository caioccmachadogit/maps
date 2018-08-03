package base.imonitore.com.br.mapsapplication.utils.hotspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.List;

/**
 * Created by csouza on 11/08/2016.
 */
public class WifiReceiver implements WifiReceiverListener {

    private String TAG = WifiReceiver.class.getSimpleName();

    private BroadcastReceiver mReceiver;

    protected WifiReceiverListener listener;

    protected Context context;

    public boolean hotSpot = false;

    public WifiReceiver(Context context) {
        this.context = context;
    }

    public void setListener(WifiReceiverListener listener){
        this.listener = listener;
    }

    public void registerReceiver(List<String> actions) {
        Log.d(TAG, "registerReceiver");
        IntentFilter mIntentFilter = null;

        mIntentFilter = new IntentFilter();
        for (String action : actions) {
            mIntentFilter.addAction(action);
        }

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (WifiApManager.WIFI_AP_STATE_CHANGED_ACTION.equals(action)) {
                    int state = intent.getIntExtra(WifiApManager.EXTRA_WIFI_AP_STATE, WifiApManager.WIFI_AP_STATE_FAILED);
                    Log.d(TAG, "Broadcast Receiver: " + state);
                    if (state == 13) {
                        onEnableHotSpot();
                    }
                    else {
                        hotSpot = false;
                        onDisableHotspot();
                    }
                }
            }
        };
        context.registerReceiver(mReceiver, mIntentFilter);
    }

    public void unregisterReceiver() {
        try{
            context.unregisterReceiver(mReceiver);
        }catch (Exception e){
            Log.e(TAG,"Erro ao remover registro de broadcast receiver FTP", e);
        }
    }

    @Override
    public void onEnableHotSpot() {
        Log.d(TAG,"HotSpot Ativo");
        if (listener != null){
            listener.onEnableHotSpot();
        }
    }

    @Override
    public void onDisableHotspot() {
        Log.d(TAG,"HotSpot Inativo");
        if (listener != null){
            listener.onDisableHotspot();
        }
    }
}

