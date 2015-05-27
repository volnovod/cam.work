package cam.aim.comport;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Created by victor on 20.03.15.
 * клас для отримання інформації по СОМ-порту
 */
public class ComPortReaderImpl implements ComPortReader{

    private String comUrl;
    private String longitude;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * конструктор, в якому відбувається ініціалізація адреси доступного порту
     */
    public ComPortReaderImpl() {
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            this.comUrl=portNames[i];
        }
    }

    /**
     * метод, який зчитує масив значень із СОМ-порту
     * @return масив символів ASCII, в якому
     */
    @Override
    public int[] readData(){

        SerialPort serialPort = new SerialPort(comUrl);
        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8 , 1, 0);
            int[] buffer = serialPort.readIntArray(33);
            System.out.println("read " + buffer.length + " bytes");
            serialPort.closePort();
            return buffer;

        } catch (SerialPortException e) {
            setLatitude("Помилка читання Com-port");
            setLongitude("Помилка читання Com-port");
            setPitch("Помилка читання Com-port");
            setRoll("Помилка читання Com-port");
            e.printStackTrace();


        }
        return null;

    }

    /**
     * метод, що виділяє із отриманого масиву значень виділити координати GPS та кути нахилу платформи
     * @param buffer
     */
    @Override
    public void getCoordinates(int[] buffer){
        String lat1="";
        String longt="";

        String ph="";
        String rl="";

        int counter=0;

        if(buffer[0] == 63){
            setLatitude("GPS_Error");
            setLongitude("GPS_Error");
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
        setLongitude(longt);
        while(buffer[counter] != 80){
            counter++;
        }
        counter++;
        ph+=(char)buffer[counter];
        counter++;
        ph+=(char)buffer[counter];
        counter++;
        ph+=(char)buffer[counter];
        this.setPitch(ph);

        counter+=2;
        rl+=(char) buffer[counter];
        counter++;
        rl+= (char) buffer[counter];
        counter++;
        rl+= (char) buffer[counter];

        this.setRoll(rl);
    }

//    public static void main(String[] args) {
//        int[] data = {49, 48, 50, 52, 46, 55, 57, 57, 57, 44, 102, 44, 48, 51, 48, 53, 56, 46, 51, 56, 53, 53, 44, 69, 44, 80, 45, 105, 82, 43, 255};
//        ComPortReaderImpl reader = new ComPortReaderImpl();
//        int[] data = reader.readData();
//        for(int el: data){
//            System.out.print(el + " ");
//        }
//        reader.getCoordinates(data);
//        System.out.println("Latitude " + reader.getLatitude());
//        System.out.println("Longtitude " + reader.getLongitude());
//        System.out.println("Pitch " + reader.getPitch());
//        System.out.println("Roll " + reader.getRoll());
//        System.out.println(reader.comUrl);

//        String comUrl ="";
//        String[] portNames = SerialPortList.getPortNames();
//        for(int i = 0; i < portNames.length; i++){
//            comUrl=portNames[i];
//        }
//
//        System.out.println(comUrl);
//    }
}
