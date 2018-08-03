package base.imonitore.com.br.mapsapplication.utils.hotspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.utils.CountUpTimerUtils;

public class WifiBase {

    private final String TAG = getClass().getSimpleName();
    private final boolean mEnableProgress;

    private WifiBaseListener mListener;

    private CountUpTimerUtils timer;
    private final long mDuration = 5000;//5 segundos
    private Handler handlerTime;
    private Runnable runnableTime;

    public interface WifiBaseListener {
        String getWifiSSID();
        String getWifiPass();
        int getSecondsTimeout();
        Context getmContext();
        Activity getmActivity();
        void connectFinish();
        void connectError(String msg);
    }

    public WifiBase(WifiBaseListener wifiBaseListener, boolean enableProgress) {
        this.mListener = wifiBaseListener;
        this.mEnableProgress = enableProgress;
        handle();
    }

    private String getWifiSSID() {
        return mListener.getWifiSSID();
    }

    private String getWifiPass(){
        return mListener.getWifiPass();
    }

    private int getSecondsTimeout() {
        return mListener.getSecondsTimeout();
    }

    private Context getContext() {return mListener.getmContext();}

    private Activity getActivity() {return mListener.getmActivity();}

    private void connectFinish(){
        mListener.connectFinish();
    }

    public static final String PSK = "PSK";
    public static final String WEP = "WEP";
    public static final String OPEN = "Open";

    private static final int REQUEST_ENABLE_WIFI = 10;

    private final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture taskHandler;

    private ProgressDialog progressDialog;
    private ScanReceiver scanReceiver;
    private ConnectionReceiver connectionReceiver;

    protected void handle() {
        handleWIFI();
    }

    public void finale(){
        try {
            handlerTime = null;
                if(connectionReceiver != null)
                    getContext().unregisterReceiver(connectionReceiver);
                if(scanReceiver != null)
                    getContext().unregisterReceiver(scanReceiver);
                if(taskHandler != null)
                    taskHandler.cancel(true);
        }
        catch (IllegalArgumentException e){
            Log.e(TAG,"finale->",e);
        }
    }

