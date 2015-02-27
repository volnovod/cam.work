package cam.aim.coordinate;

/**
 * Created by victor on 20.02.15.
 */
public class Coordinate {

    private double latitude;
    private double longtitude;
    private double latitudeGrad;
    private double longtitudeGrad;

    public Coordinate(double latitude, double longtitude) {
        this.latitude = (latitude*Math.PI)/180;
        this.longtitude = (longtitude*Math.PI)/180;
        this.latitudeGrad = latitude;
        this.longtitudeGrad = longtitude;
    }

    public Coordinate() {
    }

    public double getLatitudeRad() {
        return latitude;
    }

    public void setLatitudeRad(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitudeRad() {
        return longtitude;
    }

    public void setLongtitudeRad(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitudeGrad() {
        return latitudeGrad;
    }

    public void setLatitudeGrad(double latitudeGrad) {
        this.latitudeGrad = latitudeGrad;
    }

    public double getLongtitudeGrad() {
        return longtitudeGrad;
    }

    public void setLongtitudeGrad(double longtitudeGrad) {
        this.longtitudeGrad = longtitudeGrad;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", latitudeGrad=" + latitudeGrad +
                ", longtitudeGrad=" + longtitudeGrad +
                '}';
    }
}
