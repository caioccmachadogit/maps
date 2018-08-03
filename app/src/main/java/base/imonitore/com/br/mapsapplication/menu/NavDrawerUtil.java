package base.imonitore.com.br.mapsapplication.menu;

import android.view.View;

/**
 * Created by ccouto on 24/10/2017.
 */

public class NavDrawerUtil {
    public static void setHeaderValues(View navDrawerView, int listViewContainerId, int imgNavDrawerHeaderId, int imgUserUserPhotoId, int stringNavUserName, int stringNavUserEmail) {

        View view = navDrawerView.findViewById(listViewContainerId);
        if(view == null) {
            return;
        }

        view.setVisibility(View.VISIBLE);

//        ImageView imgUserBackground = (ImageView) view.findViewById(br.com.livroandroid.androidutils.R.id.imgUserBackground);
//
//        if (imgUserBackground == null) {
//            return;
//        }
//
//        imgUserBackground.setImageResource(imgNavDrawerHeaderId);
//
//        TextView tUserName = (TextView) view.findViewById(br.com.livroandroid.androidutils.R.id.tUserName);
//        TextView tUserEmail = (TextView) view.findViewById(br.com.livroandroid.androidutils.R.id.tUserEmail);
//
//        ImageView imgUserPhoto = (ImageView) view.findViewById(br.com.livroandroid.androidutils.R.id.imgUserPhoto);
//        if (imgUserPhoto != null) {
//            imgUserPhoto.setImageResource(imgUserUserPhotoId);
//        }
//
//        if (tUserName != null && tUserEmail != null) {
//            tUserName.setText(stringNavUserName);
//            tUserEmail.setText(stringNavUserEmail);
//        }
    }
}
