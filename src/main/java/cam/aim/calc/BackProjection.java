package cam.aim.calc;

import cam.aim.coordinate.Coordinate;

/**
 * Created by victor on 04.03.15.
 */
public class BackProjection {

    private final double aKr = 6378245;
    private final double bKr = 6356863.019;
    private double eKr;

    private final double a = 6378245;
    private final double b = 6356863.019;
    private double e;
    private final double error = 0.001;
    private WGStoCK42Impl ck42;

    public BackProjection() {
        this.ck42 = new WGStoCK42Impl();
        this.e = Math.sqrt((a * a - b * b) / (a * a));
        this.eKr = Math.sqrt((aKr * aKr - bKr * bKr) / (aKr * aKr));
    }

    public WGStoCK42Impl getCk42() {
        return ck42;
    }

    public void setCk42(WGStoCK42Impl ck42) {
        this.ck42 = ck42;
    }

    public Coordinate transform() {
        double longtitude;
        double latitude;
        double height;
        double[] param = this.ck42.transformtoCk42();
        double x = param[0];
        double y = param[1];
        double z = param[2];

        double D = Math.sqrt(x * x + y * y);
        if (D == 0) {
            latitude = Math.toDegrees((Math.PI / 2) * z / (Math.abs(z)));
        } else {
            if (y < 0 && x > 0) {
                longtitude = Math.toDegrees(2 * Math.PI - Math.abs(Math.asin(y / D)));
            } else if (y < 0 && x < 0) {
                longtitude = Math.toDegrees(Math.PI + Math.abs(Math.asin(y / D)));
            } else if (y > 0 && x < 0) {
                longtitude = Math.toDegrees(Math.PI - Math.abs(Math.asin(y / D)));
            } else if (y < 0 && x > 0) {
                longtitude = Math.toDegrees(Math.abs(Math.asin(y / D)));
            } else if (y == 0 && x > 0) {
                longtitude = 0;
            } else if (y == 0 && x < 0) {
                longtitude = Math.toDegrees(Math.PI);
            }
        }

        if (z == 0) {
            latitude = 0;
            height = D - aKr;
        } else {
            double r = Math.sqrt(x * x + y * y + z * z);
            double c = Math.asin(z / r);
            double p = eKr * eKr * aKr / (2 * r);

            double s1 = 0;
            double s2 = 0;
            double b;
            double d;
            do {
                s1 = s2;
                s2=Math.asin((p * Math.sin(2 * bKr))/(Math.sqrt(1 - eKr*eKr*Math.sin(bKr)*Math.sin(bKr))));
                b = c + s1;
                d = Math.abs(s2-s1);

            }while (d > error);
                latitude = b;
                height = D*Math.cos(latitude) + z*Math.sin(latitude) - aKr * (Math.sqrt(1 - eKr*eKr*Math.sin(bKr)*Math.sin(bKr)));
            }
        return null;
        }
    }

