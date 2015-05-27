package cam.aim.rotate;

/**
 * Created by victor on 27.05.15.
 */
public interface RotateSurface {

    double[] calculateIdealCoordinates(double elevation, double azimuth);
    double[] fromDecToSpherical(double[] coordinates);
    double[] rotateAroundOx(double angle);
    double[] rotateAroundOy(double angle);
}