    /**
     * Start connecting to specific wifi network
     */
    protected void handleWIFI() {
        WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            connectToSpecificNetwork();
        } else {
            connectWifiEnable();
            connectToSpecificNetwork();
//            showWifiDisabledDialog();
        }
    }

    private void connectWifiEnable() {
        final WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
        Log.d(TAG,"connectWifiEnable");
    }

    /**
     * Ask user to go to settings and enable wifi
     */
    private void showWifiDisabledDialog() {
        new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setMessage(getContext().getString(R.string.wifi_disabled))
                .setPositiveButton(getContext().getString(R.string.enable_wifi), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // open settings screen
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        getActivity().startActivityForResult(intent, REQUEST_ENABLE_WIFI);
                    }
                })
                .setNegativeButton(getContext().getString(R.string.exit_app), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    /**
     * Get the security type of the wireless network
     *
     * @param scanResult the wifi scan result
     * @return one of WEP, PSK of OPEN
     */
    private String getScanResultSecurity(ScanResult scanResult) {
        final String cap = scanResult.capabilities;
        final String[] securityModes = {WEP, PSK};
        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (cap.contains(securityModes[i])) {
                return securityModes[i];
            }
        }

        return OPEN;
    }

    // User has returned from settings screen. Check if wifi is enabled
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_WIFI && resultCode == 0) {
            WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi.isWifiEnabled() || wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                connectToSpecificNetwork();
            } else {
                getActivity().finish();
            }
        } else {
            getActivity().finish();
        }
    }

    /**
     * Start to connect to a specific wifi network
     */
    private void connectToSpecificNetwork() {
        Log.d(TAG,"connectToSpecificNetwork");
        final WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (networkInfo.isConnected() && wifiInfo.getSSID().replace("\"", "").equals(getWifiSSID())) {
            connectFinish();//wifi ligado e conectado na rede
            return;
        }
        else {
            wifi.disconnect();
        }

        if(mEnableProgress)
            progressDialog = ProgressDialog.show(getContext(), getContext().getString(R.string.connecting), String.format(getContext().getString(R.string.connecting_to_wifi), getWifiSSID()));

        taskHandler = worker.schedule(new TimeoutTask(), getSecondsTimeout(), TimeUnit.SECONDS);
        scanReceiver = new ScanReceiver();
        getContext().registerReceiver(scanReceiver
                , new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
    }

    /**
     * Broadcast receiver for connection related events
     */
    private class ConnectionReceiver extends BroadcastReceiver {

        WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        @Override
        public void onReceive(final Context context, Intent intent) {
            if(handlerTime == null){
                handlerTime = new Handler(Looper.getMainLooper());
                runnableTime = new Runnable(){
                    @Override
                    public void run(){
                        timer = new CountUpTimerUtils(mDuration) {
                            public void onTick(int second) {
                                mListener.connectError(getContext().getString(R.string.connecting));
                            }
                            public void onFinish(boolean finish) {
                                Log.d(TAG, "ConnectionReceiver - onFinish");
                                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                                WifiInfo wifiInfo = wifi.getConnectionInfo();
                                if (networkInfo.isConnected()) {
                                    if (wifiInfo.getSSID().replace("\"", "").equals(getWifiSSID())) {
//                                    context.unregisterReceiver(connectionReceiver);
                                        if (taskHandler != null) {
                                            taskHandler.cancel(true);
                                        }
                                        if (progressDialog != null) {
                                            progressDialog.dismiss();
                                        }
                                        connectFinish();
                                    }
                                }
                            }
                        };
                        timer.start();
                    }
                };
                handlerTime.post(runnableTime);
            }
        }
    }

    /**
     * Broadcast receiver for wifi scanning related events
     */
    private class ScanReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifi = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            List<ScanResult> scanResultList = wifi.getScanResults();
            boolean found = false;
            String security = null;
            for (ScanResult scanResult : scanResultList) {
                if (scanResult.SSID.equals(getWifiSSID())) {
                    security = getScanResultSecurity(scanResult);
                    found = true;
                    break; // found don't need continue
                }
            }
            if (!found) {
                // if no wifi network with the specified ssid is not found exit
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
//                isFinale = true;
                mListener.connectError(context.getString(R.string.wifi_not_found)+" - "+getWifiSSID());
//                new AlertDialog.Builder(context)
//                        .setCancelable(false)
//                        .setMessage(String.format(context.getString(R.string.wifi_not_found), getWifiSSID()))
//                        .setPositiveButton(context.getString(R.string.exit_app), new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                getActivity().unregisterReceiver(ScanReceiver.this);
//                                getActivity().finish();
//                            }
//                        })
//                        .show();
            } else {
                // configure based on security
                final WifiConfiguration conf = new WifiConfiguration();
                conf.SSID = "\"" + getWifiSSID() + "\"";
                switch (security) {
                    case WEP:
                        conf.wepKeys[0] = "\"" + getWifiPass() + "\"";
                        conf.wepTxKeyIndex = 0;
                        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                        break;
                    case PSK:
                        conf.preSharedKey = "\"" + getWifiPass() + "\"";
                        break;
                    case OPEN:
                        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        break;
                }
                try {context.unregisterReceiver(connectionReceiver);} catch (Exception e) {} // do nothing

                connectionReceiver = new ConnectionReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
                intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
                context.registerReceiver(connectionReceiver, intentFilter);
                int netId = wifi.addNetwork(conf);
                wifi.disconnect();
                wifi.enableNetwork(netId, true);
                wifi.reconnect();
                context.unregisterReceiver(this);
            }
        }
    }

    /**
     * Timeout task. Called when timeout is reached
     */
    private class TimeoutTask implements Runnable {
        @Override
        public void run() {
            WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (networkInfo.isConnected() && wifiInfo.getSSID().replace("\"", "").equals(getWifiSSID())) {
                try {
                    getContext().unregisterReceiver(connectionReceiver);
                } catch (Exception ex) {
                    // ignore if receiver already unregistered
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                });
            } else {
                try {
                    getContext().unregisterReceiver(connectionReceiver);
                } catch (Exception ex) {
                    // ignore if receiver already unregistered
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        mListener.connectError(getContext().getString(R.string.wifi_not_connected));

//                        new AlertDialog.Builder(getContext())
//                                .setCancelable(false)
//                                .setMessage(String.format(getContext().getString(R.string.wifi_not_connected), getWifiSSID()))
//                                .setPositiveButton(getContext().getString(R.string.exit_app), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        getActivity().finish();
//                                    }
//                                })
//                                .show();
                    }
                });
            }
        }
    }
}
