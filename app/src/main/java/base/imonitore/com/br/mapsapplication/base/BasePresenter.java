package base.imonitore.com.br.mapsapplication.base;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.component.DialogMensagem;
import base.imonitore.com.br.mapsapplication.component.DialogMensagem_;

/**
 * Created by ccouto on 03/01/2018.
 */
public abstract class BasePresenter {

    public String TAG;

    public abstract void init(Object view);

    public Activity mActivity;

    private Dialog mLoadingDialog;

    protected DialogMensagem mDialogMensagem = DialogMensagem_.builder().build();

    public void showLoading(Activity act, String lblLoading) {
        //create dialog
        mLoadingDialog = new Dialog(act, R.style.transparentBgTheme);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setOwnerActivity(act);
        mLoadingDialog.setContentView(R.layout.dialog_loading);
        mLoadingDialog.setCancelable(false);
        if(lblLoading != null)
            setTextTvLoading(lblLoading);

        try {
            mLoadingDialog.show();
        } catch (Exception e) {
            Log.e("dialogLoading", "Erro showLoading", e);
        }
    }

    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public void setTextTvLoading(String lblLoading){
        if(mLoadingDialog != null){
            TextView tvLoading = mLoadingDialog.findViewById(R.id.tvLoading);
            tvLoading.setText(lblLoading);
        }
    }

    protected void showDialogMensagem(String msgDialog) {
        dismissLoading();
        mDialogMensagem.setCancelable(false);
        mDialogMensagem.setTvMensagem(msgDialog);
        mDialogMensagem.show(mActivity.getFragmentManager(), null);
    }

    protected void log(String msg) {
        if(TAG != null)
            Log.d(TAG, msg);
        else
            Log.d(getClass().getSimpleName(), msg);
    }
}
