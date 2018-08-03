package base.imonitore.com.br.mapsapplication.component;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import base.imonitore.com.br.mapsapplication.R;

/**
 * Created by aluiz on 25/10/2017.
 * ****
 */

@EFragment(R.layout.dialog_mensagem_default)
public class DialogMensagem extends DialogFragment {

    @ViewById
    protected TextView tvMensagem;
    @ViewById
    protected Button btOk;
    private Dialog dialog;
    private String mMensagem;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_POWER) {
                    Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                    getActivity().sendBroadcast(closeDialog);
                }
                return false;
            }
        });
        return dialog;
    }

    @AfterViews
    protected void setTextTvMensagem(){
        tvMensagem.setText(mMensagem);
    }

    @Click
    protected void btOk() {
        dialog.dismiss();

    }

    public void setTvMensagem(String mensagem) {
        this.mMensagem = mensagem;
    }
}
