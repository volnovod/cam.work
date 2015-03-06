package cam.aim.calc;

/**
 * Created by victor on 03.03.15.
 */
public class WGStoCK42Impl implements WGStoCK42 {

    private ProjectionToPlane projectionToPlane;
    private double longtitude;
    private double latitude;
    private double height;

    public WGStoCK42Impl() {
    }

    public WGStoCK42Impl(double longtitude, double latitude, double height) {
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
    public double[] transformtoCk42() {
        double[] res = new double[3];
        double[] xyz = this.transformtoPz90();

        double x = xyz[0];
        double y = xyz[1];
        double z = xyz[2];

        res[0] = (x + 3.1998*10E-6 * y - 1.6968*10E-6 * z) - 25;
        res[1] = (-3.1998*10E-6 * x + y) + 141;
        res[2] = (1.6968*10E-6 * x + z) + 80;
        return res;
    }

    public static void main(String[] args) {
        WGStoCK42 wgStoCK42 = new WGStoCK42Impl(51.1245, 32.2356, 125.36);
        wgStoCK42.transformtoCk42();
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


}
