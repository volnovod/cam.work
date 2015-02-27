package cam.aim.calc;

import cam.aim.coordinate.Coordinate;

/**
 * Created by victor on 20.02.15.
 * it must calculate aim coordinates
 */
public interface AimCalculator {

    Coordinate calculate(double elevation, double azimuth, long distance);
    long calculateRadius(double latitude);
}
