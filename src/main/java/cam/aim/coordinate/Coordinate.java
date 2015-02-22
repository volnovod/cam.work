package cam.aim.coordinate;

/**
 * Created by victor on 20.02.15.
 */
public class Coordinate {

    private long latitude;
    private long longtitude;

    public Coordinate(long latitude, long longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Coordinate() {
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(long longtitude) {
        this.longtitude = longtitude;
    }
}
