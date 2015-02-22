package cam.aim.calc;

import cam.aim.domain.Aim;

/**
 * Created by victor on 20.02.15.
 * it must calculate aim coordinates
 */
public interface AimCalculator {

    Aim calculate(long elevation, long azimuth, long distance);
}
