package base.imonitore.com.br.mapsapplication.servicesmanager;

/**
 * Created by ccouto on 21/05/2018.
 */

public interface CallBackGeneric<T> {

    void callBackSuccess(T response);

    void callBackError(T response);
}
