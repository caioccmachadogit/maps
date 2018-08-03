package base.imonitore.com.br.mapsapplication.utils.hotspot;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccouto on 12/01/2018.
 */

public class WifiHotSpotUtil {

    private static WifiHotSpotUtil instance;
    private String ssid;
    private String pass;

    public static WifiHotSpotUtil getInstance() {
        if(instance == null)
            instance = new WifiHotSpotUtil();

        return instance;
    }

    private WifiReceiver wifiReceiver;

    private Context mContext;

    private final String TAG = WifiHotSpotUtil.class.getSimpleName();

    private WifiApManager wifiApManager;

    private WifiManager wifiManager;

    private int waitTentativa = 1;

    private WifiReceiverListener wifiReceiverListener;

    public void initAndSet(Context context, String ssid, String pass, WifiReceiverListener wifiReceiverListener) {
        this.wifiReceiverListener = wifiReceiverListener;
        this.ssid = ssid;
        this.pass = pass;
        this.mContext = context;
        wifiReceiver = new WifiReceiver(context);
        registerReceiver();
        createWiFiAp();
        waitTentativa = 1;
    }

    private void registerReceiver() {
        Log.d(TAG, "registerReceiver");
        List<String> actions = new ArrayList<>();
        actions.add(WifiApManager.WIFI_AP_STATE_CHANGED_ACTION);
        actions.add(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiReceiver.setListener(wifiReceiverListener);
        wifiReceiver.registerReceiver(actions);
    }

    protected void createWiFiAp() {
        Log.i(TAG, "Habilitando HotSpot...");

        try {
            if (wifiManager == null) {
                wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            }
            if (wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(false);
                Thread.sleep(5000);
            }
        }
        catch (Exception e){
            Log.e(TAG, "Falha ao desativar wifi", e);
        }


        try {
            wifiApManager = new WifiApManager(mContext);
            int state = wifiApManager.getWifiApState();

            if (state == 2 || state == 3) {
                wifiApManager.setWifiApState(null, false);
            }

            Method enableWifi = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            WifiConfiguration myConfig =  new WifiConfiguration();
            myConfig.SSID = ssid;
            myConfig.preSharedKey  = pass ;
            myConfig.status = WifiConfiguration.Status.ENABLED;
            myConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            myConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            myConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            myConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            myConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            myConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            myConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            boolean result = (Boolean) enableWifi.invoke(wifiManager, myConfig, true);

        }
        catch (SecurityException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e ) {
            Log.e("Hotspot", "Erro ao abir wifi AP Manager", e);
        }
    }

    public void stopHotSpotReativaWiFi() {
        try {
            wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            wifiReceiver.unregisterReceiver();

            if (wifiApManager == null) {
                try {
                    wifiApManager = new WifiApManager(mContext);
                }
                catch (Exception e) {
                    Log.e(TAG, "Erro ao desativar HotSpot", e);
                }
            }

            if (wifiApManager != null) {
                wifiApManager.setWifiApState(null, false);
            }

            wifiManager.setWifiEnabled(true);
            wifiManager.reconnect();

            Log.d(TAG, "HotSpot desativado");

        }
        catch (Exception ex){
            Log.e(TAG, "HotSpot desativado", ex);
        }
    }
}
