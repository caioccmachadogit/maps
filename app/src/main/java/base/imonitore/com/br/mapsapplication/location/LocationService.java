package base.imonitore.com.br.mapsapplication.location;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.androidannotations.annotations.EService;

import base.imonitore.com.br.mapsapplication.maps.domain.LocationPoints;

@EService
public class LocationService extends Service {

    private String TAG = getClass().getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;

    private LocationRequest mLocationRequest;

    private LocationCallback mLocationCallback;

    private long UPDATE_INTERVAL = 10 * 2000;  /* 5 secs */

    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private boolean isStartUpdate = false;

    private LocationPoints mLocationPoints;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        mLocationPoints = LocationPoints.getInstance();
    }

    @Override
    public void onDestroy() {
        mLocationPoints.clearPoints();
        stopLocationUpdates();
        Log.d(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLastLocation();

        startLocationUpdates();

        return START_STICKY;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }

    private void logE(String msg, Throwable ex) {
        Log.e(TAG, msg, ex);
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initProviderClient(){
        if(mFusedLocationClient == null)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void getLastLocation() {
        initProviderClient();
        // Get last known recent location using new Google Play Services SDK (v11+)
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
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        log("getLastLocation");
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        logE("MapDemoActivity-> Error trying to get last GPS location", e);
                        e.printStackTrace();
                    }
                });
    }

    // Trigger new location updates at interval
    private void startLocationUpdates() {
        if(!isStartUpdate){
            isStartUpdate = true;
            initProviderClient();
            log("startLocationUpdates");
            // Create the location request to start receiving updates
            mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(UPDATE_INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

            // Create LocationSettingsRequest object using location request
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();

            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(this);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
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
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    // do work here
                    log("requestLocationUpdates");
                    onLocationChanged(locationResult.getLastLocation());
                }
            };
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
        }
    }

    private void stopLocationUpdates() {
        if(isStartUpdate){
            isStartUpdate = false;
            log("stopLocationUpdates");
            if (mFusedLocationClient != null) {
                if(mLocationCallback != null)
                    mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }
            mFusedLocationClient = null;
            mLocationRequest = null;
            mLocationCallback = null;
        }
    }

    private void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        toast(msg);
        log(msg);
        // You can now create a LatLng Object for use with maps
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mLocationPoints.addPoint(location.getLatitude(), location.getLongitude());
    }
}
