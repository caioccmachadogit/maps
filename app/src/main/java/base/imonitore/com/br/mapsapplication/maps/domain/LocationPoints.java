package base.imonitore.com.br.mapsapplication.maps.domain;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocationPoints {

    private static LocationPoints instance = null;

    public static LocationPoints getInstance() {
        if(instance == null){
            instance = new LocationPoints();
            latLngList = new ArrayList<>();
        }
        return instance;
    }

    private static List<LatLng> latLngList;

    public void addPoint(double lat, double lon){
        latLngList.add(new LatLng(lat,lon));
    }

    public List<LatLng> getPoints() {
        return latLngList;
    }

    public void clearPoints(){
        instance = null;
    }
}
