package base.imonitore.com.br.mapsapplication.main.view;

import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseActivity;
import base.imonitore.com.br.mapsapplication.menu.MenuItemEnum;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @AfterViews
    public void init(){
        replaceLinear(R.layout.main_include);
        setTitleApp(getResources().getString(R.string.titleMain));
        setUpToolbar();
        setupNavDrawer(MenuItemEnum.MAIN);

    }

}
