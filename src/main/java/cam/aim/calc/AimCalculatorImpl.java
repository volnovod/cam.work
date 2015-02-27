package cam.aim.calc;

import cam.aim.coordinate.Coordinate;
import cam.aim.domain.Aim;
import java.lang.Math;

/**
 * Created by victor on 20.02.15.
 */
public class AimCalculatorImpl implements AimCalculator {

    private Coordinate coordinate;
    private Aim aim;
    private final long a=6378137;//metrs

    public AimCalculatorImpl(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.aim = new Aim();
    }

    @Override
    public Coordinate calculate(double elevation, double azimuth, long distance) {
        long radius = this.calculateRadius(this.coordinate.getLatitudeGrad());
        double xCoordinate = radius * (this.coordinate.getLongtitudeRad());
        double yCoordinate = radius * (Math.log(Math.tan(Math.PI/4+this.coordinate.getLatitudeRad()/2)));
        double dx = distance * (Math.sin(azimuth));
        double dy = distance * (Math.cos(azimuth));

        double xAim = xCoordinate + (dx);
        double yAim = yCoordinate + (dy);

        System.out.println(xAim);
        System.out.println(yAim);

        double longtitude = Math.toDegrees(xAim / (radius));
        double latitude = Math.toDegrees(2*Math.atan(Math.exp(yAim/(radius)))-Math.PI/2);
        return (new Coordinate(latitude, longtitude));
    }

    @Override
    public long calculateRadius(double latitude) {
        long resRadius = (long) (a * (0.99832407+0.00167644*Math.cos(2*latitude)-0.00000352*Math.cos(4*latitude)));//sum multiply 10e8
        return resRadius;
    }

    public static void main(String[] args) {
        Coordinate coordinate1 = new Coordinate(50.155446,31.904916);
        AimCalculator calculator = new AimCalculatorImpl(coordinate1);
        Coordinate coordinate2 = new Coordinate();
        coordinate2 = calculator.calculate(123.23, 6.23, (long)435);
        System.out.println(calculator.calculateRadius(coordinate1.getLatitudeGrad()));
    }
}
