package cam.aim.calc;

import cam.aim.coordinate.Coordinate;

/**
 * Created by victor on 20.02.15.
 * it must calculate aim coordinates
 */
public interface AimCalculator {

    public double calcE();
    public void calcCoordinate(double B, double L, double A, double S);
    public double calcB(double B, double A, double S, double M);
    public double calcL(double B, double A, double L, double S, double N);
    public double calcA(double B, double A, double S, double N);
}
