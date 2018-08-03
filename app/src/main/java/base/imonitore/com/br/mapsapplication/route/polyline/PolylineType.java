package base.imonitore.com.br.mapsapplication.route.polyline;

import com.google.android.gms.maps.model.BitmapDescriptor;

public class PolylineType {

    private int colorPolyline;

    private BitmapDescriptor icon;

    private PolylineEnum polylineEnum;

    public PolylineEnum getPolylineEnum() {
        return polylineEnum;
    }

    public void setPolylineEnum(PolylineEnum polylineEnum) {
        this.polylineEnum = polylineEnum;
    }

    public int getColorPolyline() {
        return colorPolyline;
    }

    public void setColorPolyline(int colorPolyline) {
        this.colorPolyline = colorPolyline;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }

    public PolylineType(int colorPolyline, BitmapDescriptor icon, PolylineEnum polylineEnum) {
        this.colorPolyline = colorPolyline;
        this.icon = icon;
        this.polylineEnum = polylineEnum;
    }
}
