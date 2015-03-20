package cam.aim.comport;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Created by victor on 20.03.15.
 */
public class ComPortReader {

    private final String comUrl = "/dev/ttyUSB1";
    private String longtitude;
    private String latitude;

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public ComPortReader() {
    }

    public byte[] readData(){

        SerialPort serialPort = new SerialPort(comUrl);
        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8 , 1, 0);
            byte[] buffer = serialPort.readBytes(24);
            System.out.println("read " + buffer.length + " bytes");
            serialPort.closePort();
            return buffer;

        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void getCoordinates(byte[] buffer){
        String lat1="";
        String longt="";

        int counter=0;

        for(int i =0; i < 2; i++){
            lat1+=(char)buffer[i];
        }

        lat1+=".";

        for(int i=2;i < 4; i++, counter=i ){
            lat1+=(char)buffer[i];
        }

        counter++;

        while(buffer[counter] != 44){
            lat1+=(char)buffer[counter];
            counter++;
        }
        this.setLatitude(lat1);

        counter+=3;

        int j = counter;

        for(int i = counter; i < (j + 3); i++, counter=i){
            longt+=(char) buffer[i];
        }

        longt+=".";
        j = counter;
        for(int i=counter; i < (j+2); i++, counter++){
            longt+=(char)buffer[i];
        }
        counter++;

        while(buffer[counter] != 44){
            longt+=(char)buffer[counter];
            counter++;
        }
        setLongtitude(longt);


    }

    public static void main(String[] args) {
//        byte[] data = {49, 48, 50, 52, 46, 55, 57, 57, 57, 44, 102, 44, 48, 51, 48, 53, 56, 46, 51, 56, 53, 53, 44, 69};
        ComPortReader reader = new ComPortReader();
        byte[] data = reader.readData();
        reader.getCoordinates(data);
        System.out.println(reader.getLatitude());
        System.out.println(reader.getLongtitude());
    }
}
