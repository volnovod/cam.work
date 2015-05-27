package cam.aim.comport;

/**
 * Created by victor on 27.05.15.
 */
public interface ComPortReader {

    int[] readData();
    void getCoordinates(int[] buffer);
}
