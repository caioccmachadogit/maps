package base.imonitore.com.br.mapsapplication.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by ccouto on 24/10/2017.
 */

public class BaseFragment extends Fragment {

    public Context getContext() {
        return getActivity();
    }
}
