package cam.aim.calc;

import cam.aim.coordinate.Coordinate;

/**
 * Created by victor on 03.03.15.
 */
public class WGStoCK42Impl implements WGStoCK42 {

    private ProjectionToPlane projectionToPlane;
    private double longtitude;
    private double latitude;
    private double height;

    private double longitude42;
    private double latitude42;
    private double height42;

    public WGStoCK42Impl() {
        this.projectionToPlane = new ProjectionToPlaneImpl();
    }

    public WGStoCK42Impl(double latitude, double longtitude, double height) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.height = height;
        this.projectionToPlane = new ProjectionToPlaneImpl();
    }

    @Override
    public double[] transformtoPz90() {
        double[] res = new double[3];
        double[] xyz = this.projectionToPlane.projection(this.getLatitude(), this.getLongtitude(), this.getHeight());



        double x = xyz[0];
        double y = xyz[1];
        double z = xyz[2];

        res[0] = (1 + 0.12*10E-6) * (x + y * 0.9696 * 10E-6) + 1.1;
        res[1] = (1 + 0.12*10E-6) * (-x*0.9696 * 10E-6 + y ) + 0.3;
        res[2] = (1 + 0.12*10E-6) * z + 0.9;

        return res;
    }

    @Override
    public void transformtoCk42() {

        double[] xyz = this.transformtoPz90();

        double x = xyz[0];
        double y = xyz[1];
        double z = xyz[2];

        double lat = (x + 3.1998*10E-6 * y - 1.6968*10E-6 * z) - 25;
        double lon = (-3.1998 * 10E-6 * x + y) + 141;
        double he = (1.6968*10E-6 * x + z) + 80;

        this.setLatitude42(lat);
        this.setLongitude42(lon);
        this.setHeight42(he);
    }

    public static void main(String[] args) {
        WGStoCK42Impl wgStoCK42 = new WGStoCK42Impl(51.145, 132.2356, 125.36);
        wgStoCK42.transformtoCk42();
        BackProjection backProjection  = new BackProjection();
        backProjection.setCk42(wgStoCK42);
        Coordinate res = backProjection.transform();

        System.out.println(res.toString());

    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLongitude42() {
        return longitude42;
    }

    public void setLongitude42(double longitude42) {
        this.longitude42 = longitude42;
    }

    public double getLatitude42() {
        return latitude42;
    }

    public void setLatitude42(double latitude42) {
        this.latitude42 = latitude42;
    }

    public double getHeight42() {
        return height42;
    }

    public void setHeight42(double height42) {
        this.height42 = height42;
    }
}
