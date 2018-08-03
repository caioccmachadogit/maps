package base.imonitore.com.br.mapsapplication.menu;

import base.imonitore.com.br.mapsapplication.R;

/**
 * Created by ccouto on 19/12/2017.
 */

    public enum MenuItemEnum {
        MAIN("Main", false, R.string.menu_main),
        LOCATION("Location", false, R.string.menu_location),
        MAPA("Maps", false, R.string.menu_maps),
        ROUTES("Routes", false, R.string.menu_route),
        ENCERRAR("Encerrar", false, R.string.menu_encerrar);

        private String mItem;
        private boolean isChecked;
        private int menuId;

        MenuItemEnum(String item, boolean isChecked, int menuId) {
            this.mItem = item;
            this.isChecked = isChecked;
            this.menuId = menuId;
        }

        public String getItem() {
            return mItem;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

    public int getMenuId() {
        return menuId;
    }

    public void itemIsChecked(MenuItemEnum itemChecked){
            MAIN.setChecked(false);
            MAPA.setChecked(false);
            ENCERRAR.setChecked(false);
            LOCATION.setChecked(false);
            switch (itemChecked){
                case ENCERRAR:
                    ENCERRAR.setChecked(true);
                    break;
                case MAIN:
                    MAIN.setChecked(true);
                    break;
                case MAPA:
                    MAPA.setChecked(true);
                    break;
                case LOCATION:
                    LOCATION.setChecked(true);
                    break;
                case ROUTES:
                    ROUTES.setChecked(true);
                    break;
            }
        }
    }
