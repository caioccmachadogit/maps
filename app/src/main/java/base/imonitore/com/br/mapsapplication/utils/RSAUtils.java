package base.imonitore.com.br.mapsapplication.utils;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import base.imonitore.com.br.mapsapplication.remoteconfig.MapRemoteConfig;

public class RSAUtils {
    private static String TAG = "RSAUtils";
//    private static String modulus = "xVnuHhWx7LZ9Zg6QRuvvlm7gqB4ppgJFR9U+CO7zsykyt6MtIpDCN6IEptAkkxJ7bqCFhIblXtMeg0II59yEMy5SxeAT3BLQPlRLqp6SU8fgSGj0HJmnUR6Tkj9gb1lz/2zL0v6Ou7WPInxnpoOrdYVUQrUfsRyREounu+j3M38j38IaCr0RwAmeeevSYpiJlBzmFP1a4nx/lZgvZdStIzI3AYXVOpqEDAOPAF/9r2BJy2r3ElDMGHQqUAm5ov0bhESqUYMMlrwpsQtRewZ5SQzRJcEWLn916PDKCPCuf/WOYZQrmWbGEr98Wo/ssBTqKG1arCcCappLh9jy9p2rHQ==";
//    private static String exponent = "AQAB";
    private static final String KEY_ALGORITHM = "RSA";

    public static String encryptByPublicKey(String data){
        String encodeStr;
        try {
            byte[] dataByte = data.getBytes("UTF-8");

            PublicKey publicKey = getPublicKey(getModulus(),getExponent());
            String publicKeyStr = encryptBASE64(publicKey.getEncoded());

            byte[] encodeData = encryptByPublicKey(dataByte, publicKeyStr);
            encodeStr = encryptBASE64(encodeData);
            Log.d(TAG,"encryptByPublicKey->"+encodeStr.trim());
            return encodeStr.trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR->",e);
            return null;
        }
    }

    private static PublicKey getPublicKey(String MODULUS, String EXPONENT) throws Exception {
        byte[] modulusBytes = Base64.decode(MODULUS,0);
        byte[] exponentBytes = Base64.decode(EXPONENT,0);

        BigInteger modulus = new BigInteger(1, (modulusBytes) );
        BigInteger exponent = new BigInteger(1, (exponentBytes));

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return kf.generatePublic(spec);
    }

    private static String encryptBASE64(byte[] key){
        return Base64.encodeToString(key, Base64.NO_WRAP);
    }

    private static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    private static byte[] decryptBASE64(String key){
        return Base64.decode(key, Base64.DEFAULT);
    }

    private static String getModulus() {
        return new Gson().fromJson(MapRemoteConfig.getInstance().getRSAKeyValueFlavor(), RSAKeyValue.class).getModulus();
    }

    private static String getExponent() {
        return new Gson().fromJson(MapRemoteConfig.getInstance().getRSAKeyValueFlavor(), RSAKeyValue.class).getExponent();
    }

    public class RSAKeyValue{

        private String modulus;

        private String exponent;

        public String getModulus() {
            return modulus;
        }

        public void setModulus(String modulus) {
            this.modulus = modulus;
        }

        public String getExponent() {
            return exponent;
        }

        public void setExponent(String exponent) {
            this.exponent = exponent;
        }
    }
}
