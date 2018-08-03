package base.imonitore.com.br.mapsapplication.servicesmanager.services;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import base.imonitore.com.br.mapsapplication.BaseApplication;
import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.remoteconfig.MapRemoteConfig;
import base.imonitore.com.br.mapsapplication.servicesmanager.CallBackGeneric;
import base.imonitore.com.br.mapsapplication.servicesmanager.MockInterceptor;
import base.imonitore.com.br.mapsapplication.servicesmanager.ServiceEnum;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.APIError;
import base.imonitore.com.br.mapsapplication.session.Session;
import base.imonitore.com.br.mapsapplication.utils.DateUtils;
import base.imonitore.com.br.mapsapplication.utils.ErrorUtils;
import base.imonitore.com.br.mapsapplication.utils.JSONUtils;
import base.imonitore.com.br.mapsapplication.utils.Utils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ccouto on 27/10/2017.
 */

public class BaseService {

    private Gson gson = new Gson();

    public static Retrofit retrofit;

    private String bearer;

    protected Object retrofitCreate(Class aClass, ServiceEnum serviceEnum) {

        if (BaseApplication.getInstance().isMock && serviceEnum == serviceEnum.MOCK) {
            return retrofitCreateMockInstance(aClass);
        } else {
            return retrofitCreateInstance(aClass, serviceEnum);
        }
    }

    protected Object retrofitCreateToken(Class aClass, ServiceEnum serviceEnum) {
        setBearer();
        return retrofitCreateTokenInstance(aClass, serviceEnum);
    }

    protected void setBearer(){
        String token = Session.getInstance().getUser() != null ? Session.getInstance().getUser().getToken() : "";
        Log.d("retrofitCreateToken", "token->"+token);
        bearer = createBearer(token).toString();
    }

    public void refreshBearer(){
        setBearer();
    }

    private Object retrofitCreateInstance(Class aClass, ServiceEnum serviceEnum) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(serviceEnum, aClass))
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(JSONUtils.newGson()))
                .build();

        return retrofit.create(aClass);
    }

    private Object retrofitCreateMockInstance(Class aClass) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);

        clientBuilder.addInterceptor(new MockInterceptor(BaseApplication.getInstance().getApplicationContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(ServiceEnum.MOCK, aClass))
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(aClass);
    }

    private Object retrofitCreateTokenInstance(Class aClass, ServiceEnum serviceEnum) {
        OkHttpClient.Builder clientToken = new OkHttpClient.Builder();
        clientToken.readTimeout(20, TimeUnit.SECONDS);
        clientToken.connectTimeout(10, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor interceptorToken = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + bearer)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        clientToken.addInterceptor(interceptor);
        clientToken.addInterceptor(interceptorToken);

        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(serviceEnum, aClass))
                .client(clientToken.build())
                .addConverterFactory(GsonConverterFactory.create(JSONUtils.newGson()))
                .build();

        return retrofit.create(aClass);
    }

    private Bearer createBearer(String token){
        return new Bearer(
                token,
                Utils.getDeviceImei(),
                DateUtils.getNowyyyyMMddHHmmss());
    }

    private String getBaseUrl(ServiceEnum serviceEnum, Class aClass) {
        String baseUrl = "";
        switch (serviceEnum) {
            case APP:
                baseUrl = MapRemoteConfig.getInstance().getBaseUrlFlavor();
                break;
            case LOCAL:
                baseUrl = "http://" + BaseApplication.getInstance().getString(R.string.server_host) + ":" + BaseApplication.getInstance().getString(R.string.server_port) + "/";
                break;
            case MOCK:
                baseUrl = BaseApplication.getInstance().getString(R.string.server_mock);
                break;
        }
        Log.d("retrofitCreate", aClass.getSimpleName() + "-baseUrl->" + baseUrl);
        return baseUrl;
    }

    protected void onResponseValidation(CallBackGeneric callBackGeneric, Response response, String tag) {
        APIError error = new APIError();
        switch (response.code()) {
            case HttpURLConnection.HTTP_OK:
            case HttpURLConnection.HTTP_CREATED:
                callBackGeneric.callBackSuccess(response.body());
                logSuccess(tag, response);
                break;

            case HttpURLConnection.HTTP_UNAUTHORIZED:
                error = ErrorUtils.parseError(response);
                callBackGeneric.callBackError(error);
                logApiError(tag, "codeError:" + response.code(), error);
                break;

            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            case HttpURLConnection.HTTP_NO_CONTENT:
            default:
                error.msgPadrao();
                callBackGeneric.callBackError(error);
                logApiError(tag, "codeError:" + response.code(), error);
                break;
        }
    }

    protected void onFailureDefault(CallBackGeneric callBackGeneric, Throwable t, String tag) {
        APIError apiError = new APIError();
        apiError.msgPadrao();
        callBackGeneric.callBackError(apiError);
        logFailure(tag, t != null ? t.getLocalizedMessage() : "Error desconhecido");
    }

    private void logSuccess(String tag, Response response) {
        String strResp = gson.toJson(response.body());
        Log.d("CallSuccess", tag + " --> " + strResp);
    }

    private void logFailure(String tag, String error) {
        Log.e("CallFailure", error != null ? tag + " --> " + error : tag + "--> Error desconhecido");
    }

    private void logApiError(String tag, String code, APIError error) {
        Log.e("CallApiError", error != null ? tag + " - " + code + "--> " + error.message() : tag + "--> Error desconhecido");
    }
}
