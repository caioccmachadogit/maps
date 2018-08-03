package base.imonitore.com.br.mapsapplication.menu;

import android.view.Menu;

import base.imonitore.com.br.mapsapplication.session.Session;

/**
 * Created by ccouto on 13/12/2017.
 */

public class MenuCreator {

    public static void create(Menu menu){
        switch (Session.getInstance().getUser().getPerfilUserEnum()){
            case ADM:
                addItemAdmin(menu);
                break;
            case SUP_EXAMINADOR:
                addItemSupervisor(menu);
                addItemExaminador(menu);
                break;
            case EXAMINADOR:
                addItemExaminador(menu);
                break;
        }
        menu.add(1, MenuItemEnum.ENCERRAR.getMenuId(), 1, MenuItemEnum.ENCERRAR.getItem()).setCheckable(true).setChecked(MenuItemEnum.ENCERRAR.isChecked());
//        menuItemEncerrar.setActionView(R.layout.menu_item_encerrar);
    }

    private static void addItemAdmin(Menu menu){
        menu.add(1,MenuItemEnum.MAIN.getMenuId(),1, MenuItemEnum.MAIN.getItem()).setCheckable(true).setChecked(MenuItemEnum.MAIN.isChecked());
        menu.add(1,MenuItemEnum.MAPA.getMenuId(),1, MenuItemEnum.MAPA.getItem()).setCheckable(true).setChecked(MenuItemEnum.MAPA.isChecked());
        menu.add(1,MenuItemEnum.LOCATION.getMenuId(),1, MenuItemEnum.LOCATION.getItem()).setCheckable(true).setChecked(MenuItemEnum.LOCATION.isChecked());
        menu.add(1,MenuItemEnum.ROUTES.getMenuId(),1, MenuItemEnum.ROUTES.getItem()).setCheckable(true).setChecked(MenuItemEnum.ROUTES.isChecked());
    }

    private static void addItemSupervisor(Menu menu){

    }

    private static void addItemExaminador(Menu menu){

    }


}
