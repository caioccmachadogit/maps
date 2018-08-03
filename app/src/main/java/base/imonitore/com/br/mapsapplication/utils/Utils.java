package base.imonitore.com.br.mapsapplication.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import base.imonitore.com.br.mapsapplication.BaseApplication;
import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.Notificacoes;

/**
 * Created by ccouto on 16/03/2018.
 */

public class Utils {

    public static String getDeviceImei() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getMacAddress() {
        WifiManager wimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String macAddress = wimanager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            Log.e("getMacAddress", "macAddress nao reconhecido");
            macAddress = "";
        }
        return macAddress;
    }

    public static String getVersionName(){
        String version = "0";
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private static Context getApplicationContext(){
        return BaseApplication.getInstance().getApplicationContext();
    }

    public static String buildMsgErrorExibirApp(List<Notificacoes> notificacoes){
        StringBuilder builder = new StringBuilder();
        for (Notificacoes n:notificacoes) {
            if(n.isExibirApp()){
                builder.append(n.getMensagem());
                builder.append(" ");
            }
        }
        if(builder.length() > 0)
            return builder.toString();

        return getApplicationContext().getResources().getString(R.string.mensagem_erro_desconhecido);
    }
}
