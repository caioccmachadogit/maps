package base.imonitore.com.br.mapsapplication.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import base.imonitore.com.br.mapsapplication.servicesmanager.domain.LoginOut;
import base.imonitore.com.br.mapsapplication.servicesmanager.domain.APIError;
import base.imonitore.com.br.mapsapplication.servicesmanager.services.BaseService;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by ccouto on 20/03/2018.
 */

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, LoginOut> converter =
                BaseService.retrofit.responseBodyConverter(LoginOut.class, new Annotation[0]);

        APIError apiError = new APIError();
        try {
            LoginOut error = converter.convert(response.errorBody());
            if(error.getNotificacoes() != null)
                apiError.buildMsgErrorExibirApp(error.getNotificacoes());
            else
                apiError.msgPadrao();
        }
        catch (IOException e) {
            apiError.msgPadrao();
            return apiError;
        }
        return apiError;
    }
}
