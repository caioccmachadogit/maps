package base.imonitore.com.br.mapsapplication.route.view;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

import java.util.ArrayList;
import java.util.List;

import base.imonitore.com.br.googledirection.DirectionCallback;
import base.imonitore.com.br.googledirection.GoogleDirection;
import base.imonitore.com.br.googledirection.constant.TransportMode;
import base.imonitore.com.br.googledirection.model.Direction;
import base.imonitore.com.br.googledirection.model.Info;
import base.imonitore.com.br.googledirection.model.Route;
import base.imonitore.com.br.mapsapplication.R;
import base.imonitore.com.br.mapsapplication.base.BaseActivity;
import base.imonitore.com.br.mapsapplication.menu.MenuItemEnum;
import base.imonitore.com.br.mapsapplication.route.polyline.MapAnimator;
import base.imonitore.com.br.mapsapplication.route.polyline.PolylineEnum;
import base.imonitore.com.br.mapsapplication.route.polyline.PolylineType;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_main)
public class RouteActivity extends BaseActivity implements OnMapReadyCallback, DirectionCallback {

    private GoogleMap map;
    private ArrayList markerPoints= new ArrayList();
    private String serverKey = "AIzaSyDB0aLRyNm9MrCPAYHfKsFK41LauOjrFN8";
    private LatLng origin, destination;
    private Button btnStartRoute;
    private PolylineType polylineType;

    //===Anim Car====
    private Marker marker;
    private float v;
    private double lat, lng;
    private Handler handler;
    private LatLng startPosition, endPosition;
    private int index, next;

    @AfterViews
    public void init() {
        TAG = getClass().getSimpleName();
        replaceLinear(R.layout.routes_include);
        setTitleApp(getResources().getString(R.string.titleRoutes));
        setUpToolbar();
        setupNavDrawer(MenuItemEnum.MAPA);

        findView();
    }

    private void findView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnStartRoute = findViewById(R.id.btnStartRoute);
        btnStartRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(origin != null && destination != null)
                    mockRouteCar();
            }
        });
    }

    private void mockRouteCar() {
//        LatLng ori = new LatLng(-23.62393165260316,-46.70234441757202);
//        LatLng dest = new LatLng(-23.62256562289136,-46.70200880616903);

        // Adding new item to the ArrayList
        markerPoints.add(destination);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(destination);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // Add new marker to the Google Map Android API V2
        map.addMarker(options);

//        origin = ori;
//        destination = dest;
        polylineType = new PolylineType(Color.BLUE, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),PolylineEnum.CAR);
        requestDirection();
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

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                log(latLng.toString());

                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    map.clear();
                }

                // Adding new item to the ArrayList
                markerPoints.add(latLng);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(latLng);

                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }

                // Add new marker to the Google Map Android API V2
                map.addMarker(options);

                // Checks, whether start and end locations are captured
                if (markerPoints.size() >= 2) {
                    polylineType = new PolylineType(Color.RED,BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED), PolylineEnum.DIRECTION);
                    origin = (LatLng) markerPoints.get(0);
                    destination = (LatLng) markerPoints.get(1);

                    requestDirection();
                }
            }
        });
    }

    public void requestDirection() {
        log(origin.toString()+" | "+destination.toString());
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);

            map.addMarker(new MarkerOptions().position(origin).icon(polylineType.getIcon()));
            map.addMarker(new MarkerOptions().position(destination).icon(polylineType.getIcon()));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
//            map.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, polylineType.getColorPolyline()));
            switch (polylineType.getPolylineEnum()){
                case DIRECTION:
                    startAnim(directionPositionList);
                    setCameraWithCoordinationBounds(route);
                    break;
                case CAR:
                    startAnim(directionPositionList);
                    Info duration = route.getLegList().get(0).getDuration();
                    log("DURATION-> "+duration.getText());
                    startAnimCar(directionPositionList, origin, convertSecondToMillisecond(duration.getValue()));
                    break;
            }
        } else {

        }
    }

    private long convertSecondToMillisecond(String second) {
        return Long.parseLong(second)*1000;
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    private void startAnim(ArrayList<LatLng> directionPositionList){
        if(map != null) {
            MapAnimator.getInstance().animateRoute(map, directionPositionList);
        } else {
            Toast.makeText(getApplicationContext(), "Map not ready", Toast.LENGTH_LONG).show();
        }
    }


    private void startAnimCar(final List<LatLng> polyLineList, LatLng origin, long duration){
        marker = map.addMarker(new MarkerOptions().position(origin)
        .flat(true)
        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_car)));
        handler = new Handler();
        index = -1;
        next = 1;
        final int countPolyLine = polyLineList.size()-1;
        final long delayMillis = duration/countPolyLine;

        log("DURATION MILLI-> "+duration);
        log("COUNT POLYLINE-> "+countPolyLine);
        log("DELAYMILLIS-> "+delayMillis);

        log("HANDLER-START");
        handler.postDelayed(new Runnable() {
            boolean endAnim;
            @Override
            public void run() {
                log("INDEXPOLYLINE-> "+index);
                if (index < countPolyLine) {
                    index++;
                    next = index + 1;
                }
                else endAnim = true;
                if (index < countPolyLine) {
                    startPosition = polyLineList.get(index);
                    endPosition = polyLineList.get(next);
                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        v = valueAnimator.getAnimatedFraction();
                        lng = v * endPosition.longitude + (1 - v)
                                * startPosition.longitude;
                        lat = v * endPosition.latitude + (1 - v)
                                * startPosition.latitude;
                        LatLng newPos = new LatLng(lat, lng);
                        marker.setPosition(newPos);
                        marker.setAnchor(0.5f, 0.5f);
                        marker.setRotation(getBearing(startPosition, newPos));
                        map.moveCamera(CameraUpdateFactory
                                .newCameraPosition
                                        (new CameraPosition.Builder()
                                                .target(newPos)
                                                .zoom(15.5f)
                                                .build()));
                    }
                });
                if(!endAnim){
                    valueAnimator.start();
                    handler.postDelayed(this, delayMillis);
                }
                else
                    log("HANDLER-END");
            }
        }, 1000);
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }
}
