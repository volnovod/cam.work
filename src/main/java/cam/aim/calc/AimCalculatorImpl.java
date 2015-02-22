package cam.aim.calc;

import cam.aim.coordinate.Coordinate;
import cam.aim.domain.Aim;

/**
 * Created by victor on 20.02.15.
 */
public class AimCalculatorImpl implements AimCalculator {

    private Coordinate coordinate;
    private Aim aim;

    public AimCalculatorImpl(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.aim = new Aim();
    }

    @Override
    public Aim calculate(long elevation, long azimuth, long distance) {
        return null;
    }
}
