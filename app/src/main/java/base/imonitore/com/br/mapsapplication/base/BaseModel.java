package base.imonitore.com.br.mapsapplication.base;

import android.app.Activity;

import base.imonitore.com.br.mapsapplication.session.Session;

/**
 * Created by ccouto on 03/01/2018.
 */

public abstract class BaseModel {

    public abstract void init(Object presenter, Activity activity);

    protected Activity mActivity;

    protected Session mSession = Session.getInstance();

    public String TAG;
}
