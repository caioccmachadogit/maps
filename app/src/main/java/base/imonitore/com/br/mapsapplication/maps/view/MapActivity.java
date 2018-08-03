package base.imonitore.com.br.mapsapplication.maps.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import java.util.List;

import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseActivity;
import base.imonitore.com.br.mapsapplication.maps.domain.LocationPoints;
import base.imonitore.com.br.mapsapplication.menu.MenuItemEnum;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_main)
public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap map;

    private LocationPoints mLocationPoints;

    private Button btnRefresh;

    @AfterViews
    public void init() {
        TAG = getClass().getSimpleName();
        replaceLinear(R.layout.maps_include);
        setTitleApp(getResources().getString(R.string.titleMaps));
        setUpToolbar();
        setupNavDrawer(MenuItemEnum.MAPA);

        mLocationPoints = LocationPoints.getInstance();

        findView();
    }

    private void findView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMap(map);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        LatLng sydney = new LatLng(-33.867, 151.206);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));

        drawRouteOnMap(map, mLocationPoints.getPoints());
    }

    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        if(positions.size() > 1){
            int currentPosition = positions.size()-1;
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            options.addAll(positions);
            Polyline polyline = map.addPolyline(options);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(positions.get(currentPosition).latitude, positions.get(currentPosition).longitude))
                    .zoom(18)
                    .bearing(90)
                    .tilt(40)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        else
            toast("Nenhuma Localização Capturada");
    }

    private void refreshMap(GoogleMap mapInstance){
        mapInstance.clear();
        drawRouteOnMap(mapInstance, mLocationPoints.getPoints());
    }
}
