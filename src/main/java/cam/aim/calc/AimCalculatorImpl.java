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
    private WGStoCK42Impl wgStoCK42;
    private final long a=6378137;//meters
    private final double f = 1/298.257223;
    private final long b = (long) (a*(1-f));
    private double height;

    public AimCalculatorImpl() {
        this.coordinate = new Coordinate();
        this.aim = new Aim();
        this.wgStoCK42 = new WGStoCK42Impl();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Aim getAim() {
        return aim;
    }

    public void setAim(Aim aim) {
        this.aim = aim;
    }

    public double calcE(){
        return (Math.sqrt((a*a-b*b)/(a*a)));
    }

    public double[] calcNM(double B){
        double[] res = new double[2];
        double e = calcE();
        double W = Math.sqrt(1-e*e*Math.sin(B*Math.PI/180)*Math.sin(B*Math.PI/180));
        double N = a/W;
        double M =a*(1-e*e)/(W*W*W);
        res[0] = N;
        res[1] = M;
        return res;
    }

    public static void main(String[] args) {
        AimCalculatorImpl calc = new AimCalculatorImpl();
        calc.calcCoordinate(48.489024, 30.804334, 0, 1700);
        System.out.println(calc.getAim().toString());
    }




    //    B,L,A must be grad
    public void calcCoordinate(double B, double L, double A, double S){
        double[] NM = calcNM(B);
        double N = NM[0];
        double M = NM[1];
        this.coordinate.setLatitudeGrad(calcB(B, A, S, M));
        this.coordinate.setLongtitudeGrad(calcL(B, A, L, S, N));

        this.wgStoCK42.setLatitude(B);
        this.wgStoCK42.setLongtitude(L);
        this.wgStoCK42.setHeight(getHeight());

        wgStoCK42.transformtoCk42();
        BackProjection backProjection  = new BackProjection();
        backProjection.setCk42(wgStoCK42);
        Coordinate ck42= backProjection.transform();

        this.aim.setLatitude(this.coordinate.getLatitudeGrad());
        this.aim.setLongitude(this.coordinate.getLongtitudeGrad());
        this.aim.setLatitudeck42(ck42.getLatitudeGrad());
        this.aim.setLongitudeck42(ck42.getLongtitudeGrad());
    }

    /* result in degrees*/
    public double calcB(double B, double A, double S, double M){
        double res;
        res = Math.toDegrees(S * Math.cos(Math.toRadians(A))/M) + B;
        return res;
    }

    public double calcL(double B, double A, double L, double S, double N){
        double res;
        res = Math.toDegrees(S * Math.sin(Math.toRadians(A))/(N * Math.cos(Math.toRadians(B)))) + L;
        return res;
    }

    public double calcA(double B, double A, double S, double N){
        double res;
        res = Math.toDegrees(S * Math.sin(Math.toRadians(A)) * Math.tan(Math.toRadians(B)) / N) + A;
        return res;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
