package base.imonitore.com.br.mapsapplication.utils.hotspot;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.lang.reflect.Method;
//import com.google.common.base.Preconditions;

/**
 * Created by csouza on 02/05/2016.
 */
public final class WifiApManager {

    public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    public static final String NETWORK_IDS_CHANGED_ACTION = "android.net.wifi.NETWORK_IDS_CHANGED_ACTION";
    public static final String NETWORK_STATE_CHANGED_ACTION = "android.net.wifi.NETWORK_STATE_CHANGED_ACTION";
    public static final String RSSI_CHANGED_ACTION = "android.net.wifi.RSSI_CHANGED_ACTION";
    public static final String SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.SCAN_RESULTS_AVAILABLE_ACTION";
    public static final String SUPPLICANT_CONNECTION_CHANGE_ACTION = "android.net.wifi.SUPPLICANT_CONNECTION_CHANGE_ACTION";
    public static final String SUPPLICANT_STATE_CHANGED_ACTION = "android.net.wifi.SUPPLICANT_STATE_CHANGED_ACTION";

	/* NETWORK_IDS_CHANGED_ACTION
	 NETWORK_STATE_CHANGED_ACTION
	 RSSI_CHANGED_ACTION
	 SCAN_RESULTS_AVAILABLE_ACTION
	 SUPPLICANT_CONNECTION_CHANGE_ACTION
	 SUPPLICANT_STATE_CHANGED_ACTION
	 WIFI_AP_STATE_CHANGED_ACTION*/

    public static final String EXTRA_WIFI_AP_STATE = "wifi_state";

    public static final int WIFI_AP_STATE_UNKNOWN= -1;
    public static final int WIFI_AP_STATE_DISABLING = 0;
    public static final int WIFI_AP_STATE_DISABLED = 1;
    public static final int WIFI_AP_STATE_ENABLING = 2;
    public static final int WIFI_AP_STATE_ENABLED = 3;
    public static final int WIFI_AP_STATE_FAILED = 4;

    private final WifiManager mWifiManager;
    private final String TAG = "Wifi Access Manager";
    private Method wifiControlMethod;
    private Method wifiApConfigurationMethod;
    private Method wifiApState;

    /**
     * Exemplo:
     * WifiConfiguration netConfig = new WifiConfiguration();
     * netConfig.SSID = "EVE03";
     * netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
     * netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
     * netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
     * netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
     *
     * @param wifiConfiguration passar null para manter as configurações atuais
     * @param enable habilitar ou nao o hotspot/wifi ap
     * @throws Exception
     *
    public void setWifiApEnable(WifiConfiguration wifiConfiguration, boolean enable) throws Exception {
    WifiManager wifi_manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    Method wifiApConfigurationMethod = wifi_manager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
    wifiApConfigurationMethod.invoke(wifi_manager, wifiConfiguration, enable);
    }
     */

    public WifiApManager(Context context) throws SecurityException, NoSuchMethodException {
        // context = Preconditions.checkNotNull(context);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiControlMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
        wifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration");
        wifiApState = mWifiManager.getClass().getMethod("getWifiApState");
    }

    public boolean setWifiApState(WifiConfiguration config, boolean enabled) {
        // config = Preconditions.checkNotNull(config);
        try {
            if (enabled) {
                mWifiManager.setWifiEnabled(!enabled);
            }
            return (Boolean) wifiControlMethod.invoke(mWifiManager, config,
                    enabled);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
    }

    public WifiConfiguration getWifiApConfiguration() {

        try {
            return (WifiConfiguration) wifiApConfigurationMethod.invoke(mWifiManager);
        } catch (Exception e) {
            return null;
        }
    }

    public int getWifiApState() {

        try {
            int state = (Integer) wifiApState.invoke(mWifiManager);

            if (state >= 10){
                state = state - 10;
            }

            return state;

        } catch (Exception e) {
            Log.e(TAG, "", e);
            return WIFI_AP_STATE_FAILED;
        }
    }
}
