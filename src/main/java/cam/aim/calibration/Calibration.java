package cam.aim.calibration;

/**
 * Created by victor on 30.03.15.
 */
public class Calibration {

    private double deltaA;// up/down deviation
    private double deltaB;// deviation to left/right

    public double getDeltaB() {
        return deltaB;
    }

    public void setDeltaB(double deltaB) {
        this.deltaB = deltaB;
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
