package base.imonitore.com.br.mapsapplication.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.location.view.LocationActivity;
import base.imonitore.com.br.mapsapplication.location.view.LocationActivity_;
import base.imonitore.com.br.mapsapplication.main.view.MainActivity_;
import base.imonitore.com.br.mapsapplication.maps.view.MapActivity;
import base.imonitore.com.br.mapsapplication.maps.view.MapActivity_;
import base.imonitore.com.br.mapsapplication.menu.MenuCreator;
import base.imonitore.com.br.mapsapplication.menu.MenuItemEnum;
import base.imonitore.com.br.mapsapplication.route.view.RouteActivity;
import base.imonitore.com.br.mapsapplication.route.view.RouteActivity_;
import base.imonitore.com.br.mapsapplication.session.Session;
import base.imonitore.com.br.mapsapplication.utils.AlertUtils;

public class BaseActivity extends AppCompatActivity {

    protected String TAG;

    protected DrawerLayout drawerLayout;

    protected View view;

    protected TextView tvTitle;

    protected Activity mActivity;

    protected TextView tvCodigo;

    protected TextView tvDataData;

    protected Session mSession;

    protected Context getContext() {
        return this;
    }

    protected View getLayouView(){
        return  view;
    }

    protected void replaceLinear(int view) {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container =  findViewById(R.id.content_view);
        this.view = inflater.inflate(view, container);

        this.mSession = Session.getInstance();
    }

    // Adiciona o fragment no centro da tela
    protected void replaceFragment(Fragment frag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag, "TAG").commit();
    }

    //=========================MENU LATERAL E TOOLBAR==========================

    protected void setTitleApp(String title){
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    // Configura a Toolbar
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        setCabecalho();
    }

    private void setCabecalho() {
        tvCodigo = findViewById(R.id.tvCodigo);
//        tvCodigo.setText(mSession.getUser().getCodigoEmpresa());

        tvDataData = findViewById(R.id.tvDataData);
//        tvDataData.setText(Session.getInstance().getDataLogin());
    }

    // Configura o Nav Drawer
    protected void setupNavDrawer(MenuItemEnum menuItemChecked) {
        menuItemChecked.itemIsChecked(menuItemChecked);
        // Drawer Layout
        final ActionBar actionBar = getSupportActionBar();
        // Ícone do menu do nav drawer
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null && drawerLayout != null) {
            // Atualiza a imagem e textos do header
            // NavDrawerUtil.setHeaderValues(navigationView, R.id.containerNavDrawerListViewHeader, R.drawable.nav_drawer_header, R.drawable.ic_logo_user, R.string.nav_drawer_username, R.string.nav_drawer_email);
            // Trata o evento de clique no menu.
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            // Seleciona a linha
                            menuItem.setChecked(true);
                            // Fecha o menu
                            drawerLayout.closeDrawers();
                            // Trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    });
        }

        Menu menu = navigationView.getMenu();
        MenuCreator.create(menu);
        navigationView.invalidate();
    }

    // Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.string.menu_main:
                startActivity(new Intent(this, MainActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case R.string.menu_location:
                startActivity(new Intent(this, LocationActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case R.string.menu_maps:
                startActivity(new Intent(this, MapActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case R.string.menu_route:
                startActivity(new Intent(this, RouteActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                break;
            case R.string.menu_encerrar:
//                showDialogLogout();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trata o clique no botão que abre o menu.
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fecha o menu lateral
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //=========================MENU LATERAL E TOOLBAR==========================

    protected void log(String msg) {
        Log.d(TAG, msg);
    }

    protected void logE(String msg, Throwable ex) {
        Log.e(TAG, msg, ex);
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void alert(String msg) {
        AlertUtils.alert(this, msg);
    }

    protected void alert(String title, String msg) {
        AlertUtils.alert(this, title, msg);
    }

    protected void alert(int msg) {
        AlertUtils.alert(this, getString(msg));
    }

    protected void alert(int title, int msg) {
        AlertUtils.alert(this,getString(title), getString(msg));
    }

    protected void snack(View view, int msg, Runnable runnable) {
        this.snack(view, this.getString(msg), runnable);
    }

    protected void snack(View view, int msg) {
        this.snack(view, this.getString(msg), (Runnable) null);
    }

    protected void snack(View view, String msg) {
        this.snack(view, msg, (Runnable) null);
    }

    protected void snack(View view, String msg, final Runnable runnable) {
        Snackbar.make(view, msg, 0).setAction("Ok", new View.OnClickListener() {
            public void onClick(View v) {
                if (runnable != null) {
                    runnable.run();
                }

            }
        }).show();
    }
}
