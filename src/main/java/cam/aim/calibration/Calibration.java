package cam.aim.calibration;

/**
 * Created by victor on 30.03.15.
 */
public class Calibration {

    private double deltaA;// up/down deviation
    private double deltaNorth;// deviation to north

    public double getDeltaNorth() {
        return deltaNorth;
    }

    public void setDeltaNorth(double deltaNorth) {
        this.deltaNorth = deltaNorth;
    }

    public double getDeltaA() {
        return deltaA;
    }

    public void setDeltaA(double deltaA) {
        this.deltaA = deltaA;
    }

    public Calibration() {

    }
}
