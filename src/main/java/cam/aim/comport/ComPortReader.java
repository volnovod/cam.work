package cam.aim.comport;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Created by victor on 20.03.15.
 */
public class ComPortReader {

    private String comUrl;
    private String longtitude;
    private String latitude;
    private String pitch;
    private String roll;

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

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
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            this.comUrl=portNames[i];
        }
    }

    public int[] readData(){

        SerialPort serialPort = new SerialPort(comUrl);
        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8 , 1, 0);
            int[] buffer = serialPort.readIntArray(32);
            System.out.println("read " + buffer.length + " bytes");
            serialPort.closePort();
            return buffer;

        } catch (SerialPortException e) {
            setLatitude("Помилка читання Com-port");
            setLongtitude("Помилка читання Com-port");
            setPitch("Помилка читання Com-port");
            setRoll("Помилка читання Com-port");
            e.printStackTrace();


        }
        return null;

    }

    public void getCoordinates(int[] buffer){
        String lat1="";
        String longt="";

        String ph="";
        String rl="";

        int counter=0;

        if(buffer[0] == 63){
            setLatitude("GPS_Error");
            setLongtitude("GPS_Error");
            return;
        }

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
        while(buffer[counter] != 80){
            counter++;
        }
        counter++;
        ph+=(char)buffer[counter];
//        System.out.print((char)buffer[counter]);
        counter++;
        ph+=buffer[counter];
        this.setPitch(ph);
//        System.out.println(buffer[counter]);

        counter+=2;
        rl+=(char) buffer[counter];
//        System.out.print((char)buffer[counter]);
        counter++;
        rl+= buffer[counter];
//        System.out.println(buffer[counter]);

        this.setRoll(rl);


    }

    public static void main(String[] args) {
        int[] data = {49, 48, 50, 52, 46, 55, 57, 57, 57, 44, 102, 44, 48, 51, 48, 53, 56, 46, 51, 56, 53, 53, 44, 69, 44, 80, 45, 105, 82, 43, 255};
        ComPortReader reader = new ComPortReader();
////        byte[] data = reader.readData();
        reader.getCoordinates(data);
        System.out.println("Latitude " + reader.getLatitude());
        System.out.println("Longtitude " + reader.getLongtitude());
        System.out.println("Pitch " + reader.getPitch());
        System.out.println("Roll " + reader.getRoll());
//        System.out.println(reader.comUrl);
//        byte b = (byte) 128;

//        System.out.println(b);


    }
}
